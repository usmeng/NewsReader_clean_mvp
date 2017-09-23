package com.meng.newsreader.presentation.presenters;

import com.meng.newsreader.data.ApiRetrofit;
import com.meng.newsreader.presentation.views.IMainView;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mengzhou on 7/7/17.
 */
public class MainPresenter {

    private IMainView iMainView;

    public MainPresenter(IMainView iMainView) {
        this.iMainView = iMainView;
    }

    public void showArticles(Map<String, String> map, final boolean loadMoreData) {
        ApiRetrofit.getInstance().searchArticles(map)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(articles -> iMainView.showArticles(articles, loadMoreData));
    }

    public void showArticle(int id) {
        iMainView.showArticle(id);
    }

}