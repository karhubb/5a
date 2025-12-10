/***************************************************************************
 * Programa:  Program 6 - Encontrar x para Distribución t-Student
 * Clase:     Logic
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Clase controladora principal del programa Program 6. Coordina:
 *     1. Lectura interactiva de parámetros p y dof desde consola
 *     2. Ejecución del algoritmo de búsqueda para encontrar x
 *     3. Presentación de resultados en consola y archivo Out5.txt
 *
 * Parámetros Requeridos (Program 6):
 *     1. p (valor de la integral objetivo, 0 < p < 1)
 *     2. dof (grados de libertad, entero > 0)
 *     3. Error aceptable (ej: 0.00001)
 *
 * Archivo de Salida:
 *   - Out5.txt: Contiene los resultados completos de la ejecución
 *
 * Reuse Instructions:
 *   - Esta clase coordina el flujo del programa
 *   - Modificar solo si cambia el flujo principal
 *   - Para cambios en el algoritmo de búsqueda, modificar SearchAlgorithm
 ***************************************************************************/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase Logic.
 * <p>
 * Implementa la lógica de control principal del programa Program 6.
 * Proporciona métodos para ejecución interactiva con entrada directa
 * de parámetros, presentación de resultados y escritura a archivo.
 */
public class Logic5a
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Objeto para manejo de entrada de datos desde consola */
    private Data objData;
    
    /** Formato de fecha para archivo de salida */
    private SimpleDateFormat objDateFormat;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase Logic.
     * <p>
     * Inicializa los componentes necesarios para la ejecución
     * del programa Program 6.
     */
    public Logic5a()
    {
        // Inicializar objeto para entrada de datos
        this.objData = new Data();
        
        // Inicializar formato de fecha para archivos
        this.objDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    }  // Fin del constructor
    
    /*------------------------------------------------------------------*/
    /*  Public Methods                                                  */
    /*------------------------------------------------------------------*/
    
    /**
     * Método principal de ejecución del programa.
     * <p>
     * Coordina el flujo completo del programa Program 6:
     *     1. Mostrar encabezado del programa
     *     2. Obtener parámetros de entrada (p, dof, error)
     *     3. Configurar y ejecutar algoritmo de búsqueda
     *     4. Presentar resultados en formato específico
     *     5. Guardar resultados en archivo Out5.txt
     *
     * @throws Exception si ocurre error durante la ejecución
     */
    public void execute() throws Exception
    {
        /*--------------------------------------------------------------*/
        /*  Mostrar encabezado del programa                             */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   PROGRAM 6: ENCONTRAR X PARA DISTRIBUCIÓN t-STUDENT");
        System.out.println("=".repeat(70));
        
        /*--------------------------------------------------------------*/
        /*  Ejecutar modo interactivo directamente                      */
        /*--------------------------------------------------------------*/
        
        executeInteractiveMode();  // Ejecutar directamente sin menú
        
    }  // Fin del método execute
    
    /**
     * Ejecuta el modo interactivo del programa.
     * <p>
     * Solicita al usuario los parámetros requeridos (p, dof, error),
     * ejecuta el algoritmo de búsqueda, muestra resultados y guarda en archivo.
     *
     * @throws Exception si ocurre error durante la ejecución
     */
    public void executeInteractiveMode() throws Exception
    {
        /*--------------------------------------------------------------*/
        /*  Mostrar encabezado del modo interactivo                     */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("   ENTRADA INTERACTIVA - PROGRAM 6");
        System.out.println("-".repeat(50));
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Obtener parámetros de entrada interactivamente              */
        /*--------------------------------------------------------------*/
        
        // Solicitar valor p (integral objetivo)
        System.out.print("Ingrese p (valor de la integral, 0 < p < 1): ");
        
        double dblP = this.objData.readDouble();  // Leer valor p
        
        // Solicitar grados de libertad
        System.out.print("Ingrese grados de libertad (dof, entero > 0): ");
        
        int intDof = this.objData.readInt();  // Leer grados de libertad
        
        // Solicitar error aceptable
        System.out.print("Ingrese error aceptable (ej: 0.00001): ");
        
        double dblError = this.objData.readDouble();  // Leer error aceptable
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Validar parámetros ingresados                               */
        /*--------------------------------------------------------------*/
        
        // Validar que p esté en rango (0, 1)
        if (dblP <= 0.0 || dblP >= 1.0)
        {
            System.out.println("\nERROR: p debe estar entre 0 y 1 (exclusivo).");
            
            System.out.println("       Valor ingresado: " + dblP);
            
            return;  // Terminar ejecución por parámetro inválido
            
        }
        
        // Validar que dof sea positivo
        if (intDof <= 0)
        {
            System.out.println("\nADVERTENCIA: dof debe ser positivo.");
            
            System.out.println("             Usando valor absoluto: " + Math.abs(intDof));
            
            intDof = Math.abs(intDof);  // Corregir usando valor absoluto
            
        }
        
        // Validar que error sea positivo
        if (dblError <= 0.0)
        {
            System.out.println("\nADVERTENCIA: error debe ser positivo.");
            
            System.out.println("             Usando valor mínimo 0.00001");
            
            dblError = 0.00001;  // Usar valor por defecto
            
        }
        
        /*--------------------------------------------------------------*/
        /*  Mostrar parámetros configurados                             */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("   PARÁMETROS CONFIGURADOS");
        System.out.println("-".repeat(50));
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.printf("  p objetivo = %.10f%n", dblP);
        
        System.out.printf("  Grados de libertad (dof) = %d%n", intDof);
        
        System.out.printf("  Error aceptable = %.10f%n", dblError);
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Crear y ejecutar algoritmo de búsqueda                      */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("   EJECUTANDO ALGORITMO DE BÚSQUEDA");
        System.out.println("-".repeat(50));
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.println("Iniciando búsqueda de x...");
        
        System.out.println();  // Línea en blanco para separación
        
        // Crear objeto del algoritmo de búsqueda
        SearchAlgorithm objBuscador = new SearchAlgorithm(dblP, intDof, dblError);
        
        // Ejecutar búsqueda y obtener x encontrado
        double dblXEncontrado = objBuscador.findX();
        
        // Obtener información adicional del algoritmo
        int intIteraciones = objBuscador.getIterations();
        
        double dblErrorFinal = objBuscador.getFinalError();
        
        double dblPCalculado = objBuscador.getLastPCalculated();
        
        /*--------------------------------------------------------------*/
        /*  Mostrar resultados finales en formato específico            */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "-".repeat(50));
        System.out.println("   RESULTADOS FINALES - PROGRAM 6");
        System.out.println("-".repeat(50));
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.printf("Para p = %.10f y dof = %d:%n", dblP, intDof);
        
        System.out.printf("x encontrado = %.10f%n", dblXEncontrado);
        
        System.out.printf("Iteraciones realizadas: %d%n", intIteraciones);
        
        System.out.printf("Error final alcanzado: %.10f%n", dblErrorFinal);
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.printf("El algoritmo encontró x tal que:%n");
        
        System.out.printf("∫₀^%.10f f(t) dt ≈ %.10f%n", dblXEncontrado, dblP);
        
        System.out.println();  // Línea en blanco para separación
        
        // Mostrar en formato tabla específico
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMEN EN FORMATO TABLA:");
        System.out.println("=".repeat(50));
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.printf("%-10s %-10s %-15s%n", "p", "dof", "x");
        
        System.out.println("-".repeat(35));
        
        System.out.printf("%-10.6f %-10d %-15.10f%n", dblP, intDof, dblXEncontrado);
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Guardar resultados en archivo Out5.txt                      */
        /*--------------------------------------------------------------*/
        
        escribirResultadosArchivo(dblP, intDof, dblError, dblXEncontrado, 
                                 intIteraciones, dblErrorFinal, dblPCalculado);
        
    }  // Fin del método executeInteractiveMode
    
    /**
     * Escribe los resultados en archivo Out5.txt.
     * <p>
     * Crea o sobrescribe el archivo Out5.txt con todos los resultados
     * de la ejecución del programa Program 6.
     *
     * @param dblP            valor p objetivo
     * @param intDof          grados de libertad
     * @param dblError        error aceptable
     * @param dblXEncontrado  valor x encontrado
     * @param intIteraciones  número de iteraciones realizadas
     * @param dblErrorFinal   error final alcanzado
     * @param dblPCalculado   último valor p calculado
     */
    private void escribirResultadosArchivo(double dblP, int intDof, double dblError,
                                          double dblXEncontrado, int intIteraciones,
                                          double dblErrorFinal, double dblPCalculado)
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Writer para el archivo de salida */
        PrintWriter objWriter = null;
        
        /** Nombre del archivo de salida */
        final String STR_NOMBRE_ARCHIVO = "Out5.txt";
        
        try
        {
            /*----------------------------------------------------------*/
            /*  Crear archivo para escritura                            */
            /*----------------------------------------------------------*/
            
            objWriter = new PrintWriter(new FileWriter(STR_NOMBRE_ARCHIVO));
            
            /*----------------------------------------------------------*/
            /*  Escribir encabezado del reporte                         */
            /*----------------------------------------------------------*/
            
            objWriter.println("=".repeat(70));
            objWriter.println("   REPORTE PROGRAM 6 - ENCONTRAR X PARA DISTRIBUCIÓN t-STUDENT");
            objWriter.println("=".repeat(70));
            objWriter.println("   Fecha de generación: " + this.objDateFormat.format(new Date()));
            objWriter.println("=".repeat(70));
            
            /*----------------------------------------------------------*/
            /*  Escribir parámetros de entrada                          */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nPARÁMETROS DE ENTRADA:");
            objWriter.println("-".repeat(40));
            objWriter.printf("  p (valor objetivo): %.10f%n", dblP);
            objWriter.printf("  Grados de libertad (dof): %d%n", intDof);
            objWriter.printf("  Error aceptable: %.10f%n", dblError);
            
            /*----------------------------------------------------------*/
            /*  Escribir resultados del algoritmo                       */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nRESULTADOS DEL ALGORITMO:");
            objWriter.println("-".repeat(40));
            objWriter.printf("  Iteraciones totales: %d%n", intIteraciones);
            objWriter.printf("  Error final alcanzado: %.10f%n", dblErrorFinal);
            objWriter.printf("  x encontrado: %.10f%n", dblXEncontrado);
            objWriter.printf("  Último p calculado: %.10f%n", dblPCalculado);
            objWriter.printf("  Verificación: ∫₀^%.10f f(t) dt ≈ %.10f%n", 
                            dblXEncontrado, dblPCalculado);
            
            /*----------------------------------------------------------*/
            /*  Escribir resumen en formato tabla                       */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nRESUMEN EN FORMATO TABLA:");
            objWriter.println("-".repeat(60));
            objWriter.printf("%-15s %-10s %-20s%n", 
                            "p", "dof", "x (encontrado)");
            objWriter.println("-".repeat(45));
            objWriter.printf("%-15.10f %-10d %-20.10f%n",
                            dblP, intDof, dblXEncontrado);
            
            /*----------------------------------------------------------*/
            /*  Escribir información de validación                      */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nVALIDACIÓN:");
            objWriter.println("-".repeat(40));
            objWriter.println("  Precisión alcanzada: " + 
                             (dblErrorFinal < dblError ? "SÍ" : "NO"));
            objWriter.printf("  Diferencia respecto al error aceptable: %.10f%n",
                            Math.abs(dblErrorFinal - dblError));
            
            /*----------------------------------------------------------*/
            /*  Escribir información del algoritmo                      */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nINFORMACIÓN DEL ALGORITMO:");
            objWriter.println("-".repeat(40));
            objWriter.println("  Algoritmo: Búsqueda adaptativa (Program 6)");
            objWriter.println("  Método de integración: Regla de Simpson 1/3");
            objWriter.println("  Valor inicial de x: 1.0");
            objWriter.println("  Valor inicial de d: 0.5");
            objWriter.println("  Segmentos iniciales para Simpson: 10");
            
            /*----------------------------------------------------------*/
            /*  Escribir pie del reporte                                */
            /*----------------------------------------------------------*/
            
            objWriter.println("\n" + "=".repeat(70));
            objWriter.println("   FIN DEL REPORTE");
            objWriter.println("=".repeat(70));
            
            /*----------------------------------------------------------*/
            /*  Confirmar escritura exitosa                            */
            /*----------------------------------------------------------*/
            
            System.out.println("\n✓ Resultados guardados en: " + STR_NOMBRE_ARCHIVO);
            
        }
        catch (IOException objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejar error de escritura de archivo                   */
            /*----------------------------------------------------------*/
            
            System.err.println("\n✗ ERROR: No se pudo escribir el archivo de resultados.");
            System.err.println("   Archivo: " + STR_NOMBRE_ARCHIVO);
            System.err.println("   Mensaje: " + objEx.getMessage());
            
        }
        finally
        {
            /*----------------------------------------------------------*/
            /*  Cerrar recursos en bloque finally                       */
            /*----------------------------------------------------------*/
            
            if (objWriter != null)
            {
                objWriter.close();  // Cerrar el writer
            }
        }
        
    }  // Fin del método escribirResultadosArchivo
    
}  // Fin de la clase Logic