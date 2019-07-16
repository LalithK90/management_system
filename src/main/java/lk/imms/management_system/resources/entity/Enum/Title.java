package lk.imms.management_system.resources.entity.Enum;

public enum Title {
    MR("Mr."),
    MRS("Mrs."),
    MISS("Miss."),
    MS("Ms."),
    BABY("Baby."),
    REV("Rev."),
    DR("Dr."),
    DRMRS("Dr(Mrs)."),
    MAST("Master."),
   BabyOf("Baby Of."),
    PASTER("Paster."),
    PRO("Prof."),
    SISTER("Sister."),
    ANIMAL("Animal."),
    NO("OPR");

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
