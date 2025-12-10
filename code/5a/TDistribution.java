/***************************************************************************
 * Programa:  Regla de Simpson para Distribución t-Student - PSP5
 * Clase:     TDistribution
 * Autor:     [Karla Sofía Castro Pérez]
 * Fecha:     [10-12-2025]
 *
 * Descripción:
 *   Implementa la función de densidad de probabilidad (PDF) de la
 *   distribución t-Student. Calcula el valor f(x) para un x dado y
 *   grados de libertad específicos usando la fórmula estándar.
 *
 * Fórmula Matemática:
 *   f(x) = Γ((ν+1)/2) / [√(νπ) * Γ(ν/2)] * [1 + x²/ν]^(-(ν+1)/2)
 *   donde ν = grados de libertad (dof)
 *
 * Uso:
 *   TDistribution tDist = new TDistribution(dof);
 *   double fx = tDist.calculate(x);
 *
 * Dependencias:
 *   - GammaFunction.java para cálculo de función Gamma
 *
 * Restricciones:
 *   - dof debe ser > 0
 *   - La función es simétrica: f(-x) = f(x)
 ***************************************************************************/

/**
 * Clase TDistribution.
 * <p>
 * Proporciona métodos para calcular la función de densidad de
 * probabilidad de la distribución t-Student. Esta clase encapsula
 * el cálculo del coeficiente constante y el término dependiente de x.
 */
public class TDistribution
{
    /*------------------------------------------------------------------*/
    /*  Attributes                                                      */
    /*------------------------------------------------------------------*/
    
    /** Grados de libertad de la distribución t-Student */
    private double dblDof;
    
    /** Coeficiente constante precalculado para optimización */
    private double dblCoefficient;
    
    /*------------------------------------------------------------------*/
    /*  Constructor                                                     */
    /*------------------------------------------------------------------*/
    
    /**
     * Constructor de la clase TDistribution.
     * <p>
     * Inicializa la distribución con los grados de libertad especificados
     * y precalcula el coeficiente constante para mejorar el rendimiento
     * en evaluaciones posteriores.
     *
     * @param dblDofParam grados de libertad (debe ser > 0)
     *
     * @throws IllegalArgumentException si dblDofParam ≤ 0
     */
    public TDistribution(double dblDofParam)
    {
        // Validar que los grados de libertad sean positivos
        if (dblDofParam <= 0.0)
        {
            throw new IllegalArgumentException(
                "TDistribution: dof debe ser > 0. Valor recibido: " + dblDofParam);
        }
        
        // Asignar grados de libertad
        this.dblDof = dblDofParam;
        
        // Precalcular coeficiente constante una sola vez
        this.dblCoefficient = calculateCoefficient();
    }
    
    /*------------------------------------------------------------------*/
    /*  Public Methods                                                  */
    /*------------------------------------------------------------------*/
    
    /**
     * Calcula el valor de la función de densidad en un punto x.
     * <p>
     * Evalúa la fórmula completa de la PDF t-Student:
     *   f(x) = coeficiente * [1 + x²/dof]^(-(dof+1)/2)
     *
     * @param dblX punto en el que evaluar la función
     * @return valor de f(x) para la distribución t
     */
    public double calculate(double dblX)
    {
        /*--------------------------------------------------------------*/
        /*  Cálculo del término dependiente de x                        */
        /*--------------------------------------------------------------*/
        
        // Calcular término base: 1 + x²/dof
        double dblTerminoBase = 1.0 + (dblX * dblX) / this.dblDof;
        
        // Calcular exponente: -(dof+1)/2
        double dblExponente = -(this.dblDof + 1.0) / 2.0;
        
        // Elevar término base al exponente
        double dblTerminoPotencia = Math.pow(dblTerminoBase, dblExponente);
        
        /*--------------------------------------------------------------*/
        /*  Combinar coeficiente constante con término variable         */
        /*--------------------------------------------------------------*/
        
        return this.dblCoefficient * dblTerminoPotencia;
    }
    
    /*------------------------------------------------------------------*/
    /*  Private Methods                                                 */
    /*------------------------------------------------------------------*/
    
    /**
     * Calcula el coeficiente constante de la distribución t.
     * <p>
     * Coeficiente = Γ((dof+1)/2) / [√(dof*π) * Γ(dof/2)]
     * Este valor es constante para una distribución t específica
     * y se precalcula una sola vez en el constructor.
     *
     * @return coeficiente de normalización constante
     */
    private double calculateCoefficient()
    {
        /*--------------------------------------------------------------*/
        /*  Calcular numerador: Γ((dof+1)/2)                            */
        /*--------------------------------------------------------------*/
        
        double dblGammaNumerador = GammaFunction.calculateHalf(this.dblDof);
        
        /*--------------------------------------------------------------*/
        /*  Calcular denominador: √(dof*π) * Γ(dof/2)                   */
        /*--------------------------------------------------------------*/
        
        double dblGammaDenominador = GammaFunction.calculate(this.dblDof / 2.0);
        double dblRaiz = Math.sqrt(this.dblDof * Math.PI);
        double dblDenominadorCompleto = dblRaiz * dblGammaDenominador;
        
        /*--------------------------------------------------------------*/
        /*  Calcular coeficiente final                                 */
        /*--------------------------------------------------------------*/
        
        return dblGammaNumerador / dblDenominadorCompleto;
    }
    
    /*------------------------------------------------------------------*/
    /*  Getters                                                         */
    /*------------------------------------------------------------------*/
    
    /**
     * Retorna los grados de libertad de esta distribución.
     *
     * @return grados de libertad (dof)
     */
    public double getDof()
    {
        return this.dblDof;
    }
}