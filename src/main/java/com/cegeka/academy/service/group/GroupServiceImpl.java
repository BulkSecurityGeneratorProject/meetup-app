package com.cegeka.academy.service.group;
import com.cegeka.academy.domain.User;
import com.cegeka.academy.domain.Group;
import com.cegeka.academy.domain.GroupUserRole;
import com.cegeka.academy.repository.GroupRepository;
import com.cegeka.academy.repository.GroupUserRoleRepository;
import com.cegeka.academy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupUserRoleRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsersByGroup(Long id)
    {
        List<GroupUserRole> groupUserRoles =  groupRepository.findAllByGroupId(id);

        List<User> users = new ArrayList<>();

        for(GroupUserRole groupUserRole : groupUserRoles)
        {
            users.add(groupUserRole.getUser());
        }

        return users;
    }


}
