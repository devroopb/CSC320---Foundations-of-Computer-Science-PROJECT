           /*
  CSC 320 Project
  Sudoku Solver
  Sonia, Callum, Nelson, ChengXiang, Devroop
*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import java.io.*;

public class sud2sat {
  
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
  
  /*
    *  Method 5 - Chengxiang Xiong
    *  decimal number change to the number based 9
	* */
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
  
  /* Method 6: Takes a string in base 10  and convert it to base 9.
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
	   Method 7 - Callum Thomas
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
  Method 8 - Devroop Banerjee
  Takes an array or list of strings and joins them by a space.
  */
	public static String stringify(String[] splits){
	  
		String joined = String.join(" ", splits); // joins the strings in the array
		return joined;
		
  }
  
  /*
  Method 9 - negate_clause
   Current version by Callum Thomas. Prints out the negated clauses and adds leading zeroes
  */
  public static String negate_clause(String clause){
  
    String[] items = clause.split(" ");
    
    String negated_clause = "";
    
    for(int i =0; i<items.length; i++){
      
      if(items[i].equals("0")){
        negated_clause = negated_clause + "0";
        break;
      }
      
      negated_clause = negated_clause + "-" + items[i] + " ";
      
    }
    
    return negated_clause;
  
  
  }
  
  /*
	 * Method 10 - Chengxiang Xiong
	 *
	*/
  public static String dcmi_format(String line) {
    
		String[] splitLine = line.split("\\s+");
		for (int i = 0; i < splitLine.length; i++) {
		  
			splitLine[i] = plus1(nonal2dec_triple(minus1_all(splitLine[i])));
		}
		return stringify(splitLine);
	}
  
  /* Method 11: Takes a String and separate the string by the space using an array. Return a String modified by minus1, dec2nona, plus1_all. - Sonia Barrios
  */
  public static String readable_format(String line){
    String[] splitLine = line.split("\\s+"); // creates an array with the strings
    for(int i=0; i< splitLine.length; i++){
      splitLine[i] = plus1_all(dec2nonal_triple(minus1(splitLine[i])));
    }
    return stringify(splitLine); // uses method 8 to join the strings by a space and return it.
  }
  
  /*
	Method 12 - Callum Thomas
	Prints a header
	*/
  public static void print_header(){
    System.out.println ("c cnf is file type, first num is variables, second num is clauses");
    System.out.println ("p cnf 729 8577");
  }
  
  /*
	  Method 13 - Devroop Banerjee
	  Reads file
	  Removes all symbols
	  Splits strings into substrings of 3
	  Replaces " " with a " 0"
	*/
  
  public static void print_given_cells_requirement(String txt){
    //Encoding for input cells
    //Open a File
 
    BufferedReader br;
    try{
        File file = new File(txt);
        br = new BufferedReader(new FileReader(file));
        try{
            String encoding="";
            int i = 1;
            int j = 1;
            String line="";
            while ((line = br.readLine())!= null){
                
                
                line = line.replace("\n", "");
                line = line.replace(" ", "");
                line = line.replace("-", "");
                line = line.replace("+", "");
                line = line.replace("|", "");
                
                
                for(int k = 0; k < line.length(); k++){
                    if(Character.isDigit(line.charAt(k)) && Character.getNumericValue(line.charAt(k))>0){
                        encoding +=String.format("%s%s%s ", i, j, line.charAt(k));
                    }
                    j += 1;
                    if (j > 9){
                        i += 1;
                        j = 1;
                    }
                }
            }
            System.out.println("c Given cells");
            
            String [] splits = dcmi_format(encoding).split("\\s+");
            for(int l=0; l<splits.length; l++){
                System.out.println(splits[l]+" 0");
            }
        }catch(IOException e){
            System.err.println("Error reading the file");
            System.exit(1);

        }


    }catch (FileNotFoundException e){
        System.err.println("Error opening or creating the file");
        System.exit(1);
    }
  }
  
  /* Method 14 - Nelson Dai
  Prints the clauses for the requirement of each cell needing a value */
  
  public static void print_cell_requirement() {
		System.out.print("c Output for first criteria. Each square needs a number.");
		System.out.println();

		ArrayList<String> clause = new ArrayList<String>();
		ArrayList<String> term = new ArrayList<String>();

		for (int i = 1; i < 10; i++) {
			for (int j = 1; j < 10; j++) {
				clause.clear();
				for (int k = 1; k < 10; k++) {
					clause.add(String.format("%s%s%s", i, j, k));
				}
				clause.add("0");
				term.add(stringify(clause.toArray(new String[0])));
			}
		}
		for (int i = 0; i < term.size(); i++) {
			String st = dcmi_format(term.get(i));
			System.out.println(st);
		}
	}
	/*
	 * Method 15 - Chengxiang Xiong
	* */
  public static void print_row_requirement() {
  		System.out.println();
  		System.out.print("c Output for second criteria. Each row is unique.");
  		System.out.println();
  		
  		ArrayList<String> clause = new ArrayList<String>();
  		ArrayList<String> term = new ArrayList<String>();
  		
  		for (int i = 1; i < 10; i++) {
  			for (int k = 1; k < 10; k++) {
  				for (int j = 1; j < 9; j++) {
  					for (int l = j + 1; l < 10; l++) {
  					  clause.clear();
  						clause.add(String.format("%s%s%s", i, j, k));
  						clause.add(String.format("%s%s%s", i, l, k));
  						clause.add("0");
  						term.add(stringify(clause.toArray(new String[0])));
  
  					}
  				}
  			}
  		}
  		for (int i = 0; i < term.size(); i++) {
  		  
  			String st = dcmi_format(term.get(i));
      
        System.out.println(negate_clause(st));
        
  		}
  
  	}

  /* Method 16: Doesn’t take any parameters -Sonia Barrios
   ///Rough method (need testing and debugging)
  */
  public static void print_col_requirement() {
      System.out.println("c Output for the third criteria. Each column is unique");
      ArrayList<String> clause = new ArrayList<String>();
      ArrayList<String> term = new ArrayList<String>();
      for(int j=1; j<10; j++){
          for(int k=1;k<10; k++){
              for(int i=1; i<9; i++){
                  for( int l=i+1; l<10; l++){
                      clause.clear();
                      clause.add(String.format("%s%s%s", i, j, k));
                      clause.add(String.format("%s%s%s", l, j, k));
                      clause.add("0");
                      term.add(stringify(clause.toArray(new String[clause.size()])));
                  }
              }
          }
      }
      for(int i=0; i< term.size(); i++){
          System.out.println(negate_clause(dcmi_format(term.get(i))));
      }
  }

  /*Method 17 - Callum Thomas
  	Prints the subgrid 3x3 uniqueness requirement clauses.
  */
  public static void print_subgrid_requirement() {
    
    System.out.println();
    System.out.println("c Output for the fourth criteria. Each 3x3 cell is unique");
    

    ArrayList<String> term = new ArrayList<String>();
    ArrayList<String> clause = new ArrayList<String>();
    
    for(int k=1; k<10; k++){
        for(int a=0;a<3; a++){
            for(int b=0; b<3; b++){
                for(int u=1; u<3; u++){
                  for(int v=1; v<4; v++){
                    for(int w=u+1; w<4; w++){
                      for(int t=1; t<4; t++){
                        if(v != t){
                          clause.clear();
                          clause.add(String.format("%s%s%s", 3*a+u, 3*b+v, k));
                          clause.add(String.format("%s%s%s", 3*a+w, 3*b+t, k));
                          clause.add("0");
                          term.add(stringify(clause.toArray(new String [0])));
                        }
                      }
                    }
                  }
                }
            }
        }
    }
    
    for(int i=0; i< term.size(); i++){

      String st = dcmi_format(term.get(i));
      
      System.out.println(negate_clause(st));
      
    }
    
    
  }


  

  /*
	  Method 18 - Devroop Banerjee
	  Main.... Self explanatory
	*/
	
  public static void main(String[] args){
    
        
    print_header();
    
    String file = args[0];
    
    print_given_cells_requirement(file);

    print_cell_requirement();

    print_row_requirement();

    print_col_requirement();

    print_subgrid_requirement();
    
    
      
  }


}

