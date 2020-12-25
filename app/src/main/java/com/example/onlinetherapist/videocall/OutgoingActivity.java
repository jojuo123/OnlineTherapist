package com.example.onlinetherapist.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
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
import org.w3c.dom.Text;

import java.net.URL;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingActivity extends AppCompatActivity {

    private String inviterToken = null;
    private String meetingType = null;
    private String inviteeUName = null;
    private String inviteeToken = null;
    private String meetingRoom = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_call);

        InitVariable();
        SetUpLayout();
//        Log.e("type", meetingType);
//        Log.e("token", inviteeToken);
//        Log.e("token", inviterToken);
//        Log.e("uname", inviteeUName);
        initiateMeeting(meetingType, inviteeToken);
//        intent.putExtra("inviteeUName", adminUName);
//        intent.putExtra("invitee_token", adminToken);
//        intent.putExtra("type", "audio");
//        activity.startActivity(intent);
        ImageButton btnStopInvitation = this.findViewById(R.id.btnStopInvitation);
        btnStopInvitation.setOnClickListener(v -> {
            cancelInvitation(inviteeToken);
        });
    }

    private void SetUpLayout() {
        ((TextView)this.findViewById(R.id.txtPatientName)).setText(inviteeUName);
    }

    private void InitVariable()
    {
        meetingType = getIntent().getStringExtra("type");
        inviteeToken = getIntent().getStringExtra("invitee_token");
        inviteeUName = getIntent().getStringExtra("inviteeUName");
        inviterToken = GetToken();
    }

    private String GetToken()
    {
        String token;
        SharedPreferences sharedPreferences = getSharedPreferences("SavedFCMToken", MODE_PRIVATE);
        token = sharedPreferences.getString("fcm_token_value", "");
        return token;
    }

    public String SavedCurrentUsername(){
        String Username;
        SharedPreferences saved=getSharedPreferences("SavedUsername", MODE_PRIVATE);
        Username = saved.getString("username","");
        return Username;
    }


    private void initiateMeeting(String meetingType, String receiverToken)
    {
        try {

            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();

            data.put(Constant.REMOTE_MSG_TYPE, Constant.REMOTE_MSG_INVITATION);
            data.put(Constant.REMOTE_MSG_MEETING_TYPE, meetingType);
            data.put(Constant.ANY_USERNAME, SavedCurrentUsername());
            data.put(Constant.REMOTE_MSG_INVITER_TOKEN, inviterToken);

            meetingRoom = SavedCurrentUsername() + "_" + UUID.randomUUID().toString().substring(0, 5);
            data.put(Constant.REMOTE_MSG_MEETING_ROOM, meetingRoom);

            body.put(Constant.REMOTE_MSG_DATA, data);
            body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);

            Log.e("json", body.toString());

            sendRemoteMessage(body.toString(), Constant.REMOTE_MSG_INVITATION);
        } catch (Exception exception)
        {
            Toast.makeText(OutgoingActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void sendRemoteMessage(String remoteMessageBody, String type)
    {
        ApiClient.getClient().create(ApiService.class).sendRemoteMessage(
                Constant.getRemoteMessageHeader(), remoteMessageBody
        ).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull  Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful())
                {
                    Log.e("success call", "success");
                    if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION))
                    {

                    }
                    else if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION_RESPONSE))
                    {
                        finish();
                    }
                }
                else
                {
                    Log.e("response type wrong", response.message());
                    Toast.makeText(OutgoingActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e("api fail", t.getMessage());
                Toast.makeText(OutgoingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void cancelInvitation(String receiverToken)
    {
        try {
            JSONArray tokens = new JSONArray();
            tokens.put(receiverToken);

            JSONObject body = new JSONObject();
            JSONObject data = new JSONObject();
            data.put(Constant.REMOTE_MSG_TYPE, Constant.REMOTE_MSG_INVITATION_RESPONSE);
            data.put(Constant.REMOTE_MSG_INVITATION_RESPONSE, Constant.REMOTE_MSG_INVITATION_CANCEL);

            body.put(Constant.REMOTE_MSG_DATA, data);
            body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);

            sendRemoteMessage(body.toString(), Constant.REMOTE_MSG_INVITATION_RESPONSE);
        } catch (Exception e)
        {
            finish();
        }
    }

    private BroadcastReceiver invitationResponseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String type = intent.getStringExtra(Constant.REMOTE_MSG_INVITATION_RESPONSE);
            if (type != null)
            {
                if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION_ACCEPTED))
                {
                    try {
                        URL serverURL = new URL("https://meet.jit.si");
                        JitsiMeetConferenceOptions conferenceOptions =
                                new JitsiMeetConferenceOptions.Builder()
                                        .setServerURL(serverURL)
                                        .setWelcomePageEnabled(false)
                                        .setRoom(meetingRoom)
                                        .build();
                        JitsiMeetActivity.launch(OutgoingActivity.this, conferenceOptions);
                        finish();
                    } catch(Exception e)
                    {
                        finish();
                    }
                }
                else if (type.equalsIgnoreCase(Constant.REMOTE_MSG_INVITATION_REJECTED))
                {
                    Toast.makeText(context, "Invitation rejected", Toast.LENGTH_SHORT).show();
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