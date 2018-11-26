package pl.sugl.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * author: Hanson
 * date:   2018/3/19
 * describe:
 */

public abstract class DisableLazyLoadFragment extends BaseFragment {
    private boolean isInit = false;
    protected boolean isLoad = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
        handleLoadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        handleLoadData();
    }

    @Override
    final public void loadDataWhenOnResume() {

    }

    @Override
    final public void cancelRequestWhenOnPause() {

    }

    public abstract void loadDataWhenOnVisible();

    public abstract void cancelLoadRequest();

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void handleLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            loadDataWhenOnVisible();
            isLoad = true;
        } else {
            if (isLoad) {
                cancelLoadRequest();
            }
        }
    }
}
