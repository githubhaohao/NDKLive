package com.haohao.live.pusher;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.haohao.live.jni.NativePush;
import com.haohao.live.params.VideoParams;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * author: haohao
 * time: 2018/1/8
 * mail: haohaochang86@gmail.com
 * desc: VideoPusher
 */
public class VideoPusher implements Pusher, SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final String TAG = "VideoPusher";
    private final NativePush mNativePush;
    private SurfaceHolder mSurfaceHolder;
    private VideoParams mVideoParams;
    private Camera mCamera;
    private byte[] buffer;
    private volatile boolean mIsPushing = false;

    public VideoPusher(NativePush mNativePush, SurfaceHolder mSurfaceHolder, VideoParams mVideoParams) {
        this.mNativePush = mNativePush;
        this.mSurfaceHolder = mSurfaceHolder;
        this.mVideoParams = mVideoParams;
        this.mSurfaceHolder.addCallback(this);
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        if (mCamera != null) {
            mCamera.addCallbackBuffer(buffer);
        }

        if (mIsPushing) {
            mNativePush.fireVideo(bytes);
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //SurfaceView初始化完成以后，开始初始化摄像头，并且进行预览
        startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void startPush() {
        mNativePush.setVideoOptions(mVideoParams.getWidth(), mVideoParams.getHeight(), mVideoParams.getBitRate(), mVideoParams.getFps());
        mIsPushing = true;

    }

    @Override
    public void stopPush() {
        mIsPushing = false;

    }

    @Override
    public void release() {
        stopPreview();
    }

    public void startPreview(){
        try {
            mCamera = Camera.open(mVideoParams.getCameraId());
            Camera.Parameters param = mCamera.getParameters();

            List<Camera.Size> previewSizes = param.getSupportedPreviewSizes();
            int length = previewSizes.size();
            for (int i = 0; i < length; i++) {
                Log.i(TAG, "SupportedPreviewSizes : " + previewSizes.get(i).width + "x" + previewSizes.get(i).height);
            }

            mVideoParams.setWidth(previewSizes.get(0).width);
            mVideoParams.setHeight(previewSizes.get(0).height);

            param.setPreviewFormat(ImageFormat.NV21);
            param.setPreviewSize(mVideoParams.getWidth(), mVideoParams.getHeight());

            mCamera.setParameters(param);
            //mCamera.setDisplayOrientation(90); // 竖屏
            mCamera.setPreviewDisplay(mSurfaceHolder);

            buffer = new byte[mVideoParams.getWidth() * mVideoParams.getHeight() * 4];
            mCamera.addCallbackBuffer(buffer);
            mCamera.setPreviewCallbackWithBuffer(this);

            mCamera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPreview(){
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    public void switchCamera(){
        if (mVideoParams.getCameraId() == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mVideoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            mVideoParams.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK);
        }

        stopPreview();
        startPreview();
    }
}
