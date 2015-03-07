package com.singlewire;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Helper to encode queries. Intentionally not exposed outside of this package
 *
 * @author vincent
 */
final class UrlEncodeUtils {
    /**
     * URL encodes a value using the UTF-8 charset
     *
     * @param value the value to encode
     * @return the encoded value
     * @throws UnsupportedEncodingException
     */
    static String encode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    /**
     * Encodes a map into a query string to be appended to a url
     *
     * @param queryMap the query map to encode
     * @return the encoded query string
     * @throws UnsupportedEncodingException
     */
    static String urlEncode(Map<String, String> queryMap) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder("?");
        for (Map.Entry<?, ?> entry : queryMap.entrySet()) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append("&");
            }
            stringBuilder.append(String.format("%s=%s",
                    encode(entry.getKey().toString()),
                    encode(entry.getValue().toString())
            ));
        }
        return stringBuilder.toString();
    }
}