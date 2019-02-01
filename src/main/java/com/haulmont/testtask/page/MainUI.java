package com.haulmont.testtask.page;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import static com.haulmont.testtask.page.PageConstants.*;

@Theme(MainThemeConstants.THEME_NAME)
public class MainUI extends UI {

    private Button mainButton;
    private Button groupsButton;
    private Button studentsButton;

    private VerticalLayout mainLayout;
    private HorizontalLayout headerLayout;
    private VerticalLayout viewLayout;
    private VerticalLayout footerLayout;

    @Override
    protected void init(VaadinRequest request) {
        headerLayout = new HorizontalLayout();
        viewLayout = new VerticalLayout();
        mainLayout = new VerticalLayout();
        footerLayout = new VerticalLayout();

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

        headerLayout.addComponents(mainButton,groupsButton,studentsButton);
        mainLayout.addComponents(headerLayout,viewLayout);
        mainLayout.setExpandRatio(viewLayout,1.0f);
        mainLayout.setSizeFull();

        headerLayout.setStyleName(MainThemeConstants.HEADER_LAYOUT);
        viewLayout.setStyleName(MainThemeConstants.VIEW_LAYOUT);
        mainLayout.setStyleName(MainThemeConstants.MAIN_LAYOUT);

        setContent(mainLayout);

        Navigator navigator = new Navigator(this, viewLayout);
        navigator.addView(MAIN_PAGE, new MainView());
        navigator.addView(GROUPS_PAGE, new MainGroupView());
        navigator.addView(STUDENTS_PAGE, new MainStudentView());
        mainButton.addClickListener(event -> navigator.navigateTo(mainButton.getData().toString()));
        groupsButton.addClickListener(event -> navigator.navigateTo(groupsButton.getData().toString()));
        studentsButton.addClickListener(event -> navigator.navigateTo(studentsButton.getData().toString()));
    }
}