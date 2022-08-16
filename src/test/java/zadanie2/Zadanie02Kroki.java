package zadanie2;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.time.Duration;
import java.io.IOException;

public class Zadanie02Kroki {
    private WebDriver driver;
    private String lastOrderReference;
    private String lastPriceOfOrder;

    @Given("użytkownik zaloguje się")
    public void logowanie () {
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
    }
    @When("użytkownik wybierze (.*) w ilości (.*) i (.*) oraz doda do koszyka gdzie rozmiar 1-S, 2-M, 3-L, 4-XL$")
    public void wyborProduktu (String product, String quantity, String sizeSMLXL){
        WebElement searchButtton = driver.findElement(By.name("s"));
        searchButtton.sendKeys(product);
        searchButtton.submit();

        WebElement searchedProduct = driver.findElement(By.xpath("//*[@id='js-product-list']/div[1]/article[1]/div/div[1]/h2/a"));
        Assert.assertEquals(searchedProduct.getText(), product);
        searchedProduct.click();

        WebElement regularPrice = driver.findElement(By.xpath("//*[@class='product-prices']/div/span"));
        WebElement discountPrice = driver.findElement(By.xpath("//*[@class='product-prices']/div[2]/div/span"));
        double regularPriceInDoubleFormat = Double.parseDouble(regularPrice.getText().substring(1));
        double discountPriceInDoubleFormat = Double.parseDouble(discountPrice.getText().substring(1));

        String checkDiscount = null;
        if (regularPriceInDoubleFormat * 0.8 == discountPriceInDoubleFormat){
            checkDiscount = "Zniżka naliczona prawidłowo";
        }
        Assert.assertEquals(checkDiscount, "Zniżka naliczona prawidłowo");

        WebElement size = driver.findElement(By.id("group_1"));
        size.click();
        String findElementPath = "//*[@id='group_1']/option";
        String sizeToChoose = "[" + sizeSMLXL + "]";
        WebElement chooseSize = driver.findElement(By.xpath(findElementPath + sizeToChoose));
        chooseSize.click();

        WebElement quantityWanted = driver.findElement(By.id("quantity_wanted"));
        quantityWanted.clear();
        quantityWanted.sendKeys(quantity);

        WebElement addToCartButton = driver.findElement(By.className("add-to-cart"));
        addToCartButton.click();
    }
    @And("przejdzie do checkout i dokona zakupu poprzez zamówienie z obowiązkiem zapłaty")
    public void potwierdzenieZakupu () {

        WebElement proceedToCheckoutWindow = driver.findElement(By.xpath("//*[@class='cart-content']/div/a"));
        proceedToCheckoutWindow.click();

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

        WebElement orderReference = driver.findElement(By.xpath("//*[@id='order-details']/ul/li[1]"));
        lastOrderReference = orderReference.getText().substring(17);

        WebElement totalPrice = driver.findElement(By.xpath("//*[@id='order-items']/div/table/tbody/tr[3]/td[2]"));
        lastPriceOfOrder = totalPrice.getText().substring(1);

    }
    @Then("zrobi screenshot z testu (.*) z potwierdzeniem zamówienia (.*) i kwotą$")
    public void screenshot (String testNumber, String nameOfProduct) throws IOException {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String fileLocation = "./screenshots/";
        String fileNumber = " Test " + testNumber + " ";
        String fileExtension = ".jpg";
        FileUtils.copyFile(scrFile, new File(fileLocation+nameOfProduct+fileNumber+System.currentTimeMillis()+fileExtension));
    }
    @And("sprawdzi czy zamówienie znajduje się w historii zamówień z odpowiednim statusem i kwotą")
    public void sprawdzenieZamowienia (){
        WebElement userAccount = driver.findElement(By.xpath("//*[@id='_desktop_user_info']/div/a[2]/span"));
        userAccount.click();
        WebElement orderHistory = driver.findElement(By.xpath("//*[@id='history-link']/span"));
        orderHistory.click();
        WebElement checkReference = driver.findElement(By.xpath("//*[@id='content']/table/tbody/tr/th"));
        Assert.assertEquals(checkReference.getText(), lastOrderReference);
        WebElement details = driver.findElement(By.xpath("//*[@id='content']/table/tbody/tr/td[6]/a"));
        details.click();
        WebElement awaitingCheckPayment = driver.findElement(By.xpath("//*[@id='order-history']/table/tbody/tr/td[2]/span"));
        Assert.assertEquals(awaitingCheckPayment.getText(), "Awaiting check payment");
        WebElement totalPriceCheck = driver.findElement(By.xpath("//*[@id='order-products']/tfoot/tr[3]/td[2]"));
        Assert.assertEquals(totalPriceCheck.getText().substring(1), lastPriceOfOrder);
    }
    @And("zamknie przeglądarkę")
    public void closeBrowser (){
        driver.quit();
    }
}