/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zip_unzip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
/**
 *
 * @author Arjun
 */
public class LZ77_version2 {
    
  public static final int DEFAULT_BUFF_SIZE = 1024;
  protected int bufferSize;
  protected Reader mIn;
  protected PrintWriter mOut;
  protected StringBuffer searchBuff; 

  public LZ77_version2() {
    this(DEFAULT_BUFF_SIZE);
  }
  
  public LZ77_version2(int buffSize) {
    bufferSize = buffSize;
    searchBuff = new StringBuffer(bufferSize);
  }

  private void searchBuffer() {
    if (searchBuff.length() > bufferSize) {
      searchBuff = 
  searchBuff.delete(0,  searchBuff.length() - bufferSize);
    }
  }

  public void unCompress(String infile) throws IOException {
    mIn = new BufferedReader(new FileReader(infile+".lz77"));
    StreamTokenizer st = new StreamTokenizer(mIn);

    st.ordinaryChar((int)' ');
    st.ordinaryChar((int)'.');
    st.ordinaryChar((int)'-');
    st.ordinaryChar((int)'\n');
    st.wordChars((int)'\n', (int)'\n');

    int offset, length;
    while (st.nextToken() != StreamTokenizer.TT_EOF) {
      switch (st.ttype) {
      case StreamTokenizer.TT_WORD:
	searchBuff.append(st.sval);
	System.out.print(st.sval);
	 
	searchBuffer();
	break;
      case StreamTokenizer.TT_NUMBER:
	offset = (int)st.nval;  
	st.nextToken(); 
	if (st.ttype == StreamTokenizer.TT_WORD) {
	  
	  searchBuff.append(offset+st.sval);
	  System.out.print(offset+st.sval);
	  break;
	} 
	st.nextToken();  
	length = (int)st.nval; 
	String output = searchBuff.substring(offset, offset+length);
	System.out.print(output);
	searchBuff.append(output);
 
	searchBuffer();
	break;
      default:
	 
      }
    }
    mIn.close();
  }
 
  public void compress(String infile) throws IOException {
    
    mIn = new BufferedReader(new FileReader(infile));
    mOut = new PrintWriter(new BufferedWriter(new FileWriter(infile+".lz77")));

    int nextChar;
    String currentMatch = "";
    int matchIndex = 0, tempIndex = 0;

      while ((nextChar = mIn.read()) != -1) {
       tempIndex = searchBuff.indexOf(currentMatch + (char)nextChar);
        if (tempIndex != -1) {
	currentMatch += (char)nextChar;
	matchIndex = tempIndex;
      } else {
 	searchBuffer();
      }
    }
     if (matchIndex != -1) {
      String codedString = 
	"~"+matchIndex+"~"+currentMatch.length();
      if (codedString.length() <= currentMatch.length()) {
	mOut.print("~"+matchIndex+"~"+currentMatch.length());
      } else {
	mOut.print(currentMatch);
      }
    }
     mIn.close();
    mOut.flush(); mOut.close();
  }
  
  public static void main(String [] args) throws IOException {
    if (args.length < 2) {
      System.err.println("Usage: LZ77_version2 [c|d] <filename>");
      System.exit(1);
    }
    LZ77_version2 lz = new LZ77_version2();
    if (args[0].indexOf('d') != -1) {
        lz.unCompress(args[1]);
    } else {
        lz.compress(args[1]);
    }
  }

}
