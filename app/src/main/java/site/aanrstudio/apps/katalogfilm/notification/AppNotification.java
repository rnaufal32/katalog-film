package site.aanrstudio.apps.katalogfilm.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import site.aanrstudio.apps.katalogfilm.BuildConfig;
import site.aanrstudio.apps.katalogfilm.FavoriteActivity;
import site.aanrstudio.apps.katalogfilm.R;

public class AppNotification {

    private static final int NOTIFICATION_ID = new Random().nextInt();
    private static CharSequence CHANNEL_USER = "reminder_user";
    private static CharSequence CHANNEL_NEW = "reminder_new";
    private Context context;

    public AppNotification(Context context) {
        this.context = context;
    }

    public void userNotification() {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String CHANNEL_ID_USER = "reminder01";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_USER)
                .setSmallIcon(R.drawable.ic_ondemand_video_black_24dp)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(context.getResources().getString(R.string.notif_user))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_USER, CHANNEL_USER, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID_USER);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICATION_ID, notification);
        }

    }

    public void newNotification() {

        Intent intent = new Intent(context.getApplicationContext(), FavoriteActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        final NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIF_GROUP = "new_movies";
        String CHANNEL_ID_NEW = "reminder02";
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID_NEW)
                .setSmallIcon(R.drawable.ic_ondemand_video_black_24dp)
                .setContentIntent(pendingIntent)
                .setGroup(NOTIF_GROUP)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_NEW, CHANNEL_NEW, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID_NEW);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        SimpleDateFormat sdm = new SimpleDateFormat("yyyy-MM-dd");
        String current = sdm.format(new Date());

        AndroidNetworking.get(BuildConfig.TMDB_URL + "discover/movie")
            .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
            .addQueryParameter("region", "US")
            .addQueryParameter("primary_release_date.gte", current)
            .addQueryParameter("primary_release_date.lte", current)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(new JSONObjectRequestListener() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("results");
                        if (jsonArray.length() > 0) {
                            for (int i = 0;i < jsonArray.length(); i++) {
                                mBuilder.setContentTitle(jsonArray.getJSONObject(i).getString("title"))
                                        .setContentText(jsonArray.getJSONObject(i).getString("overview"));

                                Notification notification = mBuilder.build();
                                if (mNotificationManager != null) {
                                    mNotificationManager.notify(jsonArray.getJSONObject(i).getInt("id"), notification);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(ANError anError) {
                    anError.printStackTrace();
                }
            });

    }

}
