package utils;

public interface ParsableSortedSet<E> extends SortedSet<E> {
    void add(String var1);

    void load(String var1);

    Object clone() throws CloneNotSupportedException;
}