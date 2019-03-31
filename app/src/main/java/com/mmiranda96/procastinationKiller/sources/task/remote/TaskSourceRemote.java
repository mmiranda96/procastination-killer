package com.mmiranda96.procastinationKiller.sources.task.remote;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.CreateTaskAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.GetTasksAsyncTask;
import com.mmiranda96.procastinationKiller.sources.task.TaskSource;
import com.mmiranda96.procastinationKiller.sources.task.UpdateTaskAsyncTask;

public class TaskSourceRemote implements TaskSource {

    private String server;
    private User user;

    public TaskSourceRemote(String server, User user) {
        this.server = server;
        this.user = user;
    }

    @Override
    public CreateTaskAsyncTask newCreateTaskAsyncTask(CreateTaskAsyncTask.Listener listener) {
        return new CreateTaskAsyncTaskRemote(this.server, this.user, listener);
    }

    @Override
    public UpdateTaskAsyncTask newUpdateTaskAsyncTask(UpdateTaskAsyncTask.Listener listener) {
        return new UpdateTaskAsyncTaskRemote(this.server, this.user, listener);
    }

    @Override
    public GetTasksAsyncTask newGetTasksAsyncTask(GetTasksAsyncTask.Listener listener) {
        return new GetTasksAsyncTaskRemote(this.server, this.user, listener);
    }
}
