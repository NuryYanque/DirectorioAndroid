package com.example.directorioandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactosDatabase extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ContactosBBDD";
	
	public ContactosDatabase(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sql = "CREATE TABLE IF NOT EXISTS contacto (" +
						"_id INTEGER PRIMARY KEY AUTOINCREMENT, " + 
						"nombre TEXT, " +
						"apellido TEXT, " +
						"descripcion TEXT, " +
						"phoneCasa TEXT, " +
						"phoneCel TEXT, " +
						"email TEXT)";
		db.execSQL(sql);
		
		ContentValues values = new ContentValues();

		values.put("nombre", "John");
		values.put("apellido", "Smith");
		values.put("descripcion", "amigo");
		values.put("phoneCasa", "054-456312");
		values.put("phoneCel", "957843332");
		values.put("email", "jsmith@email.com");
		db.insert("contacto", "apellido", values);		
		
		values.put("nombre", "Nury");
		values.put("apellido", "Arosquipa");
		values.put("descripcion", "Compañera de Trabajo");
		values.put("phoneCasa", "054-486882");
		values.put("phoneCel", "957932005");
		values.put("email", "yuleny.aryan@gmail.com");
		db.insert("contacto", "apellido", values);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS contacto");
		onCreate(db);
	}
	
}
