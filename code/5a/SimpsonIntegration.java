/***************************************************************************
 * Programa:  Regla de Simpson para Distribución t-Student - PSP5
 * Clase:     SimpsonIntegration5a
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Implementa la integración numérica usando la Regla de Simpson 1/3
 *   compuesta para la distribución t-Student. Realiza integración
 *   iterativa duplicando segmentos hasta alcanzar precisión especificada.
 *
 * Algoritmo:
 *   1. Comienza con número inicial de segmentos (par)
 *   2. Calcula integral con Simpson
 *   3. Duplica segmentos y recalcula
 *   4. Compara diferencia con iteración anterior
 *   5. Repite hasta diferencia < error aceptable
 *
 * Fórmula Simpson:
 *   ∫[0,x] f(t) dt ≈ (h/3)[f(0) + 4∑f(x_impar) + 2∑f(x_par) + f(x)]
 *
 * Variables Miembro:
 *   - Distribución t para evaluación de función
 *   - Parámetros de integración (x, dof, error)
 *   - Resultados iterativos
 ***************************************************************************/

/**
 * Clase SimpsonIntegration5a.
 * <p>
 * Proporciona métodos para integración numérica iterativa usando
 * Regla de Simpson adaptativa. Controla el proceso de refinamiento
 * de segmentos hasta convergencia.
 */
