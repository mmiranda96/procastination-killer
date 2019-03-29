package com.mmiranda96.procastinationKiller.sources.user;

import com.mmiranda96.procastinationKiller.sources.user.fake.UserSourceFake;
import com.mmiranda96.procastinationKiller.sources.user.remote.UserSourceRemote;

public class UserSourceFactory {
    public static final int FAKE = 0, REMOTE = 1;

    private UserSourceFactory() {
    }

    public static UserSource newSource(int type, String... args) {
        switch (type) {
            case FAKE:
                return new UserSourceFake();
            case REMOTE:
                return new UserSourceRemote(args[0]);
            default:
                throw new IllegalArgumentException("Invalid source type");
        }
    }
}
