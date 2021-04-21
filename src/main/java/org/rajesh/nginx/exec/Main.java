package org.rajesh.nginx.exec;

import org.rajesh.nginx.http.HandleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author Rajesh Dwivedi
 * Date: 4/16/21
 */
public class Main {
    public static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws URISyntaxException {
        final HandleCache handleCache = new HandleCache();
        final String HOST = "";
        final String URI = "";
        LOGGER.info("Is cache cleared ? {}", handleCache.clear(URI, HOST));

        final ArrayList<String> uris = new ArrayList<>();
        uris.add(URI);
        uris.add(URI);
        handleCache.clear(uris, HOST);
    }
}
