package com.ever.androidsetup.user;

import com.ever.test.common.rules.TestComponentRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserManagerImplTest {

    private UserManagerImpl userManager;

    @Rule
    public final TestComponentRule component = new TestComponentRule.Builder()
            .withUserManager(true)
            .build();

    @Before
    public void setUp() throws Exception {
        userManager = (UserManagerImpl) component.getUserManager();
    }

    @Test
    public void test_name() throws Exception {
        assertThat(userManager.getDisplayName()).isEqualTo("Hey man/woman!");
    }
}