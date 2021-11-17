/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.File;

/**
 * This class prints user feedback in the commandline interface
 * 
 * @author milank
 */
public class Feedback {
	
	private static Feedback feedbackInstance;
	
	private Feedback() {
		feedbackInstance = this;
	}
	
	/**
	 * @return instance of itself
	 */
	public static Feedback getInstance() {
		if (feedbackInstance == null) {
			return new Feedback();
		} else {
			return feedbackInstance;
		}
	} 
	
	/**
	 * prints out usage instructions on failure
	 */
	public void printHelp() {
		System.out.println("Could not execute program properly.");
		System.out.println();
		System.out.println("usage:");
		System.out.println("java -jar programname.jar <inputFilePath>");
		System.out.println();
		System.out.println("for example:");
		System.out.println("java -jar gpmk1.jar ." + File.separator + "input" + File.separator + "bsp1.in");
		System.out.println();
		System.out.println("To run program over all input files, use batch-file:");
		System.out.println("script.bat programname.jar -d <folderpath>");
		System.out.println();
		System.out.println("Additionally you may add optional parameters for maximum iterations and requested deviation.");
		System.out.println("for example: ");
		System.out.println("java -jar gpmk1.jar ." + File.separator + "input" + File.separator + "bsp1.in 200 0.01");
		System.out.println("script.bat programname.jar -d input 200 0.01");
	}
	
	/**
	 * prints out help for break conditions if no arguments for it were given
	 * 
	 * @param maxIterations the maximum amount of iterations the program will go through before stopping
	 * @param toleranz the desired minimum deviation
	 */
	public void printParameterHelp(int maxIterations, double toleranz) {
		System.out.println("The program was launched without break conditions, default values are used.");
		System.out.println("maximum amount of iterations: " + maxIterations + ", desired deviation: " + toleranz);
		System.out.println();
		System.out.println("To set these variables use a positive integer for the iterations and a double value for the deviation.");
		System.out.println();
		System.out.println("for example:");
		System.out.println("java -jar programname.jar inputfile.in 200 0.01\n");
	}
	
	/**
	 * prints the header of each iteration
	 * 
	 * @param iteration current iteration count
	 */
	public void printIterationHeader(int iteration) {
		System.out.println("\nIteration " + iteration);
		System.out.println("------------------------------------");
	}
	
	/**
	 * prints how many iterations the program executed and
	 * which break condition was triggered and compares the conditions with final result. 
	 * 
	 * @param iterationCondition true when the maximum iterations equal the current iteration
	 * @param qualityCondition true when the final deviation is smaller than the custom deviation
	 * @param currentIteration how many iterations the program executed
	 * @param maxIterations iteration break condition
	 * @param deviation final deviation
	 * @param toleranz deviation break condition
	 */
	public void printResultConditions(boolean iterationCondition, boolean qualityCondition, int currentIteration, int maxIterations, double deviation, double toleranz) {
		System.out.println("\nThe program finished calculations after " + currentIteration + " iterations.");
		if (qualityCondition) {
			
			System.out.println("The endresult is within the given deviation (" + toleranz + ") with a final result of " + deviation);
			
		} else if (iterationCondition) {
			
			System.out.println("The calculations were stopped at the given maximum iterations " +
						"and don't satisfy the deviation condition.\n" +
						"final deviation: " + deviation + ", given deviation: " + toleranz + ".");
			
		}
	}
	
	/**
	 * prints out error message and where the error occurred
	 * 
	 * @param error errormessage
	 * @param className location of error 
	 */
	public void printError(String error, String className) {
		System.out.println();
		System.err.println("Error in " + className + ": " + error);
	}
	
	/**
	 * prints out warning message and where the warning occurred
	 * 
	 * @param warning warnmessage
	 * @param className location of warning
	 */
	public void printWarning(String warning, String className) {
		System.out.println();
		System.out.println("Warning in " + className + ": " + warning);
	}
	
	/**
	 * prints out the displacement values for each country each iteration
	 * 
	 * @param kennzeichen country symbol
	 * @param x x-coordinate before displacement
	 * @param y y-coordinate before displacement
	 * @param neuX x-coordinate after displacement
	 * @param neuY y-coordinate after displacement
	 */
	public void printVerschiebung(String kennzeichen, double x, double y, double neuX, double neuY) {
		System.out.println("Verschiebe (" + kennzeichen + ") von (" + x + " | " + y + ") nach (" + neuX + " | " + neuY + ")");
	}
	
}
