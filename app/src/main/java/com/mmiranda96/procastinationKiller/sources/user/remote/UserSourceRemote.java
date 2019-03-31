package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;

public class UserSourceRemote implements UserSource {

    private String server;

    public UserSourceRemote(String server) {
        this.server = server;
    }

    @Override
    public LoginAsyncTask newLoginAsyncTask(LoginAsyncTask.Listener listener) {
        return new LoginAsyncTaskRemote(this.server, listener);
    }
}
