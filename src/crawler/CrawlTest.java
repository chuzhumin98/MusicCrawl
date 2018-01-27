package crawler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CrawlTest {
	WebDriver wDriver;
	
	public CrawlTest() {
		System.setProperty("webdriver.chrome.driver","D:\\workspace\\MusicItem\\lib\\chromedriver.exe");//chromedriver服务地址
		wDriver =new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
	}
	
	/**
	 * 转移到main iframe之中
	 */
	public void toMainFrame() {
		String framePath = "//iframe[@src='about:blank']";
		this.waitUntilPageLoadedXPath(framePath);
		//wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//System.out.println("url:"+wDriver.getCurrentUrl());
		WebElement frameElement = wDriver.findElement(By.xpath(framePath));
		wDriver.switchTo().frame(frameElement);
	}
	
	public void getInfo(String url) {
		System.out.println("succeed into the getInfo function");
		wDriver.navigate().to(url);
		if (!url.equals("https://music.163.com/#/playlist?id=2032809330")) {
			wDriver.navigate().back();
		}
		//wDriver.navigate().back();
		String framePath = "//iframe[@src='about:blank']";
		this.waitUntilPageLoadedXPath(framePath);
		//wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("url:"+wDriver.getCurrentUrl());
		WebElement frameElement = wDriver.findElement(By.xpath(framePath));
		wDriver.switchTo().frame(frameElement);
		String favNum = wDriver.findElement(By.xpath("//a[@data-res-action='fav']")).getAttribute("data-count");
		System.out.println("fav num: "+favNum);
		String shareNum = wDriver.findElement(By.xpath("//a[@data-res-action='share']")).getAttribute("data-count");
		System.out.println("share num: "+shareNum);
		String commentNum = wDriver.findElement(By.xpath("//span[@id='cnt_comment_count']")).getText();
		System.out.println("comment num: "+commentNum);
		wDriver.switchTo().defaultContent();
	}
	
	public void waitUntilPageLoadedXPath(String v) {
		try {
			wDriver.findElement(By.xpath(v));
		} catch (Exception ex) {
			System.out.println(ex.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waitUntilPageLoadedXPath(v);
		}
	}
	
	public void getInfo() {
		String framePath = "//iframe[@src='about:blank']";
		this.waitUntilPageLoadedXPath(framePath);
		//wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("url:"+wDriver.getCurrentUrl());
		WebElement frameElement = wDriver.findElement(By.xpath(framePath));
		wDriver.switchTo().frame(frameElement);
		String favNum = wDriver.findElement(By.xpath("//a[@data-res-action='fav']")).getAttribute("data-count");
		System.out.println("fav num: "+favNum);
		String shareNum = wDriver.findElement(By.xpath("//a[@data-res-action='share']")).getAttribute("data-count");
		System.out.println("share num: "+shareNum);
		String commentNum = wDriver.findElement(By.xpath("//span[@id='cnt_comment_count']")).getText();
		System.out.println("comment num: "+commentNum);
		wDriver.switchTo().defaultContent();
	}
	
	public static void main(String[] args) {
		CrawlTest ct1 = new CrawlTest();
        long t1 = System.currentTimeMillis();
        String url = "https://music.163.com/#/discover/playlist";
        ct1.wDriver.navigate().to(url);
        ct1.toMainFrame();
        List<WebElement> elements = ct1.wDriver.findElements(By.xpath("//a[@class='msk']"));
        System.out.println("has "+elements.size()+" music item links in this page.");
        for (int i = 0; i < elements.size(); i++) {
        	if (i != 0) {
        		ct1.toMainFrame(); //避免重复操作
        	}
        	List<WebElement> elementsTmp = ct1.wDriver.findElements(By.xpath("//a[@class='msk']"));
        	elementsTmp.get(i).click();
        	System.out.println(i+"th visit url:"+ct1.wDriver.getCurrentUrl());
        	ct1.wDriver.navigate().back();
        }

        /**
        url = "https://music.163.com/#/playlist?id=2032809330";
		ct1.getInfo(url);
		url = "https://music.163.com/#/playlist?id=2067901040";
		ct1.getInfo(url);
		url = "https://music.163.com/#/playlist?id=2011922640";
		ct1.getInfo(url);
		*/
		long t2 = System.currentTimeMillis();
		System.out.println("cost time:"+(t2-t1)+"ms");
	}
}
