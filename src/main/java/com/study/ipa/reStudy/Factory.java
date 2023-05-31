package com.study.ipa.reStudy;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reStudy_factory")
public class Factory {

    @Id
    @Column(name = "factory_id")
    @GeneratedValue(generator = "uid")
    @GenericGenerator(strategy = "uuid", name = "uid")
    private String id;

    @Column(name = "factory_name", nullable = false)
    private String name;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Kind kind = Kind.NUT;

    @Column(nullable = false)
    private int price;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createDate;

    public enum Kind{
        BOLT,NUT
    }

}
