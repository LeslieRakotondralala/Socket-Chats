package affichage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame{
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
    String iP;
    
    public Client() {
    	Components();
		this.setContentPane(panel);
		this.setVisible(true);
    }
    
    public void Components() {
		this.setTitle("CLIENT");
		this.setBounds(750,5,550, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textField.setPreferredSize(new Dimension(370,28));
        textField.setBounds(20,400, 370, 50);
        textArea.setColumns(41);
        textArea.setRows(15);
        send.setBounds(420, 400 ,70, 50);
        send.addActionListener(new Listener_Client(this));
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
    
    public void Run() {
    	try {
			try {
				socket= new Socket(InetAddress.getByName(iP), 6789);
			} 
			catch (Exception e) {
				// TODO: handle exception
			}
			outPut= new ObjectOutputStream(socket.getOutputStream());
			outPut.flush();
			inPut = new ObjectInputStream(socket.getInputStream());
			Chatting();
		} 
    	catch (Exception e) {
    		
    	}
    }
    
    public void sendMessage(String message) {
		try {
			outPut.writeObject("Client : "+message);
			outPut.flush();
			textArea.append("\nClient : "+message);
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
	
    
}
