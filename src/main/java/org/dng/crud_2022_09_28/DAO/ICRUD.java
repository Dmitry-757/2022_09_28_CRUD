package org.dng.crud_2022_09_28.DAO;

import org.dng.crud_2022_09_28.Model.VinylRecord;

import java.util.List;

public interface ICRUD {
    VinylRecord selectById(long id);
    List<VinylRecord> selectAll();
    List<VinylRecord> searchByParam(String pName, String pAuthor, int pYear);
    void insert(VinylRecord item);
    void update(VinylRecord item);
    void deleteById(long id);
}
