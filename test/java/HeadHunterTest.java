public class HeadHunterTest {
    private WebDriver driver;
    private HeadHunterPage headHunterPage;

    @Before
    public void setup() {
        // WebDriver setup
        driver = new FirefoxDriver();
        headHunterPage = new HeadHunterPage(driver);
    }

    @Test
    public void signInTest() {
        headHunterPage.clickSignInButton();
        headHunterPage.enterUsername("your_username");
        headHunterPage.enterPassword("your_password");
    }
}
