package zadanie2;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.DoubleBuffer;
import java.time.Duration;

public class test {
    public static void main(String[] args) throws IOException {

        WebDriver driver;

        // Given użytkownik zaloguje się
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://mystore-testlab.coderslab.pl");

        WebElement singInButton = driver.findElement(By.xpath("//*[@id='_desktop_user_info']/div/a/span"));
        singInButton.click();

        WebElement inputEmail = driver.findElement(By.name("email"));
        inputEmail.sendKeys("wskidssrehxvirfvsw@kvhrs.com");
        WebElement inputPassword = driver.findElement(By.name("password"));
        inputPassword.sendKeys("asdqwe123");

        WebElement singInButton2 = driver.findElement(By.id("submit-login"));
        singInButton2.click();

        // When użytkownik wybierze produkt w ilości <sztuk> i <rozmiar> oraz doda do koszyka
        WebElement searchButtton = driver.findElement(By.name("s"));
        searchButtton.sendKeys("Hummingbird Printed Sweater");
        searchButtton.submit();

        WebElement searchedProduct = driver.findElement(By.xpath("//*[@id='js-product-list']/div[1]/article[1]/div/div[1]/h2/a"));
        Assert.assertEquals(searchedProduct.getText(), "Hummingbird Printed Sweater");
        searchedProduct.click();

        WebElement regularPrice = driver.findElement(By.xpath("//*[@class='product-prices']/div/span"));
        WebElement discountPrice = driver.findElement(By.xpath("//*[@class='product-prices']/div[2]/div/span"));
        double regularPriceInDoubleFormat = Double.parseDouble(regularPrice.getText().substring(1));
        double discountPriceInDoubleFormat = Double.parseDouble(discountPrice.getText().substring(1));

        if (regularPriceInDoubleFormat * 0.8 != discountPriceInDoubleFormat){
            System.out.println("Zniżka naliczona nieprawidłowo");
        }

        WebElement size = driver.findElement(By.id("group_1"));
        size.click();
        WebElement chooseSize = driver.findElement(By.xpath("//*[@id='group_1']/option[2]"));
        chooseSize.click();
//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        WebElement quantity = driver.findElement(By.id("quantity_wanted"));
        quantity.clear();
//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        quantity.sendKeys("5");

        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();

//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WebElement proceedToCheckoutWindow = driver.findElement(By.xpath("//*[@class='cart-content']/div/a"));
        proceedToCheckoutWindow.click();

//        try {
//            Thread.sleep(2000);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        WebElement proceedToCheckout = driver.findElement(By.xpath("//*[contains(text(),'Proceed to checkout')]"));
        proceedToCheckout.click();

        WebElement checkAddressAlias = driver.findElement(By.xpath("//*[@id='delivery-addresses']//span[2]"));
        Assert.assertEquals(checkAddressAlias.getText(), "Adres rejestracyjny");

        WebElement confirmAddress = driver.findElement(By.name("confirm-addresses"));
        confirmAddress.click();

        WebElement choosePickUpInStore = driver.findElement(By.xpath("//*[contains(text(),'Pick up in-store')]"));
        choosePickUpInStore.click();

        WebElement confirmDeliveryOption = driver.findElement(By.name("confirmDeliveryOption"));
        confirmDeliveryOption.click();

        WebElement payByCheckOption = driver.findElement(By.id("payment-option-1"));
        payByCheckOption.click();

        WebElement conditionsApprove = driver.findElement(By.id("conditions_to_approve[terms-and-conditions]"));
        conditionsApprove.click();

        WebElement confirmOrder = driver.findElement(By.xpath("//*[@id='payment-confirmation']/div[1]/button"));
        confirmOrder.click();

        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String fileLocation = "./screenshots/";
        String fileName = "HUMMINGBIRD PRINTED SWEATER";
        String fileNumber = " Test 1";
        String fileExtension = ".jpg";
        FileUtils.copyFile(scrFile, new File(fileLocation+fileName+fileNumber+fileExtension));

        driver.quit();
    }
}
