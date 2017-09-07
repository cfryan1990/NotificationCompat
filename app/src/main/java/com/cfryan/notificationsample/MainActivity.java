package com.cfryan.notificationsample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NotificationManager notificationManager;

    private RemoteViews remoteViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        remoteViews = new RemoteViews(getPackageName(), R.layout.view_notification);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        remoteViews.setOnClickPendingIntent(R.id.tv_test1, pendingIntent);
        setEvent();


    }

    private void setEvent() {
        findViewById(R.id.btn_send).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_progress).setOnClickListener(this);
        findViewById(R.id.btn_custom).setOnClickListener(this);
        findViewById(R.id.btn_style).setOnClickListener(this);
    }

    /**
     * Post a notification to be shown in the status bar. If a notification with
     * the same id has already been posted by your application and has not yet been canceled, it
     * will be replaced by the updated information.
     *
     * @param id
     */
    private void sendNotification(int id) {
        //直接跳转
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Intent intent2 = new Intent(this, Main2Activity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Intent intent3 = new Intent(this, Main3Activity.class);
        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent2 = PendingIntent.getActivities(this, 0, new Intent[]{intent2, intent3}, PendingIntent.FLAG_UPDATE_CURRENT);

        //包含parent的跳转，返回键可以返回上一级activity，api>=16,parentActivity才生效
        Intent intent1 = new Intent(this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this)
                //Add the activity parent chain as specified by manifest &lt;meta-data&gt; elements to the task stack builder.
                .addParentStack(MainActivity.class)
                .addNextIntent(intent1);
        PendingIntent pendingIntent1 = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(bitmap)
                .setContentText("content")
                .setContentTitle("title")

                //首次弹出的提示，部分机型无效
                .setTicker("hahahahahahah")

                //显示位置 right-hand side of the notification.
                //setContentInfo 在 api 24 被废弃，不再显示! 用 setSubText 代替
                .setContentInfo("contentinfo")

                //显示在第三行的内容，会影响同样使用第三行空间的progress
                //N 之后会显示在通知头部，不再影响progress（然而国内厂商依然第三行）
                .setSubText("subtext")
                //时间显示在N以下默认显示，N开始需要设置setShowWhen
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
//                .setCustomBigContentView(remoteViews)
                .setContent(remoteViews)
//
                //VISIBILITY_PUBLIC 显示通知的完整内容。
                //        VISIBILITY_SECRET 不会在锁定屏幕上显示此通知的任何部分。
                //        VISIBILITY_PRIVATE 显示通知图标和内容标题等基本信息，但是隐藏通知的完整内容。
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                //设置 VISIBILITY_PRIVATE 后，还可以通过 setPublicVersion() 提供其中隐藏了某些详细信息的替换版本通知内容。
                .setPublicVersion(new Notification(R.mipmap.ic_launcher, "喵喵喵喵", System.currentTimeMillis()))

                //4.4 以后通知栏消息不管是普通样式还是Style样式，都支持两个按钮同时出现在一条通知栏消息的底部，
                // 通过这两个按钮，可以自定义一系列动作，包括回复信息和邮件，点赞等。
                // 国内大多不支持
//                .addAction(R.mipmap.ic_launcher, "action1", pendingIntent)
//                .addAction(R.mipmap.ic_launcher, "action2", pendingIntent)
//                .addAction(R.mipmap.ic_launcher, "action3", pendingIntent)

                .setCategory(NotificationCompat.CATEGORY_EVENT)

                //同一个group的通知只显示setGroupSummary的true的那条
//                .setGroup("key")
//                .setGroupSummary(true)
//                .setSortKey("")

//                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setVibrate(new long[]{0, 300, 500, 700})
//                .setContentIntent(pendingIntent2)

                //必须设置了contentintent才有效果
//                .setAutoCancel(true)
                //.setDeleteIntent(pendingIntent)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
//                .setFullScreenIntent(pendingIntent,true)

//                .setNumber(10)
//                .setUsesChronometer(true)

                .setStyle(new NotificationCompat.BigPictureStyle().bigLargeIcon(bitmap));

        Notification notification = builder.build();
        // 持续提醒直到用户响应
//        notification.flags |= Notification.FLAG_INSISTENT;

        notificationManager.notify(id, notification);

        NotificationCompat.Builder builder1 = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentText("第二条")
                .setContentTitle("第二条标题");
//                .setGroup("key")
//                .setGroupSummary(true);
        notificationManager.notify(20000, builder1.build());

//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//        Intent alarmIntent = new Intent("com.android.alarm");
//        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60000, alarmPendingIntent);


    }


    private void sendNormalNotification() {

    }

    private void cancelAllNotification() {
        notificationManager.cancelAll();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_send:

                Intent intent = new Intent(this, MainActivity.class);
                //PendingIntent有以下flag：
//                FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的PendingIntent对象，那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象。
//                FLAG_NO_CREATE:如果当前系统中不存在相同的PendingIntent对象，系统将不会创建该PendingIntent对象而是直接返回null。
//                FLAG_ONE_SHOT:该PendingIntent只作用一次。
//                FLAG_UPDATE_CURRENT:如果系统中已存在该PendingIntent对象，那么系统将保留该PendingIntent对象，但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据，例如更新Intent中的Extras。
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

//                try {
//                    pendingIntent.send();
//                } catch (PendingIntent.CanceledException e) {
//                    e.printStackTrace();
//                }


                NotificationCompat.Builder builder = NotificationHelper.from(this)
                        .createProgress(new DefaultConfig(), "消息标题", "消息正文")
                        .setCustomEffect(null, new long[]{0, 100, 300, 1000, 500, 2000}, false)
                        .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "title1", pendingIntent))
                        .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "title2", pendingIntent))
                        .addAction(new NotificationCompat.Action(R.mipmap.ic_launcher, "title3", pendingIntent))
                        .getBuilder();


                NotificationHelper.notify(this, 1000, builder.build());

