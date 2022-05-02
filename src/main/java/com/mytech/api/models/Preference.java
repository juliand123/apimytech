package com.mytech.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Preference {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String preferenceId;
}
