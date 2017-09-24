package com.veryworks.iyeongjun.seoulssul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
        setContentView(R.layout.activity_sign_up);
    }
    protected void requestMe(){
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "Failed to get user info. msg =" + errorResult;
                Log.d("KAKAO",message);

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if(result == ErrorCode.CLIENT_ERROR_CODE){
                    finish();
                }else{
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
                Log.d("KAKAO","Login Failed"+errorResult);
            }

            @Override
            public void onNotSignedUp() {

            }

            @Override
            public void onSuccess(UserProfile result) {
                Log.d("KAKAO","Login Success");
                redirectMainActivity();
            }
        });
    }
    private void redirectMainActivity(){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    protected void redirectLoginActivity(){
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }
}
