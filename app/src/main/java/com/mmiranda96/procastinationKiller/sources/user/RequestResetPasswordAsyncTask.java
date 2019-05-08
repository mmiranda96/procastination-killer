package com.mmiranda96.procastinationKiller.sources.user;

import android.os.AsyncTask;

public abstract class RequestResetPasswordAsyncTask extends AsyncTask<String, Void, Integer> {
    public static final int SUCCESS = 0, FAILURE = 1, ERROR = 2;
    protected Listener listener;

    protected RequestResetPasswordAsyncTask(Listener listener) { this.listener = listener; }

    @Override
    protected void onPostExecute(Integer result) {
        this.listener.requestResetPasswordAsyncTaskDone(result);
    }

    public interface Listener {
        void requestResetPasswordAsyncTaskDone(Integer result);
    }
}