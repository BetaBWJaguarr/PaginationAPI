package beta.com.paginationapi.sortmanager;

import beta.com.paginationapi.errorevents.HandleExceptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortFilter<T> {
    private final HandleExceptions handleExceptions = new HandleExceptions();


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
}
