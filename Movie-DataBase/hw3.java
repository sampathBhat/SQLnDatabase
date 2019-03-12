
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class hw3 extends MouseAdapter implements ActionListener, FocusListener, PopupMenuListener {

	private static final int FONT_SIZE = 16;

	private Connection conn = null;
	private JFrame mainFrame;
	private boolean workerRunning;
	private DefaultListModel<SelectableItem> genres = new DefaultListModel<>();
	private DefaultListModel<SelectableItem> countries = new DefaultListModel<>();
	private DefaultListModel<SelectableItem> tagsList = new DefaultListModel<>();
	private List<JComboBox<String>> actorFields = new ArrayList<>(4);
	private JComboBox<String> directorField = new JComboBox<>(new DefaultComboBoxModel<>());
	private JTextField tagWeightField = new JTextField("0", 2);
	private JComboBox<String> weightComparatorBox = new JComboBox<String>(new String[] { "=", "<", ">", "<=", ">=" });
	private JTextField fromYearField = new JTextField("", 4);
	private JTextField toYearField = new JTextField("", 4);
	private UserIDSearch userIDSearch = null;
	private JComboBox<String> and_or_choose_box = new JComboBox<>(
			new String[] { "Select OR between attributes", "Select AND between attributes" });
	private JTextArea queryArea = new JTextArea("SQL QUERY: ");
	private JLabel resultLabel = new JLabel("Movie Result");
	private JLabel userResultLabel = new JLabel("User Result");
	private MovieResultTable movieTable = new MovieResultTable();
	private UserResultTabel userTable = new UserResultTabel();

	class MovieObj {
		int id;
		String title;
		int year;
		double avg_RT_audienceRating;
		int num_RT_audicenceReviews;

		String countries;
		String genre;

		public MovieObj(ResultSet rs) throws SQLException {
			id = rs.getInt("ID");
			title = rs.getString("title");
			year = rs.getInt("year");
			avg_RT_audienceRating = rs.getDouble("rating");
			num_RT_audicenceReviews = rs.getInt("numOfRating");
			countries = "";
			genre = "";
		}

		public String toString() {
			return title + " (ID: " + id + ")";
		}
	}

	class UserResultTabel extends AbstractTableModel {
		private List<String> data = new ArrayList<>();

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {

			return 1;
		}

		@Override
		public String getColumnName(int column) {
			return "User ID";
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return data.get(rowIndex);
		}

		public void addElement(String s) {
			data.add(s);
			int row = data.size() - 1;
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					fireTableCellUpdated(row, 1);
				}
			});
		}

		public void clear() {
			data.clear();
		}
	}

	class MovieResultTable extends AbstractTableModel {

		private String[] columnNames = new String[] { "ID", "Title", "Year", "Countries", "Genre", "RT Audience Rating",
				"RT Audience Num Of Reviews" };

		private List<MovieObj> data = new ArrayList<>();

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(int column) {
			return columnNames[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			MovieObj item = data.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return item.id;
			case 1:
				return item.title;
			case 2:
				return item.year;
			case 3:
				return item.countries;
			case 4:
				return item.genre;
			case 5:
				return item.avg_RT_audienceRating;
			case 6:
				return item.num_RT_audicenceReviews;
			default:
				return null;
			}
		}

		public void clear() {
			data.clear();
		}

		public void addElement(MovieObj item) {
			data.add(item);

			int row = data.size() - 1;

			// Get movie's country
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					String sql = "SELECT DISTINCT l.country "
							+ "FROM Movie m INNER JOIN COUNTRY l ON m.ID = l.movie_id " + "WHERE m.ID = " + item.id
							+ " ORDER BY l.country";

					new ListValuesQuery(sql) {
						@Override
						public void onResult(List<String> result) {
							StringBuilder sb = new StringBuilder();
							for (String v : result) {
								sb.append(v).append(", ");
							}
							item.countries = (sb.length() > 0) ? sb.substring(0, sb.length() - 2) : "";
							fireTableCellUpdated(row, 3);
						}
					};
				}
			});
			// Get movie's genres
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					String sql = "SELECT g.genre " + "FROM Movie m INNER JOIN Genre g ON m.ID = g.movie_id "
							+ "WHERE m.ID = " + item.id + " ORDER BY g.genre";

					new ListValuesQuery(sql) {
						@Override
						public void onResult(List<String> result) {
							StringBuilder sb = new StringBuilder();
							for (String v : result) {
								sb.append(v).append(", ");
							}
							item.genre = (sb.length() > 0) ? sb.substring(0, sb.length() - 2) : "";
							fireTableCellUpdated(row, 4);
						}
					};
				}
			});
		}
	}

	class SelectableItem {
		String value;
		boolean selected;

		public SelectableItem(String v) {
			value = v;
			selected = false;
		}
	}

	class SelectableItemRenderer extends JCheckBox implements ListCellRenderer<SelectableItem> {

		@Override
		public Component getListCellRendererComponent(JList<? extends SelectableItem> list, SelectableItem value,
				int index, boolean isSelected, boolean cellHasFocus) {

			setEnabled(list.isEnabled());
			setSelected(value.selected);
			setText(value.value);

			return this;
		}

	}

	abstract class DropDownSearch implements DocumentListener {

		JComboBox<String> actorField;

		boolean searching = false;

		public DropDownSearch(JComboBox<String> combo) {
			actorField = combo;

			((JTextComponent) combo.getEditor().getEditorComponent()).getDocument().addDocumentListener(this);
		}

		private synchronized void searchText(String text) {

			if (text.length() == 0)
				return;

			if (searching)
				return;

			// Making sure the typed text does not appear in the dropdown
			DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) actorField.getModel();
			if (model.getIndexOf(text) > 0) {
				return;
			}

			searching = true;

			new ListValuesQuery(buildQuery(text)) {

				@Override
				public void onResult(List<String> result) {
					actorField.hidePopup();
					DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) actorField.getModel();
					model.removeAllElements();
					model.addElement(text);

					if (result != null) {
						for (String name : result) {
							model.addElement(name);
						}
					}

					actorField.showPopup();
					searching = false;

				}
			};
			listTags();
		}

		protected abstract String buildQuery(String text);

		@Override
		public void insertUpdate(DocumentEvent e) {
			try {
				Document d = e.getDocument();
				searchText(d.getText(0, d.getLength()));
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			try {
				Document d = e.getDocument();
				searchText(d.getText(0, d.getLength()));
			} catch (BadLocationException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
		}
	}

	class CastSearch extends DropDownSearch {

		public CastSearch(JComboBox<String> combo) {
			super(combo);
		}

		@Override
		protected String buildQuery(String text) {
			String select = "SELECT DISTINCT c.actor_name FROM ACTORS c";
			String where = " WHERE c.actor_name LIKE '" + text + "%'";

			if (and_or_choose_box.getSelectedIndex() == 0) {
				// Search actors by genres and countries

				// List of selected genres and countries
				String selectedGenres = getValuesFrom(genres);
				String selectedCountries = getValuesFrom(countries);

				// select actors by genres
				if (selectedGenres.length() > 0) {
					select += ", Genre g";
					where += " AND g.movie_id = c.movie_id AND g.genre IN (" + selectedGenres + ")";
				}

				// select actor by countries
				if (selectedCountries.length() > 0) {
					select += ", COUNTRY l ";
					where += " AND l.movie_ID=c.movie_id AND l.country IN (" + selectedCountries + ")";
				}
			}

			String sql = select + where + " ORDER BY c.actor_name";
			System.out.println("In the actor Search " + sql);
			return sql;
		}
	}

	class DirectorSearch extends DropDownSearch {

		public DirectorSearch(JComboBox<String> combo) {
			super(combo);
		}

		@Override
		protected String buildQuery(String text) {
			String select = "SELECT DISTINCT d.directorName FROM Directors d ";
			String where = " WHERE  d.directorName LIKE '" + text + "%'";

			if (and_or_choose_box.getSelectedIndex() == 0) {
				// Search director by genres and countries

				// List of selected genres and countries
				String selectedGenres = getValuesFrom(genres);
				String selectedCountries = getValuesFrom(countries);

				// select director by genres
				if (selectedGenres.length() > 0) {
					select += ", Genre g ";
					where += " AND g.movie_id = d.movie_id AND g.genre IN (" + selectedGenres + ")";
				}

				// select director by countries
				if (selectedCountries.length() > 0) {
					select += ", COUNTRY l ";
					where += " AND l.movie_id=d.movie_id AND l.country IN (" + selectedCountries + ")";
				}
			}

			String sql = select + where + " ORDER BY directorName";
			System.out.println("The director SQL is " + sql);
			return sql;
		}
	}

	public hw3() {

		connect_to_DB();

		initGUI();
	}

	private void initGUI() {

		mainFrame = new JFrame("Movie Search");
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeDB();
			}
		});

		mainFrame.add(createSearchPanel(), BorderLayout.PAGE_START);
		mainFrame.add(createResultPanel(), BorderLayout.CENTER);

		listGenres();
		InitializeMovieYear();
		listCountries();
		listTags();
		buildQuery();

		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private JPanel createSearchPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 5));

		JPanel container;

		// Panel to select Genres
		container = new JPanel();
		container.setBorder(new TitledBorder("Genres"));
		container.setLayout(new GridLayout(1, 1));
		container.add(createCheckboxList(genres));
		panel.add(container);

		container = new JPanel(new BorderLayout());
		fromYearField.addActionListener(this);
		fromYearField.addFocusListener(this);

		toYearField.addActionListener(this);
		toYearField.addFocusListener(this);
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setBorder(new TitledBorder("Movie Year"));
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(new JLabel("From:"));
		panel1.add(fromYearField);
		panel1.add(new JLabel("To:"));
		panel1.add(toYearField);
		container.add(panel1, BorderLayout.NORTH);
		panel.add(container);

		// Panel to select Countries
		container = new JPanel();
		container.setBorder(new TitledBorder("Country"));
		container.setLayout(new GridLayout(1, 1));
		container.add(createCheckboxList(countries));
		panel.add(container);

		// Panel to search Cast
		container = new JPanel(new BorderLayout());
		{
			JPanel inner = new JPanel();
			inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
			createActorSearchPanel(inner);
			container.add(inner, BorderLayout.NORTH);
		}
		panel.add(container);

		// Panel to search by Rating
		container = new JPanel(new BorderLayout());
		{
			JPanel inner = new JPanel();
			inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
			createTagWeightPanel(inner);
			container.add(inner, BorderLayout.NORTH);
		}
		panel.add(container);

		// Panel for Tags and Value
		container = new JPanel();
		container.setBorder(new TitledBorder("Tag ids and Value"));
		container.setLayout(new GridLayout(1, 1));
		container.add(createCheckboxList(tagsList));
		panel.add(container);

		return panel;
	}

	private Component createResultPanel() {

		JPanel queryPane = new JPanel(new BorderLayout());

		queryArea.setEditable(false);
		queryArea.setSize(1000, 50);
		queryArea.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		queryArea.setLineWrap(true);
		queryArea.setWrapStyleWord(true);
		queryPane.add(new JLabel("SQL QUERY:"));
		queryPane.add(new JScrollPane(queryArea), BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		and_or_choose_box.addActionListener(this);
		panel.add(and_or_choose_box);
		queryPane.add(panel, BorderLayout.NORTH);

		JButton execButton = new JButton("Execute Movie Query");
		execButton.setActionCommand("ExecuteMovieQuery");
		execButton.addActionListener(this);

		JButton userExecButton = new JButton("Execute User Query");
		userExecButton.setActionCommand("ExecuteUserQuery");
		userExecButton.addActionListener(this);

		JPanel movieResultPane = new JPanel(new BorderLayout());
		JPanel userResultPane = new JPanel(new BorderLayout());
		movieResultPane.add(resultLabel, BorderLayout.NORTH);
		userResultPane.add(userResultLabel, BorderLayout.NORTH);

		movieResultPane.add(execButton, BorderLayout.SOUTH);
		userResultPane.add(userExecButton, BorderLayout.SOUTH);

		JTable resultTable = new JTable(movieTable);
		resultTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		resultTable.setCellSelectionEnabled(true);

		userIDSearch = new UserIDSearch(resultTable);
		JTable userResultTable = new JTable(userTable);
		movieResultPane.add(new JScrollPane(resultTable), BorderLayout.CENTER);
		userResultPane.add(new JScrollPane(userResultTable), BorderLayout.CENTER);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, queryPane, movieResultPane);
		splitPane.setPreferredSize(new Dimension(1000, 500));
		splitPane.setDividerLocation(200);
		splitPane.setResizeWeight(0.3);
		JSplitPane finalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, userResultPane);
		finalSplitPane.setPreferredSize(new Dimension(1900, 700));
		finalSplitPane.setDividerLocation(450);
		finalSplitPane.setResizeWeight(0.3);

		return finalSplitPane;
	}

	/* Create a scrollable list box that shows list of SelectableItems */
	private Component createCheckboxList(ListModel<SelectableItem> data) {

		JList<SelectableItem> list = new JList<>(data);

		list.setCellRenderer(new SelectableItemRenderer());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(this);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(100, 250));
		return listScroller;
	}

	/* Create a panel containing components to search for actors */
	private void createActorSearchPanel(JPanel container) {

		JPanel castPanel = new JPanel(new GridLayout(4, 1));
		castPanel.setBorder(new TitledBorder("Actor / Actress"));
		container.add(castPanel);

		for (int i = 0; i < 4; i++) {
			JComboBox<String> actorField = new JComboBox<>(new DefaultComboBoxModel<String>());
			actorFields.add(actorField);

			actorField.setEditable(true);
			actorField.getEditor().getEditorComponent().addFocusListener(this);
			new CastSearch(actorField);

			castPanel.add(actorField);
		}

		JPanel directorPanel = new JPanel(new GridLayout(1, 1));
		directorPanel.setBorder(new TitledBorder("Director"));
		container.add(directorPanel);
		{
			directorField = new JComboBox<>(new DefaultComboBoxModel<String>());
			directorField.setEditable(true);
			directorField.getEditor().getEditorComponent().addFocusListener(this);
			new DirectorSearch(directorField);

			directorPanel.add(directorField);
		}

	}

	/* Create a panel containing component for Tag Weight */
	private void createTagWeightPanel(JPanel container) {

		JPanel panel;

		// Add listeners
		weightComparatorBox.addActionListener(this);
		tagWeightField.addActionListener(this);
		tagWeightField.addFocusListener(this);

		panel = new JPanel();
		panel.setBorder(new TitledBorder("Tag Weight"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(weightComparatorBox);
		panel.add(tagWeightField);
		container.add(panel);
		container.add(Box.createVerticalGlue());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if ("ExecuteMovieQuery".equals(cmd)) {
			executeQuery();

		} else if ("ExecuteUserQuery".equals(cmd)) {
			executeUserQuery();
		} else {
			buildQuery();

			if (e.getSource() == and_or_choose_box) {
				listCountries();
				listTags();
			}
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.isTemporary()) {
			return;
		}

		Object src = e.getSource();
		if (src == tagWeightField) {
			getInt(tagWeightField.getText(), true);
		} else if (src == fromYearField) {
			getInt(fromYearField.getText(), true);
			listCountries();
			listTags();
		} else if (src == toYearField) {
			getInt(toYearField.getText(), true);
			listCountries();
			listTags();
		} else if (src == directorField.getEditor().getEditorComponent()) {

			String director = (String) directorField.getSelectedItem();
			if (director != null && director.length() > 0)
				new ListValuesQuery(
						"SELECT d.directorName FROM directors d WHERE d.directorName='" + escapeSQL(director) + "'") {
					@Override
					public void onResult(List<String> result) {
						if (result == null || result.size() == 0) {
							JOptionPane.showMessageDialog(mainFrame, "Director name: " + director + " not found",
									"Invalid Input", JOptionPane.ERROR_MESSAGE);
						}
					}
				};

		} else {

			for (JComboBox<String> actorField : actorFields) {
				if (src == actorField.getEditor().getEditorComponent()) {
					String actor = (String) actorField.getSelectedItem();
					if (actor != null && actor.length() > 0)
						new ListValuesQuery(
								"SELECT A.actor_name FROM ACTORS A WHERE A.actor_name='" + escapeSQL(actor) + "'") {
							@Override
							public void onResult(List<String> result) {
								if (result == null || result.size() == 0) {
									JOptionPane.showMessageDialog(mainFrame, "Actor name: " + actor + " not found",
											"Invalid Input", JOptionPane.ERROR_MESSAGE);
								}
							}
						};
				}
			}
		}

		buildQuery();
	}

	@Override
	public void focusGained(FocusEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (workerRunning) {
			return;
		}

		Object source = event.getSource();
		if (source instanceof JList) {
			JList list = (JList) source;

			int index = list.locationToIndex(event.getPoint());
			if (index >= 0) {
				ListModel model = list.getModel();

				SelectableItem item = (SelectableItem) model.getElementAt(index);
				item.selected = !item.selected;

				list.repaint(list.getCellBounds(index, index));
				buildQuery();
				if (model == genres) {
					// update countries list
					listCountries();
					listTags();
				} else if (model == countries) {
					listTags();
				}
			}
		}
	}

	/**
	 * Build the query from current state of the search parameters
	 */
	private void buildQuery() {
		List<String> conditions = new ArrayList<String>();

		// AND or OR between conditions
		String conditionOp = and_or_choose_box.getSelectedIndex() == 0 ? "OR" : "AND";
		// List of selected genres
		String selectedGenres = getValuesFrom(genres);
		if (selectedGenres.length() == 0) {
			queryArea.setText("");
			return; // at least one genre is required
		}
		String select = "";
		if (conditionOp.contains("OR")) {
			select = "SELECT DISTINCT m.ID, m.title, m.year, " + "m.numOfRating, m.rating " + "FROM Movie m, Genre g ";
		} else {
			select = "SELECT DISTINCT m.ID, m.title, m.year, " + "m.numOfRating, m.rating " + "FROM Movie m ";
		}
		if (conditionOp.contains("OR"))
			conditions.add("(m.ID = g.movie_id AND g.genre IN (" + selectedGenres + "))");
		else {
			conditions.add(" m.ID IN (" + joinForAndGenre(selectedGenres) + " )");
		}

		// List of selected countries
		String selectedCountries = getValuesFrom(countries);
		if (selectedCountries.length() > 0) {
			select += ", COUNTRY l ";
			if (conditionOp.contains("OR"))
				conditions.add(" l.movie_id=m.ID AND l.country IN (" + selectedCountries + ") ");
			else {
				conditions.add(" m.ID IN ( " + joinForAndCountry(selectedCountries) + " )");
			}
		}

		// Director
		if (directorField.getSelectedItem() != null) {
			String director = directorField.getSelectedItem().toString();
			if (director.length() > 0) {
				select += ", directors d ";
				conditions.add(" (m.id = d.movie_id AND d.directorName = '" + escapeSQL(director) + "')");
			}
		}

		// Actor list
		String selectedActors = joinActors(actorFields);
		if (selectedActors.length() > 0) {
			select += " , ACTORS a ";
			if (conditionOp.contains("OR"))
				conditions.add(" (a.movie_id = m.ID AND a.actor_name IN (" + selectedActors + ")) ");
			else {
				conditions.add(" m.ID IN ( " + joinForAndActor(selectedActors) + " )");
			}
		}

		// Movie Year
		int fromYear = getInt(fromYearField.getText(), false);
		int toYear = getInt(toYearField.getText(), false);
		if (fromYear > 0) {
			conditions.add(" m.year >= " + fromYear);
		}
		if (toYear > 0) {
			conditions.add(" m.year <= " + toYear);
		}

		// Rating
		int rating = getInt(tagWeightField.getText(), false);
		if (rating > 0) {
			select += ", movie_tag mt";
			String op = String.valueOf(weightComparatorBox.getSelectedItem());
			conditions.add(" (mt.movie_id = m.id AND mt.tagWieght " + op + " " + rating + " ) ");
		}
		// Tag Values

		String selectedTagID = joinTags(tagsList);
		if (selectedTagID.length() > 0) {
			if (!select.contains("movie_tag"))
				select += ", movie_tag mt ";
			if (conditionOp.contains("OR"))
				conditions.add(" mt.movie_id = m.id AND mt.tagID IN ( " + selectedTagID + " ) ");
			else {
				conditions.add(" m.ID IN ( " + joinForAndTags(selectedTagID) + " )");
			}
		}

		// Show the query
		queryArea.setText(select);

		if (conditions.size() > 0) {
			queryArea.append("\nWHERE " + conditions.get(0));
			for (int i = 1; i < conditions.size(); i++) {
				queryArea.append("\n  " + " AND " + " " + conditions.get(i));
			}
		}
	}

	/**
	 * Execute the query in the query text area
	 */
	private void executeQuery() {
		buildQuery();

		String sql = queryArea.getText();
		if (sql.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please select at least one genre", "Executing Query",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		movieTable.clear();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				MovieObj item = new MovieObj(rs);
				movieTable.addElement(item);
			}

			rs.close();
			stmt.close();
			resultLabel.setText(movieTable.getRowCount() + " Movies Found");
			movieTable.fireTableDataChanged();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Query Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/*
	 * Execute User Query
	 */

	private void executeUserQuery() {
		try {
			String movie_id = userIDSearch.EventListener();
			String tagID = joinTags(tagsList);
			if (movie_id.isEmpty() || tagID.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please select at least one movie|Tag", "Executing Query",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			String sql = "SELECT U.user_id FROM USER_TAG U WHERE U.movie_id IN ( " + movie_id + " )"
					+ " AND U.tag_id IN ( " + tagID + " ) ORDER BY U.user_id";

			userTable.clear();

			System.out.println("In the execute USER " + sql);
			queryArea.append("\n THE USER QUERY IS");
			queryArea.append("\n  " + sql);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				userTable.addElement(rs.getString(1));
			}

			rs.close();
			stmt.close();
			userResultLabel.setText(userTable.getRowCount() + " User Found");
			userTable.fireTableDataChanged();

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Query Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Open connection to database
	 */
	private void connect_to_DB() {
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:system/Sampath123@127.0.0.1:1522/orcl", "system",
					"Sampath123");

		} catch (Exception e) {
			System.out.println("Error connecting database: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Close the database connection
	 */
	private void closeDB() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Error closing connection: " + e.getMessage());
			}
		}
	}

	private List<String> getListValues(String query) throws SQLException {
		List<String> values = new ArrayList<String>();

		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			values.add(rs.getString(1));
		}
		rs.close();
		stmt.close();
		return values;
	}

	private abstract class ListValuesQuery extends SwingWorker<List<String>, String> {

		private static final String lock = "LOCK";
		private final String query;

		public ListValuesQuery(String query) {
			this.query = query;

			synchronized (mainFrame) {
				mainFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				workerRunning = true;
			}
			execute();
		}

		public abstract void onResult(List<String> result);

		@Override
		protected void done() {
			try {

				onResult(get());
			} catch (Exception e) {
			} finally {
				synchronized (mainFrame) {
					mainFrame.setCursor(Cursor.getDefaultCursor());
					workerRunning = false;
				}
			}
		}

		@Override
		protected List<String> doInBackground() throws Exception {
			// Very Important lock else Too many cursor error
			synchronized (lock) {
				return (query.length() > 0) ? getListValues(query) : null;
			}
		}
	}

	private void listGenres() {
		genres.clear();
		new ListValuesQuery("SELECT DISTINCT G.genre FROM GENRE G ORDER BY G.genre") {
			@Override
			public void onResult(List<String> result) {
				for (String genre : result) {
					genres.addElement(new SelectableItem(genre));
				}
			}
		};
	}

	private void InitializeMovieYear() {
		String sql = "SELECT DISTINCT MIN(m.year), MAX(m.year) from movie m";

		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				toYearField.setText(rs.getString(2));
				fromYearField.setText(rs.getString(1));
			}
			rs.close();
			st.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private void listCountries() {
		String query;
		String selectedGenres = getValuesFrom(genres);
		if (selectedGenres.length() > 0 && and_or_choose_box.getSelectedIndex() == 0) {
			query = "SELECT DISTINCT l.country FROM Movie m, COUNTRY l, Genre g "
					+ "WHERE m.ID=l.movie_id AND m.ID=g.movie_id AND l.country IS NOT NULL " + "AND g.genre IN ("
					+ selectedGenres + ") AND m.year <= " + getInt(toYearField.getText(), false) + " AND m.year >= "
					+ getInt(fromYearField.getText(), false) + "ORDER BY l.country";
		} else {
			query = "SELECT DISTINCT country FROM COUNTRY " + "WHERE country IS NOT NULL ORDER BY country";
		}

		countries.clear();
		System.out.println("The query for country " + query);
		new ListValuesQuery(query) {
			@Override
			public void onResult(List<String> result) {
				for (String country : result) {
					countries.addElement(new SelectableItem(country));
				}
			}
		};
	}

	private void listTags() {

		String query;

		String selectedGenres = getValuesFrom(genres);
		String selectedCountries = getValuesFrom(countries);
		String actorName = joinActors(actorFields);
		String diretorName = directorField.getSelectedItem() == null ? "" : (String) directorField.getSelectedItem();
		if (selectedGenres.length() > 0) {
			String select = "SELECT DISTINCT MT.tagID|| '    ' || T.tagValue as IDnValue FROM MOVIE_TAG MT, TAG T, movie m ";
			String where = " where T.tagValue IS NOT NULL AND MT.tagID = T.id ";
			if (selectedGenres.length() > 0 && and_or_choose_box.getSelectedIndex() == 0) {
				select += " , Genre g ";
				where += " AND MT.movie_id=g.movie_id AND m.ID = g.movie_id AND g.genre IN ( " + selectedGenres + " ) ";
			}
			if (selectedCountries.length() > 0 && and_or_choose_box.getSelectedIndex() == 0) {
				select += " , COUNTRY l ";
				where += " AND l.movie_ID=MT.movie_id AND l.country IN ( " + selectedCountries + " ) ";
			}
			if (actorName.length() > 0 && and_or_choose_box.getSelectedIndex() == 0) {
				select += ", actors a";
				where += " AND a.movie_id = MT.movie_id AND a.actor_name IN ( " + actorName + " ) ";
			}
			if (diretorName.length() > 0 && and_or_choose_box.getSelectedIndex() == 0) {
				select += ", DIRECTORS d";
				where += " AND d.movie_id = MT.movie_id AND d.directorName = '" + diretorName + "'  ";
			}
			where += " AND m.year <= " + getInt(toYearField.getText(), false) + " AND m.year >= "
					+ getInt(fromYearField.getText(), false) + " ORDER BY IDnValue";
			query = select + where;
		} else {
			query = "SELECT DISTINCT T.ID ||'   ' ||T.tagValue as IDnValue FROM TAG T "
					+ "WHERE T.tagValue IS NOT NULL FETCH FIRST 100 ROWS ONLY";
		}

		tagsList.clear();
		System.out.println("The query for Tag " + query);
		new ListValuesQuery(query) {
			@Override
			public void onResult(List<String> result) {
				for (String tag : result) {
					tagsList.addElement(new SelectableItem(tag));
				}
			}
		};
	}

	private static String getValuesFrom(ListModel<SelectableItem> items) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.getSize(); i++) {
			SelectableItem item = items.getElementAt(i);
			if (item.selected) {
				sb.append("'").append(escapeSQL(item.value)).append("',");
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
	}

	private static String joinTags(ListModel<SelectableItem> items) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < items.getSize(); i++) {
			SelectableItem item = items.getElementAt(i);
			if (item.selected) {
				sb.append("'").append(item.value.split("\\s+")[0]).append("',");
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
	}

	private static String joinActors(List<JComboBox<String>> items) {
		StringBuilder sb = new StringBuilder();
		for (JComboBox<String> item : items) {
			if (item.getSelectedItem() != null) {
				String text = String.valueOf(item.getSelectedItem()).trim();
				if (text.length() > 0) {
					sb.append("'").append(escapeSQL(text)).append("',");
				}
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
	}

	private static int getInt(String text, boolean showError) {
		if (text == null || text.trim().length() == 0) {
			return 0;
		}

		try {
			return Integer.parseInt(text);
		} catch (Exception e) {
			if (showError) {
				JOptionPane.showMessageDialog(null, "The entered input: " + text + " is not a valid", "Invalid Input",
						JOptionPane.ERROR_MESSAGE);
			}
			return 0;
		}
	}

	private static String escapeSQL(String value) {
		return value != null ? value.replaceAll("'", "''") : "";
	}

	public static void main(String[] args) {

		FontUIResource defFont = new FontUIResource("Times New Roman", Font.PLAIN, FONT_SIZE);
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value != null && value instanceof FontUIResource) {
				UIManager.put(key, defFont);
			}
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new hw3();
			}
		});
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		System.out.println(e);
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
	}

	class UserIDSearch {
		JTable jTable;

		HashSet<String> movie_id_list = new HashSet<>();

		public UserIDSearch(JTable jTable) {
			this.jTable = jTable;
		}

		public String EventListener() {
			String res = "";
			int row[] = jTable.getSelectedRows();
			for (int i = 0; i < row.length; i++) {
				res += jTable.getValueAt(row[i], 0) + " ,";
			}
			jTable.clearSelection();
			return res.substring(0, res.length() - 2);
		}
	}

	private String joinForAndGenre(String element) {
		String[] elements = element.split(",");
		String sql = "(SELECT DISTINCT g.movie_id FROM genre g where g.genre = " + elements[0] + " )";
		for (int i = 1; i < elements.length; i++) {
			sql = "SELECT DISTINCT g" + i + ".movie_id FROM genre g" + i + " where g" + i + ".movie_id IN ( " + sql
					+ " ) AND g" + i + ".genre = " + elements[i];
			sql = "( " + sql + " )";
		}
		return sql;
	}

	private String joinForAndCountry(String element) {
		String[] elements = element.split(",");
		String sql = "(SELECT DISTINCT c.movie_id FROM COUNTRY c where c.country = " + elements[0] + " )";
		for (int i = 1; i < elements.length; i++) {
			sql = "SELECT DISTINCT c" + i + ".movie_id FROM COUNTRY c" + i + " where c" + i + ".movie_id IN ( " + sql
					+ " ) AND c" + i + ".country = " + elements[i];
			sql = "( " + sql + " )";
		}
		return sql;
	}

	private String joinForAndActor(String element) {
		String[] elements = element.split(",");
		String sql = "(SELECT DISTINCT a.movie_id FROM ACTORS a where a.actor_name = " + elements[0] + " )";
		for (int i = 1; i < elements.length; i++) {
			sql += "SELECT DISTINCT a" + i + ".movie_id FROM ACTORS a" + i + " where a" + i + ".movie_id IN ( " + sql
					+ " ) AND a" + i + ".actor_name = " + elements[i];
			sql = "( " + sql + " )";
		}
		return sql;
	}

	private String joinForAndTags(String element) {
		String[] elements = element.split(",");
		String sql = "(SELECT DISTINCT mt.movie_id FROM MOVIE_TAG mt where mt.tagID = "
				+ elements[0].replaceAll("'", "") + " )";
		for (int i = 1; i < elements.length; i++) {
			sql += "SELECT DISTINCT mt" + i + ".movie_id FROM MOVIE_TAG mt" + i + " where mt" + i + ".movie_id IN ( "
					+ sql + " ) AND mt" + i + ".tagID = " + elements[i].replaceAll("'", "");
			sql = "( " + sql + " )";
		}
		return sql;
	}
}
