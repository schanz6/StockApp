/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

/**
 * This class is used to holds a days worth of information for each stock 
 * @author Jeremy Schanz
 */

// This class holds a days worth of data for each stock
public class StockData {
    
    
     public double price , priceToBook ,PEG,trailingPE,marketCap,forwardPE,returnOnEquity,EnterpriseValue,yearHigh,yearLow;
     public double institutions, threeVolume, tenVolume, priceToSale,currentRatio, ScoreOne;
     public double  valueRevenue, valueEBITDA, profitMargin, operatingMargin, returnOnAssets, revenuePerShare, revenueGrowth, grossProfit, eBITDA;
     public double netIncome, dilutedEPS, earningsGrowth, totalCash, totalDebt,debtEquity ,operatingCash ,leveredFreeCash, Beta;
     public double sP52Week, dayMoving50, dayMoving200, sharesOutstanding, floats, insiders, sharesShort, shortRatio, shortofFloat, sharesShortMonth  ;
     public String date, time, name;
     private double dataArray[];

     /**
   * This constructor sets up the StockData class
   * @param array the array of data that is read from file
   * @param tag stock tag name 
   */
     public StockData(String[] array,String tag)
     {
        name = tag;
        dataArray = new double[array.length -2];
        
       // assigns each data point to its named variable
        try
        {
         date = array[0];
         time = array[1];
         price = statToDouble( array[2]);
         priceToBook = statToDouble( array[3])/price;
         PEG = statToDouble( array[4])/price;
         trailingPE = statToDouble( array[5])/price;
         marketCap = statToDouble(array[6])/price;
         forwardPE = statToDouble(array[7])/price;
         returnOnEquity = statToDouble(array[8]);
         EnterpriseValue = statToDouble(array[9]);
         yearHigh = statToDouble(array[10]);
         yearLow = statToDouble(array[11]);
         institutions = statToDouble(array[12]);
         threeVolume = statToDouble( array[13]);
         tenVolume = statToDouble(array[14]);
         priceToSale = statToDouble( array[15])/price;
         currentRatio = statToDouble( array[16]);
         valueRevenue = statToDouble( array[17]);
         valueEBITDA = statToDouble( array[18]);
         profitMargin = statToDouble(array[19]);
         operatingMargin = statToDouble(array[20]);
         returnOnAssets = statToDouble(array[21]);
         revenuePerShare = statToDouble( array[22]);
         revenueGrowth = statToDouble(array[23]);
         grossProfit = statToDouble(array[24]);
         eBITDA = statToDouble(array[25]);
         netIncome = statToDouble(array[26]);
         dilutedEPS = statToDouble(array[27]);
         earningsGrowth = statToDouble(array[28]);
         totalCash = statToDouble(array[29]);
         totalDebt = statToDouble(array[30]);
         debtEquity = statToDouble( array[31]);
         operatingCash = statToDouble(array[32]);
         leveredFreeCash = statToDouble(array[33]);
         Beta = statToDouble(array[34]);
         sP52Week = statToDouble(array[35]);
         dayMoving50 = statToDouble( array[36]);
         dayMoving200 = statToDouble( array[37]);
         sharesOutstanding = statToDouble(array[38]);
         floats = statToDouble(array[39]);
         insiders = statToDouble(array[40]);
         sharesShort = statToDouble(array[41]);
         shortRatio = statToDouble( array[42]);
         shortofFloat = statToDouble(array[43]);
         sharesShortMonth = statToDouble(array[44]);
         
         
         ScoreOne = parseDoubleComma( array[45]);
         
        }
        catch(Exception e)
        {
            //System.out.println(" too short "+ tag+" "+ date+" "+e.getMessage());
        }
        
       // converts the input array to an array of doubles omitting date and time
        for(int i = 0; i< dataArray.length;i++ )
        {
            if((i>0 && i<6) || i == 13  )
            {
                dataArray[i] = statToDouble(array[i+2])/price;
            }
            else
            {
                dataArray[i] = statToDouble(array[i+2]);
            }
        }
        
         
     }
     
     /**
   * This Function finds the correct multiplier and then converts stat to double 
   * @param value stat being converted 
   * @return the stat converted to double 
   */
     private double statToDouble(String value )
     {
        // returns zero if there is no value found
        if(value.equals("N/A"))
        {
            return 0;
        }
        
        char moneyChar = Character.toLowerCase( value.charAt(value.length()-1));
        double multiplier = 1;
        
       // checks if there is a number indicator (i.e. m = million)  multiplies the number accordingly  
        if(moneyChar == '%' || moneyChar == 'm' || moneyChar == 'b' || moneyChar == 'k' || moneyChar == 't' )
        {
            value = value.substring(0,value.length()-2);
            
            if(moneyChar == '%')
            {
                multiplier = 1.0/100.0; 
            }
            if(moneyChar == 'm')
            {
                multiplier= 1000000;
            }
            if(moneyChar == 'b')
            {
                multiplier = 1000000000;
            }
            if(moneyChar == 'k')
            {
                multiplier = 1000;
            }
             if(moneyChar == 't')
            {
                multiplier = 1000000000000.0;
            }
        }
        
        return parseDoubleComma(value)*multiplier;
          
     }
    
    /**
   * This Function converts a string the contains commas to double 
   * @param value value being converted 
   * @return the value converted to double 
   */
    private double parseDoubleComma(String value)
    {
        if(value.equals("N/A"))
        {
            return 0;
        }
  
	String left,right;
	String out = value;
	for(int i =0;i<out.length();i++)
        {
		if(out.charAt(i)== ',')
                {
		     left = out.substring(0,i);
		     right = out.substring(i+1,out.length());
		     out = left+right;
		    
		}
	}
	
	return Double.parseDouble(out);
        
     }
    
 /**
   * This Function returns the desired stat from dataArray
   * @param index index of stat
   * @return desired stat value 
   */
    public double get(int index)
    {
        // checks if index is in range 
        if(index < length()  )
        {
           return dataArray[index];
        }
        
        return 0;
    }
    
    /**
   * This Function returns the length of the dataArray
   * @return length of dataArray
   */
    public int length()
    {
        return dataArray.length;
    }
       

}
