package test;

import java.util.*;
import java.io.*;

public class Encode
{
	// Input Text to Encode and OutPut Encoded Text
	public static void main(String[] args) throws IOException
	{
		File encodedFile = new File("lzw.text0.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(encodedFile));
		writer.write(encode("lzw.text0.txt"));
		writer.close();
	}
	
	//takes in a filename, returns a string with the integers representing the codes delimited by spaces. does not return a bitstream.
	public static String encode(String filename) throws IOException
	{
		String inputFile = LZWHelper.readFile(filename);
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		String previous = "";
		String current = "" + inputFile.charAt ( 0 );
		String combined = previous+current;
		//this value is the size of our initial dictionary.
		int value = 255;
		String output = "";
		String tableOutput = "";
		//building our ASCII dictionary
		for(int i = 0; i < 256; i++)
		{
			dictionary.put((char)(i)+"", i);
		}
		for(int i=1;i<inputFile.length()+1;i++)
		{
			combined = previous+current;
			//if the length of the character is 1, then it should already be in our dictionary; no need to waste time checking.
			if(combined.length()==1||dictionary.containsKey(combined))
			{
				
				previous = previous + current;
				//this patches the edge case when the code has reached the end of the string
				if(i == inputFile.length())
				{
					output = output + " " + ((int)dictionary.get(previous));
				}
				
			}
			else
			{
				output = output+" "+((int)dictionary.get(previous));
				value = value + 1;
				dictionary.put(combined,value);
				//this patches the edge case when the code has reached the end of the string
				if(i == inputFile.length())
				{
					output = output + " " + (int)(inputFile.charAt(inputFile.length()-1));
				}
				previous = current;
			}
			//this conditional is necessary because i runs from 1 to file.length().
			if(i < inputFile.length())
			{
				current = inputFile.substring(i,i+1);
			}
		}
		//this concatenates the dictionary into a single string called "tableOutput"
		int length = dictionary.size();
		String[] table = new String[length];
		for(Map.Entry<String, Integer> Entry: dictionary.entrySet())
		{
			table[Entry.getValue()] = Entry.getKey();
		}
		for(int i=256; i<table.length; i++)
		{
			tableOutput += table[i] + " ";
		}
		tableOutput = tableOutput.substring(0, tableOutput.length() - 1);
		//this returns the contents of both tableOutput and the encoded output
		return ("" + (length - 256) + " " + tableOutput + output);
	}
}