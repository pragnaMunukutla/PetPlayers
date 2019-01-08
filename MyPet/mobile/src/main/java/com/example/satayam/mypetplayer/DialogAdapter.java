package com.example.satayam.mypetplayer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class DialogAdapter extends ArrayAdapter<DialogList> {

    private static final String LOG_TAG = DialogAdapter.class.getSimpleName();


    public DialogAdapter(Activity context, ArrayList<DialogList> dialogList) {
        super(context, 0, dialogList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.bottom_menu_list_item, parent, false);
        }

        // Get the {@link VocabList} object located at this position in the list
        DialogList currentDialogList = getItem(position);


        ImageView optionIconView = (ImageView) listItemView.findViewById(R.id.bottommenu_option_image);
        if(currentDialogList.getOptionSlected())
            optionIconView.setImageResource(currentDialogList.getOptionid());
        else
            optionIconView.setImageResource(0);


        ImageView optionDotIconView = (ImageView) listItemView.findViewById(R.id.bottommenu_dot_image);
        TextView optionPlaybackSpeedText = (TextView) listItemView.findViewById(R.id.bottommenu_option_selected);
        if(currentDialogList.getmOptionNeedToBeDisplayed()){
            optionDotIconView.setVisibility(View.VISIBLE);
            optionPlaybackSpeedText.setVisibility(View.VISIBLE);
            optionPlaybackSpeedText.setText(currentDialogList.getmOptionPlaybackSpeed());
        }else{
            optionDotIconView.setVisibility(View.GONE);
            optionPlaybackSpeedText.setVisibility(View.VISIBLE);
            optionPlaybackSpeedText.setText(currentDialogList.getmOptionPlaybackSpeed());
        }


        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView optionTextView = (TextView) listItemView.findViewById(R.id.bottommenu_option_text);
        optionTextView.setText(currentDialogList.getOptionName());

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}