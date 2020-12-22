/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN_Client;

import Interface.formMain;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author zduyt
 */
public class Client extends JFrame implements ActionListener {
	private JButton send, exit, login;
	private JPanel p_login, p_chat;
	public JTextField nick, nick1, message;
	private JTextArea msg, online;
	public String user;
	private Socket client;
	private DataStream dataStream;
	private DataOutputStream dos;
	private DataInputStream dis;
	private static final int PORT = 8080;

	public Client() {
		super("Client: Ai là triệu phú");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		setSize(600, 400);
		addItem();
		setVisible(true);
		// System.out.println(""+user);

	}
//Tao giao dien

	private void addItem() {
		setLayout(new BorderLayout());

		login = new JButton("Đăng nhập");
		login.addActionListener(this);

		send = new JButton("Chơi game");
		send.addActionListener(this);

		exit = new JButton("Thoát");
		exit.addActionListener(this);

		p_chat = new JPanel();
		p_chat.setLayout(new BorderLayout());

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		nick = new JTextField(20); //nhap mot dong van ban voi data ngan
		p1.add(new JLabel("Người dùng : "));
		p1.add(nick);
		p1.add(exit);

		JPanel p2 = new JPanel();
		p2.setLayout(new BorderLayout());

		JPanel p22 = new JPanel();
		p22.setLayout(new FlowLayout(FlowLayout.CENTER));
		p22.add(new JLabel("Danh sách online"));
		p2.add(p22, BorderLayout.NORTH);

		online = new JTextArea(10, 10);
		online.setEditable(false);
		p2.add(new JScrollPane(online), BorderLayout.CENTER);
		p2.add(new JLabel("     "), BorderLayout.SOUTH);
		p2.add(new JLabel("     "), BorderLayout.EAST);
		p2.add(new JLabel("     "), BorderLayout.WEST);

		msg = new JTextArea(10, 20);
		msg.setEditable(false);

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		message = new JTextField(30);
		p3.add(send);

		p_chat.add(new JScrollPane(msg), BorderLayout.CENTER);
		p_chat.add(p1, BorderLayout.NORTH);
		p_chat.add(p2, BorderLayout.EAST);
		p_chat.add(p3, BorderLayout.SOUTH);
		p_chat.add(new JLabel("     "), BorderLayout.WEST);

		p_chat.setVisible(false);
		add(p_chat, BorderLayout.CENTER);
		// ----------------------------//

		// -------------------------
		p_login = new JPanel();
		p_login.setLayout(new FlowLayout(FlowLayout.CENTER));
		p_login.add(new JLabel("Tài khoản : "));
		nick1 = new JTextField(20);
		p_login.add(nick1);
		p_login.add(login);
		add(p_login, BorderLayout.NORTH);
	}
//---------[ Socket ]-----------//

	public void go() {
		try {
			client = new Socket("localhost", PORT);
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());

			// client.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lá»—i káº¿t ná»‘i, xem láº¡i dÃ¢y máº¡ng Ä‘i hoáº·c room chÆ°a má»Ÿ.",
					"Message Dialog", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new Client().go();
	}

	private void sendMSG(String data) {
		try {
			dos.writeUTF(data);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMSG() {
		String data = null;
		try {
			data = dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public void getMSG(String msg1, String msg2) {
		int stt = Integer.parseInt(msg1);
		switch (stt) {
		// tin nháº¯n cá»§a nhá»¯ng ngÆ°á»�i khÃ¡c
		case 3:
			this.msg.append(msg2);
			break;
		// update danh sach online
		case 4:
			this.online.setText(msg2);
			break;
		// server Ä‘Ã³ng cá»­a
		case 5:
			dataStream.stopThread();
			exit();
			break;
		// bá»• sung sau
		default:
			break;
		}
	}
//----------------------------------------------

	private void checkSend(String msg) {
		if (msg.compareTo("\n") != 0) {
			this.msg.append("TÃ´i : " + msg);
			sendMSG("1");
			sendMSG(msg);
		}
	}

	public String getUser() {
		return user;
	}

	private boolean checkLogin(String nick) {
		if (nick.compareTo("") == 0) {
			return false;
		} else if (nick.compareTo("0") == 0) {
			return false;
		} else {
			sendMSG(nick);
			int sst = Integer.parseInt(getMSG());
			if (sst == 0) {
				return false;
			} else {
				return true;
			}
		}
	}

	private void exit() {
		try {
			sendMSG("0");
			dos.close();
			dis.close();
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == send) {
			// checkSend(message.getText()+"\n");
			// message.setText("");
			this.user = nick1.getText();
			System.out.println("---------------------");
			System.out.println("" + user);
			formMain frm = new formMain();
			frm.setVisible(true);
		} else if (e.getSource() == login) {
			if (checkLogin(nick1.getText())) {
				p_chat.setVisible(true);
				p_login.setVisible(false);
				nick.setText(nick1.getText());
				nick.setEditable(false);
				this.setTitle(nick1.getText());
				msg.append("Đã đăng nhập thành công \n");

				// user=nick1.getText();
				dataStream = new DataStream(this, this.dis);
			} else {
				JOptionPane.showMessageDialog(this,
						"Đã tồn tại nick này trong room, bạn vui lòng nhập lại!", "Message Dialog",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == exit) {
			// p_login.setVisible(true);
			// p_chat.setVisible(false);

			this.dispose();
			exit();
		}
	}

}
