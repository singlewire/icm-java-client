package com.singlewire;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import us.monoid.json.JSONException;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Replacement;

import java.util.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.singlewire.ResourcePath.user;
import static com.singlewire.ResourcePath.users;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static us.monoid.web.Resty.*;

/**
 * Unit test for simple RestClient.
 */
public class RestClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule();

    private RestClient restClient;

    private String notFoundResponse;

    private String unauthorizedResponse;

    private Map<String, Object> mockUser;

    private String userJson;

    @Before
    public void setUp() throws JSONException {
        mockUser = new HashMap<>();
        mockUser.put("createdAt", "2015-01-12T16:52:47.614+0000");
        mockUser.put("email", "a@aol.com");
        mockUser.put("id", "6fd989e0-9a7b-11e4-a6d1-c22f013130a9");
        mockUser.put("locations", new ArrayList());
        mockUser.put("lock", null);
        mockUser.put("name", "a");
        mockUser.put("permissions", Arrays.asList("delete", "put", "get"));
        mockUser.put("securityGroups", new ArrayList());
        mockUser.put("subscriptions", new ArrayList());

        userJson = new JSONObject(mockUser).toString();

        notFoundResponse = new JSONObject("{\"status\": 404,\"message\": \"Not Found\"}").toString();
        unauthorizedResponse = new JSONObject("{\"type\": \"unauthorized\",\"status\": 401,\"message\": \"Unauthorized\"}").toString();

        restClient = new RestClient("my-access-token", "http://localhost:8080/api/v1-DEV");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClientCreation_throwsExceptionWhenTokenIsNull() {
        new RestClient(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClientCreation_throwsExceptionWhenTokenIsBlank() {
        new RestClient("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testClientCreation_throwsExceptionWhenUrlIsBlank() {
        new RestClient("my-access-token", "");
    }

    @Test
    public void testGetUser_notFoundStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(get(urlMatching("/api/v1-DEV/users/user-id"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(notFoundResponse)));

        JSONResource response = restClient.json(user("user-id"));
        assertTrue(response.status(404));
        assertEquals(response.object().toString(), notFoundResponse);
    }

    @Test
    public void testGetUser_unauthorizedStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(get(urlMatching("/api/v1-DEV/users/user-id"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody(unauthorizedResponse)));

        JSONResource response = restClient.json(user("user-id"));
        assertTrue(response.status(401));
        assertEquals(response.object().toString(), unauthorizedResponse);
    }

    @Test
    public void testGetUser_getsTheCorrectJSONData() throws Exception {
        stubFor(get(urlMatching("/api/v1-DEV/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userJson)));

        assertEquals(restClient.json(users()).object().toString(), userJson);
    }

    @Test
    public void testListUsers_unauthorizedStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=100"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody(unauthorizedResponse)));
        try {
            restClient.list(users()).hasNext();
            fail();
        } catch (PaginationFailedException e) {
            assertEquals(e.getJson().object().toString(), unauthorizedResponse);
        }
    }

    @Test
    public void testListUsers_canGetListWhenPageIsEmpty() throws Exception {
        Map<String, Object> emptyPage = new HashMap<>();
        emptyPage.put("total", 0);
        emptyPage.put("next", null);
        emptyPage.put("previous", null);
        emptyPage.put("data", new ArrayList());
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=100"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new JSONObject(emptyPage).toString())));

        assertFalse(restClient.list(users()).hasNext());
    }

    @Test
    public void testListUsers_canGetFullPageOfData() throws Exception {
        Map<String, Object> emptyPage = new HashMap<>();
        emptyPage.put("total", 1);
        emptyPage.put("next", null);
        emptyPage.put("previous", null);
        emptyPage.put("data", Collections.singletonList(mockUser));
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=100"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new JSONObject(emptyPage).toString())));

        Iterator<JSONObject> usersList = restClient.list(users());
        assertTrue(usersList.hasNext());
        assertEquals(usersList.next().toString(), userJson);
        assertFalse(usersList.hasNext());
    }

    @Test
    public void testListUsers_canGetMultiplePagesOfData() throws Exception {
        Map<String, Object> page = new HashMap<>();
        page.put("total", 3);
        page.put("next", "first");
        page.put("previous", null);
        page.put("data", Collections.singletonList(mockUser));
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new JSONObject(page).toString())));

        page.put("next", "second");
        page.put("previous", "first");
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=1&start=first"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new JSONObject(page).toString())));

        page.put("next", null);
        page.put("previous", "second");
        stubFor(get(urlEqualTo("/api/v1-DEV/users?limit=1&start=second"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(new JSONObject(page).toString())));

        Iterator<JSONObject> usersList = restClient.list(users() + "?limit=1");
        assertTrue(usersList.hasNext());
        assertEquals(usersList.next().toString(), userJson);
        assertTrue(usersList.hasNext());
        assertEquals(usersList.next().toString(), userJson);
        assertTrue(usersList.hasNext());
        assertEquals(usersList.next().toString(), userJson);
        assertFalse(usersList.hasNext());
    }

    @Test
    public void testCreateUser_unauthorizedStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(post(urlMatching("/api/v1-DEV/users"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody(unauthorizedResponse)));

        JSONResource response = restClient.json(users(), form(data("name", "Craig Smith"), data("email", "craig.smith@acme.com")));
        assertTrue(response.status(401));
        assertEquals(response.object().toString(), unauthorizedResponse);
    }

    @Test
    public void testCreateUser_createsUserCorrectly() throws Exception {
        stubFor(post(urlMatching("/api/v1-DEV/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userJson)));

        JSONResource response = restClient.json(users(), form(data("name", "a"), data("email", "a@aol.com")));
        assertTrue(response.status(200));
        assertEquals(response.object().toString(), userJson);
    }

    @Test
    public void testUpdateUser_notFoundStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(put(urlMatching("/api/v1-DEV/users/user-id"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(notFoundResponse)));

        JSONResource response = restClient.json(user("user-id"), new Replacement(content(new JSONObject("{\"name\":\"a\"}"))));
        assertTrue(response.status(404));
        assertEquals(response.object().toString(), notFoundResponse);
    }

    @Test
    public void testUpdateUser_unauthorizedStatusCodeAndBodyAreCorrect() throws Exception {
        stubFor(put(urlMatching("/api/v1-DEV/users/user-id"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody(unauthorizedResponse)));

        JSONResource response = restClient.json(user("user-id"), new Replacement(content(new JSONObject("{\"name\":\"a\"}"))));
        assertTrue(response.status(401));
        assertEquals(response.object().toString(), unauthorizedResponse);
    }

    @Test
    public void testUpdateUser_updatesUserCorrectly() throws Exception {
        stubFor(put(urlMatching("/api/v1-DEV/users/user-id"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(userJson)));

        JSONResource response = restClient.json(user("user-id"), new Replacement(content(new JSONObject("{\"name\":\"a\"}"))));
        assertTrue(response.status(200));
        assertEquals(response.object().toString(), userJson);
    }
}
