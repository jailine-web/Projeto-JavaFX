package gui.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Utils {

	public static Stage PalcoAtual(ActionEvent evento) {
		return (Stage) ((Node) evento.getSource()).getScene().getWindow();

	}

	// Facilitar o processo
	public static Integer tentarCoverterParaInt(String str) {
		try {

			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}

	}

	public static <T> void formatarColunaDataDaTabela(TableColumn<T, Date> tableColumn, String formatar) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Date> cell = new TableCell<T, Date>() {
				private SimpleDateFormat sdf = new SimpleDateFormat(formatar);

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						setText(sdf.format(item));
					}
				}
			};
			return cell;
		});
	}

	public static <T> void formatarColunaDoubleDaTabela(TableColumn<T, Double> tableColumn, int lugaresDecimais) {
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {

				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + lugaresDecimais + "f", item));
					}
				}
			};
			return cell;
		});
	}

	public static void formatDatePicker(DatePicker datePicker, String format) {
		datePicker.setConverter(new StringConverter<LocalDate>() {

			DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern(format);
			{
				datePicker.setPromptText(format.toLowerCase());
			}

			@Override
			public String toString(LocalDate data) {
				if (data != null) {
					return dataFormatada.format(data);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dataFormatada);
				} else {
					return null;
				}
			}
		});
	}

}
