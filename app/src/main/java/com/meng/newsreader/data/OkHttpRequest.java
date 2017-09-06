package com.meng.newsreader.data;

import com.meng.newsreader.MyApp;
import com.meng.newsreader.utils.CommonUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpRequest {

    private final OkHttpClient mClient;

    public OkHttpClient getClient() {
        return mClient;
    }

    public OkHttpRequest() {
        File httpCacheDir = new File(MyApp.getContext().getCacheDir(), "response");
        int cacheSize = 10 * 1024 * 1024;// 10 MiB
        Cache cache = new Cache(httpCacheDir, cacheSize);

//        //cookie
//        ClearableCookieJar cookieJar =
//                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApp.getContext()));

        //OkHttpClient
        mClient = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_HEADER_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(new LoggingInterceptor())
//                .addInterceptor(loggingInterceptor)//设置 Debug Log 模式
                .cache(cache)
//                .cookieJar(cookieJar)
                .build();
    }

    //header
    Interceptor REWRITE_HEADER_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
//                .addHeader("Content-Type", "application/json; charset=utf-8")
//                .addHeader("Accept-Encoding", "gzip, deflate")
//                .addHeader("Connection", "keep-alive")
//                .addHeader("Accept", "*/*")
//                .addHeader("Cookie", "add cookies here")
                .build();
        return chain.proceed(request);
    };

    //cache配置
    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        //通过 CacheControl 控制缓存数据
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);//这个是控制缓存的最大生命时间
        cacheBuilder.maxStale(365, TimeUnit.DAYS);//这个是控制缓存的过时时间
        CacheControl cacheControl = cacheBuilder.build();

        //设置拦截器
        Request request = chain.request();
        if (!CommonUtils.isNetworkAvailable(MyApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
        }

        Response originalResponse = chain.proceed(request);
        if (CommonUtils.isNetworkAvailable(MyApp.getContext())) {
            int maxAge = 0;//read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28;//tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Prama")
                    .header("Cache-Control", "poublic, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            return response;
        }
    }

    /*
    String apiKey = "api-key=c281ccb0cd1e48f18fb06ce418a1bfe3";
    String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json?" + apiKey + "&q=";
    public String getUrl(String keyword) {
        return url + keyword;
    }

    public String imageUrl(String url) {
        return "https://www.nytimes.com/" + url;
    }

    public String getString(String url) {
    	OkHttpClient client = new OkHttpClient();

    	Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public InputStream getStream(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

}
