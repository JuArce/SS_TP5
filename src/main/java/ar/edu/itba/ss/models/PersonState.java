package ar.edu.itba.ss.models;

import ar.edu.itba.ss.interfaces.Behavior;
import ar.edu.itba.ss.models.behavior.HumanBehavior;
import ar.edu.itba.ss.models.behavior.TransitioningBehavior;
import ar.edu.itba.ss.models.behavior.ZombieBehavior;
import lombok.Getter;

public enum PersonState {
    HUMAN(HumanBehavior.class),
    ZOMBIE(ZombieBehavior.class),
    TRANSITIONING(TransitioningBehavior.class);

    @Getter
    private final Class<? extends Behavior> behaviorClass;

    PersonState(Class<? extends Behavior> behaviorClass) {
        this.behaviorClass = behaviorClass;
    }
}
