package com.beidousat.libpartyvoice.bussiness.presenter;



import com.beidousat.libpartyvoice.base.CommonPresenter;
import com.beidousat.libpartyvoice.base.ISBaseModel;
import com.beidousat.libpartyvoice.base.ISBasePager;
import com.beidousat.libpartyvoice.bussiness.ApiService;
import com.beidousat.libpartyvoice.bussiness.contracts.IBannerContracts;
import com.beidousat.libpartyvoice.exception.AbsExceptionEngine;
import com.beidousat.libpartyvoice.model.ISBanner;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BannerPresenter extends CommonPresenter<IBannerContracts.View> implements IBannerContracts.Presenter {


    public BannerPresenter(ApiService apiService, IBannerContracts.View view) {
        super(apiService, view);
    }

    @Override
    public void fetchBanners(int type) {
        final String method = getMethodName();
        Disposable disposable = mApiService.getBanner(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ISBaseModel<ISBasePager<ISBanner>>>() {
                    @Override
                    public void accept(ISBaseModel<ISBasePager<ISBanner>> response) throws Exception {
                        mView.refreshBanner(response.getEntity().getData());
                    }
                }, new AbsExceptionEngine() {
                    @Override
                    public void handMessage(String message) {
                        mView.onFeedBack(false, method, message);
                    }
                });

        addDispose(disposable);
    }
}
