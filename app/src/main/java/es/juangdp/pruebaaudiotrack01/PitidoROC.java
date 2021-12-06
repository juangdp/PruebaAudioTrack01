package es.juangdp.pruebaaudiotrack01;

/**
 * Proporciona los parámetros del sonido a generar
 */
class PitidoROC {

    private final int PERIODO_MINIMO = 2 * ParametrosPitidoROC.DURACION_PITIDO_ms;
    private ParametrosPitidoROC param;

    public PitidoROC(ParametrosPitidoROC param) {
        this.param = param;
    }


    public int frecuencia_Hz(double ROC_m_s) {
        double resp = 0.0;

        if(ROC_m_s >= param.umbralMaxAsc_m_s) {
            resp = param.freqFinalAsc;
        }
        else if(ROC_m_s >= param.umbralMinAsc_m_s) {
            resp = param.freqInicialAsc
                  + (param.freqFinalAsc - param.freqInicialAsc)
                  * (ROC_m_s - param.umbralMinAsc_m_s)
                  / (param.umbralMaxAsc_m_s - param.umbralMinAsc_m_s);
        }
        else if(ROC_m_s <= param.umbralMaxDsc_m_s) {
            resp = param.freqFinalDsc;
        }
        else if(ROC_m_s <= param.umbralMinDsc_m_s) {
            resp = param.freqInicialDsc
                  + (param.freqFinalDsc - param.freqInicialDsc)
                  * (ROC_m_s - param.umbralMinDsc_m_s)
                  / (param.umbralMaxDsc_m_s - param.umbralMinDsc_m_s);
        }

        return (int)resp;
    }

    public int cadencia_ms(double ROC_m_s) {
        double resp = 0.0;

        if(ROC_m_s >= param.umbralMaxAsc_m_s) {
            resp = param.cadenciaFinalAsc_ms;
        }
        else if(ROC_m_s >= param.umbralMinAsc_m_s) {
            resp = param.cadenciaInicialAsc_ms
                  + (param.cadenciaFinalAsc_ms - param.cadenciaInicialAsc_ms)
                  * (ROC_m_s - param.umbralMinAsc_m_s)
                  / (param.umbralMaxAsc_m_s - param.umbralMinAsc_m_s);
        }
        else if(ROC_m_s <= param.umbralMaxDsc_m_s) {
            resp = param.cadenciaFinalDsc_ms;
        }
        else if(ROC_m_s <= param.umbralMinDsc_m_s) {
            resp = param.cadenciaInicialDsc_ms
                  + (param.cadenciaFinalDsc_ms - param.cadenciaInicialDsc_ms)
                  * (ROC_m_s - param.umbralMinDsc_m_s)
                  / (param.umbralMaxDsc_m_s - param.umbralMinDsc_m_s);
        }

        return (int)Math.max(resp, PERIODO_MINIMO);
    }
}
