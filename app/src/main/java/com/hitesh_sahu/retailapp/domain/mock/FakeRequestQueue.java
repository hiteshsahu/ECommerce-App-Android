//package com.hitesh_sahu.retailapp.domain.mock;
//
//import android.content.Context;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Cache;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.BasicNetwork;
//import com.android.volley.toolbox.NoCache;
//
////To use FakeHttpStack, you just have to pass it to your RequestQueue. I override RequestQueue too.
//
//public class FakeRequestQueue extends RequestQueue {
//    public FakeRequestQueue(Context context) {
//        super(new NoCache(), new BasicNetwork(new FakeHttpStack(context)));
//        start();
//    }
//
//    @Override
//    public void start() {
//        System.out.println("request start");
//        super.start();
//    }
//
//    @Override
//    public void stop() {
//        System.out.println("request stop");
//        super.stop();
//    }
//
//    @Override
//    public Cache getCache() {
//        System.out.println("request start");
//        return super.getCache();
//    }
//
//    @Override
//    public void cancelAll(RequestFilter filter) {
//        System.out.println("Request cancel with filter " + filter);
//        super.cancelAll(filter);
//    }
//
//    @Override
//    public void cancelAll(Object tag) {
//        System.out.println("Request cancel with tag " + tag);
//        super.cancelAll(tag);
//    }
//
//    @Override
//    public Request add(Request request) {
//        System.out.println("Note: FakeRequestQueue is used");
//        System.out.println("New request " + request.getUrl()
//                + " is added with priority " + request.getPriority());
//        try {
//            if (request.getBody() == null) {
//                System.out.println("body is null");
//            } else {
//                System.out.println("Body:" + new String(request.getBody()));
//            }
//        } catch (AuthFailureError e) {
//            // cannot do anything
//        }
//        return super.add(request);
//    }
//}
