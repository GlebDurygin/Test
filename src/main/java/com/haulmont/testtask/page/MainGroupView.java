package com.haulmont.testtask.page;

import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.entity.Group;
import com.haulmont.testtask.service.Service;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import java.util.logging.Logger;

import static com.haulmont.testtask.database.impl.HSQLDBConstants.TABLE_GROUP_FACULTY;
import static com.haulmont.testtask.database.impl.HSQLDBConstants.TABLE_GROUP_NUMBER;


public class MainGroupView extends BasicMainView {

    private static Logger logger = Logger.getLogger(MainStudentView.class.getName());

    public MainGroupView() {
        super();
        table.setHeight("550px");
        addComponents(table,buttonsLayout);

        setTable();
        refresh();
    }

    @Override
    protected void setTable() {
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
                if (event.getItemId() != null)
                    getUI().addWindow(new ModalGroupWindow(table.getItem(table.getValue()),(Long)table.getValue()));
            }
        });

        addButton.addClickListener(event -> {
            ModalGroupWindow modalGroupWindow = new ModalGroupWindow();
            modalGroupWindow.addCloseListener(e -> refresh());
            getUI().addWindow(modalGroupWindow);
        });
        editButton.addClickListener(event -> {
            ModalGroupWindow modalGroupWindow = new ModalGroupWindow(table.getItem(table.getValue()),(Long)table.getValue());
            modalGroupWindow.addCloseListener(e -> refresh());
            getUI().addWindow(modalGroupWindow);
        });
        deleteButton.addClickListener(event -> delete());
    }

    @Override
    protected void refresh() {
        table.removeAllItems();
        try {
            for (Group g : Service.getInstance().getGroups()) {
                table.addItem(new Object[]{
                                g.getFaculty(),
                                g.getNumber()},
                        g.getId());
            }
        } catch (ServiceException e) {
            logger.severe(e.getMessage());
        }
    }

    protected void delete() {
        try {
            if (Service.getInstance().deleteGroup((Long)table.getValue()))
                refresh();
            else
                Notification.show("Группу нельзя удалить, так как в ней есть студенты!",Notification.Type.ERROR_MESSAGE);
        } catch (ServiceException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
