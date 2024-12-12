package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RedBlackTree<E extends Comparable<E>> {

    private RedBlackNode<E> root = null;
    public static final String RED1 = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    public static class RedBlackNode<N> {

        protected enum Color {RED, BLACK}
        protected Color color;
        protected N key;
        protected RedBlackNode<N> left;
        protected RedBlackNode<N> right;
        protected RedBlackNode<N> parent;

        protected RedBlackNode() {
        }

        public RedBlackNode(N key) {
            this.key = key;
            this.color = Color.RED;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private RedBlackNode<E> findNode(E element) {
        RedBlackNode<E> current = root;

        while (current != null) {
            int comparison = element.compareTo(current.key);

            if (comparison == 0) {
                return current;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public RedBlackNode<E> getRoot() {
        return root;
    }

    public boolean isRed(RedBlackNode<E> element) {
        return element != null && element.color == RedBlackNode.Color.RED;
    }

    public boolean isBlack(RedBlackNode<E> element) {
        return element == null || element.color == RedBlackNode.Color.BLACK;
    }

    public void Insert(E element) {
        RedBlackNode<E> x = root;
        RedBlackNode<E> parent = null;
        RedBlackNode<E> newNode = new RedBlackNode<>(element);

        while (x != null) {
            parent = x;
            if (newNode.key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }

        newNode.parent = parent;

        if (parent == null) {
            root = newNode;
        } else if (newNode.key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.left = null;
        newNode.right = null;

        Insert_fixup(newNode);
    }


    private void Insert_fixup(RedBlackNode<E> element) {
        while (element.parent != null && element.parent.color == RedBlackNode.Color.RED) {
            if (element.parent == element.parent.parent.left) {
                RedBlackNode<E> node = element.parent.parent.right;

                if (node != null && node.color == RedBlackNode.Color.RED) {
                    element.parent.color = RedBlackNode.Color.BLACK;
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    element = element.parent.parent;
                } else {
                    if (element == element.parent.right) {
                        element = element.parent;
                        LeftRotation(element);
                    }
                    element.parent.color = RedBlackNode.Color.BLACK;
                    if (element.parent.parent != null) {
                        element.parent.parent.color = RedBlackNode.Color.RED;
                        RightRotation(element.parent.parent);
                    }
                }
            } else {
                RedBlackNode<E> node = element.parent.parent.left;

                if (node != null && node.color == RedBlackNode.Color.RED) {
                    element.parent.color = RedBlackNode.Color.BLACK;
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.parent.color = RedBlackNode.Color.RED;
                    element = element.parent.parent;
                } else {
                    if (element == element.parent.left) {
                        element = element.parent;
                        RightRotation(element);
                    }
                    element.parent.color = RedBlackNode.Color.BLACK;
                    if (element.parent.parent != null) {
                        element.parent.parent.color = RedBlackNode.Color.RED;
                        LeftRotation(element.parent.parent);
                    }
                }
            }
        }
        root.color = RedBlackNode.Color.BLACK;
    }

    private void LeftRotation(RedBlackNode<E> element) {
        RedBlackNode<E> node = element.right;
        element.right = node.left;
        if (node.left != null) {
            node.left.parent = element;
        }
        node.parent = element.parent;

        if (element.parent == null) {
            root = node;
        } else if (element == element.parent.left) {
            element.parent.left = node;
        } else {
            element.parent.right = node;
        }
        node.left = element;
        element.parent = node;
    }

    private void RightRotation(RedBlackNode<E> element) {
        RedBlackNode<E> node = element.left;
        element.left = node.right;
        if (node.right != null) {
            node.right.parent = element;
        }
        node.parent = element.parent;

        if (element.parent == null) {
            root = node;
        } else if (element == element.parent.left) {
            element.parent.left = node;
        } else {
            element.parent.right = node;
        }
        node.right = element;
        element.parent = node;
    }

    public void Transplant(RedBlackNode<E> node1, RedBlackNode<E> node2) {
        if (node1.parent == null) {
            root = node2;
        } else if (node1 == node1.parent.left) {
            node1.parent.left = node2;
        } else {
            node1.parent.right = node2;
        }
        if (node2 != null) {
            node2.parent = node1.parent;
        }
    }

    RedBlackNode<E> getMin(RedBlackNode<E> node) {
        return getExtremum(node, false);
    }

    private RedBlackNode<E> getExtremum(RedBlackNode<E> node, boolean findMax) {
        while (node != null) {
            if (!findMax && node.left != null) {
                node = node.left;
            } else if (findMax && node.right != null) {
                node = node.right;
            } else {
                break;
            }
        }
        return node;
    }

    public void Delete(E element) {
        RedBlackNode<E> nodeToDelete = findNode(element);
        if (nodeToDelete == null) {
            return;
        }

        RedBlackNode<E> y = nodeToDelete;
        RedBlackNode.Color originalColor = y.color;
        RedBlackNode<E> x;

        if (nodeToDelete.left == null) {
            x = nodeToDelete.right;
            Transplant(nodeToDelete, nodeToDelete.right);
        } else if (nodeToDelete.right == null) {
            x = nodeToDelete.left;
            Transplant(nodeToDelete, nodeToDelete.left);
        } else {
            y = getMin(nodeToDelete.right);
            originalColor = y.color;
            x = y.right;

            if (y.parent != nodeToDelete) {
                Transplant(y, y.right);
                y.right = nodeToDelete.right;
                y.right.parent = y;
            }

            Transplant(nodeToDelete, y);
            y.left = nodeToDelete.left;
            y.left.parent = y;
            y.color = nodeToDelete.color;
        }

        if (originalColor == RedBlackNode.Color.BLACK) {
            DeleteFixUp(x);
        }
    }

    public void DeleteFixUp(RedBlackNode<E> element) {
        while (element != root && isBlack(element)) {
            if (element == element.parent.left) {
                RedBlackNode<E> node = element.parent.right;
                if (isRed(node)) {
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.color = RedBlackNode.Color.RED;
                    LeftRotation(element.parent);
                    node = element.parent.right;
                }
                if (isBlack(node.left) && isBlack(node.right)) {
                    node.color = RedBlackNode.Color.RED;
                    element = element.parent;
                } else {
                    if (isBlack(node.right)) {
                        if (node.left != null) {
                            node.left.color = RedBlackNode.Color.BLACK;
                        }
                        node.color = RedBlackNode.Color.RED;
                        RightRotation(node);
                        node = element.parent.right;
                    }
                    node.color = element.parent.color;
                    element.parent.color = RedBlackNode.Color.BLACK;
                    if (node.right != null) {
                        node.right.color = RedBlackNode.Color.BLACK;
                    }
                    LeftRotation(element.parent);
                    element = root;
                }
            } else {
                RedBlackNode<E> node = element.parent.left;
                if (isRed(node)) {
                    node.color = RedBlackNode.Color.BLACK;
                    element.parent.color = RedBlackNode.Color.RED;
                    RightRotation(element.parent);
                    node = element.parent.left;
                }
                if (isBlack(node.right) && isBlack(node.left)) {
                    node.color = RedBlackNode.Color.RED;
                    element = element.parent;
                } else {
                    if (isBlack(node.left)) {
                        if (node.right != null) {
                            node.right.color = RedBlackNode.Color.BLACK;
                        }
                        node.color = RedBlackNode.Color.RED;
                        LeftRotation(node);
                        node = element.parent.left;
                    }
                    node.color = element.parent.color;
                    element.parent.color = RedBlackNode.Color.BLACK;
                    if (node.left != null) {
                        node.left.color = RedBlackNode.Color.BLACK;
                    }
                    RightRotation(element.parent);
                    element = root;
                }
            }
        }
        if (element != null) {
            element.color = RedBlackNode.Color.BLACK;
        }
    }

    public static class BTreePrinter {

        public static <T extends Comparable<?>> void printNode(RedBlackNode<T> root) {
            int maxLevel = BTreePrinter.maxLevel(root);
            printNodeInternal(Collections.singletonList(root), 1, maxLevel);
        }

        private static <T extends Comparable<?>> void printNodeInternal(List<RedBlackNode<T>> nodes, int level, int maxLevel) {
            if (nodes.isEmpty() || isAllElementsNull(nodes)) {
                return;
            }
            int floor = maxLevel - level;
            int edgeLines = (int) Math.pow(2, Math.max(floor - 1, 0));
            int firstSpaces = (int) Math.pow(2, floor) - 1;
            int betweenSpaces = (int) Math.pow(2, floor + 1) - 1;

            printWhitespaces(firstSpaces);

            List<RedBlackNode<T>> newNodes = new ArrayList<>();
            for (RedBlackNode<T> node : nodes) {
                if (node != null) {
                    if (Objects.equals(node.color, RedBlackNode.Color.RED)) {
                        System.out.print(RED1 + node.key + RESET);
                    } else {
                        System.out.print(node.key);
                    }
                    newNodes.add(node.left);
                    newNodes.add(node.right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }
                printWhitespaces(betweenSpaces);
            }
            System.out.println();

            for (int i = 1; i <= edgeLines; i++) {
                for (RedBlackNode<T> node : nodes) {
                    printWhitespaces(firstSpaces - i);
                    if (node == null) {
                        printWhitespaces(edgeLines + edgeLines + i + 1);
                        continue;
                    }
                    if (node.left != null) {
                        System.out.print("/");
                    } else {
                        printWhitespaces(1);
                    }
                    printWhitespaces(i + i - 1);
                    if (node.right != null) {
                        System.out.print("\\");
                    } else {
                        printWhitespaces(1);
                    }
                    printWhitespaces(edgeLines + edgeLines - i);
                }
                System.out.println();
            }
            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        private static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++) {
                System.out.print(" ");
            }
        }

        private static <T extends Comparable<?>> int maxLevel(RedBlackNode<T> node) {
            if (node == null) {
                return 0;
            }
            return Math.max(maxLevel(node.left), maxLevel(node.right)) + 1;
        }

        private static <T> boolean isAllElementsNull(List<T> list) {
            for (Object object : list) {
                if (object != null) {
                    return false;
                }
            }
            return true;
        }
    }
}
