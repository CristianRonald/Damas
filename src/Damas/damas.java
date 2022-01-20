package Damas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class damas extends JFrame {
    private int alto = 90;
    private int ancho = 90,cont_damas = 0;
    static ficha squares[][] = new ficha[8][8];
    static JPanel tablero = new JPanel();
    activar a;
    dama d[] = new dama[8];
    Image bl = null, ng = null;
    

    // BufferedImage blancas;
    // Image bl = null;

    public damas() {
        initTablero();
        initBotones();
        tableroEstadisticas();
        // try {
        // blancas = ImageIO.read(getClass().getResource("piezas/blancas.png"));
        // bl = blancas.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        // } catch (Exception e) {
        // System.out.println(e);
        // }
    }

    public void initTablero() {
        setTitle("Damas");
        setVisible(true);
        setSize(800, 520);
        this.getContentPane().setBackground(Color.black);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public boolean par(int i, int j) {
        boolean play = true;
        if (i % 2 != 0) {
            play = !play;
        }
        if (play) {
            if (j % 2 == 0) {
                return true;
            }
        } else {
            if (j % 2 != 0) {
                return true;
            }
        }

        return false;
    }
    public void tableroEstadisticas() {
    	JPanel panel_botones = new JPanel();
    	panel_botones.setLayout(new GridLayout(2,1,10,10));
    	panel_botones.setBackground(new Color( 22, 50, 31));
    	JButton soplo = new JButton("SOPLAR");
    	JButton rendirse = new JButton("RENDIRSE");
    	soplo.setBackground(new Color(85,135,46));
    	rendirse.setBackground(Color.red);
    	panel_botones.add(soplo);
    	panel_botones.add(rendirse);
    	panel_botones.setBounds(10,60,180,80);
    	JPanel panel = new JPanel();
    	JPanel central = new JPanel();
    	central.setBackground(new Color( 22, 50, 31));
    	panel.setLayout(new BorderLayout(6,6));
    	central.setLayout(null);
    	central.add(panel_botones,BorderLayout.CENTER);
    	panel.add(central,BorderLayout.CENTER);
    	add(panel);
    	panel.setBackground(Color.white);
    	panel.setBounds(550,80,200,300);
    	JLabel names[] = new JLabel[2];
    	for(int i = 0; i < 2; i++ ) {
    		names[i] = new JLabel("Text",SwingConstants.CENTER);
    		names[i].setPreferredSize(new Dimension(10,30));
    		names[i].setFont(new Font("Zekton",Font.BOLD,14));
    	}
    	panel.add(names[0],BorderLayout.SOUTH);
    	panel.add(names[1],BorderLayout.NORTH);
    	
    	
    	
    		
    }
    public void boton_soplo() {
    	
    }

    public void initBotones() {

    	tablero.setBounds(20,20,480, 480);
        tablero.setLayout(new GridLayout(8, 8, 1, 1));
        BufferedImage blancas, negras;
        
        try {
            blancas = ImageIO.read(getClass().getResource("piezas/blancas.png"));
            negras = ImageIO.read(getClass().getResource("piezas/negras.png"));
            bl = blancas.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ng = negras.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            System.out.println(e);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new ficha();

                if (!par(i, j)) {
                    if (i <= 2 || i > 4) {
                        if (i > 4) {
                            squares[i][j] = new ficha(i, j, "blanco");
                            squares[i][j].setIcon(new ImageIcon(bl));
                        } else {
                            squares[i][j] = new ficha(i, j, "negro");
                            squares[i][j].setIcon(new ImageIcon(ng));
                        }

                    }

                    squares[i][j].setFocusPainted(false);
                    squares[i][j].setBackground(Color.blue);
                    squares[i][j].setOpaque(true);
                } else {
                    squares[i][j].setBackground(new Color(200, 197, 252));
                    squares[i][j].setEnabled(false);

                }

                tablero.add(squares[i][j]);

            }
        }

        add(tablero);
        a = new activar();
    }

    public void validarSquare(int[] nums, int[] padre) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == nums[0] && j == nums[1] && !squares[i][j].getTipoSquare()) {
                    squares[i][j].setTipo("paso");
                    squares[i][j].setEnabled(true);
                    squares[i][j].setPosition(i, j);
                    squares[i][j].setPadre(padre);
                    squares[i][j].setBackground(Color.red);
                } else if (i == nums[0] && j == nums[1] && squares[i][j].getTipoSquare()) {
                    if (!squares[i][j].getTipo().equals(squares[padre[0]][padre[1]].getTipo())) {
                        squares[i][j].comer(i, j, padre);
                    }
                }
            }
        }
    }

    public void comerSquare(int[] nums, int[] padre) {
        if (!squares[nums[0]][nums[1]].getTipoSquare()) {
            squares[nums[0]][nums[1]].setTipo("comer");
            squares[nums[0]][nums[1]].setEnabled(true);
            squares[nums[0]][nums[1]].setPosition(nums[0], nums[1]);
            squares[nums[0]][nums[1]].setPadre(padre);
            squares[nums[0]][nums[1]].setBackground(Color.green);
        }
    }

    public void avanzar(int i, int j, int[] posicion_padre) {

        String padre = squares[posicion_padre[0]][posicion_padre[1]].getTipo();
        BufferedImage blancas, negras;
        Image bl = null, ng = null, ext = null;

        try {
            blancas = ImageIO.read(getClass().getResource("piezas/blancas.png"));
            negras = ImageIO.read(getClass().getResource("piezas/negras.png"));
            bl = blancas.getScaledInstance(40,40, Image.SCALE_SMOOTH);
            ng = negras.getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        } catch (Exception e) {
            System.out.println(e);
        }
        if (padre.equals("blanco")) {
            ext = bl;
        } else {
            ext = ng;
        }

        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                if (i == k && j == l) {
                    squares[k][l].setIcon(new ImageIcon(ext));
                    squares[k][l].setFicha(padre);
                }

            }
        }
        borrarPadre(posicion_padre);

    }

    public void borrarPadre(int[] nums) {
        squares[nums[0]][nums[1]].setIcon(null);
        squares[nums[0]][nums[1]].setficha_v();

    }
    // desactivar fichas

    public void borrarPasos(int[] posicion, int[] nums) {

        int pasos[][] = squares[nums[0]][nums[1]].getPasos();

        for (int k = 0; k < 2; k++) {
            if (pasos[k][0] == posicion[0] && posicion[1] == pasos[k][1]) {
                squares[pasos[k][0]][pasos[k][1]].setTipoSquare(true);
                squares[pasos[k][0]][pasos[k][1]].setFicha(squares[posicion[0]][posicion[1]].getTipo());
            } else {
                if (!squares[pasos[k][0]][pasos[k][1]].getTipoSquare())
                    squares[pasos[k][0]][pasos[k][1]].setficha_v();
            }
            if (pasos[0][0] != pasos[1][0]) {
                squares[pasos[0][0]][pasos[0][1]].setBackground(Color.blue);
            } else {
                squares[pasos[k][0]][pasos[k][1]].setBackground(Color.blue);
            }

        }

    }

    public JButton[][] winner() {
        int cont = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (squares[i][j].getTipoSquare()) {
                    cont++;
                }
            }
        }
        if (cont == 1) {
            return squares;
        }
        return null;
    }

    public void mostrarGanador(String ganador) {

    }

    public static void main(String[] args) {
        new damas();
    }

    class ficha extends JButton implements ActionListener {
        private int[] posicion = new int[2];
        private String tipo;
        private int[] padre = new int[2], eliminar = new int[2];
        private int[][] pasos = new int[2][2];
        private boolean t_ficha = true ,dama = false;// revisar si el square es una ficha o un movimiento
        private int pos_dama = 0;
        public ficha(int i, int j, String tipo) {

            this.posicion[0] = i;
            this.posicion[1] = j;
            this.tipo = tipo;
            setEnabled(false);
            this.pasos = obtenerPasos(i, j, false);
            t_ficha = true;
            addActionListener(this);
        }
        
        public ficha() {
            t_ficha = false;
            setEnabled(false);
            addActionListener(this);
        }
       
        public void setFicha(String tipo) {
            this.tipo = tipo;
            this.pasos = obtenerPasos(posicion[0], posicion[1], false);
            t_ficha = true;
        }
        public int getPositionDama() {
        	return pos_dama;
        }
        public void setficha_v() {
            t_ficha = false;
            tipo = "";
            setEnabled(false);
        }

        public void setEliminar(int[] eliminar) {
            this.eliminar = eliminar;
        }

        public void setPadre(int[] padre) {
            this.padre = padre;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public void setPosition(int i, int j) {
            this.posicion[0] = i;
            this.posicion[1] = j;

        }
        public void setTipoDama() {
        	this.dama = true;
        }
        public boolean getTipoSquare() {
            return this.t_ficha;
        }

        public void setTipoSquare(boolean t_square) {
            this.t_ficha = t_square;
        }

        public String getTipo() {
            return this.tipo;
        }
        

        public int[][] getPasos() {
            return this.pasos;
        }
        public void setDama() {
        	if(posicion[0] == 7 && tipo.equals("negro")) {
        		d[cont_damas]= new dama(posicion[0],posicion[1]);	
        		this.pos_dama = cont_damas;
        		dama = true;
        		cont_damas++;
        	}
        	if(posicion[0] == 0 && tipo.equals("blanco")) {
        		d[cont_damas] = new dama(posicion[0],posicion[1]);
       		 	pos_dama = cont_damas;
       		 	dama = true;
       		 	cont_damas++;
        	}
        }
        public void actionPerformed(ActionEvent e) {
        	
        	
            if (!"paso".equals(tipo) && !"comer".equals(tipo) && !dama && !("comer_d".equals(tipo))) {
            	
                if (pasos[0][0] != pasos[1][0]) { // pa los costados
                    validarSquare(pasos[0], posicion);
                    
                    
                } else {
                    for (int i = 0; i < 2; i++) {
                        validarSquare(pasos[i], posicion);
                    }
                                         
                }
            } else {
            	if(dama || "comer_d".equals(tipo)) {
            		if(dama) {
            			d[pos_dama].movimiento();
            		}else {
            			d[pos_dama].comer_square(posicion[0],posicion[1],padre);            			
            			setTipoDama();
            			borrarPadre(eliminar);
            		}
            	}else {
            		if (tipo.equals("comer")) {
            			borrarPadre(eliminar);
            			setBackground(Color.blue);
            		}
            		avanzar(posicion[0], posicion[1], padre);
            		setDama();
            		borrarPasos(posicion, padre);
            		a.setSiguiente();
            	}
            }
            

        }

        public void comer(int i, int j, int[] padre) {
            if (winner() != null) {
                System.out.println();
            }

            int pasos[][] = obtenerPasos(i, j, true);
            int[] paso_final = new int[2], borrar = new int[2];
            for (int k = 0; k < 2; k++) {
                if (padre[1] > j && j > pasos[k][1] || padre[1] < j && j < pasos[k][1]) {
                    paso_final = pasos[k];
                }
            }
            borrar[0] = i;
            borrar[1] = j;
            squares[paso_final[0]][paso_final[1]].setEliminar(borrar);
            comerSquare(paso_final, padre);

        }

        public int[][] obtenerPasos(int i, int j, boolean feed) {
            int pasos[][] = new int[2][2];
            String aux = this.tipo;
            int cont = 0;
            if (feed) {
                aux = squares[i][j].getTipo();
                if (aux.equals("blanco")) {
                    aux = "negro";
                } else {
                    aux = "blanco";
                }
            }
            if (aux.equals("blanco")) {

                i--;
                int k = j - 2, l = j + 3;
                if (k < 0)
                    k = 0;
                if (l > 8)
                    l = 8;

                for (; k < l; k++) {
                    if (!par(i, k)) {
                        pasos[cont][0] = i;
                        pasos[cont][1] = k;
                        cont++;
                    }
                }
            } else {
                i++;
                int k = j - 2, l = j + 3;
                if (k < 0)
                    k = 0;
                if (l > 8)
                    l = 8;
                for (; k < l; k++) {
                    if (!par(i, k)) {
                        pasos[cont][0] = i;
                        pasos[cont][1] = k;
                        cont++;
                    }
                }
            }

            return pasos;
        }
    }

    class activar extends ficha {
        private boolean isnextWhite = true;
        // Al hacer click en una ficha

        public activar(int[] posicion) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i != posicion[0] && j != posicion[j])
                        squares[i][j].setEnabled(true);
                }
            }
        }

        // Cambiar de turno
        public activar() {
            String aux;
            if (isnextWhite) {
                aux = "blanco";
            } else {
                aux = "negro";
            }

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (squares[i][j].getTipoSquare() && squares[i][j].getTipo().equals(aux)) {
                        squares[i][j].setEnabled(true);
                    }
                }
            }
        }

        public void setSiguiente() {
            isnextWhite = !isnextWhite;
            String aux, des;
            if (isnextWhite) {
                aux = "blanco";
                des = "negro";
            } else {
                aux = "negro";
                des = "blanco";
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (squares[i][j].getTipoSquare() && squares[i][j].getTipo().equals(aux)) {
                        squares[i][j].setEnabled(true);
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (squares[i][j].getTipoSquare() && squares[i][j].getTipo().equals(des)) {
                        squares[i][j].setEnabled(false);
                    }
                }
            }
        }
    }
    class dama extends ficha{
    	private int  posicion[] = new int[2];
    	private String tipo = "";
    	public boolean tipo_paso = false;
    	public dama(int i, int j) {
    		posicion[0] = i; 
    		posicion[1] = j; 
    		this.tipo = squares[posicion[0]][posicion[1]].getTipo();
    		
    		
    	}
    	public dama(int i, int j, String Tipo) {
    		posicion[0] = i; 
    		posicion[1] = j;
    		this.tipo = Tipo;
    	}
    	public void movimiento() {
    		// por la derecha adelante
    		int aux = 0;
    		int pos_inic = posicion[0]-posicion[1];
    		if( pos_inic < 0) {
    			aux = pos_inic * -1;
    			pos_inic = 0;
    		}
    		else {
    			aux = 0;
    		}
    		boolean active = false,active_comer = false;
    		for(int i = pos_inic; i< 8 ; i++) {
    			for(int j = 0; j<8; j++) {
    				if(aux == j) {
    					if(!squares[i][j].getTipoSquare()) {
    					
    					squares[i][j].setBackground(Color.red);
    									
    						active = true;
    						
    					}
    					else {
    						if(!squares[i][j].getTipo().equals(this.tipo)) {
    							active_comer =comer(i,j);    			
    						}else {
    							active_comer =false;
    						}
    						active = false;
    					}
    					if(active && active_comer) {
							active_comer = false;
							}
    					//System.out.println("1["+i+"]"+"["+j+"]"+active+"["+active_comer+"]");
    				}
    			}
    			aux++;
    		}
    		pos_inic = posicion[0]+posicion[1];
    		if( pos_inic > 7) {
    			aux = pos_inic - 7;
    			pos_inic = 7;
    		}
    		else {
    			aux = 0;
    		}
    		for(int i = pos_inic; i>0; i--) {
    			for(int j = 0; j<8; j++) {
    				if(aux == j) {
    					if(!squares[i][j].getTipoSquare()) {
    						squares[i][j].setBackground(Color.red);
    						active = true;
    					}
    					else {
    						if(!squares[i][j].getTipo().equals(this.tipo)) {
    							active_comer = comer(i,j);
    						}	else {
    							active_comer =false;
    						}
    						active = false;
    					}
    					if(active && active_comer) {
							active_comer = false;
							}
    					//System.out.println("2["+i+"]"+"["+j+"]"+active+"["+active_comer+"]");
    				}
    			}
    			aux++;
    		}
    		
    		
    	}
    	 public void comer_square(int i,int j,int[] padre) {
    	    	squares[i][j].setBackground(Color.blue);
    	    	System.out.println("->"+squares[padre[0]][padre[1]].getTipo());
    	    	if(squares[padre[0]][padre[1]].getTipo().equals("blanco")) {
    	    		squares[i][j].setIcon(new ImageIcon(bl));
    	    	}else {
    	    		squares[i][j].setIcon(new ImageIcon(ng));
    	    	}
    	    	 squares[i][j] = squares[padre[0]][padre[1]];
    	    	 squares[i][j].setTipoSquare(true);
    	    	 squares[i][j].setTipo(tipo);
    	    	 setPosition(i,j);
    	    	 borrarPadre(padre);
    	    	 borrar(padre);
    	    	}
    	private void borrar(int[] padre){
    		int aux = 0;
    		int pos_inic = padre[0]-padre[1];
    		if( pos_inic < 0) {
    			aux = pos_inic * -1;
    			pos_inic = 0;
    		}
    		else {
    			aux = 0;
    		}
    		for(int i = pos_inic; i< 8; i++) {
    			for(int j = 0; j<8; j++) {
    				if(aux == j && !squares[i][j].getTipoSquare()) {    					
						squares[i][j].setBackground(Color.blue);
				}
    			}
    			aux++;
    		}
    		pos_inic = padre[0]+padre[1];
    		if( pos_inic > 7) {
    			aux = pos_inic - 7;
    			pos_inic = 7;
    		}
    		else {
    			aux = 0;
    		}
    		for(int i = pos_inic; i>0; i--) {
    			for(int j = 0; j<8; j++) {
    				if(aux == j && !squares[i][j].getTipoSquare()) {    					
    						squares[i][j].setBackground(Color.blue);
    				}
    			}
    			aux++;
    		}
    		
    		
    	}
    	public void setPosition(int i,int j) {
    		posicion[0] = i;
    		posicion[1] = j;
    	}
    	
    	public boolean comer(int i, int j) {
    		int[] eliminar = new int[2];
    		if((i>0 && i<7) && (j>0 && j<7)) {
    			eliminar[0] = i;
    			eliminar[1] = j;
    			if(posicion[1] > j) {
    				j--;
    				if(i<posicion[0]) {			
    					i--;   			
    				}else {
    					i++;
    				}
    			}else {
    					j++;
    				if(i<posicion[0]) {
    					i--;
    				}else {
    					i++;
    				}
    			}
    			if(!squares[i][j].getTipoSquare()) {	
    				squares[i][j].setBackground(Color.green);					
    				squares[i][j].setTipo("comer_d");
    				squares[i][j].setEnabled(true);
    				squares[i][j].setPosition(i, j);
    				squares[i][j].setEliminar(eliminar);
    				squares[i][j].setPadre(posicion);
    				System.out.println("2["+posicion[0]+"]"+"["+posicion[1]+"]");
    				return true;
    			}
    		}
    			return false;
    		}
    }
   

}
