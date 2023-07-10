package proyect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Clase principal
 *
 * @version v.1.0.0 date:28/05/2023
 * @autor Kevin Jordan Alzate kevin.jordan@correounivalle.edu.co
 * @autor Junior Cantor Arevalo junior.cantor@correounivalle.edu.co
 */
public class GUI extends JFrame {

    private Header headerProject;

    public static final String PATH ="/recursos/";
    public static final String AYUDA ="Batalla Naval es un juego tradicional de estrategia y suerte, que involucra a dos participantes (para este caso un jugador vs el computador).\n"+
            "\nEl objetivo del juego es ser el primero en hundir los barcos del oponente. \n"+"\nCada jugador tiene 2 tableros compuesto por 10 filas y 10 columnas: \n"+
            "\n-> Tablero de posición: Representa tu territorio, en él distribuirás tu flota antes de comenzar la partida y sólo será de observación. Verás la posición de tus barcos \ny los disparos de tu oponente en tu territorio, pero no podrás realizar ningún cambio ni disparo en él. \n"+
            "\n-> Tablero principal: Representa el territorio del enemigo, donde tiene desplegada su flota. Será aquí donde se desarrollen los movimientos (disparos) del jugador tratando \nde hundir los barcos enemigos. Este tablero aparecerá en la pantalla del jugador una vez comience la partida y en él quedarán registrados todos sus movimientos, reflejando \ntanto los disparos al agua como los barcos tocados y hundidos hasta el momento. \n"
            +"\n\nCada jugador tiene una flota de 9 barcos de diferente tamaño, por lo que cada uno ocupará un número determinado de casillas en el tablero: \n"+
            "\n• 1 portaaviones: ocupa 4 casillas "+"\n• 2 submarinos: ocupan 3 casillas cada uno."+"\n• 3 destructores: ocupan 2 casillas cada uno "+"\n• 4 fragatas: ocupan 1 casilla cada uno "
            +"\n\nCada barco puede ser ubicado de manera horizontal o vertical en el tablero de posición. "+"\n\nTerminología y movimientos: \n\n"+"• Agua: cuando se dispara sobre una casilla donde no está colocado ningún barco enemigo. En el tablero principal del jugador aparecerá una X. Pasa el turno a tu oponente."+
            "\n• Tocado: cuando se dispara en una casilla en la que está ubicado un barco enemigo que ocupa 2 o más casillas y se destruye sólo una parte del barco. En el tablero del jugador \naparecerá esa parte del barco con una marca indicativa de que ha sido tocado. El jugador vuelve a disparar. "
            +"\n• Hundido: si se dispara en una casilla en la que está ubicado una fragata (1 casilla) u otro barco con el resto de casillas tocadas, se habrá hundido, es decir, se ha eliminado ese \nbarco del juego. Aparecerá en el tablero principal del jugador, el barco completo con la marca indicativa de que ha sido hundido. El jugador puede volver a disparar, siempre y cuando no hayas\n hundido toda la flota de su enemigo, en cuyo caso habrá ganado. ";
    public  static final String CREDITOS="CRÉDITOS\n" +
            "-> BRAYAN STIVEN SANCHEZ LEON\n" +
            "-> MAYRA ALEJANDRA SANCHEZ SALINAS";
    //private Header headerProject;
    private JButton ayuda, creditos, comenzarPartida, movimientosEnemigo,reiniciar;
    private Escucha escucha;
    private ImageIcon team, help, enemy, play, infoSentidos, reinicio;
    private JPanel panelNorte, panelSur, panelEste, panelCentro;
    private PanelTablero panelTablero;
    private PintarFlota pintarFlota;
    private PanelFlota panelFlota;
    private GUI_Secundaria ventanaOponente;
    private int estadoJuego; // 1 seleccionar barco, 2 seleccionar orientacion del barco, 3 seleccionar sentido del barco, 4 colocar barco en el tablero, 5 combate, 6 turno del oponente
    private Combate combate;
    private int contadorHundidos; // Contador de barcos hundidos
    private Timer timer; // establece el tiempo que tarde el oponente en escoger casilla
    private Image image;
    private ComenzarPartidaListener comenzarPartidaListener;
    private EscuchaPosiciones escuchaPosiciones;
    private int orientacion = 1;
    private int sentidoOrientacion = 3;
    private int barcoVinculadoCount = 0;
    private String barcosList[] = new String[]{"portavion", "submarino", "submarino" , "destructor", "destructor", "destructor", "fragata", "fragata", "fragata", "fragata"};

