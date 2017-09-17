package com.veryworks.iyeongjun.seoulssul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.veryworks.iyeongjun.seoulssul.Domain.FirebaseData;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulDataReceiver;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;
import com.veryworks.iyeongjun.seoulssul.Domain.UserLocation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AdapterCallback {
    SeoulDataReceiver receiver = new SeoulDataReceiver(this);
    List<ShuffledData> tempData;
    CustomAdapter adapter;

    int curPosition = 1;
    boolean scrolledToggle = true;

    SwipeFlingAdapterView flingContainer;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("boardData");

    @BindView(R.id.frame) SwipeFlingAdapterView frame;
    @BindView(R.id.btnAR) Button btnAR;
    @BindView(R.id.btnWrite) Button btnWrite;
    @BindView(R.id.btnMypage) Button btnMypage;
    @BindView(R.id.btnRefresh) Button btnRefresh;


//    private ArrayList<String> al;
//    private ArrayAdapter<String> arrayAdapter;
//    private int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "amsfont.ttf"));
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);
        tempData = new ArrayList<>();
        receiver.getSeoulData();


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void callback(ArrayList<ShuffledData> datas) {
        for (int i = 0; i < curPosition; i++) {
            tempData.add(datas.get(i));
        }
        final ArrayList<ShuffledData> storage = datas;
        //choose your favorite adapter
        adapter = new CustomAdapter(this, R.layout.item, R.id.txtContents, tempData);
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
                scrolledToggle = true;
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                scrolledToggle = true;
            }


            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // 어댑터가 빈다면 어떻게 할것인가
                // 여기를 바꿔줘야하넹
                Log.d("Empty", "Empty");

//                    al.add("XML ".concat(String.valueOf(i)));
//                    arrayAdapter.notifyDataSetChanged();
//                    Log.d("LIST", "notified");
//                    i++;

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                if (scrolledToggle == true && scrollProgressPercent != 0f) {
                    curPosition++;
                    tempData.add(storage.get(curPosition));
                    adapter.notifyDataSetChanged();
                    scrolledToggle = false;
                }
            }
        });
        Log.d("End", "End");
        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(MainActivity.this, "Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        adapter.notifyDataSetChanged();

    }

    @OnClick(R.id.btnAR)
    public void arButtonClicked() {
        Intent intent = new Intent(MainActivity.this, ARActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnWrite)
    public void writeButtonClicked(){
        Intent intent = new Intent(MainActivity.this, WriteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 막기
    }
}