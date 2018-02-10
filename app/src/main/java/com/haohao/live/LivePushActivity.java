package com.haohao.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.haohao.live.pusher.LivePusher;

/**
 * author: haohao
 * time: 2018/1/5
 * mail: haohaochang86@gmail.com
 * desc: LivePushActivity
 */
public class LivePushActivity extends AppCompatActivity {
    private static final String TAG = "LivePushActivity";
    public static final String URI = "rtmp://47.100.7.147:1935/live/test";
    private LivePusher mLivePusher;
    private SurfaceView mSurfaceView;
    private Button mPushBtn, mSwitchCameraBtn;
    private String mRtmpUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_live_push);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mPushBtn = (Button) findViewById(R.id.push_btn);
        mLivePusher = new LivePusher(mSurfaceView.getHolder());
        mRtmpUri = SPUtils.getInstance(this).getString(SPUtils.KEY_NGINX_SER_URI);
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.push_btn:
                if (!mLivePusher.isLiving()) {
                    mLivePusher.startPush(mRtmpUri);
                    mPushBtn.setText("停止直播");
                } else {
                    mLivePusher.stopPush();
                    mPushBtn.setText("开始直播");
                }
                break;
            case R.id.switch_camera_btn:
                mLivePusher.switchCamera();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLivePusher.isLiving()) {
            mLivePusher.release();
        }
    }
}
