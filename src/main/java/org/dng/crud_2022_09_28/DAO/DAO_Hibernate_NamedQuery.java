package org.dng.crud_2022_09_28.DAO;

import jakarta.persistence.criteria.CriteriaQuery;
import org.dng.crud_2022_09_28.Model.VinylRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;


import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class DAO_Hibernate_NamedQuery implements ICRUD {



    public void insert(VinylRecord item) {
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        //session.persist(item);
        Query<VinylRecord> query = session.createNamedQuery("insert", VinylRecord.class);
        query.setParameter("name", item.getName());
        query.setParameter("author", item.getAuthor());
        query.setParameter("year", item.getYear());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public VinylRecord selectById(long id) {
        VinylRecord item = null;
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        Query<VinylRecord> query = session.createNamedQuery("selectById", VinylRecord.class);
        query.setParameter("recId", id);

        item = query.getSingleResult();
//                session.find(VinylRecord.class, id);
        session.close();
        return item;
    }

    public List<VinylRecord> selectAll() {
        List<VinylRecord> itemList;
        Session session = HibernateUtil.getSession();
//        CriteriaQuery<VinylRecord> cq = session.getCriteriaBuilder().createQuery(VinylRecord.class);
//        cq.from(VinylRecord.class);
//        List<VinylRecord> itemList = session.createQuery(cq).getResultList();

//        itemList = session.createQuery("SELECT r FROM VinylRecord r", VinylRecord.class).getResultList();
        session.beginTransaction();
        Query<VinylRecord> query = session.createNamedQuery("selectAll", VinylRecord.class);
        itemList = query.getResultList();

        session.close();
        return itemList;
    }

    public List<VinylRecord> searchByParam(String pName, String pAuthor, int pYear) {
        List<VinylRecord> itemList = new LinkedList<>();

        Session session = HibernateUtil.getSession();
        CriteriaQuery<VinylRecord> cq = session.getCriteriaBuilder().createQuery(VinylRecord.class);
        cq.from(VinylRecord.class);

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
//        Transaction tx = session.beginTransaction();
//        session.merge(item);
//        tx.commit();
        session.beginTransaction();
        Query<VinylRecord> query = session.createNamedQuery("update", VinylRecord.class);
        query.setParameter("pName", item.getName());
        query.setParameter("pAuthor", item.getAuthor());
        query.setParameter("pYear", item.getYear());
        query.setParameter("pId", item.getId());
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteById(long id) {
        VinylRecord record = selectById(id);
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        //session.remove(record);
        Query<VinylRecord> query = session.createNamedQuery("delete", VinylRecord.class);
        query.setParameter("pId", id);
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


}
