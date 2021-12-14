package antonio.david.sportivity.Database;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncTasks<Params, Progres, Result> {
    private final ExecutorService executors;
    private Result result;
    public AsyncTasks() {
        this.executors = Executors.newSingleThreadExecutor();
    }

    private void startBackground(Params... params) {
        onPreExecute();
        executors.execute(new Runnable() {
            @Override
            public void run() {
                result= doInBackground(params);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        onPostExecute(result);
                    }
                });
            }
        });
    }

    public void execute(Params... params) {
        startBackground(params);
    }

    public void shutdown() {
        executors.shutdown();
    }

    public boolean isShutdown() {
        return executors.isShutdown();
    }

    public abstract void onPreExecute();

    public abstract Result doInBackground(Params ... params);

    public abstract void onPostExecute(Result result);
}