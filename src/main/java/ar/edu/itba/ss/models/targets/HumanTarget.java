package ar.edu.itba.ss.models.targets;

import ar.edu.itba.ss.interfaces.Target;
import ar.edu.itba.ss.models.Person;
import lombok.Getter;
import lombok.Setter;

public class HumanTarget implements Target {

    @Getter
    @Setter
    private Person target;

    public HumanTarget(Person target) {
        this.target = target;
    }




}
