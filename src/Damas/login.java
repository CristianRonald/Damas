package Damas;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.util.regex.*;
public class login extends JFrame implements ActionListener {
    private JPanel fondo,panel;
    private JPanel panel_loguearte;
    private int ancho = 320;
    private user u;
    private JPasswordField pass;
    private JTextField entrada_correo;
    private JTextField entradas[] = new JTextField[7];

    public login() {
        initPantalla();
        initPaneles();
        loguearte();
    }

    private void initPaneles() {
        JLabel titulo = new JLabel("DAMAS");
        titulo.setFont(new Font("Serif", Font.BOLD, 68));
        titulo.setForeground(Color.white);
        fondo = new JPanel();
        fondo.setBackground(Color.black);
        fondo.setBounds(120, 40, 260, 60);
        fondo.add(titulo);
        fondo.setLayout(new GridBagLayout());
        add(fondo);
    }

    private void initPantalla() {
        setTitle("login");
        this.getContentPane().setBackground(Color.black);
        setVisible(true);
        setResizable(false);
        setBackground(Color.black);
        setBounds(100, 100, 500, ancho);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void crear_cuenta() {
         panel = new JPanel();
         
        panel.setBounds(50, 70, 300, 250);
        fondo.setBounds(60, 40, 260, 60);
        panel.setBackground(Color.black);
        panel.setLayout(new GridBagLayout());
        add(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        TitledBorder titled = BorderFactory.createTitledBorder("\u2615");
        

        String[] names = {  "Correo", "Contraseña","Confirmar Contraseña","apodo" };
        for (int i = 0; i < names.length; i++) {
            
            gbc.weightx = 1.0;
            gbc.gridheight = 1;
            gbc.gridwidth = 1;
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            panel.add(entradas[i] = new JTextField(), gbc);
            titled = new TitledBorder(names[i]);
            titled.setTitleFont(new Font("Dialog", Font.BOLD, 12));
            titled.setTitleColor(Color.white);
            entradas[i].setBorder(titled);
            entradas[i].setForeground(Color.white);
            entradas[i].setBackground(Color.black);
        }
        
        JPanel panel_botones = new JPanel();
        
        JButton entrar = new JButton("ENTRAR");
        JButton salir = new JButton("Volver");
        entrar.setBackground(Color.green);
        salir.setBackground(Color.red);
        panel_botones.setBackground(Color.black);
        panel_botones.setLayout(new GridLayout(1,2,10,10));
        panel_botones.add(entrar);
        panel_botones.add(salir);
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = names.length +1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        panel.add(panel_botones,gbc);
        
        entrar.addActionListener(this);
        salir.addActionListener(this);
        
        
    }

    private int conectar_base(String tipo) {
    	String comparar = "";
    	if("contraseña".equals(tipo)) {
    		comparar = new String(u.getPassword());
    	} else {
    		comparar = u.getName();
    	}

        try {
            Connection con = null;
            String sURL = "jdbc:mysql://localhost:3306/usuarios";
            con = DriverManager.getConnection(sURL, "root", "cris2021");
            Statement myStament = con.createStatement();
            ResultSet myResultSet = myStament.executeQuery("SELECT * FROM usuario;");
            while(myResultSet.next()) {
            	
            	if(myResultSet.getString(tipo).equals(comparar)) return myResultSet.getInt("id") ;
            	
            }

        } catch (Exception e) {
            System.err.println("Error");
        }
        return 0;
    }
    private int subir_datos() {
    	String datos[] = new String[4];
    	for(int i = 0;i<4;i++) {
    		datos[i] = entradas[i].getText();
    	}
    	u = new user(datos);
    	int id =0;
    	String pass = new String(u.getPassword()),sql = "INSERT INTO usuario"
                + "(id, correo, contraseña, apodo) VALUES"
                + "(?,?,?,?)";
    	
        try {
            Connection con = null;
            String sURL = "jdbc:mysql://localhost:3306/usuarios";
            con = DriverManager.getConnection(sURL, "root", "cris2021");
            Statement myStament = con.createStatement();
            ResultSet myResultSet = myStament.executeQuery("SELECT * FROM usuario;");
            while(myResultSet.next()) {
            	
            		id =myResultSet.getInt("id") + 1;                        	
            	
            }
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,u.getName());
            ps.setString(3,new String(u.getPassword()));
            ps.setString(4,u.getApodo());
            ps.executeUpdate();
            sql = "INSERT INTO estadisticas"
                    + "(id, elo, ganadas, perdidas,empatadas) VALUES"
                    + "(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, 700);
            ps.setInt(3, 0);
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.executeUpdate();
            	JFrame frame = new JFrame();
            	JOptionPane.showMessageDialog(frame, "Datos subidos correctamente.");
        } catch (Exception e) {
            System.err.println("Error: "+ e.getMessage());
        }
        
        return id;

    }
    



	
	private void loguearte() {

        panel_loguearte = new JPanel();
        panel_loguearte.setLayout(new GridBagLayout());
        TitledBorder border;
        GridBagConstraints gbc = new GridBagConstraints();
        add(panel_loguearte);
        panel_loguearte.setBounds(101, 120, 300, 100);
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        entrada_correo = new JTextField();
        entrada_correo.setFont(new Font("Dialog", Font.ITALIC, 13));
        
        panel_loguearte.add(entrada_correo, gbc);
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pass = new JPasswordField();
        panel_loguearte.add(pass, gbc);
        entrada_correo.setForeground(Color.white);
        panel_loguearte.setBackground(Color.black);
        
        border = new TitledBorder("Correo");
        border.setTitleFont(new Font("Dialog", Font.BOLD, 12));
        border.setTitleColor(Color.white);
        entrada_correo.setBorder(border);
        
        border = new TitledBorder("Contraseña");
        border.setTitleFont(new Font("Dialog", Font.BOLD, 12));
        border.setTitleColor(Color.white);
        pass.setBorder(border);
        
        entrada_correo.setBackground(Color.black);
        pass.setBackground(Color.black);
        JPanel panel_botones = new JPanel();
        
        JButton entrar = new JButton("INGRESAR");
        JButton crear = new JButton("CREAR CUENTA");
        entrar.setBackground(Color.green);
        crear.setBackground(Color.yellow);
        panel_botones.setBackground(Color.black);
        panel_botones.setLayout(new GridLayout(1,2,5,5));
        panel_botones.add(entrar);
        panel_botones.add(crear);
        
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        panel_loguearte.add(panel_botones,gbc);
        
        entrar.addActionListener(this);
        crear.addActionListener(this);
      
    }
        
        
    private void entrar() {
    	int id;
    	if((id = conectar_base("correo")) == conectar_base("contraseña")) {
    		setVisible(false);
    		new interfaz(id);
    	}
    	else {
    		loguearte(); 
    	}
    }
    private boolean verificar(int i) {
    	switch(i) {
    	case 0:
    		String regex = new String(), palabras[]  = {"hotmail","gmail"};
    		String separacion[] = {"",""};
            Pattern pattern;
            Matcher matcher;
            
            
            
            for(int j = 0;j < palabras.length; j++){ 
            regex = "@";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(entradas[i].getText());
            if (matcher.find()) {
            	separacion = entradas[i].getText().split(regex);
            }
            if(separacion[1].equals(palabras[j]+".com") && !separacion[0].equals("")) {
            	return false;
            }
    	}
            
    		break;
    	case 1: 
    		if(entradas[i].getText().length() >= 8) return false;
    		break;
    	case 2: 
    		if((entradas[i].getText()).equals(entradas[i-1].getText()) && !(entradas[i].getText().equals("")) ) return false;
    		break;
    	case 3: 
    		if(!(entradas[i].getText().equals(""))) {
    			return false;
    		}
    		break;
    	}
    	return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("INGRESAR")) {
        	u = new user(entrada_correo.getText(),pass.getPassword()); 
        	entrar();
            panel_loguearte.setVisible(false);
        }
        if (e.getActionCommand().equals("CREAR CUENTA")) {
            panel_loguearte.setVisible(false);
            crear_cuenta();
            ancho = 400;
            setSize(380, ancho);
        }
        if(e.getActionCommand().equals("ENTRAR")) {
        	int contador = 0;
        	for (int i = 0; i < 4; i++) {
            	if(verificar(i)) {
            		entradas[i].setBackground(Color.red);
            	}
            	else {
            		entradas[i].setBackground(Color.black);
            		contador++;
            		}
        	}
        	if(contador == 4) {
        		
        		setVisible(false);
        		 new interfaz(subir_datos()); 
        	}

        }
        if(e.getActionCommand().equals("Volver")) {
        	panel.setVisible(false);
        	initPantalla();
            loguearte();
            fondo.setBounds(120, 40, 260, 60);
            ancho = 320;
            setBounds(100, 100, 500, ancho);
        }

    }

    public static void main(String[] args) {
        new login();
    }
}
class user {
	private String name = "",apodo = "";
	private char password[];
	public user(String name, char[] password){
		this.name = name;
		this.password = password;
	}
	public user(String[] datos) {
		this.name = datos[0];
		this.password = datos[1].toCharArray();
		this.apodo = datos[3];
	}
	public String getName() { return name; }
	public char[] getPassword() { return password; }
	public String getApodo() { return apodo; }
	
}
