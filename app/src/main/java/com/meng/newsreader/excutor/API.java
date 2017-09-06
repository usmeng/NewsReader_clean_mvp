package com.meng.newsreader.excutor;

/**
 * Created by mengzhou on 7/4/17.
 */

public class API {

    String apiKey = "api-key=c281ccb0cd1e48f18fb06ce418a1bfe3";

    String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?" + apiKey + "&q=";

    public String getUrl(String keyword) {
        return url + keyword;
    }

    public String imageUrl(String url) {
        return "https://www.nytimes.com/" + url;
    }
}
