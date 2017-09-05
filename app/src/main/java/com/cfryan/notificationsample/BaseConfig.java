package com.cfryan.notificationsample;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;

/**
 * 通知系统默认样式下的配置
 *
 * @author chenfeng
 * @since 2017-09-01 14:16
 */

public abstract class BaseConfig {
    /**
     * 默认小图标
     *
     * @return
     */
    public abstract int getSmallIcon();

    public abstract Bitmap getLargeIcon(Context context);

    public int getPriority() {
        return NotificationCompat.PRIORITY_DEFAULT;
    }

    /**
     * 最大进度条数值
     * @return
     */
    public int getProgressMax() {
        return 100;
    }

    /**
     * 时间显示在N以下默认显示，N开始需要设置setShowWhen
     *
     * @return
     */
    public boolean getShowWhen() {
        return true;
    }

    /**
     * 默认显示当前时间
     *
     * @return
     */
    public long getWhen() {
        return System.currentTimeMillis();
    }

    /**
     * VISIBILITY_PUBLIC 显示通知的完整内容。
     * VISIBILITY_SECRET 不会在锁定屏幕上显示此通知的任何部分。
     * VISIBILITY_PRIVATE 显示通知图标和内容标题等基本信息，但是隐藏通知的完整内容。
     *
     * @return
     */
    public @NotificationCompat.NotificationVisibility int getNotificationVisibility() {
        return NotificationCompat.VISIBILITY_PUBLIC;
    }

    /**
     * 设置 VISIBILITY_PRIVATE 后，还可以通过 setPublicVersion() 提供其中隐藏了某些详细信息的替换版本通知内容。
     *
     * @return
     */
    public Notification getPublicVersion() {
        return null;
    }

    /**
     * <p>Must be one of the predefined notification categories (see the <code>CATEGORY_*</code>
     * constants in {@link Notification}) that best describes this notification.
     * May be used by the system for ranking and filtering.
     *
     * @return
     */
    public String getNotificationCategory() {
        return null;
    }

    /**
     * 点击自动显示，必须设置了contentIntent才有效果
     * @return
     */
    public boolean getAutoCancel() {
        return true;
    }

    /**
     * Set whether this is an ongoing notification.
     *
     * <p>Ongoing notifications differ from regular notifications in the following ways:
     * <ul>
     *   <li>Ongoing notifications are sorted above the regular notifications in the
     *   notification panel.</li>
     *   <li>Ongoing notifications do not have an 'X' close button, and are not affected
     *   by the "Clear all" button.
     * </ul>
     * @return
     */
    public abstract boolean getOngoing();

    /**
     *
     * @return
     */
    public boolean getOnlyAlertOnce() {
        return true;
    }

    /**
     * Show the {@link Notification#when} field as a stopwatch(count up).
     *
     * Instead of presenting <code>when</code> as a timestamp, the notification will show an
     * automatically updating display of the minutes and seconds since <code>when</code>.
     *
     * Useful when showing an elapsed time (like an ongoing phone call).
     * @return
     */
    public boolean getUsesChronometer() {
        return false;
    }

    public NotificationCompat.Builder getBaseBuilder(Context context) {
        return new NotificationCompat.Builder(context)
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon(context))
                .setShowWhen(getShowWhen())
                .setWhen(getWhen())
                .setPriority(getPriority())
                .setVisibility(getNotificationVisibility())
                .setPublicVersion(getPublicVersion())
                .setAutoCancel(getAutoCancel())
                .setOngoing(getOngoing())
                .setOnlyAlertOnce(getOnlyAlertOnce());
    }

}
