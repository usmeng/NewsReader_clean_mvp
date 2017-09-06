package com.meng.newsreader.mvvm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.meng.newsreader.R;
import com.meng.newsreader.databinding.RecycleArticleItem2Binding;
import com.meng.newsreader.excutor.API;
import com.meng.newsreader.presentation.beans.Article;
import com.meng.newsreader.mvvm.ArticleListFragment2.ArticleListCallback;

import java.util.List;

/**
 * Created by mengzhou on 7/3/17.
 */
public class ArticleListAdapter2 extends RecyclerView.Adapter<ArticleListAdapter2.ArticleListViewHolder> {

    private static final String TAG = ArticleListAdapter2.class.getSimpleName();
    private Context mContext;

    private List<Article> mList;

    private ArticleListCallback onArticleItemClickListener;

    public ArticleListAdapter2(Context context, ArticleListCallback onArticleItemClickListener) {
        this.mContext = context;
        this.onArticleItemClickListener = onArticleItemClickListener;
    }

    public void setArticleList(List<Article> list) {
        this.mList = list;
    }

    @Override
    public ArticleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleArticleItem2Binding view = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.recycle_article_item2, parent, false);
        return new ArticleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleListViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder method called: " + position);
        if (mList == null) return;
        Article article = mList.get(position);
        holder.binding.setArticle(article);
        holder.binding.getRoot().setOnClickListener(v -> onArticleItemClickListener.showArticle(position));
        if (article.multimedia.size() > 0)
            Glide.with(mContext).load(new API().imageUrl(article.multimedia.get(0).url)).into(holder.binding.articleCover);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ArticleListViewHolder extends RecyclerView.ViewHolder {
        final RecycleArticleItem2Binding binding;

        public ArticleListViewHolder(RecycleArticleItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
