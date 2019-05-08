package com.mmiranda96.procastinationKiller.sources.user;

import com.mmiranda96.procastinationKiller.models.User;

public interface UserSource {
    LoginAsyncTask newLoginAsyncTask(LoginAsyncTask.Listener listener);

    SignupAsyncTask newSignupAsyncTask(SignupAsyncTask.Listener listener);

    UpdateFirebaseTokenAsyncTask newUpdateFirebaseTokenAsyncTask(User user);

    RequestResetPasswordAsyncTask newRequestResetPasswordAsyncTask(RequestResetPasswordAsyncTask.Listener listener);

    ResetPasswordAsyncTask newResetPasswordAsyncTask(ResetPasswordAsyncTask.Listener listener, String token);
}
