package pl.czarczeslaw.simplecalendar.model;

public enum Priority {
    NONE("None"),
    LOW("Low"),
    MID("Medium"),
    HIGH("High");

    private final String name;

    Priority(String nameEnum) {
        this.name = nameEnum;
    }
    public String getName(){
        return name;
    }
}
