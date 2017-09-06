package com.meng.newsreader.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.meng.newsreader.data.response.SearchArticleResponse;
import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mengzhou on 7/5/17.
 */

public class ApiRetrofit {

    private static ApiRetrofit mApiRetrofit;
    public RestAPI mApi;

    private ApiRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();

        mApi = new Retrofit.Builder()
                .baseUrl(RestAPI.BASE_SEARCH_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(RestAPI.class);
    }

    public OkHttpClient getClient() {
        return new OkHttpClient.Builder().build();
    }

    public Observable<List<Article>> searchArticles(String keyword) {
        return mApi.searchArticle(keyword).subscribeOn(Schedulers.io())
                .map(response -> response.response.docs);
    }

    public Observable<SearchArticleResponse> searchArticle(String keyword) {
        return mApi.searchArticle(keyword);
    }

    public static ApiRetrofit getInstance() {
        if(mApiRetrofit == null) {
            synchronized (ApiRetrofit.class) {
                mApiRetrofit = new ApiRetrofit();
            }
        }
        return mApiRetrofit;
    }
}
