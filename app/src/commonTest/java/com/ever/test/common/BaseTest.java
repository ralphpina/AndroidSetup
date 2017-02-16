package com.ever.test.common;

import com.ever.androidsetup.api.models.Giphy;

import java.util.ArrayList;
import java.util.List;

public class BaseTest {

    public List<Giphy> getGiphies(int count) {
        List<Giphy> list = new ArrayList<>();
        for (int i = count; i > 0; i--) {
            list.add(new Giphy.Builder().url("some_url_" + i).build());
        }
        return list;
    }
}