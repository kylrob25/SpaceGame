package me.krob.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Spaceship extends Drawable {
    private static final int MOVEMENT_SPEED = 350;

    private final SpaceGameView view;

    private Bitmap bitmapUp;
    private Bitmap bitmapLeft;
    private Bitmap bitmapRight;
    private Bitmap bitmapDown;

    public Direction direction = Direction.NONE;
    private int movementSpeed = MOVEMENT_SPEED;

    public Spaceship(SpaceGameView view, Context context){
        super(view.getScreenY() / 10f, view.getScreenY() / 10f);
        this.view = view;

        locX = view.getScreenX() / 2f;
        locY = view.getScreenY() / 2f;

        createBitmap(context);
    }

    /**
     * Creating our bitmap images
     * @param context
     */
    public void createBitmap(Context context) {
        Bitmap decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmap = Bitmap.createScaledBitmap(decoded, (int) length, (int) height, false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        bitmapUp = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipright);
        bitmapRight = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipleft);
        bitmapLeft = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);

        decoded = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipdown);
        bitmapDown = Bitmap.createScaledBitmap(decoded, (int) (length), (int) (height),false);
    }

    /**
     * Draw the object
     * @param canvas
     * @param paint
     */
    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.argb(255,  255, 255, 255));
        canvas.drawBitmap(bitmap, locX, locY , paint);
    }

    /**
     * Updating the location
     * @param fps
     */
    public void update(long fps) {
        long movement = movementSpeed / fps;

        switch (direction) {
            case LEFT:
                locX -= movement;
                setBitmap(bitmapLeft);

                if (locX <= 0) {
                    locX = view.getScreenX() - length;
                }
                break;
            case RIGHT:
                locX += movement;
                setBitmap(bitmapRight);

                if ((locX - length) >= view.getScreenX() - 500) {
                    locX = 0;
                }
                break;
            case UP:
                locY -= movement;
                setBitmap(bitmapUp);

                if (locY + height <= 0) {
                    locY = view.getScreenY();
                }
                break;
            case DOWN:
                locY += movement;
                setBitmap(bitmapDown);

                if (locY >= view.getScreenY()) {
                    locY = 0 - height;
                }
                break;
        }

        updateRect();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
