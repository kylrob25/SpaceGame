package me.krob.spacegame.handler;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import me.krob.spacegame.drawable.object.GameObjectType;
import me.krob.spacegame.drawable.object.model.Bullet;
import me.krob.spacegame.drawable.object.model.Joypad;
import me.krob.spacegame.drawable.object.model.Invader;
import me.krob.spacegame.drawable.object.model.Defender;
import me.krob.spacegame.view.SpaceGameView;

public class GameObjectHandler {
    private final Joypad joypad;

    private final Defender defender;
    private final Invader invader;

    private final List<Bullet> bullets = new ArrayList<>();
    private final List<Bullet> expiredBullets = new ArrayList<>();

    public GameObjectHandler(SpaceGameView view) {
        joypad = new Joypad(view);
        defender = new Defender(view);
        invader = new Invader(view);
    }

    public void update(long framesPerSecond) {
        defender.update(framesPerSecond);
        invader.update(framesPerSecond);

        bullets.forEach(bullet -> {
            if (bullet.isActive()) {
                bullet.update(framesPerSecond);
            }
        });

        // Make sure there aren't any memory leaks
        expiredBullets.removeIf(bullet -> {
            bullets.remove(bullet);
            return true;
        });
    }

    public void draw(Canvas canvas) {
        defender.draw(canvas);
        joypad.draw(canvas);
        invader.draw(canvas);

        bullets.forEach(bullet -> {
            if (bullet.isActive()) {
                bullet.draw(canvas);
            }
        });
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        expiredBullets.add(bullet);
    }

    public void onTouchEvent(MotionEvent motionEvent) {
        joypad.onTouchEvent(motionEvent);
    }

    public Defender getDefender() {
        return defender;
    }

    public Invader getInvader() {
        return invader;
    }

    public Joypad getJoypad() {
        return joypad;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Stream<Bullet> getBulletsByOwner(GameObjectType type) {
        return bullets.stream().filter(bullet -> bullet.getOwner().getType() == type);
    }
}
