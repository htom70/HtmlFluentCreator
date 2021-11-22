package hu.tom.element;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HtmlElementProperty {

    private ElementType elementType;
    private String attributeKey ="";
    private String attributeValue="";
    private String content="";

    public HtmlElementProperty(ElementType elementType) {
        this.elementType = elementType;
    }
}
