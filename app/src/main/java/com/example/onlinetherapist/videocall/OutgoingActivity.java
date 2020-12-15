package com.example.onlinetherapist.videocall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.onlinetherapist.Constant;
import com.example.onlinetherapist.R;
import com.example.onlinetherapist.network.ApiClient;
import com.example.onlinetherapist.network.ApiService;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OutgoingActivity extends AppCompatActivity {

    private String inviterToken = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing);

        getFCM();
    }

    private void getFCM()
    {
        inviterToken = getIntent().getStringExtra("inviter_token");
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

            body.put(Constant.REMOTE_MSG_DATA, data);
            body.put(Constant.REMOTE_MSG_REGISTRATION_IDS, tokens);

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
                if(type.equalsIgnoreCase(Constant.REMOTE_MSG_TYPE))
                {

                }
                else
                {
                    Toast.makeText(OutgoingActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(OutgoingActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}