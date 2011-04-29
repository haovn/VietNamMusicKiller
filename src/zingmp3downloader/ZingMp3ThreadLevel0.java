/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zingmp3downloader;

import java.util.ArrayList;

/**
 *
 * @author haonguyen
 */
public class ZingMp3ThreadLevel0 extends Thread{
  public String url;
  public ArrayList<StringBuffer> result;
  public void run()
  {
    ZingMp3Class nct=new ZingMp3Class();
    //nct.DanhSachBaiHatTheLinkSearch=this.result;
    result=nct.layBaiHatTheoURLSearch(url);
  }
}
