package model;

import java.util.*;

public class UsersOnline {
    private static Set<User> usersOnline = new HashSet<>();

    public UsersOnline() {
    }

    public static Set<User> getUsersOnline() {
        return usersOnline;
    }
}
