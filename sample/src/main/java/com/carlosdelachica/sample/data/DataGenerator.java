package com.carlosdelachica.sample.data;

import com.carlosdelachica.sample.adapter.ImageData;
import com.carlosdelachica.sample.adapter.TextData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    public static ImageData generateRandomImageData(int width, int height, int i) {
        int randomIndex = new Random().nextInt(50);
        return new ImageData("http://placeimg.com/" + width + "/" + height + "/nature/" + randomIndex, "I'm a Image row at position: " + i);
    }

    public static TextData generateRandomTextData(int i) {
        return new TextData("I'm a Text row at position: " + i);
    }

    public static List<Object> generateRandomData(int width, int height) {
        ArrayList<Object> data = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if (i < 5) {
                data.add(generateRandomImageData(width, height, i));
            } else if (i >= 5 && i <10) {
                data.add(generateRandomTextData(i));
            } else {
                data.add(generateRandomImageData(width, height, i));
            }
        }
        return data;
    }

}
