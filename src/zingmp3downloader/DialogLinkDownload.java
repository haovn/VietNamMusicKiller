/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogLinkDownload.java
 *
 * Created on Apr 28, 2011, 11:13:11 PM
 */
package zingmp3downloader;

import javax.swing.JTable;

/**
 *
 * @author haonguyen
 */
public class DialogLinkDownload extends javax.swing.JDialog {
  JTable table;
  public ZingMp3Class zing;
  /** Creates new form DialogLinkDownload */
  public DialogLinkDownload(java.awt.Frame parent, boolean modal) {
    super(parent, modal);
    initComponents();
    
    
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    txtLinkdownload = new javax.swing.JTextArea();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setName("Form"); // NOI18N
    addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowOpened(java.awt.event.WindowEvent evt) {
        formWindowOpened(evt);
      }
    });

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    txtLinkdownload.setColumns(20);
    txtLinkdownload.setRows(5);
    txtLinkdownload.setName("txtLinkdownload"); // NOI18N
    jScrollPane1.setViewportView(txtLinkdownload);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 665, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(21, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(22, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    // TODO add your handling code here:
    // hiển thị danh sách bài hát
    for(int i=0;i<zing.DanhSachBaiHatLevel3.size();i++)
    {
      txtLinkdownload.append(zing.DanhSachBaiHatLevel3.get(i).toString()+"\n");
    }
  }//GEN-LAST:event_formWindowOpened

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        DialogLinkDownload dialog = new DialogLinkDownload(new javax.swing.JFrame(), true);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {

          public void windowClosing(java.awt.event.WindowEvent e) {
            System.exit(0);
          }
        });
        dialog.setVisible(true);
      }
    });
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea txtLinkdownload;
  // End of variables declaration//GEN-END:variables
}
