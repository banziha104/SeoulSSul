package com.veryworks.iyeongjun.seoulssul;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.veryworks.iyeongjun.seoulssul.Domain.Const;
import com.veryworks.iyeongjun.seoulssul.Domain.TempFirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.veryworks.iyeongjun.seoulssul.MainActivity.firebaseList;

public class DetailActivity extends AppCompatActivity {
    int curPos;
    boolean dataType;
    @BindView(R.id.detailtxtTitle)
    TextView detailtxtTitle;
    @BindView(R.id.detailtxtContents)
    TextView detailtxtContents;
    @BindView(R.id.detailBackImgView)
    ImageView detailBackImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        curPos = intent.getExtras().getInt("position");
        dataType = intent.getExtras().getBoolean("DataType");
        if(dataType == Const.DataType.FIREBASE_DATA) setViewWithFirebase(curPos);
        else if(dataType == Const.DataType.SEOUL_DATA) setViewWithSeoulData(curPos);
    }
    private void setViewWithFirebase(int position){
        TempFirebaseDatabase data = firebaseList.get(position);
        detailtxtTitle.setText(data.title);
        detailtxtContents.setText(data.contents);
    }
    private void setViewWithSeoulData(int position){
        TempFirebaseDatabase data = firebaseList.get(position);
        detailtxtTitle.setText(data.title);
        detailtxtContents.setText(data.contents);
    }
}
