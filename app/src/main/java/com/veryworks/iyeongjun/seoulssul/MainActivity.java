package com.veryworks.iyeongjun.seoulssul;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulDataReceiver;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements AdapterCallback {
    SeoulDataReceiver receiver = new SeoulDataReceiver(this);
    List<ShuffledData> tempData;
    CustomAdapter adapter;
    int position = 0;
    SwipeFlingAdapterView flingContainer;

    @BindView(R.id.button2) Button button2;
    @BindView(R.id.button3) Button button3;
    @BindView(R.id.button4) Button button4;
    @BindView(R.id.button5) Button button5;

//    private ArrayList<String> al;
//    private ArrayAdapter<String> arrayAdapter;
//    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        tempData = new ArrayList<>();
        receiver.getSeoulData();

    }


    @Override
    public void callback(ArrayList<ShuffledData> datas) {
        tempData = datas;
        //choose your favorite adapter
        adapter = new CustomAdapter(this, R.layout.item, R.id.txtContents,tempData);

        for(int i = 0 ; i < tempData.size() ; i++){
            Log.d("tempData",tempData.get(i).getImage());
        }
        flingContainer.setAdapter(adapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                tempData.remove(0);
                adapter.notifyDataSetChanged();

//                    al.remove(0);
//                    arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
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
                Log.d("Empty","Empty");
                ShuffledData data = new ShuffledData();
                data.setTitle("빔");
                data.setContents("끝");
                tempData.add(data);
                adapter.notifyDataSetChanged();

//                    al.add("XML ".concat(String.valueOf(i)));
//                    arrayAdapter.notifyDataSetChanged();
//                    Log.d("LIST", "notified");
//                    i++;

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                Toast.makeText(MainActivity.this, "OnScroll", Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("End","End");
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            Thread.sleep(5000);
            adapter.notifyDataSetChanged();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
