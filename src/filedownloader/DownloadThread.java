/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;

/**
 *
 * @author haonguyen
 */ 
public class DownloadThread extends Thread{
  // khai báo các thuộc tính
  public String strURL;
  public String file_name_to_save;
  public JTable tableFile=new JTable();
  public int row_index;
  Downloader dl = null;
  // khai báo các phương thức
  public void run()
  {
    
    try {
     
      URL url=new URL(strURL) ;
      String filename=url.getFile();
      File f=new File(filename);
      String name=f.getName();
      name=name.replaceAll("%20", " ") ;
//      if(!f.exists())
//      {
//        try {
//          f.createNewFile();
//        } catch (IOException ex) {
//          Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE, null, ex);
//        }
//      }
      File real_name=new File(this.file_name_to_save+name);
      if(real_name.exists())// nếu file đã tồn tại thì xóa
      {
        real_name.delete();
      }
      dl = new Downloader(url, real_name);
      
      // gọi 1 thread khác đãm nhận việc download
      
      DownloadWrapperThread wrapper=new DownloadWrapperThread();
      wrapper.dl=dl;
      wrapper.start();
      
//      String adfa=url.getPath();
//      String ref=url.getRef();
//      File temp=new File(filename);
//      
//      String s=temp.getName();
     
      //dl.run();
      
      // thực hiện cập nhật lại giá trị của table tương ứng với thread này
      while(wrapper.dl.isCompleted()==false)// trong khi chưa download xong
      {
        try {
          // cập nhật lại giá trị cho table
          this.tableFile.setValueAt(name, row_index, 1);
          this.tableFile.setValueAt(dl.getLength()/1024, row_index, 2);
          this.tableFile.setValueAt(dl.getProgressPercent()+"%", row_index, 3);
          
          
          this.sleep(200);
        } catch (InterruptedException ex) {
          Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      this.tableFile.setValueAt("Completed", row_index, 3);
    } catch (MalformedURLException ex) {
      Logger.getLogger(DownloadThread.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
}
