package com.example.directorioandroid;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactosList extends ListActivity {

	protected EditText etBuscar;
	protected SQLiteDatabase db;
	protected Cursor cursor;
	protected ListAdapter adapter;
	
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = (new ContactosDatabase(this)).getWritableDatabase();
        etBuscar = (EditText) findViewById (R.id.etBuscar);
    }
    
    public void buscarContactos(View view) {
    	
		cursor = db.rawQuery("SELECT _id, nombre, apellido, descripcion FROM contacto WHERE nombre || ' ' || apellido LIKE ?", 
						new String[]{"%" + etBuscar.getText().toString() + "%"});
		adapter = new SimpleCursorAdapter(
				this, 
				R.layout.contacto_list_item, 
				cursor, 
				new String[] {"nombre", "apellido", "descripcion"}, 
				new int[] {R.id.tvNombre, R.id.tvApellido, R.id.tvDescripcion});
		setListAdapter(adapter);
    }
    
    public void onListItemClick(ListView parent, View view, int position, long id) {
    	Intent intent = new Intent(this, ContactosDetalles.class);
    	Cursor cursor = (Cursor) adapter.getItem(position);
    	intent.putExtra("CONTACTO_ID", cursor.getInt(cursor.getColumnIndex("_id")));
    	startActivity(intent);
    }

}
