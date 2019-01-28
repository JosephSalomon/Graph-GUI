package practice2;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class practice2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		   try {
			   UIManager.setLookAndFeel(UIManager
					   .getCrossPlatformLookAndFeelClassName());
		   } catch (ClassNotFoundException | InstantiationException
				   | IllegalAccessException | UnsupportedLookAndFeelException e) {
		   }
		   new GraphGUI();
	}

}
