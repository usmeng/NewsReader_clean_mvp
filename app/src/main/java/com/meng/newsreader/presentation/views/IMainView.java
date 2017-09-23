package com.meng.newsreader.presentation.views;

import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

/**
 * Created by mengzhou on 7/6/17.
 */

public interface IMainView {

    void showArticles(List<Article> articles, boolean loadMoreData);

    void showArticle(int articleId);
}
