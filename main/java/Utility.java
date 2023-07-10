public class Utility {
    private WebDriver driver;

    public Utility(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForVisibilityOfElement(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Example of Error Handling Utility Method
    public void handleException(Exception e) {
        System.out.println("An exception occurred: " + e.getMessage());
    }

    // Example of Logging Utility Method
    public void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
}
