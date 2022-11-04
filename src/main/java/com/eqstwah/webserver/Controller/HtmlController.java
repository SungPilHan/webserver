package com.eqstwah.webserver.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.eqstwah.webserver.Entity.LicenseEntity;
import com.eqstwah.webserver.Repository.LicenseRepo;
import com.eqstwah.webserver.Scheduler.Scheduling;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    private final LicenseRepo licenseRepo;

    @GetMapping("/")
    public String hello(Model model) {
        
        if(licenseRepo.findById(1L).isPresent()){
            LicenseEntity licenseEntity = licenseRepo.findById(1L).get();
            model.addAttribute("license", licenseEntity.getLicense());
        }
        else{
            Scheduling sch = new Scheduling(licenseRepo);
            sch.cronJobSch();
            if(licenseRepo.findById(1L).isPresent()){
                LicenseEntity licenseEntity = licenseRepo.findById(1L).get();
                model.addAttribute("license", licenseEntity.getLicense());
            }
            else{
                return "redirect:/error";
            }
        }
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
}
