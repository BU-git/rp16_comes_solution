package com.bionic.model;

import com.bionic.model.dict.Job;
import com.bionic.model.dict.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author vitalii.levash
 * @author Dima Budko
 * @version 0.5
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer id;

    @Column(name = "userEmail")
    @NotNull
    @Email(message = "Your email is incorect")
    @NotEmpty(message = "Email can't be empty")
    private String email;

    @NotEmpty(message = "Password can't be empty")
    @Column(name = "userPassword")
    @Size(max = 60)
    @NotNull(message = "password must be not null")
    private String password;

    @NotNull
    @NotEmpty(message = "First name can't be empty")
    private String firstName;

    @NotNull
    @NotEmpty(message = "Last name can't be empty")
    private String lastName;

    private String insertion;

    @NotNull
    private String sex;

    @NotNull
    private boolean fourWeekPayOff;

    @NotNull
    private boolean zeroHours;

    @NotNull
    private int contractHours;

    @NotNull
    private boolean enabled;

    private boolean verified;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordExpire;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workScheduleId")
    private WorkSchedule workSchedule;

    @Column(name = "role")
    @Enumerated(EnumType.ORDINAL)
    private UserRoleEnum role;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employerId")
    private Employer employer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_jobs",
            joinColumns = @JoinColumn(name = "userId"))
    @Column(name = "jobId")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Job> jobs;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Shift> shifts;

    private String postalCode;

    private boolean paidTimeForTime;

    private Integer tvt;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isFourWeekPayOff() {
        return fourWeekPayOff;
    }

    public void setFourWeekPayOff(boolean fourWeekPayOff) {
        this.fourWeekPayOff = fourWeekPayOff;
    }

    public boolean isZeroHours() {
        return zeroHours;
    }

    public void setZeroHours(boolean zeroHours) {
        this.zeroHours = zeroHours;
    }

    public int getContractHours() {
        return contractHours;
    }

    public void setContractHours(int contractHours) {
        this.contractHours = contractHours;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getPasswordExpire() {
        return passwordExpire;
    }

    public void setPasswordExpire(Date passwordExpire) {
        this.passwordExpire = passwordExpire;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public void setRole(UserRoleEnum role) {
        this.role = role;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorkSchedule getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(WorkSchedule workSchedule) {
        this.workSchedule = workSchedule;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", insertion='" + insertion + '\'' +
                ", sex='" + sex + '\'' +
                ", fourWeekPayOff=" + fourWeekPayOff +
                ", zeroHours=" + zeroHours +
                ", contractHours=" + contractHours +
                ", enabled=" + enabled +
                ", passwordExpire=" + passwordExpire +
                ", workSchedule=" + workSchedule +
                ", role=" + role +
                ", employer=" + employer +
                ", jobs=" + jobs +
                ", postalCode=" + postalCode +
                "}";
    }

    public boolean isPaidTimeForTime() {
        return paidTimeForTime;
    }

    public void setPaidTimeForTime(boolean paidTimeForTime) {
        this.paidTimeForTime = paidTimeForTime;
    }

    public Integer getTvt() {
        return tvt;
    }

    public void setTvt(Integer tvt) {
        this.tvt = tvt;
    }
}
