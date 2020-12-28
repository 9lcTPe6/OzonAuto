package ru.appline.ibs.homework.framework.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm"},
        glue = {"ru.appline.ibs.homework.framework.cucumber"},
        features = {"src/test/resources/"},
        tags = "@wirelessEarsDrop"
)

public class TestRunner {
}
