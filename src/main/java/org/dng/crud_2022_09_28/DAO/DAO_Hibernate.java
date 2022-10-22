package org.dng.crud_2022_09_28.DAO;

import org.dng.crud_2022_09_28.Model.VinylRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DAO_Hibernate implements ICRUD {



    public void insert(VinylRecord item) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.persist(item);
        session.close();
    }

    public VinylRecord selectById(long id) {
        VinylRecord item = null;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        item = session.find(VinylRecord.class, id);
        session.close();
        return item;
    }

    public List<VinylRecord> selectAll() {
        List<VinylRecord> itemList;
        Session session = HibernateUtil.getSession();
//        CriteriaQuery<VinylRecord> cq = session.getCriteriaBuilder().createQuery(VinylRecord.class);
//        cq.from(VinylRecord.class);
//        List<VinylRecord> itemList = session.createQuery(cq).getResultList();
        itemList = session.createQuery("SELECT r FROM VinylRecord r", VinylRecord.class).getResultList();
        return itemList;
    }

    public List<VinylRecord> searchByParam(String pName, String pAuthor, int pYear) {
        List<VinylRecord> itemList = new LinkedList<>();

        Session session = HibernateUtil.getSession();
        if (pName!=""&&pAuthor!=""&&pYear>0){
            String hql = "select r from VinylRecord r where r.name = :pName and r.author=:pAuthor and r.year = :pYear";
            itemList = session.createQuery(hql, VinylRecord.class)
                    .setParameter("pName",pName)
                    .setParameter("pAuthor", pAuthor)
                    .setParameter("pYear", pYear)
                    .getResultList();

        } else if (pName!=""&&pAuthor!="") {
            String hql = "select r from VinylRecord r where r.name = :pName and r.author=:pAuthor";
            itemList = session.createQuery(hql, VinylRecord.class)
                    .setParameter("pName",pName)
                    .setParameter("pAuthor", pAuthor)
                    .getResultList();
        } else if (pName!="") {
            String hql = "select r from VinylRecord r where r.name = :pName ";
            itemList = session.createQuery(hql, VinylRecord.class)
                    .setParameter("pName",pName)
                    .getResultList();

        } else if (pAuthor!="") {
            String hql = "select r from VinylRecord r where r.author=:pAuthor";
            itemList = session.createQuery(hql, VinylRecord.class)
                    .setParameter("pAuthor", pAuthor)
                    .getResultList();
        } else if (pYear>0) {
            String hql = "select r from VinylRecord r where r.year = :pYear";
            itemList = session.createQuery(hql, VinylRecord.class)
                    .setParameter("pYear", pYear)
                    .getResultList();
        }
        return itemList;
    }


    public void update(VinylRecord item) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        session.merge(item);
        tx.commit();
        session.close();
    }

    public void deleteById(long id) {
        VinylRecord record = selectById(id);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.remove(record);
        session.getTransaction().commit();
        session.close();
    }


    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}
