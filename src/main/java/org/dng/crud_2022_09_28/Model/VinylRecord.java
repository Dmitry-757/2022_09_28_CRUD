package org.dng.crud_2022_09_28.Model;

public class VinylRecord {
    private long id;
    private String name;
    private String author;
    private int year;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public VinylRecord(long id, String name, String author, int year) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }
}
