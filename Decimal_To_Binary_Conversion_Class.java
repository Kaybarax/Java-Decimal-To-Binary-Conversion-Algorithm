
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.concurrent.RecursiveAction;

/**
 *
 * @author KevinBarasa
 */
public class Decimal_To_Binary_Conversion_Class extends RecursiveAction {

    private static final long serialVersionUID = -8920870196083630584L;
    
    //Declare instance variables
    
    @SuppressWarnings("FieldMayBeFinal")
    private ArrayList<String> _left_side_binary_units_array, _right_side_binary_units_array;
    StringTokenizer _array_of_DecimalInput;
    
    @SuppressWarnings("FieldMayBeFinal")
    private String _left_side_decimal_string_unit, _right_side_decimal_string_unit, 
            _input_Decimal_String_Value, _final_Binary_Result;
    
    HandleLeftSide handleLeftSide;
    HandleRightSide handleRightSide;
    
    //Initialize instance variables in class constructor
    public Decimal_To_Binary_Conversion_Class(double decimalInput)
    {
        this._left_side_binary_units_array = new ArrayList<>();
        this._right_side_binary_units_array = new ArrayList<>();
        this._array_of_DecimalInput =  null;
        
        this._left_side_decimal_string_unit = "";
        this._right_side_decimal_string_unit = "";
        
        //Just for logging
        //System.out.println("Recieved decimal input is: "+decimalInput+"\n");
        
        this._input_Decimal_String_Value = String.valueOf(decimalInput); //Prepare input for processing
        this._final_Binary_Result = "";
        
        handleLeftSide = new HandleLeftSide();
        handleRightSide = new HandleRightSide();
    }
    
    //Divide the decimal input into Left Side (LS) and Right Side (RS) of the decimal point, and left side
    //setter method
    private void setUp_DecimalInput_Array()
    {
        System.out.println("Splitting decimal input into Left Side (LS) and Right Side (RS)\n");
        System.out.println("String representation of decimal input is: "+this._input_Decimal_String_Value+"\n");
        
        this._array_of_DecimalInput =  new StringTokenizer(this._input_Decimal_String_Value, ".");
        
        this._left_side_decimal_string_unit = this._array_of_DecimalInput.nextToken();
        this._right_side_decimal_string_unit = this._array_of_DecimalInput.nextToken();
        
        System.out.println("LS is: "+this._left_side_decimal_string_unit+"\n");
        System.out.println("RS is: "+this._right_side_decimal_string_unit+"\n");
        
        System.out.println("Split into LS and RS for binary conversion successful\n");
    }
    
    //getter methods
    private String get_left_side_decimal_string_unit()
    {
        return this._left_side_decimal_string_unit;
    }
    private String get_right_side_decimal_string_unit()
    {
        return this._right_side_decimal_string_unit;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void combine_LS_RS_And_Generate_Binary_Result()
    {   
        //Fit in the left side binary result
        this._left_side_binary_units_array.stream().forEach((element) -> {
            this._final_Binary_Result += element;
        });
        
        //Just for logging
        //System.out.println("LS Binary: "+this._final_Binary_Result);
        
        this._final_Binary_Result += "."; //include the decimal point
        
        //Just for logging
        //System.out.println("LS Binary with decimal point: "+this._final_Binary_Result);
        
        //Fit in the right side binary result
        this._right_side_binary_units_array.stream().forEach((element) -> {
            this._final_Binary_Result += element;
        });
        
        //Just for logging
        //System.out.println("Full Binary Result: "+this._final_Binary_Result);
    }
    
    private void printBinaryResult()
    {
        System.out.println("Full Binary Result: \n"+this._final_Binary_Result+"\nto 8 significant figures");
    }

    @Override
    protected void compute() {
        setUp_DecimalInput_Array();
        invokeAll(this.handleLeftSide, this.handleRightSide);
        combine_LS_RS_And_Generate_Binary_Result();
        printBinaryResult();
    }
    
    //Inner helper class to handle LS of decimal input
    class HandleLeftSide extends RecursiveAction
    {
        private static final long serialVersionUID = 800378596461009133L;
        
        private void handle_LS()
        {
            String string_LS = get_left_side_decimal_string_unit(); //Obtain LS string
            int int_LS = Integer.parseInt(string_LS); //Convert the LS back into integer
            
            System.out.println("Integer value of LS: "+int_LS);

            while (int_LS > 0)
            {
                int modulus = int_LS % 2; //Obtain the binary entities, i.e 1 or 0
                
                //Just for logging
                //System.out.println("Got binary unit: "+modulus);
                
                _left_side_binary_units_array.add(String.valueOf(modulus)); //Push the binary entities into 
                                                                                   //LS as binary units, array holder
                int_LS = Math.floorDiv(int_LS, 2); // Ready int_LS to provide the next binary entity
                
                //Just for logging
                //System.out.println("Continuing LS Integer: "+int_LS);
            }

            //Reverse array to have the correct binary representation
            Collections.reverse(_left_side_binary_units_array);
            
            //Just for logging
            /*System.out.println("The correct LS binary digits are:\n");
            _left_side_binary_units_array.stream().forEach((element) -> {
                System.out.println("LS binary digit value: "+element+"\n");
            });*/
        }
        
        @Override
        protected void compute() {
            handle_LS();
        }
    }
    
    //Inner helper class to handle RS of decimal input
    class HandleRightSide extends RecursiveAction
    {
        private static final long serialVersionUID = -5252842714205435521L;
        
        @SuppressWarnings("UnusedAssignment")
        private void handle_RS()
        {
            int figures = 8; //For getting the binary only up to 8 figures

            @SuppressWarnings("UnusedAssignment")
            String divider_Determinant = ""; //Helper in determining divider
            int divider = 0; //Will be divider
            
            String string_RS = get_right_side_decimal_string_unit(); //Obtain RS string
            int string_RS_Length = string_RS.length(); // Get the number of digits in RS to determine divider
            int int_RS = Integer.parseInt(string_RS); //Convert the RS back into integer
            
            System.out.println("Integer value of RS: "+int_RS+"\n");

            //if the decimal at the right side of the decimal point is 0, then automatically,
            //the RS value is 0
            if(string_RS_Length == 1 && int_RS == 0)
            {
                _right_side_binary_units_array.add(String.valueOf(int_RS));
                
                //Just for logging
                //System.out.println(".......//...... RS value is 0 .......//........\n");
            }

            else
            {
                while(figures > 0)
                {
                    divider_Determinant = ""; //Start with as empty always
                    divider = 0; //Start with as zero always
                    
                    divider_Determinant = String.valueOf(int_RS);
                    divider = divider_Determinant.length();
                    
                    double double_RS = (double) ((int_RS*2)/(Math.pow(10, divider)));
                    
                    //Just for logging
                    //System.out.println("RS value in computation: "+double_RS+"\n");
                    
                    _right_side_binary_units_array.add(String.valueOf((int)Math.floor(double_RS)));
                    
                    int_RS = (int) ((int_RS*2)%(Math.pow(10, divider)));
                    
                    //Just for logging
                    //System.out.println("The disturbing modulus: "+int_RS+"\n");
                    
                    figures--;
                }
                
                //Just for logging
                /*_right_side_binary_units_array.stream().forEach((element) -> {
                    System.out.println("RS binary digit value: "+element+"\n");
                });*/
            }
        }

        @Override
        protected void compute() {
            handle_RS();
        }
    }
}
