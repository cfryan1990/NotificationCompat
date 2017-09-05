package com.cfryan.notificationsample;

import android.app.PendingIntent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Event Class of Notification
 *
 * @author chenfeng
 * @since 2017-08-31 14:18
 */

public class NotificationEvent {

    private NotificationCompat.Builder mBuilder;
    private RemoteViews mRemoteViews;

    public NotificationEvent(NotificationCompat.Builder builder, RemoteViews remoteViews) {
        this.mBuilder = builder;
        this.mRemoteViews = remoteViews;
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    /**
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
     * @param action
     * @return
     */
    public NotificationEvent addAction(NotificationCompat.Action action) {
        mBuilder.addAction(action);
        return this;
    }

    /**
     * 给remoteViews中的view绑定事件
     * @param viewId
     * @param pendingIntent
     * @return
     */
    public NotificationEvent setContentIntent(@IdRes int viewId, PendingIntent pendingIntent) {
        if (mRemoteViews == null) throw new RuntimeException("mRemotesViews must be no-null");
        mRemoteViews.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    /**
     * 设置清除通知时的事件
     * @param pendingIntent
     * @return
     */
    public NotificationEvent setDeleteIntent(PendingIntent pendingIntent) {
        mBuilder.setDeleteIntent(pendingIntent);
        return this;
    }
}
