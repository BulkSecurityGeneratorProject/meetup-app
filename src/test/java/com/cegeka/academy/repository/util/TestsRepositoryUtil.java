package com.cegeka.academy.repository.util;

import com.cegeka.academy.domain.Address;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.User;

public class TestsRepositoryUtil {

    public static Address createAddress(String country, String city, String street, String number, String details, String name) {
        Address address = new Address();
        address.setCity(city);
        address.setCountry(country);
        address.setStreet(street);
        address.setDetails(details);
        address.setNumber(number);
        address.setName(name);
        return address;
    }

    public static Event createEvent(String description, String name, boolean isPublic, Address address, User user) {
        Event event = new Event();
        event.setDescription(description);
        event.setName(name);
        event.setPublic(isPublic);
        event.setOwner(user);
        event.setAddressId(address);
        return event;
    }

    public static User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    public static Invitation createInvitation(String status, String description, Event event, User user) {
        Invitation invitation = new Invitation();
        invitation.setStatus(status);
        invitation.setDescription(description);
        invitation.setUser(user);
        invitation.setEvent(event);
        return invitation;
    }


}
