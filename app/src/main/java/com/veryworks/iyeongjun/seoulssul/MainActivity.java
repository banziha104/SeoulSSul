package com.veryworks.iyeongjun.seoulssul;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;
import com.veryworks.iyeongjun.seoulssul.Domain.SeoulDataReceiver;
import com.veryworks.iyeongjun.seoulssul.Domain.ShuffledData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity implements AdapterCallback {
    @BindView(R.id.btnLike) ImageButton btnLike;
    @BindView(R.id.btnAr) ImageButton btnAr;
    @BindView(R.id.btnRedirect) ImageButton btnRedirect;
    @BindView(R.id.btnCall) ImageButton btnCall;
    @BindView(R.id.btnWrite) ImageButton btnWrite;
    @BindView(R.id.frame) SwipeFlingAdapterView frame;

    List<ShuffledData> tempData;
    CustomAdapter adapter;
    SeoulDataReceiver receiver = new SeoulDataReceiver(this);
    ShuffledData tempShuffledData = new ShuffledData();
    int curPosition = 1;
    boolean scrolledToggle = true;

    SwipeFlingAdapterView flingContainer;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("boardData");



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
                    tempShuffledData = storage.get(curPosition);
                    adapter.notifyDataSetChanged();
                    scrolledToggle = false;
                    Log.d("CALL", tempShuffledData.getInquiry());
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

    @OnTouch(R.id.btnCall)
    public boolean callButtonClicked(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            btnCall.setImageResource(R.drawable.btn_call_click);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            btnCall.setImageResource(R.drawable.btn_call); if (!tempShuffledData.isFirebase()) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + tempShuffledData.getInquiry()));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                startActivity(intent);
            }
        }

        return true;
    }

    @OnTouch(R.id.btnRedirect)
    public boolean redirectButtonClicked(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            btnRedirect.setImageResource(R.drawable.btn_redirect_click);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            btnRedirect.setImageResource(R.drawable.btn_redirect);
            if (!tempShuffledData.isFirebase()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempShuffledData.getOrg_link()));
                startActivity(intent);
            }
        }
        return true;
    }

    @OnTouch(R.id.btnAr)
    public boolean arButtonClicked(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            btnAr.setImageResource(R.drawable.btn_ar_click);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            btnAr.setImageResource(R.drawable.btn_ar);
            Intent intent = new Intent(MainActivity.this, ARActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @OnTouch(R.id.btnWrite)
    public boolean writeButtonClicked(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            btnWrite.setImageResource(R.drawable.btn_write_click);
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            btnWrite.setImageResource(R.drawable.btn_write);
            Intent intent = new Intent(MainActivity.this, WriteActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        // 뒤로가기 막기
    }

}