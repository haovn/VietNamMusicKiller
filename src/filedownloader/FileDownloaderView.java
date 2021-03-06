/*
 * FileDownloaderView.java
 */

package filedownloader;

import java.awt.Point;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * The application's main frame.
 */
public class FileDownloaderView extends FrameView {
    // khai báo các thuộc tính
    ArrayList file_list=new ArrayList();
    // quản lý danh sách tất cả downloader đang chạy
    ArrayList<DownloadThread> dowloaders=new ArrayList<DownloadThread>();
    
    int row_selected_index;
    public FileDownloaderView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = FileDownloaderApp.getApplication().getMainFrame();
            aboutBox = new FileDownloaderAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        FileDownloaderApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    mainPanel = new javax.swing.JPanel();
    jToolBar1 = new javax.swing.JToolBar();
    btnURL = new javax.swing.JButton();
    btnStart = new javax.swing.JButton();
    btnPauseAll = new javax.swing.JButton();
    btnResumAll = new javax.swing.JButton();
    btnPauseOne = new javax.swing.JButton();
    btnResumOne = new javax.swing.JButton();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    tableFiles = new javax.swing.JTable();
    txtSaveTo = new javax.swing.JTextField();
    jLabel1 = new javax.swing.JLabel();
    btnSelectFolder = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu fileMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenu helpMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
    statusPanel = new javax.swing.JPanel();
    javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
    statusMessageLabel = new javax.swing.JLabel();
    statusAnimationLabel = new javax.swing.JLabel();
    progressBar = new javax.swing.JProgressBar();

    mainPanel.setName("mainPanel"); // NOI18N

