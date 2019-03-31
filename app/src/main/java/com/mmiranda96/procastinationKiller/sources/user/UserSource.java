package com.mmiranda96.procastinationKiller.sources.user;

public interface UserSource {
    LoginAsyncTask newLoginAsyncTask(LoginAsyncTask.Listener listener);

    SignupAsyncTask newSignupAsyncTask(SignupAsyncTask.Listener listener);
}
