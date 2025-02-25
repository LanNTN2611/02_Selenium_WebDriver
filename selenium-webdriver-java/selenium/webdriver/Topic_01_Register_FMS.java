package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

public class Topic_01_Register_FMS {
    WebDriver driver;
    WebDriverWait explicitWait;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String email = getEmailAddress(), name ="Nhân viên",password = "Ab@123",confirmpw = "Ab@123";
    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }
        driver = new ChromeDriver();
        explicitWait = new WebDriverWait(driver,Duration.ofSeconds(20));
        driver.manage().window().maximize();
        driver.get("https://v2fmstest.ziz.vn/login");
        driver.findElement(By.xpath("//span[@class='text-forgetpass pointer']")).click();
    }
    @Test
    public void Register_01_Empty_Data(){
        //action
        driver.findElement(By.id("tenDangNhap")).clear();
        driver.findElement(By.id("hoTen")).clear();
        driver.findElement(By.id("matkhau")).clear();
        driver.findElement(By.id("xacnhanmatkhau")).clear();
        driver.findElement(By.xpath("//input[@formcontrolname='ngaySinh']")).clear();
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
        //verify
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class ='col-sm-6 col-12 padding_form_cus has-error']/descendant::span[@class='help-block']")).getText(), "Trường dữ liệu này không được để trống");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class ='col-sm-6 col-12 padding_form_right_cus has-error']/descendant::span[@class='help-block']")).getText(), "Trường dữ liệu này không được để trống");
        Assert.assertEquals(driver.findElement(By.xpath("//form[@id='createUserForm']/descendant::input[@Id='matkhau']/following-sibling::div/descendant::span[@class='help-block']")).getText(), "Trường dữ liệu này không được để trống");
        Assert.assertEquals(driver.findElement(By.xpath("//form[@id='createUserForm']/descendant::input[@Id='xacnhanmatkhau']/following-sibling::div/descendant::span[@class='help-block']")).getText(), "Trường dữ liệu này không được để trống");

        driver.quit();
    }
    @Test
    public void Register_02_Email_exists() {
        driver.findElement(By.id("tenDangNhap")).sendKeys("congtylantest@gmail.com");
        driver.findElement(By.id("hoTen")).sendKeys(name);
        driver.findElement(By.id("matkhau")).sendKeys(password);
        driver.findElement(By.id("xacnhanmatkhau")).sendKeys(confirmpw);
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
        //verify
        WebElement notification = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.toast-message")
        ));
        Assert.assertEquals(notification.getText().trim(),
                "Email đã tồn tại.");
        driver.quit();
    }
    @Test
    public void Register_03_Invalid_Email() {
        driver.findElement(By.id("tenDangNhap")).sendKeys("congtylantest");
        driver.findElement(By.id("hoTen")).sendKeys(name);
        driver.findElement(By.id("matkhau")).sendKeys(password);
        driver.findElement(By.id("xacnhanmatkhau")).sendKeys(confirmpw);
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
        //verify
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class ='col-sm-6 col-12 padding_form_cus has-error']/descendant::span[@class='help-block']")).getText(), "Email không đúng định dạng");
        driver.quit();
    }
    @Test
    public void Register_04_Invalid_password(){
        //action
        driver.findElement(By.id("tenDangNhap")).sendKeys(email);
        driver.findElement(By.id("hoTen")).sendKeys(name);
        driver.findElement(By.id("matkhau")).sendKeys("123");
        driver.findElement(By.id("xacnhanmatkhau")).sendKeys(confirmpw);
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
       //verify
        WebElement notification = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.toast-message")
        ));
        Assert.assertEquals(notification.getText().trim(),
                "Mật khẩu phải bao gồm tối thiểu 6 ký tự bao gồm cả chữ, số, ký tự đặc biệt và 1 chữ cái viết hoa.");
        driver.quit();
    }
    @Test
    public void Register_05_Invalid_confirmpw() {
        driver.findElement(By.id("tenDangNhap")).sendKeys(email);
        driver.findElement(By.id("hoTen")).sendKeys(name);
        driver.findElement(By.id("matkhau")).sendKeys(password);
        driver.findElement(By.id("xacnhanmatkhau")).sendKeys("123");
        driver.findElement(By.xpath("//button[@class='login100-form-btn']")).click();
        //verify
        Assert.assertEquals(driver.findElement(By.xpath("//form[@id='createUserForm']/descendant::input[@Id='xacnhanmatkhau']/following-sibling::div/descendant::span[@class='help-block']")).getText(), "Mật khẩu không khớp");
        driver.quit();
    }
    @Test
    public void Register_06_Success() {

        driver.quit();
    }
    public String getEmailAddress(){
        Random rand=new Random();
        return "nhavien" + rand.nextInt(99999) + "@gmail.fms";
    }
    public void sleepInSeconds(long timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}