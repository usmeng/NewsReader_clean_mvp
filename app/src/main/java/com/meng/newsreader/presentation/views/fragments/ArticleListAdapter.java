package com.meng.newsreader.presentation.views.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meng.newsreader.R;
import com.meng.newsreader.data.ApiRetrofit;
import com.meng.newsreader.presentation.beans.Article;
import com.meng.newsreader.presentation.views.fragments.ArticleListFragment.ArticleListCallback;

import java.util.List;

/**
 * Created by mengzhou on 7/3/17.
 */
public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder> {

    private static final String TAG = ArticleListAdapter.class.getSimpleName();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Article> mList;

    private ArticleListCallback onArticleItemClickListener;

    public void setOnArticleItemClickListener(ArticleListCallback onArticleItemClickListener) {
        this.onArticleItemClickListener = onArticleItemClickListener;
    }

    public ArticleListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    public void setArticleList(List<Article> list) {
        this.mList = list;
    }

    @Override
    public ArticleListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder method called");
        View view = mLayoutInflater.inflate(R.layout.recycle_article_item, null);
        return new ArticleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleListViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder method called: " + position);
        if(mList == null) return;

        holder.view.setOnClickListener(v -> {
                if(onArticleItemClickListener != null)
                    onArticleItemClickListener.onArticleItemClick(position);
            });
        Article article = mList.get(position);
        holder.title.setText(article.snippet);
//        holder.summary.setText(article.summary.length() != 0 ? article.summary : article.lead_paragraph);

        holder.info.setText(article.source);

        if(article.multimedia.size() > 0) {
            String url = ApiRetrofit.imageUrl(article.multimedia.get(0).url);
            Glide.with(mContext).load(url).into(holder.cover);
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ArticleListViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView title;
        public TextView summary;
        public ImageView cover;
        public TextView info;

        public ArticleListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = itemView.findViewById(R.id.article_tittle);
            summary = itemView.findViewById(R.id.article_abstract);
            cover = itemView.findViewById(R.id.article_cover);
            info = itemView.findViewById(R.id.article_info);
        }
    }
}
