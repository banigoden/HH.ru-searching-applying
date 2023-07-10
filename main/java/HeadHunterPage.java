public class HeadHunterPage {

    private WebDriver driver;

    // Locators
    private By signInButton = By.cssSelector("button.bloko-button_small > span:nth-child(1)");
    private By usernameField = By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/div[1]/label/input");
    private By passwordField = By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/label/input");

    public HeadHunterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }
}
