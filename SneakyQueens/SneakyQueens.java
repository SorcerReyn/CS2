// Ryan Sheffield
// COP 3503, FALL 2019

import java.io.*;
import java.util.*;
import java.util.Arrays;

public class SneakyQueens
{
        public static boolean allTheQueensAreSafe(ArrayList<String> coordinateStrings, int boardSize)
        {
                // Declare an ArrayList to store numeric coordinate values, one for letter values, and one for converted letter values
                ArrayList<Integer> coordinateNumbers = new ArrayList<>(coordinateStrings.size());
                ArrayList<Integer> coordinateLettersConverted = new ArrayList<>(coordinateStrings.size());
                ArrayList<String[]> coordinateLetters = new ArrayList<>(coordinateStrings.size());
                // Set the number coordinates and the converted letter coordinates into hashmaps, and compares them to the sizes of the arrayLists. If the sizes differ, then there were duplicate rows or columns
                Set<Integer> numbersSet = new HashSet<>();
                Set<Integer> lettersSet = new HashSet<>();
                // Take first index, split coordinateStrings into 2 parts: alphabetical and numeric
                // Use the split() method with predefined character classes
                for(int i = 0; i < coordinateStrings.size(); i++)
                {
                        coordinateLetters.add(coordinateStrings.get(i).split("(?<=\\D)(?=\\d)"));
                        // Assign an integer value to the numerical part
                        coordinateNumbers.add(Integer.parseInt(coordinateLetters.get(i)[1]));
                        // Run alphabetical part through a base 26 to base 10 conversion method
                        coordinateLettersConverted.add(convertLetters(coordinateLetters.get(i)[0]));
                }
                // Adds all of the coordinates to a Hash Set to make a simple check for duplicates
                numbersSet.addAll(coordinateNumbers);
                lettersSet.addAll(coordinateLettersConverted);
                if((coordinateNumbers.size() != numbersSet.size()) || (coordinateLettersConverted.size() != lettersSet.size()))
                {
                        return false;
                }
                // Converts the set to an array, so that each value can be added and subtracted
                // If the sum or difference of each coordinate is equal, then the queen is threatened
                Integer[] numbersArray = coordinateNumbers.toArray(new Integer[coordinateNumbers.size()]);
                Integer[] lettersArray = coordinateLettersConverted.toArray(new Integer[coordinateLettersConverted.size()]);
                Integer[] sum = new Integer[numbersArray.length];
                Integer[] diff = new Integer[lettersArray.length];
                // Create just one more set for sum and diff, and check if the size == the coordinate lists
                Set<Integer> sumSet = new HashSet<>();
                Set<Integer> diffSet = new HashSet<>();
                for(int j = 0; j < coordinateStrings.size(); j++)
                {
                        sum[j] = numbersArray[j] + lettersArray[j];
                        diff[j] = numbersArray[j] - lettersArray[j];
                        sumSet.add(sum[j]);
                        diffSet.add(diff[j]);
                }
                // Final check, if the sum or diff is different, there was a repeated element, meaning that the queen was threatened by some coordinate
                if((sumSet.size() != sum.length) || (diffSet.size() != diff.length))
                {
                        return false;
                }
                return true;
        }

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
                        // Convert the chars to their int value in ASCII, then subtract the value of 'a' - 1 to find converted value, multiply by the power for conversion to decimal, and sum each iteration
                        value += ((int) letterCoordinates.charAt(i) - 96) * power;
                }

                return value;
        }

        public static double difficultyRating()
        {
                return 3;
        }

        public static double hoursSpent()
        {
                return 8;
        }
}
