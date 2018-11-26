package com.beidousat.libpartyvoice.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

import java.io.Serializable;

/**
 * author: Hanson
 * date:   2018/1/16
 * describe:
 */

public class ISListModule implements Serializable {
    private  @DrawableRes int mDrawableRes;
    private @IdRes int mId;
    private String mTitle;
    private String mDescribe;
    public ISListModule(int id, int drawableRes, String title) {
        mId = id;
        mDrawableRes = drawableRes;
        mTitle = title;
    }
    public ISListModule(int id, int drawableRes, String title, String describe) {
        mId = id;
        mDrawableRes = drawableRes;
        mTitle = title;
        mDescribe = describe;
    }

    public int getDrawableRes() {
        return mDrawableRes;
    }

    public void setDrawableRes(int drawableRes) {
        mDrawableRes = drawableRes;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        mDescribe = describe;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
