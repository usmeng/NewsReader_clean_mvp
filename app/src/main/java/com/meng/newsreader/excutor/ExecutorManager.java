package com.meng.newsreader.excutor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by mengzhou on 7/4/17.
 */

public class ExecutorManager {

    private static final String TAG = "ExecutorManager";

    private static ExecutorManager instance = new ExecutorManager();
    ExecutorService executorService;

    private ExecutorManager() {
        executorService = Executors.newFixedThreadPool(4);
    }

    public static ExecutorManager getInstance() {
        return instance;
    }

    public String get(final String keyword) {

        Future<String> submit = executorService.submit(new HttpRequestTask(new API().getUrl(keyword)));
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return "";
    }

    public Bitmap load(String url) {
        Log.i(TAG, "-------url: " + url);
        Future<InputStream> submit = executorService.submit(new ImageFetcher(url));
        try {
            InputStream inputStream = submit.get();
            if(inputStream != null)
            return BitmapFactory.decodeStream(inputStream);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class ImageFetcher implements Callable<InputStream> {

        private String url;
        public ImageFetcher(String url) {
            this.url = url;
        }

        @Override
        public InputStream call() throws Exception {
            return NetRequest.getStream(url);
        }
    }

    public class HttpRequestTask implements Callable<String> {

        private String method;
        private String url;

        public HttpRequestTask(String url) {
            this.url = url;
            method = "GET";
        }
        @Override
        public String call() throws Exception {
            if("GET".equals(method))
                return NetRequest.get(url);
            return "";
        }
    }

    public void shutdown() {
        if(executorService != null) executorService.shutdown();
    }

}
