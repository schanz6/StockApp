/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataCollector;

import Stockapp.algorithm;

/**
 *
 * @author schanz8
 */
public class Find {
    private static int count = 0;
    private static String book = "Price/Book (mrq)";
    private static final String[] wordList ={"yfi_rt_quote_summary_rt_top","Price/Book (mrq)","PEG Ratio (5 yr expected)","Trailing P/E (ttm, intraday)",
        "Market Cap (intraday)","Forward P/E (","Return on Equity (ttm):","Enterprise Value (","52-Week High ","52-Week Low (","% Held by Institutions","Avg Vol (3 month)","Avg Vol (10 day)","Score"};
    private static String[] out = new String[wordList.length];  
    
    public Find(){
        for(int a = 0;a<out.length;a++)
            out[a]="N/A";
    }
    // searches the code for the values i am looking for and adds them to array
    public static void findX(String l){
        
        String cla= "class";  
        String line = l;//.toLowerCase(); 
        for(int i =0;i<line.length(); i++){
            for(int j = 0;j<wordList.length-1;j++){
              book = wordList[j];
            if(book.charAt(0) == line.charAt(i)&& (i+book.length())<= line.length()) {
                String temp = line.substring(i, i+book.length());
                if(temp.equals(book)){
                    //System.out.println("yes " +i);
                    int t1=0;
                    int c1 = i+book.length();
                    int hold1= 0;
                    while(true){
                        if(line.charAt(c1) == 'c' ){
                            String cltest = line.substring(c1,c1+cla.length() );
                        if(cltest.equals(cla)){
                            break;
                        }
                        
                        }
                        c1++;
                            
                    } 
                    while(true){
                        if(line.charAt(c1) == '>'){
                         break;
                        }
                        c1++;
                    }
                    // done to find market cap
                    if(j==4||j==0){
                        c1++;
                        while(true){
                        if(line.charAt(c1) == '>'){
                         break;
                        }
                        c1++;
                    }
                    }
                    int back1 = ++c1;
                    while(true){
                        if(line.charAt(c1) == '<'){
                           // System.out.println("back "+c1);
                            out[j] = line.substring(back1,c1);
                            break;
                        }
                        c1++;
                                }
                }
            }
        
            
        }
        }
    }
    //allows array to be returned 
     public static String[] getCount(){
          return out;
      }
     // prints out stock numbers 
     public static int pOut(){
          System.out.println("Price: "+out[0]);
         for(int i = 1;i<wordList.length;i++){
            System.out.println(wordList[i]+" "+out[i]);
         }
         
         algorithm toA = new algorithm();
           return toA.startAlg(out);
     }
    
}

