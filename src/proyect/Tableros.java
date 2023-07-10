package proyect;

import javax.swing.*;
import java.util.HashMap;

/**
 * Clase Tableros
 * @autor Mayra Alejandra Sanchez - mayra.alejandra.sanchez@correounivalle.edu.co - 202040506
 * @autor Brayan Stiven Sanchez - brayan.sanchez.leon@correounivalle.edu.co - 202043554
 * @version 1.0.0 fecha 19/3/2022
 */
public class Tableros {
    private JLabel tablero[][];
    private HashMap<JLabel, Integer> casillasOcupadas; // Verifica si la casilla esta ocupada por una nave, 1 si esta ocupado, 2 si fue atacado
    private HashMap<JLabel, Integer> casillaBarco; // Relacion entre la casilla y las casillas que usa la nave
    private HashMap<JLabel, String> casillaNombreBarco; // Almacena el nombre del barco ubicado en la casilla

    /**
     * Constructor de la clase Tableros
     */
    public Tableros(){
        tablero = new JLabel[11][11];
        casillasOcupadas = new HashMap<>();
        casillaBarco = new HashMap<>();
        casillaNombreBarco = new HashMap<>();
    }

    /**
     * Reduce la cantidad de casillas ocupadas por los barcos para que sea hundido
     * @param barco
     */
    public void reducirCasillasUsadas(String barco){
        for (int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(casillaNombreBarco.get(tablero[row][col]) != null){
                    if(casillaNombreBarco.get(tablero[row][col]).equals(barco)){
                        casillaBarco.replace(tablero[row][col], casillaBarco.get(tablero[row][col])-1);
                    }
                }
            }
        }
    }

    /**
     * Retorna la matriz
     * @return JLabel[][]
     */
    public JLabel[][] getMatriz(){
        return tablero;
    }

    /**
     * Retorna el map casillasOcupadas
     * @return HashMap
     */
    public HashMap getCasillasOcupadas(){
        return casillasOcupadas;
    }

    /**
     * Retorna el map casillaBarco
     * @return HashMap
     */
    public HashMap getCasillaBarco(){
        return casillaBarco;
    }

    /**
     * Retorna el map casillaNombreBarco
     * @return HashMap
     */
    public HashMap getCasillaNombreBarco(){
        return casillaNombreBarco;
    }
}