package com.example.womenshop.util;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.StringConverter;

import java.util.function.Function;

public class UIUtils {
    public static <T> void setupComboBoxDisplay(ComboBox<T> comboBox, Function<T, String> textProvider) {
        // Change l'affichage String des catégories
        comboBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : textProvider.apply(item));
            }
        });
        comboBox.setButtonCell(comboBox.getCellFactory().call(null));

        //  Change l'affichage String de la catégorie du produit selectionné
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(T item) {
                return item == null ? "" : textProvider.apply(item);
            }

            @Override
            public T fromString(String string) {
                return null; // pas utilisé
            }
        });
    }


    public static <T> void setupListViewDisplay(ListView<T> listView, Function<T, String> textProvider) {
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : textProvider.apply(item));
            }
        });
    }

}
