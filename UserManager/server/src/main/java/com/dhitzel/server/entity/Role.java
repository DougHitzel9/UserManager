package com.dhitzel.server.entity;

public class Role {

    private long roleId;

    private String role;

    public Role(long roleId, String role) {
        this.roleId = roleId;
        this.role = role;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (roleId ^ (roleId >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (roleId != other.roleId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Role [roleId=" + roleId + ", role=" + role + "]";
    }
}
