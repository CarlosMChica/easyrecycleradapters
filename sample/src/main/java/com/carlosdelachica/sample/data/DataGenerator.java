package com.carlosdelachica.sample.data;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static ImageData generateRandomImageData(int width, int i) {
        return new ImageData("http://placeimg.com/" + width + "/" + 300 + "/nature/" + i);
    }

    public static TextData generateRandomTextData(int i) {
        return new TextData("I'm a Text row at position: " + i);
    }

    public static List<Object> generateRandomDataList(int width) {
        ArrayList<Object> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i < 5) {
                data.add(generateRandomImageData(width, i));
            } else if (i >= 5 && i < 10) {
                data.add(generateRandomTextData(i));
            } else {
                data.add(generateRandomImageData(width, i));
            }
        }
        return data;
    }

    public static List<ImageData> generateRandomImageDataList(int width) {
        ArrayList<ImageData> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(generateRandomImageData(width, i));
        }
        return data;
    }

}
