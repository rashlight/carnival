package com.rashlight.carnival.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "BannedRole", code = BannedRole.CODE)
public interface BannedRole {
    String CODE = "banned-role";

    @ViewPolicy(viewIds = "LoginView")
    void screens();
}