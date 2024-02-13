package app.views.UserPanel.AccountSettings;

import app.service.UserContext;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import app.service.AccountSettingsService;
import org.springframework.stereotype.Component;


@Component
public class AccountSettings extends VerticalLayout {
    TextField nameField = new TextField("Name");
    TextField surnameField = new TextField("Surname");
    EmailField emailField = new EmailField("Email");
    private static final Logger logger = LoggerFactory.getLogger(AccountSettings.class);

    private final AccountSettingsService accountSettingsService;
    public AccountSettings(AccountSettingsService accountSettingsService) {
        this.accountSettingsService = accountSettingsService;
        AccountSettingsStyle();
        add(pictureSection(),changeAccountData());
    }

    private void AccountSettingsStyle(){
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        getStyle().set("display", "flex");
        getStyle().set("align-items", "center");
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
        HorizontalLayout deleteImageContainer = new HorizontalLayout();
        deleteImageContainer.addClassName("deleteImageContainer");
        Span deleteImage = new Span("Delete Image");
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.getStyle().set("background", "white");
        deleteImageContainer.add(deleteImage,deleteButton);
        deleteButton.addClickListener(e -> deleteImage());
        Div uploadContainer = new Div(uploadImage(userIconDiv, userIcon), deleteImageContainer);
        uploadContainer.addClassName("uploadContainer");

        picture.add(profilePicture,userIconDiv);

        pictureContainer.add(picture, uploadContainer);

        return  pictureContainer;
    }


    private Upload uploadImage(Div userIconDiv, Icon userIcon) {
        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.addClassName("customUpload");
        upload.setAcceptedFileTypes("image/jpeg", "image/png");
        upload.setMaxFiles(1);
        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();
            logger.warn(event.getErrorMessage());
            Notification notification = createNotification(errorMessage, NotificationVariant.LUMO_ERROR);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
        });
        Image uploadedImage = new Image();
        upload.addSucceededListener(event -> {
            try {
                InputStream inputStream = buffer.getInputStream(event.getFileName());
                String uploadDirectory = "C:\\Users\\Kamil\\Desktop\\Java\\BookStore\\upload\\";
                Path targetPath = Paths.get(uploadDirectory, event.getFileName());

                if (Files.exists(targetPath)) {
                    logger.info("File with this name already exists");
                    return;
                }

                Files.copy(inputStream, targetPath);
                inputStream.close();
                accountSettingsService.addAccountImage(event.getFileName());

                StreamResource resource = getStreamResource(uploadDirectory);

                uploadedImage.setSrc(resource);
                uploadedImage.setVisible(true);
                userIconDiv.remove(userIcon);
                if (resource != null) {
                    userIconDiv.add(uploadedImage);
                    if (deleteImage()) {
                        userIconDiv.add(VaadinIcon.USER.create());
                    }
                }
                    else {
                    userIconDiv.add(VaadinIcon.USER.create());
                }

            } catch (IOException e) {
                Notification notification = createNotification("Couldn't save image to the database, please contact with support", NotificationVariant.LUMO_ERROR);
                notification.open();
                logger.error("Couldn't save image to the database", e);
            }
        });
        return upload;
    }

    private StreamResource getStreamResource(String basePath) {
        String fileName = accountSettingsService.getAccountImageName();
        String fullPath = basePath + fileName;

        File file = new File(fullPath);
        URI fileURI = file.toURI();

        if (!file.exists()) {
            Notification notification = createNotification("The image file does not exist", NotificationVariant.LUMO_ERROR);
            notification.open();
            logger.error("The image file does not exist at the path: {}", fullPath);
            return null;
        }

        return new StreamResource(file.getName(), () -> {
            try {
                return Files.newInputStream(file.toPath());
            } catch (IOException e) {
                Notification notification = createNotification("We can't display your image please contact with support", NotificationVariant.LUMO_WARNING);
                notification.open();
                logger.error("Can't find the image on the Path {}", fullPath, e);
                return null;
            }
        });
    }
    private boolean deleteImage(){
        String fileName = accountSettingsService.getAccountImageName();
        if (fileName != null && !fileName.isEmpty()) {
            boolean deletedFromDatabase = accountSettingsService.deleteAccountImage(fileName);
            if (deletedFromDatabase) {
                String uploadDirectory = "C:\\Users\\Kamil\\Desktop\\Java\\BookStore\\upload\\";
                Path filePath = Paths.get(uploadDirectory, fileName);
                try {
                    Files.delete(filePath);
                    Notification notification = createNotification("Image deleted successfully", NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    logger.info("Image deleted successfully");
                    return true;
                } catch (IOException e) {
                    Notification notification = createNotification("Failed to delete the image file", NotificationVariant.LUMO_ERROR);
                    notification.open();
                    logger.error("Failed to delete the image file", e);
                }
            } else {
                Notification notification = createNotification("Something went wrong while trying to delete the image from the database. Please contact support.", NotificationVariant.LUMO_ERROR);
                notification.open();
                logger.error("Failed to delete the image from the database");
            }
        }
        return false;
    }

    public static Notification createNotification(String message, NotificationVariant variant) {
        Notification notification = new Notification(message, 5000, Notification.Position.TOP_CENTER);
        notification.addThemeVariants(variant);
        return notification;
    }
    private VerticalLayout changeAccountData(){

        VerticalLayout formVerticalLayout = new VerticalLayout();
        formVerticalLayout.getStyle().set("width", "30%");
        formVerticalLayout.addClassName("formVerticalLayout");

        FormLayout formLayout = new FormLayout();
        Button buttonEdit = new Button("Edit");
        buttonEdit.getStyle().set("color", "black");
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
        formLayout.add(buttonEdit);


        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.addClassName("changePasswordButton");
        changePasswordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
           changePasswordButton.addClickListener(event -> {
            ChangePasswordOverflow changePasswordOverflow = new ChangePasswordOverflow();
            changePasswordOverflow.open();
        });
        formVerticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
        formVerticalLayout.add(buttonEdit, formLayout, changePasswordButton);

        return formVerticalLayout;
    }
    private void setValueToFields(String typeOfTextField) {

        switch (typeOfTextField) {
            case "Name":
                if (UserContext.getFirstName() != null) {
                    nameField.setValue(UserContext.getFirstName());
                }
                break;
            case "SurName":
                if (UserContext.getLastName() != null) {
                    surnameField.setValue(UserContext.getLastName());
                }
                break;
            case "Email":
                if (UserContext.getEmail() != null) {
                    emailField.setValue(UserContext.getEmail());
                }
                break;
            default:
                break;
        }

    }
}
