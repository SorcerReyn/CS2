// Ryan Sheffield, ry257606
// COP 3503, fall 2019

import java.util.*;
import java.io.*;

// Makes sure that the data in the node can be of any type, and is comparable
class Node<AnyType extends Comparable<AnyType>>
{
    // Height of node, or number of references
    int height;
    // Declares data field as AnyType
    AnyType data;
    // Reference to the next node of the same height or lower
    Node<AnyType>[] next;

    // Initializes a node to Null, creates head
    Node(int height)
    {
        this.data = null;
        this.next = null;
    }

    // Initializes a node with values
    Node(AnyType data, int height)
    {
        this.data = data;
    }

    public AnyType value()
    {
        return this.data;
    }

    public int height()
    {
        return this.height;
    }

    public Node<AnyType> next(int level)
    {
        return this.next[1];
    }
}

public class SkipList<AnyType extends Comparable<AnyType>>
{
    // Number of nodes in SkipList, excluding the head
    int size;
    Node<AnyType> head;

    SkipList()
    {

    }

    SkipList(int Height)
    {

    }

    public int size()
    {
        return this.size;
    }

    public Node<AnyType> head()
    {
        return this.head;
    }

    public void insert(AnyType data)
    {

    }

    public void insert(AnyType data, int height)
    {

    }

    public void delete(AnyType data)
    {

    }

    public boolean contains(AnyType data)
    {
        return false;
    }

    public Node<AnyType> get(AnyType data)
    {
        return this.head;
    }

    public static double difficultyRating()
    {
        return 3;
    }

    public static double hoursSpent()
    {
        return 1;
    }
}
