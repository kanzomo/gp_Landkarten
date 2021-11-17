/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verarbeitung;

import main.Feedback;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to manage country informations, contains calculation methods
 * 
 * @author milank
 */
public class Staat {
	
	private double x;
	private double y;
	private double radius;
	private double flaeche;
	private String name;
	
	private List<Staat> nachbarn = new ArrayList<>();
	private List<Vektor> abstossung = new ArrayList<>();
	private List<Vektor> anziehung = new ArrayList<>();
	
	private final Feedback feedback = Feedback.getInstance();
	
	/**
	 * Constructor
	 * 
	 * @param kennzeichen name of map characteristic
	 * @param kennwert value of map characteristic, equivalent to the surface area of the circle
	 * @param x x-coordinate of centre
	 * @param y y-coordinate of centre
	 */
	public Staat(String kennzeichen, double kennwert, double x, double y) {
		this.x = x;
		this.y = y;
		flaeche = kennwert;
		radius = Math.sqrt(flaeche / Math.PI);
		name = kennzeichen;
	}
	
	/**
	 * calculates the gravitational and repelling forces for each neighbour
	 */
	public void berechneKraft() {
		
		//werte zuruecksetzen fuer neue Iteration
		abstossung = new ArrayList<>();
		anziehung = new ArrayList<>();
		
		for (Staat nachbar : nachbarn) {
			double richtungX = nachbar.getX() - this.getX();
			double richtungY = nachbar.getY() - this.getY();

			Vektor richtung = new Vektor(richtungX, richtungY);
			double mittelpunktAbstand = 0;
			mittelpunktAbstand = richtung.getLaenge();
			double kreisAbstand = mittelpunktAbstand - this.getRadius() - nachbar.getRadius();
			richtung.setStaerke(kreisAbstand);
			if (kreisAbstand >= 0) {
				anziehung.add(richtung);
			} else {
				abstossung.add(richtung);
			}
		}
	}
	
	/**
	 * apply the sum of calculated forces, divided by amount of neighbours
	 */
	public void kraefteAnwenden() {
		double schiebeX = 0;
		double schiebeY = 0;
		int nachbarAnzahl = nachbarn.size();
		for (Vektor v : abstossung) {
			schiebeX += (v.getX() / (nachbarAnzahl));
			schiebeY += (v.getY() / (nachbarAnzahl));
		}
		for (Vektor v : anziehung) {
			schiebeX += (v.getX() / (nachbarAnzahl));
			schiebeY += (v.getY() / (nachbarAnzahl));
		}
	
		feedback.printVerschiebung(name, x, y, x+schiebeX, y+schiebeY);
		
		x += schiebeX;
		y += schiebeY;
	}
	
	/**
	 * @param scalar re-calculates surface area as area = area * scalar
	 */
	public void kennwertSkalieren(double scalar) {
		flaeche = flaeche*scalar;
		radius = Math.sqrt(flaeche / Math.PI);
	}
	/**
	 * @param nachbar Staat-object to be added to list of neighbours
	 */
	public void addNachbar(Staat nachbar) {
		nachbarn.add(nachbar);
	}
	
	/**
	 * @return sum of repelling forces
	 */
	public double getAbstossSum() {
		double sum = 0;

		for (Vektor v : abstossung) {
			sum += v.getLaenge();
		}

		return sum;
	}

	/**
	 * @return sum of gravitational forces
	 */
	public double getAnziehSum() {
		double sum = 0;

		for (Vektor v : anziehung) {
			sum += v.getLaenge();
		}

		return sum;
	}
	
	/**
	 * @return name of map characteristic
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return x-coordinate of centre
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return y-coordinate of centre
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @return circle radius
	 */
	public double getRadius() {
		return radius;
	}
	
	@Override
	public String toString() {
		return "Kennzeichen: " + name + 
				", Kennwert: " + flaeche +
				", Laengengrad: " + x +
				", Breitengrad: " + y;
	}
	
}