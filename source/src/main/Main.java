/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import eingabe.Reader;
import ausgabe.I_Writer;
import ausgabe.Writer;
import eingabe.I_Reader;
import verarbeitung.Laenderberechnung;

/**
 * Main class that manages objects and interactions between the different IPO (EVA) models
 * 
 * @author milank
 */
public class Main {

	/**
	 * @param args commandline arguments, needs to contain path to file,
	 * may also contain max iterations and desired deviation
	 */
	public static void main(String[] args) {
		
		// DARF NICH IN JAR, NUR ZU TESTZWECKEN
//			String filename = ".\\test\\input\\bsp3.in";
//			args = new String [1];
//			args[0] = filename;
		// ENDE
		
		Feedback feedback = Feedback.getInstance();

		if (args.length == 0) {
			
			feedback.printHelp();
			
		} else {
			I_Reader reader = new Reader();
			
			if (reader.read(args[0])) {
				
				I_Writer writer = new Writer(reader.getNameKennwert(), args[0]);
				
				int maxIterationen = 100;
				double toleranz = 0.1;
				if (args.length > 1) {
					maxIterationen = Math.abs(Integer.parseInt(args[1]));
					
					if (args.length > 2) {
						toleranz = Math.abs(Double.parseDouble(args[2]));
					}
				} else {
					feedback.printParameterHelp(maxIterationen, toleranz);
				}
				
				Laenderberechnung laenderBerechnung = new Laenderberechnung(reader.getStaaten(), maxIterationen, toleranz);

				laenderBerechnung.berechneKartenDarstellung();

				writer.write(laenderBerechnung);
			} else {
				feedback.printError("Unable to parse file, exit program.", "class main.Main");
			}
			
		}
	}
	
}
