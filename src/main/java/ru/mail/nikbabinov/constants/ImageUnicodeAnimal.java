package ru.mail.nikbabinov.constants;

public enum ImageUnicodeAnimal {
    BEAR("\uD83D\uDC3B"),
    BOACONSTRICTOR("\uD83D\uDC0D"),
    BUFFALO("\uD83D\uDC03"),
    CATERPILLAR("\uD83D\uDC1B"),
    DEER("\uD83E\uDD8C"),
    DUCK("\uD83E\uDD86"),
    EAGLE("\uD83E\uDD85"),
    FOX(" \uD83E\uDD8A"),
    GOAT("\uD83D\uDC10"),
    HORSE("\uD83D\uDC0E"),
    MOUSE("\uD83D\uDC01"),
    RABBIT("\uD83D\uDC07"),
    SHEEP("\uD83D\uDC11"),
    WILDBOAR("\uD83D\uDC17"),
    WOLF("\uD83D\uDC3A");

    private final String image;

    private ImageUnicodeAnimal(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
