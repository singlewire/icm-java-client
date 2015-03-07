package com.singlewire;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * REST Client for InformaCast Mobile that acts as a lightweight wrapper around Resty.
 *
 * @author vincent
 */
public class RestClient {
    /**
     * The base url of the InformaCast Mobile REST API
     */
    private final String baseUrl;

    /**
     * The instance of Resty we will be delegating method calls to
     */
    private final Resty resty;

    /**
     * Constructs a new RestClient
     *
     * @param accessToken the access token to authenticate with
     * @param baseUrl     the REST API url
     * @param someOptions new set of options
     */
    public RestClient(String accessToken, String baseUrl, Resty.Option... someOptions) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new IllegalArgumentException("accessToken must not be empty");
        }
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("baseUrl must not be empty");
        }
        this.baseUrl = baseUrl;
        this.resty = new Resty(someOptions);
        this.resty.withHeader("Authorization", "Bearer " + accessToken);
        this.resty.withHeader("X-Client-Version", "ICMJava 0.0.1");
    }

    /**
     * Constructs a new RestClient
     *
     * @param accessToken the access token to authenticate with
     * @param someOptions new set of options
     */
    public RestClient(String accessToken, Resty.Option... someOptions) {
        this(accessToken, "https://api.icmobile.singlewire.com/api/v1-DEV", someOptions);
    }

    /**
     * Creates a lazy {@link java.util.Iterator} to page through the resource at the given path
     *
     * @param path the path to paginate
     * @return a lazy {@link java.util.Iterator} of {@link us.monoid.json.JSONObject}
     * @throws IOException
     */
    public Iterator<JSONObject> list(String path) throws IOException {
        final StringBuilder pathBuilder = new StringBuilder(path);
        try {
            String rawQuery = new URI(path).getRawQuery();
            if (rawQuery == null || !rawQuery.contains("limit=")) {
                pathBuilder.append(rawQuery == null || rawQuery.isEmpty() ? "?" : "&");
                pathBuilder.append("limit=100");
            }
        } catch (URISyntaxException e) {
            throw new IOException("Failed to parse uri", e);
        }
        final String pathToUse = pathBuilder.toString();
        return new Iterator<JSONObject>() {
            private boolean started = false;
            private String nextToken = null;
            private Queue<JSONObject> currentPage = new ArrayDeque<JSONObject>();

            private synchronized void loadNextPage() throws Exception {
                StringBuilder pathBuilder = new StringBuilder(baseUrl + pathToUse);
                if (nextToken != null && !nextToken.isEmpty()) {
                    pathBuilder.append("&start=").append(UrlEncodeUtils.encode(nextToken));
                }
                JSONObject page = resty.json(pathBuilder.toString()).toObject();
                nextToken = page.isNull("next") ? null : page.getString("next");
                JSONArray resources = page.getJSONArray("data");
                for (int i = 0; i < resources.length(); i++) {
                    currentPage.add(resources.getJSONObject(i));
                }
            }

            @Override
            public synchronized boolean hasNext() {
                try {
                    if (!started) {
                        started = true;
                        loadNextPage();
                    }
                    if (currentPage.isEmpty() && nextToken != null && !nextToken.isEmpty()) {
                        loadNextPage();
                    }
                } catch (Exception e) {
                    throw new RuntimeException("Failed to query next resource page", e);
                }
                return !currentPage.isEmpty();
            }

            @Override
            public synchronized JSONObject next() {
                if (currentPage.isEmpty()) {
                    throw new IllegalStateException("Failed to get next resource because the page was empty");
                }
                return currentPage.poll();
            }
        };
    }

    /**
     * Creates a lazy {@link java.util.Iterator} to page through the resource at the given path
     *
     * @param resource the resource to paginate
     * @return a lazy {@link java.util.Iterator} of {@link us.monoid.json.JSONObject}
     * @throws IOException
     */
    public Iterator<JSONObject> list(Resource resource) throws IOException {
        return this.list(resource.builder().build());
    }

    /**
     * GET the URI and parse the result as JSON
     *
     * @param path the path to use
     * @return the json resource
     * @throws IOException
     */
    public JSONResource json(String path) throws IOException {
        return resty.json(baseUrl + path);
    }

    /**
     * GET the URI and parse the result as JSON
     *
     * @param resource the resource to use
     * @return the json resource
     * @throws IOException
     */
    public JSONResource json(Resource resource) throws IOException {
        return this.json(resource.builder().build());
    }

    /**
     * POST/PUT to a URI and parse the result as JSON
     *
     * @param path    the path to use
     * @param content the content to POST/PUT to the URI
     * @return the json resource
     * @throws IOException
     */
    public JSONResource json(String path, AbstractContent content) throws IOException {
        return resty.json(baseUrl + path, content);
    }

    /**
     * POST/PUT to a URI and parse the result as JSON
     *
     * @param resource the resource to use
     * @param content  the content to POST/PUT to the URI
     * @return the json resource
     * @throws IOException
     */
    public JSONResource json(Resource resource, AbstractContent content) throws IOException {
        return this.json(resource.builder().build(), content);
    }

    /**
     * GET the URI and parse the result as text
     *
     * @param path the path to use
     * @return the text resource
     * @throws IOException
     */
    public TextResource text(String path) throws IOException {
        return resty.text(baseUrl + path);
    }

    /**
     * GET the URI and parse the result as text
     *
     * @param resource the resource to use
     * @return the text resource
     * @throws IOException
     */
    public TextResource text(Resource resource) throws IOException {
        return this.text(resource.builder().build());
    }

    /**
     * POST/PUT to a URI and parse the result as text
     *
     * @param path    the path to use
     * @param content the content to POST/PUT to the URI
     * @return the text resource
     * @throws IOException
     */
    public TextResource text(String path, AbstractContent content) throws IOException {
        return resty.text(baseUrl + path, content);
    }

    /**
     * POST/PUT to a URI and parse the result as text
     *
     * @param resource the resource to use
     * @param content  the content to POST/PUT to the URI
     * @return the text resource
     * @throws IOException
     */
    public TextResource text(Resource resource, AbstractContent content) throws IOException {
        return this.text(resource.builder().build(), content);
    }

    /**
     * GET the URI and get the resource as binary resource.
     *
     * @param path the path to send to
     * @return a binary resource
     * @throws IOException
     */
    public BinaryResource bytes(String path) throws IOException {
        return resty.bytes(baseUrl + path);
    }

    /**
     * GET the URI and get the resource as binary resource.
     *
     * @param resource the resource to send to
     * @return a binary resource
     * @throws IOException
     */
    public BinaryResource bytes(Resource resource) throws IOException {
        return this.bytes(resource.builder().build());
    }

    /**
     * POST/PUT to the URI and get the resource as binary resource.
     *
     * @param path    the path to send to
     * @param content the content to send
     * @return a binary resource
     * @throws IOException
     */
    public BinaryResource bytes(String path, AbstractContent content) throws IOException {
        return resty.bytes(baseUrl + path, content);
    }

    /**
     * POST/PUT to the URI and get the resource as binary resource.
     *
     * @param resource the resource to send to
     * @param content  the content to send
     * @return a binary resource
     * @throws IOException
     */
    public BinaryResource bytes(Resource resource, AbstractContent content) throws IOException {
        return this.bytes(resource.builder().build(), content);
    }

    /**
     * Set options if you missed your opportunity in the c'tor or if you want to change the options.
     *
     * @param someOptions new set of options
     * @return this
     */
    public RestClient setOptions(Resty.Option... someOptions) {
        resty.setOptions(someOptions);
        return this;
    }

    /**
     * Sets the User-Agent to identify as Mozilla/Firefox. Otherwise a Resty specific User-Agent is used
     */
    public RestClient identifyAsMozilla() {
        resty.identifyAsMozilla();
        return this;
    }

    /**
     * Sets the User-Agent to Resty. WHICH IS THE DEFAULT. Sorry for yelling.
     */
    public RestClient identifyAsResty() {
        resty.identifyAsResty();
        return this;
    }

    /**
     * Tell Resty to send the specified header with each request done on this instance. These headers will also be sent from any resource object returned by this instance. I.e. chained calls will carry
     * over the headers r.json(url).json(get("some.path.to.a.url")); Multiple headers of the same type are not supported (yet).
     *
     * @param aHeader the header to send
     * @param aValue  the value
     */
    public void withHeader(String aHeader, String aValue) {
        resty.withHeader(aHeader, aValue);
    }

    /**
     * Don't send a header that was formely added in the alwaysSend method.
     *
     * @param aHeader the header to remove
     */
    public void dontSend(String aHeader) {
        resty.dontSend(aHeader);
    }

    /**
     * Set the proxy for this instance of Resty.
     * Note, that the proxy settings will be carried over to Resty instances created by this instance only if you used
     * new Resty(Option.proxy(...))!
     *
     * @param proxyhost name of the host
     * @param proxyport port to be used
     */
    public void setProxy(String proxyhost, int proxyport) {
        resty.setProxy(proxyhost, proxyport);
    }
}
