package controller;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;

import model.AFNDtoAFD.AFNDtoAFD;
import model.domain.AFD;
import model.input.InputReader;
import model.logica.GraficadorAutomata;
import model.solver.AFDSolver;
import view.AutomatasWindow;
import view.MainWindow;

public class ControladorPrincipal implements ActionListener {
	private MainWindow ventanaPrincipal;
	private AutomatasWindow ventanaAutomatas;
	private File archivoSeleccionado;
	private boolean automataConvertido = false;
	private AFD afd = null;

	public ControladorPrincipal(MainWindow vista) {
		this.ventanaPrincipal = vista;
		this.ventanaPrincipal.getBtnAutomata().addActionListener(this);
		this.ventanaPrincipal.getBtnGramaticas().addActionListener(this);
	}

	public void inicializarVentanaPrincipal() {
		if (this.ventanaPrincipal == null) {
			this.ventanaPrincipal = new MainWindow();
			this.ventanaPrincipal.getBtnAutomata().addActionListener(this);
			this.ventanaPrincipal.getBtnGramaticas().addActionListener(this);
		}
		this.ventanaPrincipal.show();
	}

	public void inicializarAutomatasWindow() {
		if (this.ventanaAutomatas == null) {
			this.ventanaAutomatas = new AutomatasWindow();
			this.ventanaAutomatas.getBtnSeleccionarArchivo().addActionListener(this);
			this.ventanaAutomatas.getBtnVolver().addActionListener(this);
			this.ventanaAutomatas.getBtnConvertir().addActionListener(this);
			this.ventanaAutomatas.getBtnGraficar().addActionListener(this);
			this.ventanaAutomatas.getBtnProcesar().addActionListener(this);
			this.ventanaAutomatas.getBtnGuardar().addActionListener(this);
		}
		this.ventanaAutomatas.show();
	}

	public void actionPerformed(ActionEvent e) {
		//////////////////////////////////////////////////////////////////
		/////////////////// VENTANA PRINCIPAL/////////////////////////////
		//////////////////////////////////////////////////////////////////
		if (e.getSource() == this.ventanaPrincipal.getBtnAutomata()) {
			this.ventanaPrincipal.getFrame().dispose();
			this.inicializarAutomatasWindow();
			this.vaciarTxtStrings();
		} else if (e.getSource() == this.ventanaPrincipal.getBtnGramaticas()) {
			System.out.println("ABRIR VENTANA DE GRAMATICA");
		}
		//////////////////////////////////////////////////////////////////
		/////////////////// VENTANA AUTOMATAS//////////////////////////////
		//////////////////////////////////////////////////////////////////
		else if (e.getSource() == this.ventanaAutomatas.getBtnSeleccionarArchivo()) {
			System.out.println("SELECT ARCHIVO");
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				archivoSeleccionado = fileChooser.getSelectedFile();
				this.ventanaAutomatas.getLblNombreArchivo().setText(archivoSeleccionado.getName());
				this.ventanaAutomatas.getBtnGraficar().setVisible(true);
				this.ventanaAutomatas.getBtnConvertir().setVisible(true);
				automataConvertido = false;
			}
			this.mostrarOcultarProcesarString(false);
		}
		else if (e.getSource() == this.ventanaAutomatas.getBtnGraficar()) {
			if(automataConvertido)
				GraficadorAutomata.graficarAFD(afd);
			else
				GraficadorAutomata.graficarAFND(archivoSeleccionado);			
		}
		else if (e.getSource() == this.ventanaAutomatas.getBtnConvertir()) {
			AFNDtoAFD converter = new AFNDtoAFD();
			this.afd = converter.fromAFNDtoAFD(InputReader.crearAFND(archivoSeleccionado));
			this.automataConvertido = true;
			this.mostrarOcultarProcesarString(true);
			this.vaciarTxtStrings();
			this.ventanaAutomatas.getBtnGuardar().setVisible(true);
		}
		else if (e.getSource() == this.ventanaAutomatas.getBtnProcesar()) {
			String resultado = AFDSolver.resolver(afd, this.ventanaAutomatas.getTxtString().getText());
			if(resultado.equals("ACEPTADO"))	
				this.ventanaAutomatas.getLblAceptarRechazar().setForeground(Color.GREEN);
			else
				this.ventanaAutomatas.getLblAceptarRechazar().setForeground(Color.RED);
			this.ventanaAutomatas.getLblAceptarRechazar().setText(resultado);
		}
		else if (e.getSource() == this.ventanaAutomatas.getBtnVolver()) {
			archivoSeleccionado = null;
			automataConvertido = false;
			this.ventanaAutomatas.getLblNombreArchivo().setText("");
			this.ventanaAutomatas.getBtnGraficar().setVisible(false);
			this.ventanaAutomatas.getBtnConvertir().setVisible(false);
			this.ventanaAutomatas.getBtnGuardar().setVisible(false);
			this.mostrarOcultarProcesarString(true);
			this.ventanaAutomatas.getFrame().dispose();
			this.inicializarVentanaPrincipal();
			this.mostrarOcultarProcesarString(false);
		}
		else if (e.getSource() == this.ventanaAutomatas.getBtnGuardar()) {
			
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showSaveDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
		            FileWriter fw = new FileWriter(fileChooser.getSelectedFile()+".txt");
		            fw.write(this.afd.toString());
		            fw.close();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    
			}

		    
			
			
			
		}

	}
	
	
	private void mostrarOcultarProcesarString(boolean bool) {
		this.ventanaAutomatas.getLblAceptarRechazar().setVisible(bool);
		this.ventanaAutomatas.getLblResultado().setVisible(bool);
		this.ventanaAutomatas.getBtnProcesar().setVisible(bool);
		this.ventanaAutomatas.getTxtString().setVisible(bool);
		this.ventanaAutomatas.getLblString().setVisible(bool);
	}
	
	private void vaciarTxtStrings() {
		this.ventanaAutomatas.getLblAceptarRechazar().setText("");
		this.ventanaAutomatas.getTxtString().setText("");
	}
	
	
	
}