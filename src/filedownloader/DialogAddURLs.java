/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DialogAddURLs.java
 *
 * Created on Apr 29, 2011, 9:16:35 PM
 */
package filedownloader;

import java.util.ArrayList;

/**
 *
 * @author haonguyen
 */
public class DialogAddURLs extends javax.swing.JDialog {
  public ArrayList file_list=new ArrayList();
  /** Creates new form DialogAddURLs */
  public DialogAddURLs(java.awt.Frame parent, boolean modal) {
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
    txtURL = new javax.swing.JTextArea();
    btnAdd = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setName("Form"); // NOI18N

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    txtURL.setColumns(20);
    txtURL.setRows(5);
    txtURL.setName("txtURL"); // NOI18N
    jScrollPane1.setViewportView(txtURL);

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(zingmp3downloader.ZingMp3DownloaderApp.class).getContext().getResourceMap(DialogAddURLs.class);
    btnAdd.setText(resourceMap.getString("btnAdd.text")); // NOI18N
    btnAdd.setName("btnAdd"); // NOI18N
    btnAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnAddActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 783, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(66, Short.MAX_VALUE))
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(647, Short.MAX_VALUE)
        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(95, 95, 95))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(23, 23, 23)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addComponent(btnAdd)
        .addContainerGap(15, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
    // TODO add your handling code here:
    String text=txtURL.getText();
    
    String []text_arr=text.split("\n");
    
    for(int i=0;i<text_arr.length;i++)
    {
      file_list.add(text_arr[i]);
    }
    this.setVisible(false);
  }//GEN-LAST:event_btnAddActionPerformed

  /**
   * @param args the command line arguments
   */
  public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

      public void run() {
        DialogAddURLs dialog = new DialogAddURLs(new javax.swing.JFrame(), true);
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
  private javax.swing.JButton btnAdd;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextArea txtURL;
  // End of variables declaration//GEN-END:variables
}
