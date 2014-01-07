/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StatFinder;

/**
 * THis class hold various static statistic functions 
 * @author Jeremy Schanz
 */
public class Statistics 
{
    
    /**
   * This Function find standard deviation from a list of doubles 
   * @param array  data you want to find SD of 
   * @return standard deviation of the list 
   */
       public static double standardDeviation(double[] array)
       {
            
            int length = array.length;
            
            double mean = mean(array);
            double differenceTotal = 0;
            //Finds the total of all the numbers minus the mean squared
            for (int index = 0; index < length; index++)
            {
                differenceTotal = differenceTotal + Math.pow(array[index] - mean, 2);
            }
            // result is the total devided by length-1 then square rooted 
            return Math.sqrt(differenceTotal / (length-1));
        }
       
    /**
   * This Function finds the mean of a list of doubles 
   * @param array data you want to find mean of
   * @return the mean of the list 
   */
        public static double mean(double[] array)
        {
            double total = 0;
            int length = array.length;

            for (int index = 0; index < length; index++)
            {
                total = total + array[index];
            }

           return total / length;
        }
/**
   * This Function finds the correlation between two list of data
   * @param x first list of data
   * @param y second list of data
   * @return returns correlation of data (between -1 to 1)
   */
        public static double correlation(double[] x, double[] y)
        {
            double meanX = mean(x);
            double meanY = mean(y);

            double deviationX = standardDeviation(x);
            double deviationY = standardDeviation(y);
          
            double total = 0;

            
            for (int index = 0; index < x.length; index++)
            {
                // calculates Z scores
                double devValueX = (x[index] - meanX) / deviationX;
                double devValueY = (y[index] - meanY) / deviationY;
              
                total = total + (devValueX * devValueY);
            }

            return total / (x.length - 1);

        }
        
   /**
   * This Function finds a linear regression between 2 lists of data
   * @param x first list of data
   * @param y second list of data
   * @return an array where the first value is the y intercept (b_0) and second is the b_1 value (y = b_0 + b_1*x)
   */
        public static double[] regression(double[] x, double[] y)
        {
            double totalCov = 0;
            double totalVarX = 0;
            double meanX = mean(x);
            double meanY = mean(y);            
            
            
            for(int i = 0; i< x.length; i++)
            {
                // calculates the variance of x and the covariance of  x,y
                totalVarX = totalVarX+(x[i]*x[i]);
                totalCov = totalCov + (x[i] * y[i]);
                
            }
            
            double varX = (totalVarX/x.length)- (meanX*meanX);
            double covXY = (totalCov/x.length) - (meanX*meanY);
            // finds b1 value = cov(x,y)/var(x)
            double b1 = covXY/varX;
            // finds b0 value = E[y] - b1*E[x]
            double b0 = meanY - (b1*meanX);
            
            return new double[] {b0,b1}; 
            
        }
    
    
}
