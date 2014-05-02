package com.cs429.todorpg.revised;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.cs429.todorpg.revised.model.Daily;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

/**
 * This class sets the calendar view and simple task handling for clicking the
 * corresponding day button moves onto the day view
 * 
 * @author ssong25, hlim10
 * 
 */
public class CalendarView extends Activity implements OnClickListener {

	private Button prev;
	private Button next;
	private TextView selectedText;
	private TextView currentMonthText;
	private GridView gridView;
	private GridCell gridCell;
	private Calendar cal;
	private int month, year;
	private final DateFormat dateFormat = new DateFormat();
	private final String dateTemplate = "MMMM yyyy";

	public static String date_month_year = "selected date";

	/**
	 * First function to be called automatically when Application is turned on.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smart_calendar);

		cal = Calendar.getInstance(Locale.getDefault());
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		prev = (Button) this.findViewById(R.id.prev);
		prev.setOnClickListener(this);
		next = (Button) this.findViewById(R.id.next);
		next.setOnClickListener(this);

		selectedText = (TextView) this.findViewById(R.id.selected);
		currentMonthText = (TextView) this.findViewById(R.id.current_month);
		currentMonthText
				.setText(dateFormat.format(dateTemplate, cal.getTime()));

		gridView = (GridView) this.findViewById(R.id.gridview);
		gridCell = new GridCell(getApplicationContext(), R.id.gridcell_button,
				month, year);
		gridCell.notifyDataSetChanged();
		gridView.setAdapter(gridCell);

		findViewById(R.id.calendar_okay).setOnClickListener(this);
		findViewById(R.id.calendar_cancel).setOnClickListener(this);

	}

	public void updateGridCell(int month, int year) {
		gridCell = new GridCell(getApplicationContext(), R.id.gridcell_button,
				month, year);
		cal.set(year, month, cal.get(Calendar.DAY_OF_MONTH));
		currentMonthText
				.setText(dateFormat.format(dateTemplate, cal.getTime()));
		gridCell.notifyDataSetChanged();
		gridView.setAdapter(gridCell);
	}

	/**
	 * Listener function that defines what action to take when "Prev" or "Next"
	 * button is clicked.
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == prev) {
			if (month == 0) {
				month = 11;
				year--;
			} else {
				month--;
			}
			updateGridCell(month, year);
		}
		if (v == next) {
			if (month == 11) {
				month = 0;
				year++;
			} else {
				month++;
			}
			updateGridCell(month, year);
		}
		if (v.getId() == R.id.calendar_okay) {// okay button
			Log.d("[Calendar]", "okay button pressed");

			Intent intent = new Intent(CalendarView.this, ToDoActivity.class);
			intent.putExtra("DATE", date_month_year);
			intent.putExtra("pos", getIntent().getIntExtra("pos", -1));
			setResult(RESULT_OK, intent);
			finish();
		}
		if (v.getId() == R.id.calendar_cancel) {// cancel button
			Log.d("[Calendar]", "cancel button pressed");
			finish();
		}
	}

	/**
	 * GridCell class draws 2-dimensional (2D-array like) buttons and defines
	 * how to display dates and months.
	 * 
	 * @author dshin9
	 */
	public class GridCell extends BaseAdapter implements OnClickListener {

		private static final int DAY_OFFSET = 1;
		private int month, year;
		private int numDays, prevNumDays, dateOfToday;
		private Context context;
		private ArrayList<String> list;
		private Button gridCellButton;
		private final String[] months = new String[] { "1", "2", "3", "4", "5",
				"6", "7", "8", "9", "10", "11", "12" };
		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
				31, 30, 31 };

		/**
		 * Constructor with parameter for GridCell class.
		 */
		public GridCell(Context context, int textViewResourceID, int month,
				int year) {
			super();
			this.context = context;
			this.list = new ArrayList<String>();
			this.month = month;
			this.year = year;

			Calendar calendar = Calendar.getInstance();
			setDateOfToday(calendar.get(Calendar.DAY_OF_MONTH));

			updateDayAndMonth(month, year);
		}

		/**
		 * Function that convert month (integer value) as a String.
		 */
		private String getMonthAsString(int i) {
			return months[i];
		}

