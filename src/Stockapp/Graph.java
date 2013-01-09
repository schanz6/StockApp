/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Stockapp;

import javax.swing.*;
import java.awt.*;
import java.util.* ;


/**
 *
 * @author Jeremy Scahnz
 */
public class Graph extends JPanel {
   
        private int height, width;
        private double high, low;
	private ArrayList<Integer> val;
	private ArrayList<String> date;
        private boolean yes;
	
    
    public Graph(){
        width = 500;
        height = 545;
        yes = false;
        
       
      this.setPreferredSize( new Dimension(width,height)); 
   }
    
   public void getVal(ArrayList<String> v){
       val = new ArrayList<Integer>();
      if(v.size()>0){
       high = (int)(parseDoubleComma(v.get(0))*100);
       low = high;
      
      
       for(int a = 0;a<v.size();a++ ){
           
           int out = (int)(parseDoubleComma(v.get(a))*100);
          
           if(out<low)
               low =out;
           if(out>high)
               high =out;
           
           val.add(out);
           
       }
      }
      
   }
   public void getDate(ArrayList<String> d){
       date =d;
   }
      
   public ArrayList<cord> findY(){
       ArrayList<cord> out = new ArrayList<cord>();
      if(val.size()>0){
       double ratio = 400/val.size();
       double temp;
       for(int a =0;a<val.size();a++){
           if(high !=low){
              temp = (val.get(a)-low)/(high-low); 
              temp = 325-(300*temp);
           }else{
                temp = 175;
           }
           
           
           double tx = (ratio*(a))+75;
        
           out.add(new cord((int)temp,(int)tx));
       }
      }
       return out;
       
   }
   
   public  double parseDoubleComma(String out){
  
	String left,right;
	String s = out;
	for(int i =0;i<s.length();i++){
		if(s.charAt(i)== ','){
		     left = s.substring(0,i);
		     right = s.substring(i+1,s.length());
		     s = left+right;
		    
		}
	}
        
        if(s.charAt(s.length()-1)== 'B'||s.charAt(s.length()-1)== 'M' ){
            s = s.substring(0,s.length()-1);
        }
	return Double.parseDouble(s);
	
    }
   
   public void go(){
       yes= true;
       repaint(); 
   }
    
   public void paintComponent(Graphics g)
   {   g.clearRect(0,0,height,width);
       g.setColor(Color.black);

       g.drawLine(75, 25, 75, 325);
       g.drawLine(75 , 325, 475,325);
       if(yes){
       ArrayList<cord> out = findY();
      
       for(int a=0;a<out.size()-1;a++){
            g.setColor(Color.blue);
           g.fillOval(out.get(a).X-3,out.get(a).Y-3,5,5);
           g.drawLine(out.get(a).X,out.get(a).Y,out.get(a+1).X,out.get(a+1).Y);
            g.setColor(Color.black);
           g.drawLine(out.get(a).X,327,out.get(a).X,323);
          g.drawString(date.get(a).substring(8),out.get(a).X-9,340);

       }
       if(out.size()>0){
            g.setColor(Color.blue);
            g.fillOval(out.get(out.size()-1).X-3,out.get(out.size()-1).Y-3,5,5);
            g.setColor(Color.black);
            g.drawLine(out.get(out.size()-1).X,327,out.get(out.size()-1).X,323);
            g.drawString(date.get(out.size()-1).substring(8),out.get(out.size()-1).X-9,340);
       }
       
       double l = low/100;
       double h  = high/100;
       double ratio = (h -l)/8;
       int scale = 300/8;
      
       for(int a =0;a<=8;a++){
           String temp = String.format("%.4g",(h - (ratio*a)));
           g.drawLine(73,25+(scale*a),77,25+(scale*a));
           g.drawString(temp,0,25+(scale*a));
           
       }
       
       }
       
       
       
   }
   
   public class cord{
       int X;
       int Y;
      
       
       public cord(int y, int x){
           X =x;
           Y=y;
           
       
       }
   }

}
