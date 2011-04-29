/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author haonguyen
 */
public class NhacCuaTuiThreadLevel4 extends  Thread{
  JTextArea txtDanhSachBaiHat;// nơi để hiển thị dữ liệu
  JTextArea txtDownlasableLink;
  JTextField txtSoLuong;
  int index;
  public void run()
  {
    String level3_content=this.txtDownlasableLink.getText();
    String []level3_content_arr=level3_content.split("\n");
      // trong khi level2 chưa có dữ liệu thì vẫn chờ
      while(level3_content_arr.length<index+1 || (level3_content_arr.length==index+1 && level3_content_arr[index].isEmpty()))
      {
        try {
          this.sleep(100);
          level3_content=this.txtDownlasableLink.getText();
          level3_content_arr=level3_content.split("\n");
        } catch (InterruptedException ex) {
          Logger.getLogger(NhacCuaTuiDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
    level3_content=this.txtDownlasableLink.getText();
    level3_content_arr=level3_content.split("\n");
          
    NhacCuaTuiClass nct=new NhacCuaTuiClass();
    if(level3_content_arr[index].isEmpty()==false)
    {
      String tenbaihat=nct.LayTenBaiHatThongQuanLinkDownloadThucSu(level3_content_arr[index]);
      
      txtDanhSachBaiHat.append(tenbaihat+"\n");
      
      // cập nhật lại số lượng
      
      try{
        Integer current=Integer.parseInt(txtSoLuong.getText());
        current++;

        txtSoLuong.setText(current.toString());
      }
      catch(Exception ex)
      {
        
      }
    }
  }
}
