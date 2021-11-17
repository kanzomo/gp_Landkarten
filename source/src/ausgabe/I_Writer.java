/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ausgabe;

import verarbeitung.Laenderberechnung;

/**
 * The interface for generating the output file. 
 * 
 * @author milank
 */
public interface I_Writer {

	/**
	 * Writes calculation results to file.
	 *
	 * @param berechnung Object with calculation results
	 */
	public void write(Laenderberechnung berechnung);
}
