package kreandoapp.mpsupervisores.clientes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import kreandoapp.mpsupervisores.ContactosRVActivity;
import kreandoapp.mpsupervisores.Notifications.Token;
import kreandoapp.mpsupervisores.R;
import kreandoapp.mpsupervisores.adapter.AdapterCategoria;
import kreandoapp.mpsupervisores.adapter.AdapterNodos;
import kreandoapp.mpsupervisores.adapter.SliderAdapterExample;
import kreandoapp.mpsupervisores.clientes.ProcesoVenta.DetectandoUbicacionActivity;
import kreandoapp.mpsupervisores.db.database.AppDb;
import kreandoapp.mpsupervisores.detectorInternet;
import kreandoapp.mpsupervisores.loginapp.MainActivity;
import kreandoapp.mpsupervisores.modoadmin.ModoAdmin;
import kreandoapp.mpsupervisores.pojo.Categoria;
import kreandoapp.mpsupervisores.pojo.ModeloNodo;
import kreandoapp.mpsupervisores.pojo.SliderItem;
import kreandoapp.mpsupervisores.pojo.User;
import kreandoapp.mpsupervisores.pojo.modelo_prod;
import kreandoapp.mpsupervisores.pojo.pojo_productos;
import kreandoapp.mpsupervisores.volley.claseSendVolleyFCM;


