package agentes;

import java.awt.Point;
import java.util.ArrayList;
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
    int traeMuestra ;
    private int contadorMuestras;
    private Agente agenteDos;
    
    ImageIcon sampleIcon = new ImageIcon("imagenes/sample.png");
    ImageIcon motherIcon = new ImageIcon("imagenes/mothership.png");

    JLabel casillaAnterior;
    int idIconoAnterior;
    Random aleatorio = new Random();

    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][]) {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;

        this.i = aleatorio.nextInt(matrix.length)-1;
        this.j = aleatorio.nextInt(matrix.length)-1;
        tablero[i][j].setIcon(icon);
        
    }

    public void run() {

        int dirRow = 1;
        int dirCol = 1;

        while (true) {
            
            System.out.println("contador muestras "+ contadorMuestras);
            
            casillaAnterior = tablero[i][j];
            idIconoAnterior = matrix[i][j];

            movimientoRandom();
            siChoquesBordes();
            
            if (detectaMuestra(i, j) && traeMuestra != 1) {
                System.out.println("recogiendo muestra");
                recogeMuestra();
                disminuirContadorMuestras();
            }
            
            if(traeMuestra == 1 && estaEnNave()) {
                System.out.println("dejandomuestra");
                traeMuestra = 0;
            }

            actualizarPosicion();

            try {
                sleep(100 + aleatorio.nextInt(100));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    public synchronized void actualizarPosicion() {
        if(idIconoAnterior == 0) {
            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        }
        if (idIconoAnterior == 3) {
            sampleIcon = new ImageIcon(sampleIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
            casillaAnterior.setIcon(sampleIcon);
        }
        if(idIconoAnterior == 2){
            motherIcon = new ImageIcon(motherIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
            casillaAnterior.setIcon(motherIcon);
        }  
            tablero[i][j].setIcon(icon); // Pone su figura en la nueva casilla
        System.out.println(nombre + " in -> Row: " + i + " Col:" + j);
    }
    
    public synchronized void disminuirContadorMuestras() {
        contadorMuestras--;
    }

    public void movimientoRandom() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(4) + 1;

        switch (opcMovimiento) {
            case 1:
                if ( i - 1 > -1)
                    moverArriba();
                break;
            case 2:
                    moverAbajo();
                break;
            case 3:
                    moverDerecha();
                break;
            case 4:
                if ( j - 1 > -1)
                    moverIzquierda();
                break;
        }
    }
    public boolean detectaMuestra(int i, int j) {
        if(matrix[i][j] == 3 ) {
            return true;
        }
        return false;
    }
    
    public void recogeMuestra () {
        matrix[i][j] = 0;
        System.out.println("recogio la muestra");
        traeMuestra = 1;
    }
    
    public boolean estaEnNave () {
        if (matrix[i][j] == 2) {
            return true;
        }
        return false;
    }
    
    public void viajarNave() {
        
    }

    public void moverArriba() {
        if (matrix[i - 1][j] != 1)
            i = i - 1;
    }

    public void moverAbajo() {
        if (matrix[i + 1][j] != 1)
            i = i + 1;
    }

    public void moverIzquierda() {
        if (matrix[i][j - 1] != 1)
            j = j - 1;
    }

    public void moverDerecha() {
        if (matrix[i][j + 1] != 1)
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

    public int getContadorMuestras() {
        return contadorMuestras;
    }    
    
}
