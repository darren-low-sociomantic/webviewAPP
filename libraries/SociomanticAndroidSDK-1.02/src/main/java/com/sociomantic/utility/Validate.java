package com.sociomantic.utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by John Nilsen on 06.12.13.
 */
public final class Validate {

    private Validate(){

    }

    public static void notNull(Object object){
        if(object == null){
            throw new NullPointerException("Object was null!"); //NOSONAR
        }
    }

    public static void urlFormatValid(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static <T> void notEmpty(Collection<T> collection) {
        if(collection.isEmpty()){
            throw new IllegalArgumentException("Collection was empty!");
        }
    }
}
