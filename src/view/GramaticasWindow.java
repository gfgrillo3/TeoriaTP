package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.Color;

public class GramaticasWindow{

	
	private JFrame frame;
	private JButton btnSeleccionarArchivo;
	private JButton btnVolver;
	private JLabel lblMadeBy;
	private JLabel lblArchivoSeleccionado;
	private JLabel lblNombreArchivo;
	private JLabel lblString;
	private JTextField txtString;
	private JLabel lblResultado;
	private JLabel lblAceptarRechazar;
	private JButton btnProcesar;
	private JLabel lblLogo;
	

	public GramaticasWindow() 
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
		
		
		
		btnSeleccionarArchivo = new JButton("Seleccionar Archivo");
		btnSeleccionarArchivo.setBounds(10, 228, 159, 23);
		panel.add(btnSeleccionarArchivo);
		
		
		lblArchivoSeleccionado = new JLabel("Archivo seleccionado :");
		lblArchivoSeleccionado.setBounds(10, 11, 159, 48);
		panel.add(lblArchivoSeleccionado);
		
		lblMadeBy = new JLabel("Made by: Cruz, Gustavo and Grillo, Gian Franco");
		lblMadeBy.setFont(new Font("Tahoma", Font.BOLD, 8));
		lblMadeBy.setBounds(381, 234, 193, 14);
		panel.add(lblMadeBy);
		
		lblLogo = new JLabel();
		lblLogo.setBounds(381, 11, 193, 210);
		lblLogo.setIcon(new ImageIcon("images/ungsLogo.png"));
		panel.add(lblLogo);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(179, 228, 159, 23);
		panel.add(btnVolver);
		
		lblNombreArchivo = new JLabel(" ");
		lblNombreArchivo.setBounds(179, 11, 159, 48);
		panel.add(lblNombreArchivo);
		
		lblString = new JLabel("String:");
		lblString.setBounds(10, 104, 127, 48);
		panel.add(lblString);
		lblString.setVisible(false);
		
		txtString = new JTextField();
		txtString.setBounds(63, 119, 176, 23);
		panel.add(txtString);
		txtString.setColumns(10);
		txtString.setVisible(false);
		
		lblResultado = new JLabel("Resultado:");
		lblResultado.setBounds(10, 148, 127, 48);
		panel.add(lblResultado);
		lblResultado.setVisible(false);
		
		lblAceptarRechazar = new JLabel("");
		lblAceptarRechazar.setBounds(118, 148, 220, 48);
		lblAceptarRechazar.setFont(new Font("Bradley Hand ITC", Font.BOLD, 24));
		panel.add(lblAceptarRechazar);
		lblAceptarRechazar.setVisible(false);
		
		btnProcesar = new JButton("Procesar");
		btnProcesar.setBounds(249, 118, 89, 23);
		panel.add(btnProcesar);
		btnProcesar.setVisible(false);
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


	public JButton getBtnSeleccionarArchivo() {
		return btnSeleccionarArchivo;
	}
	
	public JButton getBtnVolver() {
		return btnVolver;
	}
	
	public JLabel getLblNombreArchivo() {
		return lblNombreArchivo;
	}

	public JLabel getLblMadeBy() {
		return lblMadeBy;
	}


	public JLabel getLblArchivoSeleccionado() {
		return lblArchivoSeleccionado;
	}


	public JLabel getLblString() {
		return lblString;
	}


	public JTextField getTxtString() {
		return txtString;
	}


	public JLabel getLblResultado() {
		return lblResultado;
	}


	public JLabel getLblAceptarRechazar() {
		return lblAceptarRechazar;
	}


	public JButton getBtnProcesar() {
		return btnProcesar;
	}
	
	
	
}
