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
        metodoRecord();

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);
    }

    public void jugar(View vista) {
        String nombre = et_nombre.getText().toString();
        String score = String.valueOf(consultarJugadorEnLaBase(nombre));
        if (!nombre.isEmpty()) {
            mp.stop();
            mp.release();
            Intent intent = new Intent(this, Main2Activity_Nivel1.class);
            intent.putExtra("jugador", nombre);
            intent.putExtra("score", score);
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        // cargamos el estado de la imagen
       // image = savedInstanceState.getParcelable("BitmapImage");
        //targetImage.setImageBitmap(image);
       // textTargetUri.setText(savedInstanceState.getString("path_to_picture"));

        // cargamos el estado de la musica

        // cargamos el estado de los textview

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Guardamos el estado de la imagen

       // savedInstanceState.putParcelable("BitmapImage", iv_personaje.getDrawable());
       // savedInstanceState.putString("path_to_picture", picture_location);

        //Guardamos el estado de los textview
        //savedInstanceState.putString("jugador", et_nombre.getText().toString());
        //savedInstanceState.putString("Password", mPassword);
        // savedInstanceState.putString("UserID", mUserID);

        //Guardamos el estado de la musica


        super.onSaveInstanceState(savedInstanceState);
    }

    public void eliminarPuntajeEnLaBase(View vista){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();
        String nombres = et_nombre.getText().toString();

        if (!nombres.isEmpty()) {
            int cantidad = DB.delete("puntaje", "nombre"+"='"+nombres+"'",null);
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
        metodoRecord();
    }


    //devuelve el score del jugador
    //se le pasa por el intent al main2Activity_nivel1
    public int consultarJugadorEnLaBase(String jugador){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        Cursor fila = baseDatos.rawQuery("select score, nombre FROM " + "puntaje" + " WHERE "+"nombre"+"='"+jugador+"'",null);

        if(fila.moveToFirst()){
            int temp_score = fila.getInt(0);
            baseDatos.close();
            return temp_score;

        }else{
            baseDatos.close();
            return 0;
        }
    }

    public void metodoRecord(){
        AdminSQLiteHelper admin = new AdminSQLiteHelper(this, "db", null, 1);
        SQLiteDatabase DB = admin.getWritableDatabase();
        TextView textView = tv_bestScore;
        Cursor consulta = DB.rawQuery("select nombre, score from puntaje where score = (select MAX(score) from puntaje)", null);
        
        if (consulta.moveToFirst()) {
            String temp_nombre = consulta.getString(0);
            int temp_score = consulta.getInt(1);

            StringBuilder sb = new StringBuilder();
            sb.append("Record -> Puntaje: " + temp_score + " Jugador(a): " + temp_nombre);
            textView.setText(sb.toString());
        }else{
            tv_bestScore.setText("Record -> No existe ningun record, a jugar!");
        }
        DB.close();
    }


}
