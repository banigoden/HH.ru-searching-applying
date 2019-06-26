import org.junit.*;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MainTest {
    private WebDriver driver;
    private HeadHunter head;

    @BeforeClass
    public void beforeCassMethod(){


    }
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Deni\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://spb.hh.ru/");
        head = new HeadHunter();
    }


    @Test
    public void method(){
        head.connection();
    }
    @After
    public void signIn(){
        driver.quit();
    }
    @AfterClass
    public void afterClassMethod(){

    }
}
