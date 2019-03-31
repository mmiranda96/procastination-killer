package com.mmiranda96.procastinationKiller.sources.user;

public interface UserSource {
    LoginAsyncTask newLoginAsyncTask(LoginAsyncTask.Listener listener);
}
