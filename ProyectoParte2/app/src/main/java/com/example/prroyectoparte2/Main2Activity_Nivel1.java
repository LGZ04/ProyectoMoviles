package com.example.prroyectoparte2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ThreadLocalRandom;

public class Main2Activity_Nivel1 extends AppCompatActivity {
   
    private EditText et_respuesta;
    private ImageView iv_dos, iv_uno, iv_vidas;
    private TextView tv_nombre, tv_score;
    private MediaPlayer mp, mp_bad, mp_great;
    //probando

    int aleatorio1, aleatorio2, resultado, score, vidas = 3;
    String nombre_jugador, string_score, string_vidas;
    String[] numero = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "dieciocho", "diecinueve", "veinte"};
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__nivel1);

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_vidas = (ImageView) findViewById(R.id.iv_vidas);
        iv_uno = (ImageView) findViewById(R.id.iv_num1);
        iv_dos = (ImageView) findViewById(R.id.iv_num2);
        et_respuesta = (EditText) findViewById(R.id.et_resultado);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: "+ nombre_jugador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);
        numeroAleatorio();
    }

    public void numeroAleatorio() {

        int level = 1;

        if(score >= 0 && score <= 10) {
            level = 1;
        } if (score >= 11 && score <= 20){
            level = 2;
        } if(score >= 21 && score <=30){
            level = 3;
        } if(score >= 31 && score <=40) {
            level = 4;
        }

        int randomLimit = level == 1 ? level * 10 : numero.length;

        if (score >= (level-1)*10 && score <= level * 10 ) {

            aleatorio1 = ThreadLocalRandom.current().nextInt(0, randomLimit);

            int aleatorio2Limit = randomLimit - aleatorio1;

            aleatorio2 = ThreadLocalRandom.current().nextInt(0, aleatorio2Limit);
            resultado = aleatorio1 + aleatorio2;
            Toast.makeText(this, + aleatorio1 + " + " + aleatorio2 + " = " + resultado , Toast.LENGTH_SHORT).show();


            if (score <= 40) {
                for (int i = 0; i < numero.length; i++) {
                 int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if (aleatorio1 == i) {
                        iv_uno.setImageResource(id);
                    }
                    if (aleatorio2 == i) {

                        iv_dos.setImageResource(id);
                    }
                }
            } else {
                numeroAleatorio();
            }
        }
          /*
        }else{
            Intent intent = new Intent(this, Main2Activity_Nivel2.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);
            startActivity(intent);
            finish();
            mp.stop();
            mp.release();

        }
       */
    }



    public void evaluar(View vista) {
        String respuesta = et_respuesta.getText().toString();

        if (!respuesta.isEmpty()) {
            int respEntera = Integer.parseInt(respuesta);
            if (resultado == respEntera) {
                mp_great.start();
                score++;
                tv_score.setText("Score: "+ score);
                
            } else {
                mp_bad.start();
                vidas--;
                switch (vidas) {
                    case 1:
                    Toast.makeText(this, "Queda 1 vida", Toast.LENGTH_SHORT).show();
                    iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                        case 2:
                    iv_vidas.setImageResource(R.drawable.dosvidas);
                        break; 
                        case 0:
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        mp.stop();
                        mp.release();
                        finish();
                        break;
                    
                }
            }
            this.et_respuesta.setText("");
            numeroAleatorio();
        }else{
            Toast.makeText(this, "Debes dar una respuesta", Toast.LENGTH_LONG).show();
        }
        
    }
}