public class SimpsonIntegration
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Distribución t-Student para evaluación de función */
    private TDistribution objTDistribution;
    
    /** Límite superior de integración (de 0 a x) */
    private double dblX;
    
    /** Grados de libertad de la distribución */
    private int intDof;
    
    /** Error aceptable para convergencia */
    private double dblAcceptableError;
    
    /** Número inicial de segmentos (debe ser par) */
    private int intNumSegInicial;
    
    /** Resultado final de la integral */
    private double dblResultadoFinal;
    
    /** Número de iteraciones realizadas */
    private int intIteraciones;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase SimpsonIntegration5a.
     * <p>
     * Inicializa los parámetros de integración y crea la distribución
     * t-Student correspondiente.
     *
     * @param dblXParam             límite superior de integración
     * @param intDofParam           grados de libertad
     * @param dblAcceptableErrorParam error aceptable para convergencia
     * @param intNumSegInicialParam número inicial de segmentos (par)
     *
     * @throws IllegalArgumentException si parámetros inválidos
     */
    public SimpsonIntegration(double dblXParam, int intDofParam,
                               double dblAcceptableErrorParam,
                               int intNumSegInicialParam)
    {
        // Validar parámetros de entrada
        validarParametros(dblXParam, intDofParam, dblAcceptableErrorParam,
                         intNumSegInicialParam);
        
        // Asignar parámetros validados
        this.dblX = dblXParam;
        this.intDof = intDofParam;
        this.dblAcceptableError = dblAcceptableErrorParam;
        this.intNumSegInicial = intNumSegInicialParam;
        
        // Crear distribución t-Student
        this.objTDistribution = new TDistribution(intDofParam);
        
        // Inicializar contadores
        this.dblResultadoFinal = 0.0;
        this.intIteraciones = 0;
    }
    
    /*------------------------------------------------------------------*/
    /*  Public Methods                                                  */
    /*------------------------------------------------------------------*/
    
    /**
     * Ejecuta la integración numérica iterativa.
     * <p>
     * Realiza integración con Simpson, duplicando segmentos en cada
     * iteración hasta que la diferencia entre resultados consecutivos
     * sea menor que el error aceptable.
     *
     * @return resultado final de la integral P(x)
     */
    public double integrate()
    {
        /*--------------------------------------------------------------*/
        /*  Variables para control de iteraciones                       */
        /*--------------------------------------------------------------*/
        
        // Resultados de iteraciones consecutivas
        double dblResultadoAnterior = 0.0;
        double dblResultadoActual = 0.0;
        
        // Diferencia entre iteraciones
        double dblDiferencia = this.dblAcceptableError + 1.0; // Forzar primera iteración
        
        // Número de segmentos actual (se duplica cada iteración)
        int intSegmentosActuales = this.intNumSegInicial;
        
        // Contador de iteraciones
        this.intIteraciones = 0;
        
        /*--------------------------------------------------------------*/
        /*  Ciclo iterativo principal                                   */
        /*--------------------------------------------------------------*/
        
        do
        {
            // Incrementar contador de iteraciones
            this.intIteraciones++;
            
            // Guardar resultado anterior si no es la primera iteración
            if (this.intIteraciones > 1)
            {
                dblResultadoAnterior = dblResultadoActual;
            }
            
            // Calcular integral con número actual de segmentos
            dblResultadoActual = calculateIntegral(intSegmentosActuales);
            
            // Calcular diferencia a partir de la segunda iteración
            if (this.intIteraciones > 1)
            {
                dblDiferencia = Math.abs(dblResultadoActual - dblResultadoAnterior);
            }
            
            // Duplicar segmentos para siguiente iteración
            intSegmentosActuales *= 2;
            
        } while (this.intIteraciones < 2 || dblDiferencia >= this.dblAcceptableError);
        // Mínimo 2 iteraciones, continuar hasta convergencia
        
        /*--------------------------------------------------------------*/
        /*  Guardar y retornar resultado final                          */
        /*--------------------------------------------------------------*/
        
        this.dblResultadoFinal = dblResultadoActual;
        return this.dblResultadoFinal;
    }
    
    /**
     * Calcula una iteración de la integral usando Regla de Simpson.
     * <p>
     * Implementación directa de la fórmula compuesta de Simpson 1/3
     * para el intervalo [0, x] con número específico de segmentos.
     *
     * @param intNumSeg número de segmentos (debe ser par)
     * @return valor aproximado de la integral
     *
     * @throws IllegalArgumentException si intNumSeg no es par
     */
    public double calculateIntegral(int intNumSeg)
    {
        /*--------------------------------------------------------------*/
        /*  Validar número de segmentos                                 */
        /*--------------------------------------------------------------*/
        
        if (intNumSeg % 2 != 0)
        {
            throw new IllegalArgumentException(
                "calculateIntegral: número de segmentos debe ser par. " +
                "Valor recibido: " + intNumSeg);
        }
        
        /*--------------------------------------------------------------*/
        /*  Calcular ancho de segmento: h = x / n                       */
        /*--------------------------------------------------------------*/
        
        double dblH = this.dblX / intNumSeg;
        
        /*--------------------------------------------------------------*/
        /*  Inicializar suma con f(0) + f(x)                            */
        /*--------------------------------------------------------------*/
        
        double dblSuma = this.objTDistribution.calculate(0.0) +
                        this.objTDistribution.calculate(this.dblX);
        
        /*--------------------------------------------------------------*/
        /*  Sumar términos internos con coeficientes 4 y 2              */
        /*--------------------------------------------------------------*/
        
        for (int intI = 1; intI < intNumSeg; intI++)
        {
            // Calcular punto xi = i * h
            double dblXi = intI * dblH;
            
            // Evaluar función t en xi
            double dblFxi = this.objTDistribution.calculate(dblXi);
            
            // Aplicar coeficientes de Simpson:
            // - Índices impares: coeficiente 4
            // - Índices pares: coeficiente 2
            if (intI % 2 == 1)
            {
                dblSuma += 4.0 * dblFxi;   // Términos impares
            }
            else
            {
                dblSuma += 2.0 * dblFxi;   // Términos pares
            }
        }
        
        /*--------------------------------------------------------------*/
        /*  Aplicar factor final de Simpson: (h/3) * suma               */
        /*--------------------------------------------------------------*/
        
        return (dblH / 3.0) * dblSuma;
    }
    
    /*------------------------------------------------------------------*/
    /*  Private Methods                                                 */
    /*------------------------------------------------------------------*/
    
    /**
     * Valida todos los parámetros de entrada del constructor.
     *
     * @param dblX             límite superior de integración
     * @param intDof           grados de libertad
     * @param dblError         error aceptable
     * @param intNumSegInicial número inicial de segmentos
     *
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    private void validarParametros(double dblX, int intDof,
                                  double dblError, int intNumSegInicial)
    {
        // Validar límite de integración
        if (dblX < 0.0)
        {
            throw new IllegalArgumentException(
                "SimpsonIntegration5a: x no puede ser negativo. " +
                "Valor recibido: " + dblX);
        }
        
        // Validar grados de libertad
        if (intDof <= 0)
        {
            throw new IllegalArgumentException(
                "SimpsonIntegration5a: dof debe ser > 0. " +
                "Valor recibido: " + intDof);
        }
        
        // Validar error aceptable
        if (dblError <= 0.0)
        {
            throw new IllegalArgumentException(
                "SimpsonIntegration5a: error debe ser > 0. " +
                "Valor recibido: " + dblError);
        }
        
        // Validar número inicial de segmentos
        if (intNumSegInicial <= 0)
        {
            throw new IllegalArgumentException(
                "SimpsonIntegration5a: número de segmentos debe ser > 0. " +
                "Valor recibido: " + intNumSegInicial);
        }
        
        if (intNumSegInicial % 2 != 0)
        {
            throw new IllegalArgumentException(
                "SimpsonIntegration5a: número de segmentos debe ser par. " +
                "Valor recibido: " + intNumSegInicial);
        }
    }
    
    /*------------------------------------------------------------------*/
    /*  Getters                                                         */
    /*------------------------------------------------------------------*/
    
    /**
     * Retorna el resultado final de la integral.
     *
     * @return valor final de P(x)
     */
    public double getResultadoFinal()
    {
        return this.dblResultadoFinal;
    }
    
    /**
     * Retorna el número de iteraciones realizadas.
     *
     * @return número de iteraciones
     */
    public int getIteraciones()
    {
        return this.intIteraciones;
    }
    
    /**
     * Retorna el límite superior de integración.
     *
     * @return valor de x
     */
    public double getX()
    {
        return this.dblX;
    }
    
    /**
     * Retorna los grados de libertad.
     *
     * @return valor de dof
     */
    public int getDof()
    {
        return this.intDof;
    }
}