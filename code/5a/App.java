/***************************************************************************
 * Programa:  Program 6 - Encontrar x para Distribución t-Student
 * Clase:     App
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]    
 *
 * Descripción:
 *   Punto de entrada principal del programa Program 6. Crea una instancia
 *   de la clase Logic y ejecuta el procesamiento principal para encontrar
 *   el valor x tal que la integral de 0 a x de la distribución t-Student
 *   sea igual a un valor p dado.
 *
 * Compilación:
 *   javac App.java Logic.java SearchAlgorithm.java SimpsonIntegration.java
 *         TDistribution.java GammaFunction.java Data.java
 *
 * Ejecución:
 *   java App
 *
 * Reuse Instructions:
 *   - Esta clase solo debe ser modificada para cambios en el flujo principal
 *   - El manejo de excepciones ya está implementado
 *   - Para cambios en la lógica, modificar la clase Logic
 *
 * Notas:
 *   - Requiere Java 8 o superior
 *   - Todos los archivos .java deben estar en mismo directorio
 ***************************************************************************/

/**
 * Clase App.
 * <p>
 * Proporciona el método main como punto único de entrada al programa Program 6.
 * Implementa manejo robusto de excepciones y mensajes de error claros.
 * Sigue el estándar PSP para estructura de programas.
 */
public class App
{
    /**
     * Método principal (main) del programa Program 6.
     * <p>
     * Punto de entrada único. Crea la instancia de Logic,
     * ejecuta el programa y maneja cualquier excepción que ocurra.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args)
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Objeto que controla toda la lógica del programa Program 6 */
        Logic5a objLogic = null;
        
        /** Código de salida del programa (0=éxito, 1=error) */
        int intCodigoSalida = 0;
        
        /*--------------------------------------------------------------*/
        /*  Bloque try-catch para manejo robusto de excepciones         */
        /*--------------------------------------------------------------*/
        
        try
        {
            /*----------------------------------------------------------*/
            /*  Crear instancia de la lógica principal                  */
            /*----------------------------------------------------------*/
            
            objLogic = new Logic5a();  // Instanciar objeto Logic
            
            /*----------------------------------------------------------*/
            /*  Ejecutar el flujo principal del programa                */
            /*----------------------------------------------------------*/
            
            objLogic.execute();  // Llamar al método de ejecución principal
            
            /*----------------------------------------------------------*/
            /*  Finalización exitosa                                    */
            /*----------------------------------------------------------*/
            
            intCodigoSalida = 0;  // Establecer código de éxito
            
        }
        catch (IllegalArgumentException objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejo de argumentos inválidos                          */
            /*----------------------------------------------------------*/
            
            System.err.println("\n" + "=".repeat(60));
            System.err.println("   ERROR: ARGUMENTO INVÁLIDO");
            System.err.println("=".repeat(60));
            System.err.println("\nMensaje: " + objEx.getMessage());
            System.err.println("\nCausa probable:");
            System.err.println("  - Parámetro numérico fuera de rango válido");
            System.err.println("  - Valor negativo donde no está permitido");
            System.err.println("  - Tipo de dato incorrecto");
            System.err.println("\nAcción correctiva:");
            System.err.println("  - Verifique los valores ingresados");
            System.err.println("  - Asegúrese de que cumplen restricciones");
            
            intCodigoSalida = 1;  // Establecer código de error
            
        }
        catch (ArithmeticException objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejo de errores aritméticos                           */
            /*----------------------------------------------------------*/
            
            System.err.println("\n" + "=".repeat(60));
            System.err.println("   ERROR: FALLA ARITMÉTICA");
            System.err.println("=".repeat(60));
            System.err.println("\nMensaje: " + objEx.getMessage());
            System.err.println("\nCausa probable:");
            System.err.println("  - División por cero");
            System.err.println("  - Overflow/underflow numérico");
            System.err.println("  - Operación matemática inválida");
            System.err.println("\nAcción correctiva:");
            System.err.println("  - Revise los cálculos en el código");
            System.err.println("  - Verifique rangos de valores válidos");
            
            intCodigoSalida = 1;  // Establecer código de error
            
        }
        catch (Exception objEx)
        {
            /*----------------------------------------------------------*/
            /*  Manejo de excepciones generales                         */
            /*----------------------------------------------------------*/
            
            System.err.println("\n" + "=".repeat(60));
            System.err.println("   ERROR INESPERADO");
            System.err.println("=".repeat(60));
            System.err.println("\nMensaje: " + objEx.getMessage());
            System.err.println("\nTipo de excepción: " + 
                              objEx.getClass().getSimpleName());
            System.err.println("\nStack trace:");
            
            objEx.printStackTrace();  // Imprimir traza de la excepción
            
            intCodigoSalida = 1;  // Establecer código de error
            
        }
        finally
        {
            /*----------------------------------------------------------*/
            /*  Bloque finally: siempre se ejecuta                      */
            /*----------------------------------------------------------*/
            
            System.out.println("\n" + "-".repeat(50));
            System.out.println("PROGRAMA PROGRAM 6 FINALIZADO");
            // Se eliminó la línea "Código de salida: X"
            System.out.println("-".repeat(50));
            
        }
        
        /*--------------------------------------------------------------*/
        /*  Salir con código apropiado                                  */
        /*--------------------------------------------------------------*/
        
        System.exit(intCodigoSalida);  // Terminar programa con código de salida
        
    }  // Fin del método main
    
}  // Fin de la clase App