package com.haulmont.testtask.page;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

abstract class BasicMainView extends VerticalLayout implements View {

    protected HorizontalLayout buttonsLayout;
    protected Table table;
    protected Button addButton;
    protected Button editButton;
    protected Button deleteButton;
    protected Navigator navigator;

    public BasicMainView() {
        table = new Table();
        table.setNullSelectionAllowed(false);
        table.setSelectable(true);
        table.setImmediate(true);
        table.setSizeFull();

        buttonsLayout = new HorizontalLayout();

        addButton = new Button("Добавить");
        editButton = new Button("Редактировать");
        deleteButton = new Button("Удалить");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        buttonsLayout.addComponents(addButton,editButton,deleteButton);
        setMargin(true);
        setSpacing(false);
        addComponents(table,buttonsLayout);
        setExpandRatio(table,1.0f);
    }
    protected abstract void setTable();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
