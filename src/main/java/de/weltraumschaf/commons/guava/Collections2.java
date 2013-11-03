package de.weltraumschaf.commons.guava;

import java.util.Collection;

public final class Collections2 {

    private Collections2() {
        super();
    }

    /**
     * Used to avoid http://bugs.sun.com/view_bug.do?bug_id=6558557
     */
    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection<T>) iterable;
    }

}
