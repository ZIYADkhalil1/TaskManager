package com.softxpert.taskManager.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.softxpert.taskManager.Entities.TaskDependency;

public interface TaskDependencyRepository extends JpaRepository<TaskDependency, Long> {

    @Query("SELECT td.dependencyId FROM TaskDependency td WHERE td.taskId = :taskId")
    List<Long> findDependencyIdByTaskId(Long taskId);
    
    Optional<TaskDependency> findByTaskIdAndDependencyId(Long taskId, Long dependencyId);
}
