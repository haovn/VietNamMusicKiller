/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filedownloader;

/**
 *
 * @author haonguyen
 */
public class DownloadWrapperThread extends Thread{
  Downloader dl;
  public void run()
  {
    dl.run();
  }
}
