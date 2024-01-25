package app.views;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;

public interface ViewConfigurator extends HasSize, HasStyle {
    default void configureView() {
        setSizeFull();
        getElement().getStyle().set("align-items", "center");
        getElement().getStyle().set("justify-content", "center");
    }
}
