/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eingabe;

import java.util.List;
import verarbeitung.Staat;

/**
 * The interface for reading files. 
 * 
 * @author milank
 */
public interface I_Reader {
	
	/**
	 * Reads file from path.
	 *  
	 * @param inputFile relative path to file
	 * @return true if file was read successfully
	 */
	public boolean read(String inputFile);
	
	/**
	 * @return name of map characteristic
	 */
	public String getNameKennwert();
	
	/**
	 * @return list of Staat-objects
	 */
	public List<Staat> getStaaten();
	
}
