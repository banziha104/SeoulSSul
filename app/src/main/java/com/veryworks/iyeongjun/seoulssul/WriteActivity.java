package com.veryworks.iyeongjun.seoulssul;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.veryworks.iyeongjun.seoulssul.Domain.Const;
import com.veryworks.iyeongjun.seoulssul.Util.CustomBitmapPool;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class WriteActivity extends AppCompatActivity {
    int[] drawableResource = new int[Const.Num.IMG_LENGTH];
    @BindView(R.id.imgWriteBackground) ImageView imgWriteBackground;
    @BindView(R.id.editWrite) EditText editWrite;
    @BindView(R.id.imgWrite) ImageView imgWrite;
    @BindView(R.id.imageButton) ImageButton imageButton;
    @BindView(R.id.writeContainer) RelativeLayout writeContainer;
    Random random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
        random = new Random();
        setImagBackground();
    }

    private void setImagBackground(){
        int randomImage = Math.abs(random.nextInt() % Const.Num.IMG_LENGTH);
        for(int i = 0; i< Const.Num.IMG_LENGTH; i++){
            String resName;
            if(i<10) resName= "@drawable/img0"+i;
            else resName= "@drawable/img"+i;
            drawableResource[i] = this.getResources().getIdentifier(resName,"drawable",this.getPackageName());
            Log.d("drwable",drawableResource[i]+"");
        }
        Glide.with(this).load(drawableResource[randomImage])
                .bitmapTransform(new ColorFilterTransformation(new CustomBitmapPool(), Color.argb(80, 0, 0, 0)))
                .override(800,1000).into(imgWriteBackground);
    }
}
