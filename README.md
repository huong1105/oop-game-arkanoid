# 🎮 Arkanoid - Bài tập lớn Lập trình Hướng đối tượng

Chào mừng đến với dự án game Arkanoid!

Đây là một dự án được phát triển bằng Java và thư viện JavaFX, được thực hiện như một bài tập lớn cho môn Lập trình Hướng đối tượng (OOP).

Mục tiêu chính của dự án này là áp dụng các nguyên tắc cốt lõi của OOP (như Đóng gói, Kế thừa, Đa hình, Trừu tượng) để xây dựng một trò chơi hoàn chỉnh, có cấu trúc tốt, dễ bảo trì và mở rộng.
Mã UML của dự án: https://drive.google.com/drive/folders/1ADVP8I3q-8XWJMCVzgV21x1QjkvdzXiq?usp=drive_link

## ✨ Chi tiết về giao diện và tính năng

### 1. Hệ thống Giao diện (GUI)

* **Menu đầy đủ:** Game có hệ thống menu hoàn chỉnh bao gồm:
    * **MainMenu:** Bắt đầu game mới, Tiếp tục (vào màn chọn level), Điểm cao, Cài đặt, Thoát.
    * **LevelSelectionMenu:** Cho phép người chơi chọn các màn chơi đã được mở khóa.
    * **SettingsMenu:** Tùy chỉnh âm lượng nhạc nền (BGM) và hiệu ứng âm thanh (SFX).
    * **PauseMenu:** Tạm dừng, chơi tiếp, chơi lại màn, hoặc thoát về menu chính.
    * **Giao diện thông tin (HUD):** Cột thông tin bên phải màn hình (HudManager) hiển thị rõ ràng Điểm số, Mạng sống, và Màn chơi hiện tại.

* **Đồ họa & Âm thanh:**
    * Sử dụng hình ảnh (sprites) cho tất cả các đối tượng (Bóng, Paddle, Gạch, Power-up) do `SpriteManager` quản lý.
    * Hệ thống âm thanh (`SoundManager`) phát nhạc nền và các hiệu ứng âm thanh (SFX) khi bóng va chạm, phá gạch, hoặc nhặt power-up.

### 2. Gameplay nâng cao

* **Hệ thống 5 Màn chơi:** Game có 5 màn chơi được thiết kế sẵn với độ khó tăng dần.
* **Bảng xếp hạng:** Tự động lưu và hiển thị 5 điểm số cao nhất (`HighScoreManager`).
* **Các loại gạch đặc biệt:**
    * **Gạch thường (Normal):** Vỡ sau 1 lần va chạm.
    * **Gạch cứng (Strong):** Cần 2 lần va chạm (có hiệu ứng "nứt").
    * **Gạch đặc biệt (Special):** Cần 3 lần va chạm (có 2 hiệu ứng "nứt").
    * **Gạch nổ (Explosive):** Khi vỡ sẽ phá hủy các viên gạch xung quanh nó.
    * **Tường (Wall):** Không thể bị phá hủy.
* **Hệ thống Power-Up đa dạng:**
    * **ExpandPaddle:** Làm thanh đỡ (paddle) dài ra.
    * **FastBall:** Tăng tốc độ của bóng.
    * **MultiBall:** Nhân đôi tất cả bóng đang có trên màn hình.
    * **CannonPowerUp:** Paddle mọc ra 2 khẩu súng, cho phép bắn đạn phá gạch.
    * **FireBall:** Bóng biến thành "bóng lửa", phá hủy gạch ngay lập tức mà không nảy lại.
    * **FeverBall:** Kích hoạt "Fever Mode" (x2 điểm, nhưng cũng x2 hình phạt nếu mất bóng).
    * **Shield:** Tạo một tấm khiên ở đáy màn hình, cứu bóng khỏi bị rơi.

---

## 🏛️ Cách dự án áp dụng OOP (Thiết kế Hướng đối tượng)

1.  **Trừu tượng hóa (Abstraction)**
    * **Ý tưởng:** "Ẩn giấu sự phức tạp và chỉ đưa ra các tính năng cần thiết."
    * **Trong Code:**
        * Chúng ta có lớp `GameObject`. Đây là một lớp trừu tượng (abstract class) định nghĩa "ý tưởng" chung cho *mọi thứ* xuất hiện trong game.
        * Nó quy định rằng mọi vật thể (Bóng, Gạch, Paddle...) đều *phải* có vị trí (x, y), kích thước (width, height) và *phải* biết cách tự cập nhật trạng thái (`update()`) và tự vẽ mình lên màn hình (`render()`).
        * Chúng ta không cần quan tâm *chi tiết* làm sao `Ball` hay `Paddle` tự vẽ, chúng ta chỉ cần biết rằng chúng *có thể* làm được điều đó.

