/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package Stockapp;
 
import java.io.*;
import java.net.URL;
import java.util.*;
import javax.swing.* ;


 
/**
*
* @author Jeremy Schanz
*/
 class main   {
 
  private static tofile f;
  
  private static String[] wordList ={"price","Price/Book","PEG","Trailing",
        "Cap","Forward","Equity","Value","High","Low","Institutions","Vol3","Vol10","score"};
        
 
    public static void main(String[] args)throws IOException  {
          
          new window(); 
     
    }
    /*This code is from the days when this was a comandline program
     * 
     * 
     * 
    public static void com()throws IOException{
      
        Scanner key = new Scanner(System.in);
       
        
        System.out.print("Retreaving data(y/n)? ");
        String ret = key.nextLine();
        char noF= ret.charAt(0);
       if(noF == 'y'){
           System.out.print("Enter a Stocks 3 or 4 leter tag: ");
           String toret = key.nextLine();
           
           System.out.print("Enter a Stat Type: ");
           String tostat = key.nextLine();
           
           ret(toret,tostat);
           
           
           
       }else{
        System.out.print("Input from file(y/n)? ");
        String nofile = key.nextLine();
         noF= nofile.charAt(0);
       // the fallowing code askes if you want to take in from file or no and if you dont askes you to type in a stock tag 
        if(noF == 'y'){
            
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
        else{    
        System.out.print("Enter a Stocks 3 or 4 leter tag: ");
        String stock = key.nextLine();
        
        
       // stock = stock.toUpperCase();
        String stock_url = "http://finance.yahoo.com/q/ks?s="+stock+"+Key+Statistics/";
       
        
        search(stock_url,stock);  
        
        f.out();
        }
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
        f.add(out, tag, score);
        f.addData(out, tag, score);
       
        
    }
    
    public static void ret(String tag, String type){
        ArrayList<String> data = f.getData(tag);
        int num =0;
        while(true){
            if(type.trim().equals(wordList[num])){
                break;
            }
            num++;
        }
        System.out.println(data.size());
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
                System.out.println(data.get(a).substring(front,back));
                break;
              }
              
          }  
        }
    }
        */
}