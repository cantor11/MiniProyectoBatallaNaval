package proyect;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Clase PintarFlota
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 19/3/2022
 */
public class PintarFlota {
    private PanelTablero panelTablero;
    private PanelFlota panelFlota;
    private int barcoUsado; // Acumulador para identificar cuál nave ha sido desplegada (en orden del 1 al 10)
    private ArrayList<Integer> casillasUsadasBarco; // casillas usadas por cada nave

    /**
     * Constructor de la clase PintarFlota
     * @param _panelTablero
     * @param _panelFlota
     */
    public PintarFlota(PanelTablero _panelTablero, PanelFlota _panelFlota){
        this.panelTablero = _panelTablero;
        this.panelFlota = _panelFlota;
        barcoUsado = 1;
        casillasUsadasBarco = new ArrayList<>();
    }

    /**
     * Retorna la dirección de la imagen dependiendo del barco ingresado
     * @param barco
     * @param estadoOrientacion
     * @param estadoSentidoOrientacion
     * @return String
     */
    public String pathImages(String barco, int estadoOrientacion, int estadoSentidoOrientacion){
        String path = "";
        if(estadoOrientacion == 0){
            switch(estadoSentidoOrientacion){
                case 1:
                    path = "/recursos/" + barco + "_V_S_I/";
                    break;
                case 2:
                    path = "/recursos/" + barco + "_V_I_S/";
                    break;
            }
        }else{
            switch(estadoSentidoOrientacion){
                case 3:
                    path = "/recursos/" + barco + "_H_I_D/";
                    break;
                case 4:
                    path = "/recursos/" + barco + "_H_D_I/";
                    break;
            }
        }

        return path;
    }

