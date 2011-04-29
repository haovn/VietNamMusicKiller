/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author haonguyen
 */
public class NhacCuaTuiThreadlevel3 extends Thread{
  JTextArea txtDownlasableLink;
  JTextArea txtRealLink;
  //String url;
  int index;
  public void run()
  {
    String level2_content=this.txtRealLink.getText();
    String []level2_content_arr=level2_content.split("\n");
      // trong khi level2 chưa có dữ liệu thì vẫn chờ
      while(level2_content_arr.length<index+1 || (level2_content_arr.length==index+1 && level2_content_arr[index].isEmpty()))
      {
        try {
          this.sleep(100);
          level2_content=this.txtRealLink.getText();
          level2_content_arr=level2_content.split("\n");
        } catch (InterruptedException ex) {
          Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
    level2_content=this.txtRealLink.getText();
    level2_content_arr=level2_content.split("\n");
          
    NhacCuaTuiClass nct=new NhacCuaTuiClass();
    if(level2_content_arr[index].isEmpty()==false)
    {
      String t=nct.LayLinkThucSuCuaBaiHat(level2_content_arr[index]);
      
      txtDownlasableLink.append(t+"\n");
    }
  }
  
}
