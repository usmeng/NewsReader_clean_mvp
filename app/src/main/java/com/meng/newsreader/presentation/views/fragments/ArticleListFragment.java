package com.meng.newsreader.presentation.views.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

/**
 * Created by mengzhou on 7/3/17.
 */
public class ArticleListFragment extends Fragment {

    public static final String TAG = ArticleListFragment.class.getSimpleName();
    private ArticleListCallback articleListCallback;
    private EndlessScrollListener listener;
    private ArticleListAdapter mArticleListAdapter;
    private List<Article> mData;
    private static final int SPAN_COUNT = 2;

    public interface ArticleListCallback {
        void onArticleItemClick(int id);
        void onLoadMorePage(int page);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ArticleListCallback) {
            articleListCallback = (ArticleListCallback) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ArticleListCallback");
        }
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "ArticleListFragment onCreateView() method called");
        return inflater.inflate(R.layout.fragment_article_list, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "ArticleListFragment onViewCreated() method called");

        RecyclerView mRecyclerView = view.findViewById(R.id.article_recycleview);
        mArticleListAdapter = new ArticleListAdapter(getContext());
        mArticleListAdapter.setArticleList(mData);
        mArticleListAdapter.setOnArticleItemClickListener(articleListCallback);

        mRecyclerView.setAdapter(mArticleListAdapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        listener = new EndlessScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                articleListCallback.onLoadMorePage(page);
            }
        };
        mRecyclerView.addOnScrollListener(listener);
    }


    public void resetData(List<Article> articles) {
        mData = articles;
        listener.reset();
        mArticleListAdapter.setArticleList(mData);
        mArticleListAdapter.notifyDataSetChanged();
    }

    public void loadData(List<Article> articles) {
        int curSize = mArticleListAdapter.getItemCount();
        mData.addAll(articles);
        mArticleListAdapter.setArticleList(mData);
//        mArticleListAdapter.notifyItemRangeInserted(curSize, mData.size() - 1);
    }

    public List<Article> getData() {
        return mData;
    }

}
