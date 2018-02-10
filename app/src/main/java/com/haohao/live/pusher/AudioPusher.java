package com.haohao.live.pusher;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.haohao.live.jni.NativePush;
import com.haohao.live.params.AudioParams;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: haohao
 * time: 2018/1/6
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class AudioPusher implements Pusher {
    private static final String TAG = "AudioPusher";
    private NativePush mNativePush;
    private AudioParams mAudioParams;
    private AudioRecord mAudioRecord;
    private int mMinBufferSize;
    private volatile boolean mIsPushing;
    private ExecutorService mExecutorService;

    public AudioPusher(NativePush mNativePush, AudioParams mAudioParams) {
        this.mNativePush = mNativePush;
        this.mAudioParams = mAudioParams;

        int channelConfig = mAudioParams.getChannel() == 1 ?
                AudioFormat.CHANNEL_IN_MONO : AudioFormat.CHANNEL_IN_STEREO;

        //最小缓冲区大小
        mMinBufferSize = AudioRecord.getMinBufferSize(mAudioParams.getSampleRateInHz(), channelConfig, AudioFormat.ENCODING_PCM_16BIT);
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, mAudioParams.getSampleRateInHz(),channelConfig, AudioFormat.ENCODING_PCM_16BIT, mMinBufferSize);
        mExecutorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void startPush() {
        mNativePush.setAudioOptions(mAudioParams.getSampleRateInHz(), mAudioParams.getChannel());
        mIsPushing = true;
        mExecutorService.submit(new AudioRecordRunnable());
    }

    @Override
    public void stopPush() {
        mIsPushing = false;
        mAudioRecord.release();
    }

    @Override
    public void release() {
        if (mExecutorService != null) {
            mExecutorService.shutdown();
            mExecutorService = null;
        }

        if (mAudioRecord != null) {
            mAudioRecord.release();
            mAudioRecord = null;
        }
    }

    private class AudioRecordRunnable implements Runnable {

        @Override
        public void run() {
            mAudioRecord.startRecording();
            while (mIsPushing) {
                //通过AudioRecord不断读取音频数据
                byte[] buffer = new byte[mMinBufferSize];
                int length = mAudioRecord.read(buffer, 0, buffer.length);
                if (length > 0) {
                    //传递给 Native 代码，进行音频编码
                    mNativePush.fireAudio(buffer, length);
                }
            }
        }
    }
}
