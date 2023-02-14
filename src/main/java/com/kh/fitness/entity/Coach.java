package com.kh.fitness.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@ToString(exclude = {"trainings"})
@Data
@Entity
public class Coach implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String patronymic;
    private String lastname;
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String specialization;
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(optional = false, fetch = LAZY)
    private Gym gym;

    @OneToMany(mappedBy = "coach")
    private Set<Training> trainings;

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Coach)) return false;
        final Coach other = (Coach) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstname = this.getFirstname();
        final Object other$firstname = other.getFirstname();
        if (this$firstname == null ? other$firstname != null : !this$firstname.equals(other$firstname)) return false;
        final Object this$patronymic = this.getPatronymic();
        final Object other$patronymic = other.getPatronymic();
        if (this$patronymic == null ? other$patronymic != null : !this$patronymic.equals(other$patronymic))
            return false;
        final Object this$lastname = this.getLastname();
        final Object other$lastname = other.getLastname();
        if (this$lastname == null ? other$lastname != null : !this$lastname.equals(other$lastname)) return false;
        final Object this$birthDate = this.getBirthDate();
        final Object other$birthDate = other.getBirthDate();
        if (this$birthDate == null ? other$birthDate != null : !this$birthDate.equals(other$birthDate)) return false;
        final Object this$phone = this.getPhone();
        final Object other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$specialization = this.getSpecialization();
        final Object other$specialization = other.getSpecialization();
        if (this$specialization == null ? other$specialization != null : !this$specialization.equals(other$specialization))
            return false;
        final Object this$image = this.getImage();
        final Object other$image = other.getImage();
        if (this$image == null ? other$image != null : !this$image.equals(other$image)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$gym = this.getGym();
        final Object other$gym = other.getGym();
        if (this$gym == null ? other$gym != null : !this$gym.equals(other$gym)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Coach;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstname = this.getFirstname();
        result = result * PRIME + ($firstname == null ? 43 : $firstname.hashCode());
        final Object $patronymic = this.getPatronymic();
        result = result * PRIME + ($patronymic == null ? 43 : $patronymic.hashCode());
        final Object $lastname = this.getLastname();
        result = result * PRIME + ($lastname == null ? 43 : $lastname.hashCode());
        final Object $birthDate = this.getBirthDate();
        result = result * PRIME + ($birthDate == null ? 43 : $birthDate.hashCode());
        final Object $phone = this.getPhone();
        result = result * PRIME + ($phone == null ? 43 : $phone.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $specialization = this.getSpecialization();
        result = result * PRIME + ($specialization == null ? 43 : $specialization.hashCode());
        final Object $image = this.getImage();
        result = result * PRIME + ($image == null ? 43 : $image.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $gym = this.getGym();
        result = result * PRIME + ($gym == null ? 43 : $gym.hashCode());
        return result;
    }
}