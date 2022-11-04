package com.eqstwah.webserver.Entity;

import lombok.*;

import javax.persistence.*;

@Getter // getter 메소드 생성
@Builder // 빌더를 사용할 수 있게 함
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="access_log") // 테이블 명을 작성
public class AccessLogEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long aid;
    
    @Column(length = 100)
    private String token;

    @Column(length = 100)
    private String ip;

    public AccessLogEntity(String token, String ip){
        this.token = token;
        this.ip = ip;
    }
}
