package com.mmiranda96.procastinationKiller.sources.user.fake;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.user.SignupAsyncTask;

class SignupAsyncTaskFake extends SignupAsyncTask {

    SignupAsyncTaskFake(Listener listener) {
        super(listener);
    }

    @Override
    protected Integer doInBackground(User... users) {
        // TODO: implement
        return null;
    }

}
