package com.beidousat.libpartyvoice.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ISBanner implements Serializable {

    /**
     * id : 19
     * user_id : 174
     * img_url : http://192.168.1.99:5600/singing/timg.jpg
     * jump_link : http://www.baidu.com
     * sort : 3
     * type : 3
     * position : 1
     * online : 2018-04-27 00:00:00
     * downline : 2018-05-15 11:59:59
     * created_at : 2018-04-26 17:07:54
     * updated_at : 2018-04-26 17:07:54
     * deleted_at : null
     */

    @SerializedName("id")
    private int mId;
    @SerializedName("user_id")
    private int mUserId;
    @SerializedName("img_url")
    private String mImgUrl;
    @SerializedName("jump_link")
    private String mJumpLink;
    @SerializedName("sort")
    private int mSort;
    @SerializedName("type")
    private int mType;
    @SerializedName("position")
    private int mPosition;
    @SerializedName("online")
    private String mOnline;
    @SerializedName("downline")
    private String mDownline;
    @SerializedName("created_at")
    private String mCreatedAt;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public String getJumpLink() {
        return mJumpLink;
    }

    public void setJumpLink(String jumpLink) {
        mJumpLink = jumpLink;
    }

    public int getSort() {
        return mSort;
    }

    public void setSort(int sort) {
        mSort = sort;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public String getOnline() {
        return mOnline;
    }

    public void setOnline(String online) {
        mOnline = online;
    }

    public String getDownline() {
        return mDownline;
    }

    public void setDownline(String downline) {
        mDownline = downline;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }
}
