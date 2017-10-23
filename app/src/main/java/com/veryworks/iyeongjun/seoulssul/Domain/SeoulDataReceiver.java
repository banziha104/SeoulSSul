package com.veryworks.iyeongjun.seoulssul.Domain;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.veryworks.iyeongjun.seoulssul.AdapterCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Path;

import static com.veryworks.iyeongjun.seoulssul.Domain.UserLocation.currentUserDivision;

/**
 * Created by iyeongjun on 2017. 8. 24..
 */

/**
 * 서울 데이터 크롤링
 */
public class SeoulDataReceiver {
    //http://openAPI.seoul.go.kr:8088/6d59646256646c6431303073497a5353/json/SearchConcertDetailService/1/5/
    Retrofit retrofit;
    Context context;
    Row[] row = null;
    AdapterCallback adapterCallback;
    UserLocation userLocation;
    int start = 1;
    int end = 300;
    public SeoulDataReceiver(Context context) {
        this.context = context;
        adapterCallback = (AdapterCallback)context;
    }

    /**
     * 서울 공공 데이터를 크롤링하는 메소드
     */
    public Row[] getSeoulData() {
        retrofit = new Retrofit.Builder()                   //Retrofit 객체 생성 및 BaseURL 설정
                .baseUrl("http://openAPI.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        InterfaceForGetSeoulData interfaceForGetSeoulData   //인터페이스 생성
                = retrofit.create(InterfaceForGetSeoulData.class);
        final Call<SeoulData> result
                = interfaceForGetSeoulData.getSeoulData(Const.Key.SEOUL_API_KEY,start,end); //서울시 API키 입력
        start += Const.Num.NEXT_PAGE;
        end += Const.Num.NEXT_PAGE;
          result.enqueue(new Callback<SeoulData>() {
            @Override
            public void onResponse(Call<SeoulData> call, Response<SeoulData> response) {
                ArrayList<ShuffledData> datas;
                ArrayList<Row> rowdatas = new ArrayList<Row>();
                if (currentUserDivision == "failed" || currentUserDivision == null){
                    ShuffledData data = new ShuffledData();
                    data.getShuffledData(response.body().getSearchConcertDetailService().getRow());
                    datas = Data.shuffledData;
                }else {
                    Row[] rows = response.body().getSearchConcertDetailService().getRow();
                    for (Row row : rows) {
                        Log.d("LOCATION","/"+row.getGCODE()+"/");
                        if (row.getGCODE() == currentUserDivision) rowdatas.add(row);
                    }
                    ShuffledData data = new ShuffledData();
                    rowdatas.toArray();
                    data.getShuffledData(rowdatas.toArray(new Row[rowdatas.size()]));
                    datas = Data.shuffledData;
                    for (int i = 0; i < datas.size(); i++) {
                        Log.d("Receiever", datas.get(i).getTitle());
                    }
                }
                adapterCallback.callback(datas); //어뎁터 콜백 인터페이스
            }
            @Override
            public void onFailure(Call<SeoulData> call, Throwable t) {
                Log.d("Receiver",t.getMessage() +"");
                row = null;
                Toast.makeText(context, "데이터를 읽어오는데 실패 했습니다", Toast.LENGTH_SHORT).show();
            }
        });
        return row;
    }

    /**
     * 레트로핏 인터페이스 정의
     */
    interface InterfaceForGetSeoulData{
        @GET("/{API_KEY}/json/SearchConcertDetailService/{START}/{END}/")
        Call<SeoulData> getSeoulData(@Path("API_KEY") String apikey,
                                     @Path("START") int start,
                                     @Path("END") int end);
    }


}
