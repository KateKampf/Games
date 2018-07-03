package com.example.kampf.games.games;

public class Game {

    private final String name;
    private final String deck;
    private final String imgUrl;

    public Game(String name, String deck, String imgUrl) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.deck = deck;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getDeck() {
        return deck;
    }
}
