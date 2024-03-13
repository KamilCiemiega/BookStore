package app.views.UserPanel.Utils;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class ShowNotification {

    public static void showNotification(String text, NotificationVariant variant) {
        Notification notification = new Notification(text, 3000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
        notification.open();
    }
}
