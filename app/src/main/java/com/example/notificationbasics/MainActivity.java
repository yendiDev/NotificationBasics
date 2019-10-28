package com.example.notificationbasics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "personal_notification";
    public static final int NOTIFICATION_ID = 001;
    public static final String TXT_REPLY = "text_reply";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayNotification(View view)
    {
        Intent landingIntent = new Intent(this,SecondActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,landingIntent,PendingIntent.FLAG_ONE_SHOT);

        Intent YesIntent = new Intent(this, YesActivity.class);
        YesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent yesPendingIntent = PendingIntent.getActivity(this,0,YesIntent,PendingIntent.FLAG_ONE_SHOT);

        Intent NoIntent = new Intent(this, NoActivity.class);
        NoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent noPendingIntent = PendingIntent.getActivity(this,0,NoIntent,PendingIntent.FLAG_ONE_SHOT);


        createNotification();
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification.setSmallIcon(R.drawable.ic_vibration);
        notification.setContentTitle("Trial Notification");
        notification.setContentText("This is trial notification to ne displayed in android version lower than 8.0");
        notification.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notification.setContentIntent(pendingIntent);
        notification.setAutoCancel(true);
        notification.addAction(R.drawable.ic_thumb_up_black_24dp,"Yes",yesPendingIntent);
        notification.addAction(R.drawable.ic_thumb_down_black_24dp,"No",noPendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            RemoteInput remoteInput = new RemoteInput.Builder(TXT_REPLY).setLabel("Reply").build();

            Intent replyIntent = new Intent(this,RemoteReceiver.class);
            replyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent replyPendingIntent = PendingIntent.getActivity(this,0,replyIntent,PendingIntent.FLAG_ONE_SHOT);

            //NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_vibration,"Reply",replyPendingIntent).addRemoteInput(remoteInput).build();

        }


        NotificationManagerCompat compat = NotificationManagerCompat.from(this);
        compat.notify(NOTIFICATION_ID,notification.build());
    }

    public void createNotification()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) 
        {
            CharSequence name = "Personal Notification";
            String Description = "Includes all the personal notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,importance);

            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }


    }

}
