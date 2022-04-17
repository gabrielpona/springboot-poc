package dev.vfcardoso.poc.helper.arch.datatables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vinícius on 11/09/2015.
 */
public class DataTables<T extends Object> {

    private int draw;
    private int recordsTotal;
    private int recordsFiltered;
    private List<T> data = new ArrayList<>();

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}