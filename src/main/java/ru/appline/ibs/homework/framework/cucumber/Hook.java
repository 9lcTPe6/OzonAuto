package ru.appline.ibs.homework.framework.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Attachment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static ru.appline.ibs.homework.framework.managers.FrameworkLaunchManager.launchFramework;
import static ru.appline.ibs.homework.framework.managers.FrameworkLaunchManager.stopFramework;
import static ru.appline.ibs.homework.framework.utils.PropertyVars.CHROME_DRIVER;

public class Hook {

    @Before
    public void run() {
        launchFramework(CHROME_DRIVER);
    }

    @After
    public void stop() {
        stopFramework();
    }

    @Attachment(value = "Отчет", type = "application/json", fileExtension = ".txt")
    public static byte[] getReport(String resourceName) throws IOException {
        return Files.readAllBytes(Paths.get("src/main/resources", resourceName));
    }

}
