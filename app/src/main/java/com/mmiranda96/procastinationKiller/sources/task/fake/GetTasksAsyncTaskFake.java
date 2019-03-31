package com.mmiranda96.procastinationKiller.sources.task.fake;

import com.mmiranda96.procastinationKiller.models.Task;
import com.mmiranda96.procastinationKiller.sources.task.GetTasksAsyncTask;

import java.util.ArrayList;

class GetTasksAsyncTaskFake extends GetTasksAsyncTask {
    GetTasksAsyncTaskFake(Listener listener) {
        super(listener);
    }

    @Override
    protected ArrayList<Task> doInBackground(Void... voids) {
        // TODO: implement
        return null;
    }
}
