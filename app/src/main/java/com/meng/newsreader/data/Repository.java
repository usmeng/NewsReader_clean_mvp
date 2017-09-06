package com.meng.newsreader.data;


import com.google.gson.Gson;
import com.meng.newsreader.data.response.SearchArticleResponse;
import com.meng.newsreader.excutor.API;
import com.meng.newsreader.excutor.NetRequest;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;

/**
 * Created by mengzhou on 7/4/17.
 */

public class Repository {
    private static Repository instance = new Repository();

    private Repository(){}

    public static Repository getInstance() {
        return instance;
    }

    public Flowable<SearchArticleResponse> searchArticles(final String keyword) {
        return Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                String json = NetRequest.get(new API().getUrl(keyword));
                e.onNext(json);
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER).map(new Function<String, SearchArticleResponse>() {
            @Override
            public SearchArticleResponse apply(String s) throws Exception {
                return new Gson().fromJson(s, SearchArticleResponse.class);
            }
        });
    }

}
