package com.mmiranda96.procastinationKiller.sources.task.fake;

import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.sources.task.UpdateTaskAsyncTask;

class UpdateTaskAsyncTaskFake extends UpdateTaskAsyncTask {
    UpdateTaskAsyncTaskFake(Listener listener) {
        super(listener);
    }

    @Override
    protected Integer doInBackground(Task... tasks) {
        // TODO: implement
        return null;
    }
}
