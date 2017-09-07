package com.meng.newsreader.presentation.views.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

/**
 * Created by mengzhou on 7/3/17.
 */
public class ArticleListFragment extends Fragment {

    public static final String TAG = ArticleListFragment.class.getSimpleName();
    private ArticleListCallback articleListCallback;
    private RecyclerView mRecyclerView;
    private ArticleListAdapter mArticleListAdapter;
    private List<Article> mData;
    private EditText mEditText;
    private Button mSearchButton;

    public interface ArticleListCallback {
         void onSearchArticles(String keyword);
         void onArticleItemClick(int id);
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

        mRecyclerView = view.findViewById(R.id.article_recycleview);
        mEditText = view.findViewById(R.id.search_hint);
        mSearchButton = view.findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(v -> {
            String keyword = mEditText.getText().toString();
            if(keyword.length() > 0)
            articleListCallback.onSearchArticles(keyword);
        });

        mArticleListAdapter = new ArticleListAdapter(getContext());
        mArticleListAdapter.setArticleList(mData);
        mArticleListAdapter.setOnArticleItemClickListener(articleListCallback);

        mRecyclerView.setAdapter(mArticleListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void loadData(List<Article> articles) {
        mData = articles;
        if(mArticleListAdapter == null) return;
        mArticleListAdapter.setArticleList(mData);
        mArticleListAdapter.notifyDataSetChanged();
    }

    public List<Article> getData() {
        return mData;
    }

    class FetchData {

        /**
         * get data by retrofit
         *//*
        public void initData(String keyword){
            ApiRetrofit.getInstance().searchArticle(keyword)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<SearchArticleResponse, List<Article>>() {
                        @Override
                        public List<Article> apply(SearchArticleResponse response) throws Exception {
                            return response.response.docs;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Article>>() {
                        @Override
                        public void accept(List<Article> articles) throws Exception {
                            mData = articles;
                            if(mArticleListAdapter == null) return;
                            mArticleListAdapter.setArticleList(mData);
                            mArticleListAdapter.notifyDataSetChanged();
                        }
                    });
        }

        *//**
         * get data by Executor
         *//*
        public void initData1() {
            ExecutorManager dataManager = ExecutorManager.getInstance();
            SearchArticleResponse responseObject = new Gson().fromJson(dataManager.get("california"), SearchArticleResponse.class);
            if(responseObject == null || mArticleListAdapter == null) {
                Toast.makeText(getContext(), "responseObject get null", Toast.LENGTH_LONG).show();
            } else {
                mData = responseObject.response.docs;
                Log.i(TAG, "article numbers: " + mData.size());
                mArticleListAdapter.setArticleList(mData);
                mArticleListAdapter.notifyDataSetChanged();
            }
    //        dataManager.shutdown();
        }

        *//**
         * get data by RxJava
         *//*
        public void initData2() {
            Repository.getInstance().searchArticles("california")
                    .subscribeOn(Schedulers.io())
                    .map(new Function<SearchArticleResponse, List<Article>>() {
                        @Override
                        public List<Article> apply(SearchArticleResponse response) throws Exception {
                            return response.response.docs;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Article>>() {
                        @Override
                        public void accept(List<Article> articles) throws Exception {
                            mData = articles;
                            if(mArticleListAdapter == null) return;
                            mArticleListAdapter.setArticleList(mData);
                            mArticleListAdapter.notifyDataSetChanged();
                        }
                    });
        }

        *//**
         * get data by work thread and callback
         *//*
        public void initData3() {
            AsynNetRequest.get(new API().getUrl("california"), new AsynNetRequest.Callback() {
                @Override
                public void onResponse(String responseString) {
                    SearchArticleResponse responseObject = new Gson().fromJson(responseString, SearchArticleResponse.class);
                    if(responseObject == null || mArticleListAdapter == null) {
                        Toast.makeText(getContext(), "responseObject get null", Toast.LENGTH_LONG).show();
                    } else {
                        mData = responseObject.response.docs;
                        Log.i(TAG, "article numbers: " + mData.size());
                        mArticleListAdapter.setArticleList(mData);
                        mArticleListAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            ExecutorManager.getInstance().shutdown();
            Log.i(TAG, "ArticleListFragment onDestroy() method called");
        }*/
    }

}
