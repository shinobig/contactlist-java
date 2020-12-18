package shinobi.model;

public class Variables {
    public static final String DB_NAME = "contacts.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_NOTES = "notes";
    public static final String INSERT_CONTACT =  "INSERT INTO " + TABLE_CONTACTS + '(' + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME + ", " + COLUMN_NUMBER + ", " + COLUMN_NOTES + ") VALUES(?,?,?,?)";
}
