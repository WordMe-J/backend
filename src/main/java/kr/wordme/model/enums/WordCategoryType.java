package kr.wordme.model.enums;

public enum WordCategoryType {
    BUSINESS("BUSINESS"),
    TRAVELING("TRAVELING"),
    TOEIC("TOEIC");

    private final String categoryName;

    // 생성자
    WordCategoryType(String categoryName) {
        this.categoryName = categoryName;
    }

    // Getter 메서드
    public String getTypeName() {
        return categoryName;
    }
}