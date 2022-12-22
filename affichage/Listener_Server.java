package affichage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener_Server implements ActionListener{
	Server s;
	public Listener_Server(Server s) {
		this.s=s;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==s.send){
			s.sendMessage(s.textField.getText());
			s.textField.setText("");
		}
	}
}
