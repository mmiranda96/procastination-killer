package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;
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

    @Override
    public SignupAsyncTask newSignupAsyncTask(SignupAsyncTask.Listener listener) {
        return new SignupAsyncTaskRemote(this.server, listener);
    }
}
