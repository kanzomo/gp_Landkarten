/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verarbeitung;

import main.Feedback;
import java.util.List;

/**
 * This class implements the main algorithm and manages results
 *
 * @author milank
 */
public class Laenderberechnung {
	
	private List<Staat> staaten;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	private int iteration = 1;
	
	private final Feedback feedback = Feedback.getInstance();
	
	private int maxIterationen;
	private double toleranz;
	
	private boolean solved = false;
	
	/**
	 * Constructor
	 * 
	 * @param staaten list of Staat-objects
	 * @param maximaleIterationen iteration break condition
	 * @param toleranz deviation break condition
	 */
	public Laenderberechnung(List<Staat> staaten, int maximaleIterationen, double toleranz) {
		if (toleranz > 0.) {
			this.toleranz = toleranz;
		}
		if (maximaleIterationen > 1) {
			maxIterationen = maximaleIterationen;
		}
		this.staaten = staaten;
		berechneDarstellungsbereich();
		
	}
	
	/**
	 * Main algorithm that calculates the map displacements iteratively based on break conditions
	 */
	public void berechneKartenDarstellung() {
	
		for (Staat staat : staaten) {
			staat.berechneKraft();
		}
		
		while (berechneUeberschneidung() > 0) {
			auseinanderZiehen();
			for (Staat staat : staaten) {
				staat.berechneKraft();
			}
		}
		
		boolean unzureichendeQualitaet = !toleranzErreicht();
		
		while (iteration < maxIterationen && unzureichendeQualitaet) {
			
			feedback.printIterationHeader(iteration);
			
			berechneIteration();

			unzureichendeQualitaet = !toleranzErreicht();
			iteration++;
		}
		
		solved = true;
		
		feedback.printResultConditions(iteration == maxIterationen, !unzureichendeQualitaet,
				iteration, maxIterationen, Math.abs(berechneUeberschneidung()), toleranz);
		
	}
	
	private void auseinanderZiehen() {
		for (Staat staat: staaten) {
			staat.kennwertSkalieren(0.5);
		}
	}
	
	private void berechneIteration() {
		
		for (Staat staat: staaten) {
			staat.berechneKraft();
		}
		
		for (Staat s: staaten) {
			s.kraefteAnwenden();
		}
		
		berechneDarstellungsbereich();
	}
	
	
	private void berechneDarstellungsbereich() {
		//beliebige initial Werte in Darstellungsbereich erforderlich
		xMin = staaten.get(0).getX();
		xMax = staaten.get(0).getX();
		yMin = staaten.get(0).getY();
		yMax = staaten.get(0).getY();
		
		for (Staat staat : staaten) {
			
			double xStaat = staat.getX();
			double yStaat = staat.getY();
			double radius = staat.getRadius();
			
			xMin = Math.min(xStaat - radius, xMin);
			xMax = Math.max(xStaat + radius, xMax);
			yMin = Math.min(yStaat - radius, yMin);
			yMax = Math.max(yStaat + radius, yMax);
			
		}
		
		//xmax-xmin soll gleich ymax-ymin zur Darstellung in gnuplot
		double rangeX = xMax-xMin;
		double rangeY = yMax-yMin;
		double rangeDif; 
		if (rangeX > rangeY) {
			rangeDif = rangeX - rangeY;
			yMin -= rangeDif/2;
			yMax += rangeDif/2;
		} else if (rangeX < rangeY) {
			rangeDif = rangeY - rangeX;
			xMin -= rangeDif/2;
			xMax += rangeDif/2;
		}
	}
	
	private boolean toleranzErreicht() {
		return Math.abs( berechneUeberschneidung() ) < toleranz;
	}
	
	private double berechneUeberschneidung() {
		double anziehSum = anziehkraefteGesamt();
		double abstossSum = abstosskraefteGesamt();
		return abstossSum - anziehSum;
	}
	
	private double anziehkraefteGesamt() {
		double anziehSum = 0;
		
		for (Staat staat : staaten) {
			anziehSum += staat.getAnziehSum();
		}
		
		return anziehSum;
	}
	
	private double abstosskraefteGesamt() {
		double abstossSum = 0;
		
		for (Staat staat : staaten) {
			abstossSum += staat.getAbstossSum();
		}
		
		return abstossSum;
	}
	
	/**
	 * @return List of Staat-Objects if the calculation is done, null otherwise
	 */
	public List<Staat> getStaaten() {
		if (solved) {
			return staaten;
		} else {
			return null;
		}
	}
	
	/**
	 * @return upper boundary on the x axis of the currently used display area
	 */
	public double getXmax() {
		return xMax;
	}
	
	
	/**
	 * @return lower boundary on the x axis of the currently used display area
	 */
	public double getXmin() {
		return xMin;
	}
	
	
	/**
	 * @return upper boundary on the y axis of the currently used display area
	 */
	public double getYmax() {
		return yMax;
	}
	
	
	/**
	 * @return lower boundary on the y axis of the currently used display area
	 */
	public double getYmin() {
		return yMin;
	}
	
	
	/**
	 * @return currentIteration
	 */
	public int getIterationen() {
		return iteration;
	}
	
}
