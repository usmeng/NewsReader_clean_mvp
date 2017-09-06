package com.meng.newsreader.data;

import com.meng.newsreader.data.response.SearchArticleResponse;

import java.io.InputStream;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mengzhou on 7/5/17.
 */

public interface RestAPI {

    // https://hackernoon.com/restful-api-designing-guidelines-the-best-practices-60e1d954e7c9
    // http://blog.restcase.com/5-basic-rest-api-design-guidelines/

    // API search engine:
    // 1. http://apis.io
    // 2. https://www.programmableweb.com/

    // zhihu: https://www.zhihu.com/question/39479153

    /* Instagram key and secret
    CLIENT ID	8d0a402b1d1d4dbbb83010743cb87f04
    CLIENT STATUS	Sandbox Mode
    Client Secret 7cd284b3c7264effb149c92691ec96e8

    token
    */

    // login: post -> domain/oauth2/access_token/
    //                domain/login/access
    //
    // Get Message:   domain/messages/message_id  filter, sort, grand
    //                domain/user/id/messages
    //                domain/device_info

    // Response: { status: okay, false
    //             message:
    //             result: {
    //                 meta: {
    //                 }
    //                 messages: {
    //                     message:
    //                 }
    //             }}
    /**
     *
     */

    // https://api.nytimes.com/svc/search/v2/articlesearch.json
    // article search: c281ccb0cd1e48f18fb06ce418a1bfe3
    // Top    Stories: c281ccb0cd1e48f18fb06ce418a1bfe3
    // String BASE_URL = "https://www.nytimes.com/";

    String apiKey = "articlesearch.json?api-key=c281ccb0cd1e48f18fb06ce418a1bfe3&";
    String BASE_SEARCH_URL = "https://api.nytimes.com/svc/search/v2/";

    @GET(apiKey)
    Observable<SearchArticleResponse> searchArticle(@Query("q") String keyword);

    @GET("{path}")
    Observable<InputStream> fetchImage(@Path("path") String url);
}