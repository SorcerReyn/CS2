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
        this.next = new ArrayList<Node<AnyType>>();
        for(int i = 0; i < height; i++)
            this.next.add(i, null);
    }

    // Initializes a node with data and height
    Node(AnyType data, int height)
    {
        this.data = data;
        this.height = height;
        this.next = new ArrayList<Node<AnyType>>();
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
        if((level < 0) || (level >= this.height))
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
        this.height = height() + 1;
        this.next.add(this.height() - 1, null);
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
        this.size = 0;
        Node<AnyType> head = new Node<>(1);
        this.head = head;
    }

    // Initializes a SkipList with a given height
    SkipList(int height)
    {
        this.size = 0;
        Node<AnyType> head = new Node<>(height);
        this.head = head;
    }

    // Returns the size
    public int size()
    {
        return this.size;
    }

    public int height()
    {
        return head().height;
    }

    // Returns the head of the SkipList
    public Node<AnyType> head()
    {
        return this.head;
    }

    // Inserts some data into the SkipList and randomizes the height of the node
    public void insert(AnyType data)
    {
        // Increments size
        this.size = size() + 1;

        // If the newNode is the first to be inserted, add it directly
        // If the new max is larger than the previous, growth must occur
        if(head().next(0) == null)
        {
            Node<AnyType> newNode = new Node<>(data, 1);
            head().setNext(0, newNode);
        }
        else
        {
            // Start insertion by checking if the new node will affect the max height
            // Compare the ceiling of log base 2 of n + 1 and n
            double max = getMaxHeight(size());
            double prevMax = getMaxHeight(size() - 1);
            // Generate the height of the new node, so while the SkipList is traversed,
            // the appropriate references can be set to the new node
            int h = generateRandomHeight(height());

            // Initialize the new node with the data and height
            Node<AnyType> newNode = new Node<>(data, h);

            // Use the temp to navigate the highest level of references
            Node<AnyType> temp = head();

            if(max > prevMax)
            {
                head().grow();
                boolean truther = true;

                // Travel along the previous highest level and maybe grow each node
                while(truther == true)
                {
                    if(height() == 2)
                        break;
                    if(temp.next(height() - 2) != null)
                    {
                        temp.next(height() - 2).maybeGrow();
                        if((temp.height()) == (temp.next(height() - 2).height()))
                            temp.setNext(height() - 1, temp.next(height() - 2));
                        temp = temp.next(temp.height() - 2);
                    }
                    else
                    {
                        truther = false;
                    }
                }
            }

            // Use temp set initially as the head in order to traverse the SkipList
            temp = head();
            int i = height() - 1;

            // Traverse the list until the bottom level of the SkipList is checked
            while(i >= 0)
            {

                if(temp.next(i) == null)
                {
                    if(newNode.height() >= (i + 1))
                    {
                        newNode.setNext(i, temp.next(i));
                        temp.setNext(i, newNode);
                    }
                    i--;
                }
                // If the old data is less than the new data, set temp equal to the
                // node being referenced, go forward
                else if(temp.next(i).value().compareTo(data) < 0)
                {
                    temp = temp.next(i);
                }
                // If the old data is greater than or equal to the new data, check
                // the next level of references, go down
                else
                {
                    // If the height of the new node is greater than or equal to the
                    // previous temp node, set the temps highest reference to the new node
                    if(newNode.height() >= (i + 1))
                    {
                        newNode.setNext(i, temp.next(i));
                        temp.setNext(i, newNode);
                    }
                    i--;
                }
            }
        }
    }

    // Inserts using the same code as above, but without making the height random
    public void insert(AnyType data, int height)
    {
        // Increments size
        this.size = size() + 1;
        int h = height;

        // If the newNode is the first to be inserted, add it directly
        // If the new max is larger than the previous, growth must occur
        if(head().next(0) == null)
        {
            Node<AnyType> newNode = new Node<>(data, h);
            for(int i = 0; i < h; i++)
            {
                head().setNext(i, newNode);
            }
        }
        else
        {
            // Start insertion by checking if the new node will affect the max height
            // Compare the ceiling of log base 2 of n + 1 and n
            double max = getMaxHeight(size());
            double prevMax = getMaxHeight(size() - 1);

            // Initialize the new node with the data and height
            Node<AnyType> newNode = new Node<>(data, h);

            // Use the temp to navigate the highest level of references
            Node<AnyType> temp = head();

            if(max > prevMax)
            {
                head().grow();
                boolean truther = true;

                // Travel along the previous highest level and maybe grow each node
                while(truther == true)
                {
                    if(height() == 2)
                        break;
                    if(temp.next(height() - 2) != null)
                    {
                        temp.next(height() - 2).maybeGrow();
                        if((temp.height()) == (temp.next(height() - 2).height()))
                            temp.setNext(height() - 1, temp.next(height() - 2));
                        temp = temp.next(temp.height() - 2);
                    }
                    else
                    {
                        truther = false;
                    }
                }
            }

            // Use temp set initially as the head in order to traverse the SkipList
            temp = head();
            int i = height() - 1;

            // Traverse the list until the bottom level of the SkipList is checked
            while(i >= 0)
            {

                if(temp.next(i) == null)
                {
                    if(newNode.height() >= (i + 1))
                    {
                        newNode.setNext(i, temp.next(i));
                        temp.setNext(i, newNode);
                    }
                    i--;
                }
                // If the old data is less than the new data, set temp equal to the
                // node being referenced, go forward
                else if(temp.next(i).value().compareTo(data) < 0)
                {
                    temp = temp.next(i);
                }
                // If the old data is greater than or equal to the new data, check
                // the next level of references, go down
                else
                {
                    // If the height of the new node is greater than or equal to the
                    // previous temp node, set the temps highest reference to the new node
                    if(newNode.height() >= (i + 1))
                    {
                        newNode.setNext(i, temp.next(i));
                        temp.setNext(i, newNode);
                    }
                    i--;
                }
            }
        }
    }

    // Function for deletion of nodes while moving references to the next
    // appropriate place
    public void delete(AnyType data)
    {
        // If the SkipList only has 1 other node, just set all of heads references to null
        if(size() == 1)
        {
            for(int j = 0; j < height(); j++)
                head().setNext(j, null);
        }
        else
        {
            Node<AnyType> temp = head();
            // Use target node to replace references
            Node<AnyType> target = get(data);
            int i = height() - 1;

            // Traverse the list until the bottom level of the SkipList is checked
            while(i >= 0)
            {
                if(temp.next(i) == null)
                    i--;
                // If the old data is less than the target set temp equal to the
                // node being referenced, go forward
                else if(temp.next(i).value().compareTo(data) < 0)
                {
                    temp = temp.next(i);
                }
                // If the old data is greater than or equal to the target, check
                // the next level of references, go down
                else
                {
                    // If the next node is equal to the target, set the temp
                    // nodes references to the target
                    if(temp.next(i).value() == target.value())
                        temp.setNext(i, target.next(i));
                    i--;
                }
            }
        }
    }

    // Function for checking if a value is contained in the SkipList
    public boolean contains(AnyType data)
    {
        Node<AnyType> temp = head();
        int i = height() - 1;

        // Traverse the list until the bottom level of the SkipList is checked
        while(i >= 0)
        {
            if(temp.next(i) == null)
                i--;
            // If the old data is less than the new data, set temp equal to the
            // node being referenced, go forward
            else if(temp.next(i).value().compareTo(data) < 0)
            {
                temp = temp.next(i);
            }
            // If the old data is greater than or equal to the new data, check
            // the next level of references, go down
            else
            {
                if(temp.next(i).value().compareTo(data) == 0)
                    return true;
                i--;
            }
        }
        return false;
    }

    public Node<AnyType> get(AnyType data)
    {
        Node<AnyType> temp = head();
        Node<AnyType> target = null;
        int i = height() - 1;

        // Traverse the list until the bottom level of the SkipList is checked
        while(i >= 0)
        {
            if(temp.next(i) == null)
                i--;
            // If the old data is less than the new data, set temp equal to the
            // node being referenced, go forward
            else if(temp.next(i).value().compareTo(data) < 0)
            {
                temp = temp.next(i);
            }
            // If the old data is greater than or equal to the new data, check
            // the next level of references, go down
            else
            {
                if(temp.next(i).value().compareTo(data) == 0)
                    target = temp.next(i);
                i--;
            }
        }
        return target;
    }

    public static double difficultyRating()
    {
        return 4;
    }

    public static double hoursSpent()
    {
        return 8;
    }

    // Past here are the methods suggested by Dr. Szumlanski
    // Returns the max height of the SkipList when it has n nodes
    private static int getMaxHeight(int n)
    {
        double max = Math.ceil(Math.log(n)/Math.log(2));
        int intMax = (int) max;
        if(max == 0)
            return 1;
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
                return h;
            else
                h++;
        }
        return h;
    }

    private void trimSkipList()
    {

    }

    /*public static void main(String [] args)
    {
        SkipList<Integer> s = new SkipList<Integer>();
        Node<Integer> nod = s.head();
		s.insert(10);
		s.insert(20);
		s.insert(3);
		s.insert(15);
		s.insert(5);

    }*/
}
