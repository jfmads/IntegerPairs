package integerpairs;


import java.util.Scanner;

/**
 *
 * @author Joseph Madden
 *
 * Date Created : 04/26/2019 
 * Date Modified: 04/26/2019
 *
 * A program that allows for an integer array to be passed in. It will then
 * output all of the pairs that sum up to 10.
 *
 * 1) output all pairs (includes duplicates and the reversed ordered pairs) 
 * 2) output unique pairs only once (removes the duplicates but includes the
 * reversed ordered pairs) 
 * 3) output the same combo pair only once (removes the reversed ordered pairs)
 *
 * ex: [1, 1, 2, 4, 4, 5, 5, 5, 6, 7, 9] the following results should occur: 
 * 1) [1,9], [1,9], [4,6], [4,6], [5,5], [5,5], [5,5], [5,5], [5,5], [5,5], [6,4], [6,4] [9,1] [9,1] 
 * 2) [1,9], [4,6], [5,5], [6,4], [9,1] 
 * 3) [1,9], [4,6], [5,5]
 *
 */
public class Main {

    static int[] inputNum;                      // string will be converted to ints and stored here
    static int[][] pairUnique;                  // will store the unique pairs
    static int[][] pairNoDups;                  // will store the no duplicate pair
    static int sumVal = 10;                     // sum value for pair to equal
    static int sizeU = 0;                       // size of unique 2D array
    static int sizeND = 0;                      // size of non dup 2D array
    static long pairRunTime = -1;               // estimate runtime
    static boolean noPairs = true;              // changes when a pair is found

    public static void main(String[] args) {
        getData();          
        System.out.println("Pairing Time: " + (System.currentTimeMillis() - pairRunTime) + " milliseconds\n");
    }

    /**
     * This method gathers a string of data from the keyboard. It then checks
     * if the data that was input is valid. If the data is invalid it will loop
     * back around. The method pairs() is called otherwise.
     */
    private static void getData() {
        Scanner in = new Scanner(System.in);    // scanner used for keyboard input
        String[] inputString;                   // string array to store keyboard input
        boolean invalid = true;                 // boolean value for while loop

        while (invalid) {
            System.out.print("Enter a string of integers seperated by spaces: "); // prompts for user input
            /* String values = "0 0 1 1 2 3 4 4 5 5 5 6 7 9 10 11 32";  // Test purposes
            System.out.print(values + "\n\n");                          // Test purposes
            inputString = values.trim().split(" "); */                  // Test purposes
            inputString = in.nextLine().trim().split(" ");      // trims and splits user string input
            invalid = false;
            inputNum = new int[inputString.length];
            for (int i = 0; i < inputString.length; i++) {      // traverses string to convert to int
                try {
                    inputNum[i] = Integer.parseInt(inputString[i]);
                } catch (NumberFormatException ne) {            // catches incorrect input
                    System.out.println(ne + "\n");              // outputs error message
                    invalid = true;                             // states invalid input
                    break;                // the rest of the string is irrelevent once there is an invalid integer input
                }   
            }
        }
        pairs();
    }
    
    /**
     * This method is used to determine if there are any pairs within the 
     * integer dataset. It initializes the 2D arrays so that they 
     * can store the various pairs. As the pairs are found, they are output
     * to the user. There are 2 methods that are called to check for 
     * unique and duplicates.
     */
    private static void pairs(){
        
        pairRunTime = System.currentTimeMillis();   // this is where the program pair analysis begins
        pairUnique = new int[inputNum.length * 2][];            // to verify there is enough space allocated
        pairNoDups = new int[inputNum.length * 2][];
        
        for (int i = 0; i < inputNum.length; i++){              // traverse integer array starting with first value
            for(int j = 0; j < inputNum.length; j++){           // traverse integer array starting with first value
                if(Integer.sum(inputNum[i], inputNum[j]) == sumVal && i != j){  // if the sum == sumValue && the values are not at the same location
                    noPairs = false;                            // there is atleast one pair !!
                    if(checkUnique(i,j)){                       // calls the method to check for uniqueness
                        pairUnique[sizeU] = new int[2];         // allocates 2 slots for the values
                        pairUnique[sizeU][0] = inputNum[i];     // sets the first slot equal to the value at location i in the integer array
                        pairUnique[sizeU][1] = inputNum[j];     // sets the second slot equal to the value at location j in the integer array
                        sizeU++;                                // increments the known size of the unique array
                    }
                    
                    if(checkDup(i,j)){                          // calls the method to check for duplicates
                        pairNoDups[sizeND] = new int[2];        // allocates 2 slots for the values
                        pairNoDups[sizeND][0] = inputNum[i];    // sets the first slot equal to the value at location i in the integer array
                        pairNoDups[sizeND][1] = inputNum[j];    // sets the second slot equal to the value at location j in the integer array
                        sizeND++;                               // increments the known size of the non-duplicate array
                    }
                    
                    System.out.print("[" +inputNum[i] + "," + inputNum[j] + "]" + " "); // outputs a pair == sum
                }
            }
        }
        
        if(noPairs){                                            // if there are no pairs getData() is called again
            System.err.println("Please enter valid integer pairs");
            getData();
        } else {
            System.out.print("\n");
            
            for(int i = 0; i < sizeU; i++)                      // outputs the unique pairs
                System.out.print("[" +pairUnique[i][0] + "," + pairUnique[i][1] + "]" + " ");

            System.out.println();
            for(int i = 0; i < sizeND; i++)                    // outputs the non-dup pairs
                System.out.print("[" + pairNoDups[i][0] + "," + pairNoDups[i][1] + "]" + " ");

            System.out.print("\n\n");
        }
    }
    /**
     * This method returns a true value if the pair is unique, and a false
     * value if it is already in the array. Ex: If the first value in pairNonDup
     * is equal to the value at location i in the inputNum and the second at 
     * location j.
     * 
     * @param i location in inputNum array
     * @param j location in inputNum array
     * @return stated above
     */
    private static boolean checkUnique(int i, int j){
        
        for (int n = 0; n < sizeU; n++)
            if((pairUnique[n][0] == inputNum[i]) && (pairUnique[n][1] == inputNum[j]))
                return false;
        return true;
        
    }
    
    /**
     * This method returns a true value if the pair is not a duplicate, and a false
     * value if it is already in the array. Ex: If the first value in pairNonDup
     * is equal to the value at location i OR j in the inputNum.
     * 
     * @param i location in inputNum array
     * @param j location in inputNum array
     * @return stated above
     */
    private static boolean checkDup(int i, int j){
        
        for (int n = 0; n < sizeND; n++)
            if(((pairNoDups[n][0] == inputNum[i]) && (pairNoDups[n][1] == inputNum[j])) || ((pairNoDups[n][0] == inputNum[j]) && (pairNoDups[n][1] == inputNum[i])))
                return false;
        return true;
        
    }
}