public class home1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    //checando estado login.----
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseauthlistener;
    ///------fin login
    Animation fromsmall;

    ArrayList<ModeloNodo> NodosArrayList;
    AdapterNodos adapter;
    RecyclerView rv_category;

    ImageButton fab;
    ImageButton btn_modoadmin;
    detectorInternet internet;
    ProgressBar loadrv;
    TextView tv_count;

    TelephonyManager manager;

    FirebaseDatabase database = FirebaseDatabase.getInstance();



    String nombreTxt, direccionTxt, telefonoTxt;
    private FusedLocationProviderClient fusedLocationClient;
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    int version;
    FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String currentUserID = user.getUid();

            // Use currentUserID
        } else {
            gologing();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/neufreu.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setContentView(R.layout.activity_home1);

        //pasatodoadouble();











        //------------->

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        Bundle parametros = this.getIntent().getExtras();
        if (parametros != null) {
            nombreTxt = parametros.getString("nombre");
            telefonoTxt = parametros.getString("telefono");
            direccionTxt = parametros.getString("direccion");
            userunico();
        }else {
            userface();
        }


        //Toast.makeText(this, user.getDisplayName(), Toast.LENGTH_SHORT).show();




        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo")
                .addOnCompleteListener(task -> {

                    if (!task.isSuccessful()) {
                        Toast.makeText(home1.this, "suscrito al tema grupo promo", Toast.LENGTH_SHORT).show();
                    }


                });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        fromsmall = AnimationUtils.loadAnimation(this, R.anim.fromsmall);

        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo");

        loadrv = findViewById(R.id.load_rv);


        datoidUnico();

        //datosobligatorioapp();

        updateToken(FirebaseInstanceId.getInstance().getToken());




        //fin creando user unico


        //actualizar tokem


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FirebaseMessaging.getInstance().subscribeToTopic("grupopromo").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //Toast.makeText(home1.this, "Suscrito al tema", Toast.LENGTH_SHORT).show();
            }
        });


        //startActivity(i);

        //update TOken..


        internet = new detectorInternet(this);



        rv_category = findViewById(R.id.recycler_menu);
        rv_category.setLayoutManager(new GridLayoutManager(this, 1));


        //Mostrar modo admin

        // MOSTRAR RECYCLER VIEW

        NodosArrayList = new ArrayList<>();
        adapter = new AdapterNodos(NodosArrayList, this);
        rv_category.setAdapter(adapter);

        // conexion();


        DatabaseReference myRef4 = database.getReference("Nodos");
        Query query4 = myRef4.orderByChild("id_supervisor").equalTo(user.getUid());
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    loadrv.setVisibility(View.GONE);
                    rv_category.setVisibility(View.VISIBLE);
                    NodosArrayList.removeAll(NodosArrayList);


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        ModeloNodo req = snapshot.getValue(ModeloNodo.class);
                        if(req.getAutorizacion1().equals("no") && req.getNotificacion1().equals("si")){
                            NodosArrayList.add(req);
                        }
                        if(req.getAutorizacion2().equals("no") && req.getNotificacion2().equals("si")){
                            NodosArrayList.add(req);
                        }



                    }

                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(home1.this, "Ups, no encontramos resultados!", Toast.LENGTH_LONG).show();

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        TextView nav_user = hView.findViewById(R.id.tv_nombre_completo);
        assert user != null;
        nav_user.setText(user.getDisplayName());

        TextView tv_mail = hView.findViewById(R.id.tv_mail);
        tv_mail.setText(user.getEmail());

        CircleImageView img_profile = hView.findViewById(R.id.img_profile);
        Picasso.with(this).load(user.getPhotoUrl()).into(img_profile);



        navigationView.setNavigationItemSelectedListener(this);

    }//fin del oncreate!

    private void pasatodoadouble() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference("Productos");

       myRef3.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                   double doble = Double.parseDouble("3.50");
                   modelo_prod req = snapshot.getValue(modelo_prod.class);
                   myRef3.child(req.getProdId()).child("prodPrecio").setValue("3.50");

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    private void userface() {
        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef3 = database2.getReference();
        Query query2 = myRef3.child("Users");
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                   // Toast.makeText(home1.this, user.getEmail(), Toast.LENGTH_SHORT).show();

                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    DatabaseReference myRef3 = database2.getReference();
                    Query query2 = myRef3.child("Users").child(user.getUid());
                    query2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){

                            }else {
                                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                assert user != null;
                                User uu = new User(user.getUid(),user.getDisplayName(),String.valueOf(user.getPhotoUrl()),
                                        "","",1,user.getEmail());
                                DatabaseReference myRef3 = database2.getReference("Users").child(user.getUid());
                                myRef3.setValue(uu);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else{
                    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                    assert user != null;
                    User uu = new User(user.getUid(),user.getDisplayName(),String.valueOf(user.getPhotoUrl()),
                            "","",1,user.getEmail());
                    DatabaseReference myRef3 = database2.getReference("Users").child(user.getUid());
                    myRef3.setValue(uu);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void mostrardialog(String url,String version) {
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(home1.this);
        View view = getLayoutInflater().inflate(R.layout.dialogupdate,null);

        TextView tv_version = view.findViewById(R.id.tv_version);
        Button btn_cancelar = view.findViewById(R.id.btn_cancelar);
        Button btn_actualizar = view.findViewById(R.id.btn_actualizar);
        tv_version.setText(version);

        mbuilder.setCancelable(false);
        mbuilder.setView(view);
        AlertDialog dialog = mbuilder.create();
        dialog.show();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent abrirurl = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
              startActivity(abrirurl);
            }
        });
    }

    private void datoidUnico() {
        String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        DatabaseReference myRef4 = database.getReference("idTelefono").child(m_androidId);
        myRef4.child(m_androidId).child("id").setValue(m_androidId);

    }




    private void datosobligatorioapp() {
        DatabaseReference myRef33 = database.getReference();
        assert user != null;
        Query query23 = myRef33.child("Users").child(user.getUid()).child("mitelefono");
        query23.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String val = dataSnapshot.getValue(String.class);
                if (dataSnapshot.exists()) {
                    assert val != null;
                    if (val.equals("")) {
                        Intent i = new Intent(home1.this, misdatosobligatorios.class);
                        startActivity(i);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void userunico() {

        DatabaseReference myRef3 = database.getReference();
        Query query2 = myRef3.child("Users").child(user.getUid());
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    assert user != null;
                    User uu = new User(user.getUid(),nombreTxt,"default",direccionTxt,telefonoTxt,1,user.getEmail());
                    DatabaseReference myRef3 = database.getReference("Users").child(user.getUid());
                    myRef3.setValue(uu);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nombreTxt)

                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(home1.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                                }
                            });

                }else {

                        String nombre = dataSnapshot.child("username").getValue(String.class);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nombre)

                                .build();

                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        //Toast.makeText(home1.this, "Nombre actualizado", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    @Override
    protected void onResume() {
        super.onResume();
        if(internet.estaConectado()){

            PackageInfo packageInfo;

            try {
                packageInfo = this.getPackageManager().getPackageInfo(getPackageName(),0);
                version = packageInfo.versionCode;


            }catch (Exception e){
                e.printStackTrace();
            }

            remoteConfig.setConfigSettings(new FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(true).build());
            HashMap<String,Object> update = new HashMap<>();
            Task<Void> fetch = remoteConfig.fetch(0);
            fetch.addOnSuccessListener(this,Avoid ->{
                remoteConfig.activateFetched();
                version(version);
            });

        }else{

            Toast.makeText(this, "No existe conexion a internet", Toast.LENGTH_SHORT).show();
        }



        //tv_count.setText(new Database(this).getCountCart());
    }

    private void version(int version) {
        int nueva = (int) remoteConfig.getLong("versioncode");
        String web = remoteConfig.getString("weburl");
        String versionname = remoteConfig.getString("versionname");

        if(nueva > version){
            mostrardialog(web,versionname);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrar_sesion) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            gologing();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mi_direccion) {


            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, misdatos.class);
                    startActivity(i);
                }
            }, 200);

        } /*else if (id == R.id.cart) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, DetectandoUbicacionActivity.class);
                    startActivity(i);
                }
            }, 200);


        } else if (id == R.id.mispedidos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, misPedidos.class);
                    startActivity(i);
                }
            }, 200);
        } else if (id == R.id.mispromos) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, promociones.class);
                    startActivity(i);
                }
            }, 200);

        } else if (id == R.id.contacto)  {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    finish();
                    Intent i = new Intent(home1.this, ContactosRVActivity.class);
                    startActivity(i);
                }
            }, 200);


        }
    else if (id == R.id.preparaciones)  {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
                Intent i = new Intent(home1.this, PreparaCoctelActivity.class);
                startActivity(i);
            }
        }, 200);


    }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    FirebaseUser user2 = FirebaseAuth.getInstance().getCurrentUser();

    private void updateToken(String token){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1 = new Token(token);
        reference.child(user2.getUid()).setValue(token1);
    }

    private void gologing() {
        Intent i = new Intent(home1.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }





}








