package lk.imms.management_system.asset.employee.entity.Enum;

import lk.imms.management_system.asset.employee.entity.Employee;

public enum Designation extends Employee {
    MANAGER("Manager"),
    CASHIER("Cashier"),
    DRIVER("Instructor"),
    CLERK("Clerk"),
    STUDENT("Student");

    private final String designation;

    Designation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }
}
