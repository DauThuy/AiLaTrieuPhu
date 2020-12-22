/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAN_Server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame implements ActionListener {

	private JButton close;
	public JTextArea user;
	private ServerSocket server;
	private static final int PORT = 8080;
	public Hashtable<String, ClientConnect> listUser;

	public Server() {
		super("Server: Ai là triệu phú");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					// gá»Ÿi tin nháº¯n tá»›i táº¥t cáº£ client
					server.close();
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		setSize(400, 400);
		addItem();
		setVisible(true);
	}

	private void addItem() {
		setLayout(new BorderLayout());

		add(new JLabel("Trạng thái server : \n"), BorderLayout.NORTH);
		add(new JPanel(), BorderLayout.EAST);
		add(new JPanel(), BorderLayout.WEST);

		user = new JTextArea(10, 20);
		user.setEditable(false);
		add(new JScrollPane(user), BorderLayout.CENTER);

		close = new JButton("Close Server");
		close.addActionListener(this);
		add(close, BorderLayout.SOUTH);

		//user.append("MÃ¡y chá»§ Ä‘Ã£ Ä‘Æ°á»£c má»Ÿ.\n");
		user.append("Máy chủ đã được mở\n");
	}

	private void go() {
		try {
			listUser = new Hashtable<String, ClientConnect>();
			server = new ServerSocket(PORT);
			//user.append("MÃ¡y chá»§ báº¯t Ä‘áº§u phá»¥c vá»¥\n");
			user.append("Máy chủ bắt đầu phục vụ\n");
			while (true) {
				Socket client = server.accept();
				new ClientConnect(this, client);
			}
		} catch (IOException e) {
			user.append("KhÃ´ng thá»ƒ khá»Ÿi Ä‘á»™ng mÃ¡y chá»§\n");
		}
	}

	public static void main(String[] args) {
		new Server().go();
	}

	public void actionPerformed(ActionEvent e) {
		try {
			server.close();

			System.exit(0);
			// frmPlay frm= new frmPlay();
			// frm.dispose();

		} catch (Exception e1) {
			user.append("KhÃ´ng thá»ƒ dá»«ng Ä‘Æ°á»£c mÃ¡y chá»§\n");
		}
		System.exit(0);
	}

	public String getAllName() {
		Enumeration e = listUser.keys();
		String name = "";
		while (e.hasMoreElements()) {
			name += (String) e.nextElement() + "\n";
		}
		return name;
	}

	public void sendAll(String from, String msg) {
		Enumeration e = listUser.keys();
		String name = null;
		while (e.hasMoreElements()) {
			name = (String) e.nextElement();
			// System.out.println(name);
			if (name.compareTo(from) != 0)
				listUser.get(name).sendMSG("3", msg);
		}
	}

	public void sendAllUpdate(String from) {
		Enumeration e = listUser.keys();
		String name = null;
		while (e.hasMoreElements()) {
			name = (String) e.nextElement();
			// System.out.println(name);
			if (name.compareTo(from) != 0)
				listUser.get(name).sendMSG("4", getAllName());
		}
	}

}
