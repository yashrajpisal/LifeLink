package com.kurukshetra.controller.police;

import com.kurukshetra.dao.police.PoliceProfileDao;
import com.kurukshetra.model.PoliceUserModel;
import com.kurukshetra.model.police.PoliceProfileModel;
import javafx.application.Platform;

import java.util.function.Consumer;

public class PoliceProfileController {

    private final PoliceProfileDao dao = new PoliceProfileDao();
    
    // Dynamically assigned upon successful login/signup, defaults to active control room session
    public static String loggedInEmail = "police1@lifelink.com";

    public static String getEffectiveEmail() {
        if (loggedInEmail != null && !loggedInEmail.trim().isEmpty()) {
            return loggedInEmail.trim();
        }
        try {
            PoliceUserModel userModel = PoliceUserModel.getInstance();
            if (userModel != null && userModel.getEmail() != null && !userModel.getEmail().trim().isEmpty()) {
                loggedInEmail = userModel.getEmail().trim();
                return loggedInEmail;
            }
        } catch (Exception ignored) {}
        return "police1@lifelink.com";
    }

    public void loadProfile(Consumer<PoliceProfileModel> onSuccess, Runnable onNotFound) {
        String email = getEffectiveEmail();
        System.out.println("[PoliceProfileController] Loading profile for: " + email);

        dao.getPoliceProfile(email).thenAccept(model -> {
            Platform.runLater(() -> {
                if (model != null) {
                    onSuccess.accept(model);
                } else {
                    onNotFound.run();
                }
            });
        });
    }

    public void saveProfile(PoliceProfileModel model, Consumer<Boolean> onComplete) {
        String email = getEffectiveEmail();
        if (model.getEmail() == null || model.getEmail().trim().isEmpty()) {
            model.setEmail(email);
        }
        System.out.println("[PoliceProfileController] Saving profile for: " + model.getEmail());

        dao.savePoliceProfile(model).thenAccept(success -> {
            Platform.runLater(() -> onComplete.accept(success));
        });
    }
}