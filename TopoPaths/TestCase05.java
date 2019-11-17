// Sean Szumlanski
// COP 3503, Fall 2019

// ==========================
// TopoPaths: TestCase05.java
// ==========================
// This corresponds to graph G3 from the assignment PDF.


import java.io.*;

public class TestCase05
{
	public static void main(String [] args) throws IOException
	{
		System.out.print("Test Case #5: ");

		boolean success = (TopoPaths.countTopoPaths("input_files/g3.txt") == 1);
		System.out.println(success ? "PASS" : "fail whale :(");
	}
}
