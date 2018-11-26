//package com.beidousat.libpartyvoice.fragment;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Toast;
//
//import com.beidousat.libisinging.R;
//import com.beidousat.libisinging.R2;
//import com.beidousat.libisinging.common.Constant;
//import com.beidousat.libisinging.common.MyUserInfo;
//import com.beidousat.libisinging.common.UiJumpHelper;
//import com.beidousat.libisinging.feature.isinging.home.adapter.SongAdapter;
//import com.beidousat.libisinging.feature.isinging.home.contracts.ISingingContracts;
//import com.beidousat.libisinging.feature.isinging.home.presenter.ISingingPresenter;
//import com.beidousat.libisinging.feature.user.favorite.contracts.IFavoriteContracts;
//import com.beidousat.libisinging.feature.user.favorite.presenter.FavoritePresenter;
//import com.beidousat.libisinging.model.ISActivityMv;
//import com.beidousat.libisinging.model.ISSong;
//import com.beidousat.libisinging.model.ISUser;
//import com.beidousat.libisinging.utils.AppPermision;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//
//import java.util.List;
//
//import javax.inject.Inject;
//
//import butterknife.BindView;
//import pl.sugl.common.base.BaseFragment;
//import pl.sugl.common.base.Injectable;
//import pl.sugl.common.widget.decoration.HorizontalDividerItemDecoration;
//
///**
// * author: Hanson
// * date:   2018/1/16
// * describe:
// */
//
//public class FmList extends BaseFragment implements Injectable, ISingingContracts.SongListView,
//        IFavoriteContracts.ActionView {
//
//    @BindView(R2.id.recyclerview)
//    RecyclerView mRecyclerView;
//    SongAdapter mSongAdapter;
//    @Inject
//    ISingingPresenter mPresenter;
//    @Inject
//    FavoritePresenter mFavoritePresenter;
//
//    @Override
//    public int getContentView() {
//        return R.layout.fragment_single_list;
//    }
//
//    @Override
//    public void initViews() {
//        mSongAdapter = new SongAdapter(R.layout.list_item_song);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        mRecyclerView.setAdapter(mSongAdapter);
//        HorizontalDividerItemDecoration horDivider = new HorizontalDividerItemDecoration.Builder(getContext())
//                .marginResId(R.dimen.divider_left_margin, R.dimen.divider_right_margin).colorResId(R.color.X2)
//                .sizeResId(R.dimen.divider).build();
//        mRecyclerView.addItemDecoration(horDivider);
//    }
//
//    @Override
//    public void setListeners() {
//        mSongAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                if (mPresenter.isNoMoreData()) {
//                    mSongAdapter.loadMoreEnd();
//                } else {
//                    mPresenter.fetchYCYHSongs(false);
//                }
//            }
//        }, mRecyclerView);
//
//        mSongAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                Object object = adapter.getItem(position);
//                if (object instanceof ISActivityMv) {
//                    ISActivityMv mv = (ISActivityMv) object;
//                    if (view.getId() == R.id.iv_singing) {
//                        UiJumpHelper.startRecording(getContext(), mv);
//                    } else if (view.getId() == R.id.iv_favorite) {
//                        if (AppPermision.checkLoginPermission(view.getContext())) {
//                            ISUser myself = MyUserInfo.getInstance().getMyself();
//                            if (mv.getCollection() == null) {
//                                mFavoritePresenter.addToFavorite(myself.getUserId(), String.valueOf(mv.getSId()),
//                                        String.valueOf(Constant.WorkType.SONG));
//                            } else {
//                                mFavoritePresenter.deleteFavorite(myself.getUserId(), mv.getCollection().getId(), String.valueOf(mv.getSId()));
//                            }
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public void initData() {
//
//    }
//
//    @Override
//    public void loadDataWhenOnResume() {
//        mPresenter.fetchYCYHSongs(true);
//    }
//
//    @Override
//    public void cancelRequestWhenOnPause() {
//        mPresenter.cancel();
//    }
//
//    @Override
//    public void onFeedBack(boolean succeed, String key, String msg) {
//        if (!succeed) {
//            mSongAdapter.loadMoreFail();
//        }
//        if (!succeed && !TextUtils.isEmpty(msg)) {
//            Toast.makeText(mAttachedAct, msg, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void refreshSongList(boolean isRefresh, List<? extends ISSong> songList) {
//        mSongAdapter.loadMoreComplete();
//        mSongAdapter.refreshSongList(isRefresh, songList);
//    }
//
//    @Override
//    public void addToFavoriteFeedback(boolean success, String id, String targetId) {
//        mSongAdapter.refreshAddedFavoriteItem(success, id, targetId);
//    }
//
//    @Override
//    public void deleteFavoriteFeedback(boolean success, String id, String targetId) {
//        mSongAdapter.refreshDeletedFavoriteItem(success, false, id, targetId);
//    }
//}
