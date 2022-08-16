package zadanie1;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class test {
    public static void main(String[] args) {
        WebDriver driver;
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://mystore-testlab.coderslab.pl");

        WebElement singInButton = driver.findElement(By.xpath("//*[@id='_desktop_user_info']/div/a/span"));
        singInButton.click();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement inputEmail = driver.findElement(By.name("email"));
        inputEmail.sendKeys("wskidssrehxvirfvsw@kvhrs.com");
        WebElement inputPassword = driver.findElement(By.name("password"));
        inputPassword.sendKeys("asdqwe123");
        WebElement singInButton2 = driver.findElement(By.id("submit-login"));
        singInButton2.click();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement addresses = driver.findElement(By.xpath("//*[@id='addresses-link']/span/i"));
        addresses.click();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement createNewAddress = driver.findElement(By.cssSelector("#content > div.addresses-footer > a > span"));
        createNewAddress.click();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement alias = driver.findElement(By.name("alias"));
        alias.sendKeys("alias z ghkerkina");
        WebElement address = driver.findElement(By.name("address1"));
        address.sendKeys("adres z gherkina");
        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys("miasto z gherkina");
        WebElement zipcode = driver.findElement(By.name("postcode"));
        zipcode.sendKeys("postcode z gherkina");
        WebElement country = driver.findElement(By.name("id_country"));
        country.click();
        WebElement chooseCountry = driver.findElement(By.cssSelector("#content > div > div > form > section > div:nth-child(14) > div.col-md-6 > select > option:nth-child(2)"));
        chooseCountry.click();
        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys("123456789");
        WebElement submitAddress = driver.findElement(By.xpath("//*[@id='content']/div/div/form/footer/button"));
        submitAddress.click();

        WebElement updateAddress = driver.findElement(By.xpath("//*[@id='content']/div[last()-2]//div[2]/a//span"));
        updateAddress.click();

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        alias = driver.findElement(By.name("alias"));
        Assert.assertEquals(alias.getAttribute("value"), "alias z ghkerkina");
        WebElement firstName = driver.findElement(By.name("firstname"));
        Assert.assertEquals(firstName.getAttribute("value"), "Jan");
        WebElement lastName = driver.findElement(By.name("lastname"));
        Assert.assertEquals(lastName.getAttribute("value"), "Kowalski");
        address = driver.findElement(By.name("address1"));
        Assert.assertEquals(address.getAttribute("value"), "adres z gherkina");
        city = driver.findElement(By.name("city"));
        Assert.assertEquals(city.getAttribute("value"), "miasto z gherkina");
        zipcode = driver.findElement(By.name("postcode"));
        Assert.assertEquals(zipcode.getAttribute("value"), "postcode z g");
        country = driver.findElement(By.xpath("//*[@id='content']//div[10]//option[2]"));
        Assert.assertEquals(country.getAttribute("value"), "17");
        phone = driver.findElement(By.name("phone"));
        Assert.assertEquals(phone.getAttribute("value"), "123456789");

        driver.navigate().back();

        WebElement deleteAddress = driver.findElement(By.xpath("//*[@id='content']/div[last()-2]//div[2]/a[2]//span"));
        deleteAddress.click();


    }
}
