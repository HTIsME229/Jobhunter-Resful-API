package vn.hoidanit.jobhunter.domain.res;

public class Meta {
    int current;
    int pageSize;
    int totalsPage;
    int totalsItems;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalsItems() {
        return totalsItems;
    }

    public void setTotalsItems(int totalsItems) {
        this.totalsItems = totalsItems;
    }

    public int getTotalsPage() {
        return totalsPage;
    }

    public void setTotalsPage(int totalsPage) {
        this.totalsPage = totalsPage;
    }
}
