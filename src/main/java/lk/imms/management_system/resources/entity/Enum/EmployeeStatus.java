package lk.imms.management_system.resources.entity.Enum;

public enum EmployeeStatus {
    WORKING("Working"),
    LEAVE("Leave"),
    SUSPENED("Suspended"),
    BLOCK("Block"),
    RESINED("Resined"),
    RETIRED("Retired");

    private final String employeeStatus;

    EmployeeStatus(String employeeStatus) {

        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeStatus() {
        return employeeStatus;
    }
}
