package kreandoapp.mpsupervisores.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import kreandoapp.mpsupervisores.Constantes.Cons;
import kreandoapp.mpsupervisores.db.dao.OrdenesDao;
import kreandoapp.mpsupervisores.db.entity.Ordenes;

@Database(entities = {Ordenes.class},version = 1)
public abstract class AppDb extends RoomDatabase {
    private static AppDb INSTANCE;

    public  abstract OrdenesDao ordenesDao();

    public static AppDb getAppDb (Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDb.class, Cons.NAME_DATABASE)
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static  void destroyInstance(){
        INSTANCE = null;
    }

}
