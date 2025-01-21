package de.tum.cit.ase.bomberquest.util;

public class Rectangle {
    public float x,y,w,h;

   /**
 * Constructor to initialize a rectangle with specified coordinates and dimensions
 * @param x The x-coordinate of the rectangle
 * @param y The y-coordinate of the rectangle
 * @param w The width of the rectangle
 * @param h The height of the rectangle
 */
public Rectangle(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
}


    /**
     * Check if the current rectangle collides with another rectangle
     * @param b The other rectangle
     * @return True if the rectangles collide, false otherwise
     */
    public boolean collide(Rectangle b) {
        Rectangle rectangle = this;
        Rectangle r = b;
        if (r.x < rectangle.x + rectangle.w &&
                r.x + r.w > rectangle.x &&
                r.y < rectangle.y + rectangle.h &&
                r.h + r.y > rectangle.y) {
            return true;
        }
        return false;
    }
}
