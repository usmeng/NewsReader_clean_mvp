package com.meng.newsreader.presentation.presenters;

import com.meng.newsreader.data.ApiRetrofit;
import com.meng.newsreader.presentation.views.IMainView;

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

    public void showArticles(String keyword) {
        ApiRetrofit.getInstance().searchArticle(keyword)
            .subscribeOn(Schedulers.io())
            .map(response -> response.response.docs)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(articles -> iMainView.showArticles(articles));
    }

    public void showArticle(int id) {
        iMainView.showArticle(id);
    }
}
