package org.dng.crud_2022_09_28.DAO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.dng.crud_2022_09_28.Model.VinylRecord;
import org.hibernate.Session;
import org.hibernate.query.Query;


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
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<VinylRecord> cq = cb.createQuery(VinylRecord.class);
        Root<VinylRecord> root = cq.from(VinylRecord.class);

//        cq.select(root).where(root.get("name").in(pName));
//        Query<VinylRecord> query = session.createQuery(cq);
//        List<VinylRecord> results = query.getResultList();

        if (pName!=""&&pAuthor!=""&&pYear>0){
//            String hql = "select r from VinylRecord r where r.name = :pName and r.author=:pAuthor and r.year = :pYear";
//            itemList = session.createQuery(hql, VinylRecord.class)
//                    .setParameter("pName",pName)
//                    .setParameter("pAuthor", pAuthor)
//                    .setParameter("pYear", pYear)
//                    .getResultList();
            Predicate[] predicates = new Predicate[3];
            predicates[0] = cb.like(root.get("name"),pName);
            predicates[1] = cb.like(root.get("author"),pAuthor);
            predicates[2] = cb.equal(root.get("year"),pYear);

//            cq.select(root).where(cb.and( root.get("name").in(pName), root.get("author").in(pAuthor)), root.get("year").in(pYear) );
            cq.select(root).where(predicates);
            Query<VinylRecord> query = session.createQuery(cq);
            itemList = query.getResultList();


        } else if (pName!=""&&pAuthor!="") {
//            String hql = "select r from VinylRecord r where r.name = :pName and r.author=:pAuthor";
//            itemList = session.createQuery(hql, VinylRecord.class)
//                    .setParameter("pName",pName)
//                    .setParameter("pAuthor", pAuthor)
//                    .getResultList();
            Predicate[] predicates = new Predicate[2];
            predicates[0] = cb.like(root.get("name"),pName);
            predicates[1] = cb.like(root.get("author"),pAuthor);
            cq.select(root).where(predicates);
            Query<VinylRecord> query = session.createQuery(cq);
            itemList = query.getResultList();
        } else if (pName!="") {
//            String hql = "select r from VinylRecord r where r.name = :pName ";
//            itemList = session.createQuery(hql, VinylRecord.class)
//                    .setParameter("pName",pName)
//                    .getResultList();
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.like(root.get("name"),pName);
            cq.select(root).where(predicates);
            Query<VinylRecord> query = session.createQuery(cq);
            itemList = query.getResultList();

        } else if (pAuthor!="") {
//            String hql = "select r from VinylRecord r where r.author=:pAuthor";
//            itemList = session.createQuery(hql, VinylRecord.class)
//                    .setParameter("pAuthor", pAuthor)
//                    .getResultList();
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.like(root.get("author"),pAuthor);
            cq.select(root).where(predicates);
            Query<VinylRecord> query = session.createQuery(cq);
            itemList = query.getResultList();
        } else if (pYear>0) {
//            String hql = "select r from VinylRecord r where r.year = :pYear";
//            itemList = session.createQuery(hql, VinylRecord.class)
//                    .setParameter("pYear", pYear)
//                    .getResultList();
            Predicate[] predicates = new Predicate[1];
            predicates[0] = cb.equal(root.get("year"),pYear);
            cq.select(root).where(predicates);
            Query<VinylRecord> query = session.createQuery(cq);
            itemList = query.getResultList();
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
