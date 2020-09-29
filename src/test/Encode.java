//package test;

import java.util.*;
import java.io.*;

public class Encode
{
	//takes in "yolo.txt" and outputs the encoded "yolo.txt.lzw"
	public static void main(String[] args) throws IOException
	{
		encode("lzw-text0.txt", "lzw-text0_encoded.txt");
	}
	//takes in a filename, returns a string with the integers representing the codes delimited by spaces. does not return a bitstream.
	public static void encode(String filename, String outputFileName) throws IOException
	{
		//String inputFile = LZWHelper.readFile(filename);
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		String previous = "";
		String current = "";//inputFile.substring(0,1);
		String combined = previous+current;
		//this value is the size of our initial dictionary.
		int value = 255;
		String output = "";
		//String tableOutput = "";
		//building our ASCII dictionary
		for(int i = 0; i < 256; i++)
		{
			dictionary.put((char)(i)+"", i);
		}
		BufferedReader br = new BufferedReader(new FileReader(filename));
		PrintWriter pw = new PrintWriter (outputFileName);
		int a;
		System.out.println("started reading file");
		while((a = br.read()) != -1)
		{
			current = String.valueOf((char)a);
			combined = previous+current;
			if(combined.length() == 1 || dictionary.containsKey(combined))
			{
				previous = previous + current;
			}
			else
			{
				output += ((int)dictionary.get(previous))+" ";
				value = value + 1;
				dictionary.put(combined,value);
				pw.print("" + combined.length() + ":" + combined);
				previous = current;
			}
			
		}
		pw.print("x");
		pw.print(output);
		pw.print(dictionary.get(previous)+ " ");
		pw.close();
		br.close();
		
		System.out.println("Finished encoding");
		//this concatenates the dictionary into a single string called "tableOutput"
//		int length = dictionary.size();
//		String[] table = new String[length];
//		for(Map.Entry<String, Integer> Entry: dictionary.entrySet())
//		{
//			table[Entry.getValue()] = Entry.getKey();
//		}
//		for(int i=256; i<table.length; i++)
//		{
//			tableOutput += (table[i].length() + ":" + table[i]);
//			//tableOutput += table[i] + " ";
//		}
//		tableOutput = tableOutput.substring(0, tableOutput.length());
		//this returns the contents of both tableOutput and the encoded output
		//return (""+ tableOutput + "x" + output);
	}
}