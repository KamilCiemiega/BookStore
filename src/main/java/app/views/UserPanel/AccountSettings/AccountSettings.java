package app.views.UserPanel.AccountSettings;

import app.service.AuthenticationService;
import app.service.UserContext;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import app.service.AccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AccountSettings extends VerticalLayout {
    @Autowired
    private AccountSettingsService accountSettingsService;
    @Autowired
    private DataSource dataSource;
    TextField nameField = new TextField("Name");
    TextField surnameField = new TextField("Surname");
    EmailField emailField = new EmailField("Email");
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AccountSettings() {
        AccountSettingsStyle();
        add(pictureSection(),changeAccountData());
    }

    private void AccountSettingsStyle(){
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("display", "flex");
    }

    private HorizontalLayout pictureSection(){
        HorizontalLayout pictureContainer = new HorizontalLayout();
        pictureContainer.addClassName("pictureContainer");
        VerticalLayout picture = new VerticalLayout();
        picture.addClassName("picture");
        picture.setWidth("15%");

        H1 profilePicture = new H1("Profile Picture");
        Icon userIcon = VaadinIcon.USER.create();
        userIcon.addClassName("userIcon");
        Div userIconDiv = new Div();
        userIconDiv.addClassName("userIconDiv");
        userIconDiv.add(userIcon);

        picture.add(profilePicture,userIconDiv);

        pictureContainer.add(picture, uploadImage(userIconDiv, userIcon));

        return  pictureContainer;
    }
    private Upload uploadImage(Div userIconDiv, Icon userIcon ){
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            logger.warn(event.getErrorMessage());
            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.TOP_CENTER);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        Image uploadedImage  = new Image();
        upload.addSucceededListener(event -> {
            try {
                InputStream inputStream = buffer.getInputStream(event.getFileName());
                String uploadDirectory = "C:\\Users\\Kamil\\Desktop\\Java\\BookStore\\upload";
                Path targetPath = Paths.get(uploadDirectory, event.getFileName());

                if (Files.exists(targetPath)) {
                    String errorMessage = "File with this name already exists";
                    Notification notification = Notification.show(errorMessage, 5000, Notification.Position.TOP_CENTER);
                    notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                    logger.info("File with this name already exists");
                    return;
                }

                Files.copy(inputStream, targetPath);
                inputStream.close();
                String imagePath = targetPath.toString();
                try{
                    if (accountSettingsService != null){
                        System.out.println(event.getFileName() + imagePath);
                        accountSettingsService.addAccountImagePath(event.getFileName(), imagePath);
                    }
                }catch (SQLException e){
                    logger.error("error when adding path and filename");
                }
                uploadedImage.setSrc(new StreamResource(event.getFileName(), () -> buffer.getInputStream(event.getFileName())));
                uploadedImage.setVisible(true);
                userIconDiv.remove(userIcon);
                userIconDiv.add(uploadedImage);

            } catch (IOException e){
                System.out.println(e);
                Notification notification = Notification.show("An unexpected error occurred", 5000,
                        Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                logger.error("Couldn't save image to the database", e);
            }
        });
        return upload;
    }


    private VerticalLayout changeAccountData(){

        VerticalLayout formVerticalLayout = new VerticalLayout();
        formVerticalLayout.addClassName("formVerticalLayout");

        FormLayout formLayout = new FormLayout();
        Button button = new Button("Edit");

        nameField.setEnabled(false);
        setValueToFields("Name");
        surnameField.setEnabled(false);
        setValueToFields("SurName");
        emailField.setEnabled(false);
        setValueToFields("Email");

        formLayout.setColspan(nameField, 2);
        formLayout.add(nameField);
        formLayout.setColspan(surnameField, 3);
        formLayout.add(surnameField);
        formLayout.setColspan(emailField, 4);
        formLayout.add(emailField);
        formLayout.add(button);


        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.addClassName("changePasswordButton");
        changePasswordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
           changePasswordButton.addClickListener(event -> {
            ChangePasswordOverflow changePasswordOverflow = new ChangePasswordOverflow();
            changePasswordOverflow.open();
        });
        formVerticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
        formVerticalLayout.add(button, formLayout, changePasswordButton);

        return formVerticalLayout;
    }
    private void setValueToFields(String typeOfTextField) {
        UserContext userContext = new UserContext();

        switch (typeOfTextField) {
            case "Name":
                if (userContext.getFirstName() != null) {
                    nameField.setValue(userContext.getFirstName());
                }
                break;
            case "SurName":
                if (userContext.getLastName() != null) {
                    surnameField.setValue(userContext.getLastName());
                }
                break;
            case "Email":
                if (userContext.getEmail() != null) {
                    emailField.setValue(userContext.getEmail());
                }
                break;
            default:
                break;
        }

    }
}
