package Damas;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Color;



public class customizacion extends JFrame {
	int[] posicion = {1,1};
	int[][] colores_a = {
			{ 0, 0, 255 },
			{ 228, 255, 0},
			{ 255, 0, 220} ,
			{ 120, 10, 135  }
	};
	int[][] colores_b = {
			{200, 197, 252},
			{217, 222, 143 },
			{ 222, 143, 222 } ,
			{  155, 143, 222  }
	};
	panel_color colores[]=new panel_color[4],color_a[] = new panel_color[4];
	public customizacion() {
		initPantalla();
		initPanelColores();
	}
	public void initPantalla() {
		setTitle("login");
	       this.getContentPane().setBackground(Color.black);
	       setVisible(true);
	       setResizable(false);
	       setBackground(Color.black);
	       setBounds(100, 100, 300, 400 );
	       setLayout(null);
	       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void initPanelColores() {
		JPanel panel_colores = new JPanel();
		JPanel panel_colores_1 = new JPanel();
		panel_colores.setLayout(new GridLayout(1,4,10,10));
		panel_colores_1.setLayout(new GridLayout(1,4,10,10));
		add(panel_colores);
		add(panel_colores_1);
		panel_colores.setBounds(10,50,280,50);
		panel_colores_1.setBounds(10,110,280,50);
		panel_colores.setBackground(Color.black);
		panel_colores_1.setBackground(Color.black);
			
		for(int i = 0; i < 4 ; i++) {
			colores[i] = new panel_color(colores_a[i],1);
			color_a[i] = new panel_color(colores_b[i],2);
			panel_colores.add(colores[i]);
			panel_colores_1.add(color_a[i]);
		}
		JPanel boton_salida = new JPanel();
		add(boton_salida);
		boton_salida.setBounds(10,190,280,50);
		JButton salir = new JButton("GUARDAR");
			salir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				new interfaz(posicion);
				
				}
			});
		boton_salida.add(salir);
		boton_salida.setBackground(Color.black);
		
	}
	public boolean active(int aux) {
		Border lb = BorderFactory.createLineBorder(Color.black);
		if(aux == 1) {
			for(int i = 0; i< 4; i++) {
				colores[i].setEleccion(false);
				colores[i].setBorder(lb);
			}
		}else {
			for(int i = 0; i< 4; i++) {
				color_a[i].setEleccion(false);
				color_a[i].setBorder(lb);
			}
		}
		
		return false;
	}
	public int[] getPosicion() {
		
		return posicion;
	}
	public void activarBordes() {
		Border lb = BorderFactory.createLineBorder(Color.white);
		for(int i = 0; i< 4; i++) {
			if(colores[i].getEleccion()) {
				colores[i].setBorder(lb);
				posicion[0] = i;
			}
			if(color_a[i].getEleccion()) {
				color_a[i].setBorder(lb);
				posicion[1] = i;
			}
		}
		
	}
	
	public static void main(String[] args) {
		new customizacion();
	}
	class panel_color extends JButton implements ActionListener{
		int posicion = 0;
		boolean eleccion  = false;
		int[] color = new int[3];
		public panel_color(int[] color , int posicion) {
			this.color = color;
			this.posicion = posicion ;
			addActionListener(this);
			setBackground(new Color(color[0],color[1],color[2]));
		}
		public void actionPerformed(ActionEvent e) {
			active(posicion);
			eleccion = true;
			activarBordes();
		}
		public void setEleccion(boolean eleccion) {
			this.eleccion = eleccion;
		}
		public boolean getEleccion(){
			return eleccion;
		}
		
	}
}

