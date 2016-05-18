package me.icxd.bookshelve.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.litepal.LitePalApplication;

/**
 * Created by HaPBoy on 5/14/16.
 */
public class MyApplication extends Application {

    private static Context context;

    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        LitePalApplication.initialize(context);
        requestQueue = Volley.newRequestQueue(context);
    }

    public static Context getContext() {
        return context;
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
