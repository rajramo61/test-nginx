package org.rajesh.nginx.http;

import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * @author Rajesh Dwivedi
 * Date: 4/15/21
 */
public class HttpPurge extends HttpRequestBase {
    public static final String METHOD_NAME = "PURGE";
    private URI uri;

    public HttpPurge() {}

    public HttpPurge(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
        super.setURI(this.uri);
    }

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }

    public void setURI(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
        super.setURI(this.uri);
    }

    public URI getHttpPurgeUri() {
        return uri;
    }
}
