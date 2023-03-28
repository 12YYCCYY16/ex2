package kr.ac.poly.ex2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_memo")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter

@ToString
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mno;

    @Column(length = 200,nullable = false)
    private String memoText;
}
