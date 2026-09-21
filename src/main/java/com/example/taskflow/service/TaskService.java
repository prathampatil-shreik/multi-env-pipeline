package com.example.taskflow.service;

import com.example.taskflow.exception.TaskNotFoundException;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskService {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final Map<String, Task> store = new ConcurrentHashMap<>();

    public Task create(Task task) {
        Task newTask = new Task(task.getTitle(), task.getDescription(), task.getStatus(), task.getPriority());
        store.put(newTask.getId(), newTask);
        log.info("Task created: id={}, title={}", newTask.getId(), newTask.getTitle());
        return newTask;
    }

    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    public Task findById(String id) {
        Task task = store.get(id);
        if (task == null) throw new TaskNotFoundException(id);
        return task;
    }

    public Task update(String id, Task updated) {
        Task existing = findById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setPriority(updated.getPriority());
        existing.setUpdatedAt(LocalDateTime.now());
        log.info("Task updated: id={}", id);
        return existing;
    }

    public void delete(String id) {
        findById(id);
        store.remove(id);
        log.info("Task deleted: id={}", id);
    }

    public Task updateStatus(String id, TaskStatus status) {
        Task task = findById(id);
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        log.info("Task status updated: id={}, status={}", id, status);
        return task;
    }
}
