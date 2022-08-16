package zadanie1;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class Zadanie1Kroki {
    private WebDriver driver;

    @Given("otwarta przeglądarka na stronie mystore-testlab.coderslab.pl")
    public void otwieraniePrzegladarki () {
        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://mystore-testlab.coderslab.pl");
    }

    @When("użytkownik zaloguje się na wcześniej stworzone konto")
    public void logowanie () {
        WebElement singInButton = driver.findElement(By.xpath("//*[@id='_desktop_user_info']/div/a/span"));
        singInButton.click();

        WebElement inputEmail = driver.findElement(By.name("email"));
        inputEmail.sendKeys("wskidssrehxvirfvsw@kvhrs.com");
        WebElement inputPassword = driver.findElement(By.name("password"));
        inputPassword.sendKeys("asdqwe123");
        WebElement singInButton2 = driver.findElement(By.id("submit-login"));
        singInButton2.click();
    }

    @And("wejdzie klikając w kafelek Adresses")
    public void myAddressesPage() {
        WebElement addresses = driver.findElement(By.xpath("//*[@id='addresses-link']/span/i"));
        addresses.click();
    }
    @And("użytkownik kliknie w + Create new address")
    public void createNewAddress() {
        WebElement createNewAddress = driver.findElement(By.xpath("//*[@id='content']/div[last()]/a/span"));
        createNewAddress.click();
    }

    @And("wypełni formularz New adresses danymi pobranymi z tabeli Examples w Gherkinie (.*) (.*) (.*) (.*) (.*)$")
    public void fillData(String newAlias, String newAddress, String newCity, String newPostcode, String newPhone) {

        WebElement alias = driver.findElement(By.name("alias"));
        alias.sendKeys(newAlias);
        WebElement address = driver.findElement(By.name("address1"));
        address.sendKeys(newAddress);
        WebElement city = driver.findElement(By.name("city"));
        city.sendKeys(newCity);
        WebElement zipcode = driver.findElement(By.name("postcode"));
        zipcode.sendKeys(newPostcode);
        WebElement country = driver.findElement(By.name("id_country"));
        country.click();
        WebElement chooseCountry = driver.findElement(By.xpath("//*[@id='content']/div/div/form/section/div[10]/div/select/option[2]"));
        chooseCountry.click();
        WebElement phone = driver.findElement(By.name("phone"));
        phone.sendKeys(newPhone);
        WebElement submitAddress = driver.findElement(By.xpath("//*[@id='content']/div/div/form/footer/button"));
        submitAddress.click();
    }

    @And("sprawdzi czy dane w dodanym adresie są poprawne (.*) (.*) (.*) (.*) (.*) (.*)$")
    public void checkData(String newAlias, String newAddress, String newCity, String newPostcode, String newCountry, String newPhone) {
        WebElement updateAddress = driver.findElement(By.xpath("//*[@id='content']/div[last()-2]//div[2]/a//span"));
        updateAddress.click();

        WebElement alias = driver.findElement(By.name("alias"));
        Assert.assertEquals(alias.getAttribute("value"), newAlias);
        WebElement firstName = driver.findElement(By.name("firstname"));
        Assert.assertEquals(firstName.getAttribute("value"), "Jan");
        WebElement lastName = driver.findElement(By.name("lastname"));
        Assert.assertEquals(lastName.getAttribute("value"), "Kowalski");
        WebElement address = driver.findElement(By.name("address1"));
        Assert.assertEquals(address.getAttribute("value"), newAddress);
        WebElement city = driver.findElement(By.name("city"));
        Assert.assertEquals(city.getAttribute("value"), newCity);
        WebElement postcode = driver.findElement(By.name("postcode"));
        Assert.assertEquals(postcode.getAttribute("value"), newPostcode);
        WebElement country = driver.findElement(By.xpath("//*[@id='content']//div[10]//option[2]"));
        Assert.assertEquals(country.getAttribute("value"), newCountry);
        WebElement phone = driver.findElement(By.name("phone"));
        Assert.assertEquals(phone.getAttribute("value"), newPhone);
    }

    @And("usunie powyższy adres klikając w delete")
    public void deleteAddress () {
        driver.navigate().back();

        WebElement deleteAddress = driver.findElement(By.xpath("//*[@id='content']/div[last()-2]//div[2]/a[2]//span"));
        deleteAddress.click();
    }

    @When("sprawdzi czy adres został usunięty (.*)$")
    public void checkRemoval (String alias){
        WebElement checkRemovalByAlias = driver.findElement(By.xpath("//*[@id='content']/div//div/h4"));
        Assert.assertNotEquals(checkRemovalByAlias.getText(), alias);
    }

    @Then("zamknie przeglądarkę")
    public void closeBrowser (){
        driver.quit();
    }
}