package ru.mail.nikbabinov.constants;

public enum ViewText {
    TITLE_START_STAGE("WILD ISLAND"),
    TITLE_EDIT_ANIMAL_DIALOG("Редактирование свойств животного");

    private final String viewText;
    ViewText(String viewText) {
        this.viewText = viewText;
    }

    public String getViewText() {
        return viewText;
    }
}
