package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFileChooser;

public class AutomatasWindow{

	
	private JFrame frame;
	private JButton btnSeleccionarArchivo;
	private JButton btnVolver;
	private JLabel lblMadeBy;
	private JLabel lblArchivoSeleccionado;
	private JLabel lblNombreArchivo;
	private JButton btnGraficar;
	private JButton btnConvertir;

	public AutomatasWindow() 
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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 584, 262);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		btnSeleccionarArchivo = new JButton("Seleccionar Archivo");
		btnSeleccionarArchivo.setBounds(10, 228, 159, 23);
		panel.add(btnSeleccionarArchivo);
		
		
		lblArchivoSeleccionado = new JLabel("Archivo seleccionado :");
		lblArchivoSeleccionado.setBounds(10, 11, 127, 48);
		panel.add(lblArchivoSeleccionado);
		
		lblMadeBy = new JLabel("Made by: Cruz, Gustavo and Grillo, Gian Franco");
		lblMadeBy.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblMadeBy.setBounds(392, 233, 182, 14);
		panel.add(lblMadeBy);
		
		btnVolver = new JButton("Volver");
		btnVolver.setBounds(179, 228, 159, 23);
		panel.add(btnVolver);
		
		lblNombreArchivo = new JLabel(" ");
		lblNombreArchivo.setBounds(147, 11, 427, 48);
		panel.add(lblNombreArchivo);
		
		btnGraficar = new JButton("Graficar");
		btnGraficar.setBounds(10, 70, 159, 23);
		panel.add(btnGraficar);
		btnGraficar.setVisible(false);
		
		btnConvertir = new JButton("Convertir");
		btnConvertir.setBounds(179, 70, 159, 23);
		panel.add(btnConvertir);
		btnConvertir.setVisible(false);
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


	public JButton getBtnGraficar() {
		return btnGraficar;
	}


	public JButton getBtnConvertir() {
		return btnConvertir;
	}
	
	
}
