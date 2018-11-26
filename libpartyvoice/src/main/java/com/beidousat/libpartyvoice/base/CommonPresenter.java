package com.beidousat.libpartyvoice.base;

import com.beidousat.libpartyvoice.bussiness.ApiService;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import pl.sugl.common.contracts.BasePresenter;

/**
 * author: Hanson
 * date:   2018/1/17
 * describe:通用Presenter，确定V-P一对一关系
 */

public abstract class CommonPresenter<T> implements BasePresenter {
    protected CompositeDisposable mCompositeDisposable;
    protected ApiService mApiService;
    protected T mView;
    /**
     * 列表是否还有更多数据
     */
    protected boolean isNoMore;
    /**
     * 保存指定url当前页数据
     */
    protected Map<String, ISBasePager<?>> mPagerCache;
    /**
     * 起始页码数
     */
    protected static final int FIRST_PAGER_INDEX = 1;

    public CommonPresenter(ApiService apiService, T view) {
        mApiService = apiService;
        mView = view;
        mCompositeDisposable = new CompositeDisposable();
        mPagerCache = new HashMap<>();
    }

    @Override
    public void cancel() {
        mCompositeDisposable.clear();
    }

    public void addDispose(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    public boolean isNoMoreData() {
        return isNoMore;
    }

    @Override
    public void setNoMoreData(boolean isNoMore) {
        this.isNoMore = isNoMore;
    }

    /**
     * 获取调用该方法的方法名（例如：AAA()中调用getMethodName，返回 “AAA”）
     * @return
     */
    public String getMethodName() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        return stack[3].getMethodName();
    }

    /**
     * 保存当前页码数据
     * @param key url
     * @param curPage 当前页码
     * @param pager 当前页码实例
     */
    public void savePagerCache(String key, int curPage, ISBasePager pager) {
        mPagerCache.put(key, pager);
        setNoMoreData(curPage >= pager.getLastPage());
    }

    /**
     * 获取下一页页码
     * @return
     */
    public int getNextPager() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        String methodName = stack[3].getMethodName();
        ISBasePager pager = mPagerCache.get(methodName);
        int curPage = 1;
        if (pager != null) {
            curPage = pager.getCurrentPage() + 1;
            if (curPage > pager.getLastPage()) {
                setNoMoreData(true);
            }
        }

        return curPage;
    }
}