    /**
     * Relaciona la casilla y la cantidad de casillas que usa el barco ingresado
     * @param casilla
     * @param barco
     * @param numeroBarco
     */
    public void relacionJLabelBarco(JLabel casilla, String barco, int numeroBarco){
        if(barco.equals("portavion" + String.valueOf(numeroBarco))){
            casillasUsadasBarco.add(4);
            panelTablero.getTablero("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
        }else{
            if(barco.equals("submarino" + String.valueOf(numeroBarco))){
                casillasUsadasBarco.add(3);
                panelTablero.getTablero("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
            }else{
                if(barco.equals("destructor" + String.valueOf(numeroBarco))){
                    casillasUsadasBarco.add(2);
                    panelTablero.getTablero("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
                }else{
                    if(barco.equals("fragata" + String.valueOf(numeroBarco))){
                        casillasUsadasBarco.add(1);
                        panelTablero.getTablero("posicion").getCasillaBarco().put(casilla, casillasUsadasBarco.get(casillasUsadasBarco.size()-1));
                    }
                }
            }
        }
    }

    /**
     * Pinta el barco en las respectivas casillas del tablero posición
     * @param barco
     * @param estadoOrientacion
     * @param estadoSentidoOrientacion
     * @param col
     * @param row
     * @return boolean
     */
    public boolean funcionesFlota(String barco, int estadoOrientacion, int estadoSentidoOrientacion, int col, int row){
        int casillasAUsar;
        int casillasUsadas = 0;
        int columnaReferencia = 0;
        int filaReferencia = 0;
        int nextImage;
        boolean auxiliar = false; // false si no pudo colocar el barco, de lo contrario true

        if(barco == "portavion"){
            casillasAUsar = 4;
        }else{
            if(barco == "submarino"){
                casillasAUsar = 3;
            }else{
                if(barco == "destructor"){
                    casillasAUsar = 2;
                }else{
                    casillasAUsar = 1;
                }
            }
        }

        if(estadoOrientacion == 1){
            if(estadoSentidoOrientacion == 3){
                columnaReferencia = 10;
            }else{
                if(estadoSentidoOrientacion == 4){
                    columnaReferencia = 1;
                }
            }

            int ultimasCasillas = Math.abs(col - columnaReferencia);
            if(ultimasCasillas < casillasAUsar-1){
                panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
            }else{
                if(estadoSentidoOrientacion == 3){
                    nextImage = 1;
                    for(int casilla=col; casilla < col+casillasAUsar; casilla++){
                        if(panelTablero.getTablero("posicion").getCasillasOcupadas().get(panelTablero.getTablero("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=col; pic < col+casillasAUsar; pic++){
                            panelTablero.getTablero("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelTablero.getTablero("posicion").getCasillasOcupadas().put(panelTablero.getTablero("posicion").getMatriz()[row][pic], 1);
                            panelTablero.getTablero("posicion").getCasillaNombreBarco().put(panelTablero.getTablero("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelTablero.getTablero("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
                    }
                }else{
                    nextImage = casillasAUsar;
                    for(int casilla=col; casilla > col-casillasAUsar; casilla--){
                        if(panelTablero.getTablero("posicion").getCasillasOcupadas().get(panelTablero.getTablero("posicion").getMatriz()[row][casilla]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=col; pic > col-casillasAUsar; pic--){
                            panelTablero.getTablero("posicion").getMatriz()[row][pic].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelTablero.getTablero("posicion").getCasillasOcupadas().put(panelTablero.getTablero("posicion").getMatriz()[row][pic], 1);
                            panelTablero.getTablero("posicion").getCasillaNombreBarco().put(panelTablero.getTablero("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelTablero.getTablero("posicion").getMatriz()[row][pic], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
                    }
                }
            }
        }else{
            if(estadoSentidoOrientacion == 1){
                filaReferencia = 10;
            }else{
                if(estadoSentidoOrientacion == 2){
                    filaReferencia = 1;
                }
            }

            int ultimasCasillas = Math.abs(row - filaReferencia);
            if(ultimasCasillas < casillasAUsar-1){
                panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
            }else{
                if(estadoSentidoOrientacion == 1){
                    nextImage = 1;
                    for(int casilla=row; casilla < row+casillasAUsar; casilla++){
                        if(panelTablero.getTablero("posicion").getCasillasOcupadas().get(panelTablero.getTablero("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=row; pic < row+casillasAUsar; pic++){
                            panelTablero.getTablero("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelTablero.getTablero("posicion").getCasillasOcupadas().put(panelTablero.getTablero("posicion").getMatriz()[pic][col], 1);
                            panelTablero.getTablero("posicion").getCasillaNombreBarco().put(panelTablero.getTablero("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelTablero.getTablero("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage++;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
                    }
                }else{
                    nextImage = casillasAUsar;
                    for(int casilla=row; casilla > row-casillasAUsar; casilla--){
                        if(panelTablero.getTablero("posicion").getCasillasOcupadas().get(panelTablero.getTablero("posicion").getMatriz()[casilla][col]) == Integer.valueOf(1)) {
                            casillasUsadas++;
                        }
                    }

                    if(casillasUsadas == 0){
                        for(int pic=row; pic > row-casillasAUsar; pic--){
                            panelTablero.getTablero("posicion").getMatriz()[pic][col].setIcon(new ImageIcon(getClass().getResource(pathImages(barco, estadoOrientacion, estadoSentidoOrientacion) + String.valueOf(nextImage) + ".png")));
                            panelTablero.getTablero("posicion").getCasillasOcupadas().put(panelTablero.getTablero("posicion").getMatriz()[pic][col], 1);
                            panelTablero.getTablero("posicion").getCasillaNombreBarco().put(panelTablero.getTablero("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado));
                            relacionJLabelBarco(panelTablero.getTablero("posicion").getMatriz()[pic][col], barco + String.valueOf(barcoUsado), barcoUsado);
                            nextImage--;
                            auxiliar = true;
                        }
                        barcoUsado++;
                    }else{
                        panelFlota.getInformacionJuego().setText("No hay espacio para colocar el " + barco);
                    }
                }
            }
        }
        return auxiliar;
    }
}