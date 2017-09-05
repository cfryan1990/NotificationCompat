package com.cfryan.notificationsample;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
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
        return new NotificationEffect(baseBuilder);
    }

    /**
     * 进度条构造器,明确的进度条显示
     *
     * @param config
     * @param title
     * @param contentText
     * @return
     */
    public NotificationEffect createProgress(BaseConfig config, CharSequence title, CharSequence contentText,
                                             int progress) {
        return createProgress(config, title, contentText, progress, false);
    }

    /**
     * 进度条构造器
     *
     * @param config
     * @param title
     * @param contentText
     * @param progress
     * @param indeterminate
     * @return
     */
    public NotificationEffect createProgress(BaseConfig config, CharSequence title, CharSequence contentText,
                                             int progress, boolean indeterminate) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setContentTitle(title)
                .setContentText(contentText)
                .setProgress(config.getProgressMax(), progress, indeterminate);
        return new NotificationEffect(baseBuilder);
    }

    /**
     * 通知自定义view构造器
     *
     * @param config
     * @param remoteViews
     * @return
     */
    public NotificationEffect createCustomNotification(BaseConfig config, RemoteViews remoteViews) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setCustomContentView(remoteViews);
        return new NotificationEffect(baseBuilder, remoteViews);
    }

    /**
     * 无效
     * @param config
     * @param remoteViews
     * @return
     */
    @Deprecated
    public NotificationEffect createCustomHeadsUpView(BaseConfig config, RemoteViews remoteViews) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setCustomHeadsUpContentView(remoteViews);
        return new NotificationEffect(baseBuilder, remoteViews);
    }


    public NotificationEffect createMessageStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.MessagingStyle(""));
        return new NotificationEffect(baseBuilder);
    }

    public NotificationEffect createBigPictureStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.BigPictureStyle());
        return new NotificationEffect(baseBuilder);
    }

    public NotificationEffect createInboxStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.InboxStyle());
        return new NotificationEffect(baseBuilder);
    }

    public NotificationEffect createBigTextStyleNotification(BaseConfig config) {
        NotificationCompat.Builder baseBuilder = config.getBaseBuilder(mContext.get());
        baseBuilder.setStyle(new NotificationCompat.BigTextStyle());
        return new NotificationEffect(baseBuilder);
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
