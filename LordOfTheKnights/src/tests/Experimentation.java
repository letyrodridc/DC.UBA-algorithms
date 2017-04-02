package tests;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import solution.Knight;
import solution.TheLordOfTheKnights;

/**
 * This class runs experimentation of The Lord of the Horses
 * @author letyrodridc
 *
 */
public class Experimentation {
	
	public static void main(String args[]) throws IOException {
		run();
	}
	
	public static void run() throws IOException {
		String baseDir = "/home/nova/";
		String filenameOut = "conpodas.out";
		int tries = 5;
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(baseDir+filenameOut);
			
			for (int i=1; i< 7; i++) {
				for (int j=0; j< tries; j++) {
					TheLordOfTheKnights game = new TheLordOfTheKnights(i,new ArrayList<Knight>());
					
					long time = game.solve();
					String result = i+" "+time;
					
					writer.write(result+"\n");
					writer.flush();
				}
			}
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
