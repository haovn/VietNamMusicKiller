/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author haonguyen
 */
public class ZingMp3ThreadLevel4 extends  Thread{
//  JTextArea txtDanhSachBaiHat;// nơi để hiển thị dữ liệu
//  JTextArea txtDownlasableLink;
//  JTextField txtSoLuong;
//  int index;
  ZingMp3Class zing;
  //String url;
  int current_level4=0;// stt của bài hát đang được xử lý
  JTable tabledanhsach;
  public void run()
  {
    while(current_level4 < (zing.SoLuongTrang-1)*24)// @todo: *24 -> mỗi trang có 24 bài
    {
      // nếu trong zing chưa có đủ thông tin của level1 cần xử lý ==> chờ 100ms
      if(zing.DanhSachBaiHatLevel3.size()<=current_level4)
      {
        try {
          this.sleep(100);
        } catch (InterruptedException ex) {
          Logger.getLogger(ZingMp3Threadlevel3.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      else// ngược lại thì dữ liệu đã có ==> xử lý no liền
      {
        
//        if(zing.DanhSachBaiHatLevel3.get(current_level4).toString().isEmpty()==false)// nếu link ko rỗng thì mới làm
//        {
//          zing.LayLinkThucSuCuaBaiHat(zing.DanhSachBaiHatLevel2.get(current_level4).toString());
//          
//        }
        // lấy thông tin của bài hát và đưa lên table
        zing.LayThongTinBaiHat(current_level4);// sau khi lấy xong xe lưu trực tiếp vào properties của class zingmp3
        current_level4++;
        
      }
    }// end mothod run
  }
}