    /**
     * Constructor of GUI class
     */
    public GUI() {
        initGUI();

        initGUI();

        // Configuración del JFrame
        this.setTitle("Batalla Naval");
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(1500,750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */
    private void initGUI() {
        // Creación de la ventana del oponente
        ventanaOponente = new GUI_Secundaria(this);

        comenzarPartidaListener = new ComenzarPartidaListener();
        escuchaPosiciones = new EscuchaPosiciones();

        // Icono del JFrame
        image = new ImageIcon(getClass().getResource(PATH+"barcoIcono.png")).getImage();

        // Set up JFrame Container's Layout
        panelNorte = new JPanel();
        panelSur = new JPanel();
        panelEste = new JPanel();
        panelCentro = new JPanel();

        // Creación de paneles para el JFrame
        panelNorte.setBackground(Color.DARK_GRAY);
        panelSur.setBackground(Color.DARK_GRAY);
        panelEste.setBackground(Color.GRAY);

        panelSur.setLayout(new FlowLayout(FlowLayout.CENTER,200,5));
        panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER,200,5));
        panelEste.setLayout(new FlowLayout(FlowLayout.CENTER,100,60));

        panelSur.setPreferredSize(new Dimension(100,90));
        panelNorte.setPreferredSize(new Dimension(100,60));
        panelEste.setPreferredSize(new Dimension( 1500,20));

        // Se agregan los paneles al JFrame
        this.add(panelNorte,BorderLayout.NORTH);
        this.add(panelSur,BorderLayout.SOUTH);
        this.add(panelEste,BorderLayout.CENTER);

        // Estado del juego
        estadoJuego = 1;

        // Creación el evento escucha
        escucha = new Escucha();

        // Set up JComponents
        // Imágenes
        infoSentidos = new ImageIcon(getClass().getResource(PATH + "info.png"));

        // JComponents de la parte superior
        // Título
        headerProject = new Header("BATALLA NAVAL", Color.DARK_GRAY);
        headerProject.setFont(new Font("MONOSPACED", Font.BOLD,40));
        panelNorte.add(headerProject,FlowLayout.LEFT);

        // Creación botón ayuda
        ayuda = new JButton("Ayuda");
        ayuda.setFont(new Font("MONOSPACED", 4,20));
        ayuda.setForeground(Color.red);
        ayuda.addActionListener(escucha);
        ayuda.setBackground(Color.PINK);
        ayuda.setFocusable(false);
        ayuda.setBorder(null);
        panelNorte.add(ayuda,FlowLayout.CENTER);

        // Creación botón créditos
        creditos = new JButton("Integrantes");
        creditos.setFont(new Font("MONOSPACED",4, 20));
        creditos.setForeground(Color.red);
        creditos.addActionListener(escucha);
        creditos.setBackground(Color.PINK);
        creditos.setFocusable(false);
        creditos.setBorder(null);
        panelNorte.add(creditos,FlowLayout.LEFT);

        // JComponents de la parte central
        // Tableros
        panelTablero = new PanelTablero();
        panelEste.add(panelTablero);


        //JComponents de la parte Inferior
        // Creación de botón comenzar partida
        comenzarPartida = new JButton("Comenzar partida");
        comenzarPartida.setFont(new Font("MONOSPACED", Font.BOLD,25));
        comenzarPartida.setForeground(Color.red);
        comenzarPartida.setBackground(Color.PINK);
        comenzarPartida.setFocusable(false);
        comenzarPartida.setBorder(null);
        comenzarPartida.addActionListener(comenzarPartidaListener);
        panelSur.add(comenzarPartida,FlowLayout.LEFT);

        // Creación de botón de movimientos del oponente
        movimientosEnemigo = new JButton("Movimientos oponente");
        movimientosEnemigo.setFont(new Font("MONOSPACED", Font.BOLD,25));
        movimientosEnemigo.setForeground(Color.red);
        movimientosEnemigo.addActionListener(escucha);
        movimientosEnemigo.setBackground(Color.PINK);
        movimientosEnemigo.setFocusable(false);
        movimientosEnemigo.setBorder(null);
        panelSur.add(movimientosEnemigo,FlowLayout.CENTER);

        // Creación del botón de reinicio
        reiniciar = new JButton("Reiniciar");
        reiniciar.setFont(new Font("MONOSPACED", Font.BOLD,25));
        reiniciar.setForeground(Color.red);
        reiniciar.addActionListener(escucha);
        reiniciar.setBackground(Color.PINK);
        reiniciar.setFocusable(false);
        reiniciar.setBorder(null);
        panelSur.add(reiniciar,FlowLayout.CENTER);


        // Se distribuye la flota del oponente
        while(ventanaOponente.getPintarFlotaOponente().cantidadTotalNaves() > 0){
            ventanaOponente.distribucionFlotaOponente();
        }

        // Objeto para invocar funciones de combate
        combate = new Combate(panelTablero, ventanaOponente.getPanelTableroOponente());

        contadorHundidos = 0;
        pintarFlota = new PintarFlota(panelTablero, panelFlota);
        // Timer para el turno del oponente
        timer = new Timer(2000, escucha);
    }

