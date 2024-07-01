package beta.com.paginationapi.sortmanager;

import beta.com.paginationapi.errorevents.HandleExceptions;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Helper<T> {
    private final HandleExceptions handleExceptions;

    public Helper(HandleExceptions handleExceptions) {
        this.handleExceptions = handleExceptions;
    }

    public boolean validateList(List<T> items, String methodName) {
        if (items == null) {
            handleExceptions.handle(new IllegalArgumentException("Items cannot be null"), this.getClass().getSimpleName(), methodName);
            return false;
        }
        return true;
    }

    public List<T> sort(List<T> items, Comparator<T> comparator) {
        if (!validateList(items, "sort") || comparator == null) {
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
        if (!validateList(items, "filter") || predicate == null) {
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

    public <R> R handleOperation(Function<List<T>, R> operation, List<T> items, String methodName, R defaultValue) {
        if (!validateList(items, methodName)) return defaultValue;
        try {
            return operation.apply(items);
        } catch (Exception e) {
            handleExceptions.handle(e, this.getClass().getSimpleName(), methodName);
            return defaultValue;
        }
    }
}
