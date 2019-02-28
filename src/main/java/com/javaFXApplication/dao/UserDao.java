package com.javaFXApplication.dao;

import com.javaFXApplication.utils.HibernateSessionFactory;
import com.javaFXApplication.entities.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDao{

          public static void saveUser(User user){
                    SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction tx = null;
                    try {
                              tx = session.beginTransaction();
                              session.saveOrUpdate(user);
                              tx.commit();
                    } catch (HibernateException he){
                              if (tx!=null) tx.rollback();
                              he.printStackTrace();
                    } finally{
                              session.close();
                              sessionFactory.close();
                    }
          }

          public static void deleteUser(User user){
                    SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction tx = null;
                    try {
                              tx = session.beginTransaction();
                              session.delete(user);
                              tx.commit();
                    } catch (HibernateException he){
                              if (tx!=null) tx.rollback();
                              he.printStackTrace();
                    } finally{
                              session.close();
                              sessionFactory.close();
                    }
          }

          public static User findUserByUsername(String username) {
                    SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();
                    Session session = sessionFactory.openSession();
                    Transaction tx = null;
                    User user = new User();

                    try{
                              tx = session.beginTransaction();
                              CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
                              CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
                              Root<User> userRoot = criteriaQuery.from(User.class);
                              criteriaQuery.select(userRoot);
                              criteriaQuery.where(
                                      criteriaBuilder.equal(userRoot.get("username"),username)
                              );
                              user = session.createQuery(criteriaQuery).getSingleResult();
                              tx.commit();
                    } catch (Exception e){
                              if (tx != null) tx.rollback();
                    } finally {
                              session.close();
                              sessionFactory.close();
                    }

                    return user;
          }


}