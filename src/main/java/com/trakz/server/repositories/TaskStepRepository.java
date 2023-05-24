package com.trakz.server.repositories;

import com.trakz.server.entities.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStepRepository extends JpaRepository<TaskStep, Long> {
}
