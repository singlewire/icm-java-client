package com.singlewire;

import java.util.Map;

/**
 * Helper class to build resource paths for use with InformaCast Mobile
 *
 * @author vincent
 */
public enum Resource {
    USERS("/users"),
    USER_SUBSCRIPTIONS(USERS, "/subscriptions"),
    USER_TOKENS(USERS, "/token"),
    USER_NOTIFICATIONS(USERS, "/notifications"),
    USER_NOTIFICATION_AUDIO(USER_NOTIFICATIONS, "/audio"),
    USER_NOTIFICATION_IMAGE(USER_NOTIFICATIONS, "/image"),
    USER_NOTIFICATION_ACTIVITIES(USER_NOTIFICATIONS, "/activities"),
    USER_DEVICES(USERS, "/devices"),
    USER_SECURITY_GROUPS(USERS, "/security-groups"),
    USER_PERMISSIONS(USERS, "/user-permissions"),
    SECURITY_GROUPS("/security-groups"),
    SECURITY_GROUP_MEMBERS(SECURITY_GROUPS, "/members"),
    SECURITY_GROUP_PERMISSIONS(SECURITY_GROUPS, "/permissions"),
    MESSAGE_TEMPLATES("/message-templates"),
    MESSAGE_TEMPLATE_AUDIO(MESSAGE_TEMPLATES, "/audio"),
    MESSAGE_TEMPLATE_IMAGE(MESSAGE_TEMPLATES, "/image"),
    CONFIRMATION_REQUESTS("/confirmation-requests"),
    CONFIRMATION_REQUEST_ESCALATION_RULES(CONFIRMATION_REQUESTS, "/escalation-rules"),
    NOTIFICATIONS("/notifications"),
    NOTIFICATION_AUDIO(NOTIFICATIONS, "/audio"),
    NOTIFICATION_IMAGE(NOTIFICATIONS, "/image"),
    NOTIFICATION_ACTIVITIES(NOTIFICATIONS, "/activities"),
    NOTIFICATION_RECIPIENTS(NOTIFICATIONS, "/recipients"),
    DISTRIBUTION_LISTS("/distribution-lists"),
    DISTRIBUTION_LIST_USER_SUBSCRIPTIONS(DISTRIBUTION_LISTS, "/user-subscriptions"),
    LOAD_DEFINITIONS("/load-definitions"),
    LOAD_DEFINITION_SECURITY_GROUP_MAPPINGS(LOAD_DEFINITIONS, "/security-group-mappings"),
    LOAD_DEFINITION_DISTRIBUTION_LIST_MAPPINGS(LOAD_DEFINITIONS, "/distribution-list-mappings"),
    LOAD_DEFINITION_LOAD_REQUESTS(LOAD_DEFINITIONS, "/load-requests"),
    SESSION("/session"),
    REPORTS("/reports"),
    TRIGGERS("/triggers");

    /**
     * The URL Format to interpolate path ids
     */
    private final String urlFormat;

    /**
     * Constructs a Resource object with a url format
     *
     * @param urlFormat the URL Format to interpolate path ids
     */
    Resource(String urlFormat) {
        this.urlFormat = urlFormat + "/%s";
    }

    /**
     * Constructs a Resource object with a url format
     *
     * @param parent    the parent Resource object to act as a base path
     * @param urlFormat the URL Format to interpolate path ids
     */
    Resource(Resource parent, String urlFormat) {
        this(parent.urlFormat + urlFormat);
    }

    /**
     * Returns the raw url format of the path
     *
     * @return the raw url format
     */
    public String getUrlFormat() {
        return urlFormat;
    }

    /**
     * Creates a {@link UriBuilder} from this path
     *
     * @return a {@link UriBuilder}
     */
    public UriBuilder builder() {
        return new UriBuilder(this);
    }

    /**
     * Creates a {@link UriBuilder} from this path and ids
     *
     * @param ids the ids to interpolate
     * @return @return a {@link UriBuilder}
     */
    public UriBuilder withIds(String... ids) {
        return new UriBuilder(this, ids);
    }

    /**
     * Creates a {@link UriBuilder} from this path and query
     *
     * @param query the query string
     * @return @return a {@link UriBuilder}
     */
    public UriBuilder withQuery(Map<String, String> query) {
        return new UriBuilder(this, query);
    }
}
