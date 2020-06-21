package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;


public class MainWindow{

	
	private JFrame frame;
	private JButton btnAutomata;
	private JButton btnGramaticas;
	private JLabel lblMadeBy;
	private JLabel lblLogo;
	private JLabel lblTitulo;


	public MainWindow() 
	{
		super();
		initialize();
	}


	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 584, 262);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		btnAutomata = new JButton("Automatas");
		btnAutomata.setBounds(10, 228, 175, 23);
		panel.add(btnAutomata);
		
		btnGramaticas = new JButton("Gramaticas");
		btnGramaticas.setBounds(197, 228, 175, 23);
		panel.add(btnGramaticas);
		
		lblTitulo = new JLabel("TEORIA DE LA COMPUTACION");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Bradley Hand ITC", Font.BOLD, 24));
		lblTitulo.setBounds(12, 11, 394, 98);
		panel.add(lblTitulo);
		
		lblLogo = new JLabel();
		lblLogo.setBounds(381, 11, 193, 210);
		lblLogo.setIcon(new ImageIcon("images/ungsLogo.png"));
		panel.add(lblLogo);
		
		lblMadeBy = new JLabel("Made by: Cruz, Gustavo and Grillo, Gian Franco");
		lblMadeBy.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblMadeBy.setBounds(381, 234, 193, 14);
		panel.add(lblMadeBy);
		
		JLabel lblTitulo_1 = new JLabel("TRABAJO PRACTICO");
		lblTitulo_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo_1.setFont(new Font("Bradley Hand ITC", Font.BOLD, 24));
		lblTitulo_1.setBounds(63, 64, 263, 98);
		panel.add(lblTitulo_1);
	}
	
	public void show()
	{
		this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.frame.addWindowListener(new WindowAdapter() 
		{
			@Override
		    public void windowClosing(WindowEvent e) {
		        int confirm = JOptionPane.showOptionDialog(
		             null, "Estas seguro que quieres salir!?", 
		             "Confirmacion", JOptionPane.YES_NO_OPTION,
		             JOptionPane.QUESTION_MESSAGE, null, null, null);
		        if (confirm == 0) {
		           System.exit(0);
		        }
		    }
		});
		this.frame.setVisible(true);
	}


	public JFrame getFrame() {
		return frame;
	}


	public JButton getBtnAutomata() {
		return btnAutomata;
	}


	public JButton getBtnGramaticas() {
		return btnGramaticas;
	}
}
