package dev.esz.blog.user.dao;

public enum Authority {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private final String value;

    Authority(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