    /**
     * Main process of the Java program
     *
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI miProjectGUI = new GUI();
        });
    }


    /**
     * Reinicia el juego
     */
    public void reset(){
        this.dispose();
        GUI gui = new GUI();
    }






    private class ComenzarPartidaListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            headerProject.setText("¡Posiciona tus naves¡ ");
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].addMouseListener(escuchaPosiciones);
                }
            }
            comenzarPartida.setEnabled(false);
        }


    }

    private class EscuchaPosiciones implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                if (orientacion == 1) {
                    orientacion = 0;
                    sentidoOrientacion = 2;
                }else{
                    orientacion = 1;
                    sentidoOrientacion = 3;
                }
            }else if (e.getButton() == MouseEvent.BUTTON1)
            {
                if (barcoVinculadoCount < barcosList.length){
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == panelTablero.getTablero("posicion").getMatriz()[row][col]) {
                                pintarFlota.posicionarBarco(barcosList[barcoVinculadoCount], col, row, orientacion);
                                barcoVinculadoCount++;
                                if (barcoVinculadoCount >= barcosList.length){
                                    comienzaLaGuerra();
                                }
                            }
                        }
                    }
                }else{
                    comienzaLaGuerra();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (barcoVinculadoCount < barcosList.length) {
                for (int row = 1; row < 11; row++) {
                    for (int col = 1; col < 11; col++) {
                        pintarFlota.removerIcon(col, row);
                        if (e.getSource() == panelTablero.getTablero("posicion").getMatriz()[row][col]) {
                            if (pintarFlota.funcionesFlota(barcosList[barcoVinculadoCount], orientacion, sentidoOrientacion, col, row)) {
                                break;
                            }
                        }
                    }
                }
            }
            repaint();
            revalidate();
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        public void comienzaLaGuerra() {
            headerProject.setText("¡Hora del ataque¡ ");
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].removeMouseListener(escuchaPosiciones);
                }
            }
        }

    }



    /**
     * Agrega o remueve él escucha a cada uno de los JLabel de la matriz posición de PintarTablero
     * @param evento
     */
    public void setEscuchaCasillas(String evento){
        if(evento == "agregar"){
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < panelTablero.getTablero("posicion").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("posicion").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("posicion").getMatriz()[row][col].removeMouseListener(escucha);
                }
            }
        }
    }



    /**
     * Agrega o remueve él escucha a cada uno de los JLabel de la matriz principal de PintarTablero
     * @param evento
     */
    public void setEscuchaCasillasPrincipal(String evento){
        if(evento == "agregar"){
            for (int row = 0; row < panelTablero.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("principal").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("principal").getMatriz()[row][col].addMouseListener(escucha);
                }
            }
        }else{
            for (int row = 0; row < panelTablero.getTablero("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelTablero.getTablero("principal").getMatriz()[row].length; col++) {
                    panelTablero.getTablero("principal").getMatriz()[row][col].removeMouseListener(escucha);
                }
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
        // Establece una imagen a la casilla seleccionada del tablero principal del usuario y del tablero posicion del oponente si un barco fue tocado
        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTablero.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/tocado.png")));
        panelTablero.getTablero("principal").getCasillasOcupadas().replace(panelTablero.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce las casillas ocupadas del barco tocado para poder ser hundido
        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").reducirCasillasUsadas(barco);

        // Si no hay mas casillas ocupadas, el barco se hunde y se establecen las imagenes respectivas
        if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) == Integer.valueOf(0)){
            estadoJuego = 5;
            contadorHundidos++;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]) != null){
                        if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu]).equals(barco)){
                            ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                            panelTablero.getTablero("principal").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/recursos/hundido.png")));
                        }
                    }else{
                        continue;
                    }
                }
            }
        }else{
            estadoJuego = 5;
        }

        if(contadorHundidos == 10){
            setEscuchaCasillasPrincipal("remover");
        }
    }

    /**
     * Retorna el objeto de clase PanelTablero
     * @return PanelTablero
     */
    public PanelTablero getPanelTablero(){
        return panelTablero;
    }

    /**
     * clase interna que extiende una clase de adaptador o implementa oyentes utilizados por la clase GUI
     */
    private class Escucha implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == reiniciar){
                reset();
            }else{
                if(e.getSource() == ayuda){
                    JOptionPane.showMessageDialog(null,AYUDA,"¿Como se juega batalla naval?",JOptionPane.PLAIN_MESSAGE, help);
                }else{
                    if(e.getSource() == creditos){
                        JOptionPane.showMessageDialog(null,CREDITOS,"Créditos",JOptionPane.PLAIN_MESSAGE, team);
                    }else{
                        if(e.getSource() == comenzarPartida){
                            comenzarPartida.removeActionListener(this);
                            panelFlota.getAsignarTurno().setText("¡Tu turno!");
                            panelFlota.getInformacionJuego().setText("Selecciona la nave que quieres desplegar");
                        }else{
                            if(e.getSource() == movimientosEnemigo){
                                ventanaOponente.setVisible(true);
                            }else{
                                if(e.getSource() == panelFlota.getExplicacionBotones()){
                                    JOptionPane.showMessageDialog(null,"","Como jugar",JOptionPane.PLAIN_MESSAGE, infoSentidos);
                                }else{
                                    if(estadoJuego == 6){
                                        if(e.getSource() == timer){
                                            ventanaOponente.oponenteVsUsuario();
                                            if(ventanaOponente.getEstado() == 0){
                                                timer.stop();
                                                estadoJuego = 5;
                                                panelFlota.getAsignarTurno().setText("Tu turno");
                                                panelFlota.getInformacionJuego().setText("Selecciona otra casilla del tablero principal");
                                            }else{
                                                if(ventanaOponente.getEstado() == 2){
                                                    timer.stop();
                                                    panelFlota.getInformacionJuego().setText("Tus barcos han sido hundidos, perdiste el juego");
                                                }
                                            }
                                        }
                                    }else{
                                        switch(estadoJuego){
                                            case 1:
                                                if(e.getSource() == panelFlota.getBotonBarco("portavion")){
                                                    if(panelFlota.getCantidadBarco("portavion") > 0){
                                                        panelFlota.setCantidadBarco("portavion");
                                                        panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                        panelFlota.setNombreBoton("portavion");
                                                        estadoJuego = 2;
                                                    }else{
                                                        panelFlota.getInformacionJuego().setText("No hay mas portaviones disponibles");
                                                    }
                                                }else{
                                                    if(e.getSource() == panelFlota.getBotonBarco("destructor")){
                                                        if(panelFlota.getCantidadBarco("destructor") > 0){
                                                            panelFlota.setCantidadBarco("destructor");
                                                            panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                            panelFlota.setNombreBoton("destructor");
                                                            estadoJuego = 2;
                                                        }else{
                                                        }
                                                    }else{
                                                        if(e.getSource() == panelFlota.getBotonBarco("fragata")){
                                                            if(panelFlota.getCantidadBarco("fragata") > 0){
                                                                panelFlota.setCantidadBarco("fragata");
                                                                panelFlota.setNombreBoton("fragata");
                                                                estadoJuego = 2;
                                                            }else{
                                                                panelFlota.getInformacionJuego().setText("No hay mas fragatas disponibles");
                                                            }
                                                        }else{
                                                            if(e.getSource() == panelFlota.getBotonBarco("submarino")){
                                                                if(panelFlota.getCantidadBarco("submarino") > 0){
                                                                    panelFlota.setCantidadBarco("submarino");
                                                                    panelFlota.getInformacionJuego().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                                    panelFlota.setNombreBoton("submarino");
                                                                    estadoJuego = 2;
                                                                }else{
                                                                    panelFlota.getInformacionJuego().setText("No hay mas submarinos disponibles");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case 2:
                                                if(e.getSource() == panelFlota.getBotonOrientacion("vertical")){
                                                    panelFlota.getInformacionJuego().setText("Escoge cual sentido quieres usar");
                                                    panelFlota.setOrientacion(0);
                                                    estadoJuego = 3;
                                                }else{
                                                    if(e.getSource() == panelFlota.getBotonOrientacion("horizontal")){
                                                        panelFlota.getInformacionJuego().setText("Escoge cual sentido quieres usar");
                                                        panelFlota.setOrientacion(1);
                                                        estadoJuego = 3;
                                                    }
                                                }
                                                break;
                                            case 3:
                                                if(e.getSource() == panelFlota.getBotonSentidoOrientacion("sup_inf")){
                                                    panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                    setEscuchaCasillas("agregar");
                                                    panelFlota.setSentidoOrientacion(1);
                                                    estadoJuego = 4;
                                                }else{
                                                    if(e.getSource() == panelFlota.getBotonSentidoOrientacion("inf_sup")){
                                                        panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                        setEscuchaCasillas("agregar");
                                                        panelFlota.setSentidoOrientacion(2);
                                                        estadoJuego = 4;
                                                    }else{
                                                        if(e.getSource() == panelFlota.getBotonSentidoOrientacion("izq_der")){
                                                            panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                            setEscuchaCasillas("agregar");
                                                            panelFlota.setSentidoOrientacion(3);
                                                            estadoJuego = 4;
                                                        }else{
                                                            if(e.getSource() == panelFlota.getBotonSentidoOrientacion("der_izq")){
                                                                panelFlota.getInformacionJuego().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                                setEscuchaCasillas("agregar");
                                                                panelFlota.setSentidoOrientacion(4);
                                                                estadoJuego = 4;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int auxiliar = 0; // Variable para indicar cuando se debe terminar el primer ciclo
            switch(estadoJuego){
                case 4:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == panelTablero.getTablero("posicion").getMatriz()[row][col]) {
                                // Condicional para saber si el usuario pudo colocar el barco
                                if(pintarFlota.funcionesFlota(panelFlota.getNombreBoton(), panelFlota.getOrientacion(), panelFlota.getSentidoOrientacion(), col, row)){
                                    if(panelFlota.cantidadTotalNaves() > 0){
                                        setEscuchaCasillas("remover");
                                        panelFlota.getInformacionJuego().setText("Escoge otro barco");
                                        estadoJuego = 1;
                                    }else{
                                        setEscuchaCasillas("remover");
                                        combate.usuarioVsOponente();
                                        combate.oponenteVsUsuario();
                                        setEscuchaCasillasPrincipal("agregar");
                                        estadoJuego = 5;
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if(auxiliar == 1){
                            break;
                        }
                    }
                    break;
                case 5:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if(e.getSource() == panelTablero.getTablero("principal").getMatriz()[row][col]) {
                                // Verifica si la casilla seleccionada hay un barco oponente
                                if(panelTablero.getTablero("principal").getCasillasOcupadas().get(panelTablero.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(1)){
                                    // Verifica si todas las casillas del barco fueron seleccionadas
                                    if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]) != Integer.valueOf(0)){
                                        for(int num=1; num < 11; num++){
                                            if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))){
                                                funcionesCombate(row, col, "portavion" + String.valueOf(num));
                                                break;
                                            }else{
                                                if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))){
                                                    funcionesCombate(row, col, "submarino" + String.valueOf(num));
                                                    break;
                                                }else{
                                                    if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))){
                                                        funcionesCombate(row, col, "destructor" + String.valueOf(num));
                                                        break;
                                                    }else{
                                                        if(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getCasillaNombreBarco().get(ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))){
                                                            funcionesCombate(row, col, "fragata" + String.valueOf(num));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    if(panelTablero.getTablero("principal").getCasillasOcupadas().get(panelTablero.getTablero("principal").getMatriz()[row][col]) == Integer.valueOf(2)){
                                        panelFlota.getInformacionJuego().setText("Casilla usada, presiona otra");
                                        estadoJuego = 5;
                                    }else{
                                        panelFlota.getInformacionJuego().setText("Le diste al agua, espera el turno del oponente");
                                        panelTablero.getTablero("principal").getCasillasOcupadas().put(panelTablero.getTablero("principal").getMatriz()[row][col], Integer.valueOf(2));
                                        ventanaOponente.getPanelTableroOponente().getTableroOponente("posicion").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        panelTablero.getTablero("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/recursos/agua.png")));
                                        estadoJuego = 6;
                                        panelFlota.getAsignarTurno().setText("¡Turno del oponente!");
                                        timer.start();
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if(auxiliar == 1){
                            break;
                        }
                    }
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}
