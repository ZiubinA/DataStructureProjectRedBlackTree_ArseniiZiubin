package utils;

import java.util.Iterator;

public interface SortedSet<E> extends Set<E> {
    Set<E> headSet(E var1);

    Set<E> subSet(E var1, E var2);

    Set<E> tailSet(E var1);

    Iterator<E> descendingIterator();
}
