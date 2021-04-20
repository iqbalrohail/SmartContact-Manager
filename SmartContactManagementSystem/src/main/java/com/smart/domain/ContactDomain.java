package com.smart.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "CONTACT")
public class ContactDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    private String contactName;
    private String contactNickName;
    private String contactPhone;
    @Lob
    @Column(length = 5000 , columnDefinition="LONGTEXT")
    private String contactDescription;
    private String contactWork;
    private String contactImageUrl;
    private String contactEmail;
    @ManyToOne
    @JsonIgnore
    private UserDomain userDomain;
}
