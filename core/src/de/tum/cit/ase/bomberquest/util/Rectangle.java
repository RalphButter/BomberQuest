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
        // Assign the current rectangle to a local variable
        Rectangle rectangle = this;
        // Assign the input rectangle to a local variable
        Rectangle r = b;

        // Check if the rectangles overlap on the x and y axes
        if (r.x < rectangle.x + rectangle.w && // r's left edge is to the left of rectangle's right edge
                r.x + r.w > rectangle.x &&         // r's right edge is to the right of rectangle's left edge
                r.y < rectangle.y + rectangle.h && // r's top edge is above rectangle's bottom edge
                r.h + r.y > rectangle.y) {         // r's bottom edge is below rectangle's top edge
            return true; // Rectangles collide
        }

        return false; // Rectangles do not collide
    }
}
