package hu.tom.element;

import lombok.Getter;

@Getter
public enum ElementType {
    A("a"),
    TITLE("title"),
    H1("h1"),
    P("p"),
    TABLE("table"),
    TD("td"),
    TR("tr"),
    HTML("html"),
    HEAD("head"),
    BODY("body");

    private String showName;

    ElementType(String showName) {
        this.showName = showName;
    }
}

