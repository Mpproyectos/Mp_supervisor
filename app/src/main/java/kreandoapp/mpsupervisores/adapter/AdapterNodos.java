package kreandoapp.mpsupervisores.adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import kreandoapp.mpsupervisores.DetalleNodoActivity;
import kreandoapp.mpsupervisores.R;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.AutorizarUsuario;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.ConfirmarUsuario;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.ListaSubcategoriasActivity;
import kreandoapp.mpsupervisores.detectorInternet;
import kreandoapp.mpsupervisores.pojo.Categoria;
import kreandoapp.mpsupervisores.pojo.ModeloNodo;


public class AdapterNodos extends RecyclerView.Adapter<AdapterNodos.NodosViewHolder>{


    List<ModeloNodo> nodoList;
    public Context context;
    detectorInternet internet;

    public AdapterNodos(List<ModeloNodo> nodoList, Context context) {
        this.nodoList = nodoList;
        this.context = context;

    }



    @Override
    public NodosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_nodolist,parent,false);
        NodosViewHolder holder= new NodosViewHolder(v);
        return  holder;

    }

    @Override
    public void onBindViewHolder(final NodosViewHolder holder, final int position) {



        internet = new detectorInternet(context);

        final ModeloNodo nodo = nodoList.get(position);



                String mail = nodo.getNombreUsuario();

                String[] parts = mail.split("@");
                    String part1 = parts[0]; // 123

                holder.tv_nombre.setText(part1);


            holder.tv_valnodo.setText(nodo.getNombreNodo());

            if(nodo.getAutorizacion1().equals("no") && nodo.getNotificacion1().equals("si") && nodo.getEstado().equals("trabajando")){
                holder.btn_autorizar.setVisibility(View.VISIBLE);
                holder.btn_confirmar.setVisibility(View.GONE);


                if(nodo.getVisto().equals("si")){
                    holder.img_visto.setVisibility(View.VISIBLE);
                    holder.img_new.setVisibility(View.GONE);
                }else {
                    holder.img_new.setVisibility(View.VISIBLE);
                    holder.img_visto.setVisibility(View.GONE);
                }

            }
            if(nodo.getAutorizacion2().equals("no") && nodo.getNotificacion2().equals("si") && nodo.getEstado().equals("finalizado")){
                holder.btn_confirmar.setVisibility(View.VISIBLE);
                holder.btn_autorizar.setVisibility(View.GONE);
                holder.img_finalizado.setVisibility(View.VISIBLE);
                holder.btn_detalle.setVisibility(View.VISIBLE);

            }

            holder.btn_autorizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail = nodo.getNombreUsuario();

                    String[] parts = mail.split("@");
                    String part1 = parts[0]; // 123



                    Intent i = new Intent(context, AutorizarUsuario.class);

                    i.putExtra("nombre_nodo",nodo.getNombreNodo());
                    i.putExtra("id_usuario",nodo.getId_usuario());
                    i.putExtra("nombre",part1);
                    i.putExtra("id_nodo",nodo.getIdnodo());
                    v.getContext().startActivity(i);

                }
            });
            holder.btn_detalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent i = new Intent(context, DetalleNodoActivity.class);

                    SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    myEdit.putString("id_nodo", nodo.getIdnodo());
                    myEdit.putString("nombre_nodo", nodo.getNombreNodo());
                    myEdit.commit();

                    i.putExtra("nombre_nodo",nodo.getNombreNodo());
                    i.putExtra("id_usuario",nodo.getId_usuario());
                    i.putExtra("nombre",part1);
                    i.putExtra("id_nodo",nodo.getIdnodo());
                    v.getContext().startActivity(i);


                }
            });

            holder.btn_confirmar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mail = nodo.getNombreUsuario();

                    String[] parts = mail.split("@");
                    String part1 = parts[0]; // 123



                    Intent i = new Intent(context, ConfirmarUsuario.class);

                    i.putExtra("nombre_nodo",nodo.getNombreNodo());
                    i.putExtra("id_usuario",nodo.getId_usuario());
                    i.putExtra("nombre",part1);
                    i.putExtra("id_nodo",nodo.getIdnodo());
                    v.getContext().startActivity(i);
                }
            });






    }


    @Override
    public int getItemCount() {
        return nodoList.size();
    }

    public class NodosViewHolder extends RecyclerView.ViewHolder{

        TextView tv_valnodo,tv_nombre;
        Button btn_confirmar,btn_autorizar;
        ImageButton btn_detalle;
        CardView cardView;
        ImageView img_categorias;
        ProgressBar progressBar;
        ImageView img_new,img_finalizado,img_visto;


        public NodosViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tv_valnodo = itemView.findViewById(R.id.tv_valnodo);
            tv_nombre = itemView.findViewById(R.id.tv_nombre);
            btn_autorizar = itemView.findViewById(R.id.btn_autorizar);
            btn_confirmar = itemView.findViewById(R.id.btn_confirmar);
            img_categorias = itemView.findViewById(R.id.img_categoria);
            progressBar = itemView.findViewById(R.id.progress_row_cate);
            img_new = itemView.findViewById(R.id.img_new);
            img_visto = itemView.findViewById(R.id.img_visto);
            img_finalizado = itemView.findViewById(R.id.img_finalizado);
            btn_detalle = itemView.findViewById(R.id.btn_detalle);

        }

    }

}