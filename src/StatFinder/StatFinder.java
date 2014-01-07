/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

import java.io.*;
import java.util.*;

/**
 * This is the most experimental of the classes it is still a work in progress that used to build test and simulations on the data 
 * @author Jeremy Schanz
 */
// 
public class StatFinder 
{
  
    private static ArrayList<String> tagList;
    private static ArrayList<StockArrayList<StockData>> info;
    private static int arrayLength = 15;
    private static String[] date;
    private static final String[] wordList ={"Price","Price/Book (mrq)","PEG Ratio (5 yr expected)","Trailing P/E (ttm, intraday)",
        "Market Cap (intraday)","Forward P/E ","Return on Equity (ttm):","Enterprise Value ","52-Week High ","52-Week Low ","% Held by Institutions",
        "Avg Vol (3 month)","Avg Vol (10 day)","Price/Sales (ttm)","Current Ratio (mrq)","Enterprise Value/Revenue (ttm)","Enterprise Value/EBITDA (ttm)","Profit Margin (ttm)","Operating Margin (ttm)","Return on Assets (ttm)",
        "Revenue Per Share (ttm)","Qtrly Revenue Growth (yoy)","Gross Profit (ttm)","EBITDA (ttm)","Net Income Avl to Common (ttm)","Diluted EPS (ttm)","Qtrly Earnings Growth (yoy)","Total Cash Per Share (mrq)",
        "Total Debt (mrq)","Total Debt/Equity (mrq)","Operating Cash Flow (ttm)","Levered Free Cash Flow (ttm)","Beta:","500 52-Week Change","50-Day Moving Average","200-Day Moving Average","Shares Outstanding","Float:",
        "Held by Insiders","Shares Short ","Short Ratio ","of Float ","Shares Short (prior month)","Score"};
    
    
  /**
   * This Function is the main function for stat finder
   * it is responsible reading the data from files and 
   * putting it in to a complex array
   * @param arg not used.
   */
    public static void main(String [] arg)
    {
       
       // Gets list of stock tags
       // file = new StockFileReader();
        tagList = StockFileReader.getStockName("combinedList.txt");
        info = new ArrayList<StockArrayList<StockData>>();
        
        // loops throught the tags reading data from files 
        for(int i =0;i<tagList.size();i++)
        {
            StockArrayList<StockData> hold = StockFileReader.getStockData(tagList.get(i));
            //checks to see if data exists 
            if(hold != null)
            {
               info.add(hold);  
            }
        }
        System.out.println(info.size());
        
        // finds the dates of the data
        ArrayList<StockData> first =  info.get(0);
        date = new String[first.size()];
        
        for(int j = 0;j<date.length;j++)
        {
            date[j] = first.get(j).date; 
        }
        // puts zero value in to any missing data to that the data lines up
        for(int i = 0; i<info.size(); i++)
        {
            StockArrayList<StockData> hold = info.get(i);
            
            if(hold.size() < date.length)
            {
                for(int j =0; j< hold.size();j++)
                {
                  
                    if(!date[j].equals(hold.get(j).date))
                    {
                        String[] insert = {date[j],"20:00:00"};
                        
                        hold.add(j,new StockData(insert, hold.getName()));
                        
                    }
                }
            }
        }
        
        
       runTest();
        
        
    }
    
  /**
   * This Function is used to set up and run tests 
   */
    public static void runTest()
    {
        StockArrayList<StockData> stock = findStock("manu");
        
        printStock(stock.get(0));
        
        stock = findStock("goog");
        
        printStock(findDate(stock,"2013/10/21"));
        
        
       //totalPrice();
        regresion(stock);
        
        //System.out.println(info.get(info.size()-1).getName());
       /* 
       // priceChange();
        int last = info.get(info.size()-1).size();
        last = 25;
        //statRanking(0,16);
        for(int k = 1;k<16;k++){
            for(int i = 0;i<10;i++){
                for(int j = 1;j <10;j++){
                    PECorrelation(i,i+j,k,25);
            }
           }
        }
            
           */
        //scoreSort(info);
        
    }
 /**
   * This Function finds a particular stocks data 
   * @param tag tag of stock data you are looking for.
   * @return StockArrayList containing stock data.
   */
    public static StockArrayList<StockData> findStock(String tag)
    {
        tag = tag.toUpperCase();
        for(int i = 0; i < info.size();i++ )
        {
            if( info.get(i).getName().equals(tag))
            {
                return info.get(i);
            }
        }
        
        return null;
    }
      /**
   * This Function finds data from a particular date
   * @param stock data from a stock.
   * @param date the date you are looking for.
   * @return returns day worth of data.
   */
    public static StockData findDate(StockArrayList<StockData> stock, String date)
    {
        for(int i = 0; i< stock.size();i++)
        {
            if(stock.get(i).date.equals(date) )
            {
                return stock.get(i);
            }
        }
        
        return null;
    }
    
