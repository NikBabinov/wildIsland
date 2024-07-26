package ru.mail.nikbabinov.constants;

public enum PathToFile {
    CONFIG_FILE("src/main/java/ru/mail/nikbabinov/config.txt"),
    ;


    private final String path;

    PathToFile(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
