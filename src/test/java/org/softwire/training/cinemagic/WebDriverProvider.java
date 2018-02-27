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

    /**
     * Set this to 'false' to disable headless mode and allow debugging with developer tools.
     */
    private static final boolean HEADLESS = true;

    @Bean
    public WebDriver webDriver() {
        ChromeOptions options = new ChromeOptions();

        if (HEADLESS) {
            options.addArguments("--headless", "--disable-gpu");
        }

        ChromeDriverManager.getInstance().setup();
        return new ChromeDriver(options);
    }
}
