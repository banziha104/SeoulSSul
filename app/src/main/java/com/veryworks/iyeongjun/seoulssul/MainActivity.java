package com.veryworks.iyeongjun.seoulssul;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.veryworks.iyeongjun.seoulssul.Domain.Data;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        //add the view via xml or programmatically
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        datas = new ArrayList<>();
//            al = new ArrayList<>();
        dataLoader();

        //choose your favorite adapter
        adapter = new CustomAdapter(this, R.layout.item, R.id.helloText ,datas);
        //arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );


//            flingContainer.setAdapter(arrayAdapter);
        //set the listener and the adapter
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
                data.setText("asd");
                data.setImageUrl(Uri.parse("http://file.lotoco01.cafe24.com/Product/Images/34/SFSELFAA0034073/Editor/7941630734e3a70cf0478f.JPG"));
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

    private void dataLoader() {

        Data data = new Data();
        data.setImageUrl(Uri.parse("http://cfile240.uf.daum.net/image/2038F33A4E16CB3F0E69D7"));
        data.setText("qwe");
        datas.add(data);

    }
}
