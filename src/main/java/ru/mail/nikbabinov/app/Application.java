package ru.mail.nikbabinov.app;

import ru.mail.nikbabinov.view.View;

public class Application {
    private static View view;

    public static void main(String[] args) {
        view = new View();
        view.applicationRun();
    }

}
