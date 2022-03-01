package kreandoapp.mpsupervisores;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpsupervisores.adapter.AdapterCajas;
import kreandoapp.mpsupervisores.adapter.AdapterNodos;
import kreandoapp.mpsupervisores.clientes.home1;
import kreandoapp.mpsupervisores.pojo.ModeloCaja;
import kreandoapp.mpsupervisores.pojo.ModeloNodo;

public class DetalleNodoActivity extends AppCompatActivity {

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    ArrayList<ModeloCaja> cajasArrayList;
    AdapterCajas adapter;
    RecyclerView rv_cajas;
    TextView tv_nombre_tecnico;

    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_nodo);

        final String nombre_nodo = getIntent().getExtras().getString("nombre_nodo");
        final String id_nodo = getIntent().getExtras().getString("id_nodo");
        final String nombre_user = getIntent().getExtras().getString("nombre");

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Detalle nodo: "+nombre_nodo);

        tv_nombre_tecnico = findViewById(R.id.tv_nombre_tecnico);
        tv_nombre_tecnico.setText(nombre_user);

        botonvolver = findViewById(R.id.btn_volverAtras);
        botonvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), home1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        cargarRv();
    }

    private void cargarRv() {
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        rv_cajas =  findViewById(R.id.rv_cajas);
        rv_cajas.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        cajasArrayList = new ArrayList<>();
        adapter = new AdapterCajas(cajasArrayList,this);
        rv_cajas.setAdapter(adapter);

        cargarCajas();
    }

    private void cargarCajas() {
        final String id_nodo = getIntent().getExtras().getString("id_nodo");

        FirebaseDatabase database22 = FirebaseDatabase.getInstance();
        DatabaseReference myRef33 = database22.getReference();
        Query query4 = myRef33.child("Cajas").orderByChild("id_nodo").equalTo(id_nodo);

        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    cajasArrayList.removeAll(cajasArrayList);
                    rv_cajas.setVisibility(View.VISIBLE);



                    for (DataSnapshot snapshot :  dataSnapshot.getChildren()) {

                        ModeloCaja prod = snapshot.getValue(ModeloCaja.class);

                        cajasArrayList.add(prod);





                    }



                    adapter.notifyDataSetChanged();


                }else {

                    Toast.makeText(getApplicationContext(), "No hay cajas.", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}