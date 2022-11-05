package ar.edu.itba.ss;

import ar.edu.itba.ss.utils.Generator;

import java.util.List;

public class MultiNhGenerator {
    public static void main(String[] args) {
        List<Integer> humansQty = List.of(2, 10, 40, 80, 140, 200, 260, 320);
        humansQty.forEach(hQty -> {
            Generator generator = new Generator(1, 11, hQty);
            generator.generate(String.format("input/positions_%s.txt", hQty));
        });
    }
}
