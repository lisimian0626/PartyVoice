package com.beidousat.libpartyvoice.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class ISDataModel<T> implements Serializable {
    @SerializedName(
            value = "data",
            alternate = {"id", "jwt", "user_profile", "comment", "follow", "like", "work_channel",
                    "work", "song", "rong_cloud_token", "focus", "activity_signup", "trade", "sign_data",
                    "store", "activity_song", "collection"})
    public T data;
}
