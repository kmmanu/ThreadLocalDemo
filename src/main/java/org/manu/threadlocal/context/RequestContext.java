package org.manu.threadlocal.context;

public class RequestContext {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void set(User user) {
        userThreadLocal.set(user);
    }

    public static void unset() {
        userThreadLocal.remove();
    }

    public static User get() {
        return userThreadLocal.get();
    }
}
