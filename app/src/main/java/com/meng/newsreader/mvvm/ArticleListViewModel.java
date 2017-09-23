package com.meng.newsreader.mvvm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.meng.newsreader.data.ApiRetrofit;
import com.meng.newsreader.presentation.beans.Article;
import com.meng.newsreader.presentation.beans.QueryMap;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by mengzhou on 7/7/17.
 */
public class ArticleListViewModel extends BaseObservable{

    @Bindable
    public Observable<List<Article>> articles;

    public Observable<List<Article>> searchArticle(String keyword) {
        articles = ApiRetrofit.getInstance().searchArticles(QueryMap.initValue(keyword));
        return articles;
    }

}
