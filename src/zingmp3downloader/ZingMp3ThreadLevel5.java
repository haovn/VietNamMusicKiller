/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author haonguyen
 */
public class ZingMp3ThreadLevel5 extends Thread{
  ZingMp3Class zing;
  //String url;
  int current_level5=0;// stt của bài hát đang được xử lý
  JTable tabledanhsach;
  public void run()
  {
    while(current_level5 < (zing.SoLuongTrang-1)*24)// @todo: *24 -> mỗi trang có 24 bài
    {
      // nếu trong zing chưa có đủ thông tin của level1 cần xử lý ==> chờ 100ms
      if(zing.DanhSachBaiHatLevel4.size()<=current_level5)
      {
        try {
          this.sleep(100);
        } catch (InterruptedException ex) {
          Logger.getLogger(ZingMp3ThreadLevel5.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      else// ngược lại thì dữ liệu đã có ==> xử lý no liền
      {
        
        // lấy thông tin của bài hát và đưa lên table
        ZingSongClass s=new ZingSongClass();
        s=zing.DanhSachBaiHatLevel4.get(current_level5);
        // hiển thị s lê table "tabledanhsach"
        DefaultTableModel model=(DefaultTableModel) tabledanhsach.getModel();
        
        
       Vector  row=new Vector();

       row.add(current_level5);
       row.add(s.TenBaiHat);
       model.addRow(row);
                
                
                
                
        current_level5++;
      }
    }// end mothod run
  }
}
