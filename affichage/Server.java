package affichage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

public class Server extends JFrame{
	JPanel panel= new JPanel();
	JLabel label= new JLabel("Ecrire le message"); 
	JTextField textField= new JTextField();
	JButton send = new JButton("Envoyer");
	JTextArea textArea= new JTextArea();
	
	Socket socket;
    ServerSocket serverSocket;
    ObjectOutputStream outPut;
    ObjectInputStream inPut;
    BufferedReader reader ;
    String message="";
    
	public Server() {
		Components();
		this.setContentPane(panel);
		this.setVisible(true);
	}
	
	public void Components() {
		this.setTitle("SERVER");
		this.setBounds(5,5,550, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textField.setPreferredSize(new Dimension(370,28));
        textField.setBounds(20,400, 370, 50);
        send.setBounds(420, 400 ,70, 50);
        send.addActionListener(new Listener_Server(this));
        textArea.setColumns(41);
        textArea.setRows(15);
        panel.add(textField);
        panel.add(send);
        panel.add(textArea);
        
        textField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		sendMessage(textField.getText());
        		textField.setText("");
        	}
        });
	}
	
	public void sendMessage(String message) {
		try {
			outPut.writeObject("Server : "+message);
			outPut.flush();
			textArea.append("\nServer : "+message);
		} 
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			textArea.append("impossible d'envoyer le message");
		}
	}
	
	public void Chatting() {
		String message="";
		textField.setEditable(true);
		do {
			try {
				message=(String) inPut.readObject();
				textArea.append("\n"+message);
			} 
			catch (Exception e) {
			}
		} 
		while (!message.equals("Client : FIN"));
	}
	
	public void Run() {
		try {
			serverSocket = new ServerSocket(6789, 10);
			try {
				socket= serverSocket.accept();
				outPut = new ObjectOutputStream(socket.getOutputStream());
				inPut= new ObjectInputStream(socket.getInputStream());
				while(true) {
					Chatting();
				}
			} 
			catch (Exception e) {
			}
		} 
		catch (IOException io) {
			io.printStackTrace();
		}
	}
}
