package com.meng.newsreader.mvvm;


import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.meng.newsreader.R;
import com.meng.newsreader.databinding.FragmentArticleList2Binding;
import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by mengzhou on 7/3/17.
 */
public class ArticleListFragment2 extends Fragment {

    public static final String TAG = ArticleListFragment2.class.getSimpleName();
    private ArticleListCallback articleListCallback;
    private ArticleListAdapter2 mArticleListAdapter;
    private EditText mEditText;
    private Button mSearchButton;
    private List<Article> mList;;

    public interface ArticleListCallback {
        void showArticle(int id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ArticleListCallback) {
            articleListCallback = (ArticleListCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ArticleListCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentArticleList2Binding view = DataBindingUtil.inflate(inflater, R.layout.fragment_article_list2, container, false);
        mArticleListAdapter = new ArticleListAdapter2(getContext(), articleListCallback);
        view.articleRecycleview.setAdapter(mArticleListAdapter);
        view.articleRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        return view.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "ArticleListFragment onViewCreated() method called");
        ArticleListViewModel listViewModel = new ArticleListViewModel();
        subscribeUI(listViewModel);
    }

    private void subscribeUI(ArticleListViewModel listViewModel) {
        listViewModel.searchArticle("california")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articles -> {
                    mList = articles;
                    mArticleListAdapter.setArticleList(articles);
                    mArticleListAdapter.notifyDataSetChanged();
                });
    }

    public List<Article> getData() {
        return mList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, TAG + " onDestory() method called");
    }
}
