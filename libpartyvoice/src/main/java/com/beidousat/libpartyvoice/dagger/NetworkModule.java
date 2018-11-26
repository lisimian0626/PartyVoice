package com.beidousat.libpartyvoice.dagger;



import com.beidousat.libpartyvoice.BuildConfig;
import com.beidousat.libpartyvoice.bussiness.ApiService;
import com.beidousat.libpartyvoice.net.CommonHeaderInterceptor;
import com.beidousat.libpartyvoice.net.SSLSocketFactoryUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.sugl.common.network.UpLoadProgressInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author: Hanson
 * date:   2018/1/17
 * describe:
 */

@Module
public class NetworkModule {

    @Singleton
    @Provides
    public OkHttpClient provideOKHttpClient(UploadProgressHandler handler) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .addNetworkInterceptor(new UpLoadProgressInterceptor(handler))
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new CommonHeaderInterceptor());

        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(loggingInterceptor);
//                    .addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public ApiService createDefaultApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    public UploadProgressHandler provideUploadProgressHandler() {
        return new UploadProgressHandler();
    }
}
