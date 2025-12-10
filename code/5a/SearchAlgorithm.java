/***************************************************************************
 * Programa:  Program 6 - Encontrar x para Distribución t-Student
 * Clase:     SearchAlgorithm
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Implementa el algoritmo de búsqueda adaptativa para encontrar x tal que
 *   la integral de 0 a x de la distribución t-Student sea igual a un valor p dado.
 *   Basado en el algoritmo descrito en la página 5 del documento Program 6.
 *
 * Algoritmo (Program 6):
 *   1. Comenzar con x_prueba = 1.0, d = 0.5
 *   2. Calcular integral p_calculado = ∫₀^x_prueba f(t) dt
 *   3. Calcular error = p_calculado - p_objetivo
 *   4. Si error < 0: x_prueba = x_prueba + d (muy bajo, aumentar x)
 *      Si error > 0: x_prueba = x_prueba - d (muy alto, disminuir x)
 *   5. Si el signo del error cambia respecto a la iteración anterior: d = d/2
 *   6. Repetir hasta |error| < error_aceptable
 *
 * Formato de Salida del Algoritmo:
 *   - Encabezado con objetivo y parámetros
 *   - Tabla de iteraciones con columnas específicas
 *   - Mensajes de ajuste cuando cambia el signo del error
 *   - Resumen final con resultados
 *
 * Reuse Instructions:
 *   - Esta clase implementa específicamente el algoritmo de Program 6
 *   - Para cambiar el método de búsqueda, modificar esta clase
 *   - Para cambiar el método de integración, modificar SimpsonIntegration
 ***************************************************************************/

/**
 * Clase SearchAlgorithm.
 * <p>
 * Implementa el algoritmo de búsqueda adaptativa descrito en el Program 6.
 * Utiliza integración numérica con Simpson para evaluar la integral
 * y ajusta x iterativamente hasta alcanzar el p objetivo.
 * Genera salida en formato específico con tabla de iteraciones.
 */
