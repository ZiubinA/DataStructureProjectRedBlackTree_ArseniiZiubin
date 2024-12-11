package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RedBlackTree<E extends Comparable<E>>{

    private RedBlackNode<E> root = null;
    public static final String RED1 = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

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

    public void Transplant(RedBlackNode<E> node1, RedBlackNode<E> node2){
        if(node1.parent == null){
            root = node2;
        }
        else if(node2 == node2.parent.left){
            node1.parent.left = node2;
        }
        else node1.parent.right = node2;

        node2.parent = node1.parent;
    }


    RedBlackNode<E> getMin(RedBlackNode<E> node) {
        return get(node, false);
    }

    private RedBlackNode<E> get(RedBlackNode<E> node, boolean findMax) {
        RedBlackNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    public void Delete(RedBlackNode<E> element){
        RedBlackNode<E> node = element;
        Enum<RedBlackNode.Color> nodeOriginalColor = node.color;
        RedBlackNode<E> x = null;
        if(element.left == null){
            x = element.right;
            Transplant(element, element.right);
        }
        else if(element.right == null){
            x = element.left;
            Transplant(element, element.left);
        }
        else {
            node = getMin(element.right);
            nodeOriginalColor = node.color;
            x = node.right;
            if(node != element.right){
                Transplant(node, node.right);
                node.right = element.right;
                node.right.parent = node;
            }
            else x.parent = node;

            Transplant(element, node);
            node.left = element.left;
            node.left.parent = node;
            node.color = element.color;
        }

        if(nodeOriginalColor == RedBlackNode.Color.BLACK){ DeleteFixUp(x); }
    }

    public void DeleteFixUp(RedBlackNode<E> element){

    }


    public static class BTreePrinter {

        public static <T extends Comparable<?>> void printNode(RedBlackNode<T> root) {
            int maxLevel = BTreePrinter.maxLevel(root);

            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<RedBlackNode<T>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
                return;

            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

            BTreePrinter.printWhitespaces(firstSpaces);

            List<RedBlackNode<T>> newNodes = new ArrayList<RedBlackNode<T>>();
            for (RedBlackNode<T> node : nodes) {
                if (node != null) {
                    if (node.key == "N") {
                        System.out.print("N");
                    }
                    else {
                        if (Objects.equals(node.color, RedBlackNode.Color.RED))
                        {
                            System.out.print(RED1 + node.key + RESET);
                        }
                        else {
                            System.out.print(node.key);
                        }
                    }
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println("");

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (nodes.get(j).left != null)
                        System.out.print("/");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (nodes.get(j).right != null)
                        System.out.print("\\");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println("");
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        private static <T extends Comparable<?>> int maxLevel(RedBlackNode<T> node) {
            if (node == null)
                return 0;

            return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
        }

        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }
            return true;
        }
    }



}
