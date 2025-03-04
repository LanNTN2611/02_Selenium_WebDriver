package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
//Thực hành với từng hàm
public class WebBrowser_Ex {
    WebDriver driver;

    @BeforeClass
    public void beforeClass(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));//wait cho việc tìm element chứ k wait cho page
    }
    @Test
    public void TC_01_Page_URL() {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
        //driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();// -cách viết của CSS
        sleepInSeconds(2);//đây là sleep cứng 2 giây để chuyển trang

        Assert.assertEquals(driver.getCurrentUrl(),"https://live.techpanda.org/index.php/customer/account/login/");//lấy ra url của trang đang có

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getCurrentUrl(),"https://live.techpanda.org/index.php/customer/account/create/");
    }
    @Test
    public void TC_02_Page_title() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getTitle(),"Customer Login");

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
    }
    @Test
    public void TC_03_Page_Navigation() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(2);

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSeconds(2);

        driver.navigate().back();
        sleepInSeconds(2);
        Assert.assertEquals(driver.getCurrentUrl(),"https://live.techpanda.org/index.php/customer/account/login/");

        driver.navigate().forward();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
    }
    @Test
    public void TC_04_Page_Source() {
        driver.get("http://live.techpanda.org/");

        driver.findElement(By.cssSelector("div.footer a[title='My Account']")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getTitle(),"Customer Login");
        Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));//verify tương đối

        driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
        sleepInSeconds(2);

        Assert.assertEquals(driver.getTitle(),"Create New Customer Account");
        Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
    }
    public void sleepInSeconds(long timeInsecond){
        try {
            Thread.sleep(timeInsecond * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void afterClass(){
        driver.quit();
    }
}
