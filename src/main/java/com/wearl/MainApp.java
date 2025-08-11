package com.wearl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainApp extends Application {

    private TextArea inputTextArea;
    private TextArea outputTextArea;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AI-Futuristic Text Case Converter || By Saurabhh");
        primaryStage.getIcons().add(
            new Image(getClass().getResourceAsStream("/images/ai_logo.png"))
        );


        // Input TextArea
        inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter your text here...");
        inputTextArea.setWrapText(true);

        // Output TextArea
        outputTextArea = new TextArea();
        outputTextArea.setPromptText("Converted text will appear here...");
        outputTextArea.setWrapText(true);
        outputTextArea.setEditable(false);

        // Buttons
        Button toUpper = new Button("To Uppercase");
        toUpper.setOnAction(e -> convertToUpper());

        Button toLower = new Button("To Lowercase");
        toLower.setOnAction(e -> convertToLower());

        Button toSentence = new Button("Sentence Case");
        toSentence.setOnAction(e -> convertToSentence());

        Button toCapitalized = new Button("Capitalized Case");
        toCapitalized.setOnAction(e -> convertToCapitalized());

        Button toTitle = new Button("Title Case");
        toTitle.setOnAction(e -> convertToTitle());

        Button toAlternating = new Button("Alternating Case");
        toAlternating.setOnAction(e -> convertToAlternating());

        Button toInverse = new Button("Inverse Case");
        toInverse.setOnAction(e -> convertToInverse());

        Button copyButton = new Button("Copy to Clipboard");
        copyButton.setOnAction(e -> copyToClipboard());

        Button saveButton = new Button("Save to File");
        saveButton.setOnAction(e -> saveToFile(primaryStage));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> clearText());

        // Layout for buttons
        HBox buttonBox = new HBox(10, toUpper, toLower, toSentence, toCapitalized, toTitle, toAlternating, toInverse);
        buttonBox.setStyle("-fx-alignment: center;");

        HBox actionBox = new HBox(10, copyButton, saveButton, clearButton);
        actionBox.setStyle("-fx-alignment: center;");

        // Main layout
        VBox root = new VBox(20, inputTextArea, buttonBox, outputTextArea, actionBox);
        root.setStyle("-fx-padding: 20; -fx-background-color: #1E1E1E;");

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void convertToUpper() {
        outputTextArea.setText(inputTextArea.getText().toUpperCase());
    }

    private void convertToLower() {
        outputTextArea.setText(inputTextArea.getText().toLowerCase());
    }

    private void convertToSentence() {
        String text = inputTextArea.getText().toLowerCase();
        String[] sentences = text.split("(?<=\\.\\s)");
        StringBuilder sb = new StringBuilder();
        for (String sentence : sentences) {
            if (!sentence.isEmpty()) {
                sb.append(Character.toUpperCase(sentence.charAt(0)) + sentence.substring(1));
            }
        }
        outputTextArea.setText(sb.toString());
    }

    private void convertToCapitalized() {
        String text = inputTextArea.getText().toLowerCase();
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)) + word.substring(1) + " ");
            }
        }
        outputTextArea.setText(sb.toString().trim());
    }

    private void convertToTitle() {
        // Similar to capitalized, but handle minor words if needed. For simplicity, same as capitalized.
        convertToCapitalized();
    }

    private void convertToAlternating() {
        String text = inputTextArea.getText();
        StringBuilder sb = new StringBuilder();
        boolean upper = true;
        for (char c : text.toCharArray()) {
            sb.append(upper ? Character.toUpperCase(c) : Character.toLowerCase(c));
            upper = !upper;
        }
        outputTextArea.setText(sb.toString());
    }

    private void convertToInverse() {
        String text = inputTextArea.getText();
        StringBuilder sb = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append(Character.toLowerCase(c));
            } else if (Character.isLowerCase(c)) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
        }
        outputTextArea.setText(sb.toString());
    }

    private void copyToClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(outputTextArea.getText());
        clipboard.setContent(content);
    }

    private void saveToFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Converted Text");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(outputTextArea.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearText() {
        inputTextArea.clear();
        outputTextArea.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}