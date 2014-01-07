/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stockapp;

import DataCollector.SearchHTML;
import StatFinder.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
/**
 * This class creates the window that is used for the graphical component of this app 
 * @author Jeremy Schanz
 */


public class Window extends JFrame
{
   private final int WINDOW_WIDTH = 1000;   
   private final int WINDOW_HEIGHT = 400;  
   private  JTextField text;
   private  JComboBox drop;
   private  JTextArea area;
   private  Graph graph;
 
 
  private static  String[] wordList ={"Price","Price/Book (mrq)","PEG Ratio (5 yr expected)","Trailing P/E (ttm, intraday)",
        "Market Cap (intraday)","Forward P/E ","Return on Equity (ttm):","Enterprise Value ","52-Week High ","52-Week Low ","% Held by Institutions",
        "Avg Vol (3 month)","Avg Vol (10 day)","Price/Sales (ttm)","Current Ratio (mrq)","Enterprise Value/Revenue (ttm)","Enterprise Value/EBITDA (ttm)","Profit Margin (ttm)","Operating Margin (ttm)","Return on Assets (ttm)",
        "Revenue Per Share (ttm)","Qtrly Revenue Growth (yoy)","Gross Profit (ttm)","EBITDA (ttm)","Net Income Avl to Common (ttm)","Diluted EPS (ttm)","Qtrly Earnings Growth (yoy)","Total Cash Per Share (mrq)",
        "Total Debt (mrq)","Total Debt/Equity (mrq)","Operating Cash Flow (ttm)","Levered Free Cash Flow (ttm)","Beta:","500 52-Week Change","50-Day Moving Average","200-Day Moving Average","Shares Outstanding","Float:",
        "Held by Insiders","Shares Short ","Short Ratio ","of Float ","Shares Short (prior month)","Score"};
    /**
     * This constructor set up the window by creating the panels, buttons, and text box and
     * then connecting them to there proper place 
     * @throws IOException 
     */

   public Window() throws IOException
   {
    
      
      setTitle("Stock App");

      
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      
      setLayout(new GridLayout(1, 2));

      JButton currentData = new JButton("Current Data");
      JButton enter = new JButton("Enter");
      text = new JTextField(10);
      
      drop = new JComboBox(wordList);
      area = new JTextArea(10, 40);
      JScrollPane scroll = new JScrollPane(area); 

      
      JPanel panel1 = new JPanel();
      JPanel panel2 = new JPanel();
      JPanel panel3 = new JPanel();
      JPanel panel4 = new JPanel();
      JPanel panel5 = new JPanel();
      JPanel panel6 = new JPanel();
      graph = new Graph();
      panel3.setLayout(new GridLayout(2, 1));
      
      text.addActionListener(new EnterListener());
      enter.addActionListener(new EnterListener());
      currentData.addActionListener(new CurrentDataListener());
      
      // Add the buttons to the panels.
      panel1.add(drop);
      panel1.add(text);
      panel1.add(enter);
      panel1.add(currentData);
      panel4.add(scroll);
      panel2.add(graph);
      panel3.add(panel1);
      panel3.add(panel4);
      add(panel3);  // Goes into row 1, column 1
      add(panel2);  // Goes into row 1, column 2
      
      setVisible(true);
   }
   
   /**
    * This class handles what happens when the enter button is clicked 
    */
   private class EnterListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          String tag = text.getText();
          
          int item = drop.getSelectedIndex();
          area.append("\n\n"+wordList[item]+"\n");
          // gets requested saved data 
          ArrayList<Data> out = getSavedDate(tag,item);
          ArrayList<String> value = new ArrayList<String>();
          ArrayList<String> date = new ArrayList<String>();
         
          
          if(out != null)
          {
            // converts data to graph format 
            for(int i = 0;i<out.size();i++)
            {
                  area.append(out.get(i) +"\n");
                  if(!out.get(i).value.equals("N/A")) 
                  { 
                    value.add(out.get(i).value);
                    date.add(out.get(i).date);
                  }
                  
            }
           
            graph.setData(date,value);
            graph.update();
          }
          else
          {
             area.append("NO File\n"); 
          }
      }
   }
    /**
    * This class handles what happens when the current data button is clicked 
    */
   private class CurrentDataListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          String tag = text.getText();
         
          area.append("\n\n"+tag.toUpperCase()+"\n");
          
          try
          {
            // gets data from search 
            String[] out = search(tag.toLowerCase());
            
            if(out != null)
            {   //prints out data to text box
                for(int i = 0;i<out.length;i++)
                {
                    area.append(wordList[i]+": "+out[i] +"\n");
                }
            }
            else
            {
                area.append("\n"+"Not Found");  
            }
          
          
          }
          catch(Exception ex)
          {
              area.append("\n NOT FOUND");
          }
      
      }
   }
     /**
   * This function opens up the URL connection and read the HTML line by line
   * @param tag stock tag of desired stock 
   */
   public  String[] search(String tag) throws IOException
   {    
        String site = "http://finance.yahoo.com/q/ks?s="+tag+"+Key+Statistics/";
        SearchHTML find = new SearchHTML();
        BufferedReader reader = null;
        
        try 
        {
            // this code gets html from wedsite
            URL myUrl = new URL(site);
            reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));
            
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        
        return SearchHTML.searchHTML(reader);
    }
    /**
     * This Function get the requested data that has already been saved to files 
     * @param tag tag of stock
     * @param dataNumber number representing the requested data point
     * @return array containing requested data with its date collected 
     */
    public  ArrayList<Data> getSavedDate(String tag, int dataNumber)
    {
       ArrayList<StockData> data = StockFileReader.getStockData(tag);
       ArrayList<Data> out = new ArrayList();
       
       if(data == null)
       {
           return null;
       }
       // gets data and converts to data class format 
       for(int i = 0;i<data.size();i++)
       {
          out.add(new Data (data.get(i).date, String.valueOf(data.get(i).get(dataNumber))));
       }
        
        return out;
    }
    
/**
 * This class holds the date and the value together 
 * @author Jeremy Scahnz
 */
   public class Data
   {
       public String value;
       public String date;
       
       public Data(String d, String v)
       {
           value = v;
           date  = d;
       }
    
       public String toString()
       {
           return date+" - "+value;
       }
   } 
    
}
