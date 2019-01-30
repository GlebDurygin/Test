package com.haulmont.testtask.page;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import static com.haulmont.testtask.model.dao.DaoConstants.*;

public class MainGroupView extends BasicMainView {

    public MainGroupView() {
        super();
        setTable();

        addButton.addClickListener(event -> getUI().addWindow(new ModalGroupWindow()));
    }

    @Override
    protected void setTable() {
        table.addContainerProperty(TABLE_GROUP_FACULTY,String.class,null,"Факультет", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_GROUP_NUMBER,Integer.class,null,"Номер группы", null, Table.Align.LEFT);
        table.setColumnWidth(TABLE_GROUP_NUMBER,250);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing MainGroupView page");
    }
}
