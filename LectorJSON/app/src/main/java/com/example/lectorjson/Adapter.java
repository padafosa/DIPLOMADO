package com.example.lectorjson;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter extends BaseAdapter{
	  
    protected Activity activity;

    protected ArrayList<Personajes> items;
     
    //CONSTRUCTOR
    public Adapter(Activity activity, ArrayList<Personajes> items) {
        this.activity = activity;
        this.items = items;
      }

    public int getCount() {
        return items.size();
    }
    public Object getItem(int arg0) {
        return items.get(arg0);
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

       if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item, null);
        }
  
        Personajes dir = items.get(position);

        //IMAGEN
        ImageView img = (ImageView) v.findViewById(R.id.imageView1);
		if(img != null) {
			new LoadImage(img).execute(dir.getURLimagen());
		}
        //CAMPOS
        TextView nombre = (TextView) v.findViewById(R.id.username);
        nombre.setText("NOMBRE : "+dir.getNombre());
        TextView clase = (TextView) v.findViewById(R.id.direccion);
        clase.setText("DIRECCION : "+dir.getDireccion());
        TextView equipo = (TextView) v.findViewById(R.id.nit);
        equipo.setText("CONTACO : "+dir.getEquipo());

        // DEVOLVEMOS VISTA
        return v;
    }
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	class LoadImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				mIcon11 = BitmapFactory.decodeStream((InputStream)new URL(urldisplay).getContent());
				 
			} catch (Exception e) {
				//Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
}