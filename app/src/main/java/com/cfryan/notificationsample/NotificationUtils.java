package com.cfryan.notificationsample;

import android.app.Notification;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Utils of Build Notification
 *
 * @author chenfeng
 * @since 2017-09-11 11:01
 */

public class NotificationUtils {
    //相似阈值
    private static final double COLOR_THRESHOLD = 180.0;
    private static final String NOTIFICATION_TITLE = "notification_title";
    public static final int INVALID_COLOR = -1; //无效颜色
    private static int mNotificationTitleColor = INVALID_COLOR; //获取到颜色缓存

    /**
     * 获取系统通知栏主标题颜色，适配Activity继承自AppCompatActivity的情况
     *
     * @param context
     * @return The Color of System Notification Title
     */
    public static int getNotificationColor(Context context) {
        try {
            if (mNotificationTitleColor == INVALID_COLOR) {
                if (context instanceof AppCompatActivity) {
                    mNotificationTitleColor = getNotificationColorCompat(context);
                } else {
                    mNotificationTitleColor = getNotificationColorInternal(context);
                }
            }

        } catch (Exception ignored) {
        }
        return mNotificationTitleColor;
    }

    /**
     * 通过一个空的Notification拿到Notification.contentView,通过{@link android.widget.RemoteViews#apply(Context, ViewGroup)}
     * 方法返回通知栏消息根布局实例
     *
     * @param context
     * @return The Color of System Notification Title
     */
    private static int getNotificationColorInternal(Context context) {

        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle(NOTIFICATION_TITLE);
            notification = builder.build();
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(NOTIFICATION_TITLE);
            notification = builder.build();
        }

        try {
            RemoteViews contentView = notification.contentView;
            if (contentView == null) return INVALID_COLOR;
            ViewGroup root = (ViewGroup) notification.contentView.apply(context, new FrameLayout(context));
            TextView titleView = (TextView) root.findViewById(android.R.id.title);
            if (titleView == null) {
                iteratorView(root, new Filter() {
                    @Override
                    public void filter(View view) {
                        if (view instanceof TextView) {
                            TextView textView = (TextView) view;
                            if (NOTIFICATION_TITLE.equals(textView.getText().toString())) {
                                mNotificationTitleColor = textView.getCurrentTextColor();
                            }
                        }
                    }
                });
                return mNotificationTitleColor;
            } else {
                return titleView.getCurrentTextColor();
            }
        } catch (Exception e) {
            return getNotificationColorCompat(context);
        }
    }

    /**
     * 使用{@link NotificationUtils#getNotificationColorInternal(Context)}方法，Activity不能继承自AppCompatActivity()
     * (5.0以下机型可以，5.0及以上机型不行)，因为通知布局中的imageview替换成了AppCompatImageView,而在5.0以上中,AppCompatImageView
     * 的setBackgroundResource(int)未被标记为RemotableViewMethod,导致apply时抛异常
     *
     * @param context
     * @return The Color of System Notification Title
     */
    private static int getNotificationColorCompat(Context context) {
        try {
            Notification notification;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                Notification.Builder builder = new Notification.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(NOTIFICATION_TITLE);
                notification = builder.build();
            } else {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(NOTIFICATION_TITLE);
                notification = builder.build();
            }
            int layoutId = notification.contentView.getLayoutId();
            ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(layoutId, null);
            TextView titleView = (TextView) root.findViewById(android.R.id.title);
            if (titleView == null) {
                return getTitleColorInternalCompat(root);
            } else {
                return titleView.getCurrentTextColor();
            }
        } catch (Exception e) {
        }

        return INVALID_COLOR;
    }

    private static void iteratorView(View view, Filter filter) {
        if (view == null || filter == null) {
            return;
        }
        filter.filter(view);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                iteratorView(child, filter);
            }
        }
    }

    /**
     * 实在不行的情况下，只能寻找到字号最大的textView，把它定为title获取颜色
     *
     * @param view
     * @return
     */
    private static int getTitleColorInternalCompat(View view) {
        if (view == null) {
            return INVALID_COLOR;
        }
        List<TextView> textViews = getAllTextViews(view);
        int maxTextSizeIndex = findMaxTextSizeIndex(textViews);
        if (maxTextSizeIndex != Integer.MIN_VALUE) {
            return textViews.get(maxTextSizeIndex).getCurrentTextColor();
        }
        return INVALID_COLOR;
    }

    /**
     * 找到字号最大的textView
     *
     * @param textViews
     * @return
     */
    private static int findMaxTextSizeIndex(List<TextView> textViews) {
        float max = Integer.MIN_VALUE;
        int maxIndex = Integer.MIN_VALUE;
        int index = 0;
        for (TextView textView : textViews) {
            if (max < textView.getTextSize()) {
                max = textView.getTextSize();
                maxIndex = index;
            }
            index++;
        }
        return maxIndex;
    }

    private static List<TextView> getAllTextViews(View root) {
        final List<TextView> textViews = new ArrayList<>();
        iteratorView(root, new Filter() {
            @Override
            public void filter(View view) {
                if (view instanceof TextView) {
                    textViews.add((TextView) view);
                }
            }
        });
        return textViews;
    }

    public static boolean isColorSimilar(int baseColor, int color) {
        int simpleBaseColor = baseColor | 0xff000000;
        int simpleColor = color | 0xff000000;
        int baseRed = Color.red(simpleBaseColor) - Color.red(simpleColor);
        int baseGreen = Color.green(simpleBaseColor) - Color.green(simpleColor);
        int baseBlue = Color.blue(simpleBaseColor) - Color.blue(simpleColor);
        double value = Math.sqrt(baseRed * baseRed + baseGreen * baseGreen + baseBlue * baseBlue);
        if (value < COLOR_THRESHOLD) {
            return true;
        }
        return false;
    }

    private interface Filter {
        void filter(View view);
    }

}
