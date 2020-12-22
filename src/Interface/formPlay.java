/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Process.Question;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import LAN_Client.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import ykien.yKienC;
import ykien.ykienB;
import ykien.ykienD;
import javax.swing.Timer;
import ykien.ykienAA;

/**
 *
 * @author zduyt
 */
@SuppressWarnings("serial")
public class formPlay extends javax.swing.JFrame {

    private final String mucthuongz[] = new String[]{"200", "400", "600", "1,000", "2,000", "3,000", "6,000", "10,000", "14,000", "22,000", "30,000", "40,000", "60,000", "85,000", "150,000"};
    int demquestion = 1;
    String chon = null;
    private final Question qt = new Question();
    String anw = null;
    String a = null;
    String b = null;
    String c = null;
    String d = null;
    String dapan = null;
    String chona = "A";
    String chonb = "B";
    String chonc = "C";
    String chond = "D";
    int maqt = 0;
    ArrayList list = new ArrayList();
    public boolean checkrd = true;
    int lastquest = 16;
    String maqttest = null;
    int dem = 0;
    static Thread thread = new Thread();
    public Timer t;
    public int count = 0;
    public int tongtime = 60;
    public int hanchot = 0;
    Client cl= new Client();
    String test=cl.getUser();
    private DataOutputStream dos;
    private DataInputStream dis;
    public Socket client;
    private static final int PORT = 6969;

	
    String tim;
   

    /**
     * Creates new form frmPlay
     */
    public formPlay() throws SQLException {
       
        initComponents();
        //System.out.println("AAA");
        lblUser.setText(cl.getUser());
        go();
        
        getMaqt();
        ShowData();
        ShowQuestbyMaqt();
        timecouter();
        t.start();

        lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc1.png")));

    }
 private void go() {
        try {
            client = new Socket("localhost", PORT);
            dos = new DataOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());

            //client.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối, xem lại dây mạng đi hoặc room chưa mở.", "Message Dialog", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }
    }
    public void loadQuestion() {

    }

