package guru.springframework.sfgpetclinic.model;

import java.util.HashSet;
import java.util.Set;

public class Vet extends Person {

    private Set<Specialty> specilties = new HashSet<>();

    public Set<Specialty> getSpecilties() {
        return specilties;
    }

    public void setSpecilties(Set<Specialty> specilties) {
        this.specilties = specilties;
    }
}
