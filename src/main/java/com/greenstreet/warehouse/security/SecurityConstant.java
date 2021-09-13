package com.greenstreet.warehouse.security;

public class SecurityConstant {
    private SecurityConstant(){}

    public static final String SECRET_KEY = "FlowerWarehouseKey";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Long TOKEN_EXPIRED_MINUTES = 60L;
    public static final Long REFRESH_TOKEN_EXPIRED_MINUTES = 300L;
}
