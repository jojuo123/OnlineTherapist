package com.example.onlinetherapist;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.onlinetherapist.videocall.IncomingActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("FCM", "Token " + s);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get(Constant.REMOTE_MSG_TYPE);

        if (type != null)
        {
            if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION))
            {
                Log.e("invite", "fofo");
                Intent intent = new Intent(getApplicationContext(), IncomingActivity.class);
                intent.putExtra(Constant.REMOTE_MSG_MEETING_TYPE, remoteMessage.getData().get(Constant.REMOTE_MSG_MEETING_TYPE));
                intent.putExtra(Constant.ANY_USERNAME, remoteMessage.getData().get(Constant.ANY_USERNAME));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}
