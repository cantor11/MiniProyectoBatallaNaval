package proyect;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * Clase GUI_Secundaria
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 19/3/2022
 */
public class GUI_Secundaria extends JFrame {
    public static final String PATH = "/recursos/";
    private Header titulo;
    private ImageIcon enemyIcon;
    private PanelTableroOponente panelTableroOponente;
    private PintarFlotaOponente pintarFlotaOponente;
    private GUI guiPrincipal;
    private int contadorHundidos;
    private int estado; // 1 si continua, 2 si gana el oponente, de lo contrario 0

    /**
     * Constructor de la clase GUI_Secundaria
     */
    public GUI_Secundaria(GUI _guiPrincipal) {
        this.guiPrincipal = _guiPrincipal;
        contadorHundidos = 0;
        initGUI_Secundaria();

        // Configuración del JFrame
        this.setTitle("Batalla Naval");
        Image image = new ImageIcon(getClass().getResource(PATH + "barcoIcono.png")).getImage();
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(600, 600);
        this.setResizable(true);
        this.setVisible(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * Este método se utiliza para configurar la configuración predeterminada de JComponent,
     * crear objetos de escucha y control utilizados para la clase GUI
     */
    private void initGUI_Secundaria() {
        // Set up JFrame Container's Layout
        getContentPane().setLayout(new BorderLayout(0,0));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.CYAN);
        getContentPane().add(panelPrincipal,BorderLayout.CENTER);
        panelPrincipal.setLayout(new BorderLayout(0,0));

        JPanel panelSup = new JPanel();
        panelSup.setBackground(Color.CYAN);
        panelPrincipal.add(panelSup,BorderLayout.NORTH);
        panelSup.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.CYAN);
        panelPrincipal.add(panelInferior,BorderLayout.SOUTH);
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER,200,0));

        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.cyan);
        panelPrincipal.add(panelCentral,BorderLayout.CENTER);
        panelCentral.setLayout(new GridBagLayout());
        panelTableroOponente = new PanelTableroOponente();
        pintarFlotaOponente = new PintarFlotaOponente(panelTableroOponente);
        panelCentral.add(panelTableroOponente);

        // Set up JComponents
        // Titulo
        titulo = new Header("MOVIMIENTOS ENEMIGO", Color.CYAN);
        panelSup.add(titulo,FlowLayout.LEFT);

        // Icono
        enemyIcon = new ImageIcon(getClass().getResource(PATH+"enemy.png"));
        JLabel enemy = new JLabel(enemyIcon);
        panelSup.add(enemy,FlowLayout.CENTER);
    }

    /**
     * Selecciona aleatoriamente una casilla para atacar la flota del usuario
     */
    public void oponenteVsUsuario(){
        Random fila = new Random();
        Random columna = new Random();

        int row = fila.nextInt(10)+1;
        int col = columna.nextInt(10)+1;

        // Verifica si la casilla seleccionada hay un barco del usuario
        if(panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().get(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(1)){
            // Verifica si todas las casillas del barco fueron seleccionadas
            if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) != Integer.valueOf(0)){
                for(int num=1; num < 11; num++){
                    if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))){
                        funcionesCombate(row, col, "portavion" + String.valueOf(num));
                        break;
                    }else{
                        if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))){
                            funcionesCombate(row, col, "submarino" + String.valueOf(num));
                            break;
                        }else{
                            if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))){
                                funcionesCombate(row, col, "destructor" + String.valueOf(num));
                                break;
                            }else{
                                if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))){
                                    funcionesCombate(row, col, "fragata" + String.valueOf(num));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }else{
            if(panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().get(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(2)){
                oponenteVsUsuario();
            }else{
                panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().put(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));
                guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                estado = 0;
            }
        }
    }

    /**
     * Identifica si hay un barco en la casilla del tablero principal para hundirlo
     * @param row
     * @param col
     * @param barco
     */
    public void funcionesCombate(int row, int col, String barco){
        // Establece una imagen a la casilla seleccionada del tablero posición del usuario si un barco fue tocado
        guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().replace(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce las casillas ocupadas del barco tocado para poder ser hundido
        guiPrincipal.getPanelTablero().getTablero("posicion").reducirCasillasUsadas(barco);

        // Si no hay más casillas ocupadas, el barco se hunde y se establecen las imágenes respectivas
        if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) == Integer.valueOf(0)){
            contadorHundidos++;
            estado = 1;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu]) != null){
                        if(guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu]).equals(barco)){
                            guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                        }
                    }else{
                        continue;
                    }
                }
            }
        }else{
            estado = 1;
        }

        if(contadorHundidos == 10){
            estado = 2;
        }
    }

    /**
     * Distribuye de forma aleatoria la flota en el tablero posición del oponente
     */
    public void distribucionFlotaOponente(){
        Random barcoAleatorio = new Random();
        String nombreBarco = "";
        int numBarcoAleatorio = barcoAleatorio.nextInt(4)+1;

        switch (numBarcoAleatorio){
            case 1: nombreBarco = "portavion";
                break;
            case 2: nombreBarco = "submarino";
                break;
            case 3: nombreBarco = "destructor";
                break;
            case 4: nombreBarco = "fragata";
                break;
        }

        Random orientacionAleatoria = new Random();
        int numOrientacionAleatoria = orientacionAleatoria.nextInt(2);

        Random sentidoAleatorio = new Random();
        int numSentidoAleatorio = 0;
        switch (numOrientacionAleatoria){
            case 0:
                numSentidoAleatorio = sentidoAleatorio.nextInt(2)+1;
                break;
            case 1:
                numSentidoAleatorio = sentidoAleatorio.nextInt(4-3)+3;
                break;
        }

        Random columnaAleatoria = new Random();
        int numColumnaAleatoria = columnaAleatoria.nextInt(10)+1;

        Random filaAleatoria = new Random();
        int numFilaAleatoria = filaAleatoria.nextInt(10)+1;

        if(numBarcoAleatorio == 1 &&  pintarFlotaOponente.getCantidadBarco("portavion") > 0){
            if(!pintarFlotaOponente.funcionesFlota(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)){
                distribucionFlotaOponente();
            }else{
                pintarFlotaOponente.setCantidadBarco("portavion");
            }
        }else{
            if(numBarcoAleatorio == 2 &&  pintarFlotaOponente.getCantidadBarco("submarino") > 0){
                if(!pintarFlotaOponente.funcionesFlota(nombreBarco,numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)){
                    distribucionFlotaOponente();
                }else{
                    pintarFlotaOponente.setCantidadBarco("submarino");
                }
            }else{
                if(numBarcoAleatorio == 3 &&  pintarFlotaOponente.getCantidadBarco("destructor") > 0){
                    if(!pintarFlotaOponente.funcionesFlota(nombreBarco,numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)){
                        distribucionFlotaOponente();
                    }else{
                        pintarFlotaOponente.setCantidadBarco("destructor");
                    }
                }else{
                    if(numBarcoAleatorio == 4 &&  pintarFlotaOponente.getCantidadBarco("fragata") > 0){
                        if(!pintarFlotaOponente.funcionesFlota(nombreBarco,numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)){
                            distribucionFlotaOponente();
                        }else{
                            pintarFlotaOponente.setCantidadBarco("fragata");
                        }
                    }
                }
            }
        }
    }

    /**
     * Retorna el panelTableroOponente
     * @return PanelTableroOponente
     */
    public PanelTableroOponente getPanelTableroOponente(){
        return panelTableroOponente;
    }

    /**
     * Retorna el objeto para pintar la flota oponente
     * @return PintarFlotaOponente
     */
    public PintarFlotaOponente getPintarFlotaOponente(){
        return pintarFlotaOponente;
    }

    /**
     * Retorna la variable estado
     * @return int
     */
    public int getEstado(){
        return estado;
    }
}