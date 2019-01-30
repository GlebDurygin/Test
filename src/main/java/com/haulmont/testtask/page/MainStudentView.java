package com.haulmont.testtask.page;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;

import java.sql.Date;

import static com.haulmont.testtask.model.dao.DaoConstants.*;

public class MainStudentView extends BasicMainView {

    public MainStudentView() {
        super();
        setTable();

        addButton.addClickListener(event -> getUI().addWindow(new ModalStudentWindow()));
    }

    @Override
    protected void setTable() {
        table.addContainerProperty(TABLE_STUDENT_LAST_NAME,String.class,null,"Фамилия", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_FIRST_NAME,String.class,null,"Имя", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_MIDDLE_NAME,String.class,null,"Отчество", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_BIRTHDATE, Date.class,null,"Дата рождения", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_GROUP_ID,Long.class,null,"Номер группы", null, Table.Align.LEFT);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing MainStudentView page");
    }
}
