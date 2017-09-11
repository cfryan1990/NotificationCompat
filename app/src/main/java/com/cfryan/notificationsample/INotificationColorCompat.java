package com.cfryan.notificationsample;

import android.widget.RemoteViews;

/**
 * 自定义通知view的适配接口
 *
 * @author chenfeng
 * @since 2017-09-11 15:17
 */

public interface INotificationColorCompat {
    RemoteViews getCompatContentView(boolean isDarkBackGround);
    RemoteViews getCompatBigContentView(boolean isDarkBackGround);
}
