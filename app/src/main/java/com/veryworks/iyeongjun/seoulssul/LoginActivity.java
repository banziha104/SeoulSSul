package com.veryworks.iyeongjun.seoulssul;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.videoView) VideoView videoView;
    @BindView(R.id.btnFacebook) ImageButton btnFacebook;
    @BindView(R.id.btnNextTime) ImageButton btnNextTime;
    @BindView(R.id.btnKakao) ImageButton btnKakao;
    @BindView(R.id.imgLogo) ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.login);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }
    @OnClick(R.id.btnFacebook)
    private void goMainWithFacebook(){

    }
    @OnClick(R.id.btnKakao)
    private void goMainWithKakao(){

    }
    @OnClick(R.id.btnNextTime)
    private void goMainWithNextTime(){

    }
}
