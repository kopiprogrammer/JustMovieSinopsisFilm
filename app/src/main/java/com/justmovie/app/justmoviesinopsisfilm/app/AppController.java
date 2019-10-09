package com.justmovie.app.justmoviesinopsisfilm.app;

import android.app.Application;
import android.text.TextUtils;

import com.justmovie.app.justmoviesinopsisfilm.util.BitmapCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private static AppController mInstance;
    private ImageLoader mImageLoader;

    BitmapCache mBitmapCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        getRequestQueue();
        if (mImageLoader == null){
            getMBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mBitmapCache);
        }
        return this.mImageLoader;
    }

    public BitmapCache getMBitmapCache (){
        if (mBitmapCache == null)
            mBitmapCache = new BitmapCache();

        return this.mBitmapCache;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
