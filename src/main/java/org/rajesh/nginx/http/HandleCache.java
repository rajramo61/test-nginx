package org.rajesh.nginx.http;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

/**
 * @author Rajesh Dwivedi
 */
public class HandleCache {

    public static final String HOST = "host";
    public static final Logger LOGGER = LoggerFactory.getLogger(HandleCache.class);

    public boolean clear(final String uri, final String host) throws URISyntaxException {
        final HttpPurge httpPurge = getHttpPurge(uri, host);

        try (CloseableHttpClient httpclient = HttpClients.createMinimal()) {
            return executePurge(host, httpclient, uri);
        } catch (IOException exception) {
            LOGGER.error("Exception during cache eviction", exception);
        }
        return false;
    }

    public HashMap<String, Boolean> clear(final List<String> uris, final String host) throws URISyntaxException {
        final HashMap<String, Boolean> cachePurgeMapper = new HashMap<>();
        try (CloseableHttpClient httpclient = HttpClients.createMinimal()) {
            for (String uri : uris) {
                cachePurgeMapper.put(uri, executePurge(host, httpclient, uri));
            }
        } catch (IOException exception) {
            LOGGER.error("EXCEPTION_DURING_CACHE_EVICTION", exception);
        }
        return cachePurgeMapper;
    }

    private boolean executePurge(String host, CloseableHttpClient httpclient, String uri) throws URISyntaxException, IOException {
        final HttpPurge httpPurge = getHttpPurge(uri, host);
        try{
            final CloseableHttpResponse response = httpclient.execute(httpPurge);
            LOGGER.debug("URI {} and Response code {}", uri, response.getStatusLine());
            return response.getStatusLine().getStatusCode() == HTTP_NO_CONTENT;
        } catch (NoHttpResponseException | HttpHostConnectException | IllegalArgumentException exception) {
            LOGGER.debug("Cache purge failed for NGINX server {} for host header {}", uri, host);
            LOGGER.error("ERROR ", exception);
            return false;
        }
    }

    private HttpPurge getHttpPurge(String uri, String host) throws URISyntaxException {
        final HttpPurge httpPurge = new HttpPurge(uri);
        BasicHeader header = new BasicHeader(HOST, host);
        httpPurge.setHeader(header);
        return httpPurge;
    }
}
