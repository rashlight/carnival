package com.rashlight.carnival.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "BannedRole", code = BannedRole.CODE)
public interface BannedRole {
    String CODE = "banned-role";
}