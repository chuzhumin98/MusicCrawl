package crawler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class PhantomjsTest {
	public PhantomjsTest() {
		
	}
	
	public static void main(String[] args) {
	    //设置必要参数
	    DesiredCapabilities dcaps = new DesiredCapabilities();
	    //ssl证书支持
	    dcaps.setCapability("acceptSslCerts", true);
	    //截屏支持
	    dcaps.setCapability("takesScreenshot", true);
	    //css搜索支持
	    dcaps.setCapability("cssSelectorsEnabled", true);
	    //js支持
	    dcaps.setJavascriptEnabled(true);
	    //驱动支持
	    //dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,"C:\\Python34\\Scripts\\phantomjs.exe");
	    //创建无界面浏览器对象
	    PhantomJSDriver wDriver = new PhantomJSDriver(dcaps);
	    System.out.println("succeed load driver");
	    long t1 = System.currentTimeMillis();
        String url = "https://music.163.com/#/playlist?id=2032809330";
		wDriver.get(url);
		System.out.println("url1:"+url);
		wDriver.get(url);
		System.out.println("url:"+url);
		String framePath = "//iframe[@src='about:blank']";
		WebElement frameElement = wDriver.findElement(By.xpath(framePath));
		wDriver.switchTo().frame(frameElement);
		String favNum = wDriver.findElement(By.xpath("//a[@data-res-action='fav']")).getAttribute("data-count");
		System.out.println("fav num: "+favNum);
		String shareNum = wDriver.findElement(By.xpath("//a[@data-res-action='share']")).getAttribute("data-count");
		System.out.println("share num: "+shareNum);
		String commentNum = wDriver.findElement(By.xpath("//span[@id='cnt_comment_count']")).getText();
		System.out.println("comment num: "+commentNum);
		long t2 = System.currentTimeMillis();
		System.out.println("cost time:"+(t2-t1)+"ms");
	}
}
