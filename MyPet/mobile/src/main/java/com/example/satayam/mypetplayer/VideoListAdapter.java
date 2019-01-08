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

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
* {@link VideoListAdapter} is an {@link ArrayAdapter} that can provide the layout for each list
* based on a data source, which is a list of {@link VideoList} objects.
* */
public class VideoListAdapter extends RecyclerView.Adapter<VideoList>{

    private static final String LOG_TAG = VideoListAdapter.class.getSimpleName();

    private List<MediaFileInfo> videoList;

    private Context mContext;

    public VideoListAdapter(Context context, List<MediaFileInfo> videoList) {
        this.videoList = videoList;
        this.mContext = context;
    }

    @Override
    public VideoList onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_list_item, null);
        return new VideoList(view);
    }


    @Override
    public void onBindViewHolder(VideoList videoListHolder, int i) {
        try{
            MediaFileInfo item = videoList.get(i);
            videoListHolder.mVideoTitle.setText(Html.fromHtml(item.getFileName()));
            if(item.getImageBitmap() != null) {
                videoListHolder.mVideoThumbnail.setImageBitmap(item.getImageBitmap());
            }
            videoListHolder.mVideoPath.setText(Html.fromHtml(item.getFilePath()));
            videoListHolder.mVideoDuration.setText(Html.fromHtml(item.getFileDuration()));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != videoList ? videoList.size() : 0);
    }
}
