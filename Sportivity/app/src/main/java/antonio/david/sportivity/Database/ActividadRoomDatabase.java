package antonio.david.sportivity.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//2
@Database(entities = {Actividad.class}, version = 1, exportSchema = false)
public abstract class ActividadRoomDatabase extends RoomDatabase {

    public abstract ActividadDAO actividadDAO();

    private static ActividadRoomDatabase INSTANCE;

    static ActividadRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActividadRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActividadRoomDatabase.class, "activity_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ActividadDAO mDao;
        Actividad actividad1 = new Actividad("Correr", "Piernas", 30, 0, 1);
        Actividad actividad2 = new Actividad("Sentadillas", "Piernas", 0, 10, 1);
        Actividad actividad3 = new Actividad("Abdominales", "Abdomen", 0, 15, 1);
        Actividad actividad4 = new Actividad("Pesas", "Brazos", 0, 20, 1);
        Actividad actividad5 = new Actividad("Plancha", "Abdomen", 1, 0, 1);
        Actividad actividad6 = new Actividad("Largos piscina", "Abdomen", 0, 5, 1);
        Actividad actividad7 = new Actividad("Sprint", "Piernas", 0, 5, 1);
        Actividad [] actividades = {actividad1, actividad2, actividad3, actividad4, actividad5, actividad6, actividad7};

        PopulateDbAsync(ActividadRoomDatabase db) {
            mDao = db.actividadDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            if (mDao.getAnyActivity().length < 1) {
                for (int i = 0; i <= actividades.length - 1; i++) {
                    mDao.insert(actividades[i]);
                }
            }
            return null;
        }
    }
}
