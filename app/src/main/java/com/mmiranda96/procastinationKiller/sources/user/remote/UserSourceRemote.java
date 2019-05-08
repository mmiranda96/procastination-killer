package com.mmiranda96.procastinationKiller.sources.user.remote;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.RequestResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.ResetPasswordAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UpdateFirebaseTokenAsyncTask;
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

    @Override
    public UpdateFirebaseTokenAsyncTask newUpdateFirebaseTokenAsyncTask(User user) {
        return new UpdateFirebaseTokenAsyncTaskRemote(this.server, user);
    }

    @Override
    public RequestResetPasswordAsyncTask newRequestResetPasswordAsyncTask(RequestResetPasswordAsyncTask.Listener listener) {
        return new RequestResetPasswordAsyncTaskRemote(this.server, listener);
    }

    @Override
    public ResetPasswordAsyncTask newResetPasswordAsyncTask(ResetPasswordAsyncTask.Listener listener, String token) {
        return new ResetPasswordAsyncTaskRemote(this.server, listener);
    }
}
