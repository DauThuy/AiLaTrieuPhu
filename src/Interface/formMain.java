package Interface;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class formMain extends javax.swing.JFrame {
	private javax.swing.JButton btnBatDau;
    private javax.swing.JButton btnHuongdan;
    private javax.swing.JButton btnThoat;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelStart;
    private javax.swing.JPanel jPanel1;
    
    public formMain() {
        initComponents();
    }
    
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel(); //khung bao quanh
        jLabelStart = new javax.swing.JLabel();
        btnBatDau = new javax.swing.JButton();
        btnThoat = new javax.swing.JButton();
        btnHuongdan = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jLabelStart.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabelStart.setText("Start");
        jPanel1.add(jLabelStart);  //them label Start vao panel
        jLabelStart.setBounds(310, 590, 34, 30);

        btnBatDau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/Play.png"))); // NOI18N
        btnBatDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatDauActionPerformed(evt);
            }
        });
        jPanel1.add(btnBatDau);   //them icon button start vao panel
        btnBatDau.setBounds(280, 540, 90, 80);

        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/close.png"))); // NOI18N
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });
        jPanel1.add(btnThoat);
        btnThoat.setBounds(580, 0, 50, 50);

        btnHuongdan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/ques.png"))); // NOI18N
        btnHuongdan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuongdanActionPerformed(evt);
            }
        });
        jPanel1.add(btnHuongdan);
        btnHuongdan.setBounds(0, 0, 60, 50);

        //background-image
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/bg1.jpg"))); // NOI18N
       // jLabel2.setText("A");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(65, 0, 730, 620);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatDauActionPerformed
        try {

            formPlay play = new formPlay();
            play.setVisible(true);

        } catch (SQLException ex) {
            Logger.getLogger(formMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnBatDauActionPerformed

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnHuongdanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuongdanActionPerformed
//     play=qt.loadMusic("hoi");
        //play.play();
        JOptionPane.showMessageDialog(null, "Có tất cả 15 câu hỏi, và 3 cột mốc: 5, 10, 15 mà bạn cần vượt qua", "Thông báo", 1);
        JOptionPane.showMessageDialog(null, "Bạn có 3 quyền trợ giúp: 50-50, hỏi ý kiến khán giả trong trường quay và đổi câu hỏi");
        JOptionPane.showMessageDialog(null, "Bạn có thể dừng cuộc chơi bất cứ lúc nào");
    }//GEN-LAST:event_btnHuongdanActionPerformed

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
            java.util.logging.Logger.getLogger(formMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        formMain main = new formMain();
        main.setVisible(true);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
