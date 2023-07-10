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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GUI_Secundaria extends JFrame {
    public static final String PATH = "/recursos/";
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
        setupJFrame();
    }

    /**
     * Este método se utiliza para inicializar la interfaz gráfica de la ventana secundaria
     */
    private void initGUI_Secundaria() {
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelCentral = createPanel(Color.gray, BorderLayout.CENTER);

        panelCentral.setLayout(new GridBagLayout());

        panelTableroOponente = new PanelTableroOponente();
        pintarFlotaOponente = new PintarFlotaOponente(panelTableroOponente);
        panelCentral.add(panelTableroOponente);
    }

    private JPanel createPanel(Color color, Object layout) {
        JPanel panel = new JPanel();
        panel.setBackground(color);
        getContentPane().add(panel, layout);
        return panel;
    }

    /**
     * Este método se utiliza para configurar las propiedades de la ventana JFrame
     */
    private void setupJFrame() {
        setTitle("Batalla Naval");
        Image image = new ImageIcon(getClass().getResource(PATH + "barcoIcono.png")).getImage();
        setIconImage(image);
        setUndecorated(false);
        setSize(460, 460);
        setResizable(false);
        setVisible(false);
        setLocationRelativeTo(null);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel panelContenedor = new JPanel(new GridBagLayout());
        getContentPane().add(panelContenedor);

        panelContenedor.add(panelTableroOponente, gbc);
    }

    /**
     * Este método se utiliza para configurar las propiedades de la ventana JFrame
     */
    public void oponenteVsUsuario() {
        Random random = new Random();
        int row = random.nextInt(10) + 1;
        int col = random.nextInt(10) + 1;

        if (panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().get(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(1)) {
            // Verificar si la casilla seleccionada contiene un barco del usuario
            if (guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) != Integer.valueOf(0)) {
                for (int num = 1; num < 11; num++) {
                    String barco = "";
                    // Obtener el nombre del barco en la casilla seleccionada
                    if (guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) != null) {
                        barco = (String) guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]);
                    }
                    // Realizar acciones de combate según el barco seleccionado
                    if (barco.equals("portavion" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("submarino" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("destructor" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    } else if (barco.equals("fragata" + String.valueOf(num))) {
                        funcionesCombate(row, col, barco);
                        break;
                    }
                }
            }
        } else {
            if (panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().get(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(2)) {
                // Volver a intentar si la casilla ya fue atacada anteriormente
                oponenteVsUsuario();
            } else {
                // Actualizar casillas y estado si la casilla contiene agua
                panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().put(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));
                guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                estado = 0;
            }
        }
    }

    /**
     * Este método realiza las acciones de combate cuando se ha tocado un barco
     */
    public void funcionesCombate(int row, int col, String barco) {
        guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTableroOponente.getTableroOponente("principal").getCasillasOcupadas().replace(panelTableroOponente.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));

        guiPrincipal.getPanelTablero().getTablero("posicion").reducirCasillasUsadas(barco);

        if (guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[row][col]) == Integer.valueOf(0)) {
            contadorHundidos++;
            estado = 1;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    String nombreBarco = (String) guiPrincipal.getPanelTablero().getTablero("posicion").getCasillaNombreBarco().get(guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu]);
                    if (nombreBarco != null && nombreBarco.equals(barco)) {
                        guiPrincipal.getPanelTablero().getTablero("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                    }
                }
            }
        } else {
            estado = 1;
        }

        if (contadorHundidos == 10) {
            estado = 2;
        }
    }

    /**
     * Este método realiza la distribución aleatoria de la flota en el tablero del oponente
     */
    public void distribucionFlotaOponente() {
        Random random = new Random();
        String[] nombresBarcos = { "portavion", "submarino", "destructor", "fragata" };
        int numBarcoAleatorio = random.nextInt(4) + 1;
        String nombreBarco = nombresBarcos[numBarcoAleatorio - 1];

        int numOrientacionAleatoria = random.nextInt(2);
        int numSentidoAleatorio = numOrientacionAleatoria == 0 ? random.nextInt(2) + 1 : random.nextInt(2) + 3;
        int numColumnaAleatoria = random.nextInt(10) + 1;
        int numFilaAleatoria = random.nextInt(10) + 1;

        if (pintarFlotaOponente.getCantidadBarco(nombreBarco) > 0) {
            if (!pintarFlotaOponente.funcionesFlota(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                distribucionFlotaOponente();
            } else {
                pintarFlotaOponente.setCantidadBarco(nombreBarco);
            }
        }
    }

    /**
     * Este método retorna el panelTableroOponente
     */
    public PanelTableroOponente getPanelTableroOponente() {
        return panelTableroOponente;
    }

    /**
     * Este método retorna el panelTableroOponente
     */
    public PintarFlotaOponente getPintarFlotaOponente() {
        return pintarFlotaOponente;
    }

    /**
     * Este método retorna la variable estado
     */
    public int getEstado() {
        return estado;
    }
}
