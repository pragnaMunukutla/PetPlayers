package com.example.satayam.mypetplayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MediaFileInfo {
    private String fileName,filePath,fileDuration;

    protected Bitmap mVideoThumbnail;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(String fileDuration) {
        this.fileDuration = fileDuration;
    }

    public void setImageBitmap(Bitmap bmThumbnail){
        this.mVideoThumbnail = bmThumbnail;
    }

    public Bitmap getImageBitmap() {
        return mVideoThumbnail;
    }
}
