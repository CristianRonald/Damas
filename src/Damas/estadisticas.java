package Damas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class estadisticas extends JFrame {
	private usuario u;
	public estadisticas(int id) {
		initPantalla();
		crearUsuario(id); 
		initPerfil();
		salir();
		initEstadisticas();

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
	public void crearUsuario(int id) {
		 int cont = 1;
		 String[] datos = new String[2];
		 int[] estadisticas = new int[4];
		 try {
	            Connection con = null;
	            String sURL = "jdbc:mysql://localhost:3306/usuarios";
	            con = DriverManager.getConnection(sURL, "root", "cris2021");
	            Statement myStament = con.createStatement();
	            ResultSet myResultSet = myStament.executeQuery("SELECT * FROM usuario;");
	            while(myResultSet.next()) {
	            	if(cont == id) {
	            		datos[1] = myResultSet.getString("correo");
	            		datos[0] = myResultSet.getString("apodo");
	            	}
	            	cont++;
	            }
	            cont = 1;
	            myResultSet = myStament.executeQuery("SELECT * FROM estadisticas;");
	            while(myResultSet.next()) {
	            	if(cont == id) {
	            		estadisticas[0] = myResultSet.getInt("ganadas");
	            		estadisticas[1] = myResultSet.getInt("perdidas");
	            		estadisticas[2] = myResultSet.getInt("empatadas");
	            		estadisticas[3] = myResultSet.getInt("elo");
	            	}
	            	cont++;
	            }

	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage() );
	        }
		 u = new usuario(estadisticas,datos);
		 
	}
	public void initPerfil() {
		JPanel panel = new JPanel();
		Border loweredbevel = BorderFactory.createLineBorder(Color.gray);
		panel.setLayout(new GridLayout(2,1,10,10));
		panel.setBackground(Color.black);
		add(panel);
		panel.setBounds(10,20,280,80);
		String[] datos = u.getDatos();
		JLabel[] perfil = new JLabel[2];
		
		TitledBorder title = BorderFactory.createTitledBorder(
                loweredbevel, "Perfil");
		title.setTitlePosition(TitledBorder.ABOVE_TOP);
		title.setTitleColor(Color.green);
		panel.setBorder(title);
		String names[] = {"Apodo: ", "Correo: "};
		for(int i = 0; i< 2; i++) {
			perfil[i] = new JLabel(names[i]+datos[i]);
			panel.add(perfil[i]);
			perfil[i].setForeground(Color.pink);
			perfil[i].setBorder(loweredbevel);
			perfil[i].setFont(new Font("Serif", Font.BOLD, 12));
		}
		
		
	}
	private int numero_partidas() {
		int suma = 0;
		for(int i = 0;i<3;i++) {
			suma += u.getEstadisticas()[i];
		}
		return suma;
	}
	public void initEstadisticas() {
		JPanel panel = new JPanel(),panel_jugados = new JPanel(),panel_estadisticas = new JPanel();
		Border lb = BorderFactory.createLineBorder(Color.gray);
		
		panel.setLayout(new GridLayout(2,1,10,50));
		panel.setBackground(Color.black);
		add(panel);
		panel.add(panel_jugados);
		panel.add(panel_estadisticas);
		
		JLabel jugados[] = new JLabel[2];
		JLabel estadisticas[] = new JLabel[3];
		panel.setBounds(10,130,280,120);
		jugados[0] = new JLabel(numero_partidas()+"");
		jugados[1] = new JLabel(u.getEstadisticas()[3]+"");
		panel_jugados.setLayout(new GridLayout(1,2,10,20));
		panel_estadisticas.setLayout(new GridLayout(1,3,10,20));
		panel_estadisticas.setBackground(Color.black);
		panel_jugados.setBackground(Color.black);
		TitledBorder title;
		String names[] = {"P. Jugadas", "Elo"};
		for(int i = 0; i < 2;i++) {
			title = BorderFactory.createTitledBorder(
	                lb, names[i]);
			title.setTitleColor(Color.orange);
			panel_jugados.add(jugados[i]);
			jugados[i].setBorder(title);
			jugados[i].setForeground(Color.blue);
		}
		String	names_1[] = {"Ganadas", "Perdidas" , "Empatadas"};
		for(int i = 0; i < 3;i++) {
			title = BorderFactory.createTitledBorder(
	                lb, names_1[i]);
			title.setTitleColor(Color.magenta);
			estadisticas[i] = new JLabel(u.getEstadisticas()[i]+"");
			estadisticas[i].setForeground(Color.yellow);
			
			panel_estadisticas.add(estadisticas[i]);
			estadisticas[i].setBorder(title);
		}
		
	}
	public void salir() {
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1,1,10,10));
		panel.setBounds(160,300,100,20); 
		JButton retornar = new JButton(" <= Volver ");
		retornar.setBackground(Color.black);
		retornar.setForeground(Color.cyan);
		retornar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new interfaz();
			}
		});
		panel.add(retornar);
		
	}
	public static void main(String[] args) {
		new estadisticas(1);
	}

}
class usuario{
	private int estadisticas[];
	private String[] datos;
	public usuario(int estadisticas[], String datos[]) {
		this.estadisticas = estadisticas;
		
		this.datos = datos;
	}
	public String[] getDatos() {
		return datos;
	}
	public int[] getEstadisticas() {
	return estadisticas;	
	}
}
