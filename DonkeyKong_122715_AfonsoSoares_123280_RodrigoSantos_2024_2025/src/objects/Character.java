package objects;

import pt.iscte.poo.utils.Direction;

public interface Character {

    void takeDamage(int damage);
    void move(Direction direction);
    boolean isDead();
}
