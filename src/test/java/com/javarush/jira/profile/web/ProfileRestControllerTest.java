package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Set;

import static com.javarush.jira.profile.web.ProfileTestData.USER_MAIL;
import static com.javarush.jira.profile.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.web.ProfileTestData.PROFILE_MATCHER;
import static com.javarush.jira.profile.web.ProfileTestData.USER_CONTACT_SKYPE;
import static com.javarush.jira.profile.web.ProfileTestData.USER_CONTACT_WEBSITE;
import static com.javarush.jira.profile.web.ProfileTestData.USER_ID;
import static com.javarush.jira.profile.web.ProfileTestData.USER_PASSWORD;
import static com.javarush.jira.profile.web.ProfileTestData.getUpdated;
import static com.javarush.jira.profile.web.ProfileTestData.jsonWithPassword;
import static com.javarush.jira.profile.web.ProfileTestData.profileUser;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    ProfileRepository repository;
    @Autowired
    ProfileMapper profileMapper;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)).andExpect(status().isOk()).andDo(print()).andExpect(content()
                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_MATCHER.contentJson(profileUser));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        Profile dbProfileBefore = repository.getExisted(USER_ID);
        ProfileTo dbProfile = profileMapper.toTo(dbProfileBefore);
        dbProfile.setContacts(Set.of(USER_CONTACT_SKYPE, USER_CONTACT_WEBSITE));
        dbProfile.setMailNotifications(Set.of("one_day_before_deadline", "deadline"));
        perform(MockMvcRequestBuilders.put(REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(jsonWithPassword(dbProfile, USER_PASSWORD))).andDo(print())
                .andExpect(status().isNoContent());
        Profile dbProfileAfter = repository.getExisted(profileUser.id());
        ProfileTo dbProfileToAfter = profileMapper.toTo(dbProfileAfter);
        PROFILE_MATCHER.assertMatch(dbProfileToAfter, getUpdated());
    }
}
