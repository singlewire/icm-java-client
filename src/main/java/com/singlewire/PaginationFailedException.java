package com.singlewire;

import us.monoid.web.JSONResource;

/**
 * Exception class thrown when there was an issue with pagination. Encapsulates the response.
 *
 * @author vincent
 */
public class PaginationFailedException extends RuntimeException {
    /**
     * The response that resulted in the exception
     */
    private final JSONResource json;

    /**
     * Creates the exception
     *
     * @param json the response that resulted in the exception
     */
    public PaginationFailedException(JSONResource json) {
        this.json = json;
    }

    /**
     * Returns the response that resulted in the exception
     *
     * @return the response that resulted in the exception
     */
    public JSONResource getJson() {
        return json;
    }
}
