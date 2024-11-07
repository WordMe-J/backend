package kr.wordme.common;

public enum CustomErrorMessage {
    INVALID_PARAMETER("Parameter %s cannot be null or empty"),
    DUPLICATE_VALUE("Value %s is duplicated");

    private final String template;

    CustomErrorMessage(String template) {
        this.template = template;
    }

    public String format(Object... args) {
        return String.format(template, args);
    }
}
