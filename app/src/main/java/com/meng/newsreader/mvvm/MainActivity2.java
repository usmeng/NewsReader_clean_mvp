package com.meng.newsreader.mvvm;

import android.Manifest;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import com.meng.newsreader.R;
import com.meng.newsreader.presentation.base.BaseActivity;

public class MainActivity2 extends BaseActivity implements ArticleListFragment2.ArticleListCallback {

    public static final String TAG = MainActivity2.class.getSimpleName();
    FragmentManager fragmentManager;
    ArticleListFragment2 articleListFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        requestPermission(Manifest.permission.INTERNET).subscribe(
                integer -> {
                if(savedInstanceState == null) {
                    articleListFragment2 = new ArticleListFragment2();
                    fragmentManager.beginTransaction()
                            .add(R.id.article_fragment, articleListFragment2, ArticleListFragment2.TAG)
                            .commit();
                }
        });
    }

    @Override
    public void showArticle(int articleId) {
        ArticleListFragment2 articleListFragment = (ArticleListFragment2)
                getFragmentManager().findFragmentByTag(ArticleListFragment2.TAG);
        Toast.makeText(this, "articleListFragment is " + articleListFragment, Toast.LENGTH_LONG).show();

        ArticleFragment2 articleDetailFragment = ArticleFragment2.newInstance(articleId);
        fragmentManager.beginTransaction()
                .add(R.id.article_fragment, articleDetailFragment, ArticleFragment2.TAG)
                .addToBackStack("").hide(articleListFragment2).commit();
    }
}
