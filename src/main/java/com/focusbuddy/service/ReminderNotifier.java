package com.focusbuddy.service;

import com.focusbuddy.dto.ReminderDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ReminderNotifier {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void addEmitter(Long userId, SseEmitter emitter) {
        emitters.put(userId, emitter);
    }

    public void sendReminderEvent(ReminderDTO reminder) {
        SseEmitter emitter = emitters.get(reminder.getUserId());
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("reminder")
                        .data(reminder));
                System.out.println("SSE ReminderEvent sent ✅ at "+ reminder.getRemindAt());
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(reminder.getUserId());
            }
        }
    }
}
