package com.focusbuddy.controller;

import com.focusbuddy.dto.ReminderDTO;
import com.focusbuddy.model.Reminder;
import com.focusbuddy.service.ReminderNotifier;
import com.focusbuddy.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin
public class ReminderController {

    @Autowired
    private ReminderService reminderService;
    @Autowired
    private ReminderNotifier reminderNotifier;

    @GetMapping
    public ResponseEntity<List<ReminderDTO>> getAllReminders() {
        return ResponseEntity.ok(reminderService.getAllReminders());
    }

    @PostMapping("/add")
    public ResponseEntity<ReminderDTO> createReminder(@RequestBody ReminderDTO dto) {
        return ResponseEntity.ok(reminderService.addReminder(dto));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReminderDTO>> getRemindersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reminderService.getRemindersByUserId(userId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReminderDTO> updateReminder(@PathVariable Long id, @RequestBody ReminderDTO dto) {
        ReminderDTO updated = reminderService.updateReminder(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable Long id) {
        reminderService.deleteReminder(id);
        return ResponseEntity.ok("Reminder deleted successfully");
    }

    // For each user, store their emitter
    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    @GetMapping("/stream/{userId}")
    public SseEmitter streamReminders(@PathVariable Long userId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        reminderNotifier.addEmitter(userId, emitter);
        return emitter;
    }


    @GetMapping("/test/send/{userId}")
    public ResponseEntity<String> testSend(@PathVariable Long userId) {
        ReminderDTO dummy = new ReminderDTO();
        dummy.setMessage("Test notification 🎯");
        dummy.setRemindAt(LocalDateTime.now());
        dummy.setUserId(userId);
        dummy.setSent(false);

        reminderNotifier.sendReminderEvent(dummy);
        return ResponseEntity.ok("Test reminder sent");
    }



}