    jToolBar1.setRollover(true);
    jToolBar1.setName("jToolBar1"); // NOI18N

    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(filedownloader.FileDownloaderApp.class).getContext().getResourceMap(FileDownloaderView.class);
    btnURL.setIcon(resourceMap.getIcon("btnURL.icon")); // NOI18N
    btnURL.setText(resourceMap.getString("btnURL.text")); // NOI18N
    btnURL.setToolTipText(resourceMap.getString("btnURL.toolTipText")); // NOI18N
    btnURL.setFocusable(false);
    btnURL.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnURL.setName("btnURL"); // NOI18N
    btnURL.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnURL.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnURLActionPerformed(evt);
      }
    });
    jToolBar1.add(btnURL);

    btnStart.setIcon(resourceMap.getIcon("btnStart.icon")); // NOI18N
    btnStart.setText(resourceMap.getString("btnStart.text")); // NOI18N
    btnStart.setFocusable(false);
    btnStart.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnStart.setName("btnStart"); // NOI18N
    btnStart.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnStart.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnStartActionPerformed(evt);
      }
    });
    jToolBar1.add(btnStart);

    btnPauseAll.setIcon(resourceMap.getIcon("btnPauseAll.icon")); // NOI18N
    btnPauseAll.setText(resourceMap.getString("btnPauseAll.text")); // NOI18N
    btnPauseAll.setEnabled(false);
    btnPauseAll.setFocusable(false);
    btnPauseAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnPauseAll.setName("btnPauseAll"); // NOI18N
    btnPauseAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnPauseAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPauseAllActionPerformed(evt);
      }
    });
    jToolBar1.add(btnPauseAll);

    btnResumAll.setIcon(resourceMap.getIcon("btnResumAll.icon")); // NOI18N
    btnResumAll.setText(resourceMap.getString("btnResumAll.text")); // NOI18N
    btnResumAll.setEnabled(false);
    btnResumAll.setFocusable(false);
    btnResumAll.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnResumAll.setName("btnResumAll"); // NOI18N
    btnResumAll.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnResumAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnResumAllActionPerformed(evt);
      }
    });
    jToolBar1.add(btnResumAll);

    btnPauseOne.setIcon(resourceMap.getIcon("btnPauseOne.icon")); // NOI18N
    btnPauseOne.setText(resourceMap.getString("btnPauseOne.text")); // NOI18N
    btnPauseOne.setEnabled(false);
    btnPauseOne.setFocusable(false);
    btnPauseOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnPauseOne.setName("btnPauseOne"); // NOI18N
    btnPauseOne.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnPauseOne.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnPauseOneActionPerformed(evt);
      }
    });
    jToolBar1.add(btnPauseOne);

    btnResumOne.setIcon(resourceMap.getIcon("btnResumOne.icon")); // NOI18N
    btnResumOne.setText(resourceMap.getString("btnResumOne.text")); // NOI18N
    btnResumOne.setEnabled(false);
    btnResumOne.setFocusable(false);
    btnResumOne.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    btnResumOne.setName("btnResumOne"); // NOI18N
    btnResumOne.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    btnResumOne.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnResumOneActionPerformed(evt);
      }
    });
    jToolBar1.add(btnResumOne);

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
    jPanel1.setName("jPanel1"); // NOI18N

    jScrollPane1.setName("jScrollPane1"); // NOI18N

    tableFiles.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "ID", "File name", "Size", "Percent"
      }
    ));
    tableFiles.setName("tableFiles"); // NOI18N
    tableFiles.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        tableFilesMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(tableFiles);

    txtSaveTo.setText(resourceMap.getString("txtSaveTo.text")); // NOI18N
    txtSaveTo.setName("txtSaveTo"); // NOI18N

    jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N

    btnSelectFolder.setText(resourceMap.getString("btnSelectFolder.text")); // NOI18N
    btnSelectFolder.setName("btnSelectFolder"); // NOI18N
    btnSelectFolder.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        btnSelectFolderActionPerformed(evt);
      }
    });

    jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
    jLabel2.setName("jLabel2"); // NOI18N

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 811, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel1)
            .addGap(39, 39, 39)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2)
              .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtSaveTo, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btnSelectFolder)))))
        .addContainerGap(34, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(58, 58, 58)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(txtSaveTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(btnSelectFolder))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel2)
        .addContainerGap(204, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 855, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addContainerGap(58, Short.MAX_VALUE))
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(mainPanelLayout.createSequentialGroup()
        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    menuBar.setName("menuBar"); // NOI18N

    fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
    fileMenu.setName("fileMenu"); // NOI18N

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(filedownloader.FileDownloaderApp.class).getContext().getActionMap(FileDownloaderView.class, this);
    exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
    exitMenuItem.setName("exitMenuItem"); // NOI18N
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
    helpMenu.setName("helpMenu"); // NOI18N

    aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    statusPanel.setName("statusPanel"); // NOI18N

    statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

    statusMessageLabel.setName("statusMessageLabel"); // NOI18N

    statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

    progressBar.setName("progressBar"); // NOI18N

    javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
    statusPanel.setLayout(statusPanelLayout);
    statusPanelLayout.setHorizontalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(statusMessageLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 729, Short.MAX_VALUE)
        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(statusAnimationLabel)
        .addContainerGap())
    );
    statusPanelLayout.setVerticalGroup(
      statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(statusPanelLayout.createSequentialGroup()
        .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(statusMessageLabel)
          .addComponent(statusAnimationLabel)
          .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(3, 3, 3))
    );

    setComponent(mainPanel);
    setMenuBar(menuBar);
    setStatusBar(statusPanel);
  }// </editor-fold>//GEN-END:initComponents

    private void btnURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnURLActionPerformed
      // TODO add your handling code here:
      if(txtSaveTo.getText().isEmpty())
      {
        JOptionPane.showMessageDialog(null, "Please select folder to save file");
        return;
      }
      DialogAddURL dlg=new DialogAddURL(null, true);
      dlg.show();
      
      this.txtSaveTo.setEnabled(false);
      this.btnSelectFolder.setEnabled(false);
      // sau khi người dùng đóng dialog, sẽ lấy được danh sách các URL người dùng đưa vào
      
      this.file_list=dlg.file_list;
      
      // lấy các file này đưa lên danh sách sẵn sàng đownload
      Vector header= new Vector();
      header.add("ID");
      header.add("File name");
      header.add("Size (Kb)");
      header.add("Percent");
      //tao mot tablemodel de chua jtable
      DefaultTableModel model = new DefaultTableModel(header,0);
      tableFiles.setModel(model);       
                
      for (Integer i=0;i<this.file_list.size();i++)
      {
        Downloader dl = null;
        String strURL=(String)file_list.get(i);
        URL url=null;
        try {
          url = new URL(strURL);
        } catch (MalformedURLException ex) {
          Logger.getLogger(FileDownloaderView.class.getName()).log(Level.SEVERE, null, ex);
        }
        String filename=url.getFile();
        File f=new File(filename);
        String name=f.getName();
        name=name.replaceAll("%20", " ") ;
      
        // khỡi tạo 1 Downloader tương ứng với 1 url
        //dl=new Downloader(new URL(strURL), new File(i.toString()+".txt"));
        //dowloaders.add(dl);
        // khỡi tạo thead để thực hiện download
        DownloadThread dlthread=new DownloadThread();
        dlthread.strURL=strURL;
        dlthread.file_name_to_save=txtSaveTo.getText();
        //dlthread.start();// chờ người dùng chọn nút start thì mới download
        dlthread.tableFile=this.tableFiles;
        dlthread.row_index=i;
        this.dowloaders.add(dlthread);
        // lưu danh sách này vào 
        Vector row = new Vector();

         //String messageId=messages[i].message_id;//nếu mail được lưu trong local thì giá tri nay = null
         //String id=i.toString();
         //String filename="xxxx"+i.toString();//@ todo:
         //Integer filesize_temp="size";
         //String filesize="Size";
         //Integer percent="Percent";
         //String percent="0%";
         row.add(i.toString());
         row.add(name);
         row.add(f.length());
         row.add("0%");
         


          model.addRow(row);
            
            
           
        
      }
    }//GEN-LAST:event_btnURLActionPerformed

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
      // TODO add your handling code here:
      if(this.dowloaders.isEmpty())
      {
        JOptionPane.showMessageDialog(null, "No file to start. Please add some URLs");
      }
      btnPauseAll.setEnabled(true);
      // duyệt qua danh sách các downloader
      for(int i=0;i<this.dowloaders.size();i++)
      {
        dowloaders.get(i).start();
        // thực hiện cập nhật lại giá trị của table tương ứng với thread này
        
      }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnSelectFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectFolderActionPerformed
      // TODO add your handling code here:
      int result;
      JFileChooser chooser;    
      chooser = new JFileChooser(); 
      chooser.setCurrentDirectory(new java.io.File("."));
      chooser.setDialogTitle("Select folder to save file");
      chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      //
      // disable the "All files" option.
      //
      chooser.setAcceptAllFileFilterUsed(false);
      //    
      if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