//                sendNotification(10000);

//                findViewById(R.id.btn_send).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        remoteViews.setTextViewText(R.id.tv_test1, "变化变化");
//                        sendNotification(1);
//                    }
//                }, 5000);

                break;
            case R.id.btn_cancel:
                cancelAllNotification();

                Intent intent2 = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0, intent2, 0);
                NotificationCompat.Builder builderHeadsUp = NotificationHelper.from(this)
                        .createCustomHeadsUpView(new DefaultConfig(), new RemoteViews(getPackageName(), R.layout.view_notification_big))
                        .setEffectAllDefaults()
                        .getBuilder();
                //5.0之后支持顶部悬浮窗显示，开启条件（二选一）：
                // 1、priority是high或者max
                // 2、setFullScreenIntent，且不为空
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setFullScreenIntent(pendingIntent2, false);
                NotificationHelper.notify(this, 2000, builderHeadsUp.build());

                break;

            case R.id.btn_progress:
                //模拟进度条
                final NotificationCompat.Builder progressBuilder = NotificationHelper.from(this)
                        .createProgress(new DefaultConfig(), "Download Title", "Download in Progress").getBuilder();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <= 100; i++) {
                            NotificationHelper.updateProgress(MainActivity.this, 0, 100, i, progressBuilder);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {

                            }
                        }

                        NotificationHelper.completeProgress(MainActivity.this, 0, progressBuilder, "complete");
                    }
                }).start();

                break;

            case R.id.btn_custom:
                NotificationHelper.from(this)
                        .createCustomNotification(new DefaultConfig(), new RemoteViews(getPackageName(),
                                R.layout.view_notification), new RemoteViews(getPackageName(), R.layout.view_notification_big))
                        .setEffectAllDefaults()
                        .sendNotify(3000);
                break;

            case R.id.btn_style:


                NotificationHelper.from(this)
                        .createDefault(new DefaultConfig(), "title", "main content", "subtext")
                        .setMessageStyle("username", "title",
                                new NotificationCompat.MessagingStyle.Message[]
                                        {
                                                new NotificationCompat.MessagingStyle.Message("A", System.currentTimeMillis(), "sender1"),
                                                new NotificationCompat.MessagingStyle.Message("B", System.currentTimeMillis(), "sender2"),
                                                new NotificationCompat.MessagingStyle.Message("C", System.currentTimeMillis(), "sender3")
                                        })
                        .setEffectAllDefaults()
                        .sendNotify(5000);

                NotificationHelper.from(this)
                        .createDefault(new DefaultConfig(), "title", "main content", "subtext")
                        .setBigTextStyle("bigtitle", "bigContentbigContentbigContentbigContentbigContentbigContentbigContentbigCont" +
                                "entbigContentbigContentbigContentbigContentbigContentbigContentbigContentbigContentbigConten" +
                                "tbigContentbigContentbigContentbigContentbigContentbigContentbigContentbigContentbigContent" +
                                "bigContentbigContentbigContentbigContentbigContent", "summary")
                        .setEffectAllDefaults()
                        .sendNotify(4000);
                break;
        }
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals("com.android.alarm")) {
                Toast.makeText(context, "need update", Toast.LENGTH_LONG).show();
//                remoteViews.setTextViewText(R.id.tv_test1, "变化变化");
//                sendNotification(1);
            }
        }
    }
}
