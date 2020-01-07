package com.coachtam.tqt.listener;

import com.coachtam.tqt.event.UserEvent;
import com.coachtam.tqt.service.UserEventLogService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserLogListener implements ApplicationListener<UserEvent> {

    private UserEventLogService userEventLogService;

    @Override
    public void onApplicationEvent(UserEvent userEvent) {
        userEventLogService.save(userEvent.getUserEventLog());
    }

}
