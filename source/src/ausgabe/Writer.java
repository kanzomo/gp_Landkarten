/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ausgabe;

import main.Feedback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import verarbeitung.Laenderberechnung;
import verarbeitung.Staat;

/**
 * Implementation of I_Writer interface
 *
 * @author milank
 */
public class Writer implements I_Writer {
	
	private String kennwert;
	private String filename;
	
	/**
	 * Constructor
	 * 
	 * @param kennwert name of map characteristic
	 * @param inputFilePath relative path to inputFile
	 */
	public Writer(String kennwert, String inputFilePath) {
		this.kennwert = kennwert;
		filename = FilenameUtils.getBaseName(inputFilePath);
	}
	
	@Override
	public void write(Laenderberechnung berechnung) {
		List<Staat> staaten = berechnung.getStaaten();
		double xMin = berechnung.getXmin();
		double xMax = berechnung.getXmax();
		double yMin = berechnung.getYmin(); 
		double yMax = berechnung.getYmax();
		int iterationen = berechnung.getIterationen();

		Feedback feedback = Feedback.getInstance();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
		String saveDir = "." + File.separator + "output" + File.separator;
		File folder = new File(saveDir);

		// create dir if non-existent
		if( !folder.isDirectory()){
			if(!folder.mkdir()) {
				feedback.printWarning("failed to create missing directory. Output file will be saved in the .jar location.", this.getClass().toString());

				// if directory can't be created, default to same folder as jar
				saveDir = "";
			}
		}

		String filename = saveDir + this.filename + "_" + dtf.format(LocalDateTime.now()) + ".out";
		File outputFile = new File(filename);
		OutputStreamWriter fileWriter = null;

		try {

			fileWriter = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");

			String staatenliste = "";
			int id = 0;
			for (Staat staat : staaten) {
				staatenliste += staat.getX() + " " + staat.getY() + " " + staat.getRadius() + " " + staat.getName() + " " + id + "\n";
				id++;
			}

			String output = "reset\n" +
							"set xrange [" + xMin + ":" + xMax + "]\n" +
							"set yrange [" + yMin + ":" + yMax + "]\n" +
							"set size ratio 1.0\n" +
							"set title \"" + kennwert + ", Iteration: " + iterationen + "\"\n" +
							"unset xtics\n" +
							"unset ytics\n" + 
							"$data << EOD\n" +
							staatenliste + 
							"EOD\n" + 
							"plot \\\n" +
							"'$data' using 1:2:3:5 with circles lc var notitle, \\\n" +
							"'$data' using 1:2:4:5 with labels font \"arial, 9\" tc variable notitle";

			fileWriter.write(output);

			fileWriter.close();

		} catch (IOException ex) {
			feedback.printError(ex.toString(), this.getClass().toString());
		}
	}
}
