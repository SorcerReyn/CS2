// Ryan Sheffield
// COP 3503, Fall 2019

import java.io.*;
import java.util.*;

// Node modified so that whatever type is given extends Comparable
class Node<AnyType extends Comparable<AnyType>>
{
	// Sets up the data to be stored in the node
	// The data itself can be of any type
	AnyType data;
	Node<AnyType> left, right;

	Node(AnyType data)
	{
		this.data = data;
	}
}
// GenericBST is made to hold AnyType as well, and makes it extend Comparable
public class GenericBST<AnyType extends Comparable<AnyType>>
{
	// The root node is made private so that it can't be deleted
	private Node<AnyType> root;

	public void insert(AnyType data)
	{
		root = insert(root, data);
	}
	// The insertion function takes in the root and the data to be inserted
	// then checks if the root given is null. If it is, then the appropriate
	// spot has been found, if not, it traverses the tree until the given root
	// is null
	private Node<AnyType> insert(Node<AnyType> root, AnyType data)
	{
		// Check if the tree is empty, and if so, return a new node
		// Use the now compatable compareTo function to check if the data in
		// the node should be to the right or left of the root.
		// If compareTo returns -1, then the data in the child is smalle than
		// the root, and is inserted left. If it returns 1, then it's larger,
		// and is inserted right.
		if (root == null)
		{
			return new Node<AnyType>(data);
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = insert(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = insert(root.right, data);
		}

		return root;
	}
	// Calls the Node delete function by passing the value of the data to
	// search for.
	public void delete(AnyType data)
	{
		root = delete(root, data);
	}
	// Deletes a node with a specific data value by recursively searching for
	// the node with the value.
	private Node<AnyType> delete(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return null;
		}
		else if (data.compareTo(root.data) < 0)
		{
			root.left = delete(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			root.right = delete(root.right, data);
		}
		else
		{
			// Checks if the current node has any children, if so, then the
			// desired value doesn't exist. If one only one of them exists, it
			// returns that node.
			if (root.left == null && root.right == null)
			{
				return null;
			}
			else if (root.left == null)
			{
				return root.right;
			}
			else if (root.right == null)
			{
				return root.left;
			}
			else
			{
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is not empty.
	private AnyType findMax(Node<AnyType> root)
	{
		while (root.right != null)
		{
			root = root.right;
		}

		return root.data;
	}
	// Passes a value to the recursive contains function
	public boolean contains(AnyType data)
	{
		return contains(root, data);
	}
	// Traverses the tree, checking if the value is contained in the tree.
	private boolean contains(Node<AnyType> root, AnyType data)
	{
		if (root == null)
		{
			return false;
		}
		else if (data.compareTo(root.data) < 0)
		{
			return contains(root.left, data);
		}
		else if (data.compareTo(root.data) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}
	// Prints out the text "in-order traversal".
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}
	// Prints out the actual in-order traversal of the tree.
	private void inorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}
	// Prints out the text "pre-order traversal".
	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}
	// Prints out the actual pre-order traversal of the tree.
	private void preorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}
	// Prints out the text "post-order traversal".
	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}
	// Prints out the actual post-order traversal of the tree.
	private void postorder(Node<AnyType> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static void main(String [] args)
	{
		// Creates new GenericBST of Integers
		GenericBST<Integer> myTree = new GenericBST<>();
		// Populates with random ints from 1 to 100
		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
		}
		// Calls all of the traversals
		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}

	public static double difficultyRating()
	{
		return 2;
	}

	public static double hoursSpent()
	{
		return 2;
	}
}
