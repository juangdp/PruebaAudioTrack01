package es.juangdp.pruebaaudiotrack01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button botonPlay;
    Sonido sonido;
    double mROC = 3.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sonido = new Sonido(this);
        sonido.arrancar();

        botonPlay = (Button) findViewById(R.id.idBotonPlay);
        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlay();
            }
        });
    }

    private void onPlay() {
        mROC = -mROC;
        sonido.setROC(mROC);
    }
}