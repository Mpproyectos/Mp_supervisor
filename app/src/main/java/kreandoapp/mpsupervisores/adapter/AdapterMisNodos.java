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



    }


    @Override
    public int getItemCount() {
        return nodoList.size();
    }

    public class MisNodosViewHolder extends RecyclerView.ViewHolder{

        TextView tv_valnodo,tv_nombre;
        Button btn_confirmar,btn_autorizar;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;
        ImageView img_new,img_finalizado,img_visto;


        public MisNodosViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);



        }

    }

}