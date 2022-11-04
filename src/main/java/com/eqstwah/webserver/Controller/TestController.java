package com.eqstwah.webserver.Controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eqstwah.webserver.Entity.LicenseEntity;
import com.eqstwah.webserver.Repository.AccessLogRepo;
import com.eqstwah.webserver.Repository.LicenseRepo;
import com.eqstwah.webserver.Scheduler.Scheduling;
import com.eqstwah.webserver.Utility.AES256;
import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class TestController {
    private final LicenseRepo licenseRepo;
    private final AccessLogRepo accessLogRepo;

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/trychallenge")
    public String trychallenge(){
        String challenge = "testest";
        if(licenseRepo.findById(1L).isPresent()){
            LicenseEntity licenseEntity = licenseRepo.findById(1L).get();
            challenge = licenseEntity.getChallenge();
        }
        else{
            Scheduling sch = new Scheduling(licenseRepo,accessLogRepo);
            sch.cronJobSch();
            if(licenseRepo.findById(1L).isPresent()){
                LicenseEntity licenseEntity = licenseRepo.findById(1L).get();
                challenge = licenseEntity.getChallenge();
            }
            else{
                return "no";
            }
        }
        if(!challenge.equals("testest")){
            return challenge;
        }
        return "no";
    }

    @PostMapping("/checklicense")
    public String checklicense(@RequestParam(value = "data", defaultValue = "none") String data) {
        String license_code = "testest";
        LicenseEntity licenseEntity = null;
        Scheduling sch = new Scheduling(licenseRepo, accessLogRepo);
        data = data.trim().replace(" ", "+");
        if(licenseRepo.findById(1L).isPresent()){
            licenseEntity = licenseRepo.findById(1L).get();
            license_code = licenseEntity.getEncryptchallenge();
        }
        else{
            sch.cronJobSch();
            if(licenseRepo.findById(1L).isPresent()){
                licenseEntity = licenseRepo.findById(1L).get();
                license_code = licenseEntity.getEncryptchallenge();
            }
            else{
                return "no";
            }
        }
        if(!license_code.equals("testest") && data.trim().equals(license_code)){
            String license = licenseEntity.getLicense();
            AES256 aes256 = new AES256(license.substring(0, 32), license.substring(32));
            String yescode = "yes"+ sch.randomStr(30);
            try {
                yescode = aes256.encrypt(yescode);
                return yescode;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "no";
    }
}
