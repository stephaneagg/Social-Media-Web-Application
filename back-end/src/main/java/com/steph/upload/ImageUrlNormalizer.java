package com.steph.upload;

import org.springframework.stereotype.Component;

@Component
public class ImageUrlNormalizer {

    private static final String PROJECT_PATH_PREFIX = "back-end/";

    public String normalize(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }

        if (imageUrl.startsWith(PROJECT_PATH_PREFIX)) {
            return "/" + imageUrl.substring(PROJECT_PATH_PREFIX.length());
        }

        return imageUrl;
    }
}
