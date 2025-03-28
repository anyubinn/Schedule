package com.example.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "longtext")
    private String contents;

    public Schedule(String userName, String title, String contents) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }

    public void updateSchedule(String userName, String title, String contents) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }
}
