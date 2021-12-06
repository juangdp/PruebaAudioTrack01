package es.juangdp.pruebaaudiotrack01;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

class Sonido extends Thread {
    final int SAMPLE_RATE = 8000;
    final int NUM_MUESTRAS = (SAMPLE_RATE * ParametrosPitidoROC.DURACION_PITIDO_ms) / 1000;
    final double[] sample = new double[NUM_MUESTRAS];
    final byte[] sonidoGenerado = new byte[2 * NUM_MUESTRAS];

    private int freq = 0;
    private int freq_last = -1;
    private int periodo_ms = 500;

    private PitidoROC pitidoROC;
    private ParametrosPitidoROC parametrosPitidoROC;
    private boolean mParar = false;
    private AudioTrack mAudioTrack;

    public Sonido(Context contexto) {
        parametrosPitidoROC = new ParametrosPitidoROC();
        parametrosPitidoROC.iniParametros();

        pitidoROC = new PitidoROC(parametrosPitidoROC);

        // Nos aseguramos que se inicializa:
        generaSonido(0);

        int audioSessionId = 0;
        if(null != contexto) {
            audioSessionId = ((AudioManager) contexto.getSystemService(Context.AUDIO_SERVICE)).generateAudioSessionId();
            mAudioTrack = new AudioTrack(
                  new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                        .setUsage(AudioAttributes.USAGE_UNKNOWN)
                        .build(),
                  new AudioFormat.Builder()
                        .setSampleRate(SAMPLE_RATE)
                        .build(),
                  2 * SAMPLE_RATE,
                  AudioTrack.MODE_STATIC,
                  audioSessionId);

            // Nos aseguramos que se inicializa:
            mAudioTrack.write(sonidoGenerado, 0, sonidoGenerado.length);
        }
    }

    @Override
    public void run() {
        super.run();

        while (!mParar) {
            try {
                if(0 != freq) {
                    play(freq);
                }
                sleep(periodo_ms);
            } catch (Exception e) {
//                aviso("AvisoSonoro: " + e.toString(), AVISO_Y_LOG_ERROR, Globales.srvcAltimetroGPS);
                parar();
            } catch (OutOfMemoryError e) {
//                aviso("AvisoSonoro: " + e.toString(), AVISO_Y_LOG_ERROR, Globales.srvcAltimetroGPS);
                parar();
            }
        }
    }

    public synchronized void arrancar() {
        try {
            start();
        } catch (IllegalThreadStateException e) {
//            aviso("AvisoSonoro: " + e.toString(), AVISO_Y_LOG_ERROR, Globales.srvcAltimetroGPS);
        }
    }

    public synchronized void parar() {
        mParar = true;
        if (mAudioTrack != null) {
            mAudioTrack.stop();
            mAudioTrack.release();
            mAudioTrack = null;
        }
    }

    private synchronized void play(final int freq) {
        if (mAudioTrack != null) {
            mAudioTrack.stop();
            generaSonido(freq);
            mAudioTrack.write(sonidoGenerado, 0, sonidoGenerado.length);
            mAudioTrack.play();
        }
    }

    public synchronized void setROC(double ROC) {
        freq = pitidoROC.frecuencia_Hz(ROC);
        periodo_ms = pitidoROC.cadencia_ms(ROC);
    }

    public synchronized byte[] generaSonido(int freq) {
        if(freq_last != freq) {
            freq_last = freq;

            double t;
            double T = 1.0 / SAMPLE_RATE;
            for (int i = 0; i < NUM_MUESTRAS; ++i) {
                t = i * T;
                sample[i] = Math.sin(2 * Math.PI * freq * t);
//            sample[i] = Math.sin(2 * Math.PI * freq        * t) / 4.0
//                      + Math.sin(2 * Math.PI * freq * 1.7  * t) / 4.0
//                      + Math.sin(2 * Math.PI * freq * 2.35 * t) / 4.0
//                      + Math.sin(2 * Math.PI * freq * 3.0  * t) / 4.0;
            }

            // convert to 16 bit pcm sound array
            // assumes the sample buffer is normalised.
            int idx = 0;
            for (final double dVal : sample) {
                // scale to maximum amplitude
                final short val = (short) ((dVal * 32767));
                // in 16 bit wav PCM, first byte is the low order byte
                sonidoGenerado[idx++] = (byte) (val & 0x00ff);
                sonidoGenerado[idx++] = (byte) ((val & 0xff00) >>> 8);
            }
        }
        return sonidoGenerado;
    }
}

