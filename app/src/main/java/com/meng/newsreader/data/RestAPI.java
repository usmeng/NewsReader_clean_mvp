package com.meng.newsreader.data;

import com.meng.newsreader.data.response.SearchArticleResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by mengzhou on 7/5/17.
 */
public interface RestAPI {

    // https://api.nytimes.com/svc/search/v2/articlesearch.json
    // article search: c281ccb0cd1e48f18fb06ce418a1bfe3
    // Top    Stories: c281ccb0cd1e48f18fb06ce418a1bfe3
    // String BASE_URL = "https://www.nytimes.com/";

    String apiKey = "c281ccb0cd1e48f18fb06ce418a1bfe3";
    String BASE_SEARCH_URL = "https://api.nytimes.com/svc/search/v2/";

    @GET("articlesearch.json")
    Observable<SearchArticleResponse> searchArticle(@QueryMap Map<String, String> values);

}