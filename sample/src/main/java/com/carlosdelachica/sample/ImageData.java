package com.carlosdelachica.sample;

public class ImageData {

    private String imageUrl;
    private String title;

    public ImageData(String imageUrl, String title) {
        this.imageUrl = imageUrl;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

}
