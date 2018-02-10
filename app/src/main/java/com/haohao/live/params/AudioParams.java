package com.haohao.live.params;

/**
 * author: haohao
 * time: 2018/1/6
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class AudioParams {
    // 采样率
    private int sampleRateInHz = 44100;
    // 声道个数
    private int channel = 1;

    public int getSampleRateInHz() {
        return sampleRateInHz;
    }

    public int getChannel() {
        return channel;
    }
}
