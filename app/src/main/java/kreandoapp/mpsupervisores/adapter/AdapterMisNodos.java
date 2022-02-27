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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import kreandoapp.mpsupervisores.DetalleNodoActivity;
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
            public void onClick(View v) {

                String mail = nodo.getNombreUsuario();

                String[] parts = mail.split("@");
                String part1 = parts[0]; // 123

                Intent i = new Intent(context, DetalleNodoActivity.class);

                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("id_nodo", nodo.getIdnodo());
                myEdit.putString("destino","misnodos");
                myEdit.putString("nombre_nodo", nodo.getNombreNodo());
                myEdit.commit();

                i.putExtra("nombre_nodo",nodo.getNombreNodo());
                i.putExtra("id_usuario",nodo.getId_usuario());
                i.putExtra("nombre",part1);
                i.putExtra("id_nodo",nodo.getIdnodo());
                v.getContext().startActivity(i);

            }
        });

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference();
        Query query4 = myRef33.child("Cajas").orderByChild("id_nodo").equalTo(nodo.getIdnodo());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    holder.btn_detalle.setVisibility(View.VISIBLE);
                }else{
                    holder.btn_detalle.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        Button btn_finalizado,btn_enproceso;
        ImageButton btn_detalle;
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
            btn_detalle = itemView.findViewById(R.id.btn_detalle_misnodos);
        }

    }

}