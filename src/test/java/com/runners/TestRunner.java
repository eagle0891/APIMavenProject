package com.runners;

import io.cucumber.junit.CucumberOptions;

@CucumberOptions(
    features = "com/features",
    glue = "stepDefs",
    monochrome = true
)
public class TestRunner {
}
