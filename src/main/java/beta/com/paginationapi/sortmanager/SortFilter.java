package beta.com.paginationapi.sortmanager;

import beta.com.paginationapi.errorevents.HandleExceptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Utility class for sorting and filtering lists of objects using comparators and predicates.
 * <p>
 * This class provides methods to perform sorting, filtering, or combined sorting and filtering operations on lists of objects.
 * It handles exceptions during these operations and logs them using an instance of HandleExceptions.
 * <p>
 * Sorting Methods:
 * - {@link #sort(List, Comparator)}: Sorts the list using a single comparator.
 * - {@link #sort(List, Comparator...)}: Sorts the list using multiple comparators combined sequentially.
 * <p>
 * Filtering Methods:
 * - {@link #filter(List, Predicate)}: Filters the list based on a single predicate.
 * - {@link #filter(List, Predicate...)}: Filters the list based on multiple predicates combined using logical AND.
 * <p>
 * Combined Sorting and Filtering Methods:
 * - {@link #filterAndSort(List, Predicate, Comparator)}: Filters the list based on a predicate and then sorts it using a comparator.
 * - {@link #filterAndSort(List, Predicate...)}: Filters the list based on multiple predicates combined using logical AND.
 * <p>
 * The class uses Java Streams and Collectors to process lists efficiently.
 * It returns an empty list and logs exceptions using HandleExceptions if input parameters are null or if exceptions occur during processing.
 *
 * @param <T> the type of objects in the list
 * @see Comparator Functional interface for comparing objects
 * @see Predicate Functional interface for defining a condition on objects
 * @see HandleExceptions Utility class for handling and logging exceptions
 */

public class SortFilter<T> {
    private final HandleExceptions handleExceptions;

    public SortFilter() {
        this.handleExceptions = new HandleExceptions();
    }

    public List<T> sort(List<T> items, Comparator<T> comparator) {
        if (items == null || comparator == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or comparator cannot be null"), this.getClass().getSimpleName(), "sort");
            return Collections.emptyList();
        }

        try {
            return items.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "sort");
            return Collections.emptyList();
        }
    }

    @SafeVarargs
    public final List<T> sort(List<T> items, Comparator<T>... comparators) {
        if (items == null || comparators == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or comparators cannot be null"), this.getClass().getSimpleName(), "sort");
            return Collections.emptyList();
        }

        try {
            Comparator<T> combinedComparator = (item1, item2) -> {
                for (Comparator<T> comparator : comparators) {
                    int result = comparator.compare(item1, item2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            };
            return items.stream()
                    .sorted(combinedComparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "sort");
            return Collections.emptyList();
        }
    }

    public List<T> filter(List<T> items, Predicate<T> predicate) {
        if (items == null || predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicate cannot be null"), this.getClass().getSimpleName(), "filter");
            return Collections.emptyList();
        }

        try {
            return items.stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "filter");
            return Collections.emptyList();
        }
    }

    @SafeVarargs
    public final List<T> filter(List<T> items, Predicate<T>... predicates) {
        if (items == null || predicates == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicates cannot be null"), this.getClass().getSimpleName(), "filter");
            return Collections.emptyList();
        }

        try {
            Predicate<T> combinedPredicate = item -> {
                for (Predicate<T> predicate : predicates) {
                    if (!predicate.test(item)) {
                        return false;
                    }
                }
                return true;
            };
            return items.stream()
                    .filter(combinedPredicate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "filter");
            return Collections.emptyList();
        }
    }

    public List<T> filterAndSort(List<T> items, Predicate<T> predicate, Comparator<T> comparator) {
        if (items == null || predicate == null || comparator == null) {
            handleExceptions.handle(new IllegalArgumentException("Items, predicate, or comparator cannot be null"), this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }

        try {
            return items.stream()
                    .filter(predicate)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }
    }

    @SafeVarargs
    public final List<T> filterAndSort(List<T> items, Predicate<T>... predicates) {
        if (items == null || predicates == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicates cannot be null"), this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }

        try {
            Predicate<T> combinedPredicate = item -> {
                for (Predicate<T> pred : predicates) {
                    if (!pred.test(item)) {
                        return false;
                    }
                }
                return true;
            };

            return items.stream()
                    .filter(combinedPredicate)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }
    }

    @SafeVarargs
    public final List<T> filterAndSort(List<T> items, Predicate<T> predicate, Comparator<T>... comparators) {
        if (items == null || predicate == null || comparators == null) {
            handleExceptions.handle(new IllegalArgumentException("Items, predicate, or comparators cannot be null"), this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }

        try {
            Predicate<T> combinedPredicate = predicate;

            Comparator<T> combinedComparator = (item1, item2) -> {
                for (Comparator<T> comparator : comparators) {
                    int result = comparator.compare(item1, item2);
                    if (result != 0) {
                        return result;
                    }
                }
                return 0;
            };

            return items.stream()
                    .filter(combinedPredicate)
                    .sorted(combinedComparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "filterAndSort");
            return Collections.emptyList();
        }
    }

    public List<T> reverseSort(List<T> items, Comparator<T> comparator) {
        if (items == null || comparator == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or comparator cannot be null"), this.getClass().getSimpleName(), "reverseSort");
            return Collections.emptyList();
        }

        try {
            return items.stream()
                    .sorted(comparator.reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "reverseSort");
            return Collections.emptyList();
        }
    }
    public List<T> skip(List<T> items, int start) {
        if (items == null || start < 0) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null and start must be non-negative"), this.getClass().getSimpleName(), "skip");
            return Collections.emptyList();
        }

        return items.stream()
                .skip(start)
                .collect(Collectors.toList());
    }

}
