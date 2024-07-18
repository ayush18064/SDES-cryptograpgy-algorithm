import com.sun.security.jgss.GSSUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class SDES_ALGORITHM {
    int[] p10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    int[] p8 = {6, 3, 7, 4, 8, 5, 10, 9};
    int[] p4 = {2, 4, 3, 1};
    int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    int[] IPinv = {4, 1, 3, 5, 7, 2, 8, 6};
    int[][] S0 = {
            {1, 0, 3, 2},
            {3, 2, 1, 0},
            {0, 2, 1, 3},
            {3, 1, 3, 2}
    };
    int[][] S1 = {
            {0, 1, 2, 3},
            {2, 0, 1, 3},
            {3, 0, 1, 0},
            {2, 1, 0, 3}
    };
    int[] ep = {4, 1, 2, 3, 2, 3, 4, 1};

    boolean[] ApplyPermutations(int[] arr, String txt, int n) {

        boolean[] result = new boolean[n];
        boolean[] plain = convert(txt); // converting to a boolean array
        for (int i = 0; i < result.length; i++) {

            result[i] = plain[arr[i] - 1];

        }
        System.out.println();

        return result;
    }

    boolean[] substitutionOperation(int left[], int right[]) {


        int sOrow1 = left[0];
        int sOrow2 = left[3];

        int sOcol1 = left[1];
        int sOcol2 = left[2];

        int s1row1 = right[0];
        int s1row2 = right[3];

        int s1col1 = right[1];
        int s1col2 = right[2];

        int sOFinalRow[] = new int[2];
        sOFinalRow[0] = sOrow1;
        sOFinalRow[1] = sOrow2;

        int sOFinalCol[] = new int[2];
        sOFinalCol[0] = sOcol1;
        sOFinalCol[1] = sOcol2;

        int s1FinalRow[] = new int[2];
        s1FinalRow[0] = s1row1;
        s1FinalRow[1] = s1row2;

        int s1FinalCol[] = new int[2];
        s1FinalCol[0] = s1col1;
        s1FinalCol[1] = s1col2;


        int rowDecimal_s0 = convertBinaryArrayToDecimal(sOFinalRow);
        int colDecimal_s0 = convertBinaryArrayToDecimal(sOFinalCol);

        int rowDecimal_s1 = convertBinaryArrayToDecimal(s1FinalRow);
        int colDecimal_s1 = convertBinaryArrayToDecimal(s1FinalCol);

        int s0Value = S0[rowDecimal_s0][colDecimal_s0];
        int s1Value = S1[rowDecimal_s1][colDecimal_s1];
//----------------converting the decimal value to binary array for eg. 3 => [ 1 , 1 ]--------------------
        int[] BinaryS0value = convertDecimalToBinaryArray(s0Value); // [1,1]
        int[] BinaryS1value = convertDecimalToBinaryArray(s1Value);// [1,1]

        int[] s0_s1_combined_val = new int[4];

//        ----------------------------------combining the value of s0 and s1-----------------------------------------
        System.arraycopy(BinaryS0value, 0, s0_s1_combined_val, 0, 2);
        System.arraycopy(BinaryS1value, 0, s0_s1_combined_val, 2, 2);


        System.out.println("\n");
        for (int val : s0_s1_combined_val) {
            System.out.print(val + " ");
        }
        boolean resultArr[] = convertIntArrayToBoolArray(s0_s1_combined_val);


        return resultArr;
    }

    boolean[] SwapOperation(boolean[] arr) {
        boolean temp[] = new boolean[8];

        boolean[] leftHalf = new boolean[4];
        boolean[] rightHalf = new boolean[4];
//--------------------splitting the array for swap operation----------------------------
        System.arraycopy(arr, 0, leftHalf, 0, 4);
        System.arraycopy(arr, 4, rightHalf, 0, 4);
//        -------------------------------------------------------------
        // now performing the swap operation left half becomes the right half and vice versa
        System.arraycopy(rightHalf, 0, temp, 0, 4);
        System.arraycopy(leftHalf, 0, temp, 4, 4);

//        System.arraycopy(xorP4WithLeftHalfIp, 0, CombinationOfRightHalfiP_Xor_result, 0, rightPartIpPlainText.length);
//        System.arraycopy(rightPartIpPlainText, 0, CombinationOfRightHalfiP_Xor_result, rightPartIpPlainText.length, xorLeftPart.length);
        System.out.println("Swap operation");
        for (boolean val : temp) {
            System.out.print(val + " ");
        }

        return temp;
    }


    boolean[] convertIntArrayToBoolArray(int[] intArray) {
        boolean[] boolArray = new boolean[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            boolArray[i] = intArray[i] != 0;
        }

        return boolArray;
    }

    boolean[] convert(String str) {
        boolean[] booleanArray = new boolean[str.length()];
        for (int i = 0; i < str.length(); i++) {
            booleanArray[i] = str.charAt(i) == '1';
        }
        return booleanArray;
    }

    int[] convertDecimalToBinaryArray(int decimalNumber) {
        if (decimalNumber == 0) {
            return new int[]{0};
        }

        // Initialize an array to hold the binary digits
        int[] binaryArray = new int[32]; // Maximum of 32 bits for an integer
        int index = 0;

        // Perform the division and store remainders
        while (decimalNumber > 0) {
            binaryArray[index++] = decimalNumber % 2;
            decimalNumber = decimalNumber / 2;
        }

        // Reverse the array to get the correct order
        int[] result = new int[index];
        for (int i = 0; i < index; i++) {
            result[i] = binaryArray[index - 1 - i];
        }

        return result;
    }

    int convertBinaryArrayToDecimal(int[] binaryArray) {
        int decimalValue = 0;

        // Iterate over the array from the end to the start
        for (int i = 0; i < binaryArray.length; i++) {
            int bit = binaryArray[binaryArray.length - 1 - i];
            if (bit != 0 && bit != 1) {
                throw new IllegalArgumentException("Array can only contain binary digits (0 or 1)");
            }
            decimalValue += bit * Math.pow(2, i);
        }

        return decimalValue;
    }

    String booleanArrayToString(boolean[] array) {
        StringBuilder sb = new StringBuilder(array.length);

        for (boolean b : array) {
            sb.append(b ? '1' : '0');
        }

        return sb.toString();
    }

    boolean[] leftShiftOnce(boolean[] array) {
        int length = array.length;
        boolean[] shiftedArray = new boolean[length];

        // Perform the shift by one position
        for (int i = 0; i < length - 1; i++) {
            shiftedArray[i] = array[i + 1];
        }

        // Set the last element of the shifted array to 0
        if (length > 0) {
            shiftedArray[length - 1] = array[0];
        }

        return shiftedArray;
    }

    int[] convertStringToIntArray(String str) {
        int[] intArray = new int[str.length()];

        // Convert each character to an integer and store it in the array
        for (int i = 0; i < str.length(); i++) {
            char charAtPosition = str.charAt(i);
            if (charAtPosition == '0' || charAtPosition == '1') {
                intArray[i] = Character.getNumericValue(charAtPosition);
            } else {
                System.out.println("Invalid character in input string: " + charAtPosition);
                return null; // Return null to indicate an error
            }
        }

        return intArray;
    }


    void keyGeneration() {
        String PlainText;
        String Key;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the plain text:");
        PlainText = sc.nextLine();
        boolean[] booleanArray = convert(PlainText);  // [true, false , false , false , true , true, true , true ]
        System.out.println("Enter the Key:");
        Key = sc.nextLine();
        boolean[] boolKey = convert(Key);

//        System.out.println("This is the plaintext");

//        System.out.println("This is the key");

        int IpVal[] = new int[8];
        // Apply IP On the plainText
        boolean[] PlaintextIp = ApplyPermutations(IP, PlainText, 8);

        // apply p10 permutation
        boolean[] p10FinalVal = ApplyPermutations(p10, Key, 10);

        // dividing the p10 in 2 parts
        boolean[] leftPart = new boolean[p10FinalVal.length / 2];
        boolean[] rightPart = new boolean[p10FinalVal.length / 2];

//        ----------------------------------------------------------
        boolean[] leftPart2 = new boolean[5];
        boolean[] rightPart2 = new boolean[5];
//        --------------------------------------------------------------
        int index = 0;

//         ----------------dividing the array in 2 parts-----------------------
        System.arraycopy(p10FinalVal, 0, leftPart, 0, 5);
        System.arraycopy(p10FinalVal, 5, rightPart, 0, 5);
//        -------------------------------------------------------------------


//-------------------Applying Left Shift Operation---------------------
        boolean[] shiftedLeftSide = leftShiftOnce(leftPart);
        boolean[] shiftedRightSide = leftShiftOnce(rightPart);

//    -----------------------------------------------------------------


        int length1 = shiftedLeftSide.length;
        int length2 = shiftedRightSide.length;

        boolean[] LSCombined = new boolean[length1 + length2];
// ---------------------Combining the LS-1---------------------------------

        System.arraycopy(shiftedLeftSide, 0, LSCombined, 0, length1);
        System.arraycopy(shiftedRightSide, 0, LSCombined, length1, length2);
        String LScombinedString = booleanArrayToString(LSCombined);


//   -------------------------P8--------------------------------
        boolean[] LS_1_Key1 = ApplyPermutations(p8, LScombinedString, 8);

//-----------------------------------------------------------------------------


        //-----------------------Divide the key 1 in 2 parts and then perform 2 bits left shift ------------------------------
        System.arraycopy(LSCombined, 0, leftPart2, 0, 5);
        System.arraycopy(LSCombined, 5, rightPart2, 0, 5);
//        ----------------------------------------performing the 2 bits left shift--------------------------------------------------------
        boolean[] leftShift2bits = leftShiftOnce(leftPart2);
        boolean[] leftShift2bitsSecondTime = leftShiftOnce(leftShift2bits);

        boolean[] rightShift2bits = leftShiftOnce(rightPart2);
        boolean[] rightShift2bitsSecondTime = leftShiftOnce(rightShift2bits);
//        -----------------------------------------------------------------------------------------------------------
        boolean[] LS_2 = new boolean[10];
        System.arraycopy(leftShift2bitsSecondTime, 0, LS_2, 0, 5);
        System.arraycopy(rightShift2bitsSecondTime, 0, LS_2, 5, 5);
//        --------------------------------------------------------------------------------------------


        // converting the LS-2 to string to perform permutation
        String LS_2_p8 = booleanArrayToString(LS_2);
        boolean[] LS_2_p8_Key_2 = ApplyPermutations(p8, LS_2_p8, 8);
        System.out.println("This is the key 2");

        String KEY1 = booleanArrayToString(LS_2_p8_Key_2);
        System.out.println("\nthis is Key 1");

        String KEY2 = booleanArrayToString(LS_1_Key1);
        System.out.println("\n");

//       --------------------------------------------------------------------------------------------
        System.out.println(KEY1);
        System.out.println(KEY2);
        System.out.println("This is the IP with Plain text");

//        -------------------------------------Divide the Ip in 2 parts of 4 bits each---------------------------------------------------------

        boolean[] leftPartIpPlainText = new boolean[4];
        boolean[] rightPartIpPlainText = new boolean[4];
        System.arraycopy(PlaintextIp, 0, leftPartIpPlainText, 0, 4);
        System.arraycopy(PlaintextIp, 4, rightPartIpPlainText, 0, 4);
        System.out.println("left half");

//      ----------------------------------------Apply the Ep on the right part of the IP----------------------------------------------------------------------------------
        String EpOnRightHalf = booleanArrayToString(rightPartIpPlainText);
        boolean EpResult[] = ApplyPermutations(ep, EpOnRightHalf, 8);
//        ------------------------------------------------------------------------------------------------------------------------------------

// perform XOR operation on KEY1 and EP
        boolean[] XorResult = new boolean[8];
        for (int i = 0; i < 8; i++) {
            XorResult[i] = EpResult[i] ^ LS_1_Key1[i];
        }
//        -----------------------------------------XOR Result Printing---------------------------------
        System.out.println("\nXOR RESULT:\n");

//        -----------------------------------------dividing the Xor output in 2 parts ---------------------------------
        boolean[] XORLeftPart = new boolean[4];
        boolean[] XORRightPart = new boolean[4];
        System.arraycopy(XorResult, 0, XORLeftPart, 0, 4);
        System.arraycopy(XorResult, 4, XORRightPart, 0, 4);

//-----------------------now performing the S row operation on the separated parts--------------------------------------
        String XorLeftPart = booleanArrayToString(XORLeftPart);
        String XorRightPart = booleanArrayToString(XORRightPart);
        int[] xorLeftPart = convertStringToIntArray(XorLeftPart);
        int[] xorRightPart = convertStringToIntArray(XorRightPart);


        boolean[] substitution = substitutionOperation(xorLeftPart, xorRightPart);
        String substitutionRes = booleanArrayToString(substitution);
        boolean[] p4onsunstituionResult = ApplyPermutations(p4, substitutionRes, 4);
        System.out.println("applting p4 on s0 s1");

        boolean[] xorP4WithLeftHalfIp = new boolean[4];
//        ---------------------------Perform XOR with left part of the ip and the combination of s0 s1---------------------------
        for (int i = 0; i < 4; i++) {
            xorP4WithLeftHalfIp[i] = leftPartIpPlainText[i] ^ p4onsunstituionResult[i];
        }

//        ----------------------We combine both halves i.e. right half of initial permutation and output of ip.------------------------
        boolean CombinationOfRightHalfiP_Xor_result[] = new boolean[8];


        System.arraycopy(xorP4WithLeftHalfIp, 0, CombinationOfRightHalfiP_Xor_result, 0, rightPartIpPlainText.length);
        System.arraycopy(rightPartIpPlainText, 0, CombinationOfRightHalfiP_Xor_result, rightPartIpPlainText.length, xorLeftPart.length);


        System.out.println("right half of initial permutation and output of ip\n");

//        ---------------------performing swap operation---------------------------------------
        boolean DividingAndSwapping[] = SwapOperation(CombinationOfRightHalfiP_Xor_result); // till this using key 1 is finished


//        -----------------now doing the same thing with key 2----------------------------------


// Second Round
        boolean[] leftPartIpPlainTextSecond = new boolean[4];
        boolean[] rightPartIpPlainTextSecond = new boolean[4];
        System.arraycopy(DividingAndSwapping, 0, leftPartIpPlainTextSecond, 0, 4);
        System.arraycopy(DividingAndSwapping, 4, rightPartIpPlainTextSecond, 0, 4);

// Apply the EP on the right part of the swapped halves
        String EpOnRightHalfWithKey2 = booleanArrayToString(rightPartIpPlainTextSecond);
        boolean EpResultWithKey2[] = ApplyPermutations(ep, EpOnRightHalfWithKey2, 8);

// Perform XOR operation on KEY2 and EP result
        boolean[] XorResultWithKey2 = new boolean[8];
        for (int i = 0; i < 8; i++) {
            XorResultWithKey2[i] = EpResultWithKey2[i] ^ LS_2_p8_Key_2[i];
        }

// Divide the XOR result into 2 parts
        boolean[] XORLeftPartWithKey2 = new boolean[4];
        boolean[] XORRightPartWithKey2 = new boolean[4];
        System.arraycopy(XorResultWithKey2, 0, XORLeftPartWithKey2, 0, 4);
        System.arraycopy(XorResultWithKey2, 4, XORRightPartWithKey2, 0, 4);

// Perform substitution operation
        String XorLeftPartKey2 = booleanArrayToString(XORLeftPartWithKey2);
        String XorRightPartKey2 = booleanArrayToString(XORRightPartWithKey2);
        int[] xorLeftPartKey2 = convertStringToIntArray(XorLeftPartKey2);
        int[] xorRightPartKey2 = convertStringToIntArray(XorRightPartKey2);
        boolean[] substitutionKey2 = substitutionOperation(xorLeftPartKey2, xorRightPartKey2);

// Apply P4 permutation on substitution result
        String substitutionResKey2 = booleanArrayToString(substitutionKey2);
        boolean[] p4onsunstituionResultKey2 = ApplyPermutations(p4, substitutionResKey2, 4);

// Perform XOR with the left part of the IP after swap and the P4 result
        boolean[] xorP4WithLeftHalfIpKey2 = new boolean[4];
        for (int i = 0; i < 4; i++) {
            xorP4WithLeftHalfIpKey2[i] = leftPartIpPlainTextSecond[i] ^ p4onsunstituionResultKey2[i];
        }

// Combine both halves: XOR result and right part of the swapped IP
        boolean CombinationOfRightHalfiP_Xor_resultKey2[] = new boolean[8];
        System.arraycopy(xorP4WithLeftHalfIpKey2, 0, CombinationOfRightHalfiP_Xor_resultKey2, 0, rightPartIpPlainTextSecond.length);
        System.arraycopy(rightPartIpPlainTextSecond, 0, CombinationOfRightHalfiP_Xor_resultKey2, rightPartIpPlainTextSecond.length, xorLeftPartKey2.length);

// Apply the final inverse permutation (IPinv) on the combined result
        String FinalCombinationString = booleanArrayToString(CombinationOfRightHalfiP_Xor_resultKey2);
        boolean[] FinalPermutationResult = ApplyPermutations(IPinv, FinalCombinationString, 8);

        System.out.println("Final encrypted result:");
        for (boolean val : FinalPermutationResult) {
            System.out.print(val ? '1' : '0');
        }


    }

}

public class Main_func {
    public static void main(String[] args) {
        SDES_ALGORITHM obj = new SDES_ALGORITHM();
        obj.keyGeneration();
    }
}
