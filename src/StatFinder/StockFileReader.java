/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

/**
 * This class is a collection of static file writers and readers for Stat Finder class
 * @author Jeremy Schanz
 */

import java.io.*;
import java.util.*;


// File Reading class incharge of reading files and turning them in to arrays for stat finder
public class StockFileReader
{ 
	//private static ArrayList<String> stock;
	//private static Scanner scanFile;
	//private PrintWriter output;
	
   /**
   * This static function reads the data and turns it in to an array for a particular stock
   * @param tag name of stock tag
   * @return StockArrayList containing stock data.
   */
      public static StockArrayList<StockData> getStockData(String tag)
      {
         Scanner scanFile;
         String name = tag.trim()+".txt";
         // checks to see if file exists if not return null
         try
         {
            File  tempFile = new File(name);
            scanFile = new Scanner(tempFile);
           
         }
         catch (Exception ex) 
         {
           
            return null;
         }
         
         StockArrayList<StockData> data = new StockArrayList<StockData>(tag);
         
         String next= scanFile.nextLine();
         
              // reads the file line by line (each line is a day)
      	      while(scanFile.hasNextLine())
              {
      	      	      
                next = scanFile.nextLine();
                //splits the line in to indevidual data points
                String[] dataString =  next.split("\\s+");
                StockData tempData = new StockData(dataString, tag);
                
                data.add(tempData);
                     
      	      	  
      	      }
      	     
          
          return  data;
      }
      
      /**
   * This static function reads the stock tags from a file put it into an array
   * @param fileName name of file
   * @return Array containing stock tags 
   */
      public static ArrayList<String> getStockName(String fileName)
      {
         Scanner scanFile;
         String name = fileName.trim();
         //Checks if file exists 
         try
         {
            File  tempFile = new File(name);
            scanFile= new Scanner(tempFile);
           
         }
         catch (Exception ex) 
         {
            return null;
        }
        ArrayList<String> tagString = new ArrayList<String>();
        String next;
        
              // reads file line by line (each line is a stock tag
      	      while(scanFile.hasNextLine())
              {
      	      	  tagString.add(scanFile.nextLine());
      	      }
      	     
          
          return  tagString;
      }
      
    
      
      
}
      	      	      
      	      	      
      
   


