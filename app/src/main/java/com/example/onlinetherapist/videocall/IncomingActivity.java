package com.example.onlinetherapist.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.network.ApiClient;
import com.example.onlinetherapist.network.ApiService;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IncomingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        SetUpLayout();
    }

    private void SendInviterMSG(String type, String receiverToken)
    {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();
             data.put(Constant.REMOTE_MSG_TYPE, Constant.REMOTE_MSG_INVITATION_RESPONSE);
             data.put(Constant.REMOTE_MSG_INVITATION_RESPONSE, type);

             body.put(Constant.REMOTE_MSG_DATA, data);
             body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);

             sendRemoteMessage(body.toString(), type);
        } catch (Exception e)
        {
            finish();
        }
    }

    private void SetUpLayout() {
        //intent.putExtra(Constant.ANY_USERNAME, remoteMessage.getData().get(Constant.ANY_USERNAME));
        String uname = getIntent().getStringExtra(Constant.ANY_USERNAME);
        if (uname != null)
            ((TextView)findViewById(R.id.txtInviterName)).setText(uname);
    }

    public void RejectCall(View view) {
        SendInviterMSG(Constant.REMOTE_MSG_INVITATION_REJECTED, getIntent().getStringExtra(Constant.REMOTE_MSG_INVITER_TOKEN));
    }

    public void AcceptCall(View view) {
        SendInviterMSG(Constant.REMOTE_MSG_INVITATION_ACCEPTED, getIntent().getStringExtra(Constant.REMOTE_MSG_INVITER_TOKEN));
    }

    private void sendRemoteMessage(String remoteMessageBody, String type)
    {
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constant.getRemoteMessageHeader(), remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful())
                {
                    if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION_ACCEPTED)) {
                        try {
                            URL serverURL = new URL("https://meet.jit.si");
                            JitsiMeetConferenceOptions conferenceOptions =
                                    new JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(serverURL)
                                    .setWelcomePageEnabled(false)
                                    .setRoom(getIntent().getStringExtra(Constant.REMOTE_MSG_MEETING_ROOM))
                                    .build();
                            JitsiMeetActivity.launch(IncomingActivity.this, conferenceOptions);
                            finish();
                        } catch (Exception e)
                        {
                            Log.e("Exception jitsi", e.getMessage());
                            finish();
                        }
                    }
                    else
                    {
                        finish();
                    }
                }
                else {
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                finish();
            }
        });
    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(Constant.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null)
            {
                if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION_CANCEL))
                {
                    Toast.makeText(context, "Invitation cancelled", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                invitationResponseReceiver,
                new IntentFilter(Constant.REMOTE_MSG_INVITATION_RESPONSE)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                invitationResponseReceiver
        );
    }
}