import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class populate {
	public static final int[] MOVIE = { 0, 1, 5, 17, 18 };
	public static final int[] USER_TAG = { 0, 1, 2 };
	public static final String[] FILE_ORDER = { "movies.dat", "movie_actors.dat", "movie_countries.dat",
			"movie_directors.dat", "movie_genres.dat", "tags.dat", "movie_tags.dat", "user_taggedmovies.dat" };
	public static final String[] TABLE_NAME = { "MOVIE", "ACTORS", "COUNTRY", "DIRECTORS", "GENRE", "TAG", "MOVIE_TAG",
			"USER_TAG" };
	public static int res = 0;
	protected static HashMap dataTypeMap = getDataType(TABLE_NAME);

	public static void main(String[] args) {
		try {
			String filePath = args==null?"E:\\Quarter2\\Database\\HW-3\\imdb\\":args[0];
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection("jdbc:oracle:thin:system/Sampath123@127.0.0.1:1522/orcl",
					"system", "Sampath123");
			deleteData(con, TABLE_NAME);
			List<String> files = new ArrayList<>();
			if (args.length < FILE_ORDER.length+1) {
				System.out.println("Insufficient files provided");
				return;
			}
			for (String s : args) {
				for (int i = 0; i < FILE_ORDER.length; i++) {
					if (s.contains("\\"+FILE_ORDER[i])) {
						files.add(i, s);
						System.out.println("The order is "+s+"   "+i);
						break;
					}
				}
			}
			
			for (int i=0;i<files.size();i++) {
				System.out.println("The file path is "+filePath+files.get(i));
				parseData(con, filePath+files.get(i));
				
				// System.out.println("The res value is " + res);
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private final static void parseData(Connection conn, String filename) {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			PreparedStatement preparedStatement;
			String sCurrentLine;
			int flag = 0;
			String tableName = getTableName(filename);
			String[] dataType = (String[]) dataTypeMap.get(tableName);
			if ((sCurrentLine = br.readLine()) == null)
				return;
			int numOfColumns = sCurrentLine.split("\\s+").length;

			if (filename.contains("\\" + FILE_ORDER[0])) {
				preparedStatement = prepareStatement(conn, tableName, MOVIE.length);
				flag = 1;
			} else if (filename.contains("\\" + FILE_ORDER[FILE_ORDER.length - 1])) {
				preparedStatement = prepareStatement(conn, tableName, USER_TAG.length);
				flag = 2;
			} else {
				preparedStatement = prepareStatement(conn, tableName, sCurrentLine.split("\\s+").length);
			}
			preparedStatement.setQueryTimeout(10000);
			List<String[]> dataList = new LinkedList<>();
			while ((sCurrentLine = br.readLine()) != null) {
				if (flag != 0)
					dataList.add(getData(sCurrentLine, filename));
				else
					dataList.add(getData(sCurrentLine, numOfColumns, filename));
				if (dataList.size() == 1000) {
					populateData(preparedStatement, dataList, dataType);
					dataList.clear();
				}
			}
			if (!dataList.isEmpty()) {
				populateData(preparedStatement, dataList, dataType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Parse File and get Data
	private final static String[] getData(final String s, int numOfColumns, String filename) {
		String regex = "\\t";
		if (filename.contains("movie_tag"))
			regex = "\\s+";
		String[] tmp = s.split(regex, numOfColumns);
		if (tmp.length != numOfColumns) {
			String[] tmp1 = new String[numOfColumns];
			for (int i = 0; i < tmp.length; i++) {
				tmp1[i] = tmp[i];
			}
			return tmp1;
		} else
			return tmp;
	}
	
	// Parse file and Get Data
	private final static String[] getData(final String s, final String fileName) {

		if (fileName.contains("\\" + FILE_ORDER[0])) {
			String[] tmp = new String[MOVIE.length];
			// System.out.println("In get Data ="+tmp.length+" "+MOVIE.length);
			String[] tmp1 = s.split("\\t", MOVIE[MOVIE.length - 1] + 2);
			// System.out.println("The array is "+Arrays.toString(tmp1));
			int n = 0;
			for (int i : MOVIE) {
				tmp[n] = tmp1[i].trim().contains("\\N") ? null : tmp1[i].trim();
				n++;
			}
			return tmp;
		} else {
			String[] tmp = s.split("\\s+", USER_TAG[USER_TAG.length - 1] + 2);
			String[] tmp1 = new String[USER_TAG.length];
			for (int i = 0; i < tmp1.length; i++) {
				tmp1[i] = tmp[i].trim();
			}
			return tmp;
		}

	}
	// Create prepareStatement query
	private static final PreparedStatement prepareStatement(Connection conn, String tableName, int numOfColumns)
			throws Exception {
		String prepSt = "INSERT INTO " + tableName + " values (";
		for (int i = 1; i < numOfColumns; i++) {
			prepSt += "?,";
		}
		prepSt += "?)";
		System.out.println("THE PREP STAT IS = " + prepSt);
		return conn.prepareStatement(prepSt);

	}
	// ADD data to DB in Batch
	private static final void populateData(PreparedStatement st, final List<String[]> dataList, final String[] dataType)
			throws Exception {
		for (String[] s : dataList) {
			for (int i = 0; i < dataType.length; i++) {
				if (dataType[i].contains("String") || s[i] == null)
					st.setString(i + 1, s[i]);
				else if (dataType[i].contains("int"))
					st.setInt(i + 1, Integer.parseInt(s[i].trim()));
				else if (dataType[i].contains("float"))
					st.setFloat(i + 1, Float.parseFloat(s[i].trim()));
			}
			st.addBatch();
		}
		st.executeBatch();
		res += dataList.size();
		System.out.println("the coun is " + res + "     " + Arrays.toString(dataList.get(0)));

	}
	// Helper Method to get Table Name from file Name
	private static final String getTableName(String fileName) {
		for (int i = 0; i < FILE_ORDER.length; i++) {
			if (fileName.contains("\\" + FILE_ORDER[i]))
				return TABLE_NAME[i];
		}
		return null;
	}
	// Delete data from DB before insert 
	private static void deleteData(Connection con, final String[] tableName) throws Exception {
		System.out.println("In delete");
		Statement st = con.createStatement();
		for (int i = tableName.length - 1; i >= 0; i--) {
			st.addBatch("delete from " + tableName[i]);
		}
		st.executeBatch();
	}
	
	// Helper function to get Data Type of columns
	private static HashMap getDataType(String[] tableName) {
		HashMap<String, String[]> s = new HashMap<>();

		s.put(tableName[0], new String[] { "int", "String", "int", "float", "int" });
		s.put(tableName[1], new String[] { "int", "String", "String", "int" });
		s.put(tableName[2], new String[] { "int", "String" });
		s.put(tableName[3], new String[] { "int", "String", "String" });
		s.put(tableName[4], new String[] { "int", "String" });
		s.put(tableName[5], new String[] { "int", "String" });
		s.put(tableName[6], new String[] { "int", "int", "int" });
		s.put(tableName[7], new String[] { "int", "int", "int" });

		return s;
	}
}
