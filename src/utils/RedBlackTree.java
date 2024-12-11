package utils;

public class RedBlackTree<E extends Comparable<E>>{

    private RedBlackNode<E> root = null;
    public static class RedBlackNode<N>{

        protected enum Color { RED, BLACK }
        protected Color color;
        protected N key;
        protected RedBlackNode<N> left;
        protected RedBlackNode<N> right;
        protected RedBlackNode<N> parent;

        protected RedBlackNode(){}

        protected RedBlackNode(N key) {
            this.key = key;
            this.color = Color.RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    public boolean isEmpty() {
        return root == null;
    }

    public boolean isRed(RedBlackNode<E> element){
        return element != null && element.color == RedBlackNode.Color.RED;
    }

    public boolean isBlack(RedBlackNode<E> element){
        return element == null || element.color == RedBlackNode.Color.BLACK;
    }

    public void Insert(RedBlackNode<E> element){
        RedBlackNode<E> x = root;
        RedBlackNode<E> node = null;
        while(x != null){
            node = x;
            int cmp = element.key.compareTo(x.key);
            if(cmp < 0){
                x = x.left;
            }
            else x = x.right;
        }
        element.parent = node;
        if(node == null){
            root = element;
        } else if(element.key.compareTo(node.key) < 0) node.left = element;
        else node.right = element;
        element.left = null;
        element.right = null;
        element.color = RedBlackNode.Color.RED;
        Insert_fixup(element);

    }

    private void Insert_fixup(RedBlackNode<E> element) {
        RedBlackNode<E> node = null;
        while(element.parent.color == RedBlackNode.Color.RED){
            if(element.parent == element.parent.parent.left){
                node = element.parent.parent.right;
                if(node.color == RedBlackNode.Color.RED){
                    element.parent.color = RedBlackNode.Color.BLACK;
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    element = element.parent.parent;
                }
                else{
                    if(element ==  element.parent.right){
                        element = element.parent;
                        LeftRotation(element);
                    }
                    element.parent.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    RightRotation(element);
                }
            }
            else{
                node = element.parent.left;
                if(node.color == RedBlackNode.Color.RED){
                    element.parent.color = RedBlackNode.Color.BLACK;
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    element = element.parent.parent;
                }
                else{
                    if(element ==  element.parent.left){
                        element = element.parent;
                        RightRotation(element);
                    }
                    element.parent.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    LeftRotation(element);
                }
            }
        }
        root.color = RedBlackNode.Color.BLACK;
    }

    private void LeftRotation(RedBlackNode<E> element){
        RedBlackNode<E> node = element.right;
        element.right = node.left;
        if(node.left != null) node.left.parent = element;
        node.parent = element.parent;

        if(element.parent == null) root = node;
        else if(element == element.parent.left) element.parent.left = node;
        else element.parent.right = node;
        node.left = null;
        element.parent = node;
    }

    private void RightRotation(RedBlackNode<E> element){
        RedBlackNode<E> node = element.left;
        element.left = node.right;
        if(node.right != null) node.right.parent = element;
        node.parent = element.parent;

        if(element.parent == null) root = node;
        else if(element == element.parent.right) element.parent.right = node;
        else element.parent.left = node;
        node.right = null;
        element.parent = node;
    }



    public boolean Remove(RedBlackNode<E> node){

        return false;
    }



}
