package com.ever.androidsetup.api.models;

public class Giphy {

    public String type;
    public String id;
    public Images images;

    private Giphy(Builder builder) {
        type = builder.type;
        id = builder.id;
        images = builder.images;
    }

    public static class Images {
        public Images(Image downsized, Image original) {
            this.downsized = downsized;
            this.original = original;
        }

        public Image downsized;
        public Image original;
    }

    public static class Image {
        public Image(String url) {
            this.url = url;
        }

        public String url;
    }


    public static final class Builder {
        private String type;
        private String id;
        private Images images;

        public Builder() {
        }

        public Builder type(String val) {
            type = val;
            return this;
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder url(String url) {
            images = new Images(new Image(url), new Image(url));
            return this;
        }

        public Giphy build() {
            return new Giphy(this);
        }
    }
}
