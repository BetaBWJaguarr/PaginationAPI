package beta.com.paginationapi.sortmanager.utils;

import beta.com.paginationapi.errorevents.HandleExceptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SortFilterManager<T> {
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

    public List<T> paginate(List<T> items, int start, int end) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "paginate");
            return Collections.emptyList();
        }
        if (start < 0 || end < start || end > items.size()) {
            handleExceptions.handle(new IllegalArgumentException("Invalid index range"), this.getClass().getSimpleName(), "paginate");
            return Collections.emptyList();
        }

        try {
            return items.subList(start, end);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "paginate");
            return Collections.emptyList();
        }
    }

    public List<T> getPage(List<T> items, int pageNumber, int pageSize) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "getPage");
            return Collections.emptyList();
        }
        if (pageNumber <= 0 || pageSize <= 0) {
            handleExceptions.handle(new IllegalArgumentException("Page number and page size must be greater than 0"), this.getClass().getSimpleName(), "getPage");
            return Collections.emptyList();
        }

        try {
            int start = (pageNumber - 1) * pageSize;
            if (start >= items.size()) {
                return Collections.emptyList();
            }
            int end = Math.min(start + pageSize, items.size());
            return items.subList(start, end);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "getPage");
            return Collections.emptyList();
        }
    }

    public long count(List<T> items) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "count");
            return 0;
        }

        try {
            return items.size();
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "count");
            return 0;
        }
    }

    public boolean anyMatch(List<T> items, Predicate<T> predicate) {
        if (items == null || predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicate cannot be null"), this.getClass().getSimpleName(), "anyMatch");
            return false;
        }

        try {
            return items.stream().anyMatch(predicate);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "anyMatch");
            return false;
        }
    }

    public boolean allMatch(List<T> items, Predicate<T> predicate) {
        if (items == null || predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicate cannot be null"), this.getClass().getSimpleName(), "allMatch");
            return false;
        }

        try {
            return items.stream().allMatch(predicate);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "allMatch");
            return false;
        }
    }

    public boolean noneMatch(List<T> items, Predicate<T> predicate) {
        if (items == null || predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Items or predicate cannot be null"), this.getClass().getSimpleName(), "noneMatch");
            return false;
        }

        try {
            return items.stream().noneMatch(predicate);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), "noneMatch");
            return false;
        }
    }
}