package com.arkanoid.ui;

import com.arkanoid.Const;
import com.arkanoid.entities.PowerUp;
import com.arkanoid.game.GameManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private VBox activeEffectsBox;
    private Map<PowerUp, HBox> activePowerUpNodes = new HashMap<>();

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
        updateLivesIcons(gm.getLives());

        updateActiveEffects(gm.getPowerUps());
    }

    /**
     * Khởi tạo các thành phần UI cho infoPanel
     */
    private void setupInfoPanel() {
        VBox hudBox = new VBox();
        hudBox.setPrefWidth(Const.INFO_PANEL_WIDTH);

        hudBox.setStyle("-fx-background-color: #000000;");

        hudBox.setAlignment(Pos.TOP_CENTER);
        hudBox.setPadding(new Insets(20));
        hudBox.setSpacing(25); // Khoảng cách giữa các mục

        Label title = createHudLabel("ARKANOID", 20);
        title.setTextFill(Color.GREEN);
        Label scoreLabel = createHudLabel("SCORE", 16);
        scoreValueLabel = createHudLabel("0", 20);
        Label levelLabel = createHudLabel("LEVEL", 16);
        levelValueLabel = createHudLabel("1", 20);
        Label livesLabel = createHudLabel("LIVES", 16);
        livesIconBox = new HBox(10);
        livesIconBox.setAlignment(Pos.CENTER);

        Label effectsLabel = createHudLabel("EFFECTS", 16);
        activeEffectsBox = new VBox(5);
        activeEffectsBox.setAlignment(Pos.CENTER_LEFT);
        activeEffectsBox.setPadding(new Insets(0, 0, 0, 20));

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
                livesIconBox,
                new Separator(), // <-- THÊM MỚI
                effectsLabel,    // <-- THÊM MỚI
                activeEffectsBox // <-- THÊM MỚI
        );

        // Thêm VBox vào infoPanel
        infoPanel.getChildren().add(hudBox);
    }

    private void updateLivesIcons(int currentLives) {
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



    private void updateActiveEffects(List<PowerUp> allPowerUps) {

        //Tạo danh sách các power-up đang hoạt động
        List<PowerUp> activeList = new ArrayList<>();
        for (PowerUp p : allPowerUps) {
            if (p.isActivated()) {
                activeList.add(p);
            }
        }

        //Xóa UI của các power-up đã hết hạn
        List<PowerUp> toRemove = new ArrayList<>();
        for (PowerUp p_ui : activePowerUpNodes.keySet()) {
            if (!activeList.contains(p_ui)) {
                // Power-up này không còn hoạt động, xóa nó khỏi VBox
                activeEffectsBox.getChildren().remove(activePowerUpNodes.get(p_ui));
                toRemove.add(p_ui); // Thêm vào danh sách chờ xóa khỏi Map
            }
        }
        // Xóa khỏi Map
        toRemove.forEach(activePowerUpNodes::remove);

        //Thêm UI cho power-up mới và cập nhật timer
        for (PowerUp p_game : activeList) {
            if (!activePowerUpNodes.containsKey(p_game)) {
                HBox effectRow = createEffectRow(p_game);
                activeEffectsBox.getChildren().add(effectRow);
                activePowerUpNodes.put(p_game, effectRow);
            } else {
                HBox effectRow = activePowerUpNodes.get(p_game);
                Label timerLabel = (Label) effectRow.getChildren().get(1);

                if (p_game.getDurationSeconds() > 0) {
                    timerLabel.setText(String.format("%.1fs", p_game.getTimeRemaining()));
                } else {
                    timerLabel.setText("");
                }
            }
        }
    }


    private HBox createEffectRow(PowerUp p) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(p.getSprite());
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        Label timerLabel = createHudLabel("", 20);
        timerLabel.setTextFill(Color.CYAN); // Màu khác biệt

        if (p.getDurationSeconds() > 0) {
            timerLabel.setText(String.format("%.1fs", p.getTimeRemaining()));
        } else {
            timerLabel.setText("");
        }

        row.getChildren().addAll(icon, timerLabel);
        return row;
    }


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

    private static class Separator extends Region {
        Separator() {
            setPrefHeight(1);
            setStyle("-fx-background-color: #555555;");
            setMaxWidth(Double.MAX_VALUE);
        }
    }
}
