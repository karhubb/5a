/***************************************************************************
 * Programa:  Regla de Simpson para Distribución t-Student - PSP5
 * Clase:     Output5a
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Maneja todas las operaciones de salida del programa PSP5.
 *   Proporciona métodos para formatear y mostrar resultados en consola
 *   y para escribir reportes completos a archivos de texto.
 *
 * Funcionalidades:
 *   - Formateo de resultados en formato tabla PSP5
 *   - Escritura de archivos de salida con resultados detallados
 *   - Presentación de resultados intermedios y finales
 *   - Comparación con valores esperados
 *   - Manejo robusto de errores de escritura de archivos
 *
 * Formatos de Salida:
 *   - Consola: Formato legible con encabezados y separadores
 *   - Archivo: Formato estructurado idéntico a consola
 *
 * Archivos Generados:
 *   - Out5a.txt: Reporte completo de ejecución
 *   - Results5a.txt: Resultados en formato tabla
 ***************************************************************************/

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase Output5a.
 * <p>
 * Encapsula toda la funcionalidad relacionada con salida de datos
 * del programa PSP5. Proporciona métodos para presentación en consola
 * y escritura a archivos.
 */
public class Output
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Formato de fecha para timestamp en archivos */
    private SimpleDateFormat objDateFormat;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase Output5a.
     * <p>
     * Inicializa el formato de fecha para los archivos de salida.
     */
    public Output()
    {
        this.objDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    /*------------------------------------------------------------------*/
    /*  Public Methods - Console Output                                 */
    /*------------------------------------------------------------------*/
    
    /**
     * Muestra el encabezado del programa en consola.
     * <p> 
     * Presenta información básica sobre el programa PSP5.
     */
    public void printHeader()
    {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("   PROGRAMA 5A - PSP: INTEGRACIÓN t-STUDENT CON REGLA DE SIMPSON");
        System.out.println("=".repeat(70));
        System.out.println("   Autor: [Tu Nombre]");
        System.out.println("   Fecha: " + this.objDateFormat.format(new Date()));
        System.out.println("   Descripción: Cálculo numérico de integral t-Student (0 a x)");
        System.out.println("=".repeat(70));
    }
    
    /**
     * Muestra resultados en formato de tabla PSP5.
     * <p>
     * Presenta los resultados en el formato requerido por el PSP5:
     *   x     | dof | p (calculado) | p (esperado) | diferencia
     *
     * @param dblX            límite superior de integración
     * @param intDof          grados de libertad
     * @param dblPCalculado   valor calculado de la integral
     * @param dblPEsperado    valor esperado (opcional, puede ser 0)
     * @param dblDiferencia   diferencia absoluta (opcional)
     */
    public void printTableResult(double dblX, int intDof, double dblPCalculado,
                                double dblPEsperado, double dblDiferencia)
    {
        /*--------------------------------------------------------------*/
        /*  Mostrar encabezado de tabla                                 */
        /*--------------------------------------------------------------*/
        
        System.out.println("\n" + "-".repeat(70));
        System.out.println("   RESULTADOS - FORMATO TABLA PSP5");
        System.out.println("-".repeat(70));
        
        System.out.printf("%n%-15s %-10s %-20s %-20s %-15s%n",
                         "x", "dof", "p (calculado)", 
                         "p (esperado)", "diferencia");
        System.out.println("-".repeat(80));
        
        /*--------------------------------------------------------------*/
        /*  Mostrar fila de resultados                                  */
        /*--------------------------------------------------------------*/
        
        System.out.printf("%-15.6f %-10d %-20.10f %-20.10f %-15.10f%n",
                         dblX, intDof, dblPCalculado,
                         dblPEsperado, dblDiferencia);
        
        /*--------------------------------------------------------------*/
        /*  Mostrar indicador de precisión si hay valor esperado       */
        /*--------------------------------------------------------------*/
        
        if (dblPEsperado > 0.0)
        {
            if (dblDiferencia < 0.0001)
            {
                System.out.println("\n✓ PRECISIÓN ACEPTABLE: Diferencia < 0.0001");
            }
            else
            {
                System.out.println("\n✗ PRECISIÓN INSUFICIENTE: Diferencia ≥ 0.0001");
            }
        }
    }
    
    /**
     * Muestra resultados simples en formato línea.
     * <p>
     * Formato simplificado: x dof p
     * Útil para salida mínima requerida.
     *
     * @param dblX          límite superior de integración
     * @param intDof        grados de libertad
     * @param dblPCalculado valor calculado de la integral
     */
    public void printSimpleResult(double dblX, int intDof, double dblPCalculado)
    {
        System.out.printf("%nRESULTADO: x=%.6f, dof=%d, p=%.10f%n",
                         dblX, intDof, dblPCalculado);
    }
    
    /**
     * Muestra información de iteración en consola.
     * <p>
     * Presenta resultados intermedios durante el cálculo iterativo.
     *
     * @param intIteracion     número de iteración
     * @param intSegmentos     número de segmentos en esta iteración
     * @param dblPActual       valor de P calculado
     * @param dblDiferencia    diferencia con iteración anterior
     * @param blnConvergencia  indica si se alcanzó convergencia
     */
    public void printIteration(int intIteracion, int intSegmentos,
                              double dblPActual, double dblDiferencia,
                              boolean blnConvergencia)
    {
        System.out.printf("%nIteración %d:%n", intIteracion);
        System.out.printf("  Segmentos: %d%n", intSegmentos);
        System.out.printf("  P = %.10f%n", dblPActual);
        
        if (intIteracion > 1)
        {
            System.out.printf("  Diferencia = %.10f%n", dblDiferencia);
            
            if (blnConvergencia)
            {
                System.out.println("  ✓ CONVERGENCIA ALCANZADA");
            }
        }
    }
    
    /*------------------------------------------------------------------*/
    /*  Public Methods - File Output                                    */
    /*------------------------------------------------------------------*/
    
    /**
     * Escribe reporte completo a archivo de texto.
     * <p>
     * Crea o sobrescribe el archivo Out5a.txt con toda la información
     * de la ejecución del programa, incluyendo parámetros, iteraciones
     * y resultados finales.
     *
     * @param dblX                 límite superior de integración
     * @param intDof               grados de libertad
     * @param dblErrorAceptable    error aceptable configurado
     * @param intSegmentosInicial  segmentos iniciales
     * @param dblResultadoFinal    resultado final calculado
     * @param intIteraciones       número total de iteraciones
     * @param intSegmentosFinales  segmentos finales utilizados
     * @param dblPrecisionFinal    precisión final alcanzada
     *
     * @throws IOException si ocurre error durante la escritura del archivo
     */
    public void writeFullReport(double dblX, int intDof,
                               double dblErrorAceptable, int intSegmentosInicial,
                               double dblResultadoFinal, int intIteraciones,
                               int intSegmentosFinales, double dblPrecisionFinal)
        throws IOException
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Writer para el archivo de salida */
        PrintWriter objWriter = null;
        
        /** Nombre del archivo de salida principal */
        final String STR_OUT_FILE = "Out5a.txt";
        
        try
        {
            /*----------------------------------------------------------*/
            /*  Abrir archivo para escritura                            */
            /*----------------------------------------------------------*/
            
            objWriter = new PrintWriter(new FileWriter(STR_OUT_FILE));
            
            /*----------------------------------------------------------*/
            /*  Escribir encabezado del reporte                         */
            /*----------------------------------------------------------*/
            
            objWriter.println("=".repeat(70));
            objWriter.println("   REPORTE PSP5 - INTEGRACIÓN t-STUDENT CON REGLA DE SIMPSON");
            objWriter.println("=".repeat(70));
            objWriter.println("   Fecha de generación: " + 
                             this.objDateFormat.format(new Date()));
            objWriter.println("=".repeat(70));
            
            /*----------------------------------------------------------*/
            /*  Escribir parámetros de ejecución                        */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nPARÁMETROS DE EJECUCIÓN:");
            objWriter.println("-".repeat(40));
            objWriter.printf("  x (límite superior): %.6f%n", dblX);
            objWriter.printf("  Grados de libertad (dof): %d%n", intDof);
            objWriter.printf("  Error aceptable: %.10f%n", dblErrorAceptable);
            objWriter.printf("  Segmentos iniciales: %d%n", intSegmentosInicial);
            
            /*----------------------------------------------------------*/
            /*  Escribir resultados del cálculo                         */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nRESULTADOS DEL CÁLCULO:");
            objWriter.println("-".repeat(40));
            objWriter.printf("  Iteraciones totales: %d%n", intIteraciones);
            objWriter.printf("  Segmentos finales: %d%n", intSegmentosFinales);
            objWriter.printf("  Precisión final: %.10f%n", dblPrecisionFinal);
            objWriter.printf("  Resultado P(0 a %.6f): %.10f%n", 
                            dblX, dblResultadoFinal);
            
            /*----------------------------------------------------------*/
            /*  Escribir resumen en formato tabla                       */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nRESUMEN EN FORMATO TABLA PSP5:");
            objWriter.println("-".repeat(60));
            objWriter.printf("%-15s %-10s %-20s%n", 
                            "x", "dof", "p (calculado)");
            objWriter.println("-".repeat(45));
            objWriter.printf("%-15.6f %-10d %-20.10f%n",
                            dblX, intDof, dblResultadoFinal);
            
            /*----------------------------------------------------------*/
            /*  Escribir información de validación                      */
            /*----------------------------------------------------------*/
            
            objWriter.println("\nVALIDACIÓN:");
            objWriter.println("-".repeat(40));
            objWriter.println("  Precisión alcanzada: " + 
                             (dblPrecisionFinal < dblErrorAceptable ? "SÍ" : "NO"));
            objWriter.printf("  Diferencia respecto al error aceptable: %.10f%n",
                            Math.abs(dblPrecisionFinal - dblErrorAceptable));
            
            /*----------------------------------------------------------*/
            /*  Escribir pie del reporte                                */
            /*----------------------------------------------------------*/
            
            objWriter.println("\n" + "=".repeat(70));
            objWriter.println("   FIN DEL REPORTE");
            objWriter.println("=".repeat(70));
            
            /*----------------------------------------------------------*/
            /*  Confirmar escritura exitosa                            */
            /*----------------------------------------------------------*/
            
            System.out.println("\n✓ Reporte completo guardado en: " + STR_OUT_FILE);
        }
        catch (IOException objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejar error de escritura de archivo                   */
            /*----------------------------------------------------------*/
            
            System.err.println("\n✗ ERROR: No se pudo escribir el archivo de reporte.");
            System.err.println("   Archivo: " + STR_OUT_FILE);
            System.err.println("   Mensaje: " + objEx.getMessage());
            
            // Relanzar excepción para manejo en nivel superior
            throw objEx;
        }
        finally
        {
            /*----------------------------------------------------------*/
            /*  Cerrar recursos en bloque finally                       */
            /*----------------------------------------------------------*/
            
            if (objWriter != null)
            {
                objWriter.close();
            }
        }
    }
    
    /**
     * Escribe resultados en formato tabla simple a archivo.
     * <p>
     * Crea el archivo Results5a.txt con solo los resultados finales
     * en formato de tabla simple para fácil procesamiento posterior.
     *
     * @param dblX              límite superior de integración
     * @param intDof            grados de libertad
     * @param dblPCalculado     valor calculado de la integral
     *
     * @throws IOException si ocurre error durante la escritura
     */
    public void writeTableResults(double dblX, int intDof, double dblPCalculado)
        throws IOException
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Writer para el archivo de resultados */
        PrintWriter objWriter = null;
        
        /** Nombre del archivo de resultados */
        final String STR_RESULTS_FILE = "Results5a.txt";
        
        try
        {
            /*----------------------------------------------------------*/
            /*  Abrir archivo para escritura                            */
            /*----------------------------------------------------------*/
            
            objWriter = new PrintWriter(new FileWriter(STR_RESULTS_FILE, true)); // Append mode
            
            /*----------------------------------------------------------*/
            /*  Escribir resultados en formato simple                   */
            /*----------------------------------------------------------*/
            
            objWriter.printf("%.6f %d %.10f%n", dblX, intDof, dblPCalculado);
            
            /*----------------------------------------------------------*/
            /*  Confirmar escritura                                     */
            /*----------------------------------------------------------*/
            
            System.out.println("✓ Resultados guardados en: " + STR_RESULTS_FILE);
        }
        catch (IOException objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejar error de escritura                              */
            /*----------------------------------------------------------*/
            
            System.err.println("\n✗ ERROR: No se pudo escribir archivo de resultados.");
            System.err.println("   Archivo: " + STR_RESULTS_FILE);
            
            // Relanzar excepción
            throw objEx;
        }
        finally
        {
            /*----------------------------------------------------------*/
            /*  Cerrar recursos                                         */
            /*----------------------------------------------------------*/
            
            if (objWriter != null)
            {
                objWriter.close();
            }
        }
    }
    
    /*------------------------------------------------------------------*/
    /*  Utility Methods                                                 */
    /*------------------------------------------------------------------*/
    
    /**
     * Retorna la fecha y hora actual formateada.
     *
     * @return string con fecha y hora actual
     */
    public String getCurrentTimestamp()
    {
        return this.objDateFormat.format(new Date());
    }
    
    /**
     * Formatea un número para mostrar con precisión controlada.
     *
     * @param dblValor      valor a formatear
     * @param intDecimales  número de decimales a mostrar
     * @return string formateado
     */
    public String formatNumber(double dblValor, int intDecimales)
    {
        return String.format("%." + intDecimales + "f", dblValor);
    }
}