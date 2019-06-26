import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class HeadHunter {

    public WebDriver driver ;
    private String coverLatter;
    private String nameOfVacancy;
    private String nameOfCompany;
    private String request;
    private By signInButton = (By.xpath("(.//*[text()='Откликнуться']/..)"));
    public String requestLink;
    public HeadHunter() {

        coverLatter = "Dear sir or madam,\n" +
                "\n" +
                "I submit my resume for the open position of your company." + "\n"

                + "Some activities at my current position correspond to your requirements. My advantage is that I am flexible and well-adapted for multitasking job. Moreover, my java knowledge let me develop a program for Magna company.\n" +
                "\n" +
                "I am interested in the improvement of my Java skills. Also, I enjoy testing web interfaces. In addition, I have developed an app to auto send my resume on hh.ru Please follow by next link: https://github.com/banigoden/Selenium . Check my Selenium test program.\n" +
                "By the way, it's my repository:  https://github.com/banigoden" +" That is why I would be glad to join your team.\n" +
                " \t\n" +
                "I look forward to discussing the position in further detail.\n" +
                "\n" +
                "Best regards,\n" +
                "\n";
    }

    public void connection() {
        
        System.setProperty("webdriver.chrome.driver","C:\\Users\\Deni\\Documents\\chromedriver.exe");
        driver = new ChromeDriver();
        String url = "https://spb.hh.ru/";
        driver.get(url);
    }

    public void signIn() {

        String userLogin = "";
        String password = "";
        connection();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("button.bloko-button_small > span:nth-child(1)")).click();
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/div[1]/label/input")).sendKeys(userLogin);
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/label/input")).sendKeys(password);
        driver.findElement(By.cssSelector("input.bloko-button_primary-minor")).click();
    }

    public void findJob() {

        request = "junior java";
//        if(    request.contains(" ")){
//            requestLink =request.replace(" ", "%20");
//            System.out.println(requestLink);
//        }else{
//            requestLink = request;
//            System.out.println(requestLink);
//        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".HH-Supernova-Search-Input")).sendKeys(request);
        driver.findElement(By.cssSelector("button.bloko-button")).click();
        //selectOptions("График работы","Удаленная работа");
        //selectOptions("График работы","Гибкий график");
        click();
    }

    public void selectOptions(String option, String listName) {//  select ComboBox - remote Job
        String options = String.format("(.//*[text()='%s']/..)",option);
        String nameOptionxPath = String.format("(.//*[text()='%s']/..)",listName);
        driver.findElement(By.xpath(options)).click();
        driver.findElement(By.xpath(nameOptionxPath)).click();
        //click();
    }

    public void click() {

        List<WebElement> links = driver.findElements(By.xpath("//a[contains(@href,'?query=')]"));

       // List<WebElement> links = driver.findElements(By.xpath(String.format("//a[contains(@href,'?query=%s')]",requestLink))); // /vacancy/

        List<WebElement> pages = driver.findElements(By.xpath("//a[contains(@href,'java&page=')]"));
        List <String> page = new ArrayList<String>();

        for (int j = 0; j < pages.size() ; j++) {

            if (!page.contains(pages.get(j).getAttribute("href"))) {
                page.add(pages.get(j).getAttribute("href"));
            }
        }

        for (String p : page) {

            driver.navigate().to(p);
            for (int i = 0; i < links.size(); i++) {
                links = driver.findElements(By.xpath("//a[contains(@href,'?query=')]")); //for recovery, because all links lost
                System.out.println(links.get(i).getAttribute("href"));

                driver.navigate().to(links.get(i).getAttribute("href"));

                nameOfVacancy = driver.findElement(By.xpath("//h1[@itemprop='title']")).getText();
                nameOfCompany = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div[3]/div/div/div/div/p[1]/a[1]/span")).getText();
                System.out.println(nameOfVacancy);
                System.out.println(nameOfCompany);

                boolean isPresent = driver.findElements(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[1]/div[1]/div/div[1]/div[4]/div[2]/div/div/div/div/div[1]/a") ).size() > 0 || driver.findElements(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div[4]/div[2]/div/div/div/div/div[1]/a") ).size()>0;

                if (isPresent){
                    driver.navigate().back();
                }else {
                    if( nameOfCompany.contains("Crossover")|| nameOfCompany.contains("Бизнес технологии")){
                        driver.navigate().back();
                    }else {

                        boolean isClick = driver.findElements(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).size()>0;
                       if(isClick) {
                           System.out.println("yes");
                           driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).sendKeys(coverLatter);
                           driver.navigate().back();

                       }else {
                           System.out.println("no");
                           driver.findElement(signInButton).click();
                           driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/span/span")).click();
                           driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).sendKeys(coverLatter);
                           driver.navigate().back();
                       }
                    }
                }
                System.out.println(i);
            }

            System.out.println(p);
        }
        driver.close();
    }


    public static boolean isElementPresent(WebDriver driver, By by) {
        try {
            return true;  // Success!
        } catch (NoSuchElementException ignored) {

            driver.navigate().back();
            return false;
        }
    }
}