package com.kurukshetra.view.family;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.SetOptions;
import com.kurukshetra.config.FirebaseConfig;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Modern, Rich, Animated, and Clean Family Reviews & Feedback Center.
 * 100% Functional: Interactive 5-star rating selector, tag chips, photo
 * evidence attachment,
 * Firestore submission to 'FamilyUserReview' under the user's email, and
 * real-time saved review retrieval.
 */
public class FamilyReviewPage {

    private static final String PRIMARY = "#CA6721";
    private static final String PRIMARY_DARK = "#782F16";
    private static final String VERY_LIGHT_TERRACOTTA = "#FDF0E8";
    private static final String LIGHT_TERRACOTTA = "#F9E4D6";
    private static final String PAGE_BG = "#FCF9F7";
    private static final String SURFACE = "#FFFFFF";
    private static final String TEXT_PRIMARY = "#2B211D";
    private static final String TEXT_SECONDARY = "#675B55";
    private static final String TEXT_MUTED = "#938780";
    private static final String BORDER_COLOR = "#EDE0D8";
    private static final String GREEN = "#16A34A";
    private static final String LIGHT_GREEN = "#E8F5EC";
    private static final String RED = "#BA3B3E";

    private static final String FONT_FAMILY = "-fx-font-family: 'Segoe UI', -apple-system, system-ui, sans-serif; ";

    public static String USER_EMAIL = "rutu@gmail.com";

    private int selectedStarRating = 5;
    private String uploadedPhotoUrl = "";
    private Label ratingDescLabel;
    private Label statusMsgLbl;
    private ProgressIndicator submitSpinner;
    private VBox existingReviewsDisplayBox;
    private StackPane photoPreviewFrame;
    private ImageView photoThumbnailView;
    private Label photoFileNameLbl;

    public BorderPane setBorderPane(Stage stage) {
        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: " + PAGE_BG + "; " + FONT_FAMILY);

        VBox sidebar = Sidebar.build(stage, Sidebar.Page.REVIEWS);
        bp.setLeft(sidebar);

        VBox mainContent = buildReviewContent(stage);
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle(
                "-fx-background: " + PAGE_BG + ";" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: transparent;" +
                        "-fx-padding: 0;");
        bp.setCenter(scrollPane);

        MedicalReports.playPageAnimation(scrollPane);

        return bp;
    }

