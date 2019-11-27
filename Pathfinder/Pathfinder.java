// Ryan Sheffield, ry257606
// COP 3503, Fall 2019

import java.io.*;
import java.util.*;
import java.util.Stack;

public class Pathfinder
{
	// Used to toggle "animated" output on and off (for debugging purposes).
	private static boolean animationEnabled = false;

	// "Animation" rate (frames per second).
	private static double frameRate = 10.0;

	// Setters. Note that for testing purposes you can call enableAnimation()
	// from your backtracking method's wrapper method if you want to override
	// the fact that the test cases are disabling animation. Just don't forget
	// to remove that method call before submitting!
	public static void enableAnimation() { Pathfinder.animationEnabled = true; }
	public static void disableAnimation() { Pathfinder.animationEnabled = false; }
	public static void setFrameRate(double fps) { Pathfinder.frameRate = frameRate; }

	// Maze constants.
	private static final char WALL       = '#';
	private static final char PERSON     = '@';
	private static final char EXIT       = 'e';
	private static final char BREADCRUMB = '.';  // visited
	private static final char SPACE      = ' ';  // unvisited

	// Takes a 2D char maze, finds the starting position, puts it in a new 2D char
	// array, and sends that into another function
	public static HashSet<String> findPaths(char [][] maze)
	{
		// The HashSet that will be returned by our program
		HashSet<String> pathSet = new HashSet<>();
		// The stack that will house our paths
		Stack<Character> pathStack = new Stack<>();

		int height = maze.length;
		int width = maze[0].length;

		// The visited array keeps track of visited positions. It also keeps
		// track of the exit, since the exit will be overwritten when the '@'
		// symbol covers it up in the maze[][] variable. Each cell contains one
		// of three values:
		//
		//   '.' -- visited
		//   ' ' -- unvisited
		//   'e' -- exit
		char [][] visited = new char[height][width];
		for (int i = 0; i < height; i++)
			Arrays.fill(visited[i], SPACE);

		// Find starting position (location of the '@' character).
		int startRow = -1;
		int startCol = -1;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				if (maze[i][j] == PERSON)
				{
					startRow = i;
					startCol = j;
				}
			}
		}

		// Let's goooooooo!!
		return findPaths(maze, visited, startRow, startCol, height, width, pathStack, pathSet);
	}

	// Takes in the 2D char maze, the 2D visited array; which keeps track of where
	// we've been, the maze dimensions and the starting position, and a stack with
	// our path thus far, and the HashSet of strings containing all the paths, and
	// returns all of the possible combinations of moves to exit in the HashSet
	private static HashSet<String> findPaths(char [][] maze, char [][] visited,
	                                 int currentRow, int currentCol,
	                                 int height, int width, Stack<Character> pathStack,
									 HashSet<String> pathSet)
	{
		// This conditional block prints the maze when a new move is made.
		if (Pathfinder.animationEnabled)
		{
			printAndWait(maze, height, width, "Searching...", Pathfinder.frameRate);
		}

		// Hooray!
		if (visited[currentRow][currentCol] == 'e')
		{
			if (Pathfinder.animationEnabled)
			{
				for (int i = 0; i < 3; i++)
				{
					maze[currentRow][currentCol] = '*';
					printAndWait(maze, height, width, "Hooray!", Pathfinder.frameRate);

					maze[currentRow][currentCol] = PERSON;
					printAndWait(maze, height, width, "Hooray!", Pathfinder.frameRate);
				}
			}
			// Convert the characters in the stack into a string, getting rid of
			// the brackets and commas
			String pathString = pathStack.toString().replace("[", "").replace("]", "")
				.replace(",", "");
			// Add the path that got us here to the HashSet
			pathSet.add(pathString);
			return pathSet;
		}

		// Moves: left, right, up, down
		int [][] moves = new int[][] {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

		// Runs through every possible move and performs it, then leaves a breadcrumb
		// in our visited array, and a direction in our path array
		// When finding new branches for our paths, we can rely on this for loop
		// to find us valid unique paths, especially since we remove the old
		// breadcrumbs after finding a valid path
		for (int i = 0; i < moves.length; i++)
		{
			int newRow = currentRow + moves[i][0];
			int newCol = currentCol + moves[i][1];

			// Add movement to the pathStack depending on the iteration of 'i'
			switch (i) {
			case 0:
				pathStack.push('l');
				break;
			case 1:
				pathStack.push('r');
				break;
			case 2:
				pathStack.push('u');
				break;
			case 3:
				pathStack.push('d');
				break;
			}

			// Check move is in bounds, not a wall, and not marked as visited.
			// If move is out of bounds, pop the action off of the stack and continue
			if (!isLegalMove(maze, visited, newRow, newCol, height, width))
			{
				pathStack.pop();
				continue;
			}

			// Change state. Before moving the person forward in the maze, we
			// need to check whether we're overwriting the exit. If so, save the
			// exit in the visited[][] array so we can actually detect that
			// we've gotten there.

			if (maze[newRow][newCol] == EXIT)
				visited[newRow][newCol] = EXIT;

			maze[currentRow][currentCol] = BREADCRUMB;
			visited[currentRow][currentCol] = BREADCRUMB;
			maze[newRow][newCol] = PERSON;

			// Perform recursive descent.
			pathSet.equals(findPaths(maze, visited, newRow, newCol, height, width,
				pathStack, pathSet));

			// Remove the action from the stack, showing that the action is no
			// longer being considered for the new path
			pathStack.pop();

			// Undo state changes. If the exit was found, keep it as the exit,
			// and if we're starting new branches on our paths, change the old
			// breadcrumbs back into spaces
			if(visited[newRow][newCol] == EXIT)
				maze[newRow][newCol] = EXIT;
			else
				maze[newRow][newCol] = SPACE;
			maze[currentRow][currentCol] = PERSON;
			visited[currentRow][currentCol] = SPACE;

			// This conditional block prints the maze when a move gets undone
			// (which is effectively another kind of move).
			if (Pathfinder.animationEnabled)
			{
				printAndWait(maze, height, width, "Backtracking...", frameRate);
			}

		}
		return pathSet;
	}

	public static double difficultyRating()
	{
		return 2;
	}

	public static double hoursSpent()
	{
		return 4;
	}

	// Returns true if moving to row and col is legal (i.e., we have not visited
	// that position before, and it's not a wall).
	private static boolean isLegalMove(char [][] maze, char [][] visited,
	                                   int row, int col, int height, int width)
	{
		// Base cases for preventing evaluation past what is legal
		if (row == -1 || col == -1)
			return false;
		else if (row == height || col == width)
			return false;
		else if (maze[row][col] == WALL || visited[row][col] == BREADCRUMB)
			return false;

		return true;
	}

	// =========================================================================
	// EVERYTHING PAST THIS POINT IS FOR TESTING SOLO
	// =========================================================================

	// This effectively pauses the program for waitTimeInSeconds seconds.
	private static void wait(double waitTimeInSeconds)
	{
		long startTime = System.nanoTime();
		long endTime = startTime + (long)(waitTimeInSeconds * 1e9);

		while (System.nanoTime() < endTime)
			;
	}

	// Prints maze and waits. frameRate is given in frames per second.
	private static void printAndWait(char [][] maze, int height, int width,
	                                 String header, double frameRate)
	{
		if (header != null && !header.equals(""))
			System.out.println(header);

		if (height < 1 || width < 1)
			return;

		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				System.out.print(maze[i][j]);
			}

			System.out.println();
		}

		System.out.println();
		wait(1.0 / frameRate);
	}

	// Read maze from file. This function dangerously assumes the input file
	// exists and is well formatted according to the specification above.
	private static char [][] readMaze(String filename) throws IOException
	{
		Scanner in = new Scanner(new File(filename));

		int height = in.nextInt();
		int width = in.nextInt();

		char [][] maze = new char[height][];

		// After reading the integers, there's still a new line character we
		// need to do away with before we can continue.

		in.nextLine();

		for (int i = 0; i < height; i++)
		{
			// Explode out each line from the input file into a char array.
			maze[i] = in.nextLine().toCharArray();
		}

		return maze;
	}

	public static void main(String [] args) throws IOException
	{
		// Load maze and turn on "animation."
		char [][] maze = readMaze("maze.txt");
		Pathfinder.enableAnimation();

		// Go!!
		System.out.println(Pathfinder.findPaths(maze));
	}
}
