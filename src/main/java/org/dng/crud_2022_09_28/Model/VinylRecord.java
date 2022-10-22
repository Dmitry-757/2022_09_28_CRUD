package org.dng.crud_2022_09_28.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vinyla_tbl", schema = "vinyla_db")
public class VinylRecord {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "author")
    private String author;
    @Basic
    @Column(name = "year")
    private int year;

    public VinylRecord() {
    }

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

    public VinylRecord(String name, String author, int year) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VinylRecord that = (VinylRecord) o;
        return id == that.id && year == that.year && Objects.equals(name, that.name) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, author, year);
    }

    @Override
    public String toString() {
        return "VinylRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}


