package com.example.prroyectoparte2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Main2Activity_Nivel1 extends AppCompatActivity {
   
    private EditText et_respuesta;
    private ImageView iv_dos, iv_uno, iv_vidas, iv_signo;
    private TextView tv_nombre, tv_score;
    private MediaPlayer mp, mp_bad, mp_great;
    private MediaPlayer mp_nivel2, mp_nivel3, mp_nivel4;
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
        iv_signo = (ImageView) findViewById((R.id.iv_signo));

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador: "+ nombre_jugador);

        score = Integer.parseInt(getIntent().getStringExtra("score"));// le asigno el score que le pase por el intent
        string_score = getIntent().getStringExtra("score");

        tv_score.setText("Score: " + string_score);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);

        /*
        Codigo para cargar los archivos de auidos para los diferentes niveles
        mp_nivel2 = MediaPlayer.create(this, R.raw.amusement);
        mp_nivel3 = MediaPlayer.create(this, R.raw.bob_party);
        mp_nivel4 = MediaPlayer.create(this, R.raw.children_s_games);
        mp_nivel2 = MediaPlayer.create(this, R.raw.amusement);
        
         */
        numeroAleatorio();
    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void numeroAleatorio() {

        int level = 1;
        String operacion = "adicion";
        String [] operaciones = {"adicion", "resta", "multiplicacion"};
        int operacionAleatorio = 0;
        Random random = new Random();

        if(score >= 0 && score <= 10) {
            level = 1;
        } if (score >= 11 && score <= 20){
            level = 2;
            operacionAleatorio =  getRandomNumberInRange(0,1);
            //   Toast.makeText(this, "level 2 - Operacion : " + operaciones[operacionAleatorio] , Toast.LENGTH_SHORT).show();
            operacion = operaciones[operacionAleatorio];
            int id = getResources().getIdentifier(operacion, "drawable", getPackageName());
            iv_signo.setImageResource(id);

        } if(score >= 21 && score <=30){
            level = 3;
            operacionAleatorio = getRandomNumberInRange(0,1);
            //    Toast.makeText(this, "level 3 - Operacion : " + operaciones[operacionAleatorio] , Toast.LENGTH_SHORT).show();
            operacion = operaciones[operacionAleatorio];
            int id = getResources().getIdentifier(operacion, "drawable", getPackageName());
            iv_signo.setImageResource(id);
        } if(score >= 31 && score <=40) {
            level = 4;
            operacionAleatorio = getRandomNumberInRange(0,2);
            //   Toast.makeText(this, "level 4 - Operacion : " + operaciones[operacionAleatorio] , Toast.LENGTH_SHORT).show();
            operacion = operaciones[operacionAleatorio];
            int id = getResources().getIdentifier(operacion, "drawable", getPackageName());
            iv_signo.setImageResource(id);
        }

        int randomLimit = level == 1 ? level * 10 : numero.length;

        if (score >= (level-1)*10 && score <= level * 10 ) {

            aleatorio1 = ThreadLocalRandom.current().nextInt(0, randomLimit);
            int aleatorio2Limit = randomLimit - aleatorio1;
            aleatorio2 = ThreadLocalRandom.current().nextInt(0, aleatorio2Limit);

            if(aleatorio2 > aleatorio1 && operacion == "resta") {
                aleatorio1 = aleatorio2;
                aleatorio2 = aleatorio1;

            }


            if(operacion == "adicion"){
                resultado = aleatorio1 + aleatorio2;
            }

            if(operacion == "resta"){

               // aleatorio1 = ThreadLocalRandom.current().nextInt(0, randomLimit-1);
               // aleatorio2 = ThreadLocalRandom.current().nextInt(0, aleatorio1);

                resultado = aleatorio1 - aleatorio2;
            }

            if(operacion == "multiplicacion"){

                aleatorio1 = ThreadLocalRandom.current().nextInt(0, 10);
                aleatorio2 = ThreadLocalRandom.current().nextInt(0, 4);
                resultado = aleatorio1 * aleatorio2;
            }



         //   Toast.makeText(this, + aleatorio1 + " + " + aleatorio2 + " = " + resultado , Toast.LENGTH_SHORT).show();


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
                            modificarInsertarEnLaBase();
                            //insertarEnLabase
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

    public void insertarEnLaBase(){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String nombre = nombre_jugador;
        int puntuacion = score;
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("score", puntuacion);
        long rowInserted = baseDatos.insert("puntaje", null, registro);

        if(rowInserted != -1)
            Toast.makeText(this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();

        baseDatos.close();

    }
    public void modificarInsertarEnLaBase(){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String nombres = nombre_jugador;
        int puntuacion = score;
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombres);
        registro.put("score", puntuacion);
        int cantidad = baseDatos.update("puntaje", registro, "nombre"+"='"+nombres+"'", null);
        if(cantidad == 1){
            Toast.makeText(this,"Se actualizo el puntaje", Toast.LENGTH_LONG).show();
        }else{// si no existen datos que modificar, inserta el nuevo jugador
            long rowInserted = baseDatos.insert("puntaje", null, registro);
            if(rowInserted != -1)
                Toast.makeText(this, "Se guardo la puntuacion " + rowInserted, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Something wrong", Toast.LENGTH_SHORT).show();
        }
        baseDatos.close();
    }





}
