package com.mmiranda96.procastinationKiller.sources.task;

import android.os.AsyncTask;

public abstract class AddUserToTaskAsyncTask extends AsyncTask<String, Void, Integer> {
    public static final int SUCCESS = 0, ERROR = 1;
    protected Listener listener;

    protected AddUserToTaskAsyncTask(Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Integer result) {
        this.listener.addUserToTaskAsyncTaskDone(result);
    }

    public interface Listener {
        void addUserToTaskAsyncTaskDone(Integer result);
    }
}
