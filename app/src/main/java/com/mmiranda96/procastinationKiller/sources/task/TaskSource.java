package com.mmiranda96.procastinationKiller.sources.task;

public interface TaskSource {
    CreateTaskAsyncTask newCreateTaskAsyncTask(CreateTaskAsyncTask.Listener listener);

    GetTasksAsyncTask newGetTasksAsyncTask(GetTasksAsyncTask.Listener listener);
}
