package com.meng.newsreader.excutor;

import android.os.Handler;

/**
 * Created by mengzhou on 7/4/17.
 */

public class AsynNetRequest {

    public interface Callback {
        void onResponse(String response);
    }

    public static void get(final String url, final Callback callback) {
        final Handler handler = new Handler();
        new Thread(() -> {
            final String response = NetRequest.get(url);
            handler.post(() -> callback.onResponse(response));
        }).start();
    }

    public static void post(final String url, final String content, final Callback callback) {
        final Handler handler = new Handler();
        new Thread(() -> {
            final String response = NetRequest.post(url, content);
            handler.post(() -> callback.onResponse(response));
        }).start();
    }
}