package de.weltraumschaf.commons.guava;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class Lists {

    private Lists() {
        super();
    }

    /**
     * Creates a <i>mutable</i>, empty {@code ArrayList} instance.
     *
     * <p>
     * <b>Note:</b> if mutability is not required, use {@link
     * ImmutableList#of()} instead.
     *
     * @return a new, empty {@code ArrayList}
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<E>();
    }

    /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link ImmutableList#copyOf(Iterator)} instead.
   *
   * @param elements the elements that the list should contain, in order
   * @return a new {@code ArrayList} containing those elements
   */
  public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements) {
    if (elements == null) {
      throw new NullPointerException();
    }

    // Let ArrayList's sizing logic work, if possible
    return (elements instanceof Collection)
        ? new ArrayList<E>(Collections2.cast(elements))
        : newArrayList(elements.iterator());
  }

  /**
   * Creates a <i>mutable</i> {@code ArrayList} instance containing the given
   * elements.
   *
   * <p><b>Note:</b> if mutability is not required and the elements are
   * non-null, use {@link ImmutableList#copyOf(Iterator)} instead.
   *
   * @param elements the elements that the list should contain, in order
   * @return a new {@code ArrayList} containing those elements
   */
  public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
    ArrayList<E> list = newArrayList();
    Iterators.addAll(list, elements);
    return list;
  }


}
