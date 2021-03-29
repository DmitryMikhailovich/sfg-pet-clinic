package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.OwnerService;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
@Controller
public class VisitController {

    public static final String VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";

    private final PetService petService;

    private final VisitService visitService;

    public VisitController(PetService petService, VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable Long petId, Model model) {
        Pet pet = petService.findById(petId);
        model.addAttribute("pet", pet);
        Visit visit = Visit.builder().build();
        pet.getVisits().add(visit);
        visit.setPet(pet);

        return visit;
    }

    @InitBinder("visit")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/new")
    public String initCreationForm(Pet pet, Visit visit, @PathVariable Long petId) {
        return VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(Pet pet, @PathVariable Long ownerId, @PathVariable Long petId,
                                      @Validated Visit visit, BindingResult result, Model model) {
        pet.getVisits().add(visit);
        visit.setPet(pet);
        if(result.hasErrors()) {
            model.addAttribute("visit", visit);
            model.addAttribute("pet", pet);
            return VIEW_PETS_CREATE_OR_UPDATE_VISIT_FORM;
        } else {
            visitService.save(visit);
            return "redirect:/owners/" + ownerId;
        }
    }
}

