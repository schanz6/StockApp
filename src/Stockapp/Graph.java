/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Stockapp;

import javax.swing.*;
import java.awt.*;
import java.util.* ;


/**
 * This class creates the graph for the stock App display  
 * @author Jeremy Scahnz
 */
public class Graph extends JPanel 
{
   
        private int height, width;
        private double high, low;
	private ArrayList<Integer> value;
	private ArrayList<String> date;
        private boolean isRepaint;
	
     /**
   * This constructor sets up the graph class 
   */
   public Graph()
   {
       // sets height and width
        width  = 500;
        height = 545;
        this.setPreferredSize(new Dimension(width,height)); 
        
        
        isRepaint = false;
        value = null;
        date  = null;
        
        
   }
    
     /**
   * This Function sets the data and the dates for the graph class and finds 
   * the max and min of the data
   * @param paramData array of dates
   * @param pramValue array of data
   */
   public void setData( ArrayList<String> paramDate ,ArrayList<String> paramValue)
   {
      value = new ArrayList<Integer>();
      date = paramDate;
      
      if(paramValue.size()>0)
      {
        high = (int)(statToDouble(paramValue.get(0))*100);
        low = high;
      
      // finds min and max values
        for(int i = 1;i<paramValue.size();i++ )
        {
           
           int out = (int)(statToDouble(paramValue.get(i))*100);
          
           if(out<low)
           {
               low =out;
           }
           if(out>high)
           {
               high =out;
           }
           value.add(out);
           
        }
      }
      
   }
      
     /**
   * This Function find where each data points actual coordinates fall on the graph 
   * @return array holding  the coordinates 
   */
   private ArrayList<Coordinates> findCoordinates()
   {
      ArrayList<Coordinates > out = new ArrayList<Coordinates >();
      // checks if array is not empty 
      if(value.size()>0)
      {
            //sets ratio based on number of data points 
            double ratio = 400/value.size();
            double tempY;
           
            //finds where each point lise based on high and low values 
            for(int i =0;i<value.size();i++)
            {
                // if data is not a horizontal line 
                if(high !=low)
                {
                    tempY = (value.get(i)-low)/(high-low); 
                    tempY = 325-(300*tempY);
                }
                else
                {
                    tempY = 175;
                }
           
           
                double tempX = (ratio*(i))+75;
        
                out.add(new Coordinates ((int)tempX, (int)tempY));
            }
      }
      
     return out;
       
   }

    
   /**
   * This Function is used to update the graph and allows caller to control the update  
   */
   public void update()
   {
       isRepaint = true;
       repaint(); 
   }
    
 
    /**
   * This Function draws the actual graph on the jpanel
   * @param g graphic component
   */
   public void paintComponent(Graphics g)
   {   
       g.clearRect(0,0,height,width);
       g.setColor(Color.black);

       g.drawLine(75, 25, 75, 325);
       g.drawLine(75 , 325, 475,325);
       
       if(isRepaint == true)
       {
            ArrayList<Coordinates> out = findCoordinates();
            // draws the points and lines on the graph 
            for(int i=0;i<out.size()-1;i++)
            {
                g.setColor(Color.blue);
                g.fillOval(out.get(i).X-3,out.get(i).Y-3,5,5);
                g.drawLine(out.get(i).X,out.get(i).Y,out.get(i+1).X,out.get(i+1).Y);
                g.setColor(Color.black);
                g.drawLine(out.get(i).X,327,out.get(i).X,323);
                g.drawString(date.get(i).substring(8),out.get(i).X-9,340);

            }
            // draws the last point and line
            if(out.size()>0)
            {
                g.setColor(Color.blue);
                g.fillOval(out.get(out.size()-1).X-3,out.get(out.size()-1).Y-3,5,5);
                g.setColor(Color.black);
                g.drawLine(out.get(out.size()-1).X,327,out.get(out.size()-1).X,323);
                g.drawString(date.get(out.size()-1).substring(8),out.get(out.size()-1).X-9,340);
            }
       
            //double l = low/100;
            double highPercent  = high/100;
            double ratio = (highPercent -(low/100))/8;
            int scale = 300/8;
      
            //writes the y axis values 
            for(int i =0;i <= 8; i++)
            {
               String temp = String.format("%.4g",(highPercent - (ratio*i)));
               g.setColor(Color.black);
               g.drawLine(73,25+(scale*i),77,25+(scale*i));
               g.drawString(temp,0,25+(scale*i));
           
            }
       
       }
       
       
       
       
       
   }
      
       /**
   * This Function finds the correct multiplier and then converts data to double 
   * @param value data being converted 
   * @return the data converted to double 
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
 * This class holds an x and y coordinates 
 * @author Jeremy Scahnz
 */
   public class Coordinates 
   {
       int X;
       int Y;
      
       
       public Coordinates ( int x, int y)
       {
           X = x;
           Y = y;
           
       
       }
   }

}
