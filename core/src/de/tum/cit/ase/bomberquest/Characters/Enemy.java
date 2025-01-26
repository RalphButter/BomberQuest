package de.tum.cit.ase.bomberquest.Characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import de.tum.cit.ase.bomberquest.MapManager.Map;
import de.tum.cit.ase.bomberquest.gameobject.CellType;
import de.tum.cit.ase.bomberquest.gameobject.GameObject;
import de.tum.cit.ase.bomberquest.pathFinding.PathFinding;
import de.tum.cit.ase.bomberquest.util.Rectangle;
import de.tum.cit.ase.bomberquest.util.SpriteSheet;
import java.util.List;
import java.util.Random;

public class Enemy extends Entity {

    SpriteSheet sheet;
    PathFinding pathFinding;
    List<Vector2> path;
    int currentTargetIndex;
    float speed;
    float timeSinceLastPathUpdate = 0f;
    float updateInterval = 1f;
    Vector2 roamTarget = null;
    float roamTime = 0f;
    Random random = new Random();

    Map map;
    Player player;

    public Enemy(Vector2 pos, Map map, Player player) {
        super(pos, null);
        this.map = map;
        this.player = player;
        this.pathFinding = new PathFinding(map);

        sheet = new SpriteSheet(new Texture("enemy.png"), 1, 3);
        sheet.setPlay(0, 2, 0.1f, true);

        // Let's do an initial BFS path:
        int startRow = (int)(pos.y / 16);
        int startCol = (int)(pos.x / 16);
        int playerRow = (int)(player.pos.y / 16);
        int playerCol = (int)(player.pos.x / 16);
        path = pathFinding.findPath(startRow, startCol, playerRow, playerCol);
        currentTargetIndex = 0;
        speed = 0.25f;
    }

    @Override
    public Rectangle getRect() {
        return new Rectangle(pos.x + 2, pos.y + 2, sheet.getWidth() - 4, sheet.getHeight() / 2 - 4);
    }

    @Override
    public void update() {
        super.update();

        // 1) Back up old position for collision revert if needed
        Vector2 oldPos = pos.cpy();

        // 2) Recalc BFS every second
        timeSinceLastPathUpdate += Gdx.graphics.getDeltaTime();
        if (timeSinceLastPathUpdate >= updateInterval) {
            timeSinceLastPathUpdate = 0;

            int startRow = (int)(pos.y / 16);
            int startCol = (int)(pos.x / 16);
            int playerRow = (int)(player.pos.y / 16);
            int playerCol = (int)(player.pos.x / 16);

            System.out.println("Calling BFS from ("+startRow+","+startCol+") -> ("+playerRow+","+playerCol+")");

            path = pathFinding.findPath(startRow, startCol, playerRow, playerCol);
            currentTargetIndex = 0;
        }

        // 3) Move the enemy: chase if BFS path found, else roam
        if (!path.isEmpty()) {
            chasePlayer();
        } else {
            roam();
        }

        // 4) Collision check: if we collide with a wall, revert the move
        if (isCollidingWithWall()) {
            pos.set(oldPos);
        }

        // 5) Animate
        sheet.play();
    }



    private boolean isCollidingWithWall() {
        Rectangle enemyRect = getRect();
        for (int r = 0; r < map.getRows(); r++) {
            for (int c = 0; c < map.getCols(); c++) {
                if (map.getCell(r, c).cellType == CellType.INDESTRUCTIBLE_WALL ||
                        map.getCell(r, c).cellType == CellType.DESTRUCTIBLE_WALL) {
                    Rectangle wallRect = map.getCell(r, c).getRect();
                    if (enemyRect.collide(wallRect)) {
                        return true;
                    }
                }
            }
        }
        return false;

    }

    private void chasePlayer() {
        if (currentTargetIndex >= path.size()) {
            return;
        }
        // path node => (col, row) in the path list
        Vector2 tile = path.get(currentTargetIndex);
        // Convert tile to real coords
        Vector2 targetPos = new Vector2(tile.x * 16, tile.y * 16);

        float dist = Vector2.dst(pos.x, pos.y, targetPos.x, targetPos.y);
        if (dist < 1f) {
            currentTargetIndex++;
            if (currentTargetIndex >= path.size()) return;
            tile = path.get(currentTargetIndex);
            targetPos.set(tile.x * 16, tile.y * 16);
        }
        Vector2 dir = targetPos.cpy().sub(pos).nor();
        pos.add(dir.scl(1f)); // chase speed = 1
    }

    private void roam() {
        roamTime -= Gdx.graphics.getDeltaTime();
        if (roamTarget == null || roamTime <= 0f) {
            roamTime = 3f;
            roamTarget = getRandomRoamSpot();
        }
        float dist = Vector2.dst(pos.x, pos.y, roamTarget.x, roamTarget.y);
        if (dist < 1f) {
            roamTime = 0;
            return;
        }
        Vector2 dir = roamTarget.cpy().sub(pos).nor();
        float roamSpeed = 0.5f;
        pos.add(dir.scl(roamSpeed));
    }



    private Vector2 getRandomRoamSpot() {
        int randCol = random.nextInt(map.getCols());
        int randRow = random.nextInt(map.getRows());
        return new Vector2(randCol * 16, randRow * 16);
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        TextureRegion t = sheet.getCurrentFrame();
        batch.draw(t, pos.x, pos.y, sheet.getWidth()/2f, sheet.getHeight()/2f,
                sheet.getWidth(), sheet.getHeight(), 1, 1, 0);
        batch.end();
    }
}
