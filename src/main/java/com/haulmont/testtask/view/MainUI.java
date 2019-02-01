package com.haulmont.testtask.view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import static com.haulmont.testtask.view.ViewConstants.*;

@Theme(MainThemeConstants.THEME_NAME)
public class MainUI extends UI {

    private Button mainButton;
    private Button groupsButton;
    private Button studentsButton;

    private VerticalLayout mainLayout;
    private HorizontalLayout headerLayout;
    private VerticalLayout viewLayout;

    private Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        initHeaderLayout();
        initViewLayout();
        initMainLayout();
        setContent(mainLayout);
        initNavigator();
    }

    private void initHeaderLayout() {
        headerLayout = new HorizontalLayout();
        headerLayout.setHeight("30px");
        headerLayout.setWidth("100%");
        headerLayout.setMargin(false);

        mainButton = new Button("Главная");
        mainButton.addStyleName(MainThemeConstants.BORDERLESS);
        mainButton.setHeight("100%");
        mainButton.setData(MAIN_PAGE);

        groupsButton = new Button("Группы");
        groupsButton.addStyleName(MainThemeConstants.BORDERLESS);
        groupsButton.setHeight("100%");
        groupsButton.setData(GROUPS_PAGE);

        studentsButton = new Button("Студенты");
        studentsButton.addStyleName(MainThemeConstants.BORDERLESS);
        studentsButton.setHeight("100%");
        studentsButton.setData(STUDENTS_PAGE);

        headerLayout.addComponents(mainButton, groupsButton, studentsButton);
        headerLayout.setStyleName(MainThemeConstants.HEADER_LAYOUT);
    }

    private void initViewLayout() {
        viewLayout = new VerticalLayout();
        viewLayout.setStyleName(MainThemeConstants.VIEW_LAYOUT);
    }

    private void initMainLayout() {
        mainLayout = new VerticalLayout();
        mainLayout.addComponents(headerLayout, viewLayout);
        mainLayout.setExpandRatio(viewLayout, 1.0f);
        mainLayout.setSizeFull();
        mainLayout.setStyleName(MainThemeConstants.MAIN_LAYOUT);
    }

    private void initNavigator() {
        navigator = new Navigator(this, viewLayout);
        navigator.addView(MAIN_PAGE, new MainView());
        navigator.addView(GROUPS_PAGE, new MainGroupView());
        navigator.addView(STUDENTS_PAGE, new MainStudentView());
        mainButton.addClickListener(event -> navigator.navigateTo(mainButton.getData().toString()));
        groupsButton.addClickListener(event -> navigator.navigateTo(groupsButton.getData().toString()));
        studentsButton.addClickListener(event -> navigator.navigateTo(studentsButton.getData().toString()));
    }
}