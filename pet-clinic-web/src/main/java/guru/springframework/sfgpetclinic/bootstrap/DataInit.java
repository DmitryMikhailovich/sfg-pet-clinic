package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;

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

        Owner owner1 = Owner.builder()
                .firstName("Vasiliy")
                .lastName("Pupkin")
                .address("Lenina str, 13")
                .city("Moscow")
                .phone("+79999999999")
                .pets(new HashSet<>())
                .build();

        Pet pupkinsPet = new Pet();
        pupkinsPet.setName("Bobik");
        pupkinsPet.setPetType(dog);
        pupkinsPet.setBirthDate(LocalDate.now().minusDays(50));
        pupkinsPet.setOwner(owner1);
        owner1.getPets().add(pupkinsPet);

        ownerService.save(owner1);

        Owner owner2 = Owner.builder()
                .firstName("Helen")
                .lastName("Golovach")
                .address("Lenina str, 15")
                .city("Moscow")
                .phone("+79999999777")
                .pets(new HashSet<>())
                .build();

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
        vet1.getSpecialties().add(radiology);
        vet1.getSpecialties().add(dentistry);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Vlad");
        vet2.setLastName("Dracula");
        vet2.getSpecialties().add(surgery);
        vet2.getSpecialties().add(dentistry);

        vetService.save(vet2);

        System.out.println("Loaded Vets...");
    }
}
