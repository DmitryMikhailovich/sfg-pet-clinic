package guru.springframework.sfgpetclinic.model;

import java.util.Set;

public class Vet extends Person {

    private Set<Specialty> specilties;

    public Set<Specialty> getSpecilties() {
        return specilties;
    }

    public void setSpecilties(Set<Specialty> specilties) {
        this.specilties = specilties;
    }
}