public class SearchAlgorithm
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Valor p objetivo que debe alcanzar la integral */
    private double dblPTarget;
    
    /** Grados de libertad de la distribución t-Student */
    private int intDof;
    
    /** Error máximo aceptable para considerar convergencia */
    private double dblAcceptableError;
    
    /** Número de iteraciones realizadas durante la búsqueda */
    private int intIterations;
    
    /** Error final alcanzado al terminar la búsqueda */
    private double dblFinalError;
    
    /** Último valor p calculado durante la búsqueda */
    private double dblLastPCalculated;
    
    /** Objeto para integración numérica usando Simpson */
    private SimpsonIntegration objIntegrator;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase SearchAlgorithm.
     * <p>
     * Inicializa los parámetros del algoritmo de búsqueda.
     *
     * @param dblPTargetParam        valor p objetivo (0 < p < 1)
     * @param intDofParam            grados de libertad (entero > 0)
     * @param dblAcceptableErrorParam error aceptable para convergencia
     *
     * @throws IllegalArgumentException si parámetros inválidos
     */
    public SearchAlgorithm(double dblPTargetParam, int intDofParam,
                           double dblAcceptableErrorParam)
    {
        // Validar parámetros de entrada
        validarParametros(dblPTargetParam, intDofParam, dblAcceptableErrorParam);
        
        // Asignar parámetros validados a atributos
        this.dblPTarget = dblPTargetParam;
        
        this.intDof = intDofParam;
        
        this.dblAcceptableError = dblAcceptableErrorParam;
        
        // Inicializar contadores
        this.intIterations = 0;
        
        this.dblFinalError = 0.0;
        
        this.dblLastPCalculated = 0.0;
        
        this.objIntegrator = null;  // Se creará durante la búsqueda
        
    }  // Fin del constructor
    
    /*------------------------------------------------------------------*/
    /*  Public Methods                                                  */
    /*------------------------------------------------------------------*/
    
    /**
     * Ejecuta el algoritmo de búsqueda para encontrar x.
     * <p>
     * Implementa el algoritmo descrito en el documento Program 6:
     * Búsqueda adaptativa con ajuste de paso d.
     * Genera salida en formato específico con tabla de iteraciones.
     *
     * @return valor x encontrado tal que ∫₀^x f(t) dt ≈ p_target
     */
    public double findX()
    {
        /*--------------------------------------------------------------*/
        /*  Paso 1: Inicializar variables según algoritmo               */
        /*--------------------------------------------------------------*/
        
        double dblXTrial = 1.0;      // Valor inicial de x (paso 1)
        
        double dblD = 0.5;           // Incremento inicial d (paso 1)
        
        double dblPCalculated = 0.0; // Valor p calculado en cada iteración
        
        double dblError = 0.0;       // Error actual (p_calculado - p_target)
        
        double dblLastErrorSign = 0.0;  // Signo del error en iteración anterior
        
        int intSegmentosIniciales = 10;  // Segmentos iniciales para Simpson
        
        /*--------------------------------------------------------------*/
        /*  Mostrar encabezado del proceso de búsqueda                  */
        /*--------------------------------------------------------------*/
        
        System.out.println("Iniciando algoritmo de búsqueda adaptativa...");
        
        System.out.printf("Objetivo: encontrar x tal que ∫₀^x f(t) dt = %.10f%n", this.dblPTarget);
        
        System.out.printf("Grados de libertad: %d%n", this.intDof);
        
        System.out.printf("Error aceptable: %.10f%n", this.dblAcceptableError);
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Mostrar encabezado de la tabla de iteraciones               */
        /*--------------------------------------------------------------*/
        
        System.out.println("-".repeat(80));
        
        System.out.printf("%-10s %-15s %-15s %-15s %-15s%n",
                         "Iteración", "x_trial", "p_calculado", "error", "d");
        
        System.out.println("-".repeat(80));
        
        /*--------------------------------------------------------------*/
        /*  Ciclo iterativo principal (pasos 2-6 del algoritmo)         */
        /*--------------------------------------------------------------*/
        
        do
        {
            // Incrementar contador de iteraciones
            this.intIterations++;
            
            /*----------------------------------------------------------*/
            /*  Paso 2: Calcular integral para x_trial actual           */
            /*----------------------------------------------------------*/
            
            // Crear integrador para el x_trial actual
            this.objIntegrator = new SimpsonIntegration(dblXTrial, this.intDof,
                                                       this.dblAcceptableError,
                                                       intSegmentosIniciales);
            
            // Calcular p_calculado = ∫₀^x_trial f(t) dt
            dblPCalculated = this.objIntegrator.integrate();
            
            // Guardar último p calculado
            this.dblLastPCalculated = dblPCalculated;
            
            /*----------------------------------------------------------*/
            /*  Paso 3: Calcular error (p_calculado - p_target)         */
            /*----------------------------------------------------------*/
            
            dblError = dblPCalculated - this.dblPTarget;
            
            /*----------------------------------------------------------*/
            /*  Paso 5: Ajustar d si cambia el signo del error          */
            /*----------------------------------------------------------*/
            
            if (this.intIterations > 1)  // A partir de la segunda iteración
            {
                // Obtener signos del error actual y anterior
                double dblCurrentSign = Math.signum(dblError);
                
                double dblPreviousSign = Math.signum(dblLastErrorSign);
                
                // Si el signo cambió, dividir d por 2
                if (dblCurrentSign != dblPreviousSign && dblPreviousSign != 0.0)
                {
                    dblD = dblD / 2.0;  // Reducir paso a la mitad
                    
                    System.out.printf("  → Signo cambiado: d ajustado a %.10f%n", dblD);
                    
                }
                
            }
            
            // Guardar signo del error para la siguiente iteración
            dblLastErrorSign = dblError;
            
            /*----------------------------------------------------------*/
            /*  Paso 4: Ajustar x_trial según el error                  */
            /*----------------------------------------------------------*/
            
            if (dblError < 0.0)
            {
                // p_calculado < p_target → integral muy pequeña
                // Aumentar x_trial para obtener integral mayor
                dblXTrial = dblXTrial + dblD;
                
            }
            else if (dblError > 0.0)
            {
                // p_calculado > p_target → integral muy grande
                // Disminuir x_trial para obtener integral menor
                dblXTrial = dblXTrial - dblD;
                
            }
            
            /*----------------------------------------------------------*/
            /*  Evitar valores negativos de x (la integral es de 0 a x) */
            /*----------------------------------------------------------*/
            
            if (dblXTrial < 0.0)
            {
                dblXTrial = 0.0;  // x no puede ser negativo
                
                System.out.println("  → Ajuste: x_trial no puede ser negativo, usando 0.0");
                
            }
            
            /*----------------------------------------------------------*/
            /*  Mostrar información de esta iteración                   */
            /*----------------------------------------------------------*/
            
            System.out.printf("%-10d %-15.10f %-15.10f %-15.10f %-15.10f%n",
                             this.intIterations, dblXTrial, dblPCalculated,
                             dblError, dblD);
            
            /*----------------------------------------------------------*/
            /*  Condición de terminación: error menor al aceptable      */
            /*----------------------------------------------------------*/
            
            // También limitar el número máximo de iteraciones
            if (this.intIterations >= 100)
            {
                System.out.println("\nADVERTENCIA: Límite de iteraciones alcanzado (100)");
                
                break;  // Salir del ciclo por límite de iteraciones
                
            }
            
        } while (Math.abs(dblError) > this.dblAcceptableError);
        // Continuar hasta que error sea menor al aceptable
        
        /*--------------------------------------------------------------*/
        /*  Guardar error final y mostrar resultados                    */
        /*--------------------------------------------------------------*/
        
        this.dblFinalError = dblError;  // Guardar error final alcanzado
        
        System.out.println("-".repeat(80));
        
        System.out.println();  // Línea en blanco para separación
        
        System.out.printf("✓ Búsqueda completada en %d iteraciones%n", this.intIterations);
        
        System.out.printf("x encontrado: %.10f%n", dblXTrial);
        
        System.out.printf("p calculado: %.10f (objetivo: %.10f)%n",
                         dblPCalculated, this.dblPTarget);
        
        System.out.printf("Error final: %.10f (aceptable: %.10f)%n",
                         Math.abs(this.dblFinalError), this.dblAcceptableError);
        
        System.out.println();  // Línea en blanco para separación
        
        /*--------------------------------------------------------------*/
        /*  Retornar valor x encontrado                                 */
        /*--------------------------------------------------------------*/
        
        return dblXTrial;  // Retornar el x que satisface la condición
        
    }  // Fin del método findX
    
    /*------------------------------------------------------------------*/
    /*  Private Methods                                                 */
    /*------------------------------------------------------------------*/
    
    /**
     * Valida todos los parámetros de entrada del constructor.
     *
     * @param dblPTarget        valor p objetivo
     * @param intDof            grados de libertad
     * @param dblAcceptableError error aceptable
     *
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    private void validarParametros(double dblPTarget, int intDof,
                                  double dblAcceptableError)
    {
        // Validar que p_target esté en rango (0, 1)
        if (dblPTarget <= 0.0 || dblPTarget >= 1.0)
        {
            throw new IllegalArgumentException(
                "SearchAlgorithm: p_target debe estar entre 0 y 1 (exclusivo). " +
                "Valor recibido: " + dblPTarget);
                
        }
        
        // Validar que dof sea positivo
        if (intDof <= 0)
        {
            throw new IllegalArgumentException(
                "SearchAlgorithm: dof debe ser > 0. " +
                "Valor recibido: " + intDof);
                
        }
        
        // Validar que error aceptable sea positivo
        if (dblAcceptableError <= 0.0)
        {
            throw new IllegalArgumentException(
                "SearchAlgorithm: error aceptable debe ser > 0. " +
                "Valor recibido: " + dblAcceptableError);
                
        }
        
    }  // Fin del método validarParametros
    
    /*------------------------------------------------------------------*/
    /*  Getters                                                         */
    /*------------------------------------------------------------------*/
    
    /**
     * Retorna el número de iteraciones realizadas durante la búsqueda.
     *
     * @return número de iteraciones
     */
    public int getIterations()
    {
        return this.intIterations;
        
    }  // Fin del método getIterations
    
    /**
     * Retorna el error final alcanzado al terminar la búsqueda.
     *
     * @return error final absoluto
     */
    public double getFinalError()
    {
        return Math.abs(this.dblFinalError);
        
    }  // Fin del método getFinalError
    
    /**
     * Retorna el valor p objetivo configurado.
     *
     * @return valor p_target
     */
    public double getPTarget()
    {
        return this.dblPTarget;
        
    }  // Fin del método getPTarget
    
    /**
     * Retorna los grados de libertad configurados.
     *
     * @return valor dof
     */
    public int getDof()
    {
        return this.intDof;
        
    }  // Fin del método getDof
    
    /**
     * Retorna el último valor p calculado durante la búsqueda.
     *
     * @return último valor p calculado
     */
    public double getLastPCalculated()
    {
        return this.dblLastPCalculated;
        
    }  // Fin del método getLastPCalculated
    
}  // Fin de la clase SearchAlgorithm