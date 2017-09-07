package com.meng.newsreader.presentation;

import android.Manifest;
import android.app.FragmentManager;
import android.os.Bundle;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;
import com.meng.newsreader.presentation.presenters.MainPresenter;
import com.meng.newsreader.presentation.views.fragments.ArticleFragment;
import com.meng.newsreader.presentation.views.fragments.ArticleListFragment;
import com.meng.newsreader.presentation.views.IMainView;
import com.meng.newsreader.presentation.base.BaseActivity;

import java.util.List;

public class MainActivity extends BaseActivity implements
        IMainView, ArticleListFragment.ArticleListCallback {

    public static final String TAG = MainActivity.class.getSimpleName();
    FragmentManager fragmentManager;
    ArticleListFragment articleListFragment;
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(this);
        fragmentManager = getFragmentManager();

        requestPermission(Manifest.permission.INTERNET).subscribe(integer -> {
                if(savedInstanceState == null) {
                    articleListFragment = new ArticleListFragment();
                    fragmentManager.beginTransaction()
                            .add(R.id.article_fragment, articleListFragment, ArticleListFragment.TAG)
                            .commit();
                }
        });
    }

    @Override
    public void onSearchArticles(String keyword) {
        mainPresenter.showArticles(keyword);
    }

    @Override
    public void showArticles(List<Article> articles) {
        ArticleListFragment listFragment = (ArticleListFragment) fragmentManager.findFragmentByTag(ArticleListFragment.TAG);
        if(listFragment == null) return;
        listFragment.loadData(articles);
    }

    @Override
    public void onArticleItemClick(int article) {
        ArticleFragment articleDetailFragment = ArticleFragment.newInstance(article);
        fragmentManager.beginTransaction()
                .add(R.id.article_fragment, articleDetailFragment, ArticleFragment.TAG)
                .addToBackStack("").hide(articleListFragment).commit();
    }

    @Override
    public void showArticle(int articleId) {
        mainPresenter.showArticle(articleId);
    }
}
