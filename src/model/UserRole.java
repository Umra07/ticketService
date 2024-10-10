package model;

import enums.Role;

public abstract class UserRole extends BaseModel {
    Role role;

    public Role getRole() {
        return this.role;
    }

    public void printRole() {
        System.out.println("My role is " + this.getRole());
    }
}
