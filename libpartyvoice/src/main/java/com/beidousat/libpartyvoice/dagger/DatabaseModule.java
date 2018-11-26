package com.beidousat.libpartyvoice.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    Context mContext;

//    public DatabaseModule(Context context) {
//        mContext = context;
//    }
//
//    @Singleton
//    @Provides
//    public ISingingDatabase provideRoomDatabase() {
//        return Room.databaseBuilder(mContext, ISingingDatabase.class, "ISinging.db")
//                .build();
//    }
}
