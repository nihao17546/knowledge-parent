package com.test;

/**
 * Created by nihao on 18/3/30.
 */
public class RBTree<T extends Comparable<T>> {

    private RBNode<T> root;// 根节点
    private static final boolean RED = false;
    private static final boolean BLACK =true;

    //内部类：节点类
    public class RBNode<T extends Comparable<T>>{
        boolean color;// 颜色
        T key;// 键值
        RBNode<T> left;
        RBNode<T> right;
        RBNode<T> parent;

        public RBNode(T key, boolean color, RBNode<T> parent, RBNode<T> left, RBNode<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public T getKey() {
            return key;
        }

        public String toString() {
            return "" + key + (this.color == RED? "R" : "B");
        }
    }

    public RBTree() {
        root = null;
    }

    public RBNode<T> parentOf(RBNode<T> node) { //获得父节点
        return node != null? node.parent : null;
    }

    public void setParent(RBNode<T> node, RBNode<T> parent) { //设置父节点
        if(node != null)
            node.parent = parent;
    }

    public boolean colorOf(RBNode<T> node) { //获得节点的颜色
        return node != null? node.color : BLACK;
    }

    public boolean isRed(RBNode<T> node) { //判断节点的颜色
        return (node != null)&&(node.color == RED)? true : false;
    }

    public boolean isBlack(RBNode<T> node) {
        return !isRed(node);
    }

    public void setRed(RBNode<T> node) { //设置节点的颜色
        if(node != null)
            node.color = RED;
    }

    public void setBlack(RBNode<T> node) {
        if(node != null) {
            node.color = BLACK;
        }
    }

    public void setColor(RBNode<T> node, boolean color) {
        if(node != null)
            node.color = color;
    }

    /***************** 前序遍历红黑树 *********************/
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(RBNode<T> tree) {
        if(tree != null) {
            System.out.print(tree.key + " ");
            preOrder(tree.left);
            preOrder(tree.right);
        }
    }

    /***************** 中序遍历红黑树 *********************/
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(RBNode<T> tree) {
        if(tree != null) {
            preOrder(tree.left);
            System.out.print(tree.key + " ");
            preOrder(tree.right);
        }
    }

    /***************** 后序遍历红黑树 *********************/
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(RBNode<T> tree) {
        if(tree != null) {
            preOrder(tree.left);
            preOrder(tree.right);
            System.out.print(tree.key + " ");
        }
    }
}
