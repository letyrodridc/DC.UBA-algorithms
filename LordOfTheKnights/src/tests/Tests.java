package tests;


import java.io.IOException;
import java.util.ArrayList;

import solution.Knight;
import solution.Box;
import solution.TheLordOfTheKnights;


public class Tests {
	
	public static String testDir = "/home/nova/workjava/algo3_tps/tp1/tests/ej3/";
	
	
	@SuppressWarnings("unused")
	private static void test01() throws IOException {

		Knight c1 = new Knight(new Box(0,0));
		Knight c2 = new Knight(new Box(1,1));
		Knight c3 = new Knight(new Box(1,2));
		Knight c4 = new Knight(new Box(0,1));
		Knight c5 = new Knight(new Box(0,2));
		
		
		ArrayList<Knight> caballos = new ArrayList<Knight>();
		caballos.add(c1);
		caballos.add(c2);
		caballos.add(c3);
		caballos.add(c4);
		//caballos.add(c5);
		
		TheLordOfTheKnights juego = new TheLordOfTheKnights(3,caballos);
		juego.printBoard();
		
		long time = juego.solve();
		
		
		System.out.println("Time:"+time/1000000.0);
		
		//juego.imprimirTableroResuelto();
	}
	
	private static void testFile(String filenameIn) throws IOException {
		TheLordOfTheKnights juego = new TheLordOfTheKnights(testDir+filenameIn);
		System.out.println("---- Solving file ---"+filenameIn);
		
	
		long time = juego.solve();
		System.out.println("Time:"+time/1000000.0);
		juego.printSolution();
		
	}
	
	public static void runTestFiles() throws IOException {
		int i;
		
		for  (i = 1; i <=9; i++) {
			testFile("00"+i+".in");
		}
		
		for (; i <= 39; i++) {
			testFile("0"+i+".in");
		}
	}
	

}
  