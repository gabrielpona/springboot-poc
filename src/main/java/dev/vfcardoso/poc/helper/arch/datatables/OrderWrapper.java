package dev.vfcardoso.poc.helper.arch.datatables;

import org.springframework.context.annotation.Bean;

import javax.inject.Named;

public class OrderWrapper {
    @Named("column")
    private int column;
    @Named("dir")
    private String dir;


    public OrderWrapper(int column, String dir) {
        this.column = column;
        this.dir = dir;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getOrderColumn() {
        return column;
    }

    public String getOrderDirection() {
        return dir;
    }

}
