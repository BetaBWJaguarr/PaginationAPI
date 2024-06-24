package beta.com.paginationapi.sortmanager;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortFilter<T> {

    public List<T> sort(List<T> items, Comparator<T> comparator) {
        return items.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public final List<T> sort(List<T> items, Comparator<T>... comparators) {
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
    }

    public List<T> filter(List<T> items, Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public final List<T> filter(List<T> items, Predicate<T>... predicates) {
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
    }

    public List<T> filterAndSort(List<T> items, Predicate<T> predicate, Comparator<T> comparator) {
        return items.stream()
                .filter(predicate)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public final List<T> filterAndSort(List<T> items, Predicate<T>... predicates) {
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
    }

    @SafeVarargs
    public final List<T> filterAndSort(List<T> items, Predicate<T> predicate, Comparator<T>... comparators) {
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
    }
}