    public void timecouter() {
        count = 0;

        t = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                hanchot = tongtime - count;
                lbldemthoigian.setText(hanchot + "");
                tim = lbldemthoigian.getText();

                if (hanchot == 0) {
                    JOptionPane.showMessageDialog(null, "Hết giờ","Thông báo",1);
                    btnAnswerA.setEnabled(false);
                    btnAnswerB.setEnabled(false);
                    btnAnswerC.setEnabled(false);
                    btnAnswerD.setEnabled(false);
                    checkketqua();
                }
            }
        });

        // t.start();
    }
    public void timereset(){
        if(t.isRunning()){
            t.restart();
        }
        
    }

    public void RandomMaqt() throws SQLException {
        ResultSet rs = qt.RandomMaQuestion();
        rs.next();
        maqttest = rs.getString(1);
    }

    public void checkrandomMaqt() {
        if (!list.contains(maqttest)) {
            checkrd = true;
            return;
        }
        if (list.contains(maqttest)) {
            checkrd = false;
            return;
        }
    }

    public void addMaqtvolist() throws SQLException {
        if (checkrd == true) {
            list.add(maqttest);
        }
        if (checkrd == false) {
            for (int i = 0; i < 10; i++) {
                RandomMaqt();
                if (!list.contains(maqttest)) {
                    list.add(maqttest);
                    return;
                }

            }
        }
    }

    public void getMaqt() throws SQLException {
        
        // edit for i
        for (int i = 0; i < 1; i++) {
            RandomMaqt();
            checkrandomMaqt();
            addMaqtvolist();
        }
    }

    public void ShowQuestbyMaqt() throws SQLException {
       
        String change = String.valueOf(list.get(dem));
        maqt = Integer.parseInt(change);
        try {
            ResultSet rs = qt.ShowQuestbyRandomMaQuestion(maqt);
            rs.next();
            txtQuestion.setText(rs.getString("Question"));
            btnAnswerA.setText(rs.getString("AnswerA"));
            a = btnAnswerA.getText();
            btnAnswerB.setText(rs.getString("AnswerB"));
            b = btnAnswerB.getText();
            btnAnswerC.setText(rs.getString("AnswerC"));
            c = btnAnswerC.getText();
            btnAnswerD.setText(rs.getString("AnswerD"));
            d = btnAnswerD.getText();
            lbldapan.setText(rs.getString("Answer"));
            dapan = lbldapan.getText();
            lblMa.setText(rs.getString("MaQT"));

        } catch (SQLException e) {
            Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, e);
        }
        //checkRandom();
        checkrandomMaqt();
        addMaqtvolist();
        //addMaqt();
        dem++;
    }

    public void ShowData() throws SQLException {
        ResultSet rs = qt.ShowQuestion();

        while (rs.next()) {
            txtQuestion.setText(rs.getString("Question"));
            btnAnswerA.setText(rs.getString("AnswerA"));
            a = btnAnswerA.getText();
            btnAnswerB.setText(rs.getString("AnswerB"));
            b = btnAnswerB.getText();
            btnAnswerC.setText(rs.getString("AnswerC"));
            c = btnAnswerC.getText();
            btnAnswerD.setText(rs.getString("AnswerD"));
            d = btnAnswerD.getText();
            lbldapan.setText(rs.getString("Answer"));
            dapan = lbldapan.getText();
            lblMa.setText(rs.getString("MaQT"));

        }

    }

    public void checkketqua() {
        if (demquestion == 1) {
            JOptionPane.showMessageDialog(null, "Bạn không nhận được tiền thưởng từ chương trình", "thông báo", 1);
        } else {
            if (demquestion < 5) {
                JOptionPane.showMessageDialog(null, "Bạn nhận được 200.000đ tiền thưởng từ chương trình", "thông báo", 1);
            }
            if (demquestion >= 5 && demquestion < 10) {
                JOptionPane.showMessageDialog(null, "Bạn nhận được 2.000.000đ tiền thưởng từ chương trình", "thông báo", 1);
            }
            if (demquestion >= 10 && demquestion < 15) {
                JOptionPane.showMessageDialog(null, "Bạn nhận được 20.000.000đ tiền thưởng từ chương trình", "thông báo", 1);
            }

        }
        this.dispose();
    }

    public void checkdapan() throws SQLException {
        if (chon.equals(dapan)) {

            demquestion++;

            lblthuong.setText(mucthuongz[demquestion - 2]);
            String thuong = lblthuong.getText();
            if (demquestion < 16) { //ShowData();
                ShowQuestbyMaqt();
                if(t.isRunning())
                {   t.restart();
                    
                }
                timecouter();
            } else {
                JOptionPane.showMessageDialog(null, "Chúc mừng bạn đã chiến thắng, và nhận được 150 triệu từ chương trình", "Thông báo", 1);
                this.dispose();
            }

            try {
                if (thuong == "200") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc2.png")));
                } else if (thuong == "400") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc3.png")));
                } else if (thuong == "600") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc4.png")));
                } else if (thuong == "1,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc5.png")));
                } else if (thuong == "2,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc6.png")));
                } else if (thuong == "3,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc7.png")));
                } else if (thuong == "6,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc8.png")));
                } else if (thuong == "10,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc9.png")));
                } else if (thuong == "14,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc10.png")));
                } else if (thuong == "22,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc11.png")));
                } else if (thuong == "30,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc12.png")));
                } else if (thuong == "40,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc13.png")));
                } else if (thuong == "60,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc14.png")));
                } else if (thuong == "85,000") {
                    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc15.png")));
                    //} else if (thuong=="150,000") {
                    //    lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/muc15.png")));
                }
            } catch (Exception e) {
            }

        } else {

            checkketqua();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbltienthuong = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblUser = new javax.swing.JLabel();
        lblIdQuestion = new javax.swing.JLabel();
        lbldapan = new javax.swing.JLabel();
        lblthuong = new javax.swing.JLabel();
        lblMa = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btn5050 = new javax.swing.JButton();
        btnYkien = new javax.swing.JButton();
        btnSwap = new javax.swing.JButton();
        btnDungcuocchoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtQuestion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        btnAnswerA = new javax.swing.JButton();
        btnAnswerB = new javax.swing.JButton();
        btnAnswerC = new javax.swing.JButton();
        btnAnswerD = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lbltien = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lbldemthoigian = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);

        //jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("chức năng"));

        lblUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUser.setText("Chúc bạn may mắn.. !!");
        jPanel1.add(lblUser);

        lblIdQuestion.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lblIdQuestion.setText(".");
        jPanel1.add(lblIdQuestion);

        lbldapan.setFont(new java.awt.Font("Tahoma", 0, 7)); // NOI18N
        lbldapan.setText(".");
        jPanel1.add(lbldapan);

        lblthuong.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblthuong.setText(".");
        jPanel1.add(lblthuong);

        lblMa.setFont(new java.awt.Font("Tahoma", 0, 5)); // NOI18N
        lblMa.setText(".");
        jPanel1.add(lblMa);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quyền trợ giúp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14), new java.awt.Color(0, 0, 204))); // NOI18N

        btn5050.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/50.png"))); // NOI18N
        btn5050.setToolTipText("");
        btn5050.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5050ActionPerformed(evt);
            }
        });
        jPanel2.add(btn5050);

        btnYkien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/khangia.png"))); // NOI18N
        btnYkien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnYkienActionPerformed(evt);
            }
        });
        jPanel2.add(btnYkien);

        btnSwap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/doicau.png"))); // NOI18N
        btnSwap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSwapActionPerformed(evt);
            }
        });
        jPanel2.add(btnSwap);

        btnDungcuocchoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/stop.png"))); // NOI18N
        btnDungcuocchoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDungcuocchoiActionPerformed(evt);
            }
        });
        jPanel2.add(btnDungcuocchoi);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(null);

        txtQuestion.setEditable(false);
        txtQuestion.setBackground(new java.awt.Color(102, 204, 255));
        txtQuestion.setColumns(10);
        txtQuestion.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        txtQuestion.setLineWrap(true);
        txtQuestion.setRows(3);
        txtQuestion.setTabSize(10);
        txtQuestion.setBorder(null);
        jScrollPane1.setViewportView(txtQuestion);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(80, 30, 450, 70);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/khungcautl.jpg"))); // NOI18N
        jLabel2.setText("B");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(0, -20, 590, 290);

        btnAnswerA.setBackground(new java.awt.Color(0, 204, 255));
        btnAnswerA.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btnAnswerA.setToolTipText("AA");
        btnAnswerA.setBorder(null);
        btnAnswerA.setBorderPainted(false);
        btnAnswerA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnswerAMouseClicked(evt);
            }
        });
        btnAnswerA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnswerAActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnswerA);
        btnAnswerA.setBounds(50, 130, 210, 40);

        btnAnswerB.setBackground(new java.awt.Color(0, 204, 255));
        btnAnswerB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAnswerB.setForeground(new java.awt.Color(51, 51, 51));
        btnAnswerB.setBorder(null);
        btnAnswerB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnswerBbtnAnswerAMouseClicked(evt);
            }
        });
        btnAnswerB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnswerBActionPerformed(evt);
            }
        });
        jPanel3.add(btnAnswerB);
        btnAnswerB.setBounds(340, 130, 210, 41);

        btnAnswerC.setBackground(new java.awt.Color(51, 204, 255));
        btnAnswerC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAnswerC.setBorder(null);
        btnAnswerC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnswerCbtnAnswerAMouseClicked(evt);
            }
        });
        jPanel3.add(btnAnswerC);
        btnAnswerC.setBounds(50, 190, 210, 40);

        btnAnswerD.setBackground(new java.awt.Color(51, 204, 255));
        btnAnswerD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAnswerD.setBorder(null);
        btnAnswerD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAnswerDbtnAnswerAMouseClicked(evt);
            }
        });
        jPanel3.add(btnAnswerD);
        btnAnswerD.setBounds(340, 190, 210, 40);
        jPanel3.add(jLabel1);
        jLabel1.setBounds(40, 110, 610, 140);

        jLabel3.setText("jLabel3");
        jPanel3.add(jLabel3);
        jLabel3.setBounds(310, 140, 34, 14);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "TIỀN THƯỞNG", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(204, 0, 0))); // NOI18N

        lbltien.setBackground(new java.awt.Color(255, 255, 255));
        lbltien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Untitled.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbltien)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbltien, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Đồng hồ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(0, 0, 153))); // NOI18N

        lbldemthoigian.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbldemthoigian.setText("60");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbldemthoigian)
                .addGap(45, 45, 45))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbldemthoigian, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 264, Short.MAX_VALUE)
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/button/MC.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 617, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(lbltienthuong)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(lbltienthuong, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnswerBbtnAnswerAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnswerBbtnAnswerAMouseClicked
        chon = "B";

        String da = this.btnAnswerC.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn chọn câu trả lời là B?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chonb.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Đáp án đúng", "thông báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Đáp án của bạn sai!", "thông báo", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnAnswerBbtnAnswerAMouseClicked

    private void btnAnswerCbtnAnswerAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnswerCbtnAnswerAMouseClicked
        chon = "C";

        String da = this.btnAnswerC.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn chọn câu trả lời là C ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chonc.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Đáp án đúng", "thông báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Đáp án của bạn sai!", "thông báo", 1);

            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnAnswerCbtnAnswerAMouseClicked

    private void btnAnswerDbtnAnswerAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnswerDbtnAnswerAMouseClicked
        chon = "D";

        String da = this.btnAnswerD.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn chọn câu trả lời là D ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chond.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Đáp án đúng", "thong bao", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Đáp án của bạn sai!", "thong bao", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnAnswerDbtnAnswerAMouseClicked

    private void btnAnswerAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAnswerAMouseClicked
        chon = "A";

        String da = this.btnAnswerA.getText();
        int mh = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn chọn câu trả lời là A ?", "Xác nhận ", JOptionPane.YES_OPTION);
        if (mh == JOptionPane.YES_OPTION) {

            if (chona.equals(dapan)) {
                JOptionPane.showMessageDialog(null, "Đáp án đúng", "thông báo", 1);
            } else {
                JOptionPane.showMessageDialog(null, "Đáp án của bạn sai!", "thông báo", 1);
            }
            try {
                checkdapan();
            } catch (SQLException ex) {
                Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_btnAnswerAMouseClicked

    private void btnAnswerAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnswerAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnswerAActionPerformed

    private void btn5050ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5050ActionPerformed
        int deman = 0;

        if (!chona.equals(dapan) && deman < 2) {
            btnAnswerA.setText("");
            deman++;
        }
        if (!chonb.equals(dapan) && deman < 2) {
            btnAnswerB.setText("");
            deman++;
        }
        if (!chonc.equals(dapan) && deman < 2) {
            btnAnswerC.setText("");
            deman++;
        }
        if (!chond.equals(dapan) && deman < 2) {
            btnAnswerC.setText("");
            deman++;
        }
        btn5050.setEnabled(false);

    }//GEN-LAST:event_btn5050ActionPerformed

    private void btnSwapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSwapActionPerformed
        if (demquestion < 16) {
            int hoi = JOptionPane.showConfirmDialog(null, "Bạn muốn sử dụng quyền trợ giúp : Đổi câu hỏi ?", "Nhắc nhở", JOptionPane.YES_NO_OPTION);
            if (hoi == JOptionPane.YES_OPTION) {
                try {
                    ShowQuestbyMaqt();
                    if(t.isRunning())
                {   t.restart();
                    
                }
                timecouter();
                } catch (SQLException ex) {
                    Logger.getLogger(formPlay.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            btnSwap.setEnabled(false);
        }
        if (demquestion == 16) {
            JOptionPane.showMessageDialog(null, "Bạn không thể dùng quyền trợ giúp này ở câu cuối cùng", "Thông báo", 1);
        }
    }//GEN-LAST:event_btnSwapActionPerformed

    private void btnYkienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnYkienActionPerformed
        if(chona.equals(dapan)){
            ykien.ykienAA yk = new ykienAA();
            yk.setVisible(true);
            btnYkien.setEnabled(false);
        }
        
        if (chonb.equals(dapan)) {
            ykien.ykienB yk = new ykienB();
            yk.setVisible(true);
            btnYkien.setEnabled(false);
        }
        if (chonc.equals(dapan)) {
            ykien.yKienC yk = new yKienC();
            yk.setVisible(true);
            btnYkien.setEnabled(false);
        }
        if (chond.equals(dapan)) {
            ykien.ykienD yk = new ykienD();
            yk.setVisible(true);
            btnYkien.setEnabled(false);
        }
    }//GEN-LAST:event_btnYkienActionPerformed

    private void btnDungcuocchoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDungcuocchoiActionPerformed
        int nhacnho = JOptionPane.showConfirmDialog(null, "Bạn muốn dừng cuộc chơi?", "Nhắc nhở", JOptionPane.YES_OPTION);
        if (nhacnho == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Bạn nhận được: " + lblthuong.getText() + ".000đ tiền thưởng của chương trình", "thông báo", 1);
            this.dispose();
        }
    }//GEN-LAST:event_btnDungcuocchoiActionPerformed

    private void btnAnswerBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnswerBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnswerBActionPerformed

    public static void main(String args[]) throws SQLException {
        formPlay play = new formPlay();
        play.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn5050;
    private javax.swing.JButton btnAnswerA;
    private javax.swing.JButton btnAnswerB;
    private javax.swing.JButton btnAnswerC;
    private javax.swing.JButton btnAnswerD;
    private javax.swing.JButton btnDungcuocchoi;
    private javax.swing.JButton btnSwap;
    private javax.swing.JButton btnYkien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblIdQuestion;
    private javax.swing.JLabel lblMa;
    private javax.swing.JLabel lblUser;
    private javax.swing.JLabel lbldapan;
    private javax.swing.JLabel lbldemthoigian;
    private javax.swing.JLabel lblthuong;
    private javax.swing.JLabel lbltien;
    private javax.swing.JLabel lbltienthuong;
    private javax.swing.JTextArea txtQuestion;
    // End of variables declaration//GEN-END:variables

    private void showmaqt() throws SQLException {
        ResultSet rs = qt.RandomMaQuestion();
        while (rs.next()) {
            lblIdQuestion.setText(rs.getString("MaQT"));
            // maqt = lblIdQuestion.getText();
        }
    }

    public boolean checkRandom() throws SQLException {
        showmaqt();
        if (!list.contains(maqt)) {
            checkrd = true;
        } else {
            checkrd = false;
        }
        return checkrd;
    }

    public void xuat() throws SQLException {
        do {
            if (checkRandom() == true) {
                ShowData();
            } else {
                checkRandom();
            }
        } while (checkRandom() == false);
    }

    public void addMaqt() throws SQLException {
        if (checkrd == true) {
            list.add(maqt);
            ShowData();

        } else {
            checkRandom();
        }

    }

    public void getId() throws SQLException {
        for (int i = 0; i < 20; i++) {
            showmaqt();
            checkRandom();
            addMaqt();
        }
    }

}