     /**
   * This Function prints a days worth of data 
   * @param stock days worth of stock data.
   */
    public static void printStock(StockData stock)
    {
        System.out.println(stock.name);
        
        for(int i = 0; i< stock.length(); i++ )
        {
            System.out.println( wordList[i]+" : "+ stock.get(i) );
        }
        
        
    }
      /**
   * This Function finds the regression of all the data types of an individual stocks over price
   * @param stock data from a particular stock.
   */
    public static void regresion(ArrayList<StockData> stock)
    {
       //loops throught the differnt kinds of data points 
       for(int index =0;index< stock.get(0).length();index++)
       {
            ArrayList<Double> priceY = new ArrayList<Double>();
            ArrayList<Double> otherX = new ArrayList<Double>();
            // collects data in to array list 
            for(int i = 0; i< stock.size(); i++)
            {
                StockData day = stock.get(i);
                if(day.price != 0 && day.get(index) != 0)
                {
                  priceY.add(day.price);
                  otherX.add(day.get(index));
                }
            }
       
            double[] x = new double[priceY.size()];
            double[] y = new double[priceY.size()];
            // convert arraylist to array of doubles
            for(int j = 0; j< x.length; j++)
            {
                y[j] = priceY.get(j);
                x[j] = otherX.get(j);
            
            }
        
            double[] reg = Statistics.regression(x,y);
        
            System.out.println("Regression of price on "+wordList[index]+" b0 = "+reg[0]+" b1 = "+ reg[1]+" correlation = "+ Statistics.correlation(x, y));
    
       }
    }
    
     /**
   * This Function finds the total price of all stock each day
   */
    public static void totalPrice()
    {
        double total = 0;
        ArrayList<StockData> first =  info.get(0);
        double outArray[] = new double[first.size()];
        
        // loops throught  all stocks
        for(int i = 0; i< info.size();i++)
        {
            // loops throught each day
            for(int j = 0;j< first.size();j++)
            {
                try
                {
                   outArray[j]  = outArray[j] + info.get(i).get(j).price;
                } 
                catch(Exception e)
                {
                    System.out.println(info.get(i).getName());
                }
            }
        }
        // prints out results
        for(int j = 0;j< outArray.length;j++)
        {
            System.out.println(first.get(j).date+" : "+ outArray[j] );
            
        }
    }
    
    
     /**
   * This Function calculates the percent prise difference in all stocks between a set time period
   */
    public static void priceChange()
    {
        ArrayList<StatHolder> change = new ArrayList<StatHolder>();
        
        for(int i = 0;i < info.size(); i++)
        {
            StockArrayList<StockData> stockArray = info.get(i);
          try
          {
             
            StockData first = stockArray.get(0);
            StockData last = stockArray.get(stockArray.size()-1);
            
            double priceDif = (last.price-first.price)/first.price;
            
         //   change.add(new StatHolder(stockArray.getName(),priceDif,last.price));
          }
          catch(Exception e)
          {
              System.out.println(stockArray.getName());
          }
        }
        
        //change = sort(change);
        
        for(int j = 0;j < 50; j++)
        {
            
            StatHolder outData = change.get(j);
          //  System.out.println(outData.getName()+" : "+outData.getPrice()+" : "+outData.getStat());
        }
    
    }
     /**
   * This Function shows the corelation between selected data point and change in price of the stock 
   * over a set time period
   * @param statFirstDay first  day of time period.
   * @param statSecondDay last  day of time period.
   * @param statNumber number that represents the data point you want to compare.
   * @param priceChange minimum size of data needed to test 
   */
    public static void PECorrelation(int statFirstDay ,int statSecondDay,int statNumber,int priceChange )
    {
        int minSize = info.get(0).size() - 3;
        
        ArrayList<StatHolder> CorrelationData = new ArrayList<StatHolder>();
        // loopes throught all the stock data
        for(int i = 0;i < info.size(); i++)
        {
               StockArrayList<StockData> stockArray = info.get(i);
               
               //finds the stock data
               if(stockArray.size()> minSize && stockArray.size()> statSecondDay && stockArray.size()> priceChange  )
               {
                   StockData firstStat = stockArray.get(statFirstDay);
                   StockData secondStat = stockArray.get(stockArray.size()-1);
                   StockData thirdStat = stockArray.get(statSecondDay);
                   // adds data to array list if data is not 0 (NULL)
                   if(firstStat.get(statNumber) !=0)
                   {
                       double valueOne = (thirdStat.get(statNumber)-firstStat.get(statNumber))/(firstStat.get(statNumber));
                       double valueTwo = (secondStat.price-thirdStat.price)/thirdStat.price;
                   
                       CorrelationData.add(new StatHolder(stockArray.getName(),secondStat.price,valueOne,valueTwo ));
                   }
               
               }
        }
        
        double[] firstArray = new double[CorrelationData.size()];
        double[] secondArray = new double[CorrelationData.size()];
        // converts array list to array
        for(int j = 0;j<CorrelationData.size();j++)
        {
            StatHolder temp = CorrelationData.get(j);
            
            firstArray[j]= temp.getStatX();
            secondArray[j]= temp.getStatY();
        }
        
        double cor = Statistics.correlation(firstArray,secondArray );
        // prints correlations creater then .1
        if(cor > .1)
        {
            System.out.println(statFirstDay +" "+ statSecondDay+" "+statNumber+" "+priceChange+" "+ cor);
        }
    
    }
  /**
   * This Function calculates the change in  all the data point for all the stock
   * between a set period of time and then ranks the change compared to each other
   * @param firstDay first  day of time period.
   * @param secondDay last  day of time period.
   */
    public static void statRanking(int firstDay, int secondDay)
    {
      
      for(int statIndex = 0; statIndex< arrayLength;statIndex++ ) 
      { 
          int count = 0;
        
          for(int i = 0;i<info.size(); i++)
          {
        
              StockArrayList<StockData> stockArray = info.get(i);
          
              try
              {
                  StockData first = stockArray.get(firstDay);
            
                  StockData last = stockArray.get(secondDay);
            
                  if(first.get(statIndex) != 0 )
                  {
            
                      stockArray.addChange((last.get(statIndex)-first.get(statIndex))/first.get(statIndex));
                      count++;
                  }
                  else
                  {
                      stockArray.addChange(-10000000);
                  }
            
           // change.add(new StatHolder(stockArray.getName(),priceDif,last.price));
         
              }
             catch(Exception e)
              {
                  System.out.println(stockArray.getName()+" "+e.toString());
          
              }
        }
          
          sort(info,statIndex);
          System.out.println(statIndex+" : "+count);
          
      }
      
      sort(info,0);
      
      
       for(int l = 0;l < 50; l++)
       {
          StockArrayList<StockData> stockPrint = info.get(l);
       
          System.out.print(stockPrint.getName()+"  "+stockPrint.get(stockPrint.size()-1).price);
          
          for(int m = 0;m < arrayLength; m++)
          {
              System.out.print("  "+stockPrint.getRanking(m));
          }
          
          System.out.println("");
      
      }
        
    }
            
