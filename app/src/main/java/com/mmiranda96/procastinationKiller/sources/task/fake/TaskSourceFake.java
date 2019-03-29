package com.mmiranda96.procastinationKiller.sources.task.fake;

import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.GetTasksAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;


public class TaskSourceFake implements TaskSource {

    @Override
    public CreateTaskAsyncTask newCreateTaskAsyncTask(CreateTaskAsyncTask.Listener listener) {
        return new CreateTaskAsyncTaskFake(listener);
    }

    @Override
    public GetTasksAsyncTask newGetTasksAsyncTask(GetTasksAsyncTask.Listener listener) {
        return new GetTasksAsyncTaskFake(listener);
    }
}
