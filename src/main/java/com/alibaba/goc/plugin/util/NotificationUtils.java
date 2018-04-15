package com.alibaba.goc.plugin.util;

import com.intellij.icons.AllIcons;
import com.intellij.notification.*;
import com.intellij.openapi.project.Project;

public class NotificationUtils {
    public static void showEventLog(String title, String subtitle, String content, Project project) {
        //使用EventLog显示获取的数据信息
        NotificationGroup group = NotificationGroup.findRegisteredGroup("goc-plugin");
        if (group == null) {
            group = new NotificationGroup("goc-plugin", NotificationDisplayType.STICKY_BALLOON, true, EventLog.LOG_TOOL_WINDOW_ID, AllIcons.General.Balloon);
        }
        Notification notification = group.createNotification(title, subtitle, content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification, project);
    }
}
