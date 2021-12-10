/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package agentes;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author macario
 */
public class Agente extends Thread
{
    String nombre;
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];
    
    JLabel casillaAnterior;
    Random aleatorio = new Random(System.currentTimeMillis());
    
    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][])
    {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;

        
        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);        
    }
    
    public void run()
    {
        
        int dirRow=1;
        int dirCol=1;


        while(true)
        {

            casillaAnterior = tablero[i][j];
            
            movimientoRandom();
            siChoquesBordes();
                            
            actualizarPosicion();
                
            try
            {
               sleep(100+aleatorio.nextInt(100));
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

                      
    }
    
    public synchronized void actualizarPosicion()
    {
        casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
        System.out.println(nombre + " in -> Row: " + i + " Col:"+ j);              
    }
    
    public void movimientoRandom () {
        Random random = new Random();
        
        int opcMovimiento = random.nextInt(4) + 1;
        
        switch (opcMovimiento) {
            case 1 -> moverArriba();
            case 2 -> moverAbajo();
            case 3 -> moverDerecha();
            case 4 -> moverIzquierda();
            default -> {
            }
        }
    }
    
    public int moverArriba() {
        i = i - 1;
        return i;
    }

    public int moverAbajo() {
        i = i + 1;
        return i;
    }

    public int moverIzquierda() {
        j = j - 1;
        return j;
    }

    public int moverDerecha() {
        j = j + 1;
        return j;
    }
    
    public void siChoquesBordes() {
        if(i > matrix.length-2)
            chocaAbajo();
        if(i < 1 )
            chocaArriba();
        if(j > matrix.length-2) 
            chocaDerecha();
        if(j < 1 )
            chocaIzquierda();
    }
    
    public int chocaAbajo() {
        i = i - 1;
        return i;
    }
    
    public int chocaArriba() {
        i = i + 1;
        return i;
    }
    
    public int chocaDerecha() {
        j = j - 1;
        return j;
    }
    
    public int chocaIzquierda() {
        j = j + 1;
        return j;
    }
}
