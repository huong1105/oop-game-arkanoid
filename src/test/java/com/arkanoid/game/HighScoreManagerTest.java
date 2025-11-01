package com.arkanoid.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HighScoreManagerTest {

    private HighScoreManager highScoreManager;

    @BeforeEach
    void setUp() {
        highScoreManager = new HighScoreManager();
        highScoreManager.getHighScores().clear();
    }

    @Test
    void testScoresAreSortedDescending() {
        highScoreManager.addScore(100);
        highScoreManager.addScore(500);
        highScoreManager.addScore(50);
        List<Integer> scores = highScoreManager.getHighScores();
        assertEquals(3, scores.size(), "Phải có 3 điểm số");
        assertEquals(500, scores.get(0), "Điểm cao nhất (500) phải ở vị trí đầu tiên");
        assertEquals(100, scores.get(1), "Điểm 100 phải ở vị trí thứ hai");
        assertEquals(50, scores.get(2), "Điểm thấp nhất (50) phải ở vị trí cuối cùng");
    }

    @Test
    void testScoreLimitIsFive() {
        highScoreManager.addScore(100); // Sẽ là vị trí #2
        highScoreManager.addScore(200); // Sẽ là vị trí #1
        highScoreManager.addScore(50);  // Sẽ là vị trí #4
        highScoreManager.addScore(25);  // Sẽ là vị trí #5
        highScoreManager.addScore(75);  // Sẽ là vị trí #3
        highScoreManager.addScore(10);
        List<Integer> scores = highScoreManager.getHighScores();

        assertEquals(5, scores.size(), "Danh sách chỉ nên giữ 5 điểm cao nhất");
        assertEquals(200, scores.get(0), "Điểm cao nhất (200) phải là đầu tiên");
        assertEquals(25, scores.get(4), "Điểm thấp nhất (25) phải là cuối cùng");
        assertFalse(scores.contains(10), "Điểm 10 (thấp nhất) lẽ ra phải bị loại bỏ");
    }
}