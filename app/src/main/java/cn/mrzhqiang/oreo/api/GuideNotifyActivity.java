package cn.mrzhqiang.oreo.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

  NotificationManager notificationManager;
  NotificationManager mNotificationManager;
  NotificationManagerCompat notificationManagerCompat;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_nofity_guide);

    /*首先，取得NotificationManager*/

    // Tips：通过API参考得知，可以用Context.getSystemService(Class or String)的方式取得实例

    // a.传入Class，内部调用字符串常量取得实例
    notificationManager = getSystemService(NotificationManager.class);
    // b.字符串常量，要类型转换
    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    // c.兼容管理器，内部包裹NotificationManager，在旧版本上使用
    notificationManagerCompat = NotificationManagerCompat.from(this);

    // 关于NotificationManager：
    // 通过Context.getSystemService获取的系统服务可能与Context密切相关
    // 一般来说，不要在各种不同的Context（Activities、Applications、Services、Providers等）之间共享服务对象
    // 原文：
    /*
     * Note: System services obtained via this API may be closely associated with
     * the Context in which they are obtained from.  In general, do not share the
     * service objects between various different contexts (Activities, Applications,
     * Services, Providers, etc.)
     * */

    /*额外的，通知通道以及通道分组*/

    // 需要AccountManager获取Account来创建或更新NotificationChannelGroup：
    // 1.只要AccountManager处于活动状态，Context就会被使用，所以一定要使用一个Context，
    // 其生命周期与任何注册到addOnAccountsUpdatedListener或类似方法的监听器都是相称的
    // 2.主线程调用是安全的
    // 3.不需要任何权限
    AccountManager accountManager = AccountManager.get(this);
    Account[] accounts = accountManager.getAccounts();
    for (Account a : accounts) {
      // 创建通知类别分组。官方建议：分组内的所有通道都属于同一个账户；多账户对应多分组。
      NotificationChannelGroup group = new NotificationChannelGroup(a.name, "类别组100");
    }
    // 更新或新建这个分组，内部会转换为List，调用了createNotificationChannelGroups(List)
    notificationManager.createNotificationChannelGroup(group);


    // 8.0需要创建通知通道/通知类别：这个将区分应用发出来的不同通知，可以单独针对这一类的通知，进行屏蔽等操作
    // 如何查看通知类别？长按某个通知内容即可。
    NotificationChannel channel =
        new NotificationChannel("1", "单个类别1", NotificationManager.IMPORTANCE_DEFAULT);
    // 这里开启的话，会在应用启动图标上，显示一个主题色的圆形角标，但好像没有数字；默认就是true
    channel.setShowBadge(true);
    // 类别组，只用于分组，没有其他特别的含义，甚至系统找不到相关内容，而且目前也没有任何作用
    channel.setGroup(group.getId());
    channel.setName("新的类别1");

    // 这里是创建和更新单个类别的方法
    notificationManager.createNotificationChannel(channel);
  }

  public void design(View view) {
    Toast.makeText(this, "设计注意事项暂未完成", Toast.LENGTH_SHORT).show();
    // TODO https://material.google.com/patterns/notifications.html
    // TODO https://developer.android.google.cn/design/patterns/notifications.html
  }

  public void create(View view) {
    Toast.makeText(this, "创建了一个通知", Toast.LENGTH_SHORT).show();

    /*一、创建通知*/

    // 使用Notification.Builder或NotificationCompat.Builder指定相关内容：UI和操作
    // Notification.Builder在3.0版本（API 11）上被引入

    // FIXME 在旧系统下，仅使用this参数或许能正常工作；但在8.0系统上，发不出来通知
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
    // 使用Builder.build()创建Notification对象
    // 使用NotificationManager.notify()方法，传入Notification对象，就可以发送到抽屉式通知栏
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
        new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "重新启动通知指南", pendingIntent)
            /*.addExtras()
            .addRemoteInput()
            .extend()
            .setAllowGeneratedReplies()*/.build();
    // 可以添加多个，以按钮形式排列，当超出内容时，后续添加则没有作用
    builder.addAction(action);

    /*// 将额外的元数据合并到这个通知中，几乎很少用这个Bundle，应该是与addPerson一起使用
    Bundle bundle = new Bundle();
    bundle.putString("data", "11111");
    builder.addExtras(bundle);*/

    /*// 添加一个与此通知相关的联系人，应该是通讯录中的Uri content://
    builder.addPerson()*/

    /*// extend 看源码似乎是添加元数据，并且与会话/未读消息相关
    NotificationCompat.CarExtender carExtender = new NotificationCompat.CarExtender();
    carExtender.setColor(Color.BLUE);
    carExtender.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
    NotificationCompat.CarExtender.UnreadConversation unreadConversation =
        new NotificationCompat.CarExtender.UnreadConversation.Builder("11").addMessage("nihao")
            .setLatestTimestamp(System.currentTimeMillis() - 10 * 1000)
            .setReadPendingIntent(pendingIntent)
            .build();
    carExtender.setUnreadConversation(unreadConversation);
    builder.extend(carExtender);
    // 好像需要Compat发送通知
    notificationManagerCompat.notify("carExtender", 1, builder.build());*/

    /*// 自动取消，会触发DeleteIntent；目前没有找到自动取消的方式
    builder.setAutoCancel(true);
    builder.setDeleteIntent(pendingIntent);*/

    /*// 通知标志图标类型
    // BADGE_ICON_LARGE 惊喜！右边出现大图标
    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
    // BADGE_ICON_SMALL 小图标，看不出有什么变化
    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
    // BADGE_ICON_NONE 默认就是这样，源码说是数字，并没有看到
    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);*/

    // 通知类别

    /*// 闹钟或计时器
    builder.setCategory(NotificationCompat.CATEGORY_ALARM);
    // 来电语音、视频，或类似的同步通信
    builder.setCategory(NotificationCompat.CATEGORY_CALL);
    // 异步的大部分信息，如电子邮件
    builder.setCategory(NotificationCompat.CATEGORY_EMAIL);
    // 后台操作或身份验证状态的错误
    builder.setCategory(NotificationCompat.CATEGORY_ERROR);
    // 日历事件
    builder.setCategory(NotificationCompat.CATEGORY_EVENT);
    // 传入的消息，SMS、即时消息
    builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
    // 长时间运行的后台任务
    builder.setCategory(NotificationCompat.CATEGORY_PROGRESS);
    // 促销或广告
    builder.setCategory(NotificationCompat.CATEGORY_PROMO);
    // 推荐
    builder.setCategory(NotificationCompat.CATEGORY_RECOMMENDATION);
    // 日程提醒
    builder.setCategory(NotificationCompat.CATEGORY_REMINDER);
    // 后台运行服务的指示
    builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
    // 社交网络或共享更新
    builder.setCategory(NotificationCompat.CATEGORY_SOCIAL);
    // 关于设备或上下文状态的持续信息
    builder.setCategory(NotificationCompat.CATEGORY_STATUS);
    // 系统或设备状态更新。预留给系统使用
    builder.setCategory(NotificationCompat.CATEGORY_SYSTEM);
    // 媒体传输控制播放
    builder.setCategory(NotificationCompat.CATEGORY_TRANSPORT);*/

    // 发通知
    notificationManager.notify("notify", 1, builder.build());
    //notificationManager.notify("notify", 2, builder.build());

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
