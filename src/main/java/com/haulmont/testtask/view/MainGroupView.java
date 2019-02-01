package com.haulmont.testtask.view;

import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.service.GroupService;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.TABLE_GROUP_FACULTY;
import static com.haulmont.testtask.database.impl.HSQLDBConstants.TABLE_GROUP_NUMBER;


public class MainGroupView extends BasicMainView {

    private GroupService groupService;

    public MainGroupView() {
        super();
        groupService = GroupService.getInstance();

        table.setHeight("550px");
        addComponents(table, buttonsLayout);

        init();
        refresh();
    }

    @Override
    protected void init() {
        setTable();
        setClickListeners();
    }

    private void setTable() {
        table.addContainerProperty(TABLE_GROUP_FACULTY, String.class, null, "Факультет", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_GROUP_NUMBER, Integer.class, null, "Номер группы", null, Table.Align.LEFT);
        table.setColumnWidth(TABLE_GROUP_NUMBER, 250);

        table.addValueChangeListener(event -> {
            if (table.getValue() != null) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        table.addItemClickListener(event -> {
            if (event.isDoubleClick()) {
                if (event.getItemId() != null) {
                    getUI().addWindow(new ModalGroupWindow(table.getItem(table.getValue()), (Long) table.getValue()));
                }
            }
        });
    }

    private void setClickListeners() {
        addButton.addClickListener(event -> {
            ModalGroupWindow modalGroupWindow = new ModalGroupWindow();
            modalGroupWindow.addCloseListener(e -> refresh());
            getUI().addWindow(modalGroupWindow);
        });
        editButton.addClickListener(event -> {
            ModalGroupWindow modalGroupWindow = new ModalGroupWindow(table.getItem(table.getValue()), (Long) table.getValue());
            modalGroupWindow.addCloseListener(e -> refresh());
            getUI().addWindow(modalGroupWindow);
        });
        deleteButton.addClickListener(event -> delete());
    }

    @Override
    protected void refresh() {
        table.removeAllItems();
        for (Group g : groupService.getGroups()) {
            table.addItem(new Object[]{
                            g.getFaculty(),
                            g.getNumber()},
                    g.getId());
        }
    }

    protected void delete() {
        if (groupService.deleteGroup((Long) table.getValue())) {
            refresh();
        } else {
            Notification.show("Группу нельзя удалить, так как в ней есть студенты!", Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
