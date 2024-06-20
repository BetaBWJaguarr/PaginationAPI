package beta.com.paginationapi.page.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortFilterManager<T> {

    public List<T> sort(List<T> items, Comparator<T> comparator) {
        return items.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<T> filter(List<T> items, Predicate<T> predicate) {
        return items.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public List<T> paginate(List<T> items, int start, int end) {
        if (start < 0 || end < start || end > items.size()) {
            throw new IllegalArgumentException("Invalid index range");
        }
        return items.subList(start, end);
    }

    public List<T> getPage(List<T> items, int pageNumber, int pageSize) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException("Page number must be greater than 0");
        }
        int start = (pageNumber - 1) * pageSize;
        if (start >= items.size()) {
            return Collections.emptyList();
        }
        int end = Math.min(start + pageSize, items.size());
        return items.subList(start, end);
    }

    public long count(List<T> items) {
        return items.size();
    }

    public boolean anyMatch(List<T> items, Predicate<T> predicate) {
        return items.stream().anyMatch(predicate);
    }

    public boolean allMatch(List<T> items, Predicate<T> predicate) {
        return items.stream().allMatch(predicate);
    }

    public boolean noneMatch(List<T> items, Predicate<T> predicate) {
        return items.stream().noneMatch(predicate);
    }
}
