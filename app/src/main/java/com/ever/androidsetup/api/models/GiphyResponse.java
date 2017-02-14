package com.ever.androidsetup.api.models;

import java.util.List;

public class GiphyResponse {

    public List<Giphy> data;
    public Pagination pagination;
    public Meta meta;

    public class Pagination {
        public int count;
        public int offset;
    }

    public class Meta {
        public int status;
        public String msg;
    }
}
