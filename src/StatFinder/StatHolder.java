/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

/**
 * The class holds stats together  
 * @author Jeremy Schanz
 */
public class StatHolder {
    
    private double statX,statY;
    private double price;
    private String name;
    
    
   
    /**
   * This constructor sets up the stat holder class
   * @param Name stock tag 
   * @param Price price of stock
   * @param StatX holds data of the first stat 
   * @param StatY holds data of the second stat 
   * @param Price price of stock
   */
    public StatHolder(String Name ,double Price,double StatX, double StatY)
    {
        statX = StatX;
        statY = StatY;
        name = Name;
        price = Price;
        
    }
    
    /**
   * This Function returns stock tag 
   * @return returns name variable 
   */
    public String getName()
    {
        return name;
    }
    
     /**
   * This Function returns first stat 
   * @return returns statX variable 
   */
    public double getStatX()
    {
        return statX;
    }
    
       /**
   * This Function returns second stat 
   * @return returns StatY variable 
   */
     public double getStatY()
    {
        return statY;
    }
     
        /**
   * This Function returns price
   * @return returns price variable 
   */
    public double getPrice()
    {
        return price;
    }
   
}

