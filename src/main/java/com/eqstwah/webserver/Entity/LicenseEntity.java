package com.eqstwah.webserver.Entity;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="License") // 테이블 명을 작성
public class LicenseEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long did;
    
    @Column(length = 300)
    private String license;

    @Column(length = 100)
    private String challenge;

    @Column(length = 500)
    private String encryptchallenge;

    public LicenseEntity(String license, String challenge, String encryptchallenge) {
        this.license = license;
        this.challenge = challenge;
        this.encryptchallenge = encryptchallenge;
    }
}
