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
public class NhacCuaTuiThreadLevel2 extends Thread {
  JTextArea txtLevel1;
  JTextArea txtRealLink;
  //String url;
  int index;
  public void run()
  {
    String level1_content=this.txtLevel1.getText();
    String []level1_content_arr=level1_content.split("\n");
      // trong khi level2 chưa có dữ liệu thì vẫn chờ
      while(level1_content_arr.length<index+1 || (level1_content_arr.length==index+1 && level1_content_arr[index].isEmpty()))
      {
        try {
          this.sleep(100);
          level1_content=this.txtLevel1.getText();
          level1_content_arr=level1_content.split("\n");
        } catch (InterruptedException ex) {
          Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
    level1_content=this.txtLevel1.getText();
    level1_content_arr=level1_content.split("\n");
 
    
    NhacCuaTuiClass nct=new NhacCuaTuiClass();
    String reallink=nct.LayLinkdownLoadCuaBaiHat(level1_content_arr[index]);
      
    txtRealLink.append(reallink+"\n");
  }
}
