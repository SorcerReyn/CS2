// Ryan Sheffield, ry257606
// COP 3503, Fall 2019

import java.io.*;
import java.util.*;

public class RunLikeHell
{
    public static int maxGainRec(int [] blocks, int n)
    {
        // Base case: there is nothing in the blocks array, or the last blocks are being checked
        // If none of those, then increment the count
        if (blocks.length == 0)
            return 0;
        else if (n == blocks.length)
            return blocks[n];
        else if (n == blocks.length + 1)
            return blocks[n + 1]
        else
            n++;

        if (blocks[n + 2] > blocks [n + 3])
            return blocks[n++] + maxGainRec(blocks[n + 1])
    }

    public static int maxGain(int [] blocks)
    {
        int max = 0;

    }

    public static double difficultyRating()
    {
        return 1;
    }

    public static double hoursSpent()
    {
        return 0;
    }
}
