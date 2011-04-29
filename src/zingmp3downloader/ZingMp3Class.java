/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.util.*;
/**
 *
 * @author haonguyen
 */
public class ZingMp3Class {
  
  // khai báo các thuộc tính
  
  public ArrayList<StringBuffer> DanhSachBaiHatLevel1=new ArrayList<StringBuffer>();
  public ArrayList<StringBuffer> DanhSachBaiHatLevel2=new ArrayList<StringBuffer>();
  public ArrayList<StringBuffer> DanhSachBaiHatLevel3=new ArrayList<StringBuffer>();
  public ArrayList<ZingSongClass> DanhSachBaiHatLevel4=new ArrayList<ZingSongClass>();//danh sách các bài hát hoàn chỉnh
  
  
  //public JTextArea txtlevel1;
  public JTable txtlevel1=null;
  public int SoLuongTrang;
  public StringBuffer getContentFromURL(String URL)
    {
      //-----------------------------------------------------//
      //  Step 1:  Start creating a few objects we'll need.
      //-----------------------------------------------------//

      URL u;
      InputStream is = null;
      DataInputStream dis;
      BufferedReader dis1 = null;
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
         URLConnection yc=u.openConnection();
         //-------------------------------------------------------------//
         // Step 4:                                                     //
         //-------------------------------------------------------------//
         // Convert the InputStream to a buffered DataInputStream.      //
         // Buffering the stream makes the reading faster; the          //
         // readLine() method of the DataInputStream makes the reading  //
         // easier.                                                     //
         //-------------------------------------------------------------//

         dis = new DataInputStream(new BufferedInputStream(is));
         
          dis1 = new BufferedReader(new InputStreamReader(yc.getInputStream(),"utf-8"));

         //------------------------------------------------------------//
         // Step 5:                                                    //
         //------------------------------------------------------------//
         // Now just read each record of the input stream, and print   //
         // it out.  Note that it's assumed that this problem is run   //
         // from a command-line, not from an application or applet.    //
         //------------------------------------------------------------//
         StringBuffer result=new StringBuffer();
         while ((s = dis1.readLine()) != null) {
           
           //String s1=URLEncoder.encode(s,"UTF-8") ;
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
            //dis1.close();
         } catch (IOException ioe) {
            // just going to ignore this one
         }

      } // end of 'finally' clause
    }
   
  /**
   * Lấy thông tin url trong thẻ href
   * @param text
   * @return 
   */
  public String getHrefValue(String text)
   {
     String[] parse1 = text.split("href=");
       //String[] parse2=parse1[1].split(text);
     String parse2=parse1[1];
     int last_index=parse2.indexOf(" ");
     String url=parse2.substring(1,last_index-1);
       
//       int end_index=url.indexOf(">");
//       
//       String kq=url.substring(0, end_index-1);
       return url;
   }
  
  /*
   * Lấy số lượng trang kết quả của 1 link tìm kiếm
   * URL (String): Link tìm kiếm
   */
  public int getNumberOfPages(String URL)
  {
    int page_num=0;
    StringBuffer content=getContentFromURL(URL);
    String []content_arr=content.toString().split("\n");
    // duyệt qua danh sách các dòng của trang tìm kiếm (source)
    for(int i=0;i<content_arr.length;i++)
    {
      if(content_arr[i].indexOf("Trang cuối")!=-1)
      {
        String page_content=content_arr[i];
        // xử lý trên content
        int first_index=page_content.indexOf("&p=");
        int last_index=page_content.indexOf(" title=");
        String page=page_content.substring(first_index+3, last_index-1);
        
        page_num=Integer.parseInt(page);
        break;
      }
    }
    this.SoLuongTrang=page_num;
    return page_num;
    //int index=content.indexOf("<span> ...</span>");
    
  }
  
  public void ThemBaiHatVaoTable(ZingSongClass baihat, JTable danhsach)
  {
    
  }
  /**
   * Lấy danh sách các bài hát theo 1 link search.
   * @param strURL
   * @return 
   */
  public ArrayList<StringBuffer> layBaiHatTheoURLSearch(String strURL)
  {
    //ArrayList<StringBuffer> result=new ArrayList<StringBuffer>();
    
    //String strURL="http://www.nhaccuatui.com/tim_kiem?key=Lam+Truong&by=casi";// @todo: lấy tên riêng
    
    int page_num=getNumberOfPages(strURL);
    System.out.print(page_num);
    //int page_num=1;// @todo: lấy tất cả các trang
    for(Integer i=1;i<=page_num;i++)
    {
      String new_url=strURL+"&page="+i.toString();
      ArrayList<StringBuffer> dsbaihat=layBaiHatTheoCaSi_one_page(new_url);
      // dồn kết quả này vào kết quả cuối cùng
      for(int j=0;j<dsbaihat.size();j++)
      {
        DanhSachBaiHatLevel1.add(dsbaihat.get(j));
        //txtlevel1.append(dsbaihat.get(j).toString()+"\n");// @todo
        if(this.txtlevel1!=null)
        {
          // thực hiện cập nhật dữ liệu vào table
        }
      }
      // tam ngung 1s
      try {
        Thread.currentThread().sleep(2000);
      } catch (InterruptedException ex) {
        Logger.getLogger(ZingMp3Class.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return DanhSachBaiHatLevel1;
  }
  /**
   * Lấy danh sách các bài hát trong 1 trang trả về của kết quả tìm kiếm
   * @param strURL: http://mp3.zing.vn/tim-kiem/bai-hat.html?q=Bao+Thy&t=artist&p=2
   * @return 
   */
  public ArrayList<StringBuffer> layBaiHatTheoCaSi_one_page(String strURL)
  {
    ArrayList<StringBuffer> result=new ArrayList<StringBuffer>();
    Parser parser = null;
    
    try {
       parser = new Parser (strURL);
    } catch (ParserException ex) {
      Logger.getLogger(ZingMp3Class.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // lấy content từ parser
    // lấy các thẻ a là con của thẻ h2
    NodeList list = new NodeList ();
    NodeFilter filter =
          new AndFilter (
              new TagNameFilter ("A"),
              new LinkStringFilter("/bai-hat/")
              //new HasParentFilter(new TagNameFilter("H2"))
           );
    try {
      list = parser.parse (filter);
    } catch (ParserException ex) {
      Logger.getLogger(ZingMp3Class.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // duyệt qua danh sách các node
    // duyệt qua danh sách tất cả các link cấp 1
     for(int i=0;i<list.size();i++)
     {
      
        Node n=list.elementAt(i);
        
        String text=n.toHtml();
        String link=getHrefValue(text);
        result.add(new StringBuffer("http://mp3.zing.vn"+link));
     }
     return result;
  }
  
  /**
   * Lấy link bài hát trong thẻ object
   * @param text: nội dung của thẻ Obejct
   * @return Hàm này trả ra URL dạng: http://mp3.zing.vn/xml/song/knxHyZHNdJXpdpxTLbxtbnZn
   */
  public String getFileInObjectTag(String text)
  {
    String result="";
    int first_index=text.indexOf("xmlURL=");
    int last_index=text.indexOf(">");
    
    result=text.substring(first_index+7, last_index-1);
    return result;
            
  }
  /**
   * 
   * @param url: link của bài hát dạng: http://mp3.zing.vn/bai-hat/Lat-Lai-Ky-Uc-Bao-Thy/ZWZBOO0E.html
   * Hàm này trả ra URL dạng: http://mp3.zing.vn/xml/song/knxHyZHNdJXpdpxTLbxtbnZn
   */
  public String LayLinkdownLoadCuaBaiHat(String url)
  {
    String result = null;
    Parser parser = null;
    
    try {
       parser = new Parser (url);
    } catch (ParserException ex) {
      Logger.getLogger(ZingMp3Class.class.getName()).log(Level.SEVERE, null, ex);
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
      Logger.getLogger(ZingMp3Class.class.getName()).log(Level.SEVERE, null, ex);
    }
    // duyệt qua danh sách các node
    // duyệt qua danh sách tất cả các link cấp 1
     for(int i=0;i<list.size();i++)
     {
      
        Node n=list.elementAt(i);
        
        String text=n.toHtml();
        
        // xử lý cho string trả về:
        result=getFileInObjectTag(text);
        this.DanhSachBaiHatLevel2.add(new StringBuffer(result));
        return result;// thoát ra luôn
        
     }
     //this.DanhSachBaiHatLevel2.add(new StringBuffer(result));
     return result;
     
    
  }
  
  /**
   * Lấy link download thực sự của bài hát 
   * @param link : http://mp3.zing.vn/xml/song/LHJHyLnsdcCWdQJtLbcyDGLm
   * trả về: http://stream2.mp3.zdn.vn/fsfsdfdsfdserwrwq3/78761e209bd21973a4ade40574e33bc4/4db6fa10/2011/03/28/4/0/4071491c39aa7ac101707238c56f5e62.mp3
   */
  public String LayLinkThucSuCuaBaiHat(String url)
  {
    StringBuffer content=getContentFromURL(url);
    int first_index=content.indexOf("<source>");
    int last_index=content.indexOf("</source>");
    if(first_index!=-1 && last_index!=-1)
    {
      String link=content.substring(first_index+8, last_index)+"?q=f1e0500cea824fa761b7d35c530c17da&t=60342336";
      this.DanhSachBaiHatLevel3.add(new StringBuffer(link));
      return link;//thêm phần đuôi này mới lấy được file :((
    }
    this.DanhSachBaiHatLevel3.add(new StringBuffer("no link avalable"));
    return "no link avalable";
  }
  
  /*
   * Lấy tên của bài hát thông qua link down dạng view-source:http://mp3.zing.vn/xml/song/LHJHyLnsdcCWdQJtLbcyDGLm
   */
  public String LayTenBaiHatThongQuanLinkDownloadThucSu(String url)
  {
    StringBuffer content=getContentFromURL(url);
    int first_index_baihat=content.indexOf("<title><![CDATA[ ");
    int first_index_casi=content.indexOf("<singer><![CDATA[ ");
    
    int last_index_baihat=content.indexOf(" ]]></title>");
    int last_index_casi=content.indexOf(" ]]></singer>");
    
    String tenbaihat=content.substring(first_index_baihat+17, last_index_baihat);
    String tencasi=content.substring(first_index_casi+18, last_index_casi);
    return tenbaihat+" - "+tencasi;
  }
  
  
  private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

  String decodeUTF8(byte[] bytes) {
      return new String(bytes, UTF8_CHARSET);
  }

  byte[] encodeUTF8(String string) {
      return string.getBytes(UTF8_CHARSET);
  }


  /*
   * Lấy tên của bài hát thông qua link down dạng view-source:http://mp3.zing.vn/xml/song/LHJHyLnsdcCWdQJtLbcyDGLm
   */
  public String LayThongTinBaiHat(int stt)// số thứ tự bài hát cần lấy
  {
    String url=this.DanhSachBaiHatLevel2.get(stt).toString();
    StringBuffer content=getContentFromURL(url);
    int first_index_baihat=content.indexOf("<title><![CDATA[ ");
    int first_index_casi=content.indexOf("<singer><![CDATA[ ");
    
    int last_index_baihat=content.indexOf(" ]]></title>");
    int last_index_casi=content.indexOf(" ]]></singer>");
    
    String tenbaihat=content.substring(first_index_baihat+17, last_index_baihat);
    String tencasi=content.substring(first_index_casi+18, last_index_casi);
    
    
    String string=tenbaihat+" - "+tencasi;
//    byte[] b=encodeUTF8(string);
//    string=decodeUTF8(b);
    
    
    
    
    
    
    ZingSongClass s=new ZingSongClass();
    s.TenBaiHat=string;
    s.linkdownload=this.DanhSachBaiHatLevel3.get(stt).toString();
    this.DanhSachBaiHatLevel4.add(s);
    
    

    
    //String result= new String(tenbaihat+" - "+tencasi, "UTF-8");
    
    
    
    return tenbaihat+" - "+tencasi;
  }
  
  
  
  
}


