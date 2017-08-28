/**
 * 
 */
package com.raon.toilet.common.type;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.raon.toilet.common.config.ToiletConfiguration;


/**
 * @author hjkim
 *
 */
public class StringTypeHandler extends BaseTypeHandler<String> {
	
	protected static SecStringCipher secStringCipher;
	
	private static boolean jdbcEncryptColumnEnabled = false;
	private static List<String> jdbcEncryptColumnLists = null;
	private static String dbCharSet = null;

	public StringTypeHandler() {
		super();
		
		ToiletConfiguration configuration = new ToiletConfiguration();
		jdbcEncryptColumnEnabled = configuration.getEncryptColumnEnabled();
		
		secStringCipher = new SecStringCipher(configuration.getEncryptKey());
		
		jdbcEncryptColumnLists = new ArrayList<String>();
		String encColumns = configuration.getEncryptColumnLists();
		if (encColumns != null && (encColumns.trim().length()) > 0) {
			StringTokenizer tokens = new StringTokenizer(encColumns, "|");
			if (tokens != null && 0 < tokens.countTokens()) {
				while(tokens.hasMoreElements()) {
					jdbcEncryptColumnLists.add(tokens.nextToken());
				}
			}
		}
		
		String charset = configuration.getDbChartSet();
		if (charset != null && (charset.trim().length()) > 0) {
			dbCharSet = charset;
		}
	}

	@Override
	public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
//		String columnName = columnName; 
		String columnString = rs.getString(columnName);
		
    	if (columnString != null && jdbcEncryptColumnEnabled && jdbcEncryptColumnLists.contains(columnName)) {
			String decrypted = secStringCipher.decryptString(columnString);
			if (null != decrypted) {
				return decrypted;
			}
    	}
	
		if (columnString != null && dbCharSet != null && !dbCharSet.isEmpty()) {
			try {
				return new String(columnString.getBytes(dbCharSet), "KSC5601");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return columnString; 
	}

	@Override
	public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String columnName = rs.getMetaData().getColumnClassName(columnIndex); 
		String columnString = rs.getString(columnName);
		
    	if (columnString != null && jdbcEncryptColumnEnabled && jdbcEncryptColumnLists.contains(columnName)) {
			String decrypted = secStringCipher.decryptString(columnString);
			if (null != decrypted) {
				return decrypted;
			}
    	}
	
		if (columnString != null && dbCharSet != null && !dbCharSet.isEmpty()) {
			try {
				return new String(columnString.getBytes(dbCharSet), "KSC5601");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return columnString; 
	}

	@Override
	public String getNullableResult(CallableStatement cst, int columnIndex) throws SQLException {
		String columnName = cst.getMetaData().getColumnClassName(columnIndex); 
		String columnString = cst.getString(columnName);
		
    	if (columnString != null && jdbcEncryptColumnEnabled && jdbcEncryptColumnLists.contains(columnName)) {
			String decrypted = secStringCipher.decryptString(columnString);
			if (null != decrypted) {
				return decrypted;
			}
    	}
	
		if (columnString != null && dbCharSet != null && !dbCharSet.isEmpty()) {
			try {
				return new String(columnString.getBytes(dbCharSet), "KSC5601");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		return columnString; 
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
		if (parameter != null && dbCharSet != null && !dbCharSet.isEmpty()) {
			try {
				ps.setString(i, new String(parameter.toString().getBytes("KSC5601"), "8859_1"));
				return;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
        ps.setString(i,	parameter);
	}

}
