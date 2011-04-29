/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.io.*;

/**
 *
 * @author haonguyen
 */
public class FileReader {
  public StringBuffer readFile(String f)
  {
      try{
      // Open the file that is the first 
      // command line parameter
      FileInputStream fstream = new FileInputStream(f);
      // Get the object of DataInputStream
      DataInputStream in = new DataInputStream(fstream);
          BufferedReader br = new BufferedReader(new InputStreamReader(in));
      String strLine;
      //Read File Line By Line
      StringBuffer result=new StringBuffer();
      Integer count=0;
      
      while ((strLine = br.readLine()) != null)   {
        
        
        result.append(strLine+"\n");
        

      }  
      //Close the input stream
      in.close();
      return result;
      
      }catch (Exception e){//Catch exception if any
        System.err.println("Error: " + e.getMessage());
        return null;
      }
    
  }
}
