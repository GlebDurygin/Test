package com.haulmont.testtask.model.container;

import com.haulmont.testtask.model.entity.Group;

import java.util.*;

public class GroupContainer {
    private Map<Long, Group> groups;

    public GroupContainer() {
        groups = new HashMap<>();
    }

    public void addGroup(Group group) {
        groups.put(group.getId(),group);
    }

    public void removeGroup(Group group) {
        groups.remove(group.getId());
    }

    public void removeGroup(Long id) {
        groups.remove(id);
    }

    public Group getGroup(Long id) {
        return groups.get(id);
    }

    public void updateGroup(Group group) {
        groups.replace(group.getId(),groups.get(group.getId()),group);
    }

    public List<Group> getGroups(){
        List<Group> list = new ArrayList<>(groups.values());
        return Collections.unmodifiableList(list);
    }
}
