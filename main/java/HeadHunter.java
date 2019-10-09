import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class HeadHunter {

    public WebDriver driver ;
    private String coverLatter;
    private String nameOfVacancy;
    private String nameOfCompany;
    private String request;

    private By signInButton = (By.xpath("(.//*[text()='Откликнуться']/..)"));
    public String requestLink;

   // private static final Cookie COOKIE = new Cookie("name", "value", ".hh.ru", "/",2021.08.15);

    public HeadHunter() {

        coverLatter = "Dear sir or madam,\n" +
                "\n" +
                "I submit my resume for the open position of your company." + "\n"

                + "Some activities at my current position correspond to your requirements. My advantage is that I am flexible and well-adapted for multitasking job. Moreover, my java knowledge let me develop a program for Magna company.\n" +
                "\n" +
                "I am interested in the improvement of my Java skills. .\n" +
                "By the way, it's my repository:  https://github.com/banigoden" +" That is why I would be glad to join your team.\n" +
                " \t\n" +
                "I look forward to discussing the position in further detail.\n" +
                "\n" +
                "Best regards,\n" +
                "Denis Bandurin\n";
    }

    public void connection() {

        System.setProperty("webdriver.gecko.driver","C:\\Users\\xbandude1\\IdeaProjects\\projectselenium\\src\\main\\geckodriver.exe");
        System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE,"true");

        // Инициализируем профиль
        FirefoxProfile profile = new FirefoxProfile(new File("C:\\Users\\xbandude1\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\kl1plb6i.default-release")); // надо профиль скопировать
// Указываем профиль в передаваемых опциях
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
// Создаем браузер
         driver = new FirefoxDriver(options);
       // String url = "https://spb.hh.ru/";
        String url = "https://spb.hh.ru/search/vacancy?clusters=true&enable_snippets=true&text=java&area=2&from=cluster_area&showClusters=true";

        driver.get(url);

    }

    public void signIn() {

        String userLogin = "";
        String password = "";
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.cssSelector("button.bloko-button_small > span:nth-child(1)")).click();
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/div[1]/label/input")).sendKeys(userLogin);
        driver.findElement(By.xpath("/html/body/div[4]/div[2]/div/div[3]/div/div/div[2]/form/label/input")).sendKeys(password);
        driver.findElement(By.cssSelector("input.bloko-button_primary-minor")).click();
        //
        driver.findElement(By.cssSelector("input.bloko-button_primary-minor")).click();

    }

    public void findJob() {
        connection();
        click();

    }

    public void selectOptions(String option, String listName) {//  select ComboBox - remote Job
        String options = String.format("(.//*[text()='%s']/..)",option);
        driver.findElement(By.xpath(options)).click();
        driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[3]/div/div[1]/div/div[2]/div/div/div[1]/div[8]/div[2]/ul/li[3]"));
        WebElement e = driver.findElement(By.xpath("//a[contains(@href,'=remote&from')]"));
        driver.navigate().to(e.getAttribute("href"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        click();
    }


    public void click() {

        List<WebElement> links = driver.findElements(By.xpath("//a[contains(@href,'?query=')]"));
        List<WebElement> pages = driver.findElements(By.xpath("//a[contains(@href,'java&page=')]"));
        List <String> page = new ArrayList<String>();
        checkPages(pages, page);


        String lastP= page.get(page.size()-1).substring(Math.max(0, page.get(page.size()-1).length() - 2)); // count all pages
        int lastPage = Integer.valueOf(lastP) +2;
        String linkPage = page.get(page.size()-1).substring(0,page.get(page.size()-1).length() - 2); //link on  page without numbers


        for (int i = 2; i < lastPage; i++) {
            StringBuilder link = new StringBuilder();
                link.append(linkPage).append(i);
                System.out.println(link);
                driver.navigate().to(String.valueOf(link));
                checkVacations(links);
        }
        driver.close();
    }

    private void checkPages(List<WebElement> pages, List<String> page) {
        for (int j = 0; j < pages.size() ; j++) {

            if (!page.contains(pages.get(j).getAttribute("href"))) {
                page.add(pages.get(j).getAttribute("href"));
            }
        }
    }

    public void checkVacations( List<WebElement> links){

        for (int i = 0; i < links.size(); i++) {
            links = driver.findElements(By.xpath("//a[contains(@href,'?query=')]")); //for recovery, because all links lost
            System.out.println(links.get(i).getAttribute("href"));

            driver.navigate().to(links.get(i).getAttribute("href"));

            nameOfVacancy = driver.findElement(By.xpath("//h1[@itemprop='title']")).getText();
            nameOfCompany = driver.findElement(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div[3]/div/div/div/div/p[1]/a[1]/span")).getText();
            System.out.println(nameOfVacancy);
            System.out.println(nameOfCompany);

            boolean isPresent = driver.findElements(By.xpath("/html/body/div[5]/div/div/div[2]/div/div[1]/div[1]/div/div[1]/div[4]/div[2]/div/div/div/div/div[1]/a") ).size() > 0 ||  driver.findElements(By.xpath("/html/body/div[5]/div/div/div[2]/div/div/div[1]/div[1]/div[1]/div[4]/div[4]/div/div/div[2]/div/div[1]/div/a") ).size()>0;

            if (isPresent){
                System.out.println("check element");
                driver.navigate().back();
            }else {
                System.out.println("not check element");
                if( nameOfCompany.contains("ООО Бизнес Технологии") ||nameOfCompany.contains("Сигма")|| (nameOfVacancy.contains("PHP"))|| nameOfCompany.contains("Kelly")){
                    driver.navigate().back();
                }else {

                    boolean isClick = driver.findElements(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).size()>0;
                    if(isClick) {

                        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).sendKeys(coverLatter);
                        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[3]/div/button")).click();
                        driver.navigate().back();
                        System.out.println("yes");

                    }else {
                        System.out.println("no");
                        driver.findElement(signInButton).click();
                        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/span/span")).click();
                        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[2]/div[2]/div/textarea")).sendKeys(coverLatter);
                        driver.findElement(By.xpath("/html/body/div[8]/div[1]/div/form/div[3]/div/button")).click();
                        driver.navigate().back();
                    }
                }
            }
            System.out.println(i);
        }
    }
}
