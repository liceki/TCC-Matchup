package com.matchup.dto;

import com.matchup.enums.UserAccess;
import com.matchup.model.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private long id;
    private String username;
    private String name;
    private String email;
    private LocalDate birthDate;
    @Pattern(regexp = "^(?!.*[-_.]{2})[a-zA-Z0-9][a-zA-Z0-9-_.]*[a-zA-Z0-9]$")
    private String rawPassword;
    @Pattern(regexp = "^\\+\\d{2}\\s\\(\\d{2}\\)\\s\\d{5}-\\d{4}$")
    private String cellphoneNumber;
    private MultipartFile profilePicture;
    private String formattedProfilePicture;
    private String bio;
    private List<Long> friends;
    private List<Long> interests;
    private List<Long> sentMessages;
    private List<Long> receivedMessages;
    private UserAccess access;

    private String token;

    private boolean hasInterests;

    private long addressId;
    private int addressNumber;
    private String addressStreet;
    private String addressNeighborhood;
    private String addressCity;
    private String addressState;
    private String addressZipcode;

}
