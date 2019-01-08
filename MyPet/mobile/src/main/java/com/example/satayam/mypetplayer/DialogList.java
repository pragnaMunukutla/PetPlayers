package com.example.satayam.mypetplayer;

public class DialogList {
    private int mOptionid;
    private String mOptionName;
    private boolean mOptionSlected;
    private boolean ismOptionNeedToBeDisplayed;
    private String mOptionPlaybackSpeed;
    public DialogList(int optionid, String optionName, boolean optionSlected, boolean needToBeDisplayed, String playbackSpeed)
    {
        mOptionid = optionid;
        mOptionName = optionName;
        mOptionSlected = optionSlected;
        ismOptionNeedToBeDisplayed = needToBeDisplayed;
        mOptionPlaybackSpeed = playbackSpeed;
    }

    public int getOptionid() {
        return mOptionid;
    }
    public String getOptionName() {
        return mOptionName;
    }
    public boolean getOptionSlected() { return mOptionSlected; }
    public void setmOptionSlected(boolean isSelected){ mOptionSlected = isSelected; }

    public boolean getmOptionNeedToBeDisplayed() { return ismOptionNeedToBeDisplayed;}
    public void setmOptionPlaybackSpeed(String playbackSpeed) {mOptionPlaybackSpeed = playbackSpeed;}
    public String getmOptionPlaybackSpeed() {return mOptionPlaybackSpeed;}
}
