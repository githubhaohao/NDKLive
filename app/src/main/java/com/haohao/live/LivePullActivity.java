package com.haohao.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;


/**
 * author: haohao
 * time: 2018/1/5
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class LivePullActivity extends AppCompatActivity {
    private static final String TAG = "LivePullActivity";
    private VideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_pull);
        init();

    }

    private void init(){
        mVideoView = (VideoView) findViewById(R.id.live_player_view);
        mVideoView.setVideoPath(SPUtils.getInstance(this).getString(SPUtils.KEY_NGINX_SER_URI));
        mVideoView.setMediaController(new MediaController(this));
        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
