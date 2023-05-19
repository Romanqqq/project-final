package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ContactTo;
import com.javarush.jira.profile.ProfileTo;

import java.util.Set;

public class ProfileTestData {
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_MATCHER = MatcherFactory.usingEqualsComparator(
            ProfileTo.class);
    public static final long USER_ID = 1L;
    public static final String USER_MAIL= "user@gmail.com";
    public static final String USER_PASSWORD = "password";
    public static final Set<String> USER_MAIL_NOTIFICATIONS = Set.of("assigned", "deadline", "overdue");
    public static final ContactTo CONTACT_SKYPE = new ContactTo("skype", "SkypeUser");
    public static final ContactTo CONTACT_MOBILE = new ContactTo("mobile", "+71234567890");
    public static final ContactTo CONTACT_WEBSITE = new ContactTo("website", "user.ru");
    public static final ContactTo USER_CONTACT_SKYPE = new ContactTo("skype", "SkypeUser");
    public static final ContactTo USER_CONTACT_WEBSITE = new ContactTo("website", "user.ru");

    static {
        USER_CONTACT_SKYPE.setId(USER_ID);
        USER_CONTACT_WEBSITE.setId(USER_ID);
    }

    public static final Set<ContactTo> USER_CONTACTS = Set.of(CONTACT_SKYPE, CONTACT_MOBILE, CONTACT_WEBSITE);

    public static final ProfileTo profileUser = new ProfileTo(USER_ID, USER_MAIL_NOTIFICATIONS, USER_CONTACTS);

    public static ProfileTo getUpdated() {
        return new ProfileTo(USER_ID, Set.of("one_day_before_deadline", "deadline"), Set.of(USER_CONTACT_SKYPE, USER_CONTACT_WEBSITE));
    }

    public static <T> String jsonWithPassword(T profileTo, String password) {
        return JsonUtil.writeAdditionProps(profileTo, "password", password);
    }
}