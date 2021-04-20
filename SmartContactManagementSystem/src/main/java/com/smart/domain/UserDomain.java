package com.smart.domain;

import com.smart.data.transfer.objects.ContactDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "USER" )
public class UserDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    private String userName;
    @Column( columnDefinition = "varchar(100) not null", unique = true)
    private String userEmail;
    private String userPassword;
    private String UserRole;
    private Boolean userEnabledStatus;
    private String userImageUrl;
    @Column(length = 500)
    private String aboutUser;
    @OneToMany(cascade = CascadeType.ALL ,fetch = FetchType.LAZY ,mappedBy = "userDomain")
    private List<ContactDomain> contactDomain= new ArrayList<>();
}
