package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInit implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    public DataInit(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if(count == 0) {

            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        dog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        cat = petTypeService.save(cat);

        Specialty radiology = new Specialty();
        radiology.setDescription("Radiology");
        radiology = specialtyService.save(radiology);

        Specialty surgery = new Specialty();
        surgery.setDescription("Surgery");
        surgery = specialtyService.save(surgery);

        Specialty dentistry = new Specialty();
        dentistry.setDescription("Dentistry");
        dentistry = specialtyService.save(dentistry);

        Owner owner1 = new Owner();
        owner1.setFirstName("Vasiliy");
        owner1.setLastName("Pupkin");
        owner1.setAddress("Lenina str, 13");
        owner1.setCity("Moscow");
        owner1.setPhone("+79999999999");

        Pet pupkinsPet = new Pet();
        pupkinsPet.setName("Bobik");
        pupkinsPet.setPetType(dog);
        pupkinsPet.setBirthDate(LocalDate.now().minusDays(50));
        pupkinsPet.setOwner(owner1);
        owner1.getPets().add(pupkinsPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Helen");
        owner2.setLastName("Golovach");
        owner2.setAddress("Lenina str, 15");
        owner2.setCity("Moscow");
        owner2.setPhone("+79999999777");

        Pet helensPet = new Pet();
        helensPet.setName("Leopard");
        helensPet.setPetType(cat);
        helensPet.setBirthDate(LocalDate.now().minusDays(150));
        helensPet.setOwner(owner2);
        owner2.getPets().add(helensPet);

        ownerService.save(owner2);

        System.out.println("Loaded Owners...");

        Visit catVisit = new Visit();
        catVisit.setPet(helensPet);
        catVisit.setDescription("Sleepy Cat");
        catVisit.setDate(LocalDate.now().minusDays(10));
        visitService.save(catVisit);

        System.out.println("Loaded Visits...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Ivan");
        vet1.setLastName("Ivanov");
        vet1.getSpecilties().add(radiology);
        vet1.getSpecilties().add(dentistry);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Vlad");
        vet2.setLastName("Dracula");
        vet2.getSpecilties().add(surgery);
        vet2.getSpecilties().add(dentistry);

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
