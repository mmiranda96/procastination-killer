package com.mmiranda96.procastinationKiller.sources.user.fake;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.LoginAsyncTask;

class LoginAsyncTaskFake extends LoginAsyncTask {

    LoginAsyncTaskFake(Listener listener) {
        super(listener);
    }

    @Override
    protected Integer doInBackground(User... users) {
        // TODO: implement
        return null;
    }

}
