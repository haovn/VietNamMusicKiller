/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author haonguyen
 */
public class ZingMp3ThreadLevel1 extends Thread{
  //JTextArea txtlevel1;
  //JTable txtlevel1;
  //String text;
  String url;
  ZingMp3Class zing;// đối tượng này được truyền từ form chính qua
  //ArrayList<StringBuffer> result=new ArrayList<StringBuffer>();
 // int current=0;
  public void run()
  {
    //JTable txtlevel1;
    //ZingMp3Class nct=new ZingMp3Class();
    //nct.txtlevel1=this.txtlevel1;
    zing.layBaiHatTheoURLSearch(url);
//    // cập nhật giá trị lên text
//      for(int i=0;i<result.size();i++)
//      {
//        txtlevel1.append(result.get(i).toString()+"\n");
//        
//      }
    
    //------------------------------------------------------------------------------
    
    
//    ZingMp3Class nct=new ZingMp3Class();
//    ArrayList<StringBuffer> result=nct.layBaiHatTheoURLSearch(url);
    
//    ZingMp3ThreadLevel0 level0=new ZingMp3ThreadLevel0();
//    level0.url=this.url;
//    level0.result=this.result;
//    
//    level0.start();
    
//    while(level0.isAlive())
//    {
//      // cập nhật giá trị lên text
//      //txtlevel1.setText("");
//      for(int i=current;i<result.size();i++)
//      {
//        txtlevel1.append(result.get(i).toString()+"\n");
//        current=i;
//      }
//      try {
//        this.sleep(200);
//      } catch (InterruptedException ex) {
//        Logger.getLogger(ZingMp3ThreadLevel1.class.getName()).log(Level.SEVERE, null, ex);
//      }
//    }
    
  }
}
