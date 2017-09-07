package com.cfryan.notificationsample;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Effect class of Notification
 *
 * @author chenfeng
 * @since 2017-09-04 15:11
 */

public class NotificationEffect {
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private RemoteViews mContentRemoteViews;
    private RemoteViews mBigContentRemotesViews;

    public NotificationEffect(Context context, @NonNull NotificationCompat.Builder builder) {
        this.mContext = context;
        this.mBuilder = builder;
    }

    public NotificationEffect(Context context, @NonNull NotificationCompat.Builder builder, @NonNull RemoteViews remoteViews, @Nullable RemoteViews bigContentRemotesViews) {
        this.mContext = context;
        this.mBuilder = builder;
        this.mContentRemoteViews = remoteViews;
        this.mBigContentRemotesViews = bigContentRemotesViews;
    }

    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    /**
     * Set the default notification options that will be used.
     * The value should be one or more of the following fields combined with
     * bitwise-or:
     * {@link Notification#DEFAULT_SOUND}, {@link Notification#DEFAULT_VIBRATE},
     * {@link Notification#DEFAULT_LIGHTS}.
     *
     * @return
     */
    public NotificationEvent setEffectAllDefaults() {
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);
        return new NotificationEvent(mContext, mBuilder, mContentRemoteViews, mBigContentRemotesViews);
    }

    /**
     * 设置自定义音效和震动
     *
     * @param soundUri 声音文件uri
     * @param pattern  奇数位是延迟时间，后面跟的偶数是震动时间
     */
    public NotificationEvent setCustomEffect(@Nullable Uri soundUri, @Nullable long[] pattern, boolean defaultFill) {
        if (defaultFill) {
            if (soundUri == null) mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
            if (pattern == null) mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        }
        mBuilder.setSound(soundUri);
//        mBuilder.setSound(soundUri,AudioManager.STREAM_NOTIFICATION);
        mBuilder.setVibrate(pattern);
        return new NotificationEvent(mContext, mBuilder, mContentRemoteViews, mBigContentRemotesViews);

    }

    /**
     * @param argb
     * @param onMs
     * @param offMs
     * @return
     */
    public NotificationEffect setLights(@ColorInt int argb, int onMs, int offMs) {
        mBuilder.setLights(argb, onMs, offMs);
        return this;
    }

}
