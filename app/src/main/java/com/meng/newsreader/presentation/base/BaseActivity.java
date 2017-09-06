package com.meng.newsreader.presentation.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.subjects.ReplaySubject;

/**
 * Created by mengzhou on 7/6/17.
 */

public class BaseActivity extends AppCompatActivity {

    private final int REQUEST_INTERNET = 1;
    private ReplaySubject<Integer> source;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        source = ReplaySubject.create();
    }

    public ReplaySubject<Integer> requestPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_INTERNET);
        } else {
            source.onNext(REQUEST_INTERNET);
            source.onComplete();
        }
        return source;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            source.onNext(requestCode);
            source.onComplete();
        } else {
            source.onError(new Exception("Permission request denied!"));
        }
    }
    
}
