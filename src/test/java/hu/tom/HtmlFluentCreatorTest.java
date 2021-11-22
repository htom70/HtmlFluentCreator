package hu.tom;

import hu.tom.element.ElementType;
import hu.tom.element.HtmlElementProperty;
import hu.tom.tree.TreeNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HtmlFluentCreatorTest {

    @Test
    public void test() {
        HtmlFluentCreator htmlFluentCreator = new HtmlFluentCreator();
        htmlFluentCreator
                .html()
                    .head()
                        .title().contentParam("name")
                        .content(" - Teszt Feladat")
                        .end()
                    .end()
                    .body()
                        .h1().content("Teszt Feladat").end()
                        .p()
                            .a().content("Megoldás").attributeKey("href").attributeValueParam("repository")
                            .end()
                        .end()
                        .p().content("A feladat elkészítőjének adatai").end()
                        .table().attributeKey("border").attributeValue("1px solid black")
                            .tr()
                                .td().content("NÉV").end()
                                .td().contentParam("name").end()
                            .end()
                            .tr()
                                .td().content("Elérhetőség").end()
                                .td().contentParam("email").end()
                            .end()
                        .end()
                    .end()
                .end()
                .keyParam("name").valueParam("Tom")
                .keyParam("repository").valueParam("git")
                .keyParam("email").valueParam("hering.tamas70@gmail.com")
                .folder("c:/template")
        .create();

        TreeNode<HtmlElementProperty> treeNode = htmlFluentCreator.getRoot();
        assertEquals(treeNode.getData().getElementType(),ElementType.HTML);
        assertTrue(treeNode.isRoot());
        assertEquals(treeNode.getChildren().size(),2);
    }
}
