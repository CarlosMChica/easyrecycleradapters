package com.carlosdelachica.sample.data;

import com.carlosdelachica.sample.adapter.ImageData;

import java.util.ArrayList;
import java.util.Random;

public class DataGenerator {

    public static ArrayList<ImageData> generateRandomData(int width, int height) {

        ArrayList<ImageData> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int randomIndex = new Random().nextInt(50);
            data.add(new ImageData("http://placeimg.com/" + width + "/" + height + "/nature/" + randomIndex, "Image " + i));
        }
        return data;

    }

}
