package utils;

import demo.Car;

import java.util.Comparator;

/**
 * The AVL-tree implementation of a ordered set.
 *
 * @param <E> Type of the set element. Must implement the Comparable<E> interface, or
 * an object that implements Comparator<E> interface must be passed through the class constructor
 * @author darius.matulis@ktu.lt
 * @task Review and understand the provided methods.
 */
public class AvlSet<E extends Comparable<E>> extends BstSet<E> implements SortedSet<E> {

    public AvlSet() {
    }

    public AvlSet(Comparator<? super E> c) {
        super(c);
    }

    /**
     * Adds a new element to the set.
     *
     * @param element
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }
        root = addRecursive(element, (AVLNode<E>) root);

    }

    /**
     * A recursive method used as helper method for adding a new element to the set
     *
     * @param element
     * @param node
     * @return
     */
    private AVLNode<E> addRecursive(E element, AVLNode<E> node) {
        if (node == null) {
            size++;
            return new AVLNode<>(element);
        }
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.setLeft(addRecursive(element, node.getLeft()));
            if ((height(node.getLeft()) - height(node.getRight())) == 2) {
                int cmp2 = c.compare(element, node.getLeft().element);
                node = cmp2 < 0 ? rightRotation(node) : doubleRightRotation(node);
            }
        } else if (cmp > 0) {
            node.setRight(addRecursive(element, node.getRight()));
            if ((height(node.getRight()) - height(node.getLeft())) == 2) {
                int cmp2 = c.compare(node.getRight().element, element);
                node = cmp2 < 0 ? leftRotation(node) : doubleLeftRotation(node);
            }
        }
        node.height = Math.max(height(node.getLeft()), height(node.getRight())) + 1;
        return node;
    }

    /**
     * Removes an element from the set.
     *
     * @param element
     */
    @Override
    public void remove(E element) {
        if(element == null){
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }
        root = removeRecursive(element, (AVLNode<E>)root);

    }

    private AVLNode<E> removeRecursive(E element, AVLNode<E> n) {
        if (n == null) {
            return null;
        }

        int cmp = c.compare(element, n.element);


        if (cmp < 0) {
            n.setLeft(removeRecursive(element, n.getLeft()));

            if (height(n.getRight()) - height(n.getLeft()) == 2) {
                AVLNode<E> rightChild = n.getRight();
                n = height(rightChild.getRight()) >= height(rightChild.getLeft()) ? leftRotation(n) :
                        doubleLeftRotation(n);
            }

        } else if (cmp > 0) {
            n.setRight(removeRecursive(element, n.getRight()));


            if (height(n.getLeft()) - height(n.getRight()) == 2) {
                AVLNode<E> leftChild = n.getLeft();
                n = height(leftChild.getLeft()) >= height(leftChild.getRight()) ? rightRotation(n) :
                        doubleRightRotation(n);
            }

        } else {

            if (n.getLeft() != null && n.getRight() != null) {
                AVLNode<E> minNode = (AVLNode<E>) getMin(n.getRight());
                n.element = minNode.element;
                n.setRight(removeRecursive(minNode.element, n.getRight()));

                if (height(n.getLeft()) - height(n.getRight()) == 2) {
                    AVLNode<E> leftChild = n.getLeft();
                    n = height(leftChild.getLeft()) >= height(leftChild.getRight()) ? rightRotation(n)
                            : doubleRightRotation(n);
                }

            } else {
                n = (n.getLeft() != null) ? n.getLeft() : n.getRight();
                size--;
            }
        }

        if (n != null) {
            n.height = Math.max(height(n.getLeft()), height(n.getRight())) + 1;
        }
        return n;
    }

    // AVL tree rotation methods

    //           n2
    //          /                n1
    //         n1      ==>      /  \
    //        /                n3  n2
    //       n3

    private AVLNode<E> rightRotation(AVLNode<E> n2) {
        AVLNode<E> n1 = n2.getLeft();
        n2.setLeft(n1.getRight());
        n1.setRight(n2);
        n2.height = Math.max(height(n2.getLeft()), height(n2.getRight())) + 1;
        n1.height = Math.max(height(n1.getLeft()), height(n2)) + 1;
        return n1;
    }

    private AVLNode<E> leftRotation(AVLNode<E> n1) {
        AVLNode<E> n2 = n1.getRight();
        n1.setRight(n2.getLeft());
        n2.setLeft(n1);
        n1.height = Math.max(height(n1.getLeft()), height(n1.getRight())) + 1;
        n2.height = Math.max(height(n2.getRight()), height(n1)) + 1;
        return n2;
    }

    public static Car getElement(SortedSet<Car> carSet, int index) {
        int i = 0;
        if(index <= carSet.size()){
            for (Car car : carSet) {
                if(i == index) {
                    return car;
                }
                i++;
            }
        }
        return null;
    }

    //            n3               n3
    //           /                /                n2
    //          n1      ==>      n2      ==>      /  \
    //           \              /                n1  n3
    //            n2           n1
    //
    private AVLNode<E> doubleRightRotation(AVLNode<E> n3) {
        n3.left = leftRotation(n3.getLeft());
        return rightRotation(n3);
    }

    private AVLNode<E> doubleLeftRotation(AVLNode<E> n1) {
        n1.right = rightRotation(n1.getRight());
        return leftRotation(n1);
    }

    private int height(AVLNode<E> n) {
        return (n == null) ? -1 : n.height;
    }

    /**
     * Inner class of tree node
     *
     * @param <N> node element data type
     */
    protected class AVLNode<N> extends BstNode<N> {

        protected int height;

        protected AVLNode(N element) {
            super(element);
            this.height = 0;
        }

        protected void setLeft(AVLNode<N> left) {
            this.left = left;
        }

        protected AVLNode<N> getLeft() {
            return (AVLNode<N>) left;
        }

        protected void setRight(AVLNode<N> right) {
            this.right = right;
        }

        protected AVLNode<N> getRight() {
            return (AVLNode<N>) right;
        }
    }
}
