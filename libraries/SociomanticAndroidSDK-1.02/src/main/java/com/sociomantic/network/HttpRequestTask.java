package com.sociomantic.network;

import com.sociomantic.SociomanticConstants;
import com.sociomantic.utility.SCMLog;
import com.sociomantic.utility.Validate;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created with IntelliJ IDEA.
 * User: John Nilsen
 * Date: 03.12.13
 * Time: 10:11
 */
public class HttpRequestTask implements Runnable{

    HttpURLConnection client;
    SCMLog log = new SCMLog(this.getClass());

    public HttpRequestTask(HttpURLConnection client) {
        Validate.notNull(client);
        Validate.notNull(client.getURL());
        this.client = client;
    }

    @Override
    public void run() {
        try{
            log.d("sending request: " + client.getURL().toString());
            client.setConnectTimeout(SociomanticConstants.CONNECTION_TIMEOUT);
            client.connect();
            log.d("reply from server: " + client.getResponseCode() + ": " + client.getResponseMessage());
        } catch(IOException e){
            log.e("IOException when sending request!", e);
        } finally{
            if(client != null){
                client.disconnect();
            }
        }
    }
}
