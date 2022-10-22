package org.dng.crud_2022_09_28.DAO;


import org.dng.crud_2022_09_28.Model.VinylRecord;
import static org.assertj.core.api.Assertions.*;

class DAO_HibernateTest {
    VinylRecord item = null;
    DAO_Hibernate daoh = new DAO_Hibernate();

    @org.junit.jupiter.api.Test
    void insertAndSelectById() {
        //String name, String author, int year
        item = new VinylRecord("songs about honey","Winnie the Pooh", 1970);
        daoh.insert(item);
        assertThat(daoh.selectById(item.getId()))
                .isEqualTo(item);

    }

    @org.junit.jupiter.api.Test
    void selectAll() {
        daoh.selectAll().forEach(System.out::println);
    }


    @org.junit.jupiter.api.Test
    void update() {
        VinylRecord existItem = new VinylRecord("songs about honey","Winnie the Pooh", 1970);
        daoh.insert(existItem);

        item = new VinylRecord("songs about honey","Winnie the Pooh", 1975);
        item.setId(existItem.getId());
        daoh.update(item);

        assertThat(daoh.selectById(item.getId()))
                .isEqualTo(item);

    }

    @org.junit.jupiter.api.Test
    void deleteById() {
        VinylRecord existItem = new VinylRecord("songs about honey","Winnie the Pooh", 1970);
        daoh.insert(existItem);
        daoh.deleteById(existItem.getId());
        assertThat(daoh.selectAll()).doesNotContain(existItem);
    }
}