package com.mrb.keepitgreen;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Arafin on 7/25/2017.
 */

public class RequestSingleton {

//    Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
//    Network network = new BasicNetwork(new HurlStack());
//    requestQueue = new RequestQueue(cache, network);
//    requestQueue.start();

    private static RequestSingleton requestInstance;
    private static Context context;

    private RequestQueue requestQueue;

    private RequestSingleton(Context myCtx) {
        context = myCtx;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized RequestSingleton getRequestInstance(Context context) {
        if(requestInstance == null) {
            requestInstance = new RequestSingleton(context);
        }
        return requestInstance;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        requestQueue.add(request);
    }
}
