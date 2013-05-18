package com.example.directorioandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactosDetalles extends ListActivity {
	protected TextView cont_nombre;
	protected TextView cont_descripcion;
	protected TextView cont_phoneCasa;
	protected TextView cont_phoneCel;
	protected TextView cont_email;
    protected int contactoId;
    static final String tag="ok";
    
    private int requestCode = 1;

    protected List<ContactoAccion> actions;
    protected ContactoAccionAdapter adapter;
    SQLiteDatabase db;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_detalles);
        
        contactoId = getIntent().getIntExtra("CONTACTO_ID", 0);
        db = (new ContactosDatabase(this)).getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM contacto WHERE _id = ?", 
		new String[]{""+contactoId});

        if (cursor.getCount() == 1)
        {
        	cursor.moveToFirst();
        
        	cont_nombre = (TextView) findViewById(R.id.cont_nombre);
        	cont_nombre.setText(cursor.getString(cursor.getColumnIndex("nombre")) + " " + cursor.getString(cursor.getColumnIndex("apellido")));
	
        	cont_descripcion = (TextView) findViewById(R.id.cont_descripcion);
        	cont_descripcion.setText(cursor.getString(cursor.getColumnIndex("descripcion")));

        	actions = new ArrayList<ContactoAccion>();
        	
        	String phoneCasa = cursor.getString(cursor.getColumnIndex("phoneCasa"));
            if (phoneCasa != null) {
                    actions.add(new ContactoAccion("Llamar a casa",phoneCasa, ContactoAccion.ACTION_CALL));
            }
            
            String phoneCel = cursor.getString(cursor.getColumnIndex("phoneCel"));
            if (phoneCel != null) {
                    actions.add(new ContactoAccion("Llamar al cel", phoneCel, ContactoAccion.ACTION_CALL));
                    actions.add(new ContactoAccion("SMS", phoneCel, ContactoAccion.ACTION_SMS));
            }
    
            String email = cursor.getString(cursor.getColumnIndex("email"));
            if (email != null) {
                    actions.add(new ContactoAccion("Email", email, ContactoAccion.ACTION_EMAIL));
            }
               
            adapter = new ContactoAccionAdapter();
            setListAdapter(adapter);
        } 
    }
    
    public void onListItemClick(ListView parent, View view, int position, long id) {
        
        ContactoAccion action = actions.get(position);

        Intent intent;
        switch (action.getType()) {

            case ContactoAccion.ACTION_CALL:  
                    Uri callUri = Uri.parse("tel:" + action.getData());  
                    intent = new Intent(Intent.ACTION_CALL, callUri); 
                startActivity(intent);
                    break;

            case ContactoAccion.ACTION_EMAIL:  
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{action.getData()});
            startActivity(intent);
            break;
            
            case ContactoAccion.ACTION_SMS:  
                    Uri smsUri = Uri.parse("sms:" + action.getData());  
                    intent = new Intent(Intent.ACTION_VIEW, smsUri); 
                startActivity(intent);
                    break;
        }
    }    
    
    class ContactoAccionAdapter extends ArrayAdapter<ContactoAccion> {

    	ContactoAccionAdapter() {
			super(ContactosDetalles.this, R.layout.action_list_item, actions);
		}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		ContactoAccion action = actions.get(position);
    		LayoutInflater inflater = getLayoutInflater();
    		View view = inflater.inflate(R.layout.action_list_item, parent, false);
    		TextView label = (TextView) view.findViewById(R.id.label);
    		label.setText(action.getLabel());
    		TextView data = (TextView) view.findViewById(R.id.data);
    		data.setText(action.getData());
    		return view;
    	}
    	
    }
    
    public void editarContacto(View view)
    {
    	/*Intent i=new Intent(this, EditarContactoActivity.class);
    	startActivityForResult(i,requestCode);*/
    }
    
    public void eliminarContacto(View view)
    {
    	//db = (new ContactosDatabase(this)).getWritableDatabase();
    	db.delete("contacto","_id"+"="+contactoId,null);
    	Log.d(tag,"Se ELIMINO correctamente!!");   	
    }

}
