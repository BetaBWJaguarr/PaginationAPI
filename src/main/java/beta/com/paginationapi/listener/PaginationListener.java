package beta.com.paginationapi.listener;

import beta.com.paginationapi.page.Pagination;

public class PaginationListener {

    private final Pagination pagination;

    public PaginationListener(Pagination pagination) {
        this.pagination = pagination;
    }

    public void onNextPage() {
        if (pagination.hasNextPage()) {
            pagination.nextPage();
        }
    }

    public void onPreviousPage() {
        if (pagination.hasPreviousPage()) {
            pagination.previousPage();
        }
    }
}
