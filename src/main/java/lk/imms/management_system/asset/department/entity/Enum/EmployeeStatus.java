package lk.imms.management_system.asset.department.entity.Enum;

public enum EmployeeStatus {
    WORKING("Working"),
    LEAVE("Leave"),
    SUSPENDED("Suspended"),
    BLOCK("Block"),
    RESIGNED("Resigned"),
    RETIRED("Retired");

    private final String employeeStatus;

    EmployeeStatus(String employeeStatus) {

        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }
}
