package ru.mail.nikbabinov.constants;

public enum ViewText {
    TITLE_START_STAGE("WILD ISLAND");

    private final String viewText;
    ViewText(String viewText) {
        this.viewText = viewText;
    }

    public String getViewText() {
        return viewText;
    }
}
