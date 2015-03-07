package com.singlewire;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Builder class to build a uri with an optional set of ids to interpolate and an optional query
 *
 * @author vincent
 */
public class UriBuilder {
    /**
     * The resource to start the builder from
     */
    private final Resource resource;

    /**
     * The query to append to the uri built
     */
    private final Map<String, String> query;

    /**
     * The ids to interpolate
     */
    private final String[] ids;

    /**
     * Constructs a new UriBuilder
     *
     * @param resource the resource to start the builder from
     */
    public UriBuilder(Resource resource) {
        this.resource = resource;
        this.query = null;
        this.ids = null;
    }

    /**
     * Constructs a new UriBuilder
     *
     * @param resource the resource to start the builder from
     * @param query    the query to append to the uri built
     */
    public UriBuilder(Resource resource, Map<String, String> query) {
        this.resource = resource;
        this.query = query;
        this.ids = null;
    }

    /**
     * Constructs a new UriBuilder
     *
     * @param resource the resource to start the builder from
     * @param ids      the ids to interpolate
     */
    public UriBuilder(Resource resource, String... ids) {
        this.resource = resource;
        this.query = null;
        this.ids = ids;
    }

    /**
     * Constructs a new UriBuilder
     *
     * @param resource the resource to start the builder from
     * @param query    the query to append to the uri built
     * @param ids      the ids to interpolate
     */
    public UriBuilder(Resource resource, Map<String, String> query, String... ids) {
        this.resource = resource;
        this.query = query;
        this.ids = ids;
    }

    /**
     * Creates a new builder given a new set of ids
     *
     * @param ids the ids to interpolate
     * @return the new UriBuilder
     */
    public UriBuilder withIds(String... ids) {
        return new UriBuilder(resource, query, ids);
    }

    /**
     * Creates a new builder given a new query
     *
     * @param query the query to append to the uri built
     * @return the new UriBuilder
     */
    public UriBuilder withQuery(Map<String, String> query) {
        return new UriBuilder(resource, query, ids);
    }

    /**
     * Gets the resource to start the builder from
     *
     * @return the resource to start the builder from
     */
    public Resource getResource() {
        return resource;
    }

    /**
     * Gets the query to append to the uri built
     *
     * @return the query to append to the uri built
     */
    public Map<String, String> getQuery() {
        if (query == null) {
            return null;
        }
        return new HashMap<String, String>(query);
    }

    /**
     * Gets the ids to interpolate
     *
     * @return the ids to interpolate
     */
    public String[] getIds() {
        if (ids == null) {
            return null;
        }
        return Arrays.copyOf(ids, ids.length);
    }

    /**
     * Builds a uri string from the resource, ids, and query
     *
     * @return the built uri
     * @throws UnsupportedEncodingException
     */
    public String build() throws UnsupportedEncodingException {
        String urlFormat = resource.getUrlFormat();
        int positionalArgumentCount = (urlFormat.split("/").length - 1) / 2;
        String queryString = query == null ? "" : UrlEncodeUtils.urlEncode(query);
        String[] idsToUse = ids == null ? new String[0] : ids;
        if (idsToUse.length == positionalArgumentCount) {
            return String.format(urlFormat, idsToUse) + queryString;
        } else if (idsToUse.length == positionalArgumentCount - 1) {
            String formatToUse = urlFormat.substring(0, urlFormat.lastIndexOf("/%s"));
            return String.format(formatToUse, idsToUse) + queryString;
        } else {
            throw new IllegalArgumentException("Expected either " + (positionalArgumentCount - 1) +
                    " or " + positionalArgumentCount + " identifiers");
        }
    }
}
