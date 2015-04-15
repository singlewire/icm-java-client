package com.singlewire;

import org.junit.Test;

import static com.singlewire.ResourcePath.*;
import static org.junit.Assert.assertEquals;

/**
 * User: vincent
 * Date: 4/15/15
 * Time: 2:12 PM
 */
public class ResourcePathTest {
    @Test
    public void testPaths() {
        assertEquals(users(), "/users");
        assertEquals(user("user-id"), "/users/user-id");
        assertEquals(userSubscriptions("user-id"), "/users/user-id/subscriptions");
        assertEquals(userSubscription("user-id", "sub-id"), "/users/user-id/subscriptions/sub-id");
        assertEquals(userTokens("user-id"), "/users/user-id/tokens");
        assertEquals(userToken("user-id", "token-id"), "/users/user-id/tokens/token-id");
        assertEquals(userNotifications("user-id"), "/users/user-id/notifications");
        assertEquals(userNotification("user-id", "not-id"), "/users/user-id/notifications/not-id");
        assertEquals(userNotificationAudio("user-id", "not-id"), "/users/user-id/notifications/not-id/audio");
        assertEquals(userNotificationImage("user-id", "not-id"), "/users/user-id/notifications/not-id/image");
        assertEquals(userNotificationActivities("user-id", "not-id"), "/users/user-id/notifications/not-id/activities");
        assertEquals(userNotificationActivity("user-id", "not-id", "a-id"), "/users/user-id/notifications/not-id/activities/a-id");
        assertEquals(userDevices("user-id"), "/users/user-id/devices");
        assertEquals(userDevice("user-id", "device-id"), "/users/user-id/devices/device-id");
        assertEquals(userSecurityGroups("user-id"), "/users/user-id/security-groups");
        assertEquals(userSecurityGroup("user-id", "sg-id"), "/users/user-id/security-groups/sg-id");
        assertEquals(userPermissions("user-id"), "/users/user-id/user-permissions");
        assertEquals(userPermission("user-id", "perm-id"), "/users/user-id/user-permissions/perm-id");

        assertEquals(securityGroups(), "/security-groups");
        assertEquals(securityGroup("group-id"), "/security-groups/group-id");
        assertEquals(securityGroupMembers("group-id"), "/security-groups/group-id/members");
        assertEquals(securityGroupMember("group-id", "member-id"), "/security-groups/group-id/members/member-id");
        assertEquals(securityGroupPermissions("group-id"), "/security-groups/group-id/permissions");
        assertEquals(securityGroupPermission("group-id", "member-id"), "/security-groups/group-id/permissions/member-id");

        assertEquals(messageTemplates(), "/message-templates");
        assertEquals(messageTemplate("template-id"), "/message-templates/template-id");
        assertEquals(messageTemplateAudio("template-id"), "/message-templates/template-id/audio");
        assertEquals(messageTemplateImage("template-id"), "/message-templates/template-id/image");

        assertEquals(confirmationRequests(), "/confirmation-requests");
        assertEquals(confirmationRequest("request-id"), "/confirmation-requests/request-id");
        assertEquals(confirmationRequestEscalationRules("request-id"), "/confirmation-requests/request-id/escalation-rules");
        assertEquals(confirmationRequestEscalationRule("request-id", "rule-id"), "/confirmation-requests/request-id/escalation-rules/rule-id");

        assertEquals(notifications(), "/notifications");
        assertEquals(notification("not-id"), "/notifications/not-id");
        assertEquals(notificationAudio("not-id"), "/notifications/not-id/audio");
        assertEquals(notificationImage("not-id"), "/notifications/not-id/image");
        assertEquals(notificationActivities("not-id"), "/notifications/not-id/activities");
        assertEquals(notificationActivity("not-id", "act-id"), "/notifications/not-id/activities/act-id");
        assertEquals(notificationRecipients("not-id"), "/notifications/not-id/recipients");
        assertEquals(notificationRecipient("not-id", "r-id"), "/notifications/not-id/recipients/r-id");

        assertEquals(distributionLists(), "/distribution-lists");
        assertEquals(distributionList("list-id"), "/distribution-lists/list-id");
        assertEquals(distributionListUserSubscriptions("list-id"), "/distribution-lists/list-id/user-subscriptions");
        assertEquals(distributionListUserSubscription("list-id", "sub-id"), "/distribution-lists/list-id/user-subscriptions/sub-id");

        assertEquals(loadDefinitions(), "/load-definitions");
        assertEquals(loadDefinition("def-id"), "/load-definitions/def-id");
        assertEquals(loadDefinitionSecurityGroupMappings("def-id"), "/load-definitions/def-id/security-group-mappings");
        assertEquals(loadDefinitionSecurityGroupMapping("def-id", "sec-id"), "/load-definitions/def-id/security-group-mappings/sec-id");
        assertEquals(loadDefinitionDistributionListMappings("def-id"), "/load-definitions/def-id/distribution-list-mappings");
        assertEquals(loadDefinitionDistributionListMapping("def-id", "dl-id"), "/load-definitions/def-id/distribution-list-mappings/dl-id");
        assertEquals(loadDefinitionLoadRequests("def-id"), "/load-definitions/def-id/load-requests");
        assertEquals(loadDefinitionLoadRequest("def-id", "load-id"), "/load-definitions/def-id/load-requests/load-id");

        assertEquals(session(), "/session");

        assertEquals(reports(), "/reports");
        assertEquals(report("report-id"), "/reports/report-id");

        assertEquals(triggers(), "/triggers");
        assertEquals(trigger("trigger-id"), "/triggers/trigger-id");
    }
}
