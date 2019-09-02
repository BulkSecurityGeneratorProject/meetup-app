package com.cegeka.academy.repository;

import com.cegeka.academy.domain.Category;
import com.cegeka.academy.domain.Event;
import com.cegeka.academy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOwner(User owner);

    List<Event> findAllByIsPublicIsTrue();

    List<Event> findByUsers_id(Long userId);

    List<Event> findAllByCategories_id(Long categoryId);

    List<Event> findDistinctByIsPublicIsTrueAndCategoriesIn(List<Category> categories);


}
