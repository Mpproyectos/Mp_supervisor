package kreandoapp.mpsupervisores.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kreandoapp.mpsupervisores.R;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.AutorizarUsuario;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.ConfirmarUsuario;
import kreandoapp.mpsupervisores.detectorInternet;
import kreandoapp.mpsupervisores.pojo.ModeloNodo;


public class AdapterMisNodos extends RecyclerView.Adapter<AdapterMisNodos.MisNodosViewHolder>{


    List<ModeloNodo> nodoList;
    public Context context;
    detectorInternet internet;

    public AdapterMisNodos(List<ModeloNodo> nodoList, Context context) {
        this.nodoList = nodoList;
        this.context = context;

    }



    @Override
    public MisNodosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_misnodos,parent,false);
        MisNodosViewHolder holder= new MisNodosViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final MisNodosViewHolder holder, final int position) {

        internet = new detectorInternet(context);
        final ModeloNodo nodo = nodoList.get(position);

        holder.nombrenodo.setText(nodo.getNombreNodo());
        holder.tv_fecha.setText(nodo.getFechaNodo());

        holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        if(nodo.getEstado().equals("finalizado")){
            holder.img_finalizado.setVisibility(View.VISIBLE);
            holder.btn_finalizado.setVisibility(View.VISIBLE);

            holder.img_enproceso.setVisibility(View.GONE);
            holder.btn_enproceso.setVisibility(View.GONE);
        }else{
            holder.img_enproceso.setVisibility(View.VISIBLE);
            holder.btn_enproceso.setVisibility(View.VISIBLE);

            holder.img_finalizado.setVisibility(View.GONE);
            holder.btn_finalizado.setVisibility(View.GONE);
        }

    }




    @Override
    public int getItemCount() {
        return nodoList.size();
    }

    public class MisNodosViewHolder extends RecyclerView.ViewHolder{

        TextView tv_fecha,nombrenodo;
        Button btn_finalizado,btn_enproceso,btn_detalle;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;
        ImageView img_finalizado,img_enproceso;


        public MisNodosViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            btn_finalizado = itemView.findViewById(R.id.btn_finalizado);
            img_finalizado = itemView.findViewById(R.id.img_finalizado);
            btn_enproceso = itemView.findViewById(R.id.btn_enproceso);
            img_enproceso = itemView.findViewById(R.id.img_enproceso);
            nombrenodo = itemView.findViewById(R.id.nombrenodo);
            tv_fecha = itemView.findViewById(R.id.tv_fecha);
            btn_detalle = itemView.findViewById(R.id.btn_detalle);
        }

    }

}