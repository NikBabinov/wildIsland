package ru.mail.nikbabinov.constants;

public enum ScaleViewProperty {
    MAIN_WILD_ISLAND_STAGE(10);

    private final int scale;

    ScaleViewProperty(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }
}