    private VBox buildReviewContent(Stage stage) {
        String activeEmail = FamilyHomePage.USER_EMAIL != null ? FamilyHomePage.USER_EMAIL : USER_EMAIL;

        // Header Section
        VBox titleBox = new VBox(2);
        Text headerTitle = new Text("Share Your Family Care Experience");
        headerTitle
                .setStyle(FONT_FAMILY + "-fx-font-size: 24px; -fx-font-weight: 800; -fx-fill: " + TEXT_PRIMARY + ";");

        // Label headerSub = new Label("Help enhance emergency coordination and hospital
        // response across the LifeLink network.");
        // headerSub.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " +
        // TEXT_SECONDARY + ";");
        titleBox.getChildren().addAll(headerTitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox userBadge = new HBox(6);
        userBadge.setAlignment(Pos.CENTER);
        userBadge.setPadding(new Insets(6, 12, 6, 12));
        userBadge.setStyle(
                "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;");
        // Label userIcon = new Label("👤");
        // userIcon.setStyle("-fx-font-size: 11px;");
        // Label userEmailLbl = new Label("Logged in as: " + activeEmail);
        // userEmailLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-font-weight:
        // bold; -fx-text-fill: " + PRIMARY_DARK + ";");
        // userBadge.getChildren().addAll(userIcon, userEmailLbl);

        HBox topBar = new HBox(16, titleBox, spacer);
        topBar.setAlignment(Pos.CENTER_LEFT);

        // 1. Review Submission Form Card
        VBox formCard = new VBox(16);
        formCard.setPadding(new Insets(24));
        formCard.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(120,47,22,0.05), 14, 0, 0, 3);");

        Label formTitle = new Label("Rate & Review LifeLink Services");
        formTitle.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");

        // Star Rating Row with dynamic text
        VBox starBlock = new VBox(6);
        HBox starRow = new HBox(12);
        starRow.setAlignment(Pos.CENTER_LEFT);

        Label starLabel = new Label("Select Overall Rating: ");
        starLabel.setStyle(
                FONT_FAMILY + "-fx-font-size: 12.5px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_PRIMARY + ";");

        HBox starsContainer = new HBox(6);
        Button[] starButtons = new Button[5];
        for (int i = 0; i < 5; i++) {
            final int ratingVal = i + 1;
            Button starBtn = new Button("★");
            starBtn.setStyle(FONT_FAMILY
                    + "-fx-font-size: 24px; -fx-background-color: transparent; -fx-text-fill: #EAB308; -fx-cursor: hand; -fx-padding: 0 2;");
            starBtn.setOnAction(e -> {
                selectedStarRating = ratingVal;
                updateStarButtonsStyle(starButtons, selectedStarRating);
                // updateRatingDescription(selectedStarRating);
            });
            starButtons[i] = starBtn;
            starsContainer.getChildren().add(starBtn);
        }

        starRow.getChildren().addAll(starLabel, starsContainer);

        // ratingDescLabel = new Label("⭐⭐⭐⭐⭐ Exceptional — Rapid Response &
        // Professional Coordination");
        // ratingDescLabel.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight:
        // bold; -fx-text-fill: " + PRIMARY + ";");
        updateStarButtonsStyle(starButtons, 5);
        starBlock.getChildren().addAll(starRow);

        // Feedback Text Area
        VBox commentBox = new VBox(8);
        Label commentLbl = new Label("Your Feedback & Incident Details:");
        commentLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        TextArea commentArea = new TextArea();
        commentArea.setPromptText(
                "Describe your experience with ambulance routing, hospital admission, Sister Ananya AI nurse, or emergency dispatch...");
        commentArea.setPrefRowCount(4);
        commentArea.setWrapText(true);
        commentArea.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: #FFFFFF;" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-padding: 8px;" +
                        "-fx-font-size: 12.5px;" +
                        "-fx-line-spacing: 2px;");

        // Quick Tag Suggestions
        HBox tagRow = new HBox(8);
        tagRow.setAlignment(Pos.CENTER_LEFT);
        Label tagPrompt = new Label("Quick Suggestions:");
        tagPrompt.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        Button tag1 = createSuggestionTag("⚡ Fast Dispatch", commentArea);
        Button tag2 = createSuggestionTag("🏥 Clean ICU Facility", commentArea);
        Button tag3 = createSuggestionTag("👨‍⚕️ Compassionate Doctors", commentArea);
        Button tag4 = createSuggestionTag("🧭 Accurate GPS Navigation", commentArea);
        tagRow.getChildren().addAll(tagPrompt, tag1, tag2, tag3, tag4);

        commentBox.getChildren().addAll(commentLbl, commentArea, tagRow);

        // Photo Upload Option
        VBox photoBox = new VBox(8);
        Label photoLbl = new Label("Attach Photo / Incident Evidence (Optional):");
        photoLbl.setStyle(
                FONT_FAMILY + "-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT_SECONDARY + ";");

        HBox uploadRow = new HBox(14);
        uploadRow.setAlignment(Pos.CENTER_LEFT);

        Button chooseFileBtn = new Button("📁 Choose Image");
        chooseFileBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 11.5px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 7px 16px;" +
                        "-fx-cursor: hand;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 8px;");

        photoFileNameLbl = new Label("No file chosen");
        photoFileNameLbl.setStyle(FONT_FAMILY + "-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        // Thumbnail Preview Frame
        photoPreviewFrame = new StackPane();
        photoPreviewFrame.setPrefSize(70, 50);
        photoPreviewFrame.setMaxSize(70, 50);
        photoPreviewFrame.setVisible(false);
        photoPreviewFrame.setManaged(false);
        photoPreviewFrame.setStyle("-fx-background-color: #F3F4F6; -fx-border-color: " + BORDER_COLOR
                + "; -fx-border-radius: 6px; -fx-background-radius: 6px;");

        photoThumbnailView = new ImageView();
        photoThumbnailView.setFitWidth(66);
        photoThumbnailView.setFitHeight(46);
        photoThumbnailView.setPreserveRatio(true);
        Rectangle clip = new Rectangle(66, 46);
        clip.setArcWidth(6);
        clip.setArcHeight(6);
        photoThumbnailView.setClip(clip);
        photoPreviewFrame.getChildren().add(photoThumbnailView);

        chooseFileBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Review Photo / Document");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                uploadedPhotoUrl = selectedFile.toURI().toString();
                photoFileNameLbl.setText(selectedFile.getName());
                photoThumbnailView.setImage(new Image(uploadedPhotoUrl, 66, 46, true, true));
                photoPreviewFrame.setVisible(true);
                photoPreviewFrame.setManaged(true);
            }
        });

        uploadRow.getChildren().addAll(chooseFileBtn, photoFileNameLbl, photoPreviewFrame);
        photoBox.getChildren().addAll(photoLbl, uploadRow);

        // Submit Button & Status
        HBox submitRow = new HBox(14);
        submitRow.setAlignment(Pos.CENTER_LEFT);

        Button submitBtn = new Button("Submit Verified Review");
        submitBtn.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: linear-gradient(to bottom right, " + PRIMARY + ", " + PRIMARY_DARK + ");"
                        +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 8px;" +
                        "-fx-padding: 10px 22px;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.25), 8, 0, 0, 2);");

        submitSpinner = new ProgressIndicator();
        submitSpinner.setPrefSize(20, 20);
        submitSpinner.setVisible(false);

        statusMsgLbl = new Label();

        submitBtn.setOnAction(e -> {
            String comment = commentArea.getText().trim();
            if (comment.isEmpty()) {
                statusMsgLbl.setText("⚠ Please write a review before submitting.");
                statusMsgLbl.setStyle(
                        FONT_FAMILY + "-fx-text-fill: " + RED + "; -fx-font-weight: bold; -fx-font-size: 11.5px;");
                return;
            }

            submitSpinner.setVisible(true);
            submitBtn.setDisable(true);
            submitReviewToFirestore(activeEmail, selectedStarRating, comment, uploadedPhotoUrl, submitBtn);
        });

        submitRow.getChildren().addAll(submitBtn, submitSpinner, statusMsgLbl);

        formCard.getChildren().addAll(formTitle, starBlock, commentBox, photoBox, submitRow);

        // 2. Previously Submitted Review Card (View Section)
        VBox historyCard = new VBox(14);
        historyCard.setPadding(new Insets(24));
        historyCard.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + SURFACE + ";" +
                        "-fx-border-color: " + BORDER_COLOR + ";" +
                        "-fx-border-radius: 16px;" +
                        "-fx-background-radius: 16px;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(120,47,22,0.05), 14, 0, 0, 3);");

        HBox histHeader = new HBox(8);
        histHeader.setAlignment(Pos.CENTER_LEFT);
        Label historyTitle = new Label("Your Saved Verified Review Record");
        historyTitle.setStyle(
                FONT_FAMILY + "-fx-font-size: 16px; -fx-font-weight: 800; -fx-text-fill: " + TEXT_PRIMARY + ";");
        histHeader.getChildren().add(historyTitle);

        existingReviewsDisplayBox = new VBox(10);
        existingReviewsDisplayBox.getChildren().add(new Label("Loading your review record from Firestore..."));

        loadExistingReviewFromFirestore(activeEmail);

        historyCard.getChildren().addAll(histHeader, existingReviewsDisplayBox);

        VBox contentLayout = new VBox(20, topBar, formCard, historyCard);
        contentLayout.setPadding(new Insets(20, 24, 24, 24));
        contentLayout.setMaxWidth(Double.MAX_VALUE);

        return contentLayout;
    }

    private Button createSuggestionTag(String text, TextArea targetArea) {
        Button tag = new Button(text);
        tag.setStyle(
                FONT_FAMILY +
                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                        "-fx-text-fill: " + PRIMARY_DARK + ";" +
                        "-fx-font-size: 10.5px;" +
                        "-fx-font-weight: 600;" +
                        "-fx-background-radius: 12px;" +
                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                        "-fx-border-radius: 12px;" +
                        "-fx-cursor: hand;" +
                        "-fx-padding: 3 8;");
        tag.setOnAction(e -> {
            String current = targetArea.getText().trim();
            if (current.isEmpty()) {
                targetArea.setText(text.replaceAll("[^a-zA-Z0-9\\s]", "").trim() + ". ");
            } else {
                targetArea.setText(current + " " + text.replaceAll("[^a-zA-Z0-9\\s]", "").trim() + ". ");
            }
            targetArea.positionCaret(targetArea.getText().length());
        });
        return tag;
    }

    // private void updateRatingDescription(int rating) {
    // switch (rating) {
    // case 5 -> ratingDescLabel.setText("⭐⭐⭐⭐⭐ Exceptional — Rapid Response &
    // Professional Coordination");
    // case 4 -> ratingDescLabel.setText("⭐⭐⭐⭐ Very Good — Responsive Emergency
    // Hospital Care");
    // case 3 -> ratingDescLabel.setText("⭐⭐⭐ Satisfactory — Met Essential First-Aid
    // Needs");
    // case 2 -> ratingDescLabel.setText("⭐⭐ Needs Improvement — Experiencing
    // Transit or Facility Delays");
    // case 1 -> ratingDescLabel.setText("⭐ Critical Concerns — Required Urgent
    // System Intervention");
    // default -> ratingDescLabel.setText("⭐⭐⭐⭐⭐ Select your rating above");
    // }
    // }

    private void updateStarButtonsStyle(Button[] buttons, int rating) {
        for (int i = 0; i < buttons.length; i++) {
            if (i < rating) {
                buttons[i].setStyle(FONT_FAMILY
                        + "-fx-font-size: 24px; -fx-background-color: transparent; -fx-text-fill: #EAB308; -fx-cursor: hand; -fx-padding: 0 2;");
            } else {
                buttons[i].setStyle(FONT_FAMILY
                        + "-fx-font-size: 24px; -fx-background-color: transparent; -fx-text-fill: #CBD5E1; -fx-cursor: hand; -fx-padding: 0 2;");
            }
        }
    }

    private void submitReviewToFirestore(String email, int rating, String comment, String photoUrl, Button submitBtn) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db != null) {
                    Map<String, Object> reviewData = new HashMap<>();
                    reviewData.put("email", email);
                    reviewData.put("rating", rating);
                    reviewData.put("comment", comment);
                    reviewData.put("photoUrl", photoUrl != null ? photoUrl : "");
                    reviewData.put("timestamp", Timestamp.now());

                    db.collection("FamilyUserReview").document(email).set(reviewData, SetOptions.merge()).get();

                    Platform.runLater(() -> {
                        submitSpinner.setVisible(false);
                        submitBtn.setDisable(false);
                        statusMsgLbl.setText("✓ Review successfully saved to LifeLink Network!");
                        statusMsgLbl.setStyle(FONT_FAMILY + "-fx-text-fill: " + GREEN
                                + "; -fx-font-weight: bold; -fx-font-size: 11.5px;");
                        loadExistingReviewFromFirestore(email);
                    });
                }
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    submitSpinner.setVisible(false);
                    submitBtn.setDisable(false);
                    statusMsgLbl.setText("Error saving review: " + ex.getMessage());
                    statusMsgLbl.setStyle(
                            FONT_FAMILY + "-fx-text-fill: " + RED + "; -fx-font-weight: bold; -fx-font-size: 11.5px;");
                });
            }
        }).start();
    }

    private void loadExistingReviewFromFirestore(String email) {
        new Thread(() -> {
            try {
                Firestore db = FirebaseConfig.getFirestore();
                if (db == null)
                    return;

                ApiFuture<DocumentSnapshot> future = db.collection("FamilyUserReview").document(email).get();
                DocumentSnapshot doc = future.get();

                Platform.runLater(() -> {
                    existingReviewsDisplayBox.getChildren().clear();
                    if (doc.exists()) {
                        Long rating = doc.getLong("rating");
                        String comment = doc.getString("comment");
                        String photoUrl = doc.getString("photoUrl");
                        Timestamp ts = doc.getTimestamp("timestamp");

                        VBox card = new VBox(10);
                        card.setPadding(new Insets(16));
                        card.setStyle(
                                FONT_FAMILY +
                                        "-fx-background-color: " + VERY_LIGHT_TERRACOTTA + ";" +
                                        "-fx-border-color: " + LIGHT_TERRACOTTA + ";" +
                                        "-fx-border-radius: 12px;" +
                                        "-fx-background-radius: 12px;" +
                                        "-fx-effect: dropshadow(three-pass-box, rgba(202,103,33,0.08), 8, 0, 0, 2);");

                        HBox topRow = new HBox(10);
                        topRow.setAlignment(Pos.CENTER_LEFT);

                        int starCount = rating != null ? rating.intValue() : 5;
                        HBox starsBox = new HBox(2);
                        for (int s = 0; s < starCount; s++) {
                            Label st = new Label("⭐");
                            st.setStyle("-fx-font-size: 14px;");
                            starsBox.getChildren().add(st);
                        }

                        Label verifiedBadge = new Label("✓ Verified Family Submission");
                        verifiedBadge.setStyle(
                                FONT_FAMILY +
                                        "-fx-background-color: " + LIGHT_GREEN + ";" +
                                        "-fx-text-fill: " + GREEN + ";" +
                                        "-fx-font-size: 9.5px;" +
                                        "-fx-font-weight: bold;" +
                                        "-fx-padding: 3 8;" +
                                        "-fx-background-radius: 8px;");

                        Region sp = new Region();
                        HBox.setHgrow(sp, Priority.ALWAYS);

                        Label timeLbl = new Label(ts != null ? ts.toDate().toString() : "Recently submitted");
                        timeLbl.setStyle(FONT_FAMILY + "-fx-font-size: 10.5px; -fx-text-fill: " + TEXT_MUTED + ";");

                        topRow.getChildren().addAll(starsBox, verifiedBadge, sp, timeLbl);

                        Label commentTxt = new Label("\"" + (comment != null ? comment : "") + "\"");
                        commentTxt.setWrapText(true);
                        commentTxt.setStyle(FONT_FAMILY + "-fx-font-size: 13px; -fx-text-fill: " + TEXT_PRIMARY
                                + "; -fx-font-style: italic; -fx-line-spacing: 2px;");

                        card.getChildren().addAll(topRow, commentTxt);

                        if (photoUrl != null && !photoUrl.isEmpty()) {
                            try {
                                ImageView iv = new ImageView(new Image(photoUrl, true));
                                iv.setFitWidth(140);
                                iv.setFitHeight(95);
                                iv.setPreserveRatio(true);

                                Rectangle clip = new Rectangle(140, 95);
                                clip.setArcWidth(8);
                                clip.setArcHeight(8);
                                iv.setClip(clip);

                                card.getChildren().add(iv);
                            } catch (Exception ignored) {
                            }
                        }

                        existingReviewsDisplayBox.getChildren().add(card);

                        FadeTransition ft = new FadeTransition(Duration.millis(250), card);
                        ft.setFromValue(0.3);
                        ft.setToValue(1.0);
                        ft.play();

                    } else {
                        Label noRev = new Label("No verified review record submitted yet for " + email + ".");
                        noRev.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
                        existingReviewsDisplayBox.getChildren().add(noRev);
                    }
                });

            } catch (Exception e) {
                Platform.runLater(() -> {
                    existingReviewsDisplayBox.getChildren().clear();
                    Label errLbl = new Label("Unable to load review record at this moment.");
                    errLbl.setStyle(FONT_FAMILY + "-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
                    existingReviewsDisplayBox.getChildren().add(errLbl);
                });
            }
        }).start();
    }
}