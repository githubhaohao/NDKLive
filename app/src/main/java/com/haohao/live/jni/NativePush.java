package com.haohao.live.jni;

/**
 * author: haohao
 * time: 2018/1/6
 * mail: haohaochang86@gmail.com
 * desc: description
 */

public class NativePush {
    public static final int CONNECT_FAILED = 101;
    public static final int INIT_FAILED = 102;

    private LiveStateListener mLiveStateListener;

    public void throwNativeError(int code) {
        if (mLiveStateListener != null) {
            mLiveStateListener.onStateChange(code);
        }

    }

    public native void startPush(String url);

    public native void stopPush();

    public native void release();

    /**
     * 设置视频参数
     * @param width
     * @param height
     * @param bitrate
     * @param fps
     */
    public native void setVideoOptions(int width, int height, int bitrate, int fps);

    /**
     * 设置音频参数
     * @param sampleRateInHz
     * @param channel
     */
    public native void setAudioOptions(int sampleRateInHz, int channel);


    /**
     * 发送视频数据
     * @param data
     */
    public native void fireVideo(byte[] data);

    /**
     * 发送音频数据
     * @param data
     * @param len
     */
    public native void fireAudio(byte[] data, int len);

    interface LiveStateListener{
        void onStateChange(int state_code);
    }

}
