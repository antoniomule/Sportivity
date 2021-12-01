package antonio.david.sportivity.Database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ActividadRepository {
    private ActividadDAO mActividadDAO;
    private LiveData<List<Actividad>> mAllActivities;


    ActividadRepository(Application application){
        ActividadRoomDatabase db = ActividadRoomDatabase.getDatabase(application);
        mActividadDAO = db.actividadDAO();
        mAllActivities = mActividadDAO.getAlphabetizedWords();
    }

    LiveData<List<Actividad>> getmAllActivities() {
        return mAllActivities;
    }

    public void insert (Actividad actividad){
        new insertAsyncTask(mActividadDAO).execute(actividad);
    }

    private static class insertAsyncTask extends AsyncTask<Actividad, Void, Void> {

        private ActividadDAO mAsyncTaskDao;

        insertAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Actividad... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteAll()  {
        new deleteAllWordsAsyncTask(mActividadDAO).execute();
    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private ActividadDAO mAsyncTaskDao;

        deleteAllWordsAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public void deleteWord(Actividad actividad) {
        new deleteWordAsyncTask(mActividadDAO).execute(actividad);

    }

    private static class deleteWordAsyncTask extends AsyncTask<Actividad, Void, Void> {
        private ActividadDAO mAsyncTaskDao;

        deleteWordAsyncTask(ActividadDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Actividad... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
