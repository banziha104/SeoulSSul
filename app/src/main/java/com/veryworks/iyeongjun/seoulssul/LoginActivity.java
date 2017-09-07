package com.veryworks.iyeongjun.seoulssul;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.btnFacebook) ImageButton btnFacebook;
    @BindView(R.id.btnNextTime) ImageButton btnNextTime;
    @BindView(R.id.btnKakao) ImageButton btnKakao;
    @BindView(R.id.imgLogo) ImageView imgLogo;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setVideoView();

    }

    /**
     * 페이스북 로그인 버튼
     */
    @OnClick(R.id.btnFacebook)
    private void goMainWithFacebook(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    /**
     *
     */
    @OnClick(R.id.btnKakao)
    private void goMainWithKakao(){
        Toast.makeText(this, "카카오 로그인은 릴리즈 버전에서만 가능", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     */
    @OnClick(R.id.btnNextTime)
    private void goMainWithNextTime(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

    /**
     * Setting Video
     */
    private void setVideoView(){
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }
}
