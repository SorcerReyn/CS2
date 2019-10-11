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
        // Stores Number portion of the coordinateString
        ArrayList<Integer> coordNum = new ArrayList<>();
        // Holds unconverted and unsorted number coordinates, used to link back to coordSplit
        ArrayList<String> coordNumOld = new ArrayList<>();

        for(int i = 0; i < coordinateStrings.size(); i++)
        {
            // Splits coordinateStrings into the letter and number portion and stores them
            coordSplit.add(coordinateStrings.get(i).split("(?<=\\D)(?=\\d)"));
            // Converts number coordinates into integer values
            coordNumOld.add(coordSplit.get(i)[1]);
            // Holds the unsorted, unconverted number coordinates
            coordNum.add(Integer.parseInt(coordSplit.get(i)[1]));
        }
        // Sorts coordLet based on integer value
        Collections.sort(coordNum);
        // Extra looping control variables
        int k = 1;
        // Loops through the sorted coordLet arrayList until the index before the last
        for(int j = 0; j < coordNum.size(); j++)
        {
            // If the both of the coordinates of the knights are within 2 spaces, then
            // they might threaten each other, so look closer
            int numDistance = 0;
            // Checks to ensure that the loop concludes when the last comparison can be made
            if((j + k) == (coordNum.size() - 1))
                return true;
            // Looking at the coordinates of the knights, they can only threaten each
            // other if the distance between them is 1 or 2
            numDistance = Math.abs(coordNum.get(j) - coordNum.get(j + k));
            if((numDistance <= 2) && (numDistance != 0))
            {
                // Takes the sorted numbers arrayList, turns it back into a string,
                // Searches the unsorted list of numbers, finds the index, plugs the
                // index back into the split arrayList, and converts the letters given
                int coordLet1 = convertLetters(coordSplit.get(coordNumOld.indexOf(Integer.toString(coordNum.get(j))))[0]);
                int coordLet2 = convertLetters(coordSplit.get(coordNumOld.indexOf(Integer.toString(coordNum.get(j + k))))[0]);
                int letDistance = Math.abs(coordLet1 - coordLet2);
                // Checks to see if the only possible threatened locations are true
                // and returns false if they are
                if((numDistance == 1) && (letDistance == 2))
                    return false;
                else if((numDistance == 2) && (letDistance == 1))
                    return false;
                // Increments k so that j can be compared to the value after the next
                k++;
                j--;
            }
            else if(numDistance == 0)
            {
                j--;
                k++;
            }
            // If the distance is greater than 2, then the current j can't be threatened
            // so increment it, reset the k, and set the previous j to null in the sorted
            // list, so that the indexOf function can be used even if the value is the same
            if(numDistance > 2)
            {
                coordNumOld.set(coordNumOld.indexOf(Integer.toString(coordNum.get(j))), null);
                k = 1;
            }
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
        return 3;
    }
}
