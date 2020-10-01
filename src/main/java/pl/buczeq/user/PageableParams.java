package pl.buczeq.user;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.ws.rs.QueryParam;

public class PageableParams {

    @QueryParam("page")
    private int page;

    @QueryParam("size")
    private int size;

    @QueryParam("sortParam")
    private Sort sort;

    @QueryParam("sortOrder")
    private String sortOrder;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size, sort);
    }

    public PageableParams(int page, int size) {
        this.page = page;
        this.size = size;
        this.sort = Sort.by("birthDate").ascending();
    }

    public PageableParams(int page, int size, String sort, String sortOrder) {
        this.page = page;
        this.size = size;
        if (sortOrder.equals("desc")) {
            this.sort = Sort.by(sort).descending();
        } else this.sort = Sort.by(sort).ascending();
    }

    public PageableParams() {
        this.page = 0;
        this.size = 10;
        this.sort = Sort.by("birthDate").ascending();
    }

}
