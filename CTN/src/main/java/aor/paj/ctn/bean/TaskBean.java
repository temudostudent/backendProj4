package aor.paj.ctn.bean;

import aor.paj.ctn.dao.CategoryDao;
import aor.paj.ctn.dao.TaskDao;
import aor.paj.ctn.dao.UserDao;
import aor.paj.ctn.dto.Task;
import aor.paj.ctn.dto.User;
import aor.paj.ctn.entity.TaskEntity;
import aor.paj.ctn.entity.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Stateless
public class TaskBean implements Serializable {

    @EJB
    private TaskDao taskDao;
    @EJB
    private CategoryDao categoryDao;
    @EJB
    private UserDao userDao;
    @EJB
    private UserBean userBean;
    @EJB
    private CategoryBean categoryBean;
    @EJB
    private TaskBean taskBean;




    public boolean newTask(Task task, String token) {
        boolean created = false;

        task.generateId();
        task.setInitialStateId();
        task.setOwner(userBean.convertUserEntitytoUserDto(userDao.findUserByToken(token)));
        task.setErased(false);
        task.setCategory(task.getCategory());
        if (validateTask(task)) {
            taskDao.persist(convertTaskToEntity(task));
            created = true;

        }

        return created;
    }

    public ArrayList<Task> getAllTasks(String token) {
        UserEntity userEntity = userDao.findUserByToken(token);
        ArrayList<TaskEntity> entityTasks = taskDao.findAllTasks();
        ArrayList<Task> tasks = new ArrayList<>();
        if (entityTasks != null) {
            for (TaskEntity taskEntity : entityTasks) {
                if (userEntity.getTypeOfUser() == User.DEVELOPER && !taskEntity.getErased()) {
                    tasks.add(convertTaskEntityToTaskDto(taskEntity));
                } else if (userEntity.getTypeOfUser() == User.SCRUMMASTER || userEntity.getTypeOfUser() == User.PRODUCTOWNER) {
                    tasks.add(convertTaskEntityToTaskDto(taskEntity));
                }
            }
        }
        return tasks;
    }

    public ArrayList<Task> getAllTasksFromUser(String username, String token) {
        UserEntity loggedUser = userDao.findUserByToken(token);
        UserEntity tasksOwner = userDao.findUserByUsername(username);
        ArrayList<TaskEntity> entityUserTasks = taskDao.findTasksByUser(tasksOwner);

        ArrayList<Task> userTasks = new ArrayList<>();
        if (entityUserTasks != null) {
            for (TaskEntity taskEntity : entityUserTasks) {
                if (loggedUser.getTypeOfUser() == User.DEVELOPER && !taskEntity.getErased()) {
                    userTasks.add(convertTaskEntityToTaskDto(taskEntity));
                } else if (loggedUser.getTypeOfUser() == User.SCRUMMASTER || loggedUser.getTypeOfUser() == User.PRODUCTOWNER) {
                    userTasks.add(convertTaskEntityToTaskDto(taskEntity));
                }
            }
        }
        return userTasks;
    }

    public boolean updateTask(Task task, String id, String categoryName, String startDate, String limitDate) {
        TaskEntity taskEntity = taskDao.findTaskById(id);
        Task taskDto = taskBean.convertTaskEntityToTaskDto(taskEntity);
        User taskOwner = taskDto.getOwner();
        LocalDate start = LocalDate.parse(startDate);
        LocalDate limit = LocalDate.parse(limitDate);

        boolean edited = false;
        task.setId(id);
        task.setOwner(taskOwner);
        task.setStartDate(start);
        task.setLimitDate(limit);
        task.setCategory(categoryBean.convertCategoryEntityToCategoryDto(categoryDao.findCategoryByName(categoryName)));
        if (taskDao.findTaskById(task.getId()) != null) {
            if (validateTask(task)) {
                taskDao.merge(convertTaskToEntity(task));
                edited = true;
            }
        }

        return edited;
    }

    public boolean updateTaskStatus(String taskId, int stateId) {
        boolean updated = false;
        if (stateId != 100 && stateId != 200 && stateId != 300) {
            updated = false;
        } else {
            TaskEntity taskEntity = taskDao.findTaskById(taskId);
            if (taskEntity != null) {
                taskEntity.setStateId(stateId);
                taskDao.merge(taskEntity);
                updated = true;
            }
        }
        return updated;
    }



