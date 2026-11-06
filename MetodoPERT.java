/*
 * ITESS - TICS
 * Materia: Matematicas Para la Toma de Decisiones
 * Tema: PERT / CPM
 * Descripcion: Calcular tiempos, holguras y ruta critica
 * Integrantes del proyecto:
 * Emily Navarrete Garcia
 * 
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MetodoPERT {
    FastReader reader = new FastReader();

    static class Actividad {

        //estructura de datos 
        String nombre;
        String descripcion;
        List<String> nombresPredecesores;
        List<Actividad> predecesores;
        List<Actividad> sucesores;

        double O, M, P;
        double esperado, varianza;
        double ES, EF, LS, LF, holgura;
        boolean esCritica;

        Actividad(String nombre, String descripcion, double O, double M, double P) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.O = O;
            this.M = M;
            this.P = P;

            this.nombresPredecesores = new ArrayList<>();
            this.predecesores = new ArrayList<>();
            this.sucesores = new ArrayList<>():
               
        }
    }

    //variables globales, guardan toda la informacion del proyecto mientras se realizan los calculos

    int n;
    LinkedHashMap<String, Actividad> mapa = new LinkedHashMap<>();
    List<Actividad> actividades = new ArrayList<>();
    List<Activiadd> orden = new ArrayList<>();
    double duracionProyecto = 0;
    double varianzaRutaCritica - 0;
    List<String> rutaCritica = new ArrayList<>();

    //para leer mas rapido desde la consola
    static class FastReader {
        BufferedReader b;
        StringTokenizer s;

        public FastReader() {
            b = new BufferedReader(new InputStreamReader(System.in));
        }

        //lee separado por espacios cada palabra siguiente 
        String next() {
            while (s == null || !s.hasMoreElements()) {
                try {
                    String line = b.readLine();
                    if (line == null) return null;
                    s = new StringTokenizer(line);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return s.nextToken();
        }

        //lee una linea completa de texto hasta encontrar un salto de linea
        String nextLine() {
            String str = "";
            try {
                if (s != null && s.hasMoreTokens()) {
                    str = s.nextToken("\n";)
                } else {
                    str = b.readLine();
                }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return str;
            }
        }


         public void leerDatos() {
          

    }

    public void relacionarActividades() {
        
    } 

    public void ordenarActividades() {

    } 
    public void resuelve() {
    
        int resultado = numero * 2 + 1;
        System.out.println(resultado);
    }

    public static void main(String[] args) {
        MetodoPERT id  = new MetodoPERT();

        id.leerDatos();
        id.resuelve();
    }
}

    
    
