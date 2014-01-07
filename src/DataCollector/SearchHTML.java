/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCollector;

import java.io.*;
/**
 * This class is used to fined the data from html 
 * @author Jeremy Schanz
 */

public class SearchHTML 
{
   
    private static final String[] wordList ={"yfi_rt_quote_summary_rt_top","Price/Book (mrq)","PEG Ratio (5 yr expected)","Trailing P/E (ttm, intraday)",
        "Market Cap (intraday)","Forward P/E (","Return on Equity (ttm):","Enterprise Value (","52-Week High ","52-Week Low (","% Held by Institutions",
        "Avg Vol (3 month)","Avg Vol (10 day)","Price/Sales (ttm)","Current Ratio (mrq)","Enterprise Value/Revenue (ttm)","Enterprise Value/EBITDA (ttm)","Profit Margin (ttm)","Operating Margin (ttm)","Return on Assets (ttm)",
        "Revenue Per Share (ttm)","Qtrly Revenue Growth (yoy)","Gross Profit (ttm)","EBITDA (ttm)","Net Income Avl to Common (ttm)","Diluted EPS (ttm)","Qtrly Earnings Growth (yoy)","Total Cash Per Share (mrq)",
        "Total Debt (mrq)","Total Debt/Equity (mrq)","Operating Cash Flow (ttm)","Levered Free Cash Flow (ttm)","Beta:","500 52-Week Change","50-Day Moving Average","200-Day Moving Average","Shares Outstanding","Float:",
        "Held by Insiders","Shares Short (as of ","Short Ratio (","of Float (","Shares Short (prior month)",
        "Score"};
    
    private static int[] indexList = {0 ,4 ,7 ,3 ,5 ,2 ,13 ,1 ,15 ,16 ,23 ,17 ,18 ,19 ,6 ,20 ,21 ,22 ,23 ,24 ,25 ,26 ,27 ,28 ,29 ,14 ,30 ,31 ,32 ,33 ,8 ,9 ,34 ,35 ,11 ,12 ,36 ,37 ,38 ,10 ,39 ,40 ,41 ,42};
    
    private static String[] out = new String[wordList.length];  
    private static int wordNumber = 0;
    
  /**
   * This function runs the data finding algorithm 
   * @param html contains all the html for a web page 
   */
    public static String[] searchHTML( BufferedReader html) throws IOException
    {
        
        wordNumber = 0;
        // sets all values of out to N/A 
        for(int i = 0;i<out.length;i++)
        {
            out[i]="N/A";
        }
        
        String line;
        // searches line by line 
        while(null != (line = html.readLine()))
        {
               findDate(line);
        }
        
        out[out.length-1] = Integer.toString(Algorithm.startAlg(out));
        // returns found data 
        return out;
        
    }
    
   /**
   * This function searches the html code for the required data line by line
   * @param line line of html code
   */
    public static void findDate(String line)
    {
        
        String cla= "class";  
        String statName;
        ///String line = htmlLine;//.toLowerCase(); 
        for(int i =0;i<line.length(); i++)
        {
            
               if(wordNumber >= indexList.length)
               {
                    break;
               }
        
                int index = indexList[wordNumber];
                statName = wordList[index];
                if(statName.charAt(0) == line.charAt(i) && (i+statName.length())<= line.length()) 
                {
                    String temp = line.substring(i, i+statName.length());
                // checks if the word is the stat we are loking for
                    if(temp.equals(statName))
                    {
                        wordNumber++;
                        int charNumber = i+statName.length();
                  
                    //checks for the word class
                        while(true)
                        {
                            if(line.charAt(charNumber) == 'c' )
                            {
                                String classTest = line.substring(charNumber,charNumber+cla.length() );
                            
                                if(classTest.equals(cla))
                                {
                                    break;
                                }
                        
                            }
                            charNumber++;
                            
                        } 
                        //find the left >
                        while(true)
                        {
                            if(line.charAt(charNumber) == '>')
                            {
                                break;
                            }
                            charNumber++;
                        }
                        // done to find market cap and price
                        if(index == 4||index == 0)
                        {
                            charNumber++;
                            while(true)
                            {
                                if(line.charAt(charNumber) == '>')
                                {
                                    break;
                                }
                                charNumber++;
                            }
                        }
                        int charEnd = ++charNumber;
                        // finds right < and the value inbetween the > <
                        while(true)
                        {
                            if(line.charAt(charNumber) == '<')
                            {
                                out[index] = line.substring(charEnd,charNumber);
                                break;
                            }
                            charNumber++;
                        }
                        
                   }
                }
                
               
            
            }
            
        
    }

    

    
    
}
