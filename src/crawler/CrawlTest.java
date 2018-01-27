package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CrawlTest {
	public WebDriver wDriver;
	public int failTimes = 0; //当前失败的次数
	public int nowStatus = 0; //当前的状态，0为列表界面，1为歌单界面
	public PrintStream out;
	
	public CrawlTest() {
		System.setProperty("webdriver.chrome.driver","D:\\workspace\\MusicItem\\lib\\chromedriver.exe");//chromedriver服务地址
		wDriver =new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		try {
			out = new PrintStream(new File("output/songlist1.txt"));
			out.println("id title time fav share comment song play tags");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 转移到main iframe之中
	 */
	public void toMainFrame() {
		String framePath = "//iframe[@src='about:blank']";
		this.waitUntilPageLoadedIFrame(framePath, "//ul[@id='m-pl-container']");
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
			//System.out.println(ex.toString());
			System.out.println(this.wDriver.getPageSource());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			waitUntilPageLoadedXPath(v);
		}
	}
	
	public void waitUntilPageLoadedIFrame(String v, String w) {
		try {
			WebElement frameElement = wDriver.findElement(By.xpath(v));
			wDriver.switchTo().frame(frameElement);
			this.wDriver.findElement(By.xpath(w));
			this.failTimes = 0;
		} catch (Exception ex) {
			//System.out.println(ex.toString());
			System.out.println(this.wDriver.getCurrentUrl());
			//System.out.println(this.wDriver.getPageSource());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.wDriver.switchTo().defaultContent();
			this.failTimes++;
			if (failTimes >= 5) {
				if (this.nowStatus == 0) {
					this.wDriver.navigate().forward();
					this.wDriver.navigate().back();
				} else if (this.nowStatus == 1) {
					this.wDriver.navigate().back();
					this.wDriver.navigate().forward();
				}
			}
			this.waitUntilPageLoadedIFrame(v, w);
		} finally {
			this.wDriver.switchTo().defaultContent();
		}
	}
	
	public void getInfo() {
		try {
			String framePath = "//iframe[@src='about:blank']";
			this.waitUntilPageLoadedIFrame(framePath, "//a[@data-res-action='fav']");
			//wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			String url = this.wDriver.getCurrentUrl();
			System.out.println("url:"+wDriver.getCurrentUrl());
			WebElement frameElement = wDriver.findElement(By.xpath(framePath));
			wDriver.switchTo().frame(frameElement);
			//wDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//System.out.println(this.wDriver.getPageSource());
			//this.waitUntilPageLoadedXPath("//a[@data-res-action='fav']");
			String[] ids = url.split("=");
			System.out.println("id:"+ids[1]);
			String listTitle = this.wDriver.findElement(By.xpath("//h2[@class='f-ff2 f-brk']")).getText();
			System.out.println("title:"+listTitle);
			String timeString = this.wDriver.findElement(By.xpath("//span[@class='time s-fc4']")).getText();
			String[] timeSplits = timeString.split(" ");
			System.out.println("create time:"+timeSplits[0]);
			String favNum = wDriver.findElement(By.xpath("//a[@data-res-action='fav']")).getAttribute("data-count");
			System.out.println("fav num: "+favNum);
			String shareNum = wDriver.findElement(By.xpath("//a[@data-res-action='share']")).getAttribute("data-count");
			System.out.println("share num: "+shareNum);
			String commentNum = wDriver.findElement(By.xpath("//span[@id='cnt_comment_count']")).getText();
			System.out.println("comment num: "+commentNum);
			String songNum = this.wDriver.findElement(By.xpath("//span[@id='playlist-track-count']")).getText();
			System.out.println("songNum:"+songNum);
			String playNum = this.wDriver.findElement(By.xpath("//strong[@id='play-count']")).getText();
			System.out.println("playNum:"+playNum);
			List<WebElement> tags = this.wDriver.findElements(By.xpath("//a[@class='u-tag']"));
			String tagsString = "";
			for (int i = 0; i < tags.size(); i++) {
				WebElement item = tags.get(i);
				tagsString += item.findElement(By.tagName("i")).getText();
				if (i != tags.size() - 1) {
					tagsString += ",";
				}
			}
			System.out.println("tag name:"+tagsString);
			String infos = ids[1]+" "+listTitle+" "+timeSplits[0]+" "+favNum+" "
						+shareNum+" "+commentNum+" "+songNum+" "+playNum+" "+tagsString;
			out.println(infos);
			//this.wDriver.findElement(By.xpath("//a[@id='album-desc-spread']")).click();
			String desribeString;
			try {
				desribeString = this.wDriver.findElement(By.xpath("//p[@id='album-desc-dot']")).getText();
			} catch (Exception ex) {
				System.out.println("haven't dot desc!");
				desribeString = this.wDriver.findElement(By.xpath("//p[@id='album-desc-more']")).getText();
			}
			System.out.println("description:"+desribeString);
			wDriver.switchTo().defaultContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void crawlOnePage() {
        this.toMainFrame();
        List<WebElement> elements = this.wDriver.findElements(By.xpath("//a[@class='msk']"));
        System.out.println("has "+elements.size()+" music item links in this page.");
        for (int i = 0; i < elements.size(); i++) {
        	try {
        		if (i != 0) {
            		this.toMainFrame(); //避免重复操作
            	}
            	List<WebElement> elementsTmp = this.wDriver.findElements(By.xpath("//a[@class='msk']"));
            	elementsTmp.get(i).click();
            	this.nowStatus = 1;
            	System.out.println(i+"th visit url:"+this.wDriver.getCurrentUrl());
            	this.getInfo();
            	this.wDriver.navigate().back();
            	this.nowStatus = 0;
            	System.out.println("has returned to the list");
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
        }
	}
	
	public static void main(String[] args) {
		CrawlTest ct1 = new CrawlTest();
        long t1 = System.currentTimeMillis();
        String url = "https://music.163.com/#/discover/playlist";
        ct1.wDriver.navigate().to(url);
        ct1.crawlOnePage();
        /**
        ct1.toMainFrame();
        //移动到页面最底部  
        ((JavascriptExecutor)ct1.wDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)"); 
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        ct1.wDriver.findElement(By.xpath("//a[@class='zbtn znxt']")).click();
        ct1.nowStatus = 1;
        ct1.crawlOnePage();
		*/

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
