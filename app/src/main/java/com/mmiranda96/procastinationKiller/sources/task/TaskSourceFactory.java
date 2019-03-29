package com.mmiranda96.procastinationKiller.sources.task;

import com.mmiranda96.procastinationKiller.models.User;
import com.mmiranda96.procastinationKiller.sources.task.fake.TaskSourceFake;
import com.mmiranda96.procastinationKiller.sources.task.remote.TaskSourceRemote;

public class TaskSourceFactory {
    public static final int FAKE = 0, REMOTE = 1;

    private TaskSourceFactory() {
    }

    public static TaskSource newSource(int type, User user, String... args) {
        switch (type) {
            case FAKE:
                return new TaskSourceFake();
            case REMOTE:
                return new TaskSourceRemote(args[0], user);
            default:
                throw new IllegalArgumentException("Invalid source type");
        }
    }
}
