package com.meng.newsreader.data.response;

import com.meng.newsreader.presentation.beans.Article;

import java.util.List;

/**
 * Created by mengzhou on 7/4/17.
 */

public class SearchArticleResponse {
    public Docs response;
    public String status;
    public String copyright;

    public class Docs {
        public List<Article> docs;
    }
}
