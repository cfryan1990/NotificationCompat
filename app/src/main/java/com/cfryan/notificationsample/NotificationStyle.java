package com.cfryan.notificationsample;

import android.content.Context;
import android.graphics.Bitmap;
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
     * api>=16  after 4.1
     *
     * @return
     */
    public NotificationEffect setBigPictureStyle(CharSequence title, CharSequence summary, Bitmap largeIcon, Bitmap bigPic) {
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .setBigContentTitle(title)
                .setSummaryText(summary)
                .bigLargeIcon(largeIcon)
                .bigPicture(bigPic));
        return new NotificationEffect(mContext, mBuilder);
    }

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
