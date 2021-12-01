package antonio.david.sportivity.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Actividad.class}, version = 1, exportSchema = false)
public abstract class ActividadRoomDatabase extends RoomDatabase {

    public abstract ActividadDAO actividadDAO();

    private static ActividadRoomDatabase INSTANCE;

    static ActividadRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ActividadRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ActividadRoomDatabase.class, "word_database")
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
        Actividad actividad1 = new Actividad("actividad1", "corre", "piernas");

        Actividad [] actividades = {actividad1};

        PopulateDbAsync(ActividadRoomDatabase db) {
            mDao = db.actividadDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= actividades.length - 1; i++) {
                    mDao.insert(actividades[i]);
                }
            }
            return null;
        }
    }



}
