package com.beidousat.libpartyvoice.fragment;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.beidousat.libpartyvoice.R;
import com.beidousat.libpartyvoice.adapter.ModuleAdapter;
import com.beidousat.libpartyvoice.adapter.ModuleAdapter2;
import com.beidousat.libpartyvoice.bussiness.UiJumpHelper;
import com.beidousat.libpartyvoice.common.Constant;
import com.beidousat.libpartyvoice.model.ISBanner;
import com.beidousat.libpartyvoice.model.factory.IModeFactory;
import com.beidousat.libpartyvoice.utiltool.GlideImageLoader;
import com.beidousat.libpartyvoice.utiltool.MultiScreenUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.LightStatusBarUtils;
import com.jaeger.library.ToolbarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pl.sugl.common.base.BaseActivity;
import pl.sugl.common.base.BaseFragment;
import pl.sugl.common.utils.ToolbarInstaller;
import pl.sugl.common.widget.VpSwipeRefreshLayout;
import pl.sugl.common.widget.decoration.HorizontalDividerItemDecoration;
import pl.sugl.common.widget.decoration.VerticalDividerItemDecoration;

/**
 * author: Hanson
 * date:   2018/1/15
 * describe:
 */

public class FmHome extends BaseFragment {
    Banner mBanner;
//    BannerViewPager mBannerViewPager;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    ImageView mIvAd;
    VpSwipeRefreshLayout mSwipeRefreshLayout;
    AppBarLayout mAppBarLayout;
    private ModuleAdapter mAdapter;
    private ModuleAdapter2 mAdapter2;
    private FmContent fmContent;
    @Override
    public int getContentView() {
        return R.layout.party_voice_main;
    }

    @Override
    public void adapterStatusBar() {
        super.adapterStatusBar();
        ToolbarUtil.setColorNoTranslucent(mAttachedAct, getResources().getColor(R.color.red_title));
        LightStatusBarUtils.setLightStatusBar(mAttachedAct, true);
    }

    @Override
    public void initViews() {
        setupToolbar();
        mBanner = mRootView.findViewById(R.id.banner);
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//        mBannerViewPager = mRootView.findViewById(R.id.bannerViewPager);
//        mBannerViewPager.setOffscreenPageLimit(3);
//        mBannerViewPager.setPageMargin(10);
        mRecyclerView = mRootView.findViewById(R.id.category_recyclerview);
        mAdapter = new ModuleAdapter(R.layout.list_item_module,
                IModeFactory.createModules());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(mAdapter);
        VerticalDividerItemDecoration verDivider = new VerticalDividerItemDecoration.Builder(getContext())
                .sizeResId(R.dimen.divider).color(Color.TRANSPARENT).build();
        HorizontalDividerItemDecoration horDivider = new HorizontalDividerItemDecoration.Builder(getContext())
                .sizeResId(R.dimen.divider).color(Color.TRANSPARENT).build();
        mRecyclerView.addItemDecoration(horDivider);
        mRecyclerView.addItemDecoration(verDivider);
        mRecyclerView2=mRootView.findViewById(R.id.category_recyclerview2);
        mAdapter2 = new ModuleAdapter2(R.layout.list_item_module2,
                IModeFactory.createModules2());
        mRecyclerView2.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mRecyclerView2.setAdapter(mAdapter2);
        mRecyclerView2.addItemDecoration(horDivider);
//        MultiScreenUtils.resizeViews(mRootView);
//        mRecyclerView2.addItemDecoration(verDivider);
    }

    private void addYcyhMatchList() {
        fmContent = new FmContent();
        FragmentTransaction trans = getChildFragmentManager().beginTransaction();
        trans.replace(R.id.content_view, fmContent);
        trans.commit();
    }
    @Override
    public void setListeners() {

    }

    @Override
    public void initData() {
        String Json_banner = "[{\"id\":2,\"user_id\":191,\"img_url\":\"http:\\/\\/test.beidousat.com:5600\\/singing\\/yuechangyue.jpg\",\"jump_link\":\"https:\\/\\/m.beidousat.com\\/app\\/vote\\/\",\"sort\":1,\"type\":1,\"position\":1,\"online\":\"2018-06-16 00:00:00\",\"downline\":\"2018-11-30 11:59:59\",\"created_at\":\"2018-05-08 09:48:49\",\"updated_at\":\"2018-06-15 16:56:48\",\"deleted_at\":null},{\"id\":5,\"user_id\":191,\"img_url\":\"http:\\/\\/e.beidousat.com:5600\\/singing\\/bd6a4d88a65743d21836b8ac59d02629.png\",\"jump_link\":\"http:\\/\\/e.beidousat.com\\/yue-chang-yue-hao\\/introduction-app.html\",\"sort\":1,\"type\":1,\"position\":3,\"online\":\"2018-07-26 00:00:00\",\"downline\":\"2019-01-31 11:59:59\",\"created_at\":\"2018-06-15 16:57:40\",\"updated_at\":\"2018-07-26 17:40:24\",\"deleted_at\":null}]";
        Gson gson = new Gson();
        List<ISBanner> banners = gson.fromJson(Json_banner, new TypeToken<List<ISBanner>>(){}.getType());
        List<String> urls = new ArrayList<>();
        for (ISBanner item : banners) {
            if (item.getType() == Constant.BannerType.BANNER) {
                urls.add(item.getImgUrl());
                mBanner.setImages(urls)
                        .setImageLoader(new GlideImageLoader())
                        .start();
                mBanner.setTag(banners);
            }
        }
    }
    @Override
    public void loadDataWhenOnResume() {

    }

    @Override
    public void cancelRequestWhenOnPause() {

    }

    private void setupToolbar() {
        ToolbarInstaller toolbarInstaller = new ToolbarInstaller(this, R.id.toolbar);
        ((BaseActivity) getActivity()).setupToolbarInstaller(toolbarInstaller);
        toolbarInstaller.setOnClickListener(R.id.iv_search, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UiJumpHelper.startBluetoothActivity(v.getContext());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopAutoPlay();
    }
}
