package com.cegeka.academy.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "invitation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 45)
    @Column(name = "description", length = 45)
    private String description;

    @Size(max = 45)
    @Column(name = "status", length = 45)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_invited_user", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_event", referencedColumnName = "id")
    private Event event;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getInvitedUser() {
        return user;
    }

    public void setInvitedUser(User invitedUser) {
        this.user = invitedUser;
    }

    public Event getInvitationEvent() {
        return event;
    }

    public void setInvitationEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", idInvitationUser=" + user +
                ", idEvent=" + event +
                '}';
    }
}
