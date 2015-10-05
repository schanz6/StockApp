/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

/**
 *
 * @author Jeremy Schanz
 */
public class RegressionHolder
{
    private String statName;
    private double b1;
    private double b2;
    private double correlation;
    
     /**
   * This constructor sets up the regression holder class
   * @param statName stock stat name
   * @param b1 b1 value in y = x*b1 + b2
   * @param b2 b2 value in y = x*b1 + b2
   * @param correlation correlation between x and y values  
   */
    public RegressionHolder(String statName, double b1, double b2, double correlation)
    {
        this.statName = statName;
        this.b1 = b1;
        this.b2 = b2;
        this.correlation = correlation;
    }
    
   /**
   * This Function returns Stat Name
   * @return returns statName variable 
   */
    public String getStatName()
    {
        return statName; 
    }
    
    /**
   * This Function returns B1 value 
   * @return returns b1 variable 
   */
    public double getB1()
    {
        return b1; 
    }
    
     /**
   * This Function returns B2 value 
   * @return returns b2 variable 
   */
    public double getB2()
    {
        return b2; 
    }
    
     /**
   * This Function returns Correlation value 
   * @return returns correlation variable 
   */
    public double getCorrelation()
    {
        return correlation; 
    }
}
