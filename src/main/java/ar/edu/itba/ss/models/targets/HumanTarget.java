package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import ar.edu.itba.ss.models.PersonState;
import ar.edu.itba.ss.models.Velocity;
import lombok.Getter;
import lombok.Setter;

import static ar.edu.itba.ss.utils.Constants.VD_MAX;

public class HumanTarget implements Target {

    @Getter
    @Setter
    private Person target;

    public HumanTarget(Person target) {
        this.target = target;
    }

    @Override
    public Velocity calculateVelocity(Person me) {
        return new Velocity(VD_MAX, me.getPosition().directionTo(target.getPosition()).getAngle());
    }

    @Override
    public boolean isReached(Person me) {
        return me.distanceTo(target) <= me.getRadius() + target.getRadius();
    }

    @Override
    public void execute(Person me) {
        if(isReached(me)) {
            if (target.getState() == PersonState.HUMAN) {
                target.changeState(PersonState.TRANSITIONING);
            }
            if (me.getState() == PersonState.ZOMBIE) {
                me.changeState(PersonState.TRANSITIONING);
            }
        }
    }

}