//        System.out.println("getCurrentDirectory(): " 
//           +  chooser.getCurrentDirectory());
//        System.out.println("getSelectedFile() : " 
//           +  chooser.getSelectedFile());
//        }
        StringBuffer bf=new StringBuffer(chooser.getCurrentDirectory().toString());
        bf=bf.append("/");
        txtSaveTo.setText(bf.toString());
      }
      else {
        System.out.println("No Selection ");
        }
    
    }//GEN-LAST:event_btnSelectFolderActionPerformed

    // Stop all
    private void btnPauseAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseAllActionPerformed
      // TODO add your handling code here:
      for(int i=0;i<this.dowloaders.size();i++)
      {
        dowloaders.get(i).dl.pause();
        // thực hiện cập nhật lại giá trị của table tương ứng với thread này
        
      }
      this.btnPauseAll.setEnabled(false);
      btnResumAll.setEnabled(true);
    }//GEN-LAST:event_btnPauseAllActionPerformed

    private void btnResumAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumAllActionPerformed
      // TODO add your handling code here:
       // TODO add your handling code here:
      for(int i=0;i<this.dowloaders.size();i++)
      {
        dowloaders.get(i).dl.resume();
        // thực hiện cập nhật lại giá trị của table tương ứng với thread này
        
      }
      this.btnPauseAll.setEnabled(true);
      this.btnResumAll.setEnabled(false);
      
    }//GEN-LAST:event_btnResumAllActionPerformed

    private void tableFilesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableFilesMouseClicked
      // TODO add your handling code here:
      Point p=evt.getPoint();
      
      this.row_selected_index=tableFiles.rowAtPoint(p);
      if(row_selected_index!=-1)//neu dong nay co gia tri
      {
        // hiển thị trạng thái tương ứng của url hiện tại
        DownloadThread dlThread=this.dowloaders.get(row_selected_index);
        Downloader dl=dlThread.dl;
        if(dl.isRunning()==true)
        {
          btnPauseOne.setEnabled(true);
          
        }
        else
        {
          btnResumOne.setEnabled(true);
        }
      }
    }//GEN-LAST:event_tableFilesMouseClicked

    private void btnPauseOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPauseOneActionPerformed
      // TODO add your handling code here:
      DownloadThread dlThread=this.dowloaders.get(row_selected_index);
      Downloader dl=dlThread.dl;
      dl.pause();
      btnPauseOne.setEnabled(false);
      btnResumOne.setEnabled(true);
    }//GEN-LAST:event_btnPauseOneActionPerformed

    private void btnResumOneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumOneActionPerformed
      // TODO add your handling code here:
      DownloadThread dlThread=this.dowloaders.get(row_selected_index);
      Downloader dl=dlThread.dl;
      dl.resume();
      btnResumOne.setEnabled(false);
      btnPauseOne.setEnabled(true);
    }//GEN-LAST:event_btnResumOneActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton btnPauseAll;
  private javax.swing.JButton btnPauseOne;
  private javax.swing.JButton btnResumAll;
  private javax.swing.JButton btnResumOne;
  private javax.swing.JButton btnSelectFolder;
  private javax.swing.JButton btnStart;
  private javax.swing.JButton btnURL;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JToolBar jToolBar1;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JProgressBar progressBar;
  private javax.swing.JLabel statusAnimationLabel;
  private javax.swing.JLabel statusMessageLabel;
  private javax.swing.JPanel statusPanel;
  private javax.swing.JTable tableFiles;
  private javax.swing.JTextField txtSaveTo;
  // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
