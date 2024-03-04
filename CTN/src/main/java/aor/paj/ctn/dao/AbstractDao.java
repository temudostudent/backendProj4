package aor.paj.ctn.dao;

import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;

import java.io.Serializable;
import java.util.List;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Class<T> clazz;
	
	@PersistenceContext(unitName = "PersistenceUnit")
	protected EntityManager em;

	public AbstractDao(Class<T> clazz) 
	{
		this.clazz = clazz;
	}

	
	public T find(Object id) 
	{
		return em.find(clazz, id);
	}

	
	public void persist(final T entity) 
	{
		em.persist(entity);
	}
	
	
	public void merge(final T entity) 
	{
		em.merge(entity);
	}
	
	public void remove(final T entity) 
	{
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}
	

	public List<T> findAll() 
	{
		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}

	public void deleteAll() 
	{
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}
	
	public void flush() {
		em.flush();
	}
}