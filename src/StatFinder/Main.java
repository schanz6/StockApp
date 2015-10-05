/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

import java.io.IOException;
import java.util.*;

/**
 *
 * @author Jeremy Schanz
 */
public class Main
{
    public static void main(String[] args)throws IOException  
    {
        //Read in Stock Tag Name list
        ArrayList<String> tagList = StockFileReader.getStockName("save/combinedList.txt");
        
        //Setup stat finder class
        StatFinder statFinder = new StatFinder(tagList); 
        
        //perform stat finder actions bellow. Function descriptions found in StatFinder.java
        
        
        
    }
}
