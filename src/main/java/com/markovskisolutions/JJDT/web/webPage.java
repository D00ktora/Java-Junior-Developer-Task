package com.markovskisolutions.JJDT.web;

import com.markovskisolutions.JJDT.model.DTO.ListOfViewDTO;
import com.markovskisolutions.JJDT.service.WebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class webPage {

    private final WebService webService;

    public webPage(WebService webService) {
        this.webService = webService;
    }

    @ModelAttribute("listOfAllUsers")
    public ListOfViewDTO initListOfViewDTO() {
        return new ListOfViewDTO();
    }

    @GetMapping("/web")
    private String index(Model model) {
        ListOfViewDTO listOfAllUsers = webService.getListOfAllUsers();
        model.addAttribute(listOfAllUsers);
        return "index";
    }
}
