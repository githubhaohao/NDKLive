package com.haohao.live.params;

/**
 * author: haohao
 * time: 2018/1/5
 * mail: haohaochang86@gmail.com
 * desc: description
 */
public class VideoParams {
    private int width;
    private int height;
    //视频默认码率480kbps
    private int bitRate = 480000;
    //视频默认帧频25帧/s
    private int fps = 25;
    private int cameraId;

    public VideoParams(int width, int height, int cameraId) {
        this.width = width;
        this.height = height;
        this.cameraId = cameraId;
    }

    public VideoParams(int cameraId) {
        this.width = 480;
        this.height = 320;
        this.cameraId = cameraId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCameraId() {
        return cameraId;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setCameraId(int cameraId) {
        this.cameraId = cameraId;
    }

    public int getBitRate() {
        return bitRate;
    }

    public int getFps() {
        return fps;
    }
}
