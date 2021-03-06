package com.example.kampf.games.network;

public class GbObjectResponse {

    private String name;
    private String deck;
    private Image image;
    private String guid;
    private String description;
    private String locationCountry;
    private String locationCity;

    public String getLocationCountry() {
        return locationCountry;
    }

    public String getLocationCity() {
        return locationCity;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDeck() {
        return deck;
    }

    public String getGuid() {
        return guid;
    }

    public static class Image {

        private String smallUrl;

        public String getSmallUrl() {
            return smallUrl;
        }
    }

}
