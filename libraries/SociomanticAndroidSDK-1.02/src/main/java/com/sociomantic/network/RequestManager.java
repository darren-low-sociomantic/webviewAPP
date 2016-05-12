package com.sociomantic.network;

import com.sociomantic.AdvertisingIdRetriever;

/**
 * Encapsulates RequestSubmitter with a non-final class. Sociomantic class should use this and not
 * RequestSubmitter directly.
 * Created by John Nilsen on 12.12.13 at Sociomantic Labs.
 */
public class RequestManager {

    private RequestSubmitter submitter = RequestSubmitter.getInstance();

    public void submit(HttpRequestTask request){
        submitter.submitRequest(request);
    }

    public void submit(AdvertisingIdRetriever advertisingIdRetriever) {
        submitter.submitRequest(advertisingIdRetriever);
    }
}
