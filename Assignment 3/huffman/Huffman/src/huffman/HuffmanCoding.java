package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Ishaan Ivaturi
 * @author Prince Rawal
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    private int findSumOfArray(int[] input) {
        int occuranceSum = 0;
        for (int holder : input) {
            occuranceSum += holder;
        }
        return occuranceSum;
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     */
    public void makeSortedList() {

        StdIn.setFile(fileName);

        //Array declaration of an array that holds count of how many times every single ascii character appeared in the input file
        int[] occurance = new int [128];

        //Parse and add entire input file for their characters, and count how many times they appear
        while (StdIn.hasNextChar())
        {
            occurance[StdIn.readChar()] += 1;
        }

        //Find the total occurance of all characters in the entire input
        double totalOccurance = findSumOfArray(occurance);

        //Declare unsorted CharFreq AL
        sortedCharFreqList = new ArrayList<CharFreq>();

        //Add each character that made its appearance within the input to the AL with its respective probability of their apperance
        for (int i = 0; i < occurance.length; i++)
        {
            if (occurance[i] > 0)
            {
            CharFreq node = new CharFreq((char) i, occurance[i]/totalOccurance);
            sortedCharFreqList.add(node);
            }
        }

        //Account for Huffman Algorithm not working with 1 character ONLY
        if (sortedCharFreqList.size() == 1)
        {
            if (sortedCharFreqList.get(0).getCharacter() + 1 == 128)
            {
                char tempchar = (char) 0;

                CharFreq node = new CharFreq(tempchar, 0);
                sortedCharFreqList.add(node);
            } else 
            {
                int tempint = sortedCharFreqList.get(0).getCharacter() + 1;
                char tempchar = (char) tempint;

                CharFreq node = new CharFreq(tempchar, 0);
                sortedCharFreqList.add(node);
            }
        }

        //Sort the AL based on probability of their apperance
        Collections.sort(sortedCharFreqList);
	/* Your code goes here */
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     */
    public void makeTree() {

        //Create two queues that use CharFreq as parameters
        Queue<CharFreq> source = new Queue<CharFreq>();
        Queue<TreeNode> target = new Queue<TreeNode>();

        //Enqueue the CharFreq nodes into Source in increasing order of occurrence probability using sortedCharFreqList AL
        for (int i = 0; i < sortedCharFreqList.size(); i++)
        {
            source.enqueue(sortedCharFreqList.get(i));
        }
    
        //Instantiate the TreeNode class
        TreeNode leafLeft = new TreeNode();
        TreeNode leafRight = new TreeNode();

        //Repeat Huffman Tree building until these requirements are met
        while (source.isEmpty() == false || target.size() > 1)
        {
            // if (source.isEmpty() && target.size() <= 1)
            // break;

            //Determine what would be on the left of the null char node with combined probability value
            if (target.isEmpty())
            {
                
                leafLeft = new TreeNode(source.dequeue(), null, null);

            } else if (source.isEmpty())
            {
                
                leafLeft = target.dequeue();

            } else if (source.peek().getProbOcc() <= target.peek().getData().getProbOcc())
            {
                
                leafLeft = new TreeNode(source.dequeue(), null, null); 

            } else if (source.peek().getProbOcc() > target.peek().getData().getProbOcc())
            {
                
                leafLeft = target.dequeue();

            }
    
            //Determine what would be on the right of the null char node with combined probability value
            if (target.isEmpty())
            {
                
                leafRight = new TreeNode(source.dequeue(), null, null);

            } else if (source.isEmpty())
            {
                
                leafRight = target.dequeue();

            } else if (source.peek().getProbOcc() <= target.peek().getData().getProbOcc())
            {
                
                leafRight = new TreeNode(source.dequeue(), null, null); 

            } else if (source.peek().getProbOcc() > target.peek().getData().getProbOcc())
            {
                
                leafRight = target.dequeue();

            }

            //Enqueue a new node with null character, and a combined probability value of the two least probablity value from source/target, while having them as their left/right child as well
            double probability = leafLeft.getData().getProbOcc() + leafRight.getData().getProbOcc();

            CharFreq node = new CharFreq(null, probability);
            TreeNode treeNode = new TreeNode(node, leafLeft, leafRight);

            target.enqueue(treeNode);
        }

        //Set huffmanRoot to the root of the target queue
        huffmanRoot = target.peek();
	/* Your code goes here */
    }

    private void search(TreeNode input, String s)
    {
        if (input == null)
        {
            return;
        }

        if (input.getData().getCharacter() != null)
        {
            encodings[(int)input.getData().getCharacter()] = s;
        }

        search(input.getLeft(), s + "0");
        search(input.getRight(), s + "1");
    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
    encodings = new String[128];
    String s = "";
    search(huffmanRoot, s);
	/* Your code goes here */
    }

    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        String s = "";

        while (StdIn.hasNextChar())
        {
           s = s + encodings[(int) StdIn.readChar()];
        }

        writeBitString(encodedFile, s);
	/* Your code goes here */
    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);

        String s = "";
        String decoded = "";

        s = readBitString(encodedFile);

        char[] c = s.toCharArray();

        TreeNode ptr = huffmanRoot;

        for(int i = 0; i < c.length; i++)
        {
            if (c[i] == '0')
            {

                ptr = ptr.getLeft();

            } else
            {

                ptr = ptr.getRight();

            }

            if (ptr.getLeft() == null && ptr.getRight() == null)
            {
                decoded = decoded + ptr.getData().getCharacter().toString();
                ptr = huffmanRoot;
            }
        }

        StdOut.print(decoded);
	/* Your code goes here */
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
