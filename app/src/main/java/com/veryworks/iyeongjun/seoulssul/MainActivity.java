package com.veryworks.iyeongjun.seoulssul;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.veryworks.iyeongjun.seoulssul.Domain.Data;
import com.veryworks.iyeongjun.seoulssul.Domain.Row;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulData;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulDataReceiver;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SeoulDataReceiver receiver = new SeoulDataReceiver(this);
    List<Data> datas;
    CustomAdapter adapter;
    SwipeFlingAdapterView flingContainer;

//    private ArrayList<String> al;
//    private ArrayAdapter<String> arrayAdapter;
//    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        receiver.getSeoulData();
        dataLoad();
    }

    private void dataLoader() {

        Data data = new Data();
        datas.add(data);
    }
    private void dataLoad(){
        new AsyncTask<String,Integer,Boolean>(){
            @Override
            protected Boolean doInBackground(String... strings) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }
        }.execute();
    }

    private void addAdapter(){
        //add the view via xml or programmatically
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        datas = new ArrayList<>();
//            al = new ArrayList<>();
        dataLoader();

        //choose your favorite adapter
        adapter = new CustomAdapter(this, R.layout.item);

        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                datas.remove(0);
                adapter.notifyDataSetChanged();

//                    al.remove(0);
//                    arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(MainActivity.this, "Left!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(MainActivity.this, "Right!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // 어댑터가 빈다면 어떻게 할것인가
                // 여기를 바꿔줘야하넹
                Data data = new Data();
                datas.add(data);

                adapter.notifyDataSetChanged();

//                    al.add("XML ".concat(String.valueOf(i)));
//                    arrayAdapter.notifyDataSetChanged();
//                    Log.d("LIST", "notified");
//                    i++;

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
