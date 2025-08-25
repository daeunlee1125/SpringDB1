package hello.jdbc.domain;

import lombok.Data;

/* 현재는 DTO와 동일한 역할 */
/* 추후에 비즈니스 로직 추가해서 확장 */
@Data
public class Member {
    private String memeberId;
    private int money;

    public Member(){}

    public Member(String memeberId, int money) {
        this.memeberId = memeberId;
        this.money = money;
    }
}
