package com.arkanoid.entities;

package com.arkanoid.powerups;

import com.arkanoid.GameController;
import com.arkanoid.Paddle;

public class ExpandPaddlePowerUp extends PowerUp {
    private double factor;
// ttestts
    /**
     * @param x vị trí spawn (x)
     * @param y vị trí spawn (y)
     * @param factor hệ số mở rộng (ví dụ 1.5 = tăng 50%)
     * @param durationMillis thời gian hiệu lực (ms)
     */
    public ExpandPaddlePowerUp(double x, double y, double factor, long durationMillis) {
        super(x, y, Type.EXPAND_PADDLE, durationMillis);
        this.factor = factor;
    }

    @Override
    public void applyEffect(GameController controller) {
        Paddle paddle = controller.getPaddle();
        // add modifier để mở rộng paddle
        paddle.addWidthModifier(getId(), factor);
    }

    @Override
    public void removeEffect(GameController controller) {
        Paddle paddle = controller.getPaddle();
        // gỡ modifier đã thêm khi hết hiệu lực
        paddle.removeWidthModifier(getId());
    }
}

