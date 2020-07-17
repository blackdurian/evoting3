package com.evoting.dao;

import com.evoting.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateSession {
  private Session currentSession;
  private Transaction currentTransaction;

  public void openCurrentSession() {
    currentSession = HibernateUtil.getSessionFactory().openSession();
  }
  public void openCurrentSessionWithTransaction() {
    currentSession = HibernateUtil.getSessionFactory().openSession();
    currentTransaction = currentSession.beginTransaction();
  }
  public void closeCurrentSession() {
    currentSession.close();
  }

  public void closeCurrentSessionWithTransaction() {
    currentTransaction.commit();
    currentSession.close();
  }

  public Session getCurrentSession() {
    return currentSession;
  }
  public void setCurrentSession(Session currentSession) {
    this.currentSession = currentSession;
  }

  public Transaction getCurrentTransaction() {
    return currentTransaction;
  }

  public void setCurrentTransaction(Transaction currentTransaction) {
    this.currentTransaction = currentTransaction;
  }

}
