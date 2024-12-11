package utils;

public interface Set<E> extends Iterable<E> {
    boolean isEmpty();

    int size();

    void clear();

    void add(E var1);

    void addAll(Set<E> var1);

    void remove(E var1);

    void retainAll(Set<E> var1);

    boolean contains(E var1);

    boolean containsAll(Set<E> var1);

    Object[] toArray();

    E[] toArray(Class<E> var1);

    String toVisualizedString(String var1);
}
