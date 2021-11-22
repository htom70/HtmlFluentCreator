package hu.tom;

import freemarker.template.*;
import hu.tom.element.ElementType;
import hu.tom.element.HtmlElementProperty;
import hu.tom.tree.TreeNode;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static freemarker.template.TemplateExceptionHandler.*;

public class TemplateHandler {

    private static final String DOCTYPE = "<!DOCTYPE html>";

    private TreeNode<HtmlElementProperty> root;
    private Map<String, String> parameters;
    private String folder;
    private Document document;

    public TemplateHandler(TreeNode<HtmlElementProperty> root, Map<String, String> parameters, String folder) {
        this.root = root;
        this.parameters = parameters;
        this.folder = folder;
    }

    public void process() {
        List<TreeNode<HtmlElementProperty>> childrenProperties = root.getChildren();
        TreeNode<HtmlElementProperty> headNode = childrenProperties.get(0);
        TreeNode<HtmlElementProperty> bodyNode = childrenProperties.get(1);
        Element htmlElement = new Element(ElementType.HTML.getShowName());
        Element emptyHeadElement = new Element(ElementType.HEAD.getShowName());
        Element emptyBodyElement = new Element(ElementType.BODY.getShowName());
        Element filledHeadElement = buildHtmlPart(emptyHeadElement, headNode);
        htmlElement.appendChild(filledHeadElement);
        Element filledBodyElement = buildHtmlPart(emptyBodyElement, bodyNode);
        htmlElement.appendChild(filledBodyElement);
        document = Jsoup.parse(DOCTYPE + htmlElement);
        System.out.println("TEMPLATE:");
        System.out.println(document);
        System.out.println("FILLED HTML FILE:");
        File file = new File(folder + "/template.ftlh");
        try {
            FileUtils.writeStringToFile(file, document.outerHtml(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillTemplate(folder);
        file.delete();
    }

    private void fillTemplate(String folder) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        try {
            cfg.setDirectoryForTemplateLoading(new File(folder));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        try {
            Template template = cfg.getTemplate("template.ftlh");
            Writer out = new OutputStreamWriter(System.out);
            template.process(parameters, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private Element buildHtmlPart(Element parentElement, TreeNode<HtmlElementProperty> treeNode) {
        List<TreeNode<HtmlElementProperty>> innerNodes = treeNode.getChildren();
        for (TreeNode<HtmlElementProperty> innerTreeNode : innerNodes) {
            HtmlElementProperty property = innerTreeNode.getData();
            Element currentElement;
            if (!property.getAttributeKey().isEmpty() && !property.getAttributeValue().isEmpty()) {
                currentElement = createElement(property.getElementType().getShowName(), property.getContent(), "", property.getAttributeKey(), property.getAttributeValue());
            } else if (!property.getContent().isEmpty()) {
                currentElement = createElement(property.getElementType().getShowName(), property.getContent());
            } else {
                currentElement = createElement(property.getElementType().getShowName());
            }
            if (innerTreeNode.isLeaf()) {
                parentElement.appendChild(currentElement);
            } else {
                currentElement.appendChild(buildHtmlPart(currentElement, innerTreeNode));
            }
            parentElement.appendChild(currentElement);
        }
        return parentElement;

    }

    public Element createElement(String elementTypeString, String content, String uri, String attributeKey, String attributeValue) {
        Element element = new Element(Tag.valueOf(elementTypeString), "")
                .text(content)
                .attr(attributeKey, attributeValue);
        return element;
    }

    public Element createElement(String elementTypeString, String content) {
        Element element = new Element(elementTypeString)
                .text(content);
        return element;
    }

    public Element createElement(String elementTypeString) {
        Element element = new Element(elementTypeString);
        return element;
    }


}
