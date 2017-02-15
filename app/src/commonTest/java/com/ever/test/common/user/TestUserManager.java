package com.ever.test.common.user;

import com.ever.androidsetup.user.UserManager;


public class TestUserManager implements UserManager {

    private String displayName;

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
