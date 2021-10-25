package io.xmeta.screw.model;

import io.xmeta.core.exception.MetaException;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * A relational constraint.
 *
 * @author Gavin King
 * @author Brett Meyer
 */
public abstract class Constraint implements Serializable {

	private String name;
	private final ArrayList<TableColumn> columns = new ArrayList<TableColumn>();
	private Table table;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * If a constraint is not explicitly named, this is called to generate
	 * a unique hash using the table and column names.
	 * Static so the name can be generated prior to creating the Constraint.
	 * They're cached, keyed by name, in multiple locations.
	 *
	 * @return String The generated name
	 */
	public static String generateName(String prefix, Table table, TableColumn... columns) {
		// Use a concatenation that guarantees uniqueness, even if identical names
		// exist between all table and column identifiers.

		StringBuilder sb = new StringBuilder( "table`" + table.getName() + "`" );

		// Ensure a consistent ordering of columns, regardless of the order
		// they were bound.
		// Clone the list, as sometimes a set of order-dependent Column
		// bindings are given.
		TableColumn[] alphabeticalColumns = columns.clone();
		Arrays.sort( alphabeticalColumns, ColumnComparator.INSTANCE );
		for ( TableColumn column : alphabeticalColumns ) {
			String columnName = column == null ? "" : column.getName();
			sb.append( "column`" ).append( columnName ).append( "`" );
		}
		return prefix + hashedName( sb.toString() );
	}

	/**
	 * Helper method for {@link #generateName(String, Table, TableColumn...)}.
	 *
	 * @return String The generated name
	 */
	public static String generateName(String prefix, Table table, List<TableColumn> columns) {
		//N.B. legacy APIs are involved: can't trust that the columns List is actually
		//containing Column instances - the generic type isn't consistently enforced.
		ArrayList<TableColumn> defensive = new ArrayList<>( columns.size() );
		for ( Object o : columns ) {
			if ( o instanceof TableColumn ) {
				defensive.add( (TableColumn) o );
			}
			//else: others might be Formula instances. They don't need to be part of the name generation.
		}
		return generateName( prefix, table, defensive.toArray( new TableColumn[0] ) );
	}

	/**
	 * Hash a constraint name using MD5. Convert the MD5 digest to base 35
	 * (full alphanumeric), guaranteeing
	 * that the length of the name will always be smaller than the 30
	 * character identifier restriction enforced by a few dialects.
	 *
	 * @param s
	 *            The name to be hashed.
	 * @return String The hashed name.
	 */
	public static String hashedName(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance( "MD5" );
			md.reset();
			md.update( s.getBytes() );
			byte[] digest = md.digest();
			BigInteger bigInt = new BigInteger( 1, digest );
			// By converting to base 35 (full alphanumeric), we guarantee
			// that the length of the name will always be smaller than the 30
			// character identifier restriction enforced by a few dialects.
			return bigInt.toString( 35 );
		}
		catch ( NoSuchAlgorithmException e ) {
			throw new MetaException( "Unable to generate a hashed Constraint name :" + s + "!");
		}
	}

	private static class ColumnComparator implements Comparator<TableColumn> {
		public static ColumnComparator INSTANCE = new ColumnComparator();

		public int compare(TableColumn col1, TableColumn col2) {
			return col1.getName().compareTo( col2.getName() );
		}
	}

}
