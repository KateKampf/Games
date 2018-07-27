package com.example.kampf.games.network;

public class GbObjectResponse {

    private String name;
    private String deck;
    private Image image;

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDeck() {
        return deck;
    }

    public static class Image {

        private String smallUrl;

        public String getSmallUrl() {
            return smallUrl;
        }
    }

}
