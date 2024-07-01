package beta.com.paginationapi.sortmanager.utils;

import beta.com.paginationapi.errorevents.HandleExceptions;
import beta.com.paginationapi.sortmanager.Helper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The SearchFunction class is a listener that handles search functionality within a pagination system.
 *
 * It listens for inventory click events and player chat events to facilitate a search operation.
 * The search can be performed based on item name or lore within the items managed by an ItemManagerService.
 * The results of the search are navigated using a PaginationService.
 *
 * The class maintains the state of the search operation, including the player in search mode, the current search type, and the manager ID.
 */

public class SortFilterManager<T> {
    private final HandleExceptions handleExceptions;
    private final Helper<T> helper;

    public SortFilterManager() {
        this.handleExceptions = new HandleExceptions();
        this.helper = new Helper<>(handleExceptions);
    }


    public List<T> sort(List<T> items, Comparator<T> comparator) {
        return helper.sort(items, comparator);
    }

    public List<T> filter(List<T> items, Predicate<T> predicate) {
        return helper.filter(items, predicate);
    }

    public List<T> paginate(List<T> items, int start, int end) {
        if (!helper.validateList(items, "paginate")) {
            return Collections.emptyList();
        }

        if (start < 0 || end < start || end > items.size()) {
            handleExceptions.handle(new IllegalArgumentException("Invalid index range"), this.getClass().getSimpleName(), "paginate");
            return Collections.emptyList();
        }
        return helper.handleOperation(
                list -> list.subList(start, end),
                items, "paginate", Collections.emptyList()
        );
    }

    public List<T> getPage(List<T> items, int pageNumber, int pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            handleExceptions.handle(new IllegalArgumentException("Page number and page size must be greater than zero"), this.getClass().getSimpleName(), "getPage");
            return Collections.emptyList();
        }
        int startIndex = (pageNumber - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, items.size());
        return paginate(items, startIndex, endIndex);
    }

    public long count(List<T> items) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "count");
            return 0;
        }

        return helper.handleOperation(List::size, items, "count", 0);
    }

    public boolean anyMatch(List<T> items, Predicate<T> predicate) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "anyMatch");
            return false;
        }

        if (predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Predicate cannot be null"), this.getClass().getSimpleName(), "anyMatch");
            return false;
        }
        return helper.handleOperation(list -> list.stream().anyMatch(predicate), items, "anyMatch", false);
    }

    public boolean allMatch(List<T> items, Predicate<T> predicate) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "allMatch");
            return false;
        } else if (items.isEmpty()) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be empty"), this.getClass().getSimpleName(), "allMatch");
        }

        if (predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Predicate cannot be null"), this.getClass().getSimpleName(), "allMatch");
            return false;
        }
        return helper.handleOperation(list -> list.stream().allMatch(predicate), items, "allMatch", false);
    }

    public boolean noneMatch(List<T> items, Predicate<T> predicate) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "noneMatch");
            return false;
        }

        if (predicate == null) {
            handleExceptions.handle(new IllegalArgumentException("Predicate cannot be null"), this.getClass().getSimpleName(), "noneMatch");
            return false;
        }
        return helper.handleOperation(list -> list.stream().noneMatch(predicate), items, "noneMatch", false);
    }

    public List<T> distinct(List<T> items) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "distinct");
            return Collections.emptyList();
        }
        return helper.handleOperation(
                list -> list.stream().distinct().collect(Collectors.toList()),
                items, "distinct", Collections.emptyList()
        );
    }


    public <R> List<R> map(List<T> items, Function<T, R> mapper) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "map");
            return Collections.emptyList();
        }
        if (mapper == null) {
            handleExceptions.handle(new IllegalArgumentException("Mapper function cannot be null"), this.getClass().getSimpleName(), "map");
            return Collections.emptyList();
        }
        return helper.handleOperation(
                list -> list.stream().map(mapper).collect(Collectors.toList()),
                items, "map", Collections.emptyList()
        );
    }


    public <R> R reduce(List<T> items, R identity, BiFunction<R, T, R> accumulator) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), "reduce");
            return identity;
        }
        if (accumulator == null) {
            handleExceptions.handle(new IllegalArgumentException("Accumulator function cannot be null"), this.getClass().getSimpleName(), "reduce");
            return identity;
        }
        return helper.handleOperation(
                list -> list.stream().reduce(identity, accumulator, (a, b) -> a),
                items, "reduce", identity
        );
    }
}
