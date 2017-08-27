package com.veryworks.iyeongjun.seoulssul.Domain;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

/**
 * Created by iyeongjun on 2017. 8. 24..
 */

public class SeoulDataReceiver {
    //http://openAPI.seoul.go.kr:8088/6d59646256646c6431303073497a5353/json/SearchConcertDetailService/1/5/
    Retrofit retrofit;
    Context context;

    public SeoulDataReceiver(Context context) {
        this.context = context;
    }

    /**
     * 서울 공공 데이터를 크롤링하는 메소드
     */
    public void getSeoulData() {

        retrofit = new Retrofit.Builder()                   //Retrofit 객체 생성 및 BaseURL 설정
                .baseUrl("http://openAPI.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceForGetSeoulData interfaceForGetSeoulData   //인터페이스 생성
                                        = retrofit.create(InterfaceForGetSeoulData.class);

        Call<SeoulData> result
                = interfaceForGetSeoulData.getSeoulData(Const.Key.SEOUL_API_KEY); //서울시 API키 입력

        result.enqueue(new Callback<SeoulData>() {
            @Override
            public void onResponse(Call<SeoulData> call, Response<SeoulData> response) {
                SeoulData seoulData = response.body();
                Data.rows = seoulData.getSearchConcertDetailService().getRow();
                Log.d("Data",Data.rows[0].toString());
                Log.d("Data",Data.rows.length + "");
            }
            @Override
            public void onFailure(Call<SeoulData> call, Throwable t) {
                Toast.makeText(context, "데이터를 읽어오는데 실패 했습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 레트로핏 인터페이스 정의
     */
    interface InterfaceForGetSeoulData{
        @GET("/{API_KEY}/json/SearchConcertDetailService/1/100/")
        Call<SeoulData> getSeoulData(@Path("API_KEY") String apikey);
    }
}
