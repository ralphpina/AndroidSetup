package com.ever.androidsetup.api.models;

public class Giphy {

    public String type;
    public String id;
    public Images images;

    public class Images {
        public Image downsized;
        public Image original;
    }

    public class Image {
        public String url;
    }
}
