package org.dng.crud_2022_09_28.DAO;


public class CreateAndFillDB {
    public static void main(String[] args) {
        makeBD();
    }

    public static void makeBD(){
        PrepareDB.prepareBase();
        PrepareDB.createTables();
        PrepareDB.fillTables();
    }
}
