// import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.StdRandom;

// import java.io.BufferedReader;
// import java.util.Scanner;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
public class App {
    public static void main(String[] args) {
        int index = 1;

        String word = "", chooseString = "";

        while (!StdIn.isEmpty())
        {
            word = StdIn.readString();
            if (StdRandom.bernoulli(1.0/index))
            {
                chooseString = word;

            }
            index++;
        }
        
        System.out.println(chooseString);
    }
} 
 