// Ryan Sheffield, ry257606
// COP 3503, fall 2019
import java.util.*;
import java.io.*;

public class SneakyKnights
{
    public static boolean allTheKnightsAreSafe(ArrayList<String> coordinateStrings, int boardSize)
    {
        // Stores each coordinateString as an array of strings after being split into 2 parts
        ArrayList<String[]> coordSplit = new ArrayList<>();
        // Stores the number portion of the coordinateString
        ArrayList<Integer> coordNum = new ArrayList<>();
        // Stores the letter portion of the coordinateString
        ArrayList<Integer> coordLet = new ArrayList<>();

        for(int i = 0; i < coordinateStrings.size(); i++)
        {
            // Splits coordinateStrings into the letter and number portion and stores them
            coordSplit.add(coordinateStrings.get(i).split("(?<=\\D)(?=\\d)"));
            coordFin[0].add(convertLetters(coordSplit.get(i)[0]));
            // Run alphabetical part through a base 26 to base 10 conversion method
            coordLet.add(convertLetters(coordSplit.get(i)[0]));
            // Typecast number part to integer
            coordNum.add(Integer.parseInt(coordSplit.get(i)[1]));
        }
        return true;
    }

    // Used same conversion method as in SneakyQueens
    public static int convertLetters(String letterCoordinates)
        {
            // Create int value for the letter conversion
            int value = 0;
            // Create power variable for Math.pow
            double power = 0;
            // Itterate through string
            for(int i = 0; i < letterCoordinates.length(); i++)
            {
                // Set power variable to the length of the string minus the current iteration + 1
                power = Math.pow(26, (letterCoordinates.length() - (i + 1)));
                // Convert the chars to their int value in ASCII, then subtract the value of 'a' - /// 1 to find converted value, multiply by the power for conversion to decimal, and /// sum each iteration
                value += ((int) letterCoordinates.charAt(i) - 96) * power;
            }
            return value;
        }

    public static double difficultyRating()
    {
        return 1;
    }

    public static double hoursSpent()
    {
        return 1;
    }
}
