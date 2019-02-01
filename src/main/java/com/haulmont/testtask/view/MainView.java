package com.haulmont.testtask.view;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import static com.haulmont.testtask.view.ViewConstants.*;

public class MainView extends VerticalLayout implements View {
    private Navigator navigator;
    private Button goToMainGroupView;
    private Button goToMainStudentView;

    public MainView() {
        setStyleName(MainThemeConstants.VIEW_LAYOUT);
        Label title = new Label("Система ввода и отображения информации о студентах института");
        Button.ClickListener listener = (Button.ClickListener) event -> navigator.navigateTo(event.getButton().getData().toString());

        goToMainGroupView = new Button("Отображение списка групп");
        goToMainGroupView.setData(GROUPS_PAGE);
        goToMainGroupView.addClickListener(listener);
        goToMainGroupView.setStyleName(MainThemeConstants.LINK_BUTTON);

        goToMainStudentView = new Button("Отображение списка студентов");
        goToMainStudentView.setData(STUDENTS_PAGE);
        goToMainStudentView.addClickListener(listener);
        goToMainStudentView.setStyleName(MainThemeConstants.LINK_BUTTON);

        addComponents(title,goToMainGroupView, goToMainStudentView);
        setMargin(false);
        setSizeFull();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        navigator = event.getNavigator();
    }
}
