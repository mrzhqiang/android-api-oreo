package cn.mrzhqiang.oreo.api;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import cn.mrzhqiang.oreo.R;

/**
 * 通知使用指南
 *
 * @author mrZQ.
 */

public class GuideNotifyActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nofity_guide);
  }

  public void design(View view) {
    Toast.makeText(this, "设计注意事项暂未完成", Toast.LENGTH_SHORT).show();
    // TODO https://material.google.com/patterns/notifications.html
    // TODO https://developer.android.google.cn/design/patterns/notifications.html
  }

  public void create(View view) {
    Toast.makeText(this, "创建了一个通知", Toast.LENGTH_SHORT).show();

    /*取得NotificationManager*/
    // TIP：通过API参考得知，可以用Context.getSystemService(Class or String)的方式取得实例

    // a.字符串常量的方式，需要类型转换，不建议使用。需要注意的是：如果传入错误的字符串，则返回null
    //NotificationManager notificationManager =
    //    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    // b.传入Class的方式，简单舒服，内部调用字符串常量的方式。需要注意的是：如果传入错误的Class，则返回null
    NotificationManager notificationManager = getSystemService(NotificationManager.class);

    // TODO 一般的，以上两种方式获得的系统服务，不要在Activities、Applications、Services、Providers之间共享
    // 也就是说，最好在使用的时候，去获得对应上下文的系统服务，而不是当做全局系统服务来用
    /*
     * 原文：
     * Note: System services obtained via this API may be closely associated with
     * the Context in which they are obtained from.  In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     * */

    // 也可以使用兼容包的管理器，也很简单，内部也用字符串常量方式获得系统通知服务。
    // 这个只是包裹了一个NotificationManager，并针对旧版本的相关方法进行兼容
    //NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);


    /*创建通知*/
    // 使用Notification.Builder或NotificationCompat.Builder指定相关内容：UI和操作
    // Notification.Builder在3.0版本（API 11）上被引入

    // 这里是创建和更新类别群组，API也提供了批量更新创建的方法——但基本上没什么特别的作用
    NotificationChannelGroup group = new NotificationChannelGroup("100", "类别组100");
    notificationManager.createNotificationChannelGroup(group);

    // 8.0需要创建通知通道/通知类别：这个将区分应用发出来的不同通知，可以单独针对这一类的通知，进行屏蔽等操作
    // 如何查看通知类别？长按某个通知内容即可。
    NotificationChannel channel =
        new NotificationChannel("1", "单个类别1", NotificationManager.IMPORTANCE_DEFAULT);
    // 这里开启的话，会在应用启动图标上，显示一个主题色的圆形角标，但好像没有数字；默认就是true
    //channel.setShowBadge(true);
    // 类别组，只用于分组，没有其他特别的含义，甚至系统找不到相关内容，而且目前也没有任何作用
    //channel.setGroup(group.getId());
    channel.setName("新的类别1");

    // 这里是创建和更新单个类别的方法
    notificationManager.createNotificationChannel(channel);

    // FIXME 在旧系统下，仅使用this参数或许能正常工作；但在8.0系统上，发不出来通知
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
    // 使用Builder.build()创建Notification对象
    // 使用NotificationManager.notify()方法，传入Notification对象，就可以发送到抽屉式通知栏
    // FIXME 没有设定必需内容，不可用
    //Notification notification = builder.build();
    //notificationManager.notify(1, notification);

    //1. 必需的通知内容
    builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("通知标题").setContentText("通知内容");

    //2. 可选的通知内容和设置

    // 在抽屉式通知栏显示的通知内容底部，添加一个类似按钮的动作，点击之后会触发PendingIntent包含的事件
    Intent intent = new Intent(this, GuideNotifyActivity.class);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    NotificationCompat.Action action =
        new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "启动通知指南", pendingIntent)
            //.addExtras()
            //.addRemoteInput()
            //.extend()
            //.setAllowGeneratedReplies()
            .build();
    // 可以添加多个，以按钮形式排列，当超出内容时，后续添加则没有作用
    builder.addAction(action);

    // 将额外的元数据合并到这个通知中，基本上很少用这个Bundle
    Bundle bundle = new Bundle();
    bundle.putString("data", "11111");
    builder.addExtras(bundle);

    // 添加一个与此通知相关的联系人，应该是通讯录中的Uri content://
    //builder.addPerson()

    // TODO https://developer.android.google.cn/reference/android/support/v4/app/NotificationCompat.Builder.html
    // extend

    // 发通知
    notificationManager.notify("notify", 1, builder.build());

    // 查看多个类别组以及多个类别的信息
    /*List<NotificationChannelGroup> groupList = notificationManager.getNotificationChannelGroups();
    List<NotificationChannel> channelList = notificationManager.getNotificationChannels();
    StringBuilder groupBuilder = new StringBuilder();
    for (NotificationChannelGroup group1 : groupList) {
      groupBuilder.append(group1.getId()).append(group1.getName()).append(",");
    }
    StringBuilder channelBuilder = new StringBuilder();
    for (NotificationChannel channel1 : channelList) {
      channelBuilder.append(channel1.getId())
          .append(channel1.getGroup())
          .append(channel1.getName()).append(",");
    }
    notificationManager.notify("group", 2, new Notification.Builder(this, "1")
        .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("检查群类别").setContentText(groupBuilder.toString())
        .build());
    notificationManager.notify("channel", 3, new Notification.Builder(this, "1")
        .setSmallIcon(R.mipmap.ic_launcher).setContentTitle("检查单个类别").setContentText(channelBuilder.toString())
        .build());*/
  }
}
