/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haonguyen
 */
public class QueueDownloadThread extends Thread{
  // quản lý danh sách tất cả downloader đang chạy
    public ArrayList<DownloadThread> dowloaders=new ArrayList<DownloadThread>();
    
    Integer row_selected_index;
    Integer max_queue_size=5;// số lượng file tối đa được phép download cùng lúc
    Integer current_queue_size=0;// số lượng file tối đa được phép download cùng lúc
    
    
    public void run()
    {
        int i;
        for( i=0;i<this.dowloaders.size();) {
          if(i>=1 && dowloaders.get(i-1).dl!=null && dowloaders.get(i-1).dl.isCompleted())
          {
            this.current_queue_size--;
          }
          if(this.current_queue_size<this.max_queue_size)
          {
            //dowloaders.get(i).current_queue_size=this.current_queue_size;
            dowloaders.get(i).start();
            i++;
            this.current_queue_size++;// tăng kích thước của hàng đợi lên
            // thực hiện cập nhật lại giá trị của table tương ứng với thread này
          }
          else
          {
            try {
              this.sleep(100);// tạm dừng 200ms chờ download
            } catch (InterruptedException ex) {
              Logger.getLogger(DialogDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
          }
      }
    }
}
