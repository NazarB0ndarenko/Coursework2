package lt.viko.bondarenko.enums;

public enum ProjectSortBy {
    TITLE("title"), CREATEDAT("createdAt");

    private final String fieldName;

    ProjectSortBy(String fieldName) {
        this.fieldName = fieldName;
    }

    public String fieldName() {
        return fieldName;
    }
}
