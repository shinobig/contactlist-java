package shinobi.model;

public class Variables {
    public static final String DB_NAME = "contacts.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_FIRST_NAME = "firstName";
    public static final String COLUMN_LAST_NAME = "lastName";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_NOTES = "notes";
    public static final String INSERT_CONTACT =
            "INSERT INTO " + TABLE_CONTACTS + '(' + COLUMN_FIRST_NAME + ", " + COLUMN_LAST_NAME + ", " + COLUMN_NUMBER + ", " + COLUMN_NOTES + ") VALUES(?, ?, ?, ?)";

    public static final String DELETE_CONTACT = "DELETE FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_FIRST_NAME +
            "=?";

    public static final String EDIT_CONTACT = "UPDATE " + TABLE_CONTACTS + " SET " +
            COLUMN_FIRST_NAME + "=?, " +
            COLUMN_LAST_NAME + "=?, " +
            COLUMN_NUMBER + "=?, " +
            COLUMN_NOTES + "=?" + " WHERE " +
            COLUMN_FIRST_NAME + "=?";

  //  UPDATE contacts SET firstName="TestEdit", lastName="TestEdit", number=55555, notes="TestNotes" WHERE firstName="Victor"


            //DELETE FROM contacts WHERE firstName = "asdasd"
}
