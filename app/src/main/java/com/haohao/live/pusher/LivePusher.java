package com.haohao.live.pusher;

import android.content.Context;
import android.hardware.Camera;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import com.haohao.live.jni.NativePush;
import com.haohao.live.params.AudioParams;
import com.haohao.live.params.VideoParams;

/**
 * author: haohao
 * time: 2018/1/8
 * mail: haohaochang86@gmail.com
 * desc: LivePusher
 */
public class LivePusher implements SurfaceHolder.Callback {
    private static final String TAG = "LivePusher";
    private VideoPusher mVideoPusher;
    private AudioPusher mAudioPusher;
    private VideoParams mVideoParams;
    private AudioParams mAudioParams;
    private boolean mIsLiving = false;
    private NativePush mNativePush;

    public LivePusher(SurfaceHolder holder){
        prepare(holder);
    }

    private void prepare(SurfaceHolder holder){
        mNativePush = new NativePush();
        //添加回调，在SurfaceView销毁的时候释放所有资源
        holder.addCallback(this);

        //初始化视频采集与推流
        mVideoParams = new VideoParams(Camera.CameraInfo.CAMERA_FACING_BACK);
        mVideoPusher = new VideoPusher(mNativePush, holder, mVideoParams);

        //初始化音频采集与推流
        mAudioParams = new AudioParams();
        mAudioPusher = new AudioPusher(mNativePush, mAudioParams);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        stopPush();
        release();
    }

    public boolean isLiving() {
        return mIsLiving;
    }

    public void switchCamera(){
        mVideoPusher.switchCamera();
    }

    public void startPush(String uri){
        mVideoPusher.startPush();
        mAudioPusher.startPush();
        mNativePush.startPush(uri);
        mIsLiving = true;
    }

    public void stopPush(){
        mVideoPusher.stopPush();
        mAudioPusher.stopPush();
        mNativePush.stopPush();
        mIsLiving = false;
    }

    public void release(){
        mVideoPusher.release();
        mAudioPusher.release();
        mNativePush.release();
    }
}
