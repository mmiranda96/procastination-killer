package com.mmiranda96.procastinationKiller.sources.task.fake;

import com.mmiranda96.procastinationKiller.sources.task.AddUserToTaskAsyncTask;

public class AddUserToTaskAsyncTaskFake extends AddUserToTaskAsyncTask {
    protected AddUserToTaskAsyncTaskFake(Listener listener) {
        super(listener);
    }

    @Override
    protected Integer doInBackground(String... strings) {
        return null;
    }
}
