/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Stockapp;

/**
 *
 * @author jeremy schanz 
 */
public class algorithm {
    // sends to algorithem I made up doing stock research, it's only good for undervalue stocks 
    // scale is 22 to -6 any where over 6 being a preatty good score 
    public static int startAlg(String[] data){
        int total = 0;
       total = capVvalue(data[4],data[7])+price2Book(data[1])+peg(data[2])+roe(data[6])+institution(data[10])
               +peVSandP(data[3])+forVback(data[5],data[3]);
       
       System.out.println("The value is : " +total);
       
       return total;
       
        
        
    }
    //price of stock/ book value per share 
   public static int price2Book(String p){
   	   
           if(p.equals("N/A"))
               return 0;
   	    
   	    double pvb = parseDoubleComma(p);;
   	    
   	 
   	   if(pvb<.25 && pvb> 0)
   	    	   return 4;
   	   if(pvb<.50) 
   	   	   return 3;
   	   if(pvb<.75) 
   	   	   return 2;
   	   if(pvb< 1) 
   	   	   return 1;
   	   if(pvb< 3) 
   	   	   return 0;
   	   return -1;
   
   }
   // trailing PE / S&P 500 P/E ( P/E is Stock Price/ yearly earnings) 
   public static int peVSandP(String p){
   	    
   	    if(p.equals("N/A"))
               return 0;
   	    
   	    double you = parseDoubleComma(p);;
   	    //15.48 is average p/e ratio
   	    double ratio = you/15.48;
   	    
   	   if(ratio<.45)
   	    	    return 4;
   	   if(ratio<.65) 
   	   	    return 3;
   	   if(ratio<.85)
   	    	    return 2;
   	   if(ratio< 1)
   	    	    return 1;
   	   if(ratio< 1.5)
   	    	    return 0;
   	    
   	   return -1;
   
   }
   // Peg ratio (Peg is P/E ratio / (estimated) Earing per share growth)
    public static int peg(String p){
   	    
   	   if(p.equals("N/A"))
               return 0;
   	    
   	    double peg = parseDoubleComma(p);
   	   if(peg>0)
               return -1;
            
   	   if(peg<.25)
   	    	   return 4;
   	   if(peg< .5) 
   	   	   return 3;
   	   if(peg<.75)
   	    	   return 2;
   	   if(peg< 1) 
   	   	   return 1;
   	   if(peg< 2)
   	    	   return 0;
   	   
   	   return -1;
   
   }
   // Return on Equity (earnings / equite)
   public static int roe(String p){
   	   
        if(p.equals("N/A"))
               return 0;
   	   p =  p.substring(0,p.length()-2);
           
   	   double roe = parseDoubleComma(p); 
   	   if(roe> 35)
   	    	    return 4;
   	   if(roe> 20) 
   	   	    return 3;
   	   if(roe> 10)
   	    	    return 2;
   	   if(roe> 5)
   	    	    return 1;
   	   if(roe> 0)
   	    	    return 0;
   	    
   	   return -1;
   
   }
   
   
   // Percent of stock held by institutions 
    public static int institution(String p){
   	     
        if(p.equals("N/A"))
               return 0;
   	 p =  p.substring(0,p.length()-2);
           
   	   double ins = parseDoubleComma(p);
   	   
   	   if(ins< 25)
   	    	    return 2;
   	   if(ins< 50) 
   	   	   return 1;
   	   return 0;
   
   }
    // Forward PE vs. Trailing PE
   public static int forVback(String f, String t ){
       if(f.equals("N/A")|| t.equals("N/A"))
           return 0;
       
       double fwr = parseDoubleComma(f);
       double trl = parseDoubleComma(t);
       
       double ftRatio = fwr/trl;
       
       if(ftRatio<0 )
           return 0;
       if(ftRatio<.5)
           return 2;
       if(ftRatio<=1)
           return 1;
       if(ftRatio < 2)
           return 0;
       
       return -1;
       
           
       
   }
    // Market cap VS Enterprice Value
   public static int capVvalue(String Mark, String Value){
       
       if(Mark.equals("N/A")|| Value.equals("N/A"))
           return 0;
       
       char bm = Character.toLowerCase( Mark.charAt(Mark.length()-1));
       char bv =Character.toLowerCase( Value.charAt(Value.length()-1));
       Mark = Mark.substring(0,Mark.length()-2);
       Value = Value.substring(0,Value.length()-2);
       double mm = 0;
      double mv = 0;
       
       if(bm == 'b')
           mm = 1000000000;
       
       else
         mm = 1000000;
         
       if(bv == 'b')
           mv = 1000000000;
       else
         mv = 1000000; 
       
       double cap = parseDoubleComma(Mark);
       double ent = parseDoubleComma(Value);
       
       ent = ent*mv;
       cap = cap*mm;
      
       double emRatio=  ent/cap; 
       
       if(emRatio <0)
           return 2;
       if(emRatio<1)
           return 1;
       if(emRatio<2)
           return 0;
       else 
           return -1;
      
       
   } 
   // if number has comma it takes it out allwing parseDoubel to work 
   public static double parseDoubleComma(String out){
  
	String left,right;
	String s = out;
	for(int i =0;i<s.length();i++){
		if(s.charAt(i)== ','){
		     left = s.substring(0,i);
		     right = s.substring(i+1,s.length());
		     s = left+right;
		    
		}
	}
	
	return Double.parseDouble(s);
	
}

    
}
