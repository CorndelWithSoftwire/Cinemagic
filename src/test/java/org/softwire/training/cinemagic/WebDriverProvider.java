package org.softwire.training.cinemagic;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Provide a web driver which can be automatically injected into tests.
 *
 * TODO: share driver instances between tests?
 */
@Component
public class WebDriverProvider {

    @Bean
    public WebDriver webDriver() {
        ChromeOptions options = new ChromeOptions();

        // Configure Chrome to run in 'headless' mode
        // Comment out this line to run a full Chrome window for debugging
        options.addArguments("--headless", "--disable-gpu");

        ChromeDriverManager.getInstance().setup();
        return new ChromeDriver(options);
    }
}
