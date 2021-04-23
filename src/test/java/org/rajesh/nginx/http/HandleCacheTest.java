package org.rajesh.nginx.http;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandleCacheTest {

    private final static String HOST = "11.myhost.com";
    private final static String URI = "https://21.nginx.com:9001/*";
    private final static String URI2 = "https://22.nginx.com:9001/*";

    private HandleCache handleCache;

    @BeforeEach
    void setUp() {
        handleCache = new HandleCache();
    }

    @Test
    @DisplayName("Test cache purge method")
    void clear() throws URISyntaxException {
        assertFalse(handleCache.clear(URI, HOST));
    }

    @Test
    @DisplayName("Validate cache purge for list of servers")
    void testClear() throws URISyntaxException {
        final List<String> uris = Arrays.asList(URI, URI2);
        final HashMap<String, Boolean> mapper = handleCache.clear(uris, HOST);
        assertAll("Validate both servers status",
                () -> assertFalse(mapper.get(URI)),
                () -> assertFalse(mapper.get(URI2)));
    }
}