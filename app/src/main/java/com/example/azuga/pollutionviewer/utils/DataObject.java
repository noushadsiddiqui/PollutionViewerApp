package com.example.azuga.pollutionviewer.utils;

/**
 * Created by User on 02-02-2016.
 */
public class DataObject {
    private String mText1;
    private String mText2;
    private int mColor;

    public DataObject(String text1, String text2, int color) {
        mText1 = text1;
        mText2 = text2;
        mColor = color;
    }

    public String getmText1() {
        return mText1;
    }

    public void setmText1(String mText1) {
        this.mText1 = mText1;
    }

    public String getmText2() {
        return mText2;
    }

    public void setmText2(String mText2) {
        this.mText2 = mText2;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }
}
