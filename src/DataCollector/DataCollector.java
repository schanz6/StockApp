/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCollector;

import Stockapp.look;
import Stockapp.tofile;
import java.io.*;
import java.net.URL;
import java.util.*;
/**
 *
 * @author schanz8
 */


public class DataCollector {
    private static tofile f;
    
    public static void main(String[] arg)throws IOException{
            f = new tofile("UnderVal.txt");
       
            tofile In = new tofile("stocks.txt");
           ArrayList<String> list = In.rStock(); 
            for(int j =0; j<list.size();j++){
                String stock = list.get(j);
                stock = stock.trim();
                
                String stock_url = "http://finance.yahoo.com/q/ks?s="+stock+"+Key+Statistics/";
               //System.out.println(stock_url); 
        
               search(stock_url,stock);  
        
              f.out();   
            }
        
   
    }
    
    public static void search(String site, String tag) throws IOException{    
        f = new tofile("UnderVal.txt");
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
        //f.add(out, tag, score);
        f.addData(out, tag, score);
       
        
    }
    
  
}
