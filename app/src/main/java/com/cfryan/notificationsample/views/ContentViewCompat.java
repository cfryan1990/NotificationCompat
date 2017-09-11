package com.cfryan.notificationsample.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;

import com.cfryan.notificationsample.INotificationColorCompat;
import com.cfryan.notificationsample.R;

/**
 * 自定义通知view
 *
 * @author chenfeng
 * @since 2017-09-11 16:00
 */

public class ContentViewCompat implements INotificationColorCompat {

    private RemoteViews mContentView;
    private RemoteViews mBigContentView;
    private Context mContext;

    public ContentViewCompat(Context context) {
        mContext = context;
        mContentView = new RemoteViews(context.getPackageName(), R.layout.view_notification);
        mBigContentView = new RemoteViews(context.getPackageName(), R.layout.view_notification_big);
    }

    @Override
    public RemoteViews getCompatContentView(boolean isDarkBackGround) {
        if (isDarkBackGround) {
            mContentView.setTextColor(R.id.head, Color.WHITE);
        } else {
            mContentView.setTextColor(R.id.head, Color.BLACK);
        }
        return mContentView;
    }

    @Override
    public RemoteViews getCompatBigContentView(boolean isDarkBackGround) {
        if (isDarkBackGround) {
            mBigContentView.setTextColor(R.id.head, Color.WHITE);
            mBigContentView.setTextColor(R.id.bigcontent, Color.WHITE);
        } else {
            mBigContentView.setTextColor(R.id.head, Color.BLACK);
            mBigContentView.setTextColor(R.id.bigcontent, Color.BLACK);
        }
        return mBigContentView;
    }
}
