import java.util.Scanner;

class SDES {
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
    String[] key = new String[10];
    char[] p10Val = new char[10];
    char[] IPVal = new char[8];
    char[] leftPartp10 =new char[5];
    char[] rightPartp10 =new char[5];
    String InitKey; // the initial Key given to us
    String plainTextStr;

    void KeyGeneration(long PlainText) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the PlainText: ");
        PlainText = sc.nextLong();
        sc.nextLine(); // Consume the newline character left by nextLong()
        System.out.println("Enter the Key: ");
        InitKey = sc.nextLine();
        char initkey[] = InitKey.toCharArray();
        plainTextStr = String.valueOf(PlainText);
        // Converting the string plaintext to a character array
        char[] plaintxt = plainTextStr.toCharArray();
        // Array for storing IP
        // Apply IP
        for (int i = 0; i < 8; i++) {
            IPVal[i] = plaintxt[IP[i] - 1];
        }

        // Print IP values
        System.out.println("This is IP:");
        for (char c : IPVal) {
            System.out.print(c + " ");
        }
        System.out.println();

        // Apply P10

        for (int j = 0; j < p10Val.length; j++) {
            p10Val[j] = initkey[p10[j] - 1];
        }
        // print P10
        System.out.println("This is P10:");
        for (char c : p10Val) {
            System.out.print(c);
        }
        // splitting the p10 in 2 parts
        for (int i = 0; i <leftPartp10.length ; i++) {
            leftPartp10[i]=p10Val[i];
        }
        System.out.println();


    }

}

public class Main {
    public static void main(String[] args) {
        long PlainText = 0;
        SDES obj = new SDES();
        obj.KeyGeneration(PlainText);
    }
}
