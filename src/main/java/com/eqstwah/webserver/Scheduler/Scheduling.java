package com.eqstwah.webserver.Scheduler;

import java.util.Random;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.eqstwah.webserver.Entity.LicenseEntity;
import com.eqstwah.webserver.Repository.LicenseRepo;
import com.eqstwah.webserver.Utility.AES256;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Scheduling {

    private final LicenseRepo licenseRepo;

    @Scheduled(cron = "0 0,5,10,15,20,25,30,33,40,45,50,55 * * * *")
    public void cronJobSch() {
        if (!licenseRepo.findById(1L).isPresent()) {
            String license = randomStr(48);
            String challenge = randomStr(100);
            AES256 aes256 = new AES256(license.substring(0, 32), license.substring(32));
            try {
                String encryptchallenge = aes256.encrypt(challenge);
                licenseRepo.save(LicenseEntity.builder()
                        .did(1L)
                        .license(license)
                        .challenge(challenge)
                        .encryptchallenge(encryptchallenge)
                        .build());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            LicenseEntity licenseEntity = licenseRepo.findById(1L).get();
            String license = licenseEntity.getLicense();
            String challenge = randomStr(100);

            AES256 aes256 = new AES256(license.substring(0, 32), license.substring(32));
            try {
                String encryptchallenge = aes256.encrypt(challenge);
                licenseRepo.save(LicenseEntity.builder()
                        .did(1L)
                        .license(license)
                        .challenge(challenge)
                        .encryptchallenge(encryptchallenge)
                        .build());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 1,4,7,10 *")
    public void changeLicense() {
        String license = randomStr(48);
        String challenge = randomStr(100);

        AES256 aes256 = new AES256(license.substring(0, 32), license.substring(32));
        try {
            String encryptchallenge = aes256.encrypt(challenge);
            licenseRepo.save(LicenseEntity.builder()
                    .did(1L)
                    .license(license)
                    .challenge(challenge)
                    .encryptchallenge(encryptchallenge)
                    .build());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String randomStr(int targetStringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}
