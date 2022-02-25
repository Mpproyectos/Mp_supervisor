package kreandoapp.mpsupervisores.clientes.ProcesoVenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kreandoapp.mpsupervisores.R;
import kreandoapp.mpsupervisores.clientes.home1;
import kreandoapp.mpsupervisores.clientes.pedidoenviadoActivity;
import kreandoapp.mpsupervisores.volley.claseSendVolleyFCM;

public class AutorizarUsuario extends AppCompatActivity {


    TextView tv_texto;
    Button btn_autorizar, btn_cancelar;

    MediaPlayer sound;

    ImageButton botonvolver,btn_editar;
    TextView tv_titulo_toolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorizar_usuario);

        sound = MediaPlayer.create(this,R.raw.arpeggio);



        tv_texto = findViewById(R.id.tv_texto);

        tv_titulo_toolbar = findViewById(R.id.tv_titulo_toolbar);
        tv_titulo_toolbar.setText("Autorizar creacion de nodo");

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

        final String nombre_usuario = getIntent().getExtras().getString("nombre");
        final String nombre_nodo = getIntent().getExtras().getString("nombre_nodo");
        final String id_nodo = getIntent().getExtras().getString("id_nodo");
        final String id_usuario = getIntent().getExtras().getString("id_usuario");

        tv_texto.setText("¿Seguro quieres autorizar a :" + nombre_usuario + " , con la creación del nodo: " + nombre_nodo );

        btn_autorizar = findViewById(R.id.btn_autorizar);
        btn_cancelar = findViewById(R.id.btn_cancelar);

        btn_autorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id_usuario = getIntent().getExtras().getString("id_usuario");
                final String nombre_usuario = getIntent().getExtras().getString("nombre");
                final String nombre_nodo = getIntent().getExtras().getString("nombre_nodo");
                final String id_nodo = getIntent().getExtras().getString("id_nodo");

                notificarusuario(id_usuario, nombre_usuario, nombre_nodo, id_nodo);


               //ANimacion ok -- nodo Creacion--


            }
        });

    }//fin del oncreate!

    private void notificarusuario(String iduser, String nombre_user, String nombre_nodo,String id_nodo) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tokens").child(iduser).child("token");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String token = dataSnapshot.getValue(String.class);
                if (dataSnapshot.exists()) {
                    claseSendVolleyFCM clase = new claseSendVolleyFCM();
                    clase.volleyfcm_sinfoto("Hola  " + nombre_user, " Se autorizo la creación nodo " + nombre_nodo, token, "modoadmin");

                    Toast.makeText(getApplicationContext(), nombre_nodo +" Autorizado", Toast.LENGTH_SHORT).show();



                    FirebaseDatabase database3 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef5 = database3.getReference("Nodos").child(id_nodo);
                    myRef5.child("autorizacion1").setValue("ok");

                    Intent i = new Intent(AutorizarUsuario.this, pedidoenviadoActivity.class);
                    startActivity(i);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}