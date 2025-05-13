package org.aeis.videorecording.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "video_recording")
@NamedQuery(name = "VideoRecording.findAll", query = "SELECT v FROM VideoRecording v")
public class VideoRecording{


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "video_recording_seq")
    @SequenceGenerator(name = "video_recording_seq", sequenceName = "video_recording_seq", initialValue = 1, allocationSize = 1)
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
