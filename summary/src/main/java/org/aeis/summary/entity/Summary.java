package org.aeis.summary.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "summary")
@NamedQuery(name = "Summary.findAll", query = "SELECT s FROM Summary s")
public class Summary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "summary_seq")
    @SequenceGenerator(
            name = "summary_seq",
            sequenceName = "summary_seq",
            initialValue = 1,
            allocationSize = 1
    )
    private Long id;


    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private byte[] content;


    @Column(name = "shared")
    private int isShared;






}
