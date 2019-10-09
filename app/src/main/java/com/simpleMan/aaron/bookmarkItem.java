package com.simpleMan.aaron;

public class bookmarkItem {
    private int mImageResource;
    private String mTxt1;
    private String mTxt2;
    private String mTxt3;

    public bookmarkItem(int imageResource, String txt1, String txt2, String txt3){
        mImageResource = imageResource;
        mTxt1 = txt1;
        mTxt2 = txt2;
        mTxt3 = txt3;
    }

    public void changeText1(String text){
        mTxt1 = text;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public  String getmTxt1(){
        return mTxt1;
    }

    public String getmTxt2(){
        return mTxt2;
    }

    public String getmTxt3(){
        return mTxt3;
    }
}
