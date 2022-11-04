package com.eqstwah.webserver.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eqstwah.webserver.Entity.AccessLogEntity;
import com.eqstwah.webserver.Entity.LicenseEntity;
import com.eqstwah.webserver.Repository.AccessLogRepo;
import com.eqstwah.webserver.Repository.LicenseRepo;
import com.eqstwah.webserver.Scheduler.Scheduling;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HtmlController {

    private final LicenseRepo licenseRepo;
    private final AccessLogRepo accessLogRepo;

    @GetMapping("/")
    public String hello(Model model,@RequestParam(value = "token", defaultValue = "worng") String token) {
        if(token.equals("worng")){
            return "redirect:/error";
        }
        else{
            if(accessLogRepo.findByToken(token).isEmpty()){
               return "redirect:/error"; 
            }
        }

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
