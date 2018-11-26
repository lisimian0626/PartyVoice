package com.beidousat.libpartyvoice.model.factory;

import android.content.res.TypedArray;
import android.view.View;

import com.beidousat.libpartyvoice.R;
import com.beidousat.libpartyvoice.model.ISListModule;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import pl.sugl.common.utils.ISingContextHolder;

/**
 * author: Hanson
 * date:   2018/1/16
 * describe:
 */

public class IModeFactory {
    public static List<ISListModule> createModules() {
        List<ISListModule> listCategories = new ArrayList<>();
        listCategories.add(new ISListModule(R.id.iparty_study,
                R.drawable.home_icon_quotation,
                ISingContextHolder.getContext().getString(R.string.iparty_study_title),
                ISingContextHolder.getContext().getString(R.string.iparty_study_description)));
        listCategories.add(new ISListModule(R.id.iparty_lesson,
                R.drawable.home_icon_course,
                ISingContextHolder.getContext().getString(R.string.iparty_lesson_title),
                ISingContextHolder.getContext().getString(R.string.iparty_lesson_description)));

        return listCategories;
    }

    public static List<ISListModule> createModules2() {
        List<ISListModule> listCategories = new ArrayList<>();
        listCategories.add(new ISListModule(R.id.iparty_history,
                R.drawable.home_icon_history,
                ISingContextHolder.getContext().getString(R.string.iparty_history_title)));

        listCategories.add(new ISListModule(R.id.iparty_story,
                R.drawable.home_icon_story,
                ISingContextHolder.getContext().getString(R.string.iparty_story_title)));

        listCategories.add(new ISListModule(R.id.iparty_music,
                R.drawable.home_icon_song,
                ISingContextHolder.getContext().getString(R.string.iparty_music_title)));

        listCategories.add(new ISListModule(R.id.iparty_media,
                R.drawable.home_icon_bank,
                ISingContextHolder.getContext().getString(R.string.iparty_media_title)));

        listCategories.add(new ISListModule(R.id.iparty_collect,
                R.drawable.home_icon_care,
                ISingContextHolder.getContext().getString(R.string.iparty_collect_title)));
        return listCategories;
    }
}
