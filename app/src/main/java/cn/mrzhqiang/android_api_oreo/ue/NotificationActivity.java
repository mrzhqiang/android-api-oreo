package cn.mrzhqiang.android_api_oreo.ue;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.mrzhqiang.android_api_oreo.DemoActivity;
import cn.mrzhqiang.android_api_oreo.R;


public class NotificationActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // 默认的通知管理器
        notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        // v4的通知管理器
        notificationManagerCompat = NotificationManagerCompat.from(this);

        // 8.0，创建一个通知通道
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // id：每个包唯一，值过长(>1000)会被截断；
            // name：用户可见的通道名，截断判断与id一致，推荐40个字符；
            // importance：通道的重要性
            NotificationChannel channel = new NotificationChannel("1", "1", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /** 默认最简单的通知 */
    public void sendNormal(View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Notification notify = new Notification.Builder(this, "1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("简单的通知")
                    .setContentText("empty")
                    .build();
            notificationManager.notify(1, notify);
        } else {
            Notification notify = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("简单的通知")
                    .setContentText("empty")
                    .build();
            notificationManagerCompat.notify(2, notify);
        }
    }

    public void empty() {

        Intent intent = new Intent(this, NotificationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(DemoActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent notifyPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notify = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 这个方法仅支持23以上
            Icon icon = Icon.createWithResource(this, R.mipmap.ic_launcher);

            notify = new Notification.Builder(this, "1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("您有一条新消息")
                    .setContentText("今天天气还不错！")
                    .setContentIntent(notifyPendingIntent)
                    // 底部一个操作栏，显示文字，icon好像没有显示，pendingIntent表示点击之后的事件触发
                    .addAction(new Notification.Action.Builder(icon, "点击重启26", pendingIntent).build())
                    .addPerson("tel:15099937395")
                    .setAutoCancel(true)
                    .build();
        } else {
            notify = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("您有一条新消息")
                    .setContentText("今天天气还不错！")
                    .setContentIntent(notifyPendingIntent)
                    // 操作动作
                    .addAction(R.mipmap.ic_launcher, "点击重启26以下", pendingIntent)
                    // 联系人相关
                    .addPerson("tel:15099937395")
                    // 点击自动取消
                    .setAutoCancel(true)
                    // 优先级
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();
        }
        notificationManager.notify(0, notify);
    }
}
