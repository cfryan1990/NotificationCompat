package com.cfryan.notificationsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * 文件描述
 *
 * @author chenfeng
 * @since 2017-09-01 17:35
 */

public class DefaultConfig extends BaseConfig {

    @Override
    public int getSmallIcon() {
        return R.mipmap.ic_launcher;
    }

    @Override
    public Bitmap getLargeIcon(Context context) {
        return BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
    }

    @Override
    public boolean getOngoing() {
        return false;
    }

    @Override
    public int getPriority() {
        return NotificationCompat.PRIORITY_HIGH;
    }
}
