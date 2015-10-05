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
  
    private ArrayList<String> tagList;
    private ArrayList<StockArrayList<StockData>> info;
    private int arrayLength = 15;
    private String[] date;
    private final String[] wordList ={"Price","Price/Book (mrq)","PEG Ratio (5 yr expected)","Trailing P/E (ttm, intraday)",
        "Market Cap (intraday)","Forward P/E ","Return on Equity (ttm):","Enterprise Value ","52-Week High ","52-Week Low ","% Held by Institutions",
        "Avg Vol (3 month)","Avg Vol (10 day)","Price/Sales (ttm)","Current Ratio (mrq)","Enterprise Value/Revenue (ttm)","Enterprise Value/EBITDA (ttm)","Profit Margin (ttm)","Operating Margin (ttm)","Return on Assets (ttm)",
        "Revenue Per Share (ttm)","Qtrly Revenue Growth (yoy)","Gross Profit (ttm)","EBITDA (ttm)","Net Income Avl to Common (ttm)","Diluted EPS (ttm)","Qtrly Earnings Growth (yoy)","Total Cash Per Share (mrq)",
        "Total Debt (mrq)","Total Debt/Equity (mrq)","Operating Cash Flow (ttm)","Levered Free Cash Flow (ttm)","Beta:","500 52-Week Change","50-Day Moving Average","200-Day Moving Average","Shares Outstanding","Float:",
        "Held by Insiders","Shares Short ","Short Ratio ","of Float ","Shares Short (prior month)","Score"};
    
    
   /**
   * This constructor loads data for the StatFinder class
   * @param tagList list of stock tags to be included in dataset.
   */
    public StatFinder(ArrayList<String> tagList)
    {
       // Gets list of stock tags
       // file = new StockFileReader();
        this.tagList = tagList;
       
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
                int holdSize = hold.size();
                
                for(int j =0; j< holdSize ;j++)
                {
                  
                    if(!date[j].equals(hold.get(j).date))
                    {
                        String[] insert = {date[j],"20:00:00"};
                        
                        hold.add(j,new StockData(insert, hold.getName()));
                        
                    }
                }
            }
        }
        
         System.out.println(info.size()+" Stock tags data loaded ");
        
    }
  /**
   * This Function returns the whole dataset
   * @return returns whole dataset
   */
    public ArrayList<StockArrayList<StockData>> getStocks()
    {
        return info; 
    }
    

  /**
   * This Function finds a particular stocks data 
   * @param tag tag of stock data you are looking for.
   * @return StockArrayList containing stock data.
   */
    public StockArrayList<StockData> findStock(String tag)
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
   * @param date the date you are looking for in yyyy/MM/dd format.
   * @return returns day worth of data.
   */
    public StockData findDate(StockArrayList<StockData> stock, String date)
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
    public void printStock(StockData stock)
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
   * @return returns array list of regressions
   */
    public ArrayList<RegressionHolder> regression(ArrayList<StockData> stock)
    {
        ArrayList<RegressionHolder> retList = new ArrayList<RegressionHolder>();
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
        
            retList.add(new RegressionHolder(wordList[index],reg[0],reg[1],Statistics.correlation(x, y)));
           
       }
       
       return retList;
    }
    

     /**
   * This Function finds the total price of all stock on a given date
   * @param info dataset being searched over.
   * @param date date to find total price.
   * @return returns array list of regressions
   */
    public double totalPrice(ArrayList<StockArrayList<StockData>> info, String date)
    {
        
        double retPrice = 0;
        
        // loops throught  all stocks
        for(int i = 0; i< info.size();i++)
        {
            
            try
            {
               retPrice  = retPrice + findDate(info.get(i),date).price;
            } 
            catch(Exception e)
            {
                System.out.println(info.get(i).getName());
            }
            
        }
        
        return retPrice; 
        
    }
    

    
     /**
   * This Function shows the corelation between selected data point and change in price of the stock 
   * over a set time period
   * @param info dataset being searched over.
   * @param statFirstDay first day of time period.
   * @param statLastDay last day of time period.
   * @param statNumber number that represents the data point you want to compare.
   * @param priceChange minimum size of data needed to test 
   * @return returns correlation between the two data sets 
   */
    public double PECorrelation(ArrayList<StockArrayList<StockData>> info,int statFirstDay ,int statLastDay,int statNumber,int priceChange )
    {
        int minSize = info.get(0).size() - 3;
        
        ArrayList<StatHolder> CorrelationData = new ArrayList<StatHolder>();
        // loopes throught all the stock data
        for(int i = 0;i < info.size(); i++)
        {
               StockArrayList<StockData> stockArray = info.get(i);
               
               //finds the stock data
               if(stockArray.size()> minSize && stockArray.size()> statLastDay && stockArray.size()> priceChange  )
               {
                   StockData firstStat = stockArray.get(statFirstDay);
                   StockData secondStat = stockArray.get(stockArray.size()-1);
                   StockData thirdStat = stockArray.get(statLastDay);
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
            System.out.println(statFirstDay +" "+ statLastDay+" "+statNumber+" "+priceChange+" "+ cor);
        }
        
        return cor;
    
    }
    
  /**
   * This Function calculates the change in  all the data point for all the stock
   * between a set period of time and then ranks the change compared to each other
   * @param info dataset being searched over.
   * @param firstDay first  day of time period.
   * @param lastDay last  day of time period.
   * @return returns dataset with rankings 
   */
    public ArrayList<StockArrayList<StockData>> statRanking(ArrayList<StockArrayList<StockData>> info, String firstDay, String lastDay)
    {
        
      for(int statIndex = 0; statIndex < arrayLength;statIndex++ ) 
      { 
          int count = 0;
        
          for(int i = 0;i<info.size(); i++)
          {
        
              StockArrayList<StockData> stockArray = info.get(i);
          
              try
              {
                  StockData first = findDate(stockArray,firstDay);
            
                  StockData last = findDate(stockArray,lastDay);
            
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
          //System.out.println(statIndex+" : "+count);
          
      }
      
      sort(info,0);
     
      return info;
        
    }
            
    /**
   * This Function sorts the actual rankings that are created 
   * @param original array your sorting 
   */
   public void scoreSort(ArrayList<StockArrayList<StockData>> original)
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
   * @return returns sorted list
   */
    private ArrayList<StockArrayList<StockData>> sort(ArrayList<StockArrayList<StockData>> original, int statIndex)
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
