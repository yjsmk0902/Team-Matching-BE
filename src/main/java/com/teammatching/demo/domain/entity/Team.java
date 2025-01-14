package com.teammatching.demo.domain.entity;

import com.teammatching.demo.domain.AuditingField;
import com.teammatching.demo.domain.Category;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(indexes = {
        @Index(columnList = "name"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Team extends AuditingField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String adminId;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(length = 1000)
    private String description;

    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;

    @Setter
    @Column(nullable = false)
    private String hashtag;

    @Setter
    @Column(nullable = false)
    private Integer limit;

    @Setter
    @Column(nullable = false)
    private Integer total;

    @Setter
    private LocalDateTime deadline;


    @ToString.Exclude
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private final Set<Admission> admissions = new LinkedHashSet<>();

    @Builder
    private Team(String adminId, String name, String description, Category category, String hashtag, Integer limit, Integer total, LocalDateTime deadline) {
        this.adminId = adminId;
        this.name = name;
        this.description = description;
        this.category = category;
        this.hashtag = hashtag;
        this.limit = limit;
        this.total = total;
        this.deadline = deadline;
    }
}
