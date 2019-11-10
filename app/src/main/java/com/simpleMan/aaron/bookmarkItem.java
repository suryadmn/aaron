package com.simpleMan.aaron;

public class bookmarkItem {
    private int mImageResource;
    private String mTxt1;
    private int mTxt2;
    private String mTxt3;
    private String mTxt4;

    public bookmarkItem(int imageResource, String txt1, int txt2, String txt3, String txt4){
        mImageResource = imageResource;
        mTxt1 = txt1;
        mTxt2 = txt2;
        mTxt3 = txt3;
        mTxt4 = txt4;
    }

    public int getmImageResource(){
        return mImageResource;
    }

    public  String getmTxt1(){
        return mTxt1;
    }

    public int getmTxt2(){
        return mTxt2;
    }

    public String getmTxt3(){
        return mTxt3;
    }

    public String getmTxt4(){
        return mTxt4;
    }
}
