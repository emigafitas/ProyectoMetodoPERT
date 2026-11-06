/*
 * ITESS - TICS
 * Materia: Matematicas Para la Toma de Decisiones
 * Tema: PERT / CPM
 * Descripcion: Calcular tiempos, holguras y ruta critica
 * Integrantes del proyecto:
 * Emily Navarrete Garcia
 * Axel Martin Perez Maldonado 
 * Alexandra Moncada Lopez
 * 
 * 
 * javac MetodoPERT.java
*  java MetodoPERT
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MetodoPERT {

    static final double EPSILON = 0.0000001;

    static class Actividad {

        String nombre;
        ArrayList<String> nombresPredecesores = new ArrayList<>();
        ArrayList<Actividad> predecesores = new ArrayList<>();
        ArrayList<Actividad> sucesores = new ArrayList<>();

        double O, M, P;
        double TE, varianza;
        double ES, EF, LS, LF, holgura;

        boolean esCritica;

        Actividad(String nombre, double O, double M, double P) {

            this.nombre = nombre;
            this.O = O;
            this.M = M;
            this.P = P;

            TE = (O + 4 * M + P) / 6;

            double diferencia = (P - O) / 6;
            varianza = diferencia * diferencia;
        }
    }

    ArrayList<Actividad> actividades = new ArrayList<>();
    ArrayList<Actividad> orden = new ArrayList<>();
    ArrayList<ArrayList<Actividad>> rutasCriticas = new ArrayList<>();

    double duracionProyecto = 0;

    public void leerDatos() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("pert.txt"));

        String linea;

        while ((linea = br.readLine()) != null) {

            linea = linea.trim();

            if (linea.isEmpty() || linea.startsWith("#")) {
                continue;
            }

            String[] datos = linea.split("\\s+");

            if (datos.length != 5) {
                throw new IllegalArgumentException("Error en una linea del archivo.");
            }

            String nombre = datos[0].toUpperCase();

            double O = Double.parseDouble(datos[2]);
            double M = Double.parseDouble(datos[3]);
            double P = Double.parseDouble(datos[4]);

            if (O < 0 || O > M || M > P) {
                throw new IllegalArgumentException("Tiempos incorrectos en " + nombre);
            }

            if (buscarActividad(nombre) != null) {
                throw new IllegalArgumentException("Actividad repetida: " + nombre);
            }

            Actividad nueva = new Actividad(nombre, O, M, P);

            if (!datos[1].equalsIgnoreCase("na") &&
                !datos[1].equals("-")) {

                String[] anteriores = datos[1].split(",");

                for (String anterior : anteriores) {
                    nueva.nombresPredecesores.add(anterior.toUpperCase());
                }
            }

            actividades.add(nueva);
        }

        br.close();

        if (actividades.isEmpty()) {
            throw new IllegalArgumentException("El archivo no tiene actividades.");
        }
    }

    public Actividad buscarActividad(String nombre) {

        for (Actividad actividad : actividades) {

            if (actividad.nombre.equals(nombre)) {
                return actividad;
            }
        }

        return null;
    }

    public void relacionarActividades() {

        for (Actividad actividad : actividades) {

            for (String nombre : actividad.nombresPredecesores) {

                Actividad anterior = buscarActividad(nombre);

                if (anterior == null) {
                    throw new IllegalArgumentException(
                        "No existe la actividad: " + nombre
                    );
                }

                if (anterior == actividad) {
                    throw new IllegalArgumentException(
                        "Una actividad no puede depender de si misma."
                    );
                }

                if (actividad.predecesores.contains(anterior)) {
                    throw new IllegalArgumentException(
                        "Predecesora repetida en " + actividad.nombre
                    );
                }

                actividad.predecesores.add(anterior);
                anterior.sucesores.add(actividad);
            }
        }
    }

    public void ordenarActividades() {

        while (orden.size() < actividades.size()) {

            boolean encontro = false;

            for (Actividad actividad : actividades) {

                if (orden.contains(actividad)) {
                    continue;
                }

                boolean lista = true;

                for (Actividad anterior : actividad.predecesores) {

                    if (!orden.contains(anterior)) {
                        lista = false;
                        break;
                    }
                }

                if (lista) {
                    orden.add(actividad);
                    encontro = true;
                }
            }

            if (!encontro) {
                throw new IllegalArgumentException(
                    "Error: existen ciclos en las actividades."
                );
            }
        }
    }

    public void calcularTempranos() {

        duracionProyecto = 0;

        for (Actividad actividad : orden) {

            actividad.ES = 0;

            for (Actividad anterior : actividad.predecesores) {

                if (anterior.EF > actividad.ES) {
                    actividad.ES = anterior.EF;
                }
            }

            actividad.EF = actividad.ES + actividad.TE;

            if (actividad.EF > duracionProyecto) {
                duracionProyecto = actividad.EF;
            }
        }
    }

    public void calcularTardios() {

        for (int i = orden.size() - 1; i >= 0; i--) {

            Actividad actividad = orden.get(i);

            if (actividad.sucesores.isEmpty()) {

                actividad.LF = duracionProyecto;

            } else {

                actividad.LF = Double.MAX_VALUE;

                for (Actividad siguiente : actividad.sucesores) {

                    if (siguiente.LS < actividad.LF) {
                        actividad.LF = siguiente.LS;
                    }
                }
            }

            actividad.LS = actividad.LF - actividad.TE;

            actividad.holgura = actividad.LS - actividad.ES;

            actividad.esCritica =
                Math.abs(actividad.holgura) < EPSILON;
        }
    }

    public void buscarRutasCriticas() {

        rutasCriticas.clear();

        for (Actividad actividad : actividades) {

            if (actividad.predecesores.isEmpty() &&
                actividad.esCritica) {

                ArrayList<Actividad> camino = new ArrayList<>();

                buscarRuta(actividad, camino);
            }
        }
    }

    public void buscarRuta(
        Actividad actual,
        ArrayList<Actividad> camino) {

        camino.add(actual);

        if (Math.abs(actual.EF - duracionProyecto) < EPSILON) {

            rutasCriticas.add(new ArrayList<>(camino));

        } else {

            for (Actividad siguiente : actual.sucesores) {

                if (siguiente.esCritica &&
                    Math.abs(actual.EF - siguiente.ES) < EPSILON) {

                    buscarRuta(siguiente, camino);
                }
            }
        }

        camino.remove(camino.size() - 1);
    }

    public void mostrarMatrizInicial() {

        System.out.println("\n========== MATRIZ INICIAL ==========");

        System.out.printf("%-12s %8s %8s %8s %10s %12s%n",
            "Actividad", "O", "M", "P", "TE", "Varianza");

        for (Actividad a : actividades) {

            System.out.printf("%-12s %8.2f %8.2f %8.2f %10.2f %12.4f%n",
                a.nombre, a.O, a.M, a.P, a.TE, a.varianza);
        }
    }

    public void mostrarSegundaMatriz() {

        System.out.println("\n========== MATRIZ PERT / CPM ==========");

        System.out.printf(
            "%-10s %7s %7s %7s %7s %7s %10s %12s %10s%n",
            "Actividad", "TE", "ES", "EF", "LS", "LF",
            "Holgura", "Varianza", "Estado"
        );

        for (Actividad a : orden) {

            String estado = a.esCritica ? "Critica" : "No critica";

            System.out.printf(
                "%-10s %7.2f %7.2f %7.2f %7.2f %7.2f %10.2f %12.4f %10s%n",
                a.nombre, a.TE, a.ES, a.EF, a.LS, a.LF,
                a.holgura, a.varianza, estado
            );
        }
    }

    public void mostrarResultados() {

        System.out.println("\n========== RESULTADOS ==========");

        System.out.println("Duracion del proyecto: " + duracionProyecto);

        System.out.println("Cantidad de rutas criticas: " + rutasCriticas.size());

        for (int i = 0; i < rutasCriticas.size(); i++) {

            ArrayList<Actividad> ruta = rutasCriticas.get(i);

            double varianzaRuta = 0;

            System.out.print("\nRuta critica " + (i + 1) + ": ");

            for (int j = 0; j < ruta.size(); j++) {

                Actividad a = ruta.get(j);

                System.out.print(a.nombre);

                if (j < ruta.size() - 1) {
                    System.out.print(" -> ");
                }

                varianzaRuta += a.varianza;
            }

            double desviacion = Math.sqrt(varianzaRuta);

            System.out.println("\nVarianza: " + varianzaRuta);
            System.out.println("Desviacion estandar: " + desviacion);
        }
    }

    public void resuelve() {

        calcularTempranos();
        calcularTardios();
        buscarRutasCriticas();

        mostrarMatrizInicial();
        mostrarSegundaMatriz();
        mostrarResultados();
    }

    public static void main(String[] args) {

        MetodoPERT programa = new MetodoPERT();

        try {

            programa.leerDatos();
            programa.relacionarActividades();
            programa.ordenarActividades();
            programa.resuelve();

        } catch (IOException e) {

            System.out.println("No se pudo leer el archivo pert.txt");

        } catch (NumberFormatException e) {

            System.out.println("Error: hay un dato numerico incorrecto.");

        } catch (IllegalArgumentException e) {

            System.out.println(e.getMessage());
        }
    }
}