package test;
import java.io.*;
import java.util.*;
import java.io.*;

public class Encode
{
	// Input Text to Encode and OutPut Encoded Text
	public static void main(String[] args) throws IOException
	{
		File encodedFile = new File("lzw-text0.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(encodedFile));
		writer.write(encode("yolo.txt"));
		writer.close();
	}
	
	//takes in a filename, returns a string with the integers representing the codes delimited by spaces. does not return a bitstream.
	public static String encode(String fileName) throws IOException
	{
		ArrayList <String> dictionary = new ArrayList <String> (); 
		String p = "";
		char c = 0;
		String pc = "";
		
		FileReader fr = new FileReader (fileName);
		BufferedReader br = new BufferedReader(fr);
		PrintWriter pw = new PrintWriter ("encoded.txt");
		
		while (br.ready())
		{
			c = (char)br.read();
			pc = p+c;
			//dictionary excludes characters 0-255 in the ascii table
			//if pc is already in the dictionary or if it's in the ascii table
			if (dictionary.indexOf(pc) >= 0 || pc.length() == 1)
			{
				p = pc;
			}
			//print out value for previous character
			else
			{

				//if p is already in the ascii table
				if (p.length()==1)
				{
					pw.print((int)p.charAt(0) + " ");
				}
				//if only in dictionary
				else 
				{
					pw.print(256+dictionary.indexOf(p) + " ");
				}
				if (dictionary.size()<=1000)
				{
					dictionary.add(pc);
				}
				p= "" + c;
			}

		}
		//edge case
		//if previous is just one character then convert it to an int
		if (p.length() == 1 )
		{
			pw.print((int)p.charAt(0)+ " ");
		}
		//if previous is a longer String, then find it in the dictionary
		else
		{
			pw.print(256+dictionary.indexOf(p) + " ");
		}
		// Include the dictionary at the end of the encoded file
		// Print an x to represent the end of the code and the start of the dictionary
		pw.print("x");
		// print each the index of each dictionary entry, then the length of the entry so when reading it in, it is easy to know when to stop, then print the entry itself
		// these are delimited by a ":" between the index and the length and a "-" between the length and the entry itself
		for (int i = 0; i < dictionary.size(); i++) {
			pw.print("" + (i + 256) + ":" + dictionary.get(i).length() + "-" + dictionary.get(i));
		}
		//close all writers and readers
		pw.close();
		br.close();
		fr.close();
		return pc;
	}
}