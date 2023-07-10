package proyect;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelTableroOponente
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 19/3/2022
 */
public class PanelTableroOponente extends JPanel {
    private BackgroundPane panelTableroPosicion;
    private JLabel nombreTableroPosicion;
    private Tableros tableroPosicionOponente, tableroPrincipalOponente;
    private String abecedario[];

    /**
     * Constructor de la clase PanelTableroOponente
     */
    public PanelTableroOponente(){
        GridBagLayout gb = new GridBagLayout();
        this.setLayout(gb);
        this.setBackground(Color.BLACK);
        tableroPosicionOponente = new Tableros();
        tableroPrincipalOponente = new Tableros();
        abecedario = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        iniciar();
        modelTableroOponente();
    }

    /**
     * Establece la configuración inicial del JComponent
     */
    public void iniciar(){
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tablero posición
        nombreTableroPosicion = new JLabel("POSICIONES DEL OPONENTE");
        nombreTableroPosicion.setForeground(Color.white);
        nombreTableroPosicion.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPosicion, gbc);

        panelTableroPosicion = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,15,0,15);
        this.add(panelTableroPosicion, gbc);
    }

    /**
     * JPanel con imagen para agregar las matrices
     */
    public class BackgroundPane extends JPanel{

        public BackgroundPane(){
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            revalidate();
            repaint();
        }
    }

    /**
     * Crea los tableros posición y principal
     */
    public void modelTableroOponente(){
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if(row == 0 && col == 0){
                    tableroPosicionOponente.getMatriz()[row][col] = new JLabel();
                    tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                    tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                    tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    tableroPrincipalOponente.getMatriz()[row][col] = new JLabel();
                    tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                    tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                    tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }else{
                    if(row == 0 && col > 0){
                        tableroPosicionOponente.getMatriz()[row][col] = new JLabel(abecedario[col-1], SwingConstants.CENTER);
                        tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                        tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                        tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        tableroPrincipalOponente.getMatriz()[row][col] = new JLabel(abecedario[col-1], SwingConstants.CENTER);
                        tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                        tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                        tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }else{
                        if(row > 0 && col == 0){
                            tableroPosicionOponente.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                            tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                            tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipalOponente.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                            tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                            tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }else{
                            tableroPosicionOponente.getMatriz()[row][col] = new JLabel();
                            tableroPosicionOponente.getMatriz()[row][col].setOpaque(false);
                            tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipalOponente.getMatriz()[row][col] = new JLabel();
                            tableroPrincipalOponente.getMatriz()[row][col].setOpaque(false);
                            tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }
                panelTableroPosicion.add(tableroPosicionOponente.getMatriz()[row][col]);
            }
        }
    }

    /**
     * Retorna el tablero ingresado
     * @param _tablero
     * @return Tableros
     */
    public Tableros getTableroOponente(String _tablero){
        Tableros tablero = new Tableros();
        if(_tablero.equals("posicion")){
            tablero = tableroPosicionOponente;
        }else{
            if(_tablero.equals("principal")){
                tablero = tableroPrincipalOponente;
            }
        }
        return tablero;
    }
}
