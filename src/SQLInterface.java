import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SQLInterface {

    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/RealEstate";
    private String username = "root";
    private String password = "vimaln";
    private void init() throws SQLException {
        /*
            Creates connection to SQL and sets autocommit to false.
            This is necessary because many changes might be accidental and lead to permanent loss
            of data.
        */
        connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(false);
    }

    private void close() throws SQLException {
        //Closes the connection. Good habit to close the connection at the end.
        connection.close();
    }

    private String[][] resultToMatrix(ResultSet rs) throws SQLException {
        String[][] result;
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        String[] columnNames = new String[cols];
        for (int i = 0; i < cols; i++){
            columnNames[i] = rsmd.getColumnName(i+1);
        }
        ArrayList<String[]> data = new ArrayList<>();
        while (rs.next()){
            String[] row = new String[rsmd.getColumnCount()];
            for (int i = 0; i < cols; i++){
                row[i] = rs.getString(i+1);
            }
            data.add(row);
        }
        result = new String[data.size() + 1][cols];
        result[0] = columnNames;
        int i = 1;
        for(String[] row: data)
            result[i++] = row;
        return result;
    }
    public void commit() throws SQLException {
        connection.commit();
        close();
    }

    public boolean isConnected() throws SQLException {
        return connection == null || connection.isClosed();
    }
    public void fetchData(String query, DefaultTableModel model) throws SQLException {
        init();
        /*
            Creates ResultSet to store values produced by the query and ResultSetMetaData to get
            columns of the ResultSet
        */
        ResultSet rs = connection.createStatement().executeQuery(query);
        ResultSetMetaData rsmd = rs.getMetaData();

        //Clears the model. Ensures that new data isn't added to existing one
        model.setColumnCount(0);
        model.setNumRows(0);

        //Stores the names of all the column in an array for the model to create column
        String[] columnNames = new String[rsmd.getColumnCount()];
        for (int i = 0; i < rsmd.getColumnCount(); i++){
            columnNames[i] = rsmd.getColumnName(i+1);
        }
        model.setColumnIdentifiers(columnNames);

        //Populates the model with data from each row. Every value converted to String for simplicity.
        while (rs.next()){
            String[] row = new String[rsmd.getColumnCount()];
            for (int i = 0; i < rsmd.getColumnCount(); i++){
                row[i] = rs.getString(i+1);
            }
            model.addRow(row);
        }
        close();
    }
    public String[][] fetchData(String tableName, String Key, String Value) throws SQLException {
        init();
        String query = "Select * from " + tableName + " where " + Key + " = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, Value);
        String[][] result = resultToMatrix(ps.executeQuery());
        close();
        return result;
    }

    public String fetchSingleValue(String query, String[] values) throws SQLException {
        init();
        PreparedStatement pst = connection.prepareStatement(query);
        int i = 1;
        for(String val:values){
            pst.setString(i++, val);
        }
        return resultToMatrix(pst.executeQuery())[1][0];
    }

    public void executeQuery(String query, String[] values) throws SQLException {
        init();
        PreparedStatement pst = connection.prepareStatement(query);
        int i = 1;
        for(String val:values){
            pst.setString(i++, val);
        };
        pst.execute();
        connection.commit();
        close();
    }
    public void insertValue(String tableName, String[] values) throws SQLException {
        init();
        StringBuilder query = new StringBuilder("Insert into ");
        query.append(tableName).append(" values(").append("?");
        for(int i = 0; i < values.length - 1; i++){
            query.append(",?");
        }
        query.append(")");
        //System.out.println(query);
        PreparedStatement pst = connection.prepareStatement(query.toString());
        for(int i = 0; i < values.length; i++){
            pst.setString(i + 1, values[i]);
            System.out.print(values[i] + ", ");
        }
        pst.execute();
        commit();
    }
    public void insertValue(String tableName, String primaryCol, String[] values) throws SQLException {
        String[] newVals = new String[values.length + 1];
        newVals[0] = String.valueOf(generateRandomID(tableName, primaryCol));
        System.arraycopy(values, 0, newVals, 1, values.length);
        insertValue(tableName, newVals);
    }
    public String[] showTables() throws SQLException {
        init();

        //Initializes ResultSet and ArrayList to get the table names from SQL
        ResultSet rs = connection.createStatement().executeQuery("Show tables;");
        ArrayList <String> tables = new ArrayList<>();
        while (rs.next()){
            tables.add(rs.getString(1));
        }
        //Returns String array instead of array list by creating a new array and using toArray to set the values
        String[] t = new String[tables.size()];
        tables.toArray(t);
        return t;
    }

    public void UpdateTable(String table, String primaryCol, String primaryVal, String attribute,
                            String value) throws SQLException {
        init();

        //Using Prepared statement. Concatenating column names to the query as setString() causes error
        String query = "Update " + table + " set " + attribute + " = ? where " + primaryCol + " = ?";
        PreparedStatement ps = connection.prepareStatement(query);

        /*
            Setting values in the query. using setString() as the datatype of column unknown and
            letting JDBC figure it out.
            Also handling the case where new value can be ""(empty) and setting it to null.
        */
        if(Objects.equals(value, ""))
            ps.setNull(1, Types.NULL);
        else
            ps.setString(1, value);
        ps.setString(2, primaryVal);
        ps.execute();
        connection.commit(); //Committing value for now. Will remove im the future
        close();
    }

    int generateRandomID(String tableName, String primaryCol) throws SQLException {
        init();

        //Initializes ResultSet and ArrayList to get the table names from SQL
        String query = "Select " + primaryCol + " from " + tableName;
        ResultSet rs = connection.createStatement().executeQuery(query);
        ArrayList <String> values = new ArrayList<>();
        while (rs.next()){
            values.add(rs.getString(1));
        }
        int i = (int) (Math.random() * values.size() * 5);
        while (values.contains(String.valueOf(i))){
            i = (int) (Math.random() * Integer.parseInt(values.get(values.size() - 1)));
        }
        return i;
    }

    public void deleteRow(String tableName, String key, String value) throws SQLException {
        init();
        String query = "Delete from " + tableName + " where " + key + " = ?";
        PreparedStatement ps = connection.prepareStatement(query);

        /*
            Setting values in the query. using setString() as the datatype of column unknown and
            letting JDBC figure it out.
            Also handling the case where new value can be ""(empty) and setting it to null.
        */
        ps.setString(1, value);
        ps.execute();
        connection.commit(); //Committing value for now. Will remove im the future
        close();
    }
}
