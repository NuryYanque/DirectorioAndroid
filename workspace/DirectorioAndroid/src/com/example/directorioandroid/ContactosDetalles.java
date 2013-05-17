package com.example.directorioandroid;


import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class ContactosDetalles extends Activity {
	protected TextView cont_nombre;
	protected TextView cont_descripcion;
	protected TextView cont_phoneCasa;
	protected TextView cont_phoneCel;
	protected TextView cont_email;
    protected int contactoId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_detalles);
        
        contactoId = getIntent().getIntExtra("CONTACTO_ID", 0);
        SQLiteDatabase db = (new ContactosDatabase(this)).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacto WHERE _id = ?", 
		new String[]{""+contactoId});

        if (cursor.getCount() == 1)
        {
        	cursor.moveToFirst();
        
        	cont_nombre = (TextView) findViewById(R.id.cont_nombre);
        	cont_nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")) + " " + cursor.getString(cursor.getColumnIndex("apellido")));
	
        	cont_descripcion = (TextView) findViewById(R.id.cont_descripcion);
        	cont_descripcion.setText(cursor.getString(cursor.getColumnIndex("descripcion")));

        	cont_phoneCasa = (TextView) findViewById(R.id.cont_phoneCasa);
        	cont_phoneCasa.setText(cursor.getString(cursor.getColumnIndex("phoneCasa")));

        	cont_phoneCel = (TextView) findViewById(R.id.cont_phoneCel);
        	cont_phoneCel.setText(cursor.getString(cursor.getColumnIndex("phoneCel")));

        	cont_email = (TextView) findViewById(R.id.cont_email);
        	cont_email.setText(cursor.getString(cursor.getColumnIndex("email")));
	        
        }
 
    }

}
