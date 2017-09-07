package com.cfryan.notificationsample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Event Class of Notification
 *
 * @author chenfeng
 * @since 2017-08-31 14:18
 */

public class NotificationEvent {
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private RemoteViews mContentRemoteViews;
    private RemoteViews mBigContentRemotesViews;

    public NotificationEvent(Context context, NotificationCompat.Builder builder, RemoteViews contentRemoteViews, RemoteViews bigContentRemoteViews) {
        this.mContext = context;
        this.mBuilder = builder;
        this.mContentRemoteViews = contentRemoteViews;
        this.mBigContentRemotesViews = bigContentRemoteViews;
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    /**
     * @param notifyId
     */
    public void sendNotify(int notifyId) {
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId, mBuilder.build());
    }

    /**
     * bind event
     *
     * @param pendingIntent
     * @return
     */
    public NotificationEvent setContentIntent(PendingIntent pendingIntent) {
        mBuilder.setContentIntent(pendingIntent);
        return this;
    }

    /**
     * 4.1(api 16) 以后通知栏消息不管是普通样式还是Style样式，都支持两个按钮同时出现在一条通知栏消息的底部，
     * 通过这两个按钮，可以自定义一系列动作，包括回复信息和邮件，点赞等。
     * 国内大多不支持
     *
     * @param action
     * @return
     */
    public NotificationEvent addAction(NotificationCompat.Action action) {
        mBuilder.addAction(action);
        return this;
    }

    /**
     * 给contentRemoteViews中的view绑定事件
     *
     * @param viewId
     * @param pendingIntent
     * @return
     */
    public NotificationEvent setContentIntent(@IdRes int viewId, PendingIntent pendingIntent) {
        if (mContentRemoteViews == null)
            throw new RuntimeException("mRemotesViews must be no-null");
        mContentRemoteViews.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    public NotificationEvent setBigContentIntent(@IdRes int viewId, PendingIntent pendingIntent) {
        if (mBigContentRemotesViews == null)
            throw new RuntimeException("mBigContentRemotesViews must be no-null");
        mBigContentRemotesViews.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    /**
     * 设置清除通知时的事件
     *
     * @param pendingIntent
     * @return
     */
    public NotificationEvent setDeleteIntent(PendingIntent pendingIntent) {
        mBuilder.setDeleteIntent(pendingIntent);
        return this;
    }
}
