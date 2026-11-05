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

        //Estructura de datos 
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

    //
    
    
}