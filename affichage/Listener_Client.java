package affichage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener_Client implements ActionListener {
	Client c;
	public Listener_Client(Client c) {
		this.c= c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==c.send){
			c.sendMessage(c.textField.getText());
			c.textField.setText("");
		}
	}

}
