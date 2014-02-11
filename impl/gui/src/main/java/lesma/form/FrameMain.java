/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lesma.form;

import java.awt.BorderLayout;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.io.File;
import java.lang.reflect.Method;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import lesma.model.Project;
import lesma.model.Workspace;
import lesma.util.Data;

/**
 *
 * @author vanderson
 */
@SuppressWarnings("all")
public class FrameMain extends javax.swing.JFrame {
    
    private static FrameMain instance = new FrameMain();
    
    /**
     * Creates new form FrameCronos
     */
    private FrameMain() {
        setExtendedState(MAXIMIZED_BOTH);  
        initComponents();  
        this.setExtendedState(FrameMain.MAXIMIZED_BOTH);
        
    }
    
    public static FrameMain getInstance(){
        return instance;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        lbMessage = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmenuAgents = new javax.swing.JMenu();
        mnNewProject = new javax.swing.JMenuItem();
        mnOpenProject = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        btReload = new javax.swing.JMenuItem();
        mnExit = new javax.swing.JMenuItem();
        jmenuAbout = new javax.swing.JMenu();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Learning Environment for Systems Multi Agent");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lbMessage.setText("message");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbMessage, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lbMessage))
        );

        jmenuAgents.setText("Project");

        mnNewProject.setText("New");
        mnNewProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnNewProjectActionPerformed(evt);
            }
        });
        jmenuAgents.add(mnNewProject);

        mnOpenProject.setText("Open");
        mnOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnOpenProjectActionPerformed(evt);
            }
        });
        jmenuAgents.add(mnOpenProject);
        jmenuAgents.add(jSeparator3);

        btReload.setText("Reload");
        btReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReloadActionPerformed(evt);
            }
        });
        jmenuAgents.add(btReload);

        mnExit.setText("Exit");
        mnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnExitActionPerformed(evt);
            }
        });
        jmenuAgents.add(mnExit);

        jMenuBar1.add(jmenuAgents);

        jmenuAbout.setText("About");
        jMenuBar1.add(jmenuAbout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 288, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnNewProjectActionPerformed
        message("New project...");
        FrameNewProject frame = FrameNewProject.getInstance();        
        frame.load();
        frame.clean();
        frame.setVisible(true);        
    }//GEN-LAST:event_mnNewProjectActionPerformed

    private void mnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnExitActionPerformed
       System.exit(0);
    }//GEN-LAST:event_mnExitActionPerformed

    private void mnOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnOpenProjectActionPerformed
        message("Open project...");
        FrameNewProject frame = FrameNewProject.getInstance();
        frame.setTitle("Open project");
        FileNameExtensionFilter filterExt = new FileNameExtensionFilter("Project Lesma file (.lma)", "lma"); 
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(filterExt);
        fileChooser.setCurrentDirectory(new File(Workspace.FOLDER_PROJECT));
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();                 
            FrameMain.message("open file: " +  file.getAbsolutePath());                    
            Project project = Data.fileToProject(file);            
            project.setSaveIn(file.getAbsolutePath());
            frame.updateScreen(project);
        }        
        frame.setVisible(true);        
    }//GEN-LAST:event_mnOpenProjectActionPerformed

    private void btReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReloadActionPerformed
        lesma.Lesma.main(null);   
        System.exit(0);
    }//GEN-LAST:event_btReloadActionPerformed

     private void log(String message) {
     }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrameMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btReload;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenu jmenuAbout;
    private javax.swing.JMenu jmenuAgents;
    private javax.swing.JLabel lbMessage;
    private javax.swing.JMenuItem mnExit;
    private javax.swing.JMenuItem mnNewProject;
    private javax.swing.JMenuItem mnOpenProject;
    // End of variables declaration//GEN-END:variables

    public static void message(String message) {
        instance.lbMessage.setText(message);
    }


}
