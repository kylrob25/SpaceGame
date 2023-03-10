package me.krob.spacegame.drawable;

import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class Drawable implements IDrawable {
    protected final RectF rect;
    protected float locX, locY;
    protected float height, width;

    protected Bitmap bitmap;

    public Drawable(float height, float width) {
        this.height = height;
        this.width = width;

        rect = new RectF();
    }

    protected boolean intersects(IDrawable drawable) {
        return rect.intersect(drawable.getRect());
    }

    protected void updateRect(float minX, float minY, float maxX, float maxY) {
        rect.set(minX, minY, maxX, maxY);
    }

    /**
     * Updating the rect
     */
    protected void updateRect() {
        rect.set(locX - width, locY, locX + width, locY + height);
    }

    /**
     * Set the current bitmap
     * @param bitmap
     */
    protected void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    protected float getHeight() {
        return height;
    }

    protected float getWidth() {
        return width;
    }

    protected float getLocX() {
        return locX;
    }

    protected float getLocY() {
        return locY;
    }

    public RectF getRect() {
        return rect;
    }
}
