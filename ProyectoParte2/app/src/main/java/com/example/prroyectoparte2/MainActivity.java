package com.example.prroyectoparte2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int aleatorio = ((int) (Math.random() * 10));
    private EditText et_nombre;
    private ImageView iv_personaje;
    private MediaPlayer mp;
    private TextView tv_bestScore;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        et_nombre = (EditText) findViewById(R.id.et_nombre);
        iv_personaje = (ImageView) findViewById(R.id.iv_personaje);
        tv_bestScore = (TextView) findViewById(R.id.tv_BestScore);

        int id;
     
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        
        
        if (aleatorio == 0 || aleatorio == 10) {
            id = getResources().getIdentifier("mango","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (aleatorio == 1 || aleatorio == 9) {
            id = getResources().getIdentifier("fresa","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (aleatorio == 2 || aleatorio == 8) {
            id = getResources().getIdentifier("manzana","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (aleatorio == 3 || aleatorio == 7) {
            id = getResources().getIdentifier("sandia","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (aleatorio == 4 || aleatorio == 5) {
            id = getResources().getIdentifier("naranja","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        } else if (aleatorio == 6) {
            id = getResources().getIdentifier("uva","drawable", getPackageName());
            iv_personaje.setImageResource(id);
        }

        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();

       Cursor consulta = DB.rawQuery("select nombre, score from puntaje where score = (select MAX(score) from puntaje)", null);
       // Cursor consulta = DB.rawQuery("select nombre,MAX(score) from puntaje", null);
        //Cursor consulta = DB.rawQuery("select nombre, score from puntaje", new String[]{});
        if (consulta.moveToFirst()) {
            String temp_nombre = consulta.getString(0);
            int temp_score = consulta.getInt(1);
            TextView textView = tv_bestScore;
            StringBuilder sb = new StringBuilder();
            sb.append("Record-> Puntaje: " + temp_score + " Jugador(a): " + temp_nombre);
            //sb.append(temp_score);
           // sb.append(" de ");
            //sb.append(temp_nombre);
            textView.setText(sb.toString());
        }
        DB.close();
        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);
    }

    public void jugar(View vista) {
        String nombre = et_nombre.getText().toString();
        if (!nombre.isEmpty()) {
            mp.stop();
            mp.release();
            Intent intent = new Intent(this, Main2Activity_Nivel1.class);
            intent.putExtra("jugador", nombre);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(this, "Primero debes escribir tu nombre", Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this.et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    @Override
    public void onBackPressed() {
        //
    }


    public void eliminarPuntajeEnLaBase(View vista){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();
        String nombre = et_nombre.getText().toString();

        if (!nombre.isEmpty()) {
            int cantidad = DB.delete("puntaje", "nombre=" + nombre,null);
            if (cantidad == 1) {
                Toast.makeText(this, "Se reinicio el puntaje", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No tenia puntaje que reiniciar", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Primero debes escribir tu nombre", Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(this.et_nombre, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
