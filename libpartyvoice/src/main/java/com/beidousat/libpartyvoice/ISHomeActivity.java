package com.beidousat.libpartyvoice;

import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beidousat.libpartyvoice.even.MainEvent;
import com.beidousat.libpartyvoice.fragment.FmHome;
import com.beidousat.libpartyvoice.widget.dialog.BottomDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import pl.sugl.common.base.BaseActivity;
import pl.sugl.common.event.NetWorkStateReceiver;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class ISHomeActivity extends BaseActivity {
    private NetWorkStateReceiver mNetWorkStateReceiver;
    private TextView tv_title,tv_content;
    private ImageView iv_list,iv_play;
    private RelativeLayout layout_mic;
    private PopupWindow popupWindow;
    private View contentView;
    private Fragment FmHome;
    private BottomDialog recongize;
//    private Banner banner;
//    private BannerViewPager bannerViewPager;

    //    private int[] mGuideRes = new int[] {
//        R.drawable.func_guide_01, R.drawable.func_guide_02, R.drawable.func_guide_03
//    };
    private int mGuideIndex = 0;
    private static final String KEY_GUIDE = "guide";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();
//        mToolbarInstaller = new ToolbarInstaller(this, R.id.toolbar);
//        setupToolbarInstaller(mToolbarInstaller);
//        mToolbarInstaller.setToolbarBackgroundResource(R.color.C2)
//                .setImage(R.id.iv_topsinging, R.drawable.top_sing)
//                .setOnClickListener(R.id.iv_topsinging, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        finish();
//                    }
//                })
//                .setOnClickListener(R.id.iv_search, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        UiJumpHelper.startBluetoothActivity(v.getContext());
//                    }
//                });
    }

    @Override
    public void initViews() {
        tv_content=findViewById(R.id.bottom_content);
        tv_title=findViewById(R.id.bottom_title);
        iv_list=findViewById(R.id.bottom_list);
        iv_list=findViewById(R.id.bottom_play);
        layout_mic=findViewById(R.id.buttom_mic);
        layout_mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recongize=new BottomDialog();
                recongize.show(getSupportFragmentManager(),"recongize");
//                popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 60);
            }
        });
        initPopWindow();
        if(FmHome==null){
            FmHome=new FmHome();
        }
        transFragment(FmHome);
//        banner = findViewById(R.id.banner);
//        bannerViewPager = findViewById(R.id.bannerViewPager);
//        bannerViewPager.setOffscreenPageLimit(3);
//        bannerViewPager.setPageMargin(10);
//        banner.setBannerAnimation(ZoomOutSlideTransformer.class);
//        if (GuidePageManager.hasNotShowed(this, KEY_GUIDE)) {
//            final GuidePage guidePage = new GuidePage.Builder(this)
//                    .setLayoutId(R.layout.pager_guide)
//                    .setPageTag(KEY_GUIDE)
//                    .builder();
//            setFullScreen();
//            ImageView view = ((ImageView)guidePage.getLayoutView().findViewById(R.id.guide_root));
//            view.setImageResource(mGuideRes[mGuideIndex++]);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mGuideIndex >= mGuideRes.length) {
//                         guidePage.cancel();
//                        quitFullScreen();
//                    } else {
//                        ((ImageView) view).setImageResource(mGuideRes[mGuideIndex++]);
//                    }
//                }
//            });
//            guidePage.apply();
//        }
    }


    @Override
    public void setListener() {
//        banner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                if (banner.getTag() != null) {
//                    List<ISBanner> banners = (List<ISBanner>) banner.getTag();
////                    UiJumpHelper.startWebView(getContext(), banners.get(position).getJumpLink());
//                }
//            }
//        });
    }

    @Override
    public void initData() {
        if (mNetWorkStateReceiver == null) {
            mNetWorkStateReceiver = new NetWorkStateReceiver();
        }
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetWorkStateReceiver, filter);
//        EventBusHelper.register(this);
    }

    @Override
    public void loadDataWhenOnResume() {

    }

    @Override
    public void cancelLoadingRequest() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetWorkStateReceiver);
//        EventBusHelper.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainEvent(MainEvent event) {

    }


    private long mExitTime = 0;
    private static final int MAX_EXIT_INTERVAL = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

//    public void doExitApp() {
//        if ((System.currentTimeMillis() - mExitTime) > MAX_EXIT_INTERVAL) {
//            Toast.makeText(this, R.string.exit_application, Toast.LENGTH_SHORT).show();
//            mExitTime = System.currentTimeMillis();
//        } else {
//            RongIM.getInstance().disconnect();
//            MyActivityManager.getInstance().appExit();
//        }
//    }


    private void setFullScreen() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void quitFullScreen() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
    private void initPopWindow() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_recognize, null);


        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画，指定刚才定义的style
        popupWindow.setAnimationStyle(R.style.pop_anim_style);



    }
    private void transFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content,fragment);
        transaction.addToBackStack(null).commit();
    }
}
