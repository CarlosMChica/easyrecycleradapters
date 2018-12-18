package com.carlosdelachica.sample.data;

public class ImageData {

    private String imageUrl;

    public ImageData(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        System.out.println(imageUrl);
        return imageUrl;
    }

}
