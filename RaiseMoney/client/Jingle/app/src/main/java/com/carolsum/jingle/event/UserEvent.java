package com.carolsum.jingle.event;

import com.carolsum.jingle.model.User;

public class UserEvent {
    public final User user;

    public UserEvent(User user) {
        this.user = user;
    }
}
