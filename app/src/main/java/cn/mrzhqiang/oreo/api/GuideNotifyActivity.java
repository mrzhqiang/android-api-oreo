package cn.mrzhqiang.oreo.api;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import cn.mrzhqiang.oreo.R;
import java.util.ArrayList;
import java.util.List;

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
    // 推荐用AccountManager获取Account来创建或更新NotificationChannelGroup
    // 或者应用中的其他账户系统，有一个应用中唯一的id和账户名

    // 关于AccountManager:
    // 1.只要AccountManager处于活动状态，Context就会被使用，所以一定要使用一个Context，
    // 其生命周期与任何注册到addOnAccountsUpdatedListener或类似方法的监听器都是相称的
    // 2.在主线程调用是安全的
    // 3.不需要任何权限
    AccountManager accountManager = AccountManager.get(this);
    Account[] accounts = accountManager.getAccountsByType("Oreo");

    // 事实上AccountManager相关内容比较复杂，如果有自己的多账户系统，也可以在这里遍历生成对应的通道分组

    // 由于很多时候Account[]是empty，因此在这里胡诌2个
    accounts = new Account[2];
    accounts[0] = new Account("username1", "Oreo");
    accounts[1] = new Account("username2", "Oreo");

    // 8.0需要创建通知通道/通知类别：这个将区分应用发出来的不同通知，可以单独针对这一类的通知，进行屏蔽等操作
    // 如何查看通知类别？长按某个通知内容即可。

    for (Account a : accounts) {
      // 创建通知类别分组。官方建议：分组内的所有通道都属于同一个账户；多个账户对应多个分组。
      // id是应用中唯一的字符串，name则应该是统一的名称，因此在应用中，这一组通道具有相同的功能，只是归属不同的账户
      NotificationChannelGroup group = new NotificationChannelGroup(a.name, "Oreo");
      // 要先存在这个分组
      notificationManager.createNotificationChannelGroup(group);

      List<NotificationChannel> channelList = new ArrayList<>();
      // 这里设定了三个通道，需要注意的是，id必须唯一
      for (int i = 0; i < 3; i++) {
        // 第三个参数，重要性，后面再研究
        NotificationChannel channel =
            new NotificationChannel(a.name + i, "类别-" + i, NotificationManager.IMPORTANCE_DEFAULT);
        // 这里开启的话，会在应用启动图标上，显示一个主题色的圆形角标，但好像没有数字；默认就是true
        channel.setShowBadge(true);
        // 避开免打扰
        channel.setBypassDnd(true);
        channel.setDescription("简单的描述");
        // 设定通道分组
        channel.setGroup(group.getId());
        // 其他方法设定的是这个通道的特性，比如声音、颜色等，相当于归类了通知；而分组则归类于多账户

        channelList.add(channel);
      }

      // 创建和更新当前分组下的所有类别
      notificationManager.createNotificationChannels(channelList);
    }
    // 更新或新建这个分组
    //notificationManager.createNotificationChannelGroups(groupList);
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
    final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1");
    // 使用Builder.build()创建Notification对象
    // 使用NotificationManager.notify()方法，传入Notification对象，就可以发送到抽屉式通知栏
    //Notification notification = builder.build();
    //notificationManager.notify(1, notification);

    //1. 必需的通知内容
    builder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("通知标题").setContentText("通知内容");

    //2. 可选的通知内容和设置
    // a.在抽屉式通知栏显示的通知内容底部，添加一个类似按钮的动作，点击之后会触发PendingIntent包含的事件
    Intent intent = new Intent(this, GuideNotifyActivity.class);
    PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    NotificationCompat.Action action =
        new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "重新启动通知指南", pendingIntent)
            /*.addExtras()
            .addRemoteInput()
            .extend()
            .setAllowGeneratedReplies()*/.build();
    // 可以添加多个，以按钮形式排列，不能超过3个
    builder.addAction(action);

    // b.将额外的元数据合并到这个通知中，几乎很少用这个Bundle，应该是与addPerson一起使用*
    Bundle bundle = new Bundle();
    bundle.putString("data", "11111");
    builder.addExtras(bundle);

    // c.添加一个与此通知相关的联系人，应该是通讯录中的Uri content://*
    builder.addPerson("tel:15099937395");

    // d.向通知Builder应用扩展器，这个例子只是实验性质，并未成功*
    NotificationCompat.CarExtender carExtender = new NotificationCompat.CarExtender();
    carExtender.setColor(Color.BLUE);
    carExtender.setLargeIcon(
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
    NotificationCompat.CarExtender.UnreadConversation unreadConversation =
        new NotificationCompat.CarExtender.UnreadConversation.Builder("11").addMessage("nihao")
            .setLatestTimestamp(System.currentTimeMillis() - 10 * 1000)
            .setReadPendingIntent(pendingIntent)
            .build();
    carExtender.setUnreadConversation(unreadConversation);
    builder.extend(carExtender);
    // 扩展器好像需要Compat发送通知*
    //notificationManagerCompat.notify("carExtender", 1, builder.build());

    // e.自动取消，会触发DeleteIntent，需要与setContentIntent(pendingIntent)配合使用*
    builder.setAutoCancel(true);
    builder.setContentIntent(pendingIntent);
    // TODO 触发的DeleteIntent是一个广播PendingIntent？有待验证
    //builder.setDeleteIntent(pendingIntent);

    // f.通知标志图标类型
    // BADGE_ICON_LARGE 惊喜！右边出现大图标
    //builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
    //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
    // BADGE_ICON_SMALL 小图标，看不出有什么变化
    //builder.setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);
    // 小图标是必须传入的参数，无需重复传入
    //builder.setSmallIcon(R.mipmap.ic_launcher);
    // BADGE_ICON_NONE 默认值
    builder.setBadgeIconType(NotificationCompat.BADGE_ICON_NONE);
    // 据说setBadgeIconType需要与Number一起使用，但已然木有效果，暂且略过
    builder.setNumber(1);

    // g.通知类别
    // TODO 与通知通道有什么区别？还是说这里应用的是，系统预定义的通知类别？而通知通道是自定义的类别？*
    // 闹钟或计时器
    //builder.setCategory(NotificationCompat.CATEGORY_ALARM);
    // 来电语音、视频，或类似的同步通信
    //builder.setCategory(NotificationCompat.CATEGORY_CALL);
    // 异步的大部分信息，如电子邮件
    //builder.setCategory(NotificationCompat.CATEGORY_EMAIL);
    // 后台操作或身份验证状态的错误
    //builder.setCategory(NotificationCompat.CATEGORY_ERROR);
    // 日历事件
    //builder.setCategory(NotificationCompat.CATEGORY_EVENT);
    // 传入的消息，SMS、即时消息
    //builder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
    // 长时间运行的后台任务
    //builder.setCategory(NotificationCompat.CATEGORY_PROGRESS);
    // 促销或广告
    //builder.setCategory(NotificationCompat.CATEGORY_PROMO);
    // 推荐
    //builder.setCategory(NotificationCompat.CATEGORY_RECOMMENDATION);
    // 日程提醒
    //builder.setCategory(NotificationCompat.CATEGORY_REMINDER);
    // 后台运行服务的指示
    //builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
    // 社交网络或共享更新
    //builder.setCategory(NotificationCompat.CATEGORY_SOCIAL);
    // 关于设备或上下文状态的持续信息
    //builder.setCategory(NotificationCompat.CATEGORY_STATUS);
    // 系统或设备状态更新。预留给系统使用
    //builder.setCategory(NotificationCompat.CATEGORY_SYSTEM);
    // 媒体传输控制播放
    //builder.setCategory(NotificationCompat.CATEGORY_TRANSPORT);

    // h.通道id，在Oreo之前的系统中，set没有任何意义
    builder.setChannelId("username1" + 1);

    // i.颜色，会使抽屉式通知栏中的小图标、应用名以及Action按钮颜色都变为设置的值
    //builder.setColor(Color.YELLOW);

    // j.设置通知是否可被着色。Oreo版本的方法，但似乎并不买账
    //builder.setColorized(true);

    // k.设置自定义的远程视图。暂时不探索这个
    //builder.setContent(new RemoteViews())

    // l.将大文本添加在右侧。和setNumber一样没有效果啊
    builder.setContentInfo("1111");

    // m.跳过：setContentIntent/setContentText/setContentTitle/setCustomBigContentView
    // /setCustomContentView/setCustomHeadsUpContentView

    // n.设置将使用的默认通知选项。基本上没看到效果，待验证
    //builder.setDefaults(Notification.DEFAULT_ALL);
    //builder.setDefaults(Notification.DEFAULT_LIGHTS);
    //builder.setDefaults(Notification.DEFAULT_SOUND);
    //builder.setDefaults(Notification.DEFAULT_VIBRATE);

    // setDeleteIntent在e.中有说明
    //builder.setDeleteIntent();
    // setExtras与b.一致，但这里是替换，b.是追加
    //builder.setExtras()

    // o.用于发布而不是将通知发送到通知栏的意图。Action的文字变黑，并且在中间。但并没有出现横幅式通知。
    //builder.setFullScreenIntent(pendingIntent, true);

    // p.将通知设为通知组的一部分。暂时不明白用途，可能是摘要+组合式的通知吧。
    builder.setGroup("11");

    // q.组警报行为
    //builder.setGroupAlertBehavior()

    // r.此通知为一组通知的组摘要
    //builder.setGroupSummary()

    // s.TODO 其他的方法，有待后续实验，这里将暂停一段落。

    /*3.通知操作，已经在2a.中实现*/

    /*4.通知优先级*/
    //builder.setPriority(NotificationCompat.PRIORITY_MAX);

    /*5.创建简单通知。从指南中搬过来的*/
    /*Intent resultIntent = new Intent(this, GuideNotifyActivity.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
    stackBuilder.addParentStack(GuideNotifyActivity.class);
    stackBuilder.addNextIntent(resultIntent);
    PendingIntent resultPendingIntent =
        stackBuilder.getPendingIntent(
            0,
            PendingIntent.FLAG_UPDATE_CURRENT
        );
    builder.setContentIntent(resultPendingIntent);*/

    /*6.将扩展布局应用于通知。替换了标题、内容，变成一个Box，显示多条集合通知*/
    NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
    String[] events = {
        "111",
        "222",
        "333",
        "444",
        "555"
    };
    inboxStyle.setBigContentTitle("Event tracker details:");
    for (int i = 0; i < events.length; i++) {
      inboxStyle.addLine(events[i]);
    }
    builder.setStyle(inboxStyle);

    /*7.处理兼容性。TODO 这部分内容留待以后探索*/

    // 延时发通知
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
      @Override public void run() {
        notificationManager.notify("notify", 1, builder.build());
      }
    }, 1000);
  }

  public void manager(View view) {
    // 这部分其实是在说，如果通知源相同，比如聊天消息，或新闻、广告推送，这些是属于同一源的通知，
    // 尽管可能是不同的聊天对象，以及不同新闻、广告类型，但不应该呈现多条通知，以免像垃圾一样堆占空间。
    // 所以一个良好的管理通知指南，在这里出现了。

    /*1.更新通知：同类型的通知，应当具有更新能力，如果它还没有被消除的话*/


  }
}
