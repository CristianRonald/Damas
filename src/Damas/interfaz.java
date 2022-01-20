package Damas;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class interfaz extends JFrame {
	private Image logo = null;
	private JPanel fondo;
	private int id = 0 ;
   public interfaz() {
	   initPantalla();
	   initBotones();
	   initLogo();
   }
   public interfaz(int id) {
	   initPantalla();
	   initBotones();
	   initLogo();
	   this.id = id;
   }
   private void initLogo() {
	   BufferedImage lg;
	   
	   try {
           lg = ImageIO.read(getClass().getResource("piezas/logo.png"));
           logo = lg.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

       } catch (Exception e) {
           System.out.println(e);
       }
	   JLabel titulo = new JLabel("DAMAS"),image = new JLabel();
	   image.setIcon(new ImageIcon(logo));
       titulo.setFont(new Font("Serif", Font.BOLD, 32));
       titulo.setForeground(Color.white);
       fondo = new JPanel();
       fondo.add(image);
       fondo.add(titulo);
       fondo.setBackground(Color.black);
       fondo.setBounds(30, 20, 240, 30);
       fondo.setLayout(new GridBagLayout());
       add(fondo);
   }
   private void initBotones(){
	   JPanel panel_botones = new JPanel();
	   panel_botones.setBounds(40, 120,220,210);
	   panel_botones.setLayout(new GridLayout(4,1,1,40));
	   panel_botones.setBackground(Color.black);
	   JButton[] botones = new JButton[3];
	   String names[] = {"JUGAR","Estadisticas","Personalizar"};
	   add(panel_botones);
	   for(int i = 0;i < 3; i++ ) {
		   botones[i] = new JButton(names[i]);
		   botones[i].setBackground(Color.gray);
		   botones[i].setForeground(Color.white);
		   panel_botones.add(botones[i]);
	   }
	   JPanel usuario = new JPanel();
	   JButton salir = new JButton("Salir");
	   JButton loguear = new JButton("Iniciar sesion");
	   usuario.add(salir);
	   usuario.add(loguear);
	   usuario.setLayout(new GridLayout(1,2,8,1));
	   usuario.setBackground(Color.black);
	   salir.setBackground(Color.gray);
	   loguear.setBackground(Color.gray);
	   loguear.setForeground(Color.white);
	   salir.setForeground(Color.white);
	   panel_botones.add(usuario); 
	   // Acciones 
	   botones[0].addActionListener(new ActionListener() {
		   @Override
           public void actionPerformed(ActionEvent event){
			   setVisible(false);
               new damas();
           }
	   });
	   botones[1].addActionListener(new ActionListener() {
		   @Override
           public void actionPerformed(ActionEvent event){
			   if(id>0) {
				   setVisible(false);
               new estadisticas(id);
			   	}else {
			   		JFrame frame = new JFrame();
	            	JOptionPane.showMessageDialog(frame, "Tienes que inciar sesion.");
	            	
			   	}
			   }
	   });
	   loguear.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
			   setVisible(true);
			   new login();
		   }
	   });
   }
   private void initPantalla() {
	   setTitle("login");
       this.getContentPane().setBackground(Color.black);
       setVisible(true);
       setResizable(false);
       setBackground(Color.black);
       setBounds(100, 100, 300, 400 );
       setLayout(null);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   
   public static void main(String[] args) {
       new interfaz();
   }
}
