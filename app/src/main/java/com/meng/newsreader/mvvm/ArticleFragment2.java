package com.meng.newsreader.mvvm;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;


public class ArticleFragment2 extends Fragment {
    public static final String TAG = ArticleFragment2.class.getSimpleName();
    private static final String ARTICLE_ID = "article_id";

    private TextView mTitle;
    private TextView mSummary;
    private WebView mWebView;
    private View mPrv;
    private View mNext;

    public static ArticleFragment2 newInstance(int article) {
        ArticleFragment2 fragment = new ArticleFragment2();
        Bundle args = new Bundle();
        args.putInt(ARTICLE_ID, article);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "ArticleFragment onCreateView method called");
        return inflater.inflate(R.layout.fragment_article_detail2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "ArticleFragment onViewCreated method called");
        super.onViewCreated(view, savedInstanceState);
        mTitle = (TextView) view.findViewById(R.id.article_detail_title);
        mSummary = (TextView) view.findViewById(R.id.article_detail_summary);
        mWebView = (WebView) view.findViewById(R.id.article_webview);
        mWebView.setWebViewClient(new MyBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mPrv = view.findViewById(R.id.article_prev);
        mNext = view.findViewById(R.id.article_next);
        if(getArguments() != null) {
            int mArticleId = getArguments().getInt(ARTICLE_ID);
            updateArticle(mArticleId);
        }
    }

    private class MyBrowser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String request) {
            view.loadUrl(request);
            return true;
        }
    }

    private void updateArticle(final int articleId) {
        ArticleListFragment2 articleListFragment = (ArticleListFragment2)
                getActivity().getFragmentManager().findFragmentByTag(ArticleListFragment2.TAG);
        final Article article = articleListFragment.getData().get(articleId);
        if(article == null) return;

        mTitle.setText(article.snippet.substring(0, 20));
        mSummary.setText(article.pub_date + "\t");
        mWebView.loadUrl(article.web_url);


        if(articleId > 0) {
            mPrv.setOnClickListener(v -> updateArticle(articleId - 1));
        } else mPrv.setOnClickListener(null);

        if(articleId < articleListFragment.getData().size() - 1) {
            mNext.setOnClickListener(v -> updateArticle(articleId + 1));
        } else mNext.setOnClickListener(null);
    }

}