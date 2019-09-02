package com.cegeka.academy.repository.util;

import com.cegeka.academy.domain.*;

import java.util.Set;

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

    public static Event createEvent(String description, String name, boolean isPublic, Address address, User user, Set<Category> categories, String coverPhoto) {
        Event event = new Event();
        event.setDescription(description);
        event.setName(name);
        event.setPublic(isPublic);
        event.setOwner(user);
        event.setAddressId(address);
        event.setCategories(categories);
        event.setCoverPhoto(coverPhoto);
        return event;
    }

    public static User createUser(String login, String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        return user;
    }

    public static Category createCategory(String name, String description) {
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return category;
    }

    public static Invitation createInvitation(String status, String description, Event event, User user) {
        Invitation invitation = new Invitation();
        invitation.setStatus(status);
        invitation.setDescription(description);
        invitation.setUser(user);
        invitation.setEvent(event);
        return invitation;
    }

    public static Group createGroup(String name, String description) {
        Group group = new Group();
        group.setName(name);
        group.setDescription(description);
        return group;
    }

    public static GroupUserRole createGroupUserRole(User user, Group group, Role role) {
        GroupUserRole groupUserRole = new GroupUserRole();
        groupUserRole.setUser(user);
        groupUserRole.setGroup(group);
        groupUserRole.setRole(role);
        return groupUserRole;
    }

    public static Role createRole(String name) {
        Role role = new Role();
        role.setName(name);
        return role;
    }
}
