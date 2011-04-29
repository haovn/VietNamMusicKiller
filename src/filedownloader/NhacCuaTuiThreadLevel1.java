/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author haonguyen
 */
public class NhacCuaTuiThreadLevel1 extends Thread{
  JTextArea txtlevel1;
  String text;
  String url;
  public void run()
  {
    NhacCuaTuiClass nct=new NhacCuaTuiClass();
    ArrayList<StringBuffer> result=nct.layBaiHatTheoCaSi(url,txtlevel1);
    
    //NhacCuaTuiClass nct=new NhacCuaTuiClass();
    //txtlevel1.append(text);
  }
}
