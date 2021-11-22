package hu.tom;

import hu.tom.element.ElementType;
import hu.tom.element.HtmlElementProperty;
import hu.tom.tree.TreeNode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Getter
public class HtmlFluentCreator {

    private TreeNode<HtmlElementProperty> root;
    private Stack<TreeNode<HtmlElementProperty>> treeNodeStack = new Stack();
    private Map<String, String> parameters = new HashMap<>();
    private String key;
    private String folder;

    public HtmlFluentCreator html() {
        HtmlElementProperty htmlElementProperty = new HtmlElementProperty(ElementType.HTML);
        root = new TreeNode<>(htmlElementProperty);
        treeNodeStack.push(root);
        return this;
    }

    public HtmlFluentCreator head() {
        HtmlElementProperty headElement = new HtmlElementProperty(ElementType.HEAD);
        TreeNode<HtmlElementProperty> headNode = new TreeNode(headElement);
        treeNodeStack.push(headNode);
        return this;
    }

    public HtmlFluentCreator title() {
        HtmlElementProperty titleElement = new HtmlElementProperty(ElementType.TITLE);
        TreeNode<HtmlElementProperty> titleNode = new TreeNode(titleElement);
        treeNodeStack.push(titleNode);
        return this;
    }

    public HtmlFluentCreator body() {
        HtmlElementProperty bodyElement = new HtmlElementProperty(ElementType.BODY);
        TreeNode<HtmlElementProperty> bodyNode = new TreeNode(bodyElement);
        treeNodeStack.push(bodyNode);
        return this;
    }

    public HtmlFluentCreator h1() {
        HtmlElementProperty h1Element = new HtmlElementProperty(ElementType.H1);
        TreeNode<HtmlElementProperty> h1Node = new TreeNode(h1Element);
        treeNodeStack.push(h1Node);
        return this;
    }

    public HtmlFluentCreator a() {
        HtmlElementProperty aElement = new HtmlElementProperty(ElementType.A);
        TreeNode<HtmlElementProperty> aNode = new TreeNode(aElement);
        treeNodeStack.push(aNode);
        return this;
    }

    public HtmlFluentCreator p() {
        HtmlElementProperty pElement = new HtmlElementProperty(ElementType.P);
        TreeNode<HtmlElementProperty> pNode = new TreeNode(pElement);
        treeNodeStack.push(pNode);
        return this;
    }

    public HtmlFluentCreator table() {
        HtmlElementProperty tableElement = new HtmlElementProperty(ElementType.TABLE);
        TreeNode<HtmlElementProperty> tableNode = new TreeNode(tableElement);
        treeNodeStack.push(tableNode);
        return this;
    }

    public HtmlFluentCreator tr() {
        HtmlElementProperty trElement = new HtmlElementProperty(ElementType.TR);
        TreeNode<HtmlElementProperty> trNode = new TreeNode(trElement);
        treeNodeStack.push(trNode);
        return this;
    }

    public HtmlFluentCreator td() {
        HtmlElementProperty tdElement = new HtmlElementProperty(ElementType.TD);
        TreeNode<HtmlElementProperty> tdNode = new TreeNode(tdElement);
        treeNodeStack.push(tdNode);
        return this;
    }

    public HtmlFluentCreator content(String text) {
        TreeNode<HtmlElementProperty> currentNode = treeNodeStack.peek();
        HtmlElementProperty currentElement=currentNode.getData();
        currentElement.setContent(currentElement.getContent() + text);
        return this;
    }

    public HtmlFluentCreator contentParam(String param) {
        TreeNode<HtmlElementProperty> currentNode = treeNodeStack.peek();
        HtmlElementProperty currentElement=currentNode.getData();
        String content = currentElement.getContent();
        String modifiedContent = createModifiedContent(content, param);
        currentElement.setContent(modifiedContent);
        return this;
    }

    private String createModifiedContent(String content, String param) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(content);
        stringBuilder.append(createParamString(param));
        return stringBuilder.toString();
    }

    private String createParamString(String value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("$");
        stringBuilder.append("{");
        stringBuilder.append(value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public HtmlFluentCreator attributeKey(String attributeKey) {
        TreeNode<HtmlElementProperty> currentNode = treeNodeStack.peek();
        HtmlElementProperty currentElement=currentNode.getData();
        currentElement.setAttributeKey(attributeKey);
        return this;
    }

    public HtmlFluentCreator attributeValue(String attributeValue) {
        TreeNode<HtmlElementProperty> currentNode = treeNodeStack.peek();
        HtmlElementProperty currentElement=currentNode.getData();
        currentElement.setAttributeValue(attributeValue);
        return this;
    }

    public HtmlFluentCreator attributeValueParam(String attributeValueParam) {
        TreeNode<HtmlElementProperty> currentNode = treeNodeStack.peek();
        HtmlElementProperty currentElement=currentNode.getData();
        currentElement.setAttributeValue(createParamString(attributeValueParam));
        return this;
    }

    public HtmlFluentCreator end() {
        TreeNode<HtmlElementProperty> actualNode = treeNodeStack.pop();
        if (!actualNode.equals(root)) {
            TreeNode<HtmlElementProperty> previousNode = treeNodeStack.peek();
            previousNode.addChildNode(actualNode);
        }
        return this;
    }

    public HtmlFluentCreator keyParam(String key) {
        this.key = key;
        return this;
    }

    public HtmlFluentCreator valueParam(String value) {
        parameters.put(this.key, value);
        return this;
    }

    public HtmlFluentCreator folder(String folder) {
        this.folder = folder;
        return this;
    }

    public void create() {
        TemplateHandler templateHandler = new TemplateHandler(root,parameters,folder);
        templateHandler.process();
    }




}
