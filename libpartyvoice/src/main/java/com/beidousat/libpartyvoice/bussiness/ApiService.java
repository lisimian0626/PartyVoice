package com.beidousat.libpartyvoice.bussiness;

import com.beidousat.libpartyvoice.base.ISBaseModel;
import com.beidousat.libpartyvoice.base.ISBasePager;
import com.beidousat.libpartyvoice.common.Constant;
import com.beidousat.libpartyvoice.model.ISBanner;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public interface ApiService {

    //生产环境
    String BASE_URL = Constant.BASE_URL;
    /**
     * 获取焦点图列表
     *
     * @param type 类型 {@link Constant.BannerType}
     * @return
     */
//    @GET(API_VER + "/singing/focus")
    Observable<ISBaseModel<ISBasePager<ISBanner>>> getBanner(@Query("type") int type);

}
