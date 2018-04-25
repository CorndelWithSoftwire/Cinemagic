package org.softwire.training.cinemagic.integration;

import org.apache.commons.lang3.StringUtils;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * JUnit {@link MethodRule} which performs cleanup after tests using a {@link WebDriver}:
 *
 * * Collect browser logs whatever the test outcome.
 * * Take a screenshot on test failure.
 */
public class WebDriverLoggingRule implements MethodRule {

    private static final String INTEGRATION_TEST_LOG_DIRECTORY = "target/integration-test/";

    public Statement apply(final Statement statement, final FrameworkMethod frameworkMethod, final Object target) {
        final WebDriver driver = ((BookingTest) target).driver;

        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Files.createDirectories(Paths.get(INTEGRATION_TEST_LOG_DIRECTORY));

                try {
                    statement.evaluate();
                } catch (Throwable t) {
                    captureScreenshot(driver, frameworkMethod.getName());
                    throw t;
                } finally {
                    captureLogs(driver, frameworkMethod.getName());
                }
            }
        };
    }

    private void captureScreenshot(WebDriver driver, String methodName) throws IOException {
        Files.write(
                Paths.get(INTEGRATION_TEST_LOG_DIRECTORY, "screenshot-" + methodName + ".png"),
                ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
        );
    }

    private void captureLogs(WebDriver driver, String methodName) throws IOException {
        LogEntries browser = driver.manage().logs().get("browser");
        String browserLogs = StringUtils.join(
                browser.getAll().stream().map(LogEntry::toString).collect(Collectors.toList()),
                "\n"
        );

        Files.write(
                Paths.get(INTEGRATION_TEST_LOG_DIRECTORY, "browser-logs-" + methodName),
                browserLogs.getBytes(StandardCharsets.UTF_8)
        );
    }
}
