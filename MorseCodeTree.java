//Name: Selah Rice
//Class: CS 3305/W01
//Term: Fall 2023
//Instructor: Sharon Perry
//Assignment: 7 - Part - 2 - Trees
import java.util.*;
import java.io.*;
public class MorseCodeTree {
    //hold our 'letters'
    static List<String> listOfChar = new ArrayList<String>();

    //Node class
    private static class Node {
        //value for node of tree
        private String value;
        //left branch
        private Node left;
        //right branch
        private Node right;

        //constructor
        public Node (String item) {
            //node value
            value = item;
            left = null;
            right = null;
        }
        //no value node constructor
        public Node () {
            value = null;
            left = null;
            right = null;
        }
    }
    //encode method
    public static boolean encode (Node tree, String character, StringBuilder morsecode) {
        //check if node has value
        if (tree == null) {
            return false;
        }
        //check if node equals character
        else if (tree.value.equals(character)) {
            return true;
        }
        else {
            //recursively pass values left then continue to right
            if (encode(tree.left, character, morsecode) == true) {
                morsecode = morsecode.append(".");
                return true;
            }
            else if (encode(tree.right, character, morsecode) == true) {
                morsecode = morsecode.append("-");
                return true;
            }
        }
        return false;
    }
    //decode method
    public static String decode (Node root, String morsecode) {
        //this creates tree for morsecode
        //store root in current
        Node current = root;

        //traverse through morsecode and take one character at a time
        for (int i=0; i<morsecode.length(); i++) {
            if (morsecode.charAt(i) == '.') {

                //now current pointed left
                current = current.left;
            }
            else {
                current = current.right;
            }
        }
        //after finishing traversing will give value of current
        return current.value;
    }

    //add method to add node to tree
    public static void addToTree (Node currentN, int currentL) {
        //level of tree is not less than 5, arraylist not empty, and node value is not null
        if (!currentN.value.equals("NULL")) {
            //fill current left node with first value from arraylist
            currentN.left = new Node(listOfChar.remove(0));

            //recursive use method to pass new node to next level
            addToTree(currentN.left,currentL+1);

            //do same to right
            currentN.right = new Node(listOfChar.remove(0));
            addToTree(currentN.right,currentL+1);
        }
    }

    public static void main(String[] args) throws IOException{
        //read in morse.txt file
        File file = new File("/Users/selahrice7/Downloads/morse.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String string;

        while ((string = br.readLine()) != null) {
            //split text
            if (string.length() > 0) {
                //read each line into arraylist
                listOfChar.add(string);
            }
        }

        //store first value in arraylist as root node
        Node root1 = new Node(listOfChar.get(0));

        //remove root from arraylist
        listOfChar.remove(0);

        //call tree method to create binary tree passing in root and value of 0 which is currentL
        addToTree(root1,0);

        //Scanner for option and scanner for input of encode/decode
        Scanner scan1 = new Scanner(System.in);
        Scanner scan2 = new Scanner(System.in);

        //user-friendly do-while
        int input;
        do {
            System.out.println("""
                    1. Encode
                    2. Decode
                    3. Quit""");
            input = scan1.nextInt();

            switch (input) {
                case 1:
                    //this encodes one character at a time
                    String result = "";
                    System.out.println("Enter letters to encode: ");
                    String message = scan2.nextLine();

                    for (int i=0; i<message.length(); i++) {
                        StringBuilder morsecode = new StringBuilder();
                        String character = ""+message.charAt(i);

                        encode(root1, character, morsecode);
                        result = result + morsecode.reverse() + " ";
                    }
                    System.out.println("This was encoded: " +result);
                    break;
                case 2:
                    //decodes morse code
                    System.out.println("Enter morsecode: ");
                    String morse = scan2.nextLine();

                    String[] result2 = morse.split(" ");
                    String temp = "";

                    for (int i=0; i<result2.length; i++) {
                        String character = decode(root1, result2[i]);
                        temp = temp + character;
                    }
                    System.out.println("This was decoded: " +temp);
            }
        } while (input != 3);

    }
}