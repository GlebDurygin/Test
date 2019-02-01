package com.haulmont.testtask.page;

import com.haulmont.testtask.exception.service.ServiceException;
import com.haulmont.testtask.model.entity.Student;
import com.haulmont.testtask.service.Service;
import com.vaadin.data.util.converter.StringToDateConverter;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Logger;

import static com.haulmont.testtask.database.hsqldbdao.HSQLDBConstants.*;

public class MainStudentView extends BasicMainView {
    private static Logger logger = Logger.getLogger(MainStudentView.class.getName());
    private HorizontalLayout filterLayout;
    private Button submitButton;
    private TextField filterLastNameTextField;
    private TextField filterNumberTextField;

    public MainStudentView() {
        super();
        setFilterLayout();
        setTable();
        refresh();
    }

    private void setFilterLayout() {
        filterLayout = new HorizontalLayout();
        filterLastNameTextField = new TextField("Фамилия");
        filterLastNameTextField.setHeight("100%");
        filterLastNameTextField.setMaxLength(15);
        filterLastNameTextField.addValueChangeListener(event -> refresh());

        filterNumberTextField = new TextField("Номер группы");
        filterNumberTextField.setHeight("100%");
        filterNumberTextField.setMaxLength(15);
        filterNumberTextField.addValueChangeListener(event -> refresh());

        submitButton = new Button("Применить");
        submitButton.setHeight("30px");
        submitButton.addClickListener(event -> refresh());

        filterLayout.addComponents(filterLastNameTextField, filterNumberTextField, submitButton);
        filterLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        filterNumberTextField.setSizeFull();
        addComponents(filterLayout, table, buttonsLayout);
        filterLayout.setHeight("50px");
        table.setHeight("500px");
    }

    @Override
    protected void setTable() {
        table.addContainerProperty(TABLE_STUDENT_LAST_NAME, String.class, null, "Фамилия", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_FIRST_NAME, String.class, null, "Имя", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_MIDDLE_NAME, String.class, null, "Отчество", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_STUDENT_BIRTHDATE, Date.class, null, "Дата рождения", null, Table.Align.LEFT);
        table.addContainerProperty(TABLE_GROUP_NUMBER, Integer.class, null, "Номер группы", null, Table.Align.LEFT);
        table.setConverter(TABLE_STUDENT_BIRTHDATE, new StringToDateConverter() {
            @Override
            public DateFormat getFormat(Locale locale) {
                return new SimpleDateFormat("dd.MM.yyyy");
            }
        });

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
                    getUI().addWindow(new ModalStudentWindow(table.getItem(table.getValue()), (Long) table.getValue()));
            }
        });

        addButton.addClickListener(event -> {
            ModalStudentWindow modalStudentWindow = new ModalStudentWindow();
            modalStudentWindow.addCloseListener((Window.CloseListener) e -> refresh());
            getUI().addWindow(modalStudentWindow);
        });
        editButton.addClickListener(event -> {
            ModalStudentWindow modalStudentWindow = new ModalStudentWindow(table.getItem(table.getValue()), (Long) table.getValue());
            modalStudentWindow.addCloseListener((Window.CloseListener) e -> refresh());
            getUI().addWindow(modalStudentWindow);
        });
        deleteButton.addClickListener(event -> {
            try {
                Service.getInstance().deleteStudent((Long) table.getValue());
                refresh();
            } catch (ServiceException e) {
                logger.severe(e.getMessage());
            }
        });
    }

    @Override
    protected void refresh() {
        table.removeAllItems();
        try {
            for (Student s : Service.getInstance().getStudents(filterLastNameTextField.getValue(), filterNumberTextField.getValue())) {
                table.addItem(new Object[]{
                        s.getLastName(),
                        s.getFirstName(),
                        s.getMiddleName(),
                        s.getBirthDate(),
                        Service.getInstance().getGroup(s.getGroupId()).getNumber()
                }, s.getId());
            }
        } catch (ServiceException e) {
            logger.severe(e.getMessage());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
