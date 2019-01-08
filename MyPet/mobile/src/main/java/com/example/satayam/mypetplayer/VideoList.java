/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.satayam.mypetplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * {@link VideoList} represents a single Android platform release.
 * Each object has 4 properties: title, duration, video path, video thumbnail.
 */
public class VideoList extends RecyclerView.ViewHolder{

    private Context context;

    // Name of the Video
    protected TextView mVideoTitle;

    // Video Duration
    protected TextView mVideoDuration;

    // Video Path on the Android File System.
    protected TextView mVideoPath;

    // Video Thumbnail.
    protected ImageView mVideoThumbnail;

    /*
    * Create a new VideoList object.
    *
    * @param vTitle is the title of the video.
    * @param vDuration is the corresponding video duration.
    * @param vPath is the corresponding video path
    * @param vThumbnail is drawable reference ID that corresponds to the video thumbnail.
    * */

    public VideoList(View view) {
        super(view);
        context = view.getContext();
        this.mVideoTitle = (TextView) view.findViewById(R.id.video_title);
        this.mVideoDuration = (TextView) view.findViewById(R.id.video_duration);
        this.mVideoPath =  (TextView) view.findViewById(R.id.video_path);
        this.mVideoThumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView vPath = (TextView) v.findViewById(R.id.video_path);
                String path = vPath.getText().toString();

                TextView vTitle = (TextView) v.findViewById(R.id.video_title);
                String title = vTitle.getText().toString();

                Intent myIntent = new Intent(context, PlayerActivity.class);
                myIntent.putExtra("filepath",path);
                myIntent.putExtra("filetitle",title);
                Log.d("PlayerActiva","Calling Intent");
                context.startActivity(myIntent);
            }
        });
    }
}
