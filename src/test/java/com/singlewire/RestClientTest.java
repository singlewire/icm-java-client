package com.singlewire;

import org.junit.BeforeClass;
import org.junit.Test;
import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;
import us.monoid.web.JSONResource;
import us.monoid.web.Resty;

import static com.singlewire.ResourcePath.*;
import static com.singlewire.UrlEncodeUtils.*;
import static us.monoid.web.Resty.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * Unit test for simple RestClient.
 */
public class RestClientTest {

    private static String ACCESS_TOKEN = "";
    private static String BASE_URL = "";
    private static String MSG_TEMPLATE_UUID = "";
    private static String DL_TEMPLATE_UUID = "";

    @BeforeClass
    public static void initProps() {
        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("icmobile.properties");
            Properties props = new Properties();
            props.load(is);
            ACCESS_TOKEN = (String) props.get("accessToken");
            BASE_URL = (String) props.get("baseUrl");
            MSG_TEMPLATE_UUID = (String) props.get("messageTemplateId");
            DL_TEMPLATE_UUID = (String) props.get("distributionListIds");
        } catch (Exception e) {
            throw new RuntimeException((e));
        }
    }

    @Test
    public void queryUsers() {
        Map<String, String> query = new HashMap<String, String>();
        query.put("q", "Pyeatt");
        query.put("limit", "10");
        try {
            RestClient client = new RestClient(ACCESS_TOKEN, BASE_URL, Resty.Option.timeout(1000));
            JSONResource users = client.json(users() + urlEncode(query));

            int counter = 0;
            JSONArray arr = (JSONArray) users.get("data");
            for (int i = 0; i < arr.length(); i++) {
                counter++;
                JSONObject o = arr.getJSONObject(i);
                assertTrue(o.getString("name").contains("Pyeatt"));
            }
            assertTrue("Didn't get any queried users", counter > 0);
        } catch (Exception e) {
            e.printStackTrace();
            fail("threw exception when querying users " + e.getMessage());

        }
    }

    @Test
    public void postNotification() {

        try {
            RestClient client = new RestClient(ACCESS_TOKEN, BASE_URL, Resty.Option.timeout(1000));

            JSONObject createdNotification = client.json(notifications(),
                    form(data("messageTemplateid", MSG_TEMPLATE_UUID),
                         data("distributionListIds", DL_TEMPLATE_UUID),
                         data("subject","Sent from unit test"),
                         data("body", "Unit test body"))).toObject();

            String notificationId = createdNotification.getString("id");
        } catch (Exception e) {
            fail("threw exception when posting a notification " + e.getMessage());

        }
    }

    @Test
    public void listUsers() {

        try {
            RestClient client = new RestClient(ACCESS_TOKEN, BASE_URL, Resty.Option.timeout(1000));
            Iterator<JSONObject> users = client.list(users());
            int counter = 0;
            while (users.hasNext()) {
                JSONObject o = users.next();
                counter++;
                System.out.println(o.getString("name"));
            }
            assertTrue("Didn't get any users", counter > 0);
        } catch (Exception e) {
            fail("threw exception when reading users " + e.getMessage());
        }
    }

}
