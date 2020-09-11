package test;

import java.util.*;
import java.io.*;

public class Encode
{
	//this main function was our tester. when the string "abcabcabcabcabcabcabcabcabcabcabcabc" is in yolo.txt, it prints the following:
	//"97 98 99 257 259 258 260 263 262 265 261 267 264 99"
	public static void main(String[] args) throws IOException
	{
		System.out.println(encode("yolo.txt"));
	}
	//takes in a filename, returns a string with the integers representing the codes delimited by spaces. does not return a bitstream.
	public static String encode(String filename) throws IOException
	{
		String file = LZWHelper.readFile(filename);
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		String previous = "";
		String current = file.substring(0,1);
		String combined = previous+current;
		//this value is the size of our initial dictionary.
		int value = 255;
		String output = "";
		String table = "";
		//building our ASCII dictionary
		for(int i = 0; i < 256; i++)
		{
			dictionary.put((char)(i)+"", i);
		}
		for(int i=1;i<file.length()+1;i++)
		{
			combined = previous+current;
			//if the length of the character is 1, then it should already be in our dictionary; no need to waste time checking.
			if(combined.length()==1||dictionary.containsKey(combined))
			{
				
				previous = previous + current;
				//this patches the edge case when the code has reached the end of the string
				if(i == file.length())
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
				if(i == file.length())
				{
					output = output + " " + (int)(file.charAt(file.length()-1));
				}
				previous = current;
			}
			//this conditional is necessary because i runs from 1 to file.length().
			if(i < file.length())
			{
				current = file.substring(i,i+1);
			}
		}
		int length = dictionary.size();
		String[] joeHouse = new String[length];
		for(Map.Entry<String, Integer> Entry: dictionary.entrySet())
		{
			joeHouse[Entry.getValue()] = Entry.getKey();
		}
		for(int i=0; i<joeHouse.length; i++)
		{
			table += joeHouse[i] + " ";
		}
		return table + "|" + output;
	}
}