package crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FrameTest {
	public static void main(String[] args) throws InterruptedException {
		//System.setProperty("webdriver.firefox.bin","C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		//System.setProperty("webdriver.gecko.driver", "D:\\workspace\\MusicItem\\lib\\geckodriver.exe");
		//WebDriver wDriver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver","D:\\workspace\\MusicItem\\lib\\chromedriver.exe");//chromedriver服务地址
        WebDriver wDriver =new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		String url = "http://wan.360.cn/";
		wDriver.get(url);
		String frameXpath = "//iframe[@src='/login_s.html?login_src=index']";
		waitUntilPageLoadedXPath(wDriver, frameXpath);
		WebElement frameElement = wDriver.findElement(By.xpath(frameXpath));
		wDriver.switchTo().frame(frameElement);
		wDriver.findElement(By.xpath("//input[@name='account']")).sendKeys("userName");
	}
	
	public static void waitUntilPageLoadedXPath(WebDriver iw, String v) throws InterruptedException {
		try {
			iw.findElement(By.xpath(v));
		} catch (Exception ex) {
			System.out.println(ex.toString());
			Thread.sleep(1000);
			waitUntilPageLoadedXPath(iw, v);
		}
	}
}
