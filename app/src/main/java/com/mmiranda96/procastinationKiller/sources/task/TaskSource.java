package com.mmiranda96.procastinationKiller.sources.task;

import com.mmiranda96.procastinationKiller.sources.task.remote.AddUserToTaskAsyncTaskRemote;

public interface TaskSource {
    CreateTaskAsyncTask newCreateTaskAsyncTask(CreateTaskAsyncTask.Listener listener);
    UpdateTaskAsyncTask newUpdateTaskAsyncTask(UpdateTaskAsyncTask.Listener listener);
    GetTasksAsyncTask newGetTasksAsyncTask(GetTasksAsyncTask.Listener listener);
    AddUserToTaskAsyncTask newAddUserToTaskAsyncTask(AddUserToTaskAsyncTask.Listener listener);
}
