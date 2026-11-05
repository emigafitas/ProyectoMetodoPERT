/*
 * ITESS - TICS
 * Materia: Matematicas Para la Toma de Decisiones
 * Tema: PERT / CPM
 * Descripcion: Calcular tiempos, holguras y ruta critica
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Locale;

public class MetodoPERT {
    static class Actividad {
        String nombre;
        String descripcion;
        List<String> nombresPredecesores;
        List<Actividad> predecesores;
        List<Actividad> sucesores;

        double O, M, P;
        double esperado, varianza;
        double ES, EF, LS, LF, holgura;

        Actividad(String nombre, String descripcion, double O, double M, double P) {
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.O = O;
            this.M = M;
            this.P = P;

            nombresPredecesores = new ArrayList<>();
            predecesores = new ArrayList<>();
            sucesores = new ArrayList<>():
               
        }
    }

    static List <Actividad> actividades = new ArrayList<>();
    static Map <String, Actividad> mapa = HashMap<>();
}