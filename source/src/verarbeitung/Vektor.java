/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verarbeitung;

import main.Feedback;

/**
 * Saves vector information as normalized coordinates and value of force
 * 
 * @author milank
 */
public class Vektor {
	
	private double xNorm;
	private double yNorm;
	private double staerke;
	
	private final Feedback feedback = Feedback.getInstance();
	
	/**
	 * Constructor
	 * 
	 * @param x x-coordinate of vector, will be normalized
	 * @param y y-coordinate of vector, will be normalized
	 */
	public Vektor(double x, double y) {
		staerke =  Math.sqrt( x*x + y*y );
		if (staerke == 0) {
			feedback.printError("Unable to create a Vektor, distance between two neighbours equals 0. "
					+ "Relation will be skipped this iteration.", this.getClass().toString());
			return;
		}
		xNorm = x/staerke;
		yNorm = y/staerke;
	}
	
	/**
	 * @param abstand new force value
	 */
	public void setStaerke(double abstand) {
		staerke = abstand;
	}
	
	/**
	 * @return absolute force value/distance
	 */
	public double getLaenge() {
		return Math.abs(staerke);
	}
	
	/**
	 * @return x-coordinate * force
	 */
	public double getX() {
		return xNorm * staerke;
	}
	
	/**
	 * @return y-coordinate * force
	 */
	public double getY() {
		return yNorm * staerke;
	}
	
	@Override
	public String toString() {
		return "(" + getX() + "|" + getY() + ")";
	}
	
}