2.  **Kế thừa (Inheritance)**
    * **Ý tưởng:** "Tái sử dụng code bằng cách cho phép một lớp mới 'thừa hưởng' các đặc tính từ một lớp đã có."
    * **Trong Code:**
        * **`GameObject` (Lớp cha)**
            * ➡️ `MovableObject` (Lớp con - "thừa hưởng" `GameObject` và *thêm* vào khả năng di chuyển với `speedX`, `speedY`).
                * ➡️ `Ball` (thừa hưởng `MovableObject`).
                * ➡️ `Paddle` (thừa hưởng `MovableObject`).
                * ➡️ `PowerUp` (thừa hưởng `MovableObject` - vì power-up cũng di chuyển/rơi xuống).
            * ➡️ `Brick` (Lớp con - "thừa hưởng" `GameObject` - gạch không tự di chuyển nên không cần `MovableObject`).
                * ➡️ `NormalBrick`, `StrongBrick`, `SpecialBrick`... đều thừa hưởng từ `Brick`.
        * Nhờ vậy, chúng ta không phải viết lại code xử lý vị trí, kích thước, hay tốc độ cho từng đối tượng.

3.  **Đa hình (Polymorphism)**
    * **Ý tưởng:** "Cùng một hành động, nhưng các đối tượng khác nhau sẽ thực hiện theo cách khác nhau."
    * **Trong Code:**
        * Tất cả các đối tượng đều có phương thức `render()` (nhờ Kế thừa từ `GameObject`).
        * Nhưng khi gọi `render()`:
            * `Ball` sẽ vẽ ra hình ảnh quả bóng.
            * `Paddle` sẽ vẽ ra hình ảnh thanh đỡ.
            * `StrongBrick` sẽ vẽ ra hình ảnh viên gạch cứng (hoặc gạch bị nứt nếu đã va chạm).
        * Tương tự, mọi Power-up đều có phương thức `applyEffect()`.
        * Khi gọi `applyEffect()`, `ExpandPaddlePowerUp` sẽ làm paddle *dài ra*, trong khi `CannonPowerUp` sẽ cho paddle *khả năng bắn súng*.

4.  **Đóng gói (Encapsulation)**
    * **Ý tưởng:** "Che giấu dữ liệu và logic xử lý bên trong một đối tượng, chỉ cho phép tương tác qua các phương thức (hàm) công khai."
    * **Trong Code:**
        * Lớp `Paddle` tự quản lý `speed` (tốc độ) của nó. Các lớp khác không thể tự ý thay đổi tốc độ này.
        * Lớp `GameManager` là ví dụ rõ nhất. Nó giống như "bộ não" của game, đóng gói toàn bộ logic chính: vòng lặp game (`AnimationTimer`), xử lý va chạm, quản lý điểm số, mạng sống, và trạng thái game (`GameState`). Các lớp khác không cần biết về những logic phức tạp này.

---

## 🧩 Các Mẫu thiết kế (Design Patterns) đã dùng

Dự án này cũng áp dụng một số mẫu thiết kế nâng cao để giúp code sạch sẽ hơn:

* **Singleton:**
    * **Mô tả:** Đảm bảo một lớp chỉ có *duy nhất một* thể hiện (instance) và cung cấp một điểm truy cập toàn cục đến nó.
    * **Trong Code:** `GameManager`, `SoundManager`, `SpriteManager`, `GameSettings`... đều là Singleton. Vì cả game chỉ cần *một* bộ não (`GameManager`) hoặc *một* bộ quản lý âm thanh (`SoundManager`).
* **Object Pool (Hồ chứa đối tượng):**
    * **Mô tả:** Tái sử dụng các đối tượng thay vì tạo mới và hủy bỏ liên tục, giúp tiết kiệm tài nguyên và tăng hiệu năng (tránh "lag" do trình dọn rác).
    * **Trong Code:** `PowerUpPool`, `CannonShotPool`, `FireWorkEffectPool`. Khi một viên đạn (`CannonShot`) bay ra khỏi màn hình, nó không bị "hủy", mà được "trả về hồ" để tái sử dụng cho lần bắn tiếp theo.

---

## 🛠️ Công nghệ sử dụng

* **Ngôn ngữ:** Java (Dự án được viết với JDK 21)
* **Thư viện:** JavaFX (Dùng để xử lý đồ họa, giao diện người dùng, âm thanh, và vòng lặp game)
