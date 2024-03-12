package aor.paj.ctn.dao;

import aor.paj.ctn.entity.TaskEntity;
import aor.paj.ctn.entity.UserEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.NoResultException;

import java.util.ArrayList;

@Stateless
public class TaskDao extends AbstractDao<TaskEntity> {

	private static final long serialVersionUID = 1L;

	public TaskDao() {
		super(TaskEntity.class);
	}
	

	public TaskEntity findTaskById(String id) {
		try {
			return (TaskEntity) em.createNamedQuery("Task.findTaskById").setParameter("id", id)
					.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

	public ArrayList<TaskEntity> findTasksByUser(UserEntity userEntity) {
		try {
            return (ArrayList<TaskEntity>) em.createNamedQuery("Task.findTasksByUser").setParameter("owner", userEntity).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<TaskEntity> findTasksByCategoryID(int id) {
		try {
			return (ArrayList<TaskEntity>) em.createNamedQuery("Task.findTasksByCategoryID").setParameter("categoryId", id).getResultList();
		} catch (Exception e) {
			return null;
		}
	}


	public ArrayList<TaskEntity> findErasedTasks() {
		try {
            return (ArrayList<TaskEntity>) em.createNamedQuery("Task.findErasedTasks").getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<TaskEntity> findAllTasks() {
		try {
            return (ArrayList<TaskEntity>) em.createNamedQuery("Task.findAllTasks").getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean eraseTask(String id) {
		boolean erased = false;
		if (id == null) {
			erased = false;
		} else {
			try {
				TaskEntity taskToErase = findTaskById(id);
				taskToErase.setErased(true);
				merge(taskToErase);
				erased = true;
			} catch (Exception e) {
				erased = false;
			}
		}
		return erased;
	}


	public boolean deleteTask(String id) {
		boolean deleted = false;
		if (id == null) {
			deleted = false;
		} else {
			try {
				em.createNamedQuery("DeleteTask").setParameter("id", id).executeUpdate();
				deleted = true;
			} catch (Exception e) {
				deleted = false;
			}
		}
		return deleted;
	}

}
