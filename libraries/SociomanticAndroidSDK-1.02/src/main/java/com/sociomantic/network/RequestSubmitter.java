package com.sociomantic.network;

import com.sociomantic.AdvertisingIdRetriever;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: John Nilsen
 * Date: 03.12.13
 * Time: 11:12
 */
public final class RequestSubmitter {

    private static volatile RequestSubmitter instance;

    private static ExecutorService service;

    private RequestSubmitter(){
    }

    public static RequestSubmitter getInstance(){
        if(instance == null){
            //for thread safety
            synchronized (RequestSubmitter.class){
                if(instance == null){
                    instance = new RequestSubmitter();
                    service = Executors.newSingleThreadExecutor();
                }
            }
        }
        return instance;
    }

    /**
     * By default this class has a Single Thread Executor
     * @param service
     */
    public void setService(ExecutorService service) {
        RequestSubmitter.service = service;
    }

    public void submitRequest(HttpRequestTask request){
        service.submit(request);
    }

    public void submitRequest(AdvertisingIdRetriever advertisingIdRetriever) {
        service.submit(advertisingIdRetriever);
    }
}
