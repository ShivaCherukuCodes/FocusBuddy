package com.focusbuddy.repository;

import com.focusbuddy.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
    List<Reminder> findByUserId(Long userId);
    List<Reminder> findBySentFalseAndRemindAtBefore(LocalDateTime now);
}
