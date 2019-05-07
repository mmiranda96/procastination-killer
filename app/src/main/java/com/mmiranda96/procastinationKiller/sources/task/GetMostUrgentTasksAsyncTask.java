package com.mmiranda96.procastinationKiller.sources.task;

import android.os.AsyncTask;

import com.mmiranda96.procastinationKiller.models.Task;

import java.util.ArrayList;

public abstract class GetMostUrgentTasksAsyncTask extends AsyncTask<Void, Void, ArrayList<Task>> {
    protected GetMostUrgentTasksAsyncTask.Listener listener;

    protected GetMostUrgentTasksAsyncTask(GetMostUrgentTasksAsyncTask.Listener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(ArrayList<Task> result) {
        this.listener.getMostUrgentTasksAsyncTaskDone(result);
    }

    public interface Listener {
        void getMostUrgentTasksAsyncTaskDone(ArrayList<Task> result);
    }


}
