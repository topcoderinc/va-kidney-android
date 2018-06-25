package com.topcoder.vakidney.api;

import android.content.Context;

import com.github.mikephil.charting.utils.Utils;
import com.topcoder.vakidney.BuildConfig;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by afrisalyp on 16/03/2018.
 * This class implement rest client manager to access NDB API
 */

public class NDBRestClient {
    private static final int cacheSize = 20 * 1024 * 1024; // 20 MB
    private static final int maxAge = 86400; //Cache maximum age in seconds

    private static OkHttpClient okHttpClient;

    private static final String REST_API_URL = BuildConfig.NDB_BASE_URL;

    private static Retrofit sRetrofit;

    private static Retrofit newInstance(Context context) {
        File httpCacheDirectory = new File(context.getCacheDir(), "ndbresponses");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new ResponseCacheInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(REST_API_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

    public static <T> T getService(Class<T> serviceClass, Context context) {
        if (sRetrofit == null)
            sRetrofit = newInstance(context);
        return sRetrofit.create(serviceClass);
    }

    /**
     * Interceptor to cache data and maintain it for a day.
     * <p>
     * If the same network request is sent within a day,
     * the response is retrieved from cache.
     */
    private static class ResponseCacheInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        }
    }
}
