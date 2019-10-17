// Ryan Sheffield, ry257606
// COP 3503, fall 2019

import java.util.*;
import java.io.*;
import java.util.Random;
import java.lang.Math;

// Makes sure that the data in the node can be of any type, and is comparable
class Node<AnyType extends Comparable<AnyType>>
{
    // Height of node, or number of references
    int height;
    // Declares data field as AnyType
    AnyType data;
    // Reference to the next node of the same height or lower
    ArrayList<Node<AnyType>> next;

    // Initializes a node with height
    Node(int height)
    {
        this.height = height;
        this.next.add((height - 1), null);
    }

    // Initializes a node with data and height
    Node(AnyType data, int height)
    {
        this.data = data;
        this.height = height;
        for(int i = 0; i < height; i++)
            this.next.add(i, null);
    }

    // Returns the data in the node
    public AnyType value()
    {
        return this.data;
    }

    // Returns the height of the node, or number of references it has
    public int height()
    {
        return this.height;
    }

    // Returns the reference at a given level, or null if the level can't exist
    public Node<AnyType> next(int level)
    {
        if((level < 0) || (level > (this.height - 1)))
            return null;
        return this.next.get(level);
    }

    // Past here are the methods suggested by Dr. Szumlanski
    // Sets the next reference of the node at the level given to another node
    public void setNext(int level, Node<AnyType> node)
    {
        this.next.set(level, node);
    }

    // Increases the height of the node by one level, and sets the new next
    // reference to null. Used for head
    public void grow()
    {
        // Increments height, and adds a null reference at that height
        this.height++;
        this.next.add(this.height, null);
    }

    // Has a 50% chance to grow a node who was at it's max height by 1 when the
    // height of the SkipList increases
    public void maybeGrow()
    {
        // Generates a random boolean, and if it's true, call grow
        Random rand = new Random();
        if(rand.nextBoolean() == true)
            this.grow();
    }

    // Removes next references past the height given, and sets the new height
    public void trim(int height)
    {

    }
}

public class SkipList<AnyType extends Comparable<AnyType>>
{
    // Number of nodes in SkipList, excluding the head
    int size;
    // Head of the SkipList
    Node<AnyType> head;

    // Initializes a SkipList with a height of 1
    SkipList()
    {
        SkipList<AnyType> skipList = new SkipList<>();
        this.size = 0;
        Node<AnyType> head = new Node<>(1);
        this.head = head;
    }

    // Initializes a SkipList with a given height
    SkipList(int height)
    {
        SkipList<AnyType> skipList = new SkipList<>();
        this.size = 0;
        Node<AnyType> head = new Node<>(height);
        this.head = head;
    }

    // Returns the size
    public int size()
    {
        return this.size;
    }

    // Returns the head of the SkipList
    public Node<AnyType> head()
    {
        return this.head;
    }

    // Inserts some data into the SkipList and randomizes the height of the node
    public void insert(AnyType data)
    {
        // Start insertion by checking if the new node will affect the max height
        // Compare the ceiling of log base 2 of n + 1 and n
        double max = getMaxHeight(this.size + 1);
        double prevMax = getMaxHeight(this.size);

        // Use a temp node to navigate the highest level of references
        Node<AnyType> temp = this.head;

        // Set an integer to the previous highest level
        int tempH = temp.height - 1;
        // If the new max is larger than the previous, growth must occur
        if(max > prevMax)
        {
            this.head.grow();
            // Travel along the previous highest level and maybe grow each node
            while(temp.next.get(tempH) != null)
            {
                temp.next.get(tempH).maybeGrow();
                temp = temp.next.get(tempH);
            }
        }

        // Generate the height of the new node, so while the SkipList is traversed,
        // the appropriate references can be set to the new node
        int h = this.generateRandomHeight(this.head.height);

        // Initialize the new node with the data and height
        Node<AnyType> newNode = new Node<>(data, h);

        // Re-Use temp node set initially as the head in order to traverse the SkipList
        temp = this.head;
        int i = 1;

        // Important note for continuing
        The last thing I was working on was how to traverse the SkipList and
        edit the nodes I was visiting. I'm using a temp node for traversal, but
        I can't change the references through the temp node. Find a way to edit
        the references without the convoluded cases I'm using, because it runs the
        risk of going out of bounds in the arrayList of references.

        // Traverse the list until the bottom level of the SkipList is checked
        while(i <= head.height)
        {
            // Create a base case where temp is equal to the head, and the first
            // movement down a level still has temp = head
            if(i == 1)
            {
                if(head.next.get(head.height - i).data.compareTo(data) < 0)
                {
                    // If the height of the new node is equal to the head, set heads
                    // highest reference to newNode
                    if(newNode.height == head.height)
                        head.setNext(head.height - i, newNode);
                }
                i++;
            }
            // From here, we look at the next next reference
            else if(i != head.height)
            {
                // If the old data is less than the new data, set temp equal to the
                // node being referenced, go forward
                if(temp.next.get(head.height - i).next.get(head.height - (i + 1)).data.compareTo(data) < 0)
                {
                    // If the height of the new node is greater than or equal to the
                    // previous temp node, set the temps highest reference to the new node
                    if(newNode.height >= temp.next.get(head.height - i).height)
                        temp.next.get(head.height - i).setNext(head.height - i, newNode);

                    temp = temp.next.get(head.height - i);
                }
                // If the old data is greater than or equal to the new data, check
                // the next level of references, go down
                else
                {
                    i++;
                }
            }
            // This is meant to ensure that we don't go out of bounds with our references
            else if(i == head.height)
            {
                if(temp.next.get(head.height - i).data.compareTo(data) < 0)
                {
                    if(newNode.height >= temp.next.get(head.height - i).height)
                        temp.next.get(head.height - i).setNext(head.height - i, newNode);
                }
            }
        }
    }

    // Inserts using the same code as above, but without making the height random
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
        return 4.5;
    }

    // Past here are the methods suggested by Dr. Szumlanski
    // Returns the max height of the SkipList when it has n nodes
    private static int getMaxHeight(int n)
    {
        double max = Math.ceil(Math.log(n)/Math.log(2));
        int intMax = (int) max;
        return intMax;
    }

    // Generates random height using repeated coin flips
    private static int generateRandomHeight(int maxHeight)
    {
        Random rand = new Random();
        int h = 1;
        while(rand.nextBoolean() == true)
        {
            if(h == maxHeight)
                break;
            else
                h++;
        }
        return h;
    }

    private void trimSkipList()
    {

    }
}
