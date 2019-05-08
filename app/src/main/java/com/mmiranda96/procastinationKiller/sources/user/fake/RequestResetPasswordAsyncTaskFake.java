package com.mmiranda96.procastinationKiller.sources.user.fake;

import com.mmiranda96.procastinationKiller.sources.user.RequestResetPasswordAsyncTask;

class RequestResetPasswordAsyncTaskFake extends RequestResetPasswordAsyncTask {

    RequestResetPasswordAsyncTaskFake(Listener listener)   {
        super(listener);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        // TODO: implement
        return null;
    }
}