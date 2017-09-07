package com.cfryan.notificationsample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.lang.ref.WeakReference;

/**
 * This is a Utility Class to Manage the Notification Lifecycle
 *
 * @author chenfeng
 * @since 2017-08-30 14:32
 */

public class NotificationHelper {

    private final WeakReference<Context> mContext;

    private NotificationHelper(Context context) {
        mContext = new WeakReference<>(context);
    }

    /**
     * Start Helper from a Activity
     *
     * @param activity
     * @return
     */
    public static NotificationHelper from(Activity activity) {
        return new NotificationHelper(activity);
    }

    /**
     * Start Helper from a Fragment
     *
     * @param fragment
     * @return
     */
    public static NotificationHelper from(Fragment fragment) {
        return new NotificationHelper(fragment.getActivity());
    }

    /**
     * Start Helper from a Service
     *
     * @param service
     * @return
     */
    public static NotificationHelper from(Service service) {
        return new NotificationHelper(service);
    }

    /**
     * 构造默认builder，包含基本配置
     *
     * @param config
     * @param title
     * @param contentText
     * @param subText
     * @return
     */
    public NotificationEffect createDefault(BaseConfig config, CharSequence title, CharSequence contentText,
                                            CharSequence subText) {
        return createDefault(config, title, contentText, subText, null, null);
    }

    /**
     * 构造默认builder，包含基本配置
     *
     * @param config      基本配置
     * @param title       消息标题（第一行）
     * @param contentText 消息正文（第二行）
     * @param subText     显示在第三行的消息附加内容，会影响同样使用第三行空间的progress，N 之后会显示在通知头部，不再影响progress（然而国内厂商依然）
     * @param contentInfo 显示在 right-hand side of the notification，当API>=24时不再显示，建议用subtext
     * @param ticker      首次弹出的状态栏消息提示，部分机型失效
     * @return
     */
    public NotificationEffect createDefault(BaseConfig config, CharSequence title, CharSequence contentText,
                                            CharSequence subText, CharSequence ticker, CharSequence contentInfo) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setContentTitle(title)
                .setContentText(contentText)
                .setSubText(subText)
                .setTicker(ticker);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            baseBuilder.setContentInfo(contentInfo);
        }
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    /**
     * 进度条构造器,不明确进度条
     *
     * @param config
     * @param title
     * @param contentText
     * @return
     */
    public NotificationEffect createProgress(BaseConfig config, CharSequence title, CharSequence contentText) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setContentTitle(title)
                .setContentText(contentText)
                .setProgress(0, 0, true);
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    /**
     * 进度条构造器,明确进度条
     *
     * @param config
     * @param title
     * @param contentText
     * @param progress
     * @return
     */
    public NotificationEffect createProgress(BaseConfig config, CharSequence title, CharSequence contentText,
                                             int progress) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setContentTitle(title)
                .setContentText(contentText)
                .setProgress(config.getProgressMax(), progress, false);
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    /**
     * 更新进度条
     *
     * @param context
     * @param notifyId
     * @param maxProgress
     * @param currentProgress
     * @param builder
     */
    public static void updateProgress(Context context, int notifyId, int maxProgress, int currentProgress, NotificationCompat.Builder builder) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder.setProgress(maxProgress, currentProgress, false);
        notificationManager.notify(notifyId, builder.build());
    }

    /**
     * 通知自定义view构造器
     *
     * @param config
     * @param contentRemoteViews
     * @return
     */
    public NotificationEffect createCustomNotification(BaseConfig config, RemoteViews contentRemoteViews) {
        return createCustomNotification(config, contentRemoteViews, null);
    }

    /**
     * 通知自定义view构造器(包含扩展)
     *
     * @param config
     * @param contentRemoteViews
     * @param bigContentRemoteViews
     * @return
     */
    public NotificationEffect createCustomNotification(BaseConfig config, RemoteViews contentRemoteViews, RemoteViews bigContentRemoteViews) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setCustomContentView(contentRemoteViews)
                .setCustomBigContentView(bigContentRemoteViews);
        return new NotificationEffect(mContext.get(), baseBuilder, contentRemoteViews, bigContentRemoteViews);
    }

    /**
     * When the operation is done, call setProgress(0, 0, false) and then update the notification to remove the activity indicator.
     * Always do this; otherwise, the animation will run even when the operation is complete.
     * Also remember to change the notification text to indicate that the operation is complete.
     *
     * @param builder
     */
    public static void completeProgress(Context context, int notifyId, NotificationCompat.Builder builder, CharSequence contentText) {
        notify(context, notifyId, builder.setContentText(contentText).setProgress(0, 0, false).build());
    }

    /**
     * 无效
     *
     * @param config
     * @param remoteViews
     * @return
     */
    @Deprecated
    public NotificationEffect createCustomHeadsUpView(BaseConfig config, RemoteViews remoteViews) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setCustomHeadsUpContentView(remoteViews);
        return new NotificationEffect(mContext.get(), baseBuilder, remoteViews, null);
    }


    public NotificationEffect createMessageStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.MessagingStyle(""));
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    public NotificationEffect createBigPictureStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.BigPictureStyle());
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    public NotificationEffect createInboxStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.InboxStyle());
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    public NotificationEffect createBigTextStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.BigTextStyle());
        return new NotificationEffect(mContext.get(), baseBuilder);
    }

    /**
     * Apply an extender to this notification builder. Extenders may be used to add
     * metadata or change options on this builder.
     *
     * @param origin
     * @param extend
     * @return
     */
    public static NotificationCompat.Builder extendNotification(@NonNull NotificationCompat.Builder origin,
                                                                @NonNull NotificationCompat.Extender extend) {
        return origin.extend(extend);
    }


    /**
     * @param context
     * @param notifyId
     * @param notification
     */
    public static void notify(Context context, int notifyId, Notification notification) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId, notification);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            notificationManager.getActiveNotifications()[0].getId();
        }

        //消息勿扰权限的申请
//        notificationManager.isNotificationPolicyAccessGranted();
//        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//        context.startActivity(intent);

        //消息读取权限
//        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
//        context.startActivity(intent);

    }

    /**
     * @param context
     * @param notifyId
     */
    public static void cancelNotify(Context context, int notifyId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(notifyId);
    }

    /**
     * @param context
     */
    public static void cancelAllNotify(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

    }
}
