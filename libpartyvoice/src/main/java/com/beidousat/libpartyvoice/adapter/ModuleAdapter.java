package com.beidousat.libpartyvoice.adapter;

import android.support.annotation.Nullable;


import com.beidousat.libpartyvoice.R;
import com.beidousat.libpartyvoice.model.ISListModule;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * author: Hanson
 * date:   2018/1/16
 * describe:
 */

public class ModuleAdapter extends BaseQuickAdapter<ISListModule, BaseViewHolder> {

    public ModuleAdapter(int layoutResId, @Nullable List<ISListModule> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ISListModule item) {
        helper.setImageResource(R.id.iv_img, item.getDrawableRes());
        helper.setText(R.id.tv_song_name, item.getTitle());
        helper.setText(R.id.tv_size, item.getDescribe());
    }
}
