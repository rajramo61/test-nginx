package org.rajesh.nginx.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_NO_CONTENT;

/**
 * @author Rajesh Dwivedi
 * Date: 4/15/21
 */
public class HandleCache {

    public static final String HOST = "host";
    public static final Logger LOGGER = LoggerFactory.getLogger(HandleCache.class);

    public boolean clear(final String uri, final String host) throws URISyntaxException {

        final HttpPurge httpPurge = new HttpPurge(uri);
        BasicHeader header = new BasicHeader(HOST, host);
        httpPurge.setHeader(header);

        try (CloseableHttpClient httpclient = HttpClients.createMinimal()) {
            final CloseableHttpResponse response = httpclient.execute(httpPurge);
            LOGGER.debug("Response details {}", response.getStatusLine());
            return response.getStatusLine().getStatusCode() == HTTP_NO_CONTENT;
        } catch (IOException exception) {
            LOGGER.error("Exception during cache eviction", exception);
        }
        return false;
    }

    public void clear(final List<String> uris, final String host) throws URISyntaxException {
        // Use minimal - HttpClients.createMinimal() or default - HttpClients.createDefault(), HttpClient based on need.
        // Or write your own handler to meet the needs. Currently, minimal is used which covers need.
        // Enhance HttpClient creation as per the new need or design decisions.
        try (CloseableHttpClient httpclient = HttpClients.createMinimal()) {
            for (String uri : uris) {
                final HttpPurge httpPurge = getHttpPurge(uri, host);
                final CloseableHttpResponse response = httpclient.execute(httpPurge);
                LOGGER.debug("Response code {}", response.getStatusLine());
            }
        } catch (IOException exception) {
            LOGGER.error("EXCEPTION_DURING_CACHE_EVICTION", exception);
        }
    }

    private HttpPurge getHttpPurge(String uri, String host) throws URISyntaxException {
        final HttpPurge httpPurge = new HttpPurge(uri);
        BasicHeader header = new BasicHeader(HOST, host);
        httpPurge.setHeader(header);
        return httpPurge;
    }
}
