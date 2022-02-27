package kreandoapp.mpsupervisores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kreandoapp.mpsupervisores.adapter.AdapterMisNodos;
import kreandoapp.mpsupervisores.adapter.AdapterNodos;
import kreandoapp.mpsupervisores.clientes.home1;
import kreandoapp.mpsupervisores.pojo.ModeloNodo;

public class MisnodosActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    ArrayList<ModeloNodo> NodosArrayList;
    AdapterMisNodos adapter;
    RecyclerView rv_misnodos;

    LinearLayout load_linear;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    private LinearLayoutManager mLayoutManager;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, home1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misnodos);

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Mis nodos");

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

        load_linear = findViewById(R.id.load_linear);

        rv_misnodos = findViewById(R.id.rv_misnodos);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);


        rv_misnodos.setLayoutManager(mLayoutManager);

        // MOSTRAR RECYCLER VIEW

        NodosArrayList = new ArrayList<>();
        adapter = new AdapterMisNodos(NodosArrayList, this);
        rv_misnodos.setAdapter(adapter);


        DatabaseReference myRef4 = database.getReference("Nodos");
        Query query4 = myRef4.orderByChild("id_supervisor").equalTo(user.getUid());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    load_linear.setVisibility(View.GONE);
                    rv_misnodos.setVisibility(View.VISIBLE);
                    NodosArrayList.removeAll(NodosArrayList);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ModeloNodo req = snapshot.getValue(ModeloNodo.class);

                        NodosArrayList.add(req);

                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MisnodosActivity.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }//fin del oncreate!
}