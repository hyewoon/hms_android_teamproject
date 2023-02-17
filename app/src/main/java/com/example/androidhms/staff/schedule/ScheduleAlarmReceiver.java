package com.example.androidhms.staff.schedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.androidhms.MainActivity;
import com.example.androidhms.R;

public class ScheduleAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        getNotification(context, intent);
    }

    private void getNotification(Context context, Intent intent) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        // PendingIntent 가 알림에 따라 update 되지 않는 오류때문에 cancel 후 한번 더 선언 (임시방편)
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity(context, 0, mainIntent,
                    PendingIntent.FLAG_MUTABLE);
            pendingIntent.cancel();
            pendingIntent = PendingIntent.getActivity(context, 0, mainIntent,
                    PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, mainIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            pendingIntent.cancel();
            pendingIntent = PendingIntent.getActivity(context, 0, mainIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }

        String channelId = "alarm_high_channel";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.icon_alarm)
                        .setContentTitle(intent.getStringExtra("time"))
                        .setContentText(intent.getStringExtra("content"))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel;
            channel = new NotificationChannel("alarm_high_channel",
                    "alarm_high_channel",
                    NotificationManager.IMPORTANCE_HIGH);
            // 진동설정 (작동 안되는 코드)
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{200, 300});
            notificationManager.createNotificationChannel(channel);
        } else {

        }
        notificationManager.notify(0, notificationBuilder.build());
    }


}
