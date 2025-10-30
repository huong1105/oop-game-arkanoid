package com.arkanoid.ui;

import com.arkanoid.Const;
import com.arkanoid.game.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.arkanoid.game.FontManager.*;

/**
 * Lớp này quản lý việc khởi tạo và cập nhật
 * các thành phần UI trong cột thông tin (HUD).
 */
public class HudManager {

    private Label scoreValueLabel;
    private Label levelValueLabel;
    private HBox livesIconBox;
    private final Pane infoPanel;

    public HudManager(Pane infoPanel) {
        this.infoPanel = infoPanel;
        setupInfoPanel();
    }

    /**
     * Cập nhật các giá trị trên HUD (điểm, màn, mạng)
     * Được gọi mỗi frame từ AnimationTimer.
     */
    public void update(GameManager gm) {
        // Cập nhật điểm
        scoreValueLabel.setText(String.format("%,d", gm.getScore()));

        // Cập nhật màn
        levelValueLabel.setText(String.valueOf(gm.getCurrentLevel()));

        // Cập nhật icon mạng sống
        int currentLives = gm.getLives();
        int currentIcons = livesIconBox.getChildren().size();

        if (currentLives > currentIcons) {
            // Thêm icon
            for (int i = 0; i < (currentLives - currentIcons); i++) {
                livesIconBox.getChildren().add(createLifeIcon());
            }
        } else if (currentLives < currentIcons) {
            // Bớt icon
            for (int i = 0; i < (currentIcons - currentLives); i++) {
                if (!livesIconBox.getChildren().isEmpty()) {
                    livesIconBox.getChildren().remove(livesIconBox.getChildren().size() - 1);
                }
            }
        }
    }

    /**
     * Khởi tạo các thành phần UI cho infoPanel
     */
    private void setupInfoPanel() {
        VBox hudBox = new VBox();
        hudBox.setPrefWidth(Const.INFO_PANEL_WIDTH);
        hudBox.setStyle("-fx-background-color: #000000;"); // Đồng bộ với nền
        hudBox.setAlignment(Pos.TOP_CENTER);
        hudBox.setPadding(new Insets(20));
        hudBox.setSpacing(25); // Khoảng cách giữa các mục

        // Tiêu đề game
        Label title = createHudLabel("ARKANOID", 20);
        title.setTextFill(Color.GREEN);

        // Điểm số (Score)
        Label scoreLabel = createHudLabel("SCORE", 16);
        scoreValueLabel = createHudLabel("0", 20); // Dùng biến instance

        // Màn chơi (Level)
        Label levelLabel = createHudLabel("LEVEL", 16);
        levelValueLabel = createHudLabel("1", 20); // Dùng biến instance

        // Mạng (Lives)
        Label livesLabel = createHudLabel("LIVES", 16);
        livesIconBox = new HBox(10); // HBox để chứa các icon mạng
        livesIconBox.setAlignment(Pos.CENTER);

        // Thêm tất cả vào VBox
        hudBox.getChildren().addAll(title,
                new Separator(),
                scoreLabel,
                scoreValueLabel,
                new Separator(),
                levelLabel,
                levelValueLabel,
                new Separator(),
                livesLabel,
                livesIconBox);

        // Thêm VBox vào infoPanel
        infoPanel.getChildren().add(hudBox);
    }

    // --- Phương thức trợ giúp ---
    private Label createHudLabel(String text, int fontSize) {
        Label label = new Label(text);
        if (fontSize == 24) {
            label.setFont(PAPYRUS_24);
        } else if (fontSize == 20) {
            label.setFont(PAPYRUS_20);
        } else { // Mặc định là 16
            label.setFont(PAPYRUS_16);
        }
        label.setTextFill(Color.WHITE);
        return label;
    }

    private Rectangle createLifeIcon() {
        Rectangle life = new Rectangle(40, 10); // Kích thước 40x10
        life.setFill(Color.GREEN);
        life.setStroke(Color.LIGHTGREEN);
        life.setArcWidth(5);
        life.setArcHeight(5);
        return life;
    }

    /**
     * Một lớp nội bộ đơn giản để tạo đường kẻ ngang
     */
    private static class Separator extends Region {
        Separator() {
            setPrefHeight(1);
            setStyle("-fx-background-color: #555555;");
            setMaxWidth(Double.MAX_VALUE);
        }
    }
}