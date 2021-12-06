package es.juangdp.pruebaaudiotrack01;

import android.content.Context;

public class ParametrosPitidoROC {
    public static final int DURACION_PITIDO_ms = 3000;

    public float umbralMinAsc_m_s;
    public float umbralMaxAsc_m_s;
    public float umbralMinDsc_m_s;
    public float umbralMaxDsc_m_s;
    public int freqInicialAsc;
    public int freqFinalAsc;
    public int freqInicialDsc;
    public int freqFinalDsc;
    public int cadenciaInicialAsc_ms;
    public int cadenciaFinalAsc_ms;
    public int cadenciaInicialDsc_ms;
    public int cadenciaFinalDsc_ms;

    public void iniParametros() {
        umbralMinAsc_m_s = 0.1f;
        umbralMaxAsc_m_s = 4.0f;
        freqInicialAsc = 300;
        freqFinalAsc = 1400;
        cadenciaInicialAsc_ms = 600;
        cadenciaFinalAsc_ms = 400;

        umbralMinDsc_m_s = -0.1f;
        umbralMaxDsc_m_s = -4.0f;
        freqInicialDsc = 150;
        freqFinalDsc = 300;
        cadenciaInicialDsc_ms = 600;
        cadenciaFinalDsc_ms = 400;
    }
}
