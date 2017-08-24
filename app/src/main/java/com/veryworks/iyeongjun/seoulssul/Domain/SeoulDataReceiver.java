package com.veryworks.iyeongjun.seoulssul.Domain;

import android.content.Context;

import org.json.JSONObject;

import retrofit2.Retrofit;

/**
 * Created by iyeongjun on 2017. 8. 24..
 */

public class SeoulDataReceiver {
    Retrofit retrofit;
    JSONObject jsonObject;
    Context context;
    Data data;

    public void getSeoulData(){
        retrofit = new Retrofit.Builder()
    }
}
