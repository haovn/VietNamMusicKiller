/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.io.IOException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.*;
/**
 *
 * @author haonguyen
 */
public class NhacCuaTuiClass {
  public StringBuffer getContentFromURL(String URL)
    {
      //-----------------------------------------------------//
      //  Step 1:  Start creating a few objects we'll need.
      //-----------------------------------------------------//

      URL u;
      InputStream is = null;
      DataInputStream dis;
      String s;

      try {

         //------------------------------------------------------------//
         // Step 2:  Create the URL.                                   //
         //------------------------------------------------------------//
         // Note: Put your real URL here, or better yet, read it as a  //
         // command-line arg, or read it from a file.                  //
         //------------------------------------------------------------//

         u = new URL(URL);

         //----------------------------------------------//
         // Step 3:  Open an input stream from the url.  //
         //----------------------------------------------//

         is = u.openStream();         // throws an IOException

         //-------------------------------------------------------------//
         // Step 4:                                                     //
         //-------------------------------------------------------------//
         // Convert the InputStream to a buffered DataInputStream.      //
         // Buffering the stream makes the reading faster; the          //
         // readLine() method of the DataInputStream makes the reading  //
         // easier.                                                     //
         //-------------------------------------------------------------//

         dis = new DataInputStream(new BufferedInputStream(is));

         //------------------------------------------------------------//
         // Step 5:                                                    //
         //------------------------------------------------------------//
         // Now just read each record of the input stream, and print   //
         // it out.  Note that it's assumed that this problem is run   //
         // from a command-line, not from an application or applet.    //
         //------------------------------------------------------------//
         StringBuffer result=new StringBuffer();
         while ((s = dis.readLine()) != null) {
           
            //System.out.println(s);
           result.append(s+"\n");
         }
         
         return result;

      } catch (MalformedURLException mue) {

         System.out.println("Ouch - a MalformedURLException happened.");
         
         mue.printStackTrace();
         System.exit(1);
         return null;

      } catch (IOException ioe) {

         System.out.println("Oops- an IOException happened.");
         ioe.printStackTrace();
         
         System.exit(1);
         return null;

      } finally {

         //---------------------------------//
         // Step 6:  Close the InputStream  //
         //---------------------------daf------//

         try {
            is.close();
         } catch (IOException ioe) {
            // just going to ignore this one
         }

      } // end of 'finally' clause
    }
   
  
  public String getHrefValue(String text)
   {
     String[] parse1 = text.split("href=");
       //String[] parse2=parse1[1].split(text);
       String url=parse1[1].substring(1);
       
       int end_index=url.indexOf(">");
       
       String kq=url.substring(0, end_index-1);
       return kq;
   }
  public int getNumberOfPages(String URL)
  {
    int page_num=0;
    StringBuffer content=getContentFromURL(URL);
    String []content_arr=content.toString().split("\n");
    for(int i=0;i<content_arr.length;i++)
    {
      if(content_arr[i].indexOf("<span> ...</span>")!=-1)
      {
        String page_content=content_arr[i+1];
        // xử lý trên content
        int first_index=page_content.indexOf(" | ");
        int last_index=page_content.indexOf(" trang - ");
        String page=page_content.substring(first_index+3, last_index);
        
        page_num=Integer.parseInt(page);
        break;
      }
    }
    return page_num;
    //int index=content.indexOf("<span> ...</span>");
    
  }
  public ArrayList<StringBuffer> layBaiHatTheoCaSi(String strURL,JTextArea txt)
  {
    ArrayList<StringBuffer> result=new ArrayList<StringBuffer>();
    
    //String strURL="http://www.nhaccuatui.com/tim_kiem?key=Lam+Truong&by=casi";// @todo: lấy tên riêng
    
    int page_num=getNumberOfPages(strURL);
    //int page_num=1;
    for(Integer i=1;i<=page_num;i++)
    {
      String new_url=strURL+"&page="+i.toString();
      ArrayList<StringBuffer> dsbaihat=layBaiHatTheoCaSi_one_page(new_url);
      // dồn kết quả này vào kết quả cuối cùng
      for(int j=0;j<dsbaihat.size();j++)
      {
        result.add(dsbaihat.get(j));
        txt.append(dsbaihat.get(j).toString()+"\n");// @todo
      }
      // tam ngung 1s
      try {
        Thread.currentThread().sleep(2000);
      } catch (InterruptedException ex) {
        Logger.getLogger(NhacCuaTuiClass.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return result;
  }
  public ArrayList<StringBuffer> layBaiHatTheoCaSi_one_page(String strURL)
  {
    ArrayList<StringBuffer> result=new ArrayList<StringBuffer>();
    Parser parser = null;
    //String strURL="http://www.nhaccuatui.com/tim_kiem?key=Lam+Truong&by=casi";// @todo: lấy tên riêng
    try {
       parser = new Parser (strURL);
    } catch (ParserException ex) {
      Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // lấy content từ parser
    // lấy các thẻ a là con của thẻ h2
    NodeList list = new NodeList ();
    NodeFilter filter =
          new AndFilter (
              new TagNameFilter ("A"),
              //new LinkStringFilter("/nghe?M"),
              new HasParentFilter(new TagNameFilter("H2"))
           );
    try {
      list = parser.parse (filter);
    } catch (ParserException ex) {
      Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // duyệt qua danh sách các node
    // duyệt qua danh sách tất cả các link cấp 1
     for(int i=0;i<list.size();i++)
     {
      
        Node n=list.elementAt(i);
        
        String text=n.toHtml();
        String link=getHrefValue(text);
        result.add(new StringBuffer("http://www.nhaccuatui.com"+link));
     }
     return result;
  }
  
  public String getFileInObjectTag(String text)
  {
    String result="";
    String[]text_arr=text.split(";");//
    if(text_arr.length!=2)
      return result;
    String file=text_arr[1];//file=http://www.nhaccuatui.com/api/playerv7.ashx?key2=007c00020023002b000a001f000000050064003b" id="flash-player">
    String []file_arr=file.split(" ");//file=http://www.nhaccuatui.com/api/playerv7.ashx?key2=007c00020023002b000a001f000000050064003b"
    
    String file1=file_arr[0];
    
    String []file1_arr=file1.split("=");
    
    result=file1_arr[1]+"="+file1_arr[2].substring(0,file1_arr[2].length()-1);
    
    return result;
            
  }
  /**
   * 
   * @param url: link của bài hát dạng: nhaccuatui.com/nghe/M=xxx 
   */
  public String LayLinkdownLoadCuaBaiHat(String url)
  {
    String result = null;
    Parser parser = null;
    //String strURL="http://www.nhaccuatui.com/tim_kiem?key=Lam+Truong";// @todo: lấy tên riêng
    try {
       parser = new Parser (url);
    } catch (ParserException ex) {
      Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // lấy content từ parser
    // lấy các thẻ <embeded> trong thẻ object
    NodeList list = new NodeList ();
    NodeFilter filter =
          new AndFilter (
              new TagNameFilter ("embed"),
              //new LinkStringFilter("/nghe?M"),
              new HasParentFilter(new TagNameFilter("object"))
           );
    try {
      list = parser.parse (filter);
    } catch (ParserException ex) {
      Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
    }
    // duyệt qua danh sách các node
    // duyệt qua danh sách tất cả các link cấp 1
     for(int i=0;i<list.size();i++)
     {
      
        Node n=list.elementAt(i);
        
        String text=n.toHtml();
        
        // xử lý cho string trả về:
        result=getFileInObjectTag(text);
        return result;// thoát ra luôn
        
     }
     return result;
     
    
  }
  
  /**
   * 
   * @param link : http://www.nhaccuatui.com/api/playerv7.ashx?key2=0063000500070073007600320028006e002e0026
   */
  public String LayLinkThucSuCuaBaiHat(String url)
  {
    StringBuffer content=getContentFromURL(url);
    int first_index=content.indexOf("<location><![CDATA[");
    int last_index=content.indexOf("]]></location>");
    if(first_index!=-1 && last_index!=-1)
    {
      return content.substring(first_index+19, last_index);
    }
    return "no link avalable";
  }
  
  public String LayTenBaiHatThongQuanLinkDownloadThucSu(String url)
  {
    String [] url_array=url.split("/");
    
    // lấy phần tử cuối cùng
    
    String name=url_array[url_array.length-1];
    return name;
  }
  
  
}


