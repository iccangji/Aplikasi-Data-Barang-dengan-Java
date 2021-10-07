/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author Muh. Nur Iksan
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import net.proteanit.sql.DbUtils;
import koneksi.Koneksi;
import pdf.Report;

public class Main extends javax.swing.JFrame {


   public Connection konek = Koneksi.getKoneksi();
   public PreparedStatement pst;
   public ResultSet rst;

    /**
     * Creates new form main
     */
    public Main() {
        initComponents();
        showData();
        Timer timer = new Timer(500, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        timeNow();
      }
    });
    timer.setRepeats(true);
    timer.setCoalesce(true);
    timer.setInitialDelay(0);
    timer.start();
    }
        
    public boolean  validCheck(){
        if (txtNim.getText().equals("") ||
            txtNama.getText().equals("") ||
            txtAlamat.getText().equals("") ||
            txtJk.getText().equals("") ||
            txtHobi.getSelectedItem().toString().trim().isEmpty( ) ){
        return false;
    }
    return true;

    }
    public boolean instinctCheck(){
        try {
        String sql = "select nim,nama,alamat,jk,hobi from mahasiswa where nim='" + txtNim.getText() + "'";
        Statement st = konek.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = st.executeQuery(sql);
        if (rs.first()) {
            // gagal insert
            System.out.println("gagal insert");
            return false;
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "chek kode eror=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex);
    }
    return true;
    }
    public void showData(){
       try {
        String sql="select nim, nama, alamat, jk,hobi from mahasiswa";
        pst=konek.prepareStatement(sql);
        rst=pst.executeQuery();
        table.setModel(DbUtils.resultSetToTableModel(rst));
       } catch (Exception e){ JOptionPane.showMessageDialog(null, e);}        
    }
     public void saveData(){
        try {

            String query = "INSERT INTO mahasiswa(nim,nama,alamat,jk,hobi) VALUES(?,?,?,?,?)";
            PreparedStatement prepare = konek.prepareStatement(query);


            
            prepare.setString(1, txtNim.getText());
            prepare.setString(2, txtNama.getText());
            prepare.setString(3, txtAlamat.getText());
            prepare.setString(4, txtJk.getText());
            prepare.setString(5, txtHobi.getSelectedItem().toString().trim());
            


            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            prepare.close();

            //panggil table / refresh

            showData();
             clearInput();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
        }
        
     }
     public void clearInput(){
            txtNim.setText("");
            txtNama.setText("");
            txtAlamat.setText("");
            txtJk.setText("");
            txtHobi.setSelectedItem("");
            txtNim.setEnabled(true);
        }
     public void searchData(String key){
        try{
            Object[] judul_kolom = {"nim", "nama", "alamat", "jk", "hobi"};
            DefaultTableModel tabModel = new DefaultTableModel(null,judul_kolom);
            table.setModel(tabModel);
            
            Statement stt=konek.createStatement();
            tabModel.getDataVector().removeAllElements();
            
            ResultSet RsProduk=stt.executeQuery("SELECT * from mahasiswa WHERE nim LIKE '%"+key+"%' OR nama LIKE '%"+key+"%' ");  
            while(RsProduk.next()){
                Object[] data={
                    RsProduk.getString("nim"),
                    RsProduk.getString("nama"),
                    RsProduk.getString("alamat"),
                    RsProduk.getString("jk"),
                    RsProduk.getString("hobi")      
                };
               tabModel.addRow(data);
            }                
        } catch (Exception ex) {
        System.err.println(ex.getMessage());
        }
    }
     public void editData(){
      
    try {
            String query = "UPDATE mahasiswa SET nama='" + txtNama.getText() + "', alamat='" + txtAlamat.getText() + "', jk='" + txtJk.getText() + "', hobi='" + 
                    txtHobi.getSelectedItem() +  "' WHERE nim='" + txtNim.getText() + "'";
            PreparedStatement prepare = konek.prepareStatement(query);
            prepare.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            prepare.close();

            //panggil table / refresh
            showData();
            clearInput();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex);
    } 
    }
     public void deleteData(){
            try {
                String query = "DELETE FROM mahasiswa WHERE nim='"+txtNim.getText()+"'";
                PreparedStatement prepare = konek.prepareStatement(query);
                prepare.execute();
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus", "Pesan", JOptionPane.INFORMATION_MESSAGE);
                prepare.close();

                //panggil table / refresh
                showData();
                clearInput();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Data gagal dihapus=" + ex, "Pesan", JOptionPane.ERROR_MESSAGE);
                System.out.println(ex);

    }
    }
     public void timeNow(){
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
         SimpleDateFormat clockFormat = new SimpleDateFormat("HH:mm:ss");
         txtDate.setText(dateFormat.format(Calendar.getInstance().getTime()));
         txtClock.setText(clockFormat.format(Calendar.getInstance().getTime()));
         txtClock.setEnabled(false);
         txtDate.setEnabled(false);
     }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNim = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAlamat = new javax.swing.JTextArea();
        txtJk = new javax.swing.JTextField();
        txtHobi = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnBaru = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnReport = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        txtDate = new javax.swing.JTextField();
        txtClock = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nim");

        jLabel2.setText("Nama");

        jLabel3.setText("Alamat");

        jLabel4.setText("Jenis Kelamin");

        jLabel5.setText("Hobi");

        txtAlamat.setColumns(20);
        txtAlamat.setRows(5);
        jScrollPane1.setViewportView(txtAlamat);

        txtHobi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Membaca", "Game", "Belajar", "Renang" }));

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnBaru.setText("Baru");
        btnBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBaruActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnReport.setText("Report PDF");
        btnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportActionPerformed(evt);
            }
        });

        txtCari.setText("Cari...");
        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCariFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCariFocusLost(evt);
            }
        });
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCariKeyTyped(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nim", "Nama", "Alamat", "Jenis Kelamin", "Hobi"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(table);

        txtDate.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        txtDate.setText("jTextField1");
        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        txtClock.setFont(new java.awt.Font("Agency FB", 1, 16)); // NOI18N
        txtClock.setText("jTextField2");
        txtClock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNim)
                            .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(txtJk, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(txtHobi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(279, 279, 279)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUbah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBaru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnReport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtClock, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCari)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)))
                .addGap(71, 71, 71))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtJk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4)))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHobi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(btnSimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUbah)
                        .addGap(18, 18, 18)
                        .addComponent(btnBaru)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapus)
                        .addGap(18, 18, 18)
                        .addComponent(btnReport))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed

            if (validCheck()) {
                editData();
            } else {
                JOptionPane.showMessageDialog(null,  "Silahkan Lengkapi Data", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }         
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
          if (validCheck()) {
            if (instinctCheck()) {
                saveData();
            } else {
                JOptionPane.showMessageDialog(null, "Nim " + txtNim.getText() + " sudah ada. Silahkan isi kode lain.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Lengkapi data", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked

try {
         
                txtNim.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                txtNama.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                txtAlamat.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                txtJk.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                txtHobi.setSelectedItem(table.getValueAt(table.getSelectedRow(), 4).toString());
                txtNim.setEnabled(false);
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "get row table", "Pesan", JOptionPane.ERROR_MESSAGE);
        System.out.println(ex);
    }

    }//GEN-LAST:event_tableMouseClicked
    
    private void btnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportActionPerformed
        String pdfFilename = "C:\\Users\\LENOVO\\Documents\\kmmi\\DataMahasiswa.pdf";
                int n = JOptionPane.showConfirmDialog(
                        null,
                        "Apakah yakin ingin cetak report pada path "+pdfFilename ,
                        "",
                        JOptionPane.YES_NO_OPTION);

                if(n == JOptionPane.YES_OPTION)
                {
                    Report ReportPdf = new Report();
                    ReportPdf.createPDF(pdfFilename, table);
                }        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusLost
     txtCari.setText("Cari...");   
    }//GEN-LAST:event_txtCariFocusLost

    private void txtCariFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCariFocusGained
        if(txtCari.getText().equals("Cari..."))
                   {txtCari.setText("");
               }
    }//GEN-LAST:event_txtCariFocusGained

    private void txtCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyTyped
        String key=txtCari.getText();
               System.out.println(key);  

               if(key!=""){
                   searchData(key);
               }else{
                   showData();
               }
    }//GEN-LAST:event_txtCariKeyTyped

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (validCheck()) {
            int n = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah yakin ingin menghapus kode "+txtNim.getText() ,
                    "",
                    JOptionPane.YES_NO_OPTION);

            if(n == JOptionPane.YES_OPTION)
            {
                deleteData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Lengkapi data", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBaruActionPerformed
        clearInput();
    }//GEN-LAST:event_btnBaruActionPerformed

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void txtClockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClockActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtClockActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnReport;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txtAlamat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtClock;
    private javax.swing.JTextField txtDate;
    private javax.swing.JComboBox<String> txtHobi;
    private javax.swing.JTextField txtJk;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNim;
    // End of variables declaration//GEN-END:variables
}
