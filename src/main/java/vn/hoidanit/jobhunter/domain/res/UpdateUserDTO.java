package vn.hoidanit.jobhunter.domain.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import vn.hoidanit.jobhunter.utils.Enum.GenderEnum;

import java.time.Instant;

public class UpdateUserDTO {
    private long id;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss a", timezone = "GMT+7")
    private Instant updatedAt;

    public UpdateUserDTO(String name, long id, int age, GenderEnum gender, String address, Instant updatedAt) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.updatedAt = updatedAt;
    }

    public UpdateUserDTO() {
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
