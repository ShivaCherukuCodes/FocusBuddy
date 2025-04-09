package com.focusbuddy.service;

import com.focusbuddy.controller.ReminderController;
import com.focusbuddy.dto.ReminderDTO;
import com.focusbuddy.model.Reminder;
import com.focusbuddy.repository.ReminderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderNotifier reminderNotifier;

    public ReminderDTO addReminder(ReminderDTO dto) {
        Reminder reminder = new Reminder();
        reminder.setMessage(dto.getMessage());
        reminder.setRemindAt(dto.getRemindAt());
        reminder.setSent(false);
        reminder.setUserId(dto.getUserId());
        reminder = reminderRepository.save(reminder);
        dto.setId(reminder.getId());
        return dto;
    }

    public List<ReminderDTO> getAllReminders() {
        return reminderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReminderDTO> getRemindersByUserId(Long userId) {
        List<Reminder> reminders = reminderRepository.findByUserId(userId);
        return reminders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReminderDTO updateReminder(Long id, ReminderDTO dto) {
        Reminder reminder = reminderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found with id: " + id));

        reminder.setMessage(dto.getMessage());
        reminder.setRemindAt(dto.getRemindAt());
        reminder.setSent(dto.isSent());

        reminder = reminderRepository.save(reminder);
        return convertToDTO(reminder);
    }

    public void deleteReminder(Long id) {
        if (!reminderRepository.existsById(id)) {
            throw new RuntimeException("Reminder not found with id: " + id);
        }
        reminderRepository.deleteById(id);
    }


    @Scheduled(fixedRate = 60000) // Every 60 seconds
    public void processScheduledReminders() {
        LocalDateTime now = LocalDateTime.now();
        List<Reminder> dueReminders = reminderRepository.findBySentFalseAndRemindAtBefore(now);

        for (Reminder reminder : dueReminders) {
            reminder.setSent(true);
            reminderRepository.save(reminder);

            // Send real-time SSE
            reminderNotifier.sendReminderEvent(convertToDTO(reminder));

            System.out.println("🔔 Reminder triggered for user " + reminder.getUserId() + ": " + reminder.getMessage());
        }

    }

    private ReminderDTO convertToDTO(Reminder reminder) {
        ReminderDTO dto = new ReminderDTO();
        dto.setId(reminder.getId());
        dto.setMessage(reminder.getMessage());
        dto.setRemindAt(reminder.getRemindAt());
        dto.setSent(reminder.isSent());
        dto.setUserId(reminder.getUserId());
        return dto;
    }


}
