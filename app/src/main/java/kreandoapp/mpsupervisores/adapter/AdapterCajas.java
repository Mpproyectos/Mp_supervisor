package kreandoapp.mpsupervisores.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


import kreandoapp.mpsupervisores.R;
import kreandoapp.mpsupervisores.clientes.VerPruebasActivity;
import kreandoapp.mpsupervisores.pojo.ModeloCaja;


public class AdapterCajas extends RecyclerView.Adapter<AdapterCajas.CajasviewHolder>{
    MediaPlayer soundagregar;

    List<ModeloCaja> cajalist;
    public Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    private  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION ;

    public AdapterCajas(List<ModeloCaja> cajalist, Context context) {
        this.cajalist = cajalist;
        this.context = context;

    }


    @Override
    public CajasviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cajas,parent,false);
        CajasviewHolder holder= new CajasviewHolder(v);




        return  holder;

    }

    @Override
    public void onBindViewHolder(final CajasviewHolder holder, final int position) {


        final ModeloCaja caj = cajalist.get(position);

        holder.tv_nombre.setText("Caja: "+caj.getNombre_caja());

        holder.tv_fecha.setText(caj.getFecha());

        holder.tv_tipo.setText(caj.getTipo_trabajo());

        if(caj.getVisto().equals("si")){
            holder.img_visto.setVisibility(View.VISIBLE);
            holder.img_nuevo.setVisibility(View.GONE);
        }else{
            holder.img_visto.setVisibility(View.GONE);
            holder.img_nuevo.setVisibility(View.VISIBLE);
        }

        if(!caj.getFase1_urlfoto().equals("")){
            holder.btn_ver.setVisibility(View.VISIBLE);
        }else {
            holder.btn_ver.setVisibility(View.GONE);
        }
        if(!caj.getFase2_urlfoto().equals("")){
            holder.btn_ver.setVisibility(View.VISIBLE);
        }

        holder.btn_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), VerPruebasActivity.class);
                i.putExtra("fase1-foto",caj.getFase1_urlfoto());
                i.putExtra("fase1-fecha",caj.getFase1_fecha());
                i.putExtra("fase1-hora",caj.getFase1_hora());
                i.putExtra("fase1-latitud",caj.getFase1_latitud());
                i.putExtra("fase1-longitud",caj.getFase1_longitud());

                i.putExtra("fase2-foto",caj.getFase2_urlfoto());
                i.putExtra("fase2-fecha",caj.getFase2_fecha());
                i.putExtra("fase2-hora",caj.getFase2_hora());
                i.putExtra("fase2-latitud",caj.getFase2_latitud());
                i.putExtra("fase2-longitud",caj.getFase2_longitud());



                i.putExtra("nombre_caja",caj.getNombre_caja());
                i.putExtra("id_caja",caj.getId_caja());
                i.putExtra("tiempo",caj.getTiempo_transcurrido());
                v.getContext().startActivity(i);
            }
        });


    }//fin del onvinbinholder
    private void animacionadd(){

    }
    @Override
    public int getItemCount() {
        return cajalist.size();
    }

    public class CajasviewHolder extends RecyclerView.ViewHolder{

        TextView tv_nombre,tv_fecha,tv_tipo;
        Button btn_ver;
        ImageView img_nuevo, img_visto;


        public CajasviewHolder(View itemView) {
            super(itemView);

            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            tv_tipo = itemView.findViewById(R.id.tv_tipo);
            btn_ver = itemView.findViewById(R.id.btn_ver);
            img_nuevo = itemView.findViewById(R.id.img_nuevo);
            img_visto = itemView.findViewById(R.id.img_visto);
        }

    }


}