package com.singlewire;

/**
 * Utility class to provide common paths used within the InformaCast Mobile REST API.
 *
 * @author vincent
 */
public class ResourcePath {
    public static String users() {
        return "/users";
    }

    public static String user(String id) {
        return users() + "/" + id;
    }

    public static String userSubscriptions(String userId) {
        return user(userId) + "/subscriptions";
    }

    public static String userSubscription(String userId, String id) {
        return userSubscriptions(userId) + "/" + id;
    }

    public static String userTokens(String userId) {
        return user(userId) + "/tokens";
    }

    public static String userToken(String userId, String id) {
        return userTokens(userId) + "/" + id;
    }

    public static String userNotifications(String userId) {
        return user(userId) + "/notifications";
    }

    public static String userNotification(String userId, String id) {
        return userNotifications(userId) + "/" + id;
    }

    public static String userNotificationAudio(String userId, String notificationId) {
        return userNotification(userId, notificationId) + "/audio";
    }

    public static String userNotificationImage(String userId, String notificationId) {
        return userNotification(userId, notificationId) + "/image";
    }

    public static String userNotificationActivities(String userId, String notificationId) {
        return userNotification(userId, notificationId) + "/activities";
    }

    public static String userNotificationActivity(String userId, String notificationId, String id) {
        return userNotificationActivities(userId, notificationId) + "/" + id;
    }

    public static String userDevices(String userId) {
        return user(userId) + "/devices";
    }

    public static String userDevice(String userId, String id) {
        return userDevices(userId) + "/" + id;
    }

    public static String userSecurityGroups(String userId) {
        return user(userId) + "/security-groups";
    }

    public static String userSecurityGroup(String userId, String id) {
        return userSecurityGroups(userId) + "/" + id;
    }

    public static String userPermissions(String userId) {
        return user(userId) + "/user-permissions";
    }

    public static String userPermission(String userId, String id) {
        return userPermissions(userId) + "/" + id;
    }

    public static String securityGroups() {
        return "/security-groups";
    }

    public static String securityGroup(String id) {
        return securityGroups() + "/" + id;
    }

    public static String securityGroupMembers(String securityGroupId) {
        return securityGroup(securityGroupId) + "/members";
    }

    public static String securityGroupMember(String securityGroupId, String id) {
        return securityGroupMembers(securityGroupId) + "/" + id;
    }

    public static String securityGroupPermissions(String securityGroupId) {
        return securityGroup(securityGroupId) + "/permissions";
    }

    public static String securityGroupPermission(String securityGroupId, String id) {
        return securityGroupPermissions(securityGroupId) + "/" + id;
    }

    public static String messageTemplates() {
        return "/message-templates";
    }

    public static String messageTemplate(String id) {
        return messageTemplates() + "/" + id;
    }

    public static String messageTemplateAudio(String messageTemplateId) {
        return messageTemplate(messageTemplateId) + "/audio";
    }

    public static String messageTemplateImage(String messageTemplateId) {
        return messageTemplate(messageTemplateId) + "/image";
    }

    public static String confirmationRequests() {
        return "/confirmation-requests";
    }

    public static String confirmationRequest(String id) {
        return confirmationRequests() + "/" + id;
    }

    public static String confirmationRequestEscalationRules(String confirmationRequestId) {
        return confirmationRequest(confirmationRequestId) + "/escalation-rules";
    }

    public static String confirmationRequestEscalationRule(String confirmationRequestId, String id) {
        return confirmationRequestEscalationRules(confirmationRequestId) + "/" + id;
    }

    public static String notifications() {
        return "/notifications";
    }

    public static String notification(String id) {
        return notifications() + "/" + id;
    }

    public static String notificationAudio(String notificationId) {
        return notification(notificationId) + "/audio";
    }

    public static String notificationImage(String notificationId) {
        return notification(notificationId) + "/image";
    }

    public static String notificationActivities(String notificationId) {
        return notification(notificationId) + "/activities";
    }

    public static String notificationActivity(String notificationId, String id) {
        return notificationActivities(notificationId) + "/" + id;
    }

    public static String notificationRecipients(String notificationId) {
        return notification(notificationId) + "/recipients";
    }

    public static String notificationRecipient(String notificationId, String id) {
        return notificationRecipients(notificationId) + "/" + id;
    }

    public static String distributionLists() {
        return "/distribution-lists";
    }

    public static String distributionList(String id) {
        return distributionLists() + "/" + id;
    }

    public static String distributionListUserSubscriptions(String distributionListId) {
        return distributionList(distributionListId) + "/user-subscriptions";
    }

    public static String distributionListUserSubscription(String distributionListId, String id) {
        return distributionListUserSubscriptions(distributionListId) + "/" + id;
    }

    public static String loadDefinitions() {
        return "/load-definitions";
    }

    public static String loadDefinition(String id) {
        return loadDefinitions() + "/" + id;
    }

    public static String loadDefinitionSecurityGroupMappings(String loadDefinitionId) {
        return loadDefinition(loadDefinitionId) + "/security-group-mappings";
    }

    public static String loadDefinitionSecurityGroupMapping(String loadDefinitionId, String id) {
        return loadDefinitionSecurityGroupMappings(loadDefinitionId) + "/" + id;
    }

    public static String loadDefinitionDistributionListMappings(String loadDefinitionId) {
        return loadDefinition(loadDefinitionId) + "/distribution-list-mappings";
    }

    public static String loadDefinitionDistributionListMapping(String loadDefinitionId, String id) {
        return loadDefinitionDistributionListMappings(loadDefinitionId) + "/" + id;
    }

    public static String loadDefinitionLoadRequests(String loadDefinitionId) {
        return loadDefinition(loadDefinitionId) + "/load-requests";
    }

    public static String loadDefinitionLoadRequest(String loadDefinitionId, String id) {
        return loadDefinitionLoadRequests(loadDefinitionId) + "/" + id;
    }

    public static String session() {
        return "/session";
    }

    public static String reports() {
        return "/reports";
    }

    public static String report(String id) {
        return reports() + "/" + id;
    }

    public static String triggers() {
        return "/triggers";
    }

    public static String trigger(String id) {
        return triggers() + "/" + id;
    }
}
