package test;

import java.util.*;
import java.io.*;

public class Decode
{
	//this main function was our tester. when the string "abcabcabcabcabcabcabcabcabcabcabcabc" is in yolo.txt, it prints the following:
		//"97 98 99 257 259 258 260 263 262 265 261 267 264 99"
		public static void main(String[] args) throws IOException
		{
			System.out.println(decode("yolo.txt.lzw"));
		}
		//takes in a filename, returns a string with the integers representing the codes delimited by spaces. does not return a bitstream.
		public static String decode(String filename) throws IOException
		{
			String file = LZWHelper.readFile(filename);
			HashMap<Integer, String> dictionary = new HashMap<Integer, String>();
			
			//this value is the size of our initial dictionary.
			String output = "";
			
			//building our ASCII dictionary
			for(int i = 0; i < 256; i++)
			{
				dictionary.put(i, (char)(i)+"");
			}
			
			String reconstructed = "";
			
			// Number of new characters
			String[] stream = file.split(" ");
			
			int count = Integer.parseInt(stream[0]);
			
			for (int i = 1; i < 1 + count; ++i)
			{
				dictionary.put(i + 255, stream[i]);
			}
			
			for(int i = 1 + count; i < stream.length; ++i)
			{
				reconstructed += dictionary.get(Integer.parseInt(stream[i]));
			}
			
			return reconstructed;
		}
}