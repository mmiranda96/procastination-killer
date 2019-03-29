package com.mmiranda96.procastinationKiller.sources.user.fake;

import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;
import com.mmiranda96.procastinationKiller.sources.user.UserSource;

public class UserSourceFake implements UserSource {

    @Override
    public LoginAsyncTask newLoginAsyncTask(LoginAsyncTask.Listener listener) {
        return new LoginAsyncTaskFake(listener);
    }
}
