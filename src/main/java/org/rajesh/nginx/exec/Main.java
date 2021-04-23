package org.rajesh.nginx.exec;

import org.rajesh.nginx.http.HandleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rajesh Dwivedi
 */
public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws URISyntaxException {
        final HandleCache handleCache = new HandleCache();
        final String HOST = "11.myhost.com";
        final String URI = "https://21.nginx.com:9001/*";
        final String URI2 = "https://22.nginx.com:9001/*";
        LOGGER.info("Is cache cleared ? {}", handleCache.clear(URI, HOST));

        final ArrayList<String> uris = new ArrayList<>();
        uris.add(URI);
        uris.add(URI2);
        final HashMap<String, Boolean> mapper = handleCache.clear(uris, HOST);
        mapper.forEach((server, isSuccess) -> System.out.println( server + ' ' + isSuccess));
    }
}
