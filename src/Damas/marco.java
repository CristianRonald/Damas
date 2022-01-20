package Damas;

import javax.swing.JFrame;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;


public class marco extends JFrame {
	public marco() {
		initMarco();
		initTablero();
	}
	public void initTablero() {
        damas d=new damas();
        add(d);
    }
	public void initMarco() {
		setTitle("Damas");
        setVisible(true);
        setSize(95 * 8, 95 * 8);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
	}

	public static void main(String[] args) {
		
		 
		    
		 
		        String[] nombreFuentes = GraphicsEnvironment.getLocalGraphicsEnvironment()
		                                    .getAvailableFontFamilyNames();
		 
		        System.out.println("Nombre de las fuentes disponibles");
		        System.out.println(Arrays.toString(nombreFuentes));
		 
		        System.out.println("\nFuentes disponibles");
		        Font[] fuentes = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		        for (Font f : fuentes) {
		            System.out.println(f.getName() + " - " + f.getFontName() + " - " + f.getFamily());
		        }
		 
		    }
		 
		
	}


