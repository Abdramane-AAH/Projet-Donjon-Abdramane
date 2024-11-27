import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    private Direction direction = Direction.EAST;
    private double speed = 5;
    private double timeBetweenFrame = 250;
    private boolean isWalking = true;
    private final int spriteSheetNumberOfColumn = 10;


    private int maxHealth = 100;
    private int currentHealth = maxHealth;

    public DynamicSprite(double x, double y, Image image, double width, double height) {
        super(x, y, image, width, height);
    }

    public double getSpeed() {
        return this.speed;
    }

    // Changer la vitesse du personnage
    public void setSpeed(double newSpeed) {
        this.speed = newSpeed;
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
        }
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    // détecter les collisions
    private void checkTrapCollisions(ArrayList<Sprite> environment) {
        Rectangle2D.Double playerHitbox = (Rectangle2D.Double) this.getHitBox();

        for (Sprite s : environment) {
            if (s instanceof SolidSprite) {
                SolidSprite trap = (SolidSprite) s;
                if (trap.getHitBox().intersects(playerHitbox)) {
                    takeDamage(10);
                }
            }
        }
    }


    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double moved = new Rectangle2D.Double();
        switch (direction) {
            case EAST: moved.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case WEST: moved.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case NORTH: moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
            case SOUTH: moved.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
                break;
        }

        for (Sprite s : environment) {
            if ((s instanceof SolidSprite) && (s != this)) {
                if (((SolidSprite) s).intersect(moved)) {
                    return false;
                }
            }
        }
        return true;
    }


    private void move() {
        switch (direction) {
            case NORTH -> {
                this.y -= speed;
            }
            case SOUTH -> {
                this.y += speed;
            }
            case EAST -> {
                this.x += speed;
            }
            case WEST -> {
                this.x -= speed;
            }
        }
    }

    //
    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }

        checkTrapCollisions(environment);
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame % spriteSheetNumberOfColumn);


        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * this.width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * this.width), (int) ((direction.getFrameLineNumber() + 1) * this.height), null);

        // barre de vie
        g.setColor(Color.RED);
        g.fillRect((int) x, (int) (y - 10), (int) width, 5);

        g.setColor(Color.YELLOW);
        int healthBarWidth = (int) ((currentHealth / (double) maxHealth) * width);
        g.fillRect((int) x, (int) (y - 10), healthBarWidth, 5);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

