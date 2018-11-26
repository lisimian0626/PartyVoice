package com.beidousat.libpartyvoice.bussiness.contracts;



import com.beidousat.libpartyvoice.model.ISBanner;

import java.util.List;

import pl.sugl.common.contracts.BasePresenter;
import pl.sugl.common.contracts.BaseView;

public interface IBannerContracts {
    interface View extends BaseView {
        void refreshBanner(List<ISBanner> banners);
    }

    interface Presenter extends BasePresenter {
        void fetchBanners(int type);
    }
}