    public boolean switchErasedTaskStatus(String id) {
        boolean swithedErased = false;
        TaskEntity taskEntity = taskDao.findTaskById(id);
        if(taskEntity != null) {
            taskEntity.setErased(!taskEntity.getErased());
            taskDao.merge(taskEntity);
            swithedErased = true;
        }
        return swithedErased;
    }

    public boolean permanentlyDeleteTask(String id) {
        boolean removed = false;
        TaskEntity taskEntity = taskDao.findTaskById(id);
        if (taskEntity != null && !taskEntity.getErased()) {
            taskDao.eraseTask(id);
            removed = true;
        } else if (taskEntity != null && taskEntity.getErased()) {
            taskDao.deleteTask(id);
            removed = true;
        }
        return removed;
    }

    public ArrayList<Task> getTasksByCategory(String category) {
        ArrayList<TaskEntity> entityTasks = categoryDao.findTasksByCategory(category);
        ArrayList<Task> tasks = new ArrayList<>();
        if (entityTasks != null) {
            for (TaskEntity taskEntity : entityTasks) {
                tasks.add(convertTaskEntityToTaskDto(taskEntity));
            }
        }
        return tasks;
    }

    public boolean validateTask(Task task) {
        boolean valid = true;
        if ((task.getStartDate() == null
                || task.getLimitDate() == null
              //  || task.getLimitDate().isBefore(task.getStartDate())
                || task.getTitle().isBlank()
                || task.getDescription().isBlank()
                || task.getOwner() == null
                || task.getPriority() == 0
                || task.getCategory() == null
                || !categoryBean.categoryExists(task.getCategory().getName())
                || (task.getPriority() != Task.LOWPRIORITY && task.getPriority() != Task.MEDIUMPRIORITY && task.getPriority() != Task.HIGHPRIORITY)
                || (task.getStateId() != Task.TODO && task.getStateId() != Task.DOING && task.getStateId() != Task.DONE)
        )) {
            valid = false;
        }
        return valid;
    }

    public ArrayList<Task> getErasedTasks() {
        ArrayList<TaskEntity> entityTasks = taskDao.findErasedTasks();
        ArrayList<Task> tasks = new ArrayList<>();
        if (entityTasks != null) {
            for (TaskEntity taskEntity : entityTasks) {
                tasks.add(convertTaskEntityToTaskDto(taskEntity));
            }
        }
        return tasks;
    }

    public boolean eraseAllTasksFromUser(String username) {
        boolean erased = false;
        UserEntity userEntity = userDao.findUserByUsername(username);
        if (userEntity != null) {
            ArrayList<TaskEntity> userTasks = taskDao.findTasksByUser(userEntity);
            if (userTasks != null) {
                for (TaskEntity taskEntity : userTasks) {
                    taskEntity.setErased(true);
                    taskEntity.setOwner(userDao.findUserByUsername("NotAssigned"));
                    taskDao.merge(taskEntity);
                }
                erased = true;
            }
        }
        return erased;
    }


    private TaskEntity convertTaskToEntity(Task task) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(task.getId());
        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setPriority(task.getPriority());
        taskEntity.setStateId(task.getStateId());
        taskEntity.setStartDate(task.getStartDate());
        taskEntity.setLimitDate(task.getLimitDate());
        taskEntity.setCategory(categoryDao.findCategoryByName(task.getCategory().getName()));
        taskEntity.setErased(task.getErased());
        taskEntity.setOwner(userBean.convertUserDtotoUserEntity(task.getOwner()));
        return taskEntity;
    }

    public Task convertTaskEntityToTaskDto(TaskEntity taskEntity) {
        Task task = new Task();
        task.setId(taskEntity.getId());
        task.setTitle(taskEntity.getTitle());
        task.setDescription(taskEntity.getDescription());
        task.setPriority(taskEntity.getPriority());
        task.setStateId(taskEntity.getStateId());
        task.setStartDate(taskEntity.getStartDate());
        task.setLimitDate(taskEntity.getLimitDate());
        task.setCategory(categoryBean.convertCategoryEntityToCategoryDto(taskEntity.getCategory()));
        task.setErased(taskEntity.getErased());
        task.setOwner(userBean.convertUserEntitytoUserDto(taskEntity.getOwner()));
        return task;
    }



}
