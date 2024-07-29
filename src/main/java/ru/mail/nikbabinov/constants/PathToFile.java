package ru.mail.nikbabinov.constants;

import java.nio.file.Path;

public enum PathToFile {
    CONFIG_FILE("src/main/java/ru/mail/nikbabinov/config.txt"),
    ;


    private final String path;

    PathToFile(String path) {
        this.path = path;
    }

    public String getPath() {
        Path path = Path.of(this.path);
        return path.toAbsolutePath().toString();
    }
}
