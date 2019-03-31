package com.mmiranda96.procastinationKiller.sources.task;

import android.os.AsyncTask;

import com.mmiranda96.procastinationKiller.models.Task;

public abstract class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Integer> {

    public static final int SUCCESS = 0, ERROR = 1;
    protected Listener listener;

    protected UpdateTaskAsyncTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer result) {
        this.listener.updateTaskAsyncTaskDone(result);
    }

    public interface Listener {
        void updateTaskAsyncTaskDone(Integer result);
    }
}
