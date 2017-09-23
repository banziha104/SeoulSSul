package com.veryworks.iyeongjun.seoulssul;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

/**
 * Created by iyeongjun on 2017. 9. 21..
 */

public class GlobalApplication extends Application {

    private static volatile GlobalApplication instance = null;
    private static volatile Activity currentActivity =null;

    public static Activity getCurrentActivity(){
        return currentActivity;
    }
    public static void setCurrentActivity(Activity activity){
        currentActivity = activity;
    }
    public static GlobalApplication getGlobalApplicationContext(){
        if(instance ==null)
            throw new IllegalStateException("inherit com.kakao.GlobalApplication");
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance =null;
    }
}