package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class CommentCrawl {
	public WebDriver wDriver;
	public int failTimes = 0; //当前失败的次数
	public PrintStream out;
	
	public CommentCrawl() {
		System.setProperty("webdriver.chrome.driver","D:\\workspace\\MusicItem\\lib\\chromedriver.exe");//chromedriver服务地址
		wDriver =new ChromeDriver(); //新建一个WebDriver 的对象，但是new 的是FirefoxDriver的驱动
		try {
			out = new PrintStream(new File("output/songcommentlist1.txt"));
			out.println("id title time fav share comment song play tags");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 转移到main iframe之中
	 */
	public void toMainFrame(String w) {
		String framePath = "//iframe[@src='about:blank']";
		this.waitUntilPageLoadedIFrame(framePath, w);
		//wDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//System.out.println("url:"+wDriver.getCurrentUrl());
		WebElement frameElement = wDriver.findElement(By.xpath(framePath));
		wDriver.switchTo().frame(frameElement);
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
				this.wDriver.navigate().refresh();
			}
			this.waitUntilPageLoadedIFrame(v, w);
		} finally {
			this.wDriver.switchTo().defaultContent();
		}
	}
	
	public void getInfo() {
		this.toMainFrame("//div[@class='cmmts j-flag']");
		List<WebElement> comments = this.wDriver.findElements(By.xpath("//div[@class='cnt f-brk']"));
		for (WebElement item: comments) {
			String commentTotal = item.getText();
			String[] commentSplit = commentTotal.split("：", 2);
			String comment1 = commentSplit[Math.min(2, commentSplit.length)-1];
			System.out.println(comment1);
		}
		
	}
	
	public static void main(String[] args) {
		CommentCrawl cc1 = new CommentCrawl();
		long t1 = System.currentTimeMillis();
        String url = "https://music.163.com/#/playlist?id=37432514";
        cc1.wDriver.navigate().to(url);
        cc1.getInfo();
        
		long t2 = System.currentTimeMillis();
		System.out.println("cost time:"+(t2-t1)+"ms");
	}
}
