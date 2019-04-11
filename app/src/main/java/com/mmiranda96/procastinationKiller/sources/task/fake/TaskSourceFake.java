package com.mmiranda96.procastinationKiller.sources.task.fake;

import com.mmiranda96.procastinationKiller.sources.task.AddUserToTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.GetTasksAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.UpdateTaskAsyncTask;


public class TaskSourceFake implements TaskSource {

    @Override
    public CreateTaskAsyncTask newCreateTaskAsyncTask(CreateTaskAsyncTask.Listener listener) {
        return new CreateTaskAsyncTaskFake(listener);
    }

    @Override
    public UpdateTaskAsyncTask newUpdateTaskAsyncTask(UpdateTaskAsyncTask.Listener listener) {
        return new UpdateTaskAsyncTaskFake(listener);
    }

    @Override
    public GetTasksAsyncTask newGetTasksAsyncTask(GetTasksAsyncTask.Listener listener) {
        return new GetTasksAsyncTaskFake(listener);
    }

    @Override
    public AddUserToTaskAsyncTask newAddUserToTaskAsyncTask(AddUserToTaskAsyncTask.Listener listener) {
        return new AddUserToTaskAsyncTaskFake(listener);
    }
}
