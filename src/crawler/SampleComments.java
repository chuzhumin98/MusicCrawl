package crawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SampleComments {
	public static final int sampleSize = 1000;
	public static final String initPath = "output/songcommentsample1.txt";
	
	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(new File(initPath));
			//String outPath = "output/commentsample/sample1.txt";
			ArrayList<String> total = new ArrayList<String>();
			while (input.hasNextLine()) {
				String tmp = input.nextLine();
				if (tmp != null && tmp.length() > 0) {
					total.add(tmp);
				}
			}
			System.out.println("total size:"+total.size());
			Random random = new Random();
			for (int i = 1; i <= 4; i++) {
				String outPath = "output/commentsample/sample"+Integer.toString(i)+".txt";
				PrintStream out = new PrintStream(new File(outPath));
				for (int j = 0; j < SampleComments.sampleSize; j++) {
					int index = random.nextInt(total.size());
					out.println(total.get(index));
					total.remove(index);
				}
				out.close();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
