package manipuster.ru;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CreateContactTest {

    private static final String LOGIN = "user";
    private static final String PASSWORD = "1234";
    private WebDriver driver;


    @BeforeEach
    public void setUpTest (){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown (){
        driver.quit();
    }

    @Test
    public void testCreateContact () throws InterruptedException {

        driver.get("https://crm.geekbrains.space/");

        logIn(driver);

        WebElement dropDown = driver.findElement(By.cssSelector("#main-menu .dropdown"));

        Actions mainMenuDropDown = new Actions(driver);
        mainMenuDropDown
                .moveToElement(dropDown)
                .pause(500)
                .build()
                .perform();

        driver.findElement(By.xpath("//span[.='Контактные лица']/ancestor::a[1]")).click();

        WebDriverWait wait = new WebDriverWait(driver, 5);

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Создать контактное лицо']")));
        driver
                .findElement(By.xpath("//a[@title='Создать контактное лицо']"))
                .click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='crm_contact[lastName]']")));
        driver
                .findElement(By.xpath("//input[@name='crm_contact[lastName]']"))
                .sendKeys("Иванова");
        driver
                .findElement(By.xpath("//input[@name='crm_contact[firstName]']"))
                .sendKeys("Ариадна");

        WebElement selectOrgButton = driver.findElement(By.xpath("//span[.='Укажите организацию']"));
        Actions selectOrg = new Actions(driver);
        selectOrg
                .click(selectOrgButton)
                .sendKeys("123")
                .pause(2000)
                .sendKeys(Keys.ARROW_DOWN)
                .pause(500)
                .sendKeys(Keys.ARROW_DOWN)
                .pause(500)
                .sendKeys(Keys.ARROW_DOWN)
                .pause(500)
                .sendKeys(Keys.ENTER)
                .pause(2000)
                .build()
                .perform();

        driver
                .findElement(By.xpath("//input[@name='crm_contact[jobTitle]']"))
                .sendKeys("manager");

        driver
                .findElement(By.xpath("//a[.='Добавить телефон']"))
                .click();

        driver
                .findElement(By.xpath("//select[@name='crm_contact[phones][1][type]']"))
                .click();

        driver
                .findElement(By.xpath("//select[@name='crm_contact[phones][1][type]']/option[@value='mobile']"))
                .click();

        driver
                .findElement(By.xpath("//input[@name='crm_contact[phones][1][prefixCode]']"))
                .sendKeys("955");

        driver
                .findElement(By.xpath("//input[@name='crm_contact[phones][1][phone]']"))
                .sendKeys("2317892");

        driver
                .findElement(By.xpath("//button[@type='submit' and contains(., 'Сохранить')]"))
                .click();

        Thread.sleep(2000);

        Assertions.assertEquals("Иванова Ариадна",
             driver.findElement(By.cssSelector(".customer-content h1.user-name")).getText());

    }
    private void logIn (WebDriver driver){

        driver.findElement(By.xpath("//input[@name='_username']")).sendKeys(LOGIN);

        driver.findElement(By.xpath("//input[@name='_password']")).sendKeys(PASSWORD);

        driver.findElement(By.xpath("//button[@name='_submit']")).click();

    }
}
