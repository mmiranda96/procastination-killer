package com.mmiranda96.procastinationKiller.sources.user;

import android.os.AsyncTask;

import com.mmiranda96.procastinationKiller.models.User;

public abstract class ResetPasswordAsyncTask extends AsyncTask<String, Void, Integer> {

    public static final int SUCCESS = 0, FAILURE = 1, ERROR = 2;
    protected Listener listener;

    protected ResetPasswordAsyncTask(Listener listener) { this.listener = listener; }

    @Override
    protected void onPostExecute(Integer result) { this.listener.resetPasswordAsyncTaskDone(result); }

    public interface Listener {
        void resetPasswordAsyncTaskDone(Integer result);
    }
}