
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author KevinBarasa
 */
public class Decimal_To_Binary_Conversion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        double decimalInput;
        
        //To capture number input
        Scanner keyboard = new Scanner(System.in);
        
        System.out.println("Input a decimal number to convert into binary!!");
        decimalInput = keyboard.nextDouble();
        
        System.out.println("Starting conversion!!\n");
        
        //Obtain an instance of the class that will perform the conversion
        Decimal_To_Binary_Conversion_Class obj = new Decimal_To_Binary_Conversion_Class(decimalInput);
        
        //Obtain a ForkJoin/Task pool because class that performs conversion performs a parrallel execution
        ForkJoinPool fjp = new ForkJoinPool();
        
        //And invoke class that will perform conversion
        fjp.invoke(obj);
    }
    
}

