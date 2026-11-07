package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    private static final String FORM_URL =
            "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";

    @Test
    public void testCase01() throws InterruptedException {
        Wrappers.navigate(driver, FORM_URL);

        // Enter the learner name and the dynamically generated epoch-time phrase.
        Wrappers.typeElement(driver, By.xpath("(//input[@type='text'])[1]"), "Crio Learner");
        long epochTime = System.currentTimeMillis() / 1000;
        Wrappers.typeElement(driver, By.xpath("//textarea"),
                "I want to be the best QA Engineer! " + epochTime);

        // Select experience, tools, and the preferred form of address.
        Wrappers.clickElement(driver, By.xpath("//*[@role='radio'][1]"));
        Wrappers.clickElement(driver, By.xpath("//*[@role='checkbox' and @aria-label='Java']"));
        Wrappers.clickElement(driver, By.xpath("//*[@role='checkbox' and @aria-label='Selenium']"));
        Wrappers.clickElement(driver, By.xpath("//*[@role='checkbox' and @aria-label='TestNG']"));
        Wrappers.clickElement(driver, By.xpath("//*[@role='listbox']"));
        Wrappers.clickElement(driver, By.xpath("//*[@role='option'][1]"));

        LocalDate dateValue = LocalDate.now().minusDays(7);
        String date = dateValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Wrappers.typeDateElement(driver, By.xpath("//input[@type='date']"), date);
        // Google Forms renders time as separate hour and minute fields.
        Wrappers.typeElement(driver,
                By.xpath("//input[contains(translate(@aria-label, 'HOUR', 'hour'), 'hour')][1]"),
                "07");
        Wrappers.typeElement(driver,
                By.xpath("//input[contains(translate(@aria-label, 'MINUTE', 'minute'), 'minute')][1]"),
                "30");

        Wrappers.clickElement(driver, By.xpath("//span[normalize-space()='Submit']"));
        Thread.sleep(1000);

        String successMessage = Wrappers.getElementText(driver,
                By.xpath("//*[contains(normalize-space(), 'Thanks for your response')]")
        );
        System.out.println("Success message: " + successMessage);
    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}
