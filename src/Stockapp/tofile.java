/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stockapp;

/**
 *
 * @author Jeremy Schanz
 */
import java.io.*;
import java.util.*;
import java.text.*;

// simple tofile class that reads from stock.txt and prints results to underval.txt
public class tofile 
{ 
	private ArrayList<String> stock;
	private Scanner ifile ,sfile;
	private PrintWriter oput;
	private String n;
	private File file,dfile;
        
   
      public tofile(String name)throws IOException{
       n= name;
       stock = new ArrayList<String>();
       String d = "data.txt";
       try{
       file = new File(name);
       ifile = new Scanner(file);
       toStock();
       } catch (Exception ex) {
            System.out.println("NO File");
        }
       
    
      }
      
      public void toStock()throws IOException{
      	      String next;
      	      
      	      while(ifile.hasNextLine()){
      	      	      next = ifile.nextLine();
      	      	    
      	      	      stock.add(next);
      	      	  
      	      	  
      	      	  }
      	    
      	      
      }
      
    
      
      public ArrayList<String> rStock()throws IOException{ 
      	    return stock;
          
      }
      
     
      
      public void add(String[] data, String tag,int score )
      {
          if(!data[0].equals("0.00")){
          tag = tag.toUpperCase();
          String out = tag+"  "+data[0]+"   "+score; 
          
              stock.add(out); 
          }
      }
      public void addData(String[] data, String tag,int score )throws IOException{
        
          if(!data[0].equals("0.00")){
             String d = tag.trim()+".txt";
             dfile = new File(d);
             
             if(!dfile.exists()){
                
                 dfile.createNewFile();
             }
             
             DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
               Calendar cal = Calendar.getInstance();

              String out = dateFormat.format(cal.getTime())+" ";
              
              for(int a =0;a<data.length;a++){
                  out = out +data[a]+" ";
              }
          
           
             FileWriter fw = new FileWriter(d,true);
	     BufferedWriter bw = new BufferedWriter(fw);
             bw.newLine();
             bw.write(out);
	     bw.close();
      
          
         
          
   
          }
          
      }
      
      public ArrayList<String> getData(String tag){
        
         String d = tag.trim()+".txt";
         try{
         File  f = new File(d);
            sfile = new Scanner(f);
           
        }catch (Exception ex) {
            System.out.println("NO File");
            return null;
        }
        ArrayList<String> s = new ArrayList<String>();
        String next;
        
      	      while(sfile.hasNextLine()){
      	      	      next = sfile.nextLine();
      	      	   
      	      	      s.add(next);
      	      	  
      	      	  }
      	     
          
          return  s;
      }
      
      
      public void out()throws IOException{
      	       
      	      oput = new PrintWriter(n);
      	      sort();
      	      for(int a=0;a<stock.size();a++){
      	     	     
      	      oput.println(stock.get(a));
      	      }
      	      oput.close();
      }
      
    
      public void sort(){
      	      for(int b=0;b<stock.size();b++){
      	      	      String com = stock.get(b);
      	      	      for(int c=b+1;c<stock.size();c++){
      	      	      	      if(com.compareToIgnoreCase(stock.get(c))>0){
      	      	      	      	      String temp = com;
      	      	      	      	      stock.set(b,stock.get(c));
      	      	      	      	      stock.set(c,com);
      	      	      	      	      com = stock.get(b);
      	      	      	      }
      	      	      }
      	      }
      }
      
}
      	      	      
      	      	      
      
   
