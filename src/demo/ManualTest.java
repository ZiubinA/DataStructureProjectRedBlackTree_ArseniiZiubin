package demo;

import utils.RedBlackTree;

import java.util.Locale;



public class ManualTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Locale.setDefault(Locale.US); // Unify number formats
        executeTest();

    }

    public static void executeTest(){
        RedBlackTree<Integer> tree = new RedBlackTree<>();

        tree.Insert(10);
        tree.Insert(20);
        tree.Insert(30);
        tree.Insert(12);
        tree.Insert(11);
        tree.Insert(26);
        tree.Insert(34);


        RedBlackTree.BTreePrinter.printNode(tree.getRoot());


        tree.Delete(12);
        tree.Delete(30);
        tree.Delete(20);

        RedBlackTree.BTreePrinter.printNode(tree.getRoot());
    }
}
