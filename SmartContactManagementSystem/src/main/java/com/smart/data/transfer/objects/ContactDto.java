package com.smart.data.transfer.objects;
import com.smart.domain.UserDomain;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactDto {
    private int contactId;
    private String contactName;
    private String contactNickName;
    private String contactPhone;
    private String contactDescription;
    private String contactWork;
    private String contactImageUrl;
    private String contactEmail;
    private UserDto userDto;
}
