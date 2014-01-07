/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

import java.util.ArrayList;

/**
 *This class is used to organize  the data and rankings for each stock 
 * @author Jeremy Schanz
 */

// An extended array list that holds the stock data for each indevidual stock
public class StockArrayList<T> extends ArrayList<T> 
{
    private String stockName;
    private ArrayList<Integer> ranking;
    private ArrayList<Double> change;
    
    /**
   * This constructor sets up the the StockArrayList class
   * @param Name stock tag 
   */
    public StockArrayList(String name)
    {
        stockName = name;
        ranking = new ArrayList<Integer>();
        change = new ArrayList<Double>();
      
    }
    
    /**
   * This Function returns stock tag
   * @return the stockName variable
   */
    public String getName()
    {
        return stockName;
    }
    
     /**
   * This Function adds rankings to the ranking array list
   * @param rank the value of the rank
   */
    public void addRanking(int rank)
    {
        ranking.add(rank);
    }
    
    /**
   * This Function returns the ranking from the ranking array list 
   * @param index desired index to be returned 
   * @return the desired ranking 
   */
    public int getRanking(int index)
    {
        return ranking.get(index);
    }
    
     /**
   * This Function adds percent change to the change array list
   * @param percent percent change 
   */
     public void addChange(double percent)
    {
        change.add(percent);
    }
    
      /**
   * This Function returns the percent change from the change array list 
   * @param index desired index to be returned 
   * @return the desired percent change 
   */
    public double getChange(int index)
    {
        return change.get(index);
    }
    
    
}
