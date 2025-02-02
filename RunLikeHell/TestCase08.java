// Sean Szumlanski
// COP 3503, Fall 2019

// ============================
// RunLikeHell: TestCase08.java
// ============================
// A small test case for RunLikeHell.maxGain(). Keep in mind that these test
// cases are by no means comprehensive! You need to create some of your own.


import java.io.*;
import java.util.*;

public class TestCase08
{
	private static void failwhale()
	{
		System.out.println("fail whale :(");
		System.exit(0);
	}

	public static void main(String [] args)
	{
		int [] blocks = new int[] {3, 5, 7, 3, 11, 5, 9, 8};
		int ans = 30;

		if (RunLikeHell.maxGain(blocks) != ans) failwhale();

		System.out.println("Hooray!");
	}
}
