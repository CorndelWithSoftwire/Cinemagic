package org.softwire.training.cinemagic.integration;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * Provide a {@link WebDriver} which can be automatically injected into tests.
 */
@Component
public class WebDriverProvider {

    /**
     * Set this to 'false' to disable headless mode and allow debugging with developer tools.
     */
    private static final boolean HEADLESS = true;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public WebDriver webDriver() {
        ChromeDriverManager.getInstance().setup();

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();

        capabilities.setCapability(CapabilityType.LOGGING_PREFS, verboseLoggingPrefs());
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions());

        return new ChromeDriver(capabilities);
    }

    /**
     * Collect all logs from the browser.
     */
    private LoggingPreferences verboseLoggingPrefs() {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        return logPrefs;
    }

    /**
     * Generate Chrome-specific options
     */
    private ChromeOptions chromeOptions() {
        ChromeOptions options = new ChromeOptions();

        if (HEADLESS) {
            options.addArguments("--headless", "--disable-gpu");
        }

        // We need the window to be big enough that we get a decent screenshot on failure
        options.addArguments("--window-size=1024,1024");
        return options;
    }
}
