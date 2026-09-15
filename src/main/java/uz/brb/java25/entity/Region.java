package uz.brb.java25.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.brb.java25.enums.Status;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "region")
public class Region extends BaseEntity {

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Enumerated(EnumType.STRING)
    private Status status;
}