    /**
   * This Function sorts the actual rankings that are created 
   * @param original array your sorting 
   * @return no return but changes array order of the input array .
   */
   public static void scoreSort(ArrayList<StockArrayList<StockData>> original)
   {
       
       for(int i = 0;i<original.size();i++)
       {
           
           int lastData = original.get(i).size();
           
           original.get(i).addChange(  original.get(i).get(0).get(15));
           
           
       }
       
       original = sort(original, 0);
       
       double total = 0;
       
       for(int i = 0;i<100;i++)
       {
           double change = (original.get(i).get(original.get(i).size()-2).price - original.get(i).get(0).price)/original.get(i).get(0).price;
          // System.out.println(original.get(i).getName()+" - "+original.get(i).getChange(0)+" "+change);
           
          total = total+change;
           
       }
       
       System.out.println(total);
   
   }
   
    /**
   * This Function sorts the rankings for statRancking function 
   * @param original array your sorting 
   * @param statIndex index of data point being sorted 
   * @return no return but changes array order of the input array .
   */
    public static ArrayList<StockArrayList<StockData>> sort(ArrayList<StockArrayList<StockData>> original, int statIndex)
    {
      	      for(int i = 0;i < original.size();i++)
              {
      	      	      StockArrayList<StockData> compare = original.get(i);
      	      	      
                      for(int j = i+1;j < original.size(); j++)
                      {
      	      	      	      if(original.get(j).getChange(statIndex) > compare.getChange(statIndex))
                              {
                                  
      	      	      	      	      StockArrayList<StockData> temp = compare;
      	      	      	      	      original.set(i,original.get(j));
      	      	      	      	      original.set(j,compare);
      	      	      	      	      compare = original.get(i);
      	      	      	      }
      	      	      }
      	      }
              
              for(int k = 0;k < original.size();k++)
              {
                  original.get(k).addRanking(k);
              }
              
              
              return original;
       }
    
    
    
    
    
}
