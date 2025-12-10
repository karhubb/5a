/***************************************************************************
 * Programa:  Regla de Simpson para Distribución t-Student - PSP5
 * Clase:     Data5a
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Maneja todas las operaciones de entrada de datos del programa PSP5.
 *   Proporciona métodos robustos para lectura y validación de datos
 *   desde la consola con manejo de errores.
 *
 * Funcionalidades:
 *   - Lectura de números enteros con validación
 *   - Lectura de números decimales con validación
 *   - Validación de rangos y restricciones
 *   - Mensajes de error claros y sugerencias
 *   - Prevención de bucles infinitos por entrada inválida
 *
 * Notas:
 *   - Usa java.util.Scanner para entrada estándar
 *   - Implementa reintentos automáticos para entrada inválida
 *   - Cierra recursos adecuadamente
 ***************************************************************************/

import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Clase Data5a.
 * <p>
 * Encapsula toda la funcionalidad relacionada con entrada de datos
 * desde la consola. Proporciona métodos seguros que manejan errores
 * comunes de entrada del usuario.
 */
public class Data
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Scanner para leer entrada desde la consola */
    private Scanner objScanner;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase Data5a.
     * <p>
     * Inicializa el scanner para lectura desde System.in.
     */
    public Data()
    {
        this.objScanner = new Scanner(System.in);
    }
    
    /*------------------------------------------------------------------*/
    /*  Public Methods                                                  */
    /*------------------------------------------------------------------*/
    
    /**
     * Lee un número entero desde la consola con validación.
     * <p>
     * Solicita repetidamente entrada hasta que el usuario ingrese
     * un valor entero válido. Maneja errores de formato y provee
     * mensajes de error claros.
     *
     * @return número entero válido ingresado por el usuario
     */
    public int readInt()
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Valor entero a retornar */
        int intValor = 0;
        
        /** Indica si se ha obtenido entrada válida */
        boolean blnEntradaValida = false;
        
        /*--------------------------------------------------------------*/
        /*  Ciclo hasta obtener entrada válida                          */
        /*--------------------------------------------------------------*/
        
        while (!blnEntradaValida)
        {
            try
            {
                /*------------------------------------------------------*/
                /*  Intentar leer un entero                            */
                /*------------------------------------------------------*/
                
                intValor = this.objScanner.nextInt();
                
                /*------------------------------------------------------*/
                /*  Limpiar buffer de entrada (importante)             */
                /*------------------------------------------------------*/
                
                this.objScanner.nextLine();
                
                /*------------------------------------------------------*/
                /*  Marcar entrada como válida                         */
                /*------------------------------------------------------*/
                
                blnEntradaValida = true;
            }
            catch (InputMismatchException objEx)
            {
                /*------------------------------------------------------*/
                /*  Manejar error de tipo de dato                      */
                /*------------------------------------------------------*/
                
                System.err.println("\nERROR: Debe ingresar un número entero.");
                System.err.println("       Ejemplos válidos: 1, 10, 100, -5");
                
                /*------------------------------------------------------*/
                /*  Limpiar buffer para evitar bucle infinito          */
                /*------------------------------------------------------*/
                
                this.objScanner.nextLine();
                
                /*------------------------------------------------------*/
                /*  Solicitar nuevamente entrada                       */
                /*------------------------------------------------------*/
                
                System.out.print("\nPor favor ingrese un número entero válido: ");
            }
        }
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor válido                                       */
        /*--------------------------------------------------------------*/
        
        return intValor;
    }
    
    /**
     * Lee un número decimal (double) desde la consola con validación.
     * <p>
     * Solicita repetidamente entrada hasta que el usuario ingrese
     * un valor decimal válido. Maneja errores de formato.
     *
     * @return número decimal válido ingresado por el usuario
     */
    public double readDouble()
    {
        /*--------------------------------------------------------------*/
        /*  Variables locales                                           */
        /*--------------------------------------------------------------*/
        
        /** Valor decimal a retornar */
        double dblValor = 0.0;
        
        /** Indica si se ha obtenido entrada válida */
        boolean blnEntradaValida = false;
        
        /*--------------------------------------------------------------*/
        /*  Ciclo hasta obtener entrada válida                          */
        /*--------------------------------------------------------------*/
        
        while (!blnEntradaValida)
        {
            try
            {
                /*------------------------------------------------------*/
                /*  Intentar leer un decimal                           */
                /*------------------------------------------------------*/
                
                dblValor = this.objScanner.nextDouble();
                
                /*------------------------------------------------------*/
                /*  Limpiar buffer de entrada                          */
                /*------------------------------------------------------*/
                
                this.objScanner.nextLine();
                
                /*------------------------------------------------------*/
                /*  Marcar entrada como válida                         */
                /*------------------------------------------------------*/
                
                blnEntradaValida = true;
            }
            catch (InputMismatchException objEx)
            {
                /*------------------------------------------------------*/
                /*  Manejar error de tipo de dato                      */
                /*------------------------------------------------------*/
                
                System.err.println("\nERROR: Debe ingresar un número decimal.");
                System.err.println("       Ejemplos válidos: 1.5, 0.001, -3.14");
                System.err.println("       Use punto decimal, no coma.");
                
                /*------------------------------------------------------*/
                /*  Limpiar buffer para evitar bucle infinito          */
                /*------------------------------------------------------*/
                
                this.objScanner.nextLine();
                
                /*------------------------------------------------------*/
                /*  Solicitar nuevamente entrada                       */
                /*------------------------------------------------------*/
                
                System.out.print("\nPor favor ingrese un número decimal válido: ");
            }
        }
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor válido                                       */
        /*--------------------------------------------------------------*/
        
        return dblValor;
    }
    
    /**
     * Lee un número entero positivo desde la consola.
     * <p>
     * Similar a readInt(), pero valida adicionalmente que el valor
     * sea mayor que cero.
     *
     * @return número entero positivo válido
     *
     * @throws IllegalArgumentException si el valor no es positivo
     */
    public int readPositiveInt()
    {
        /*--------------------------------------------------------------*/
        /*  Leer valor entero                                           */
        /*--------------------------------------------------------------*/
        
        int intValor = readInt();
        
        /*--------------------------------------------------------------*/
        /*  Validar que sea positivo                                    */
        /*--------------------------------------------------------------*/
        
        if (intValor <= 0)
        {
            throw new IllegalArgumentException(
                "Data5a.readPositiveInt: valor debe ser > 0. " +
                "Valor recibido: " + intValor);
        }
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor positivo                                     */
        /*--------------------------------------------------------------*/
        
        return intValor;
    }
    
    /**
     * Lee un número decimal positivo desde la consola.
     * <p>
     * Similar a readDouble(), pero valida adicionalmente que el valor
     * sea mayor que cero.
     *
     * @return número decimal positivo válido
     *
     * @throws IllegalArgumentException si el valor no es positivo
     */
    public double readPositiveDouble()
    {
        /*--------------------------------------------------------------*/
        /*  Leer valor decimal                                          */
        /*--------------------------------------------------------------*/
        
        double dblValor = readDouble();
        
        /*--------------------------------------------------------------*/
        /*  Validar que sea positivo                                    */
        /*--------------------------------------------------------------*/
        
        if (dblValor <= 0.0)
        {
            throw new IllegalArgumentException(
                "Data5a.readPositiveDouble: valor debe ser > 0. " +
                "Valor recibido: " + dblValor);
        }
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor positivo                                     */
        /*--------------------------------------------------------------*/
        
        return dblValor;
    }
    
    /**
     * Lee un número entero par desde la consola.
     * <p>
     * Solicita un entero y valida que sea par. Si es impar,
     * ajusta automáticamente sumando 1.
     *
     * @param blnAjustarAutomaticamente si true, ajusta números impares a pares
     * @return número entero par válido
     */
    public int readEvenInt(boolean blnAjustarAutomaticamente)
    {
        /*--------------------------------------------------------------*/
        /*  Leer valor entero                                           */
        /*--------------------------------------------------------------*/
        
        int intValor = readInt();
        
        /*--------------------------------------------------------------*/
        /*  Verificar si es par                                         */
        /*--------------------------------------------------------------*/
        
        if (intValor % 2 != 0)
        {
            if (blnAjustarAutomaticamente)
            {
                /*------------------------------------------------------*/
                /*  Ajustar automáticamente a número par               */
                /*------------------------------------------------------*/
                
                int intValorAjustado = intValor + 1;
                System.out.println("\nNOTA: Número ajustado de " + intValor +
                                  " a " + intValorAjustado + " (debe ser par)");
                intValor = intValorAjustado;
            }
            else
            {
                /*------------------------------------------------------*/
                /*  Lanzar excepción si no se permite ajuste           */
                /*------------------------------------------------------*/
                
                throw new IllegalArgumentException(
                    "Data5a.readEvenInt: valor debe ser par. " +
                    "Valor recibido: " + intValor);
            }
        }
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor par                                          */
        /*--------------------------------------------------------------*/
        
        return intValor;
    }
    
    /*------------------------------------------------------------------*/
    /*  Resource Management Methods                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Cierra el scanner y libera recursos.
     * <p>
     * Debe llamarse cuando ya no se necesite la clase Data5a
     * para evitar fugas de recursos.
     */
    public void close()
    {
        if (this.objScanner != null)
        {
            this.objScanner.close();
            this.objScanner = null;
        }
    }
}
