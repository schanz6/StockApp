/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stockapp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.*;
/**
 *
 * @author Jeremy Schanz
 */

    




   

public class window extends JFrame
{
   private final int WINDOW_WIDTH = 1000;   
   private final int WINDOW_HEIGHT = 400;  
   private  JTextField text;
   private  JComboBox drop;
   private  JTextArea Area;
   private  Graph G;
   private  tofile f;
   
   private static String[] List ={"Price","Price/Book","PEG Ratio","Trailing P/E",
        "Market Cap","Forward P/E","Return on Equity","Enterprise Value","52-Week High","52-Week Low","% Held by Institutions","Avg Vol (3 month)","Avg Vol (10 day)","Score"};
   
 

   public window() throws IOException
   {
        f = new tofile("UnderVal.txt");
      
      setTitle("Stock App");

      
      setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      
      setLayout(new GridLayout(1, 2));

      JButton cur = new JButton("Current Data");
      JButton ent = new JButton("Enter");
      text = new JTextField(10);
      
      drop = new JComboBox(List);
      Area = new JTextArea(10, 40);
      JScrollPane scroll = new JScrollPane(Area); 

      
      JPanel panel1 = new JPanel();
      JPanel panel2 = new JPanel();
      JPanel panel3 = new JPanel();
      JPanel panel4 = new JPanel();
      JPanel panel5 = new JPanel();
      JPanel panel6 = new JPanel();
      G = new Graph();
      panel3.setLayout(new GridLayout(2, 1));
      
     text.addActionListener(new ButtonListener());
      ent.addActionListener(new ButtonListener());
      cur.addActionListener(new CurListener());
      
      // Add the buttons to the panels.
      panel1.add(drop);
      panel1.add(text);
      panel1.add(ent);
      panel1.add(cur);
      panel4.add(scroll);
      panel2.add(G);
      panel3.add(panel1);
      panel3.add(panel4);
      add(panel3);  // Goes into row 1, column 1
      add(panel2);  // Goes into row 1, column 2
      //add(panel3);  // Goes into row 1, column 3
      //add(panel4);  // Goes into row 2, column 1
      //add(panel5);  // Goes into row 2, column 2
      //add(panel6);  // Goes into row 2, column 3

      
      setVisible(true);
   }
   
   private class ButtonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          String tag = text.getText();
          
          int item = drop.getSelectedIndex();
          Area.append("\n\n"+List[item]+"\n");
          ArrayList<Data> out = ret(tag,item);
          ArrayList<String> val = new ArrayList<String>();
          ArrayList<String> date = new ArrayList<String>();
         if(out !=null){
          for(int a = 0;a<out.size();a++){
              Area.append(out.get(a) +"\n");
          }
          for(int b=0;b<out.size();b++){
             if(!out.get(b).val.equals("N/A")) { 
              val.add(out.get(b).val);
              date.add(out.get(b).date);
             }
          }
          G.getVal(val);
          G.getDate(date);
          G.go();
      }else{
            Area.append("NO File\n"); 
         }
      }
   }
   
   private class CurListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
          String tag = text.getText();
          text.setText("");
          
          Area.append("\n\n"+tag.toUpperCase()+"\n");
          try{
          String[] out = search(tag.toLowerCase());
          if(out[0]!= "n/a"){
          for(int a = 0;a<out.length;a++){
              Area.append(List[a]+": "+out[a] +"\n");
          }
          }
          else
            Area.append("\n"+"Not Found");  
          
          
          }catch(Exception ex){
              Area.append("\n NOT FOUND");
          }
      
      }
   }
   
   public  String[] search(String tag) throws IOException{    
        String site = "http://finance.yahoo.com/q/ks?s="+tag+"+Key+Statistics/";
        look test = new look();
        try {
            // this code gets html from wedsite
            URL my_url = new URL(site);
            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
            String strTemp = "";
            while(null != (strTemp = br.readLine())){
            test.findX(strTemp);
        }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // this sends to tofile class to print to Underval.txt
        int score = test.pOut();
        String[] out = test.getCount();
        out[out.length-1]= ""+score;
        f.add(out, tag, score);
        f.addData(out, tag, score);
        
        return out;
    }
    
    public  ArrayList<Data> ret(String tag, int num){
        ArrayList<String> data = f.getData(tag);
        ArrayList<Data> out = new ArrayList();
       if(data == null){
           return null;
       }
        for(int a =1;a<data.size();a++){
            int hold =0;
           
          for(int b=0;b<data.get(a).length();b++){
              if(data.get(a).charAt(b) == ' '){
                  hold++;
                  
              }
              if(hold == num+2){
                  int front = b+1;
                  int back = front;
                while(data.get(a).charAt(back) != ' '){
                    back++;
                }
                out.add(new Data(data.get(a).substring(0,10), data.get(a).substring(front,back)));
                break;
              }
              
          }  
        }
        return out;
    }
    
   public class Data{
       public String val;
       public String date;
       
       public Data(String d, String v){
           val =v;
           date =d;
       }
       public String toString(){
           return date+" - "+val;
       }
   } 
    
}