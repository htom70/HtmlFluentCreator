package hu.tom.tree;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TreeNode<T> {

    public T data;
    public TreeNode<T> parent;
    public List<TreeNode<T>> children;

    public boolean isRoot() {
        return parent == null;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }

    public TreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<T>>();
    }

//    public TreeNode<T> addChild(T child) {
//        TreeNode<T> childNode = new TreeNode<T>(child);
//        childNode.parent = this;
//        this.children.add(childNode);
//        return childNode;
//    }

    public void addChildNode(TreeNode<T> childNode) {
        childNode.parent = this;
        this.children.add(childNode);
    }


}