		/**
		 * Function that retrieve number of days of for a specific month.
		 */
		private int getNumberOfDays(int i) {
			return daysOfMonth[i];
		}

		/**
		 * Function that retrieve today's date.
		 */
		public int getDateOfToday() {
			return dateOfToday;
		}

		/**
		 * Function that sets today's date.
		 */
		private void setDateOfToday(int dateOfToday) {
			this.dateOfToday = dateOfToday;
		}

		/**
		 * Function that needs to be overwritten. It returns size of the list
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		/**
		 * Function that needs to be overwritten. It returns String that is
		 * saved in ArrayList<String>'s inputed position
		 */
		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		/**
		 * Function that updates number of days upon corresponding month. It
		 * checks if its year is a leaper year or not
		 */
		private void updateDayAndMonth(int month, int year) {
			GregorianCalendar geoCalendar;
			String currentMonth;
			int currentWeekDay;
			int prevMonth, nextMonth, prevYear, nextYear;

			geoCalendar = new GregorianCalendar(year, month, 1);
			currentMonth = getMonthAsString(month);
			currentWeekDay = geoCalendar.get(Calendar.DAY_OF_WEEK) - 1;
			numDays = getNumberOfDays(month);

			if (month == 11) {
				prevMonth = month - 1;
				nextMonth = 0;
				prevYear = year;
				nextYear = year + 1;
				prevNumDays = getNumberOfDays(prevMonth);
			} else if (month == 0) {
				prevMonth = 11;
				nextMonth = month + 1;
				prevYear = year - 1;
				nextYear = year;
				prevNumDays = getNumberOfDays(prevMonth);
			} else {
				prevMonth = month - 1;
				nextMonth = month + 1;
				prevYear = year;
				nextYear = year;
				prevNumDays = getNumberOfDays(prevMonth);
			}

			if (geoCalendar.isLeapYear(geoCalendar.get(Calendar.YEAR))
					&& month == 1) {
				++numDays;
			}

			for (int i = 0; i < currentWeekDay; i++) {
				list.add(String
						.valueOf((prevNumDays - currentWeekDay + DAY_OFFSET)
								+ i)
						+ ",FromPrevMonthDays,"
						+ getMonthAsString(prevMonth)
						+ "," + prevYear);
			}

			for (int i = 1; i <= numDays; i++) {
				if (i == getDateOfToday()) {
					list.add(String.valueOf(i) + ",Today,"
							+ getMonthAsString(month) + "," + year);
				} else {
					list.add(String.valueOf(i) + ",CurrMonthDays,"
							+ getMonthAsString(month) + "," + year);
				}
			}

			for (int i = 0; i < list.size() % 7; i++) {
				list.add(String.valueOf(i + 1) + ",OverlapNextMonthDays,"
						+ getMonthAsString(nextMonth) + "," + nextYear);
			}
		}

		/**
		 * Function that needs to be overwritten. We do not need this function
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 * Function that updates what needs to be displayed
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View row = convertView;
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.grid_cell, parent, false);
			}

			gridCellButton = (Button) row.findViewById(R.id.gridcell_button);
			gridCellButton.setOnClickListener(this);

			String[] singleDayInfo = list.get(position).split(",");
			String thedate = singleDayInfo[0];
			String textAttr = singleDayInfo[1];
			String themonth = singleDayInfo[2];
			String theyear = singleDayInfo[3];

			gridCellButton.setText(thedate);
			gridCellButton.setTag(themonth + "/" + thedate + "/" + theyear);

			if (textAttr.equals("FromPrevMonthDays")) {
				gridCellButton.setTextColor(Color.BLACK);
			}
			if (textAttr.equals("CurrMonthDays")) {
				gridCellButton.setTextColor(Color.WHITE);
			}
			if (textAttr.equals("Today")) {
				gridCellButton.setTextColor(Color.RED);
			}
			if (textAttr.equals("OverlapNextMonthDays")) {
				gridCellButton.setTextColor(Color.BLACK);
			}
			return row;
		}

		/**
		 * Listener function that defines what action to take when one of date
		 * (made out of button) is clicked.
		 */
		@Override
		public void onClick(View v) {

			date_month_year = (String) v.getTag();
			selectedText.setText(date_month_year);// month/day/year
			selectedText.setTextSize(22);
		}
	}
}
