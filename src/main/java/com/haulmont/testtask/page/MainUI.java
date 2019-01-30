package com.haulmont.testtask.page;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import static com.haulmont.testtask.page.PageConstants.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
/*
    private Button mainButton;
    private Button groupsButton;
    private Button studentsButton;

    private VerticalLayout mainLayout;
    private HorizontalLayout headerLayout;
    private VerticalLayout viewLayout;*/

    @Override
    protected void init(VaadinRequest request) {
        /*mainLayout = new VerticalLayout();
        mainLayout.setMargin(true);

        headerLayout = new HorizontalLayout();
        headerLayout.setHeight("40px");
        headerLayout.setSizeFull();
        headerLayout.setMargin(false);

        mainButton = new Button("Главная");
        mainButton.addStyleName(ValoTheme.ACCORDION_BORDERLESS);
        mainButton.setHeight("100%");
        mainButton.setData(MAIN_PAGE);
        groupsButton = new Button("Группы");
        groupsButton.addStyleName(ValoTheme.ACCORDION_BORDERLESS);
        groupsButton.setHeight("100%");
        groupsButton.setData(GROUPS_PAGE);
        studentsButton = new Button("Студенты");
        studentsButton.addStyleName(ValoTheme.ACCORDION_BORDERLESS);
        studentsButton.setHeight("100%");
        studentsButton.setData(STUDENTS_PAGE);

        headerLayout.addComponents(mainButton,groupsButton,studentsButton);


        viewLayout = new VerticalLayout();
        viewLayout.setSizeFull();
        viewLayout.setMargin(true);
        viewLayout.setSpacing(true);

        mainLayout.addComponents(headerLayout,viewLayout);*/

        //ViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(mainLayout);
        Navigator navigator = new Navigator(this,this);
        navigator.addView(MAIN_PAGE, new MainView());
        navigator.addView(GROUPS_PAGE, new MainGroupView());
        navigator.addView(STUDENTS_PAGE,new MainStudentView());
        //mainButton.addClickListener((Button.ClickListener) event -> navigator.navigateTo(event.getButton().getData().toString()));
        //groupsButton.addClickListener((Button.ClickListener) event -> navigator.navigateTo(event.getButton().getData().toString()));
    }
}