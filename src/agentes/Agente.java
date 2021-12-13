package agentes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Agente extends Thread {

    String nombre;
    int i;
    int j;
    ImageIcon icon;
    int[][] matrix;
    JLabel tablero[][];
    int traeMuestra;
    private int contadorMuestras;
    private Agente agenteDos;

    ImageIcon sampleIcon = new ImageIcon("imagenes/sample.png");
    ImageIcon motherIcon = new ImageIcon("imagenes/mothership.png");

    JLabel casillaAnterior;
    int idIconoAnterior, radio;
    Random aleatorio = new Random();

    public Agente(String nombre, ImageIcon icon, int[][] matrix, JLabel tablero[][]) {
        this.nombre = nombre;
        this.icon = icon;
        this.matrix = matrix;
        this.tablero = tablero;
        
        this.i = aleatorio.nextInt(matrix.length-1);
        this.j = aleatorio.nextInt(matrix.length-1);
        tablero[i][j].setIcon(icon);

    }

    public void run() {

        int dirRow = 1;
        int dirCol = 1;

        while (true) {

            casillaAnterior = tablero[i][j];
            idIconoAnterior = matrix[i][j];
            
            movimientoRandom();
            actualizarPosicion();
            

            if (traeMuestra == 1) {
                try {
                    Point point = BuscarNave();
                    viajarANave(point.x, point.y);
                    siChoquesBordes();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Agente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            if (detectaMuestra(i, j) && traeMuestra != 1) {
                System.out.println("recogiendo muestra");
                recogeMuestra();
            }
            

            if (traeMuestra == 1 && estaEnNave()) {
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
        if (idIconoAnterior == 0) {
            casillaAnterior.setIcon(null); // Elimina su figura de la casilla anterior
        }
        if (idIconoAnterior == 3) {
            sampleIcon = new ImageIcon(sampleIcon.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH));
            casillaAnterior.setIcon(sampleIcon);
        }
        if (idIconoAnterior == 2) {
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
                if (i - 1 > -1) {
                    moverArriba();
                }
                break;
            case 2:
                moverAbajo();
                break;
            case 3:
                moverDerecha();
                break;
            case 4:
                if (j - 1 > -1) {
                    moverIzquierda();
                }
                break;
        }
    }

    public void movimientoRandomSinArriba() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(3) + 1;

        switch (opcMovimiento) {
            case 1:
                moverAbajo();
                break;
            case 2:
                moverDerecha();
                break;
            case 3:
                if (j - 1 > -1) {
                    moverIzquierda();
                }
                break;
        }
    }

    public void movimientoRandomSinAbajo() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(3) + 1;

        switch (opcMovimiento) {
            case 1:
                if (i - 1 > -1) {
                    moverArriba();
                }
                break;
            case 2:
                moverDerecha();
                break;
            case 3:
                if (j - 1 > -1) {
                    moverIzquierda();
                }
                break;
        }
    }

    public void movimientoRandomsinDerecha() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(3) + 1;

        switch (opcMovimiento) {
            case 1:
                if (i - 1 > -1) {
                    moverArriba();
                }
                break;
            case 2:
                moverAbajo();
                break;
            case 3:
                if (j - 1 > -1) {
                    moverIzquierda();
                }
                break;
        }
    }

    public void movimientoRandomSinIzquierda() {
        Random random = new Random();

        int opcMovimiento = random.nextInt(4) + 1;

        switch (opcMovimiento) {
            case 1:
                if (i - 1 > -1) {
                    moverArriba();
                }
                break;
            case 2:
                moverAbajo();
                break;
            case 3:
                moverDerecha();
                break;
        }
    }

    public boolean detectaMuestra(int i, int j) {
        if (matrix[i][j] == 3) {
            return true;
        }
        return false;
    }

    public void recogeMuestra() {
        matrix[i][j] = 0;
        System.out.println("recogio la muestra");
        traeMuestra = 1;
    }

    public boolean estaEnNave() {
        if (matrix[i][j] == 2) {
            return true;
        }
        return false;
    }

    public Point BuscarNave() {
        ArrayList<Point> coordenadas = new ArrayList<>(); 
        radio = 0; 

        while (true) {
            radio++;

            for (int d = 0; d <= radio; d++) {
                if (condicionExiste(i + d, j - radio) && matrix[i + d][j - radio] == 2) {
                    coordenadas.add(new Point(d, -radio));
                }
                if (condicionExiste(i - d, j + radio) && matrix[i - d][j + radio] == 2) {
                    coordenadas.add(new Point(-d, radio));
                }
                if (condicionExiste(i - radio, j - d) && matrix[i - radio][j - d] == 2) {
                    coordenadas.add(new Point(-radio, -d));
                }
                if (condicionExiste(i + radio, j + d) && matrix[i + radio][j + d] == 2) {
                    coordenadas.add(new Point(radio, d));
                }
                if (d != 0 || d != radio) {
                    if (condicionExiste(i - d, j - radio) && matrix[i - d][j - radio] == 2) {
                        coordenadas.add(new Point(-d, -radio));
                    }
                    if (condicionExiste(i + d, j + radio) && matrix[i + d][j + radio] == 2) {
                        coordenadas.add(new Point(d, radio));
                    }
                    if (condicionExiste(i - radio, j + d) && matrix[i - radio][j + d] == 2) {
                        coordenadas.add(new Point(-radio, d));
                    }
                    if (condicionExiste(i + radio, j - d) && matrix[i + radio][j - d] == 2) {
                        coordenadas.add(new Point(radio, -d));
                    }
                }
                if (!coordenadas.isEmpty()) {
                    Random random = new Random();
                    return coordenadas.get(random.nextInt(coordenadas.size()));
                }
            }
        }
    }

    private boolean condicionExiste(int x, int y) {
        if (0 <= x && x < matrix.length - 2 && 0 <= y && y < matrix.length - 2) {
            return true;
        } else {
            return false;
        }
    }

    public void viajarANave(int filaBuscada, int columnaBuscada) throws InterruptedException {
        if (filaBuscada != 0 && columnaBuscada != 0) {
            moverFila(filaBuscada);
            moverColumna(columnaBuscada);
            return;
        }

        if (filaBuscada == 0) {
            moverColumna(columnaBuscada);
            return;
        }

        if (columnaBuscada == 0) {
            moverFila(filaBuscada);
            return;
        }
    }

    private void moverColumna(int columna) throws InterruptedException {
        if (columna > 0) {
            while (columna-- != 0) {
                
                if (noObstaculoDerecha()) {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    siChoquesBordes();
                    moverDerecha();
                } else {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    movimientoRandomsinDerecha();
                    columna++;
                }
                actualizarPosicion();
            }
        } else {
            while (columna++ != 0) {
                if (noObstaculoIzquierda()) {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    siChoquesBordes();
                    moverIzquierda();
                } else {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    movimientoRandomSinIzquierda();
                    columna--;
                }
                actualizarPosicion();

            }
        }
    }

    private void moverFila(int fila) throws InterruptedException {
        if (fila > 0) {
            while (fila-- != 0) {
                
                if (noObstaculoAbajo()) {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    siChoquesBordes();
                    moverAbajo();
                } else {
                    casillaAnterior = tablero[i][j];
                    idIconoAnterior = matrix[i][j];
                    
                    movimientoRandomSinAbajo();
                    fila++;
                }
                actualizarPosicion();
            }
        } else {
            if (fila < 0) {
                while (fila++ != 0) {
                    if (noObstaculoArriba()) {
                        casillaAnterior = tablero[i][j];
                        idIconoAnterior = matrix[i][j];
                    
                        siChoquesBordes();
                        moverArriba();
                    } else {
                        casillaAnterior = tablero[i][j];
                        idIconoAnterior = matrix[i][j];
                        
                        movimientoRandomSinArriba();
                        fila--;
                    }
                    actualizarPosicion();
                }
            }
        }
    }

    public boolean noObstaculoArriba() {
        return (matrix[i - 1][j] != 1 && i-1 >= 0);
    }

    public boolean noObstaculoAbajo() {
        return (matrix[i + 1][j] != 1 &&  i +1 < matrix.length - 2);
    }

    public boolean noObstaculoIzquierda() {
        return (matrix[i][j - 1] != 1 && j - 1>=0);
    }

    public boolean noObstaculoDerecha() {
        return (matrix[i][j + 1] != 1 && j+1 <  matrix.length - 1);
    }

    public void moverArriba() {
        if (noObstaculoArriba()) {
            i = i - 1;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
    }

    public void moverAbajo() {
        if (noObstaculoAbajo()) {
            i = i + 1;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
    }

    public void moverIzquierda() {
        if (noObstaculoIzquierda()) {
            j = j - 1;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
    }

    public void moverDerecha() {
        if (noObstaculoDerecha()) {
            j = j + 1;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }        
    }

    public void siChoquesBordes() {
        if (i > matrix.length - 2) {
            chocaAbajo();
        }
        if (i <= 0) {
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
