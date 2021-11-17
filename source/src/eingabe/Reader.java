/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eingabe;

import main.Feedback;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import verarbeitung.Staat;

/**
 * Implementation of I_Reader interface
 * 
 * @author milank
 */
public class Reader implements I_Reader {
	
	private String nameKennwert;
	private List<Staat> staaten;
	
	@Override
	public boolean read(String filename) {
		Feedback errorHandling = Feedback.getInstance();
		nameKennwert = "";
		staaten = new ArrayList<>();
		File inputFile = new File(filename);
		Scanner sc = null;
		
		if (!inputFile.exists()) {
			errorHandling.printError("Eingabedatei nicht gefunden", this.getClass().toString());
			return false;
		}
		
		if (!inputFile.canRead()) {
			errorHandling.printError("Keine Leseberechtigung fuer Eingabedatei.", this.getClass().toString());
			return false;
		}
		
		try {
			
			sc = new Scanner(inputFile);
			
			int commentNr = 0;
			while (sc.hasNext()) {
				
				String currentLine = sc.nextLine();
				//trim avoids empty strings in array after split in case of leading white space
				currentLine = currentLine.trim();
						
				if (currentLine.charAt(0) == '#') {
					commentNr++;
					continue;
				} else if (currentLine.contains("#")){ //wenn kommentar in zeile nach daten
					for (int i=0; i<currentLine.length(); i++) {
						if (currentLine.charAt(i) == '#') {
							currentLine = currentLine.substring(0, i);
						}
					}
				}
				
				if (commentNr == 0) {
					
					nameKennwert += currentLine;
					
				} else if (commentNr == 1) {//lese Staaten ein
					
					String[] values = currentLine.split("\\s+");
					
					String kennzeichen =  values[0];
					//verhindern von negativer Flaecher durch Math.abs
					double kennwert = Math.abs( Double.parseDouble(values[1]) );
					double x = Double.parseDouble(values[2]);
					double y = Double.parseDouble(values[3]);
					
					Staat s =  new Staat(kennzeichen, kennwert, x, y);
					staaten.add(s);
					
				} else if (commentNr == 2) {//lese Nachbarrelationen ein
					
					String kennzeichen = "";
					String nachbarString = "";
					for (int i=0; i < currentLine.length(); i++) {
						char currentChar = currentLine.charAt(i);
						if (currentChar == ':') {
							nachbarString = currentLine.substring(i+1);
							break;
						}
						kennzeichen += currentChar;
					}
					nachbarString = nachbarString.trim();
					String[] nachbarn = nachbarString.split("\\s+");
					
					Staat currentStaat = null;
					for (Staat staat: staaten) {
						if (staat.getName().equals(kennzeichen)) {
							currentStaat = staat;
							break;
						}
					}
					
					for (Staat staat: staaten) {
						for (String nachbarName : nachbarn) {
							if (staat.getName().equals(nachbarName)) {
								currentStaat.addNachbar(staat);
								staat.addNachbar(currentStaat);
							}
						}
					}
				}
				
			}
			sc.close();
			
		} catch (FileNotFoundException | NumberFormatException ex) {
			errorHandling.printError(ex.toString(), this.getClass().toString());
			return false;
		}
		
		return true;
	}
	
	@Override
	public String getNameKennwert() {
		return nameKennwert;
	}
	
	@Override
	public List<Staat> getStaaten() {
		return staaten;
	}

}
