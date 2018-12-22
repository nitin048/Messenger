package myApp;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.net.*;
import java.io.*;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class client extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private static String ips;
	Socket sock;
	boolean flag,flag1 = true;
	char[] string = new char[15];
	int k=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					client frame = new client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public client(){
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(98, 28, 241, 27);
		contentPane.add(comboBox);
		
		JLabel lblNewMessage = new JLabel("New Message :");
		lblNewMessage.setBounds(32, 101, 106, 16);
		contentPane.add(lblNewMessage);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ips = comboBox.getSelectedItem().toString();
			}
		});
		btnSelect.setBounds(338, 27, 106, 29);
		contentPane.add(btnSelect);
		
		textField = new JTextField();
		textField.setBounds(34, 212, 294, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblEnterMessage = new JLabel("Enter message :");
		lblEnterMessage.setBounds(36, 184, 168, 16);
		contentPane.add(lblEnterMessage);
		
		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					sock=new Socket(ips,4000);
				    String fname=textField.getText().toString();
				    OutputStream ostream=sock.getOutputStream();// handles output streams which passing from client to server through socket
				    PrintWriter pwrite=new PrintWriter(ostream,true);//Printwriter obj prints filename through ostream @ sever
				    pwrite.println(fname);
				    pwrite.close();
				}catch(Exception e1){
					System.out.println(e1.getMessage());
				}
			}
		});
		btnSend.setBounds(327, 212, 117, 29);
		contentPane.add(btnSend);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(150, 101, 221, 71);
		contentPane.add(textArea);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					ProcessBuilder pb = new ProcessBuilder("arp","-a");
					Process p = pb.start();
					InputStream is = p.getInputStream();
					BufferedReader readline = new BufferedReader(new InputStreamReader(is));
					String str;
					while((str = readline.readLine()) != null){
						flag = flag1 = true;
						for(int i=0;i<str.length()-10;i++){
							if(str.subSequence(i, i+10).equals("incomplete")){
								flag = false;
							}
						}
						if(flag){
							k=0;
							for(int i=0;i<str.length()-40;i++){
								if(str.charAt(i)==')'){
									flag1 = true;
								}
								if(!flag1){
									string[k++] = (str.charAt(i));
								}
								if(str.charAt(i)=='('){
									flag1 = false;
								}	
							}
						}
						if(string[0] != ' '){
							String s = new String(string);
							comboBox.addItem(s.trim());
							for(int i=0;i<15;i++){
								string[i] = ' ';
							}
						}
					}
				}catch(Exception e1){}
			}
		});
		btnSearch.setBounds(6, 41, 93, 29);
		contentPane.add(btnSearch);
		
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				P p = new P();
				p.main(null);
			}
		});
		btnNewButton.setBounds(6, 6, 93, 29);
		contentPane.add(btnNewButton);
		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try{
						ServerSocket sersock = new ServerSocket(4000);
						Socket sock = sersock.accept();
						InputStream istream = sock.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
						textArea.setText(reader.readLine().toString());
						sock.close();
						istream.close();
						reader.close();
						sersock.close();
					}catch(Exception e2){
						System.out.println(e2.getMessage());
					}
				}
			}
		});
		t1.start();
	}
}
