/*
  CSC 320 Project
  ResultProcessing
  Sonia, Callum, Nelson, ChengXiang, Devroop
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import java.io.*;

public class sat2sud {
  
  /* Method 1: Takes a string and return the same string if it’s “0” or decreases number on the string by 1 and returns it. - Sonia Barrios
  */
	public static String minus1_all(String triple){
        if(triple.equals("0")){  //Checks if the string is “0”
      	  return triple;
        }
        String conv_triple = "";
        for(int i=0; i<triple.length(); i++){
            conv_triple= conv_triple + Integer.toString(Character.getNumericValue(( triple.charAt(i))-1)); // Decreases each character in the string by one.
        }
        return conv_triple;
   }
  
  /*
	Method 2 - Callum Thomas
	  Takes a string number in base 9 (Nonary) and converts it into a string in base 10 (decimal)
	  Either returns 0 or converts the string into decimal form and returns that string
    Also pads the number with zeroes on the left.
	*/
  public static String nonal2dec_triple(String triple){
      
    if(triple.equals("0")){
      
      return triple;
    }
    String nonal2dec = Integer.toString((Integer.parseInt(triple, 9)));
    return String.format("%03d", Integer.parseInt(nonal2dec));
  }
  
  
  /*
  Method 3 - Devroop Banerjee
	  Takes string of number
	  Either returns 0 or adds a 1 (if it's a 3 digit number)
	*/
	public static String plus1(String triple){
		if(triple.equals("0")){
			return triple;
		}

		//return Integer.toString((Integer.parseInt(triple)+1));
    String lala = String.format("%03d",(Integer.parseInt(triple)+1));
		return lala;
	}
	
  /* Method 4 - Nelson Dai
	 Takes string of number
	 Either returns 0 or minus 1 (if it's a 3 digit number) */
  public static String minus1(String triple){
    
		if(triple.equals("0")){
			return triple;
		}
		String lalala = String.format("%03d",(Integer.parseInt(triple)-1));
		return lalala;
	}
  
  
  /* Method 5: Takes a string in base 10  and convert it to base 9.
  Returns: the same string if it’s “0” or the nonary version of the input String. -Sonia Barri
  */
  public static String dec2nonal_triple(String triple){
      if(triple.equals("0")){
          return triple;
      }
      triple = Integer.toString(Integer.parseInt(triple, 10), 9); // convert the string in base 10 to base 9.
      triple = String.format("%3s", triple).replace(" ", "0"); // Replace with “0” if the string is less than 3 digits.
      return triple;
  }
   
	/*
	   Method 6 - Callum Thomas
	   Adds one to each character in a triple value and returns it.
	*/
  public static String plus1_all(String triple){

  	if(triple.equals("0")){
  
  	     return triple;
  	}

  	String triple_convert = "";
	
  	for(int i = 0; i < triple.length(); i++){
  	
  	    int trip = Character.getNumericValue(triple.charAt(i));
  	    trip++;//Add one to the character converted to an integer.
  	    triple_convert = triple_convert + trip;//Append the plus1ed characters
  
  	}
  	return String.format("%03d", Integer.parseInt(triple_convert));
  }
  
  
  /*
  Method 7 - Devroop Banerjee
  Takes an array or list of strings and joins them by a space.
  */
	public static String stringify(String[] splits){
	  
		String joined = String.join(" ", splits); // joins the strings in the array
		return joined;
		
  }
  
  
  /*
	 * Method 8 - Chengxiang Xiong
	 *
	*/
  public static String dcmi_format(String line) {
    
		String[] splitLine = line.split("\\s+");
		for (int i = 0; i < splitLine.length; i++) {
		  
			splitLine[i] = plus1(nonal2dec_triple(minus1_all(splitLine[i])));
		}
		return stringify(splitLine);
	}
  
  /* Method 9: Takes a String and separate the string by the space using an array. Return a String modified by minus1, dec2nona, plus1_all. - Sonia Barrios
  */
  public static String readable_format(String line){
    String[] splitLine = line.split("\\s+"); // creates an array with the strings
    for(int i=0; i< splitLine.length; i++){
      splitLine[i] = plus1_all(dec2nonal_triple(minus1(splitLine[i])));
    }
    return stringify(splitLine); // uses method 7 to join the strings by a space and return it.
  }
  
  public static int base9(int decimal){
  		if(decimal==0)
  			return 0;
  		int sign = 1;
  		if(decimal<0){
  			sign = -1;
  			decimal *= -1;
  		}
  		ArrayList<String> degits = new ArrayList<String>();
  		while(decimal > 0 ){
  			degits.add(String.valueOf(decimal % 9));
  			decimal =decimal/9;
  		}
  		Collections.reverse(degits);
  		StringBuffer buf_for_degits = new StringBuffer();
  		Iterator<String> deg = degits.iterator();
  		boolean isNull = deg.hasNext();
  		while(isNull){
  			buf_for_degits.append(deg.next());
  			isNull = deg.hasNext();
  		}
  		String result = buf_for_degits.toString();
  		return Integer.parseInt(result) *sign;
  }


  
  public static void print_output_as_readable(String[] args){
    
    String output = "";
    
    if(args.length > 0){
      File file = new File(args[0]);
      
      Scanner scan;
      try{
        
        scan = new Scanner(file);
        scan.next(); //Skip "SAT"
        
        int count = 0;
        
        while(scan.hasNext()){
          
          String s = scan.next();
          
          if(Integer.parseInt(s) > 0){
            
            s = readable_format(s);
            
            output = output + s.substring(s.length() - 1) + " ";
            count++;
          }
          
          if(count == 9){
            output = output + "\r\n";
            count = 0;
          }
          
        }
        
        
          
      } catch (FileNotFoundException fileNotFoundException){
          
        System.err.println("Error opening or creating the file");
        System.exit(1);
        
      }
      
    } else {
      System.err.println("no sat result file found in command line arguments.");
      System.exit(1);
    }
    
    System.out.println("Puzzle Solution:");
    System.out.println();
    System.out.println(output);
  
  }
  /*
	  Method 10 - Devroop Banerjee
	  Main.... Self explanatory
	*/
	
  public static void main(String[] args){
    
    print_output_as_readable(args);
      
  }

}

