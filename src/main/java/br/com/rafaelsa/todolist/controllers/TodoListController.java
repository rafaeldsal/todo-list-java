package br.com.rafaelsa.todolist.controllers;

import br.com.rafaelsa.todolist.controllers.dto.TaskDescription;
import br.com.rafaelsa.todolist.entities.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TodoListController {

  private List<Task> tasks = new ArrayList<>();

  @GetMapping
  public ResponseEntity<List<Task>> listTasks () {
    return ResponseEntity.ok(tasks);
  }

  @PostMapping
  public void createTask(@RequestBody Task task) {
    tasks.add(task);
  }

  @PutMapping("{id}")
  public ResponseEntity<Void> updateTask(@PathVariable(name = "id") Long id,
                                         @RequestBody TaskDescription taskDescription) {
    tasks = tasks.stream()
        .map(task -> {
          if (task.id().equals(id)) {
            return new Task(task.id(), taskDescription.description());
          }
          return task;
        }).collect(Collectors.toCollection(ArrayList::new));

    return ResponseEntity.noContent().build();

  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteTask(@PathVariable(name = "id") Long id) {
    tasks.removeIf(task -> task.id().equals(id));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> clearTasks() {
    tasks = new ArrayList<>();

    return ResponseEntity.noContent().build();
  }
}
