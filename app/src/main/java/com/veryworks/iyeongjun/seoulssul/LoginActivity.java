package com.veryworks.iyeongjun.seoulssul;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kakao.auth.AuthType;
import com.kakao.auth.helper.Base64;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.veryworks.iyeongjun.seoulssul.Domain.Const;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulDataReceiver;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;
import com.veryworks.iyeongjun.seoulssul.Util.PermissionControl;

import org.json.JSONException;
import org.json.JSONObject;
import com.kakao.auth.Session;
import com.kakao.auth.ISessionCallback;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

import static com.kakao.util.helper.Utility.getPackageInfo;
import static com.veryworks.iyeongjun.seoulssul.Domain.UserData.userInstance;
import static com.veryworks.iyeongjun.seoulssul.Util.PermissionControl.checkVersion;

public class LoginActivity extends AppCompatActivity implements PermissionControl.CallBack {
    AdapterCallback adaptercallback;
    @BindView(R.id.videoView)
    VideoView videoView;
    @BindView(R.id.btnFacebook)
    ImageButton btnFacebook;
    @BindView(R.id.btnNextTime)
    ImageButton btnNextTime;
    @BindView(R.id.btnKakao)
    ImageButton btnKakao;
    @BindView(R.id.imgLogo) ImageView imgLogo;
    @BindView(R.id.facebookLoginButton) LoginButton loginButton;
    @BindView(R.id.kakaoLoginButton) com.kakao.usermgmt.LoginButton kakaoLoginButton;
    CallbackManager callbackManager;
    AccessToken accessToken;
    SessionCallback callback;
    boolean canItLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    ArrayList<ShuffledData> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        kakaoInit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkVersion(this);
        } else {
            init();
        }
    }

    private void kakaoInit(){
        try{
            PackageInfo info = getPackageManager()
                    .getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KAKA", Base64.encodeBase64URLSafeString(md.digest()));
            }
            callback = new SessionCallback();
            Session.getCurrentSession().addCallback(callback);
            Session.getCurrentSession().checkAndImplicitOpen();
        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuthrizater();
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        mAuth = FirebaseAuth.getInstance();
        setVideoView();
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        facebookLoginButton();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    public void facebookLoginButton() {
        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        logForFacebook(object.toString());
                        try {
                            Toast.makeText(LoginActivity.this, object.getString("name") + "님 페이스북으로 시작합니다", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        redirectMainActivity();
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                logForFacebook(error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 페이스북 로그인 버튼
     */
    @OnTouch(R.id.btnFacebook)
    public boolean goMainWithFacebook(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            btnFacebook.setImageResource(R.drawable.facebook_click);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            btnFacebook.setImageResource(R.drawable.facebook);
            loginButton.performClick();
        }
        return true;
    }

    @OnClick(R.id.kakaoLoginButton)
    public void kakaoLoginButton() {
        Toast.makeText(this, "gg", Toast.LENGTH_SHORT).show();
        Session.getCurrentSession().open(AuthType.KAKAO_TALK,(Activity)getApplicationContext());
    }

    /**
     *
     */
    @OnTouch(R.id.btnKakao)
    public boolean goMainWithKakao(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            btnKakao.setImageResource(R.drawable.kakao_clcik);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            btnKakao.setImageResource(R.drawable.kakao);
            kakaoLoginButton.performClick();
        }
        return true;
    }


    /**
     *
     */
    @OnTouch(R.id.btnNextTime)
    public boolean goMainWithNextTime(MotionEvent event, View v) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            btnNextTime.setImageResource(R.drawable.next_time_click);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            btnNextTime.setImageResource(R.drawable.next_time);

            guestLogin(Const.Guest.GUEST_EMAIL, Const.Guest.GUEST_PASSWORD);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    /**
     * Setting Video
     */
    private void setVideoView() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        videoView.setListener
    }

    private void FirebaseAuthrizater() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    logForFirebase("onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    logForFirebase("onAuthStateChanged:signed_out");
                }
            }
        };
    }

    private void guestLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "게스트로 시작합니다",
                                    Toast.LENGTH_SHORT).show();
                            userInstance.setName("GUEST");
                            userInstance.setImage_url("noImage");
                            userInstance.setNextTime(true);
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private class SessionCallback implements ISessionCallback{
        @Override
        public void onSessionOpened() {
            logForKakao("Session Opened!");
            redirectSignupActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception!=null){
                logForKakao("Session Failed"+exception);
            }
        }
    }

    /**
     * 사인업 액티비티로 이동
     */
    private void redirectSignupActivity(){
        final Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 메인액티비티로 이동
     */
    private void redirectMainActivity(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void logForFacebook(String str){
        Log.d("FACEBOOK",str);
    }
    private void logForKakao(String str){
        Log.d("KAKAO",str);
    }
    private void logForFirebase(String str){
        Log.d("Firebase",str);
    }

}

