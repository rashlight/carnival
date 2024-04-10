package com.rashlight.carnival.security;

import com.rashlight.carnival.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "UserRole", code = UserRole.CODE)
public interface UserRole {
    String CODE = "user-role";

    @EntityAttributePolicy(entityClass = CrashMulResult.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = CrashMulResult.class, actions = EntityPolicyAction.ALL)
    void crashMulResult();

    @EntityAttributePolicy(entityClass = FighterResult.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FighterResult.class, actions = EntityPolicyAction.ALL)
    void fighterResult();

    @EntityAttributePolicy(entityClass = CrashMulState.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = CrashMulState.class, actions = EntityPolicyAction.ALL)
    void crashMulState();

    @EntityAttributePolicy(entityClass = FighterState.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FighterState.class, actions = EntityPolicyAction.ALL)
    void fighterState();

    @EntityAttributePolicy(entityClass = GuessNumResult.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = GuessNumResult.class, actions = EntityPolicyAction.ALL)
    void guessNumResult();

    @EntityAttributePolicy(entityClass = GuessNumState.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = GuessNumState.class, actions = EntityPolicyAction.ALL)
    void guessNumState();

    @EntityAttributePolicy(entityClass = Session.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Session.class, actions = EntityPolicyAction.ALL)
    void session();

    @EntityAttributePolicy(entityClass = State.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = State.class, actions = EntityPolicyAction.ALL)
    void state();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();

    @SpecificPolicy(resources = "*")
    void specific();

    @MenuPolicy(menuIds = {"DashboardView", "Session.list", "GuessNumView", "CrashMulView", "FighterView"})
    @ViewPolicy(viewIds = {"DashboardView", "Session.list", "GuessNumView", "CrashMulView", "FighterView", "ShutdownView"})
    void screens();

    @EntityAttributePolicy(entityClass = ShutdownResult.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ShutdownResult.class, actions = EntityPolicyAction.ALL)
    void shutdownResult();

    @EntityAttributePolicy(entityClass = ShutdownState.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ShutdownState.class, actions = EntityPolicyAction.ALL)
    void shutdownState();
}