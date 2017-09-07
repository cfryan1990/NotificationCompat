package com.cfryan.notificationsample;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

/**
 * Notification Style Creator
 *
 * @author chenfeng
 * @since 2017-09-07 14:30
 */

public class NotificationStyle {
    private Context mContext;
    private NotificationCompat.Builder mBuilder;

    public NotificationStyle(Context context, NotificationCompat.Builder builder) {
        mContext = context;
        mBuilder = builder;
    }

    public NotificationEffect setDefaultStyle() {
        return new NotificationEffect(mContext, mBuilder);
    }

    /**
     * API >= 24(N) will work
     *
     * @param userDisplayName
     * @param title
     * @param messages
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public NotificationEffect setMessageStyle(CharSequence userDisplayName, CharSequence title, NotificationCompat.MessagingStyle.Message[] messages) {
        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle(userDisplayName)
                .setConversationTitle(title);
        for (int i = 0; i < messages.length; i++) {
            messagingStyle.addMessage(messages[i]);
        }
        mBuilder.setStyle(messagingStyle);
        return new NotificationEffect(mContext, mBuilder);
    }

    /**
     * API >= 16(JB) will work
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NotificationEffect setBigPictureStyle(CharSequence title, CharSequence summary, Bitmap largeIcon, Bitmap bigPic) {
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .setBigContentTitle(title)
                .setSummaryText(summary)
                .bigLargeIcon(largeIcon)
                .bigPicture(bigPic));
        return new NotificationEffect(mContext, mBuilder);
    }

    /**
     * API >= 16(JB) will work
     *
     * @param title
     * @param summary
     * @param contentLines
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NotificationEffect setInboxStyle(CharSequence title, CharSequence summary, CharSequence[] contentLines) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                .setBigContentTitle(title)
                .setSummaryText(summary);
        for (int i = 0; i < contentLines.length; i++) {
            inboxStyle.addLine(contentLines[i]);
        }
        mBuilder.setStyle(inboxStyle);
        return new NotificationEffect(mContext, mBuilder);
    }

    /**
     * API >= 16(JB) will work
     *
     * @param title
     * @param content
     * @param summary
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NotificationEffect setBigTextStyle(CharSequence title, CharSequence content, CharSequence summary) {
        mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText(content)
                .setBigContentTitle(title)
                .setSummaryText(summary));
        return new NotificationEffect(mContext, mBuilder);
    }

    public NotificationEffect setStyle(NotificationCompat.Style style) {
        mBuilder.setStyle(style);
        return new NotificationEffect(mContext, mBuilder);
    }

}
