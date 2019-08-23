package lk.imms.management_system.general.commonEnum;

public enum Title {
    MR("Mr. "),
    MRS("Mrs. "),
    MISS("Miss. "),
    MS("Ms. "),
    BABY("Baby. "),
    REV("Rev. "),
    DR("Dr. "),
    DRMRS("Dr(Mrs). "),
    MAST("Master. "),
    PRO("Prof. "),
    SISTER("Sister. ");

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
