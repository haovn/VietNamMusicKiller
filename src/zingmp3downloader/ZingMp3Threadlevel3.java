/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author haonguyen
 */
public class ZingMp3Threadlevel3 extends Thread{
//  JTextArea txtDownlasableLink;
//  JTextArea txtRealLink;
//  //String url;
//  int index;
  ZingMp3Class zing;
  //String url;
  int current_level3=0;// stt của bài hát đang được xử lý
  
  public void run()
  {
    
    while(current_level3 < (zing.SoLuongTrang-1)*24)// @todo: *24 -> mỗi trang có 24 bài
    {
      // nếu trong zing chưa có đủ thông tin của level1 cần xử lý ==> chờ 100ms
      if(zing.DanhSachBaiHatLevel2.size()<=current_level3)
      {
        try {
          this.sleep(100);
        } catch (InterruptedException ex) {
          Logger.getLogger(ZingMp3Threadlevel3.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      else// ngược lại thì dữ liệu đã có ==> xử lý no liền
      {
        
        if(zing.DanhSachBaiHatLevel2.get(current_level3).toString().isEmpty()==false)// nếu link ko rỗng thì mới làm
        {
          zing.LayLinkThucSuCuaBaiHat(zing.DanhSachBaiHatLevel2.get(current_level3).toString());
          
        }
        current_level3++;
        
      }
    }// end mothod run
  }
  
}
