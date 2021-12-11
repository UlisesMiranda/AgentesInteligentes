package agentes;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Agente extends Thread {

    String nombre;
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];

    JLabel casillaAnterior;
    Random aleatorio = new Random();

    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][]) {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;

        this.i = aleatorio.nextInt(matrix.length);
        this.j = aleatorio.nextInt(matrix.length);
        tablero[i][j].setIcon(icon);
    }

    public void run() {

        int dirRow = 1;
        int dirCol = 1;

        while (true) {

            casillaAnterior = tablero[i][j];

            movimientoRandom();
            siChoquesBordes();

            actualizarPosicion();

            try {
                sleep(100 + aleatorio.nextInt(100));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public synchronized void actualizarPosicion() {
        casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
        System.out.println(nombre + " in -> Row: " + i + " Col:" + j);
    }

    public void movimientoRandom() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(4) + 1;

        switch (opcMovimiento) {
            case 1:
                if ( i - 1 > -1)
                    if (matrix[i - 1][j] == 0) {
                        moverArriba();
                    }
                break;
            case 2:
                if (matrix[i + 1][j] == 0) {
                    moverAbajo();
                }
                break;
            case 3:
                if (matrix[i][j + 1] == 0) {
                    moverDerecha();
                }
                break;
            case 4:
                if ( j - 1 > -1)
                    if (matrix[i][j - 1] == 0) {
                        moverIzquierda();
                    }
        }
    }

    public void moverArriba() {

        i = i - 1;
    }

    public void moverAbajo() {
        i = i + 1;

    }

    public void moverIzquierda() {
        j = j - 1;

    }

    public void moverDerecha() {
        j = j + 1;
    }

    public void siChoquesBordes() {
        if (i > matrix.length - 2) {
            chocaAbajo();
        }
        if (i < 0) {
            chocaArriba();
        }
        if (j > matrix.length - 2) {
            chocaDerecha();
        }
        if (j < 0) {
            chocaIzquierda();
        }
    }

    public void chocaAbajo() {
        i = i - 1;
    }

    public void chocaArriba() {
        i = i + 1;
    }

    public void chocaDerecha() {
        j = j - 1;
    }

    public void chocaIzquierda() {
        j = j + 1;
    }
}
