package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CrawlSongList {
	public static final String testURL = "https://music.163.com/#/playlist?id=924680166"; 
	 protected static final String ID_RESULTBOX = "cnt_comment_count";
	 protected static final String ENCODING = "UTF-8";
	
	public void crawlSongList(String url) throws IOException {
		InputStream is = null;
		Document doc = null;
		Element ele = null;
		try {

			System.out.println(url);
			// connect & download html
			/*URL urls = new URL(url);
		   	HttpURLConnection con = (HttpURLConnection) urls.openConnection(); 
		   	con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
		   	BufferedReader breader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		   	System.out.println(breader);
		   	while (true) {
			   String tmp = breader.readLine();
			   if (tmp == null) break;
			   System.out.println(tmp);
		   	} */
		  
		   
		   
			Runtime rt = Runtime.getRuntime();  
			Process p = rt.exec("phantomjs.exe D:/code.js "+url);//这里我的codes.js是保存在c盘下面的phantomjs目录
			is = p.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(is));
		   	System.out.println(breader);
		   	while (true) {
			   String tmp = breader.readLine();
			   if (tmp == null) break;
			   System.out.println(tmp);
		   	}
			//System.out.println(is);
			// parse html by Jsoup
			doc = Jsoup.parse(is, ENCODING, "");
			ele = doc.getElementById(ID_RESULTBOX);
			System.out.println(ele);
			String result = ele.text();
			return;

		} finally {
			IOUtils.closeQuietly(is);
		  	is = null;
		  	doc = null;
		  	ele = null;
		}
	}
	
	public static void main(String[] args) throws IOException {
		CrawlSongList csl = new CrawlSongList();
		csl.crawlSongList(CrawlSongList.testURL);
	}
}
