package com.raon.toilet.common.keygen;


public interface KeyGenerator {
	
	
	/**
	 * 20자리의 RandomKey를 생성한다.
	 */
	public static final int KEY_SIZE = 20;
	
	/**
	 *  20자리의 Number RandomKey를 생성한다.
	 */
	public static final int NUMBER_KEY_SIZE = 20;
	
	
	/**
	 * 20자리의 순차적인 Number RandomKey를 생성한다.
	 */
	public static final int NUMBER_SEQ_KEY_SIZE = 20;
	
	
	
	/**
	 * <pre>
	 * 20자리 자리의 RandomKey를 생성한다.
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author HJKIM
	 */
	public String nextKey();
	
	/**
	 * <pre>
	 * 20자리 자리의 Number RandomKey를 생성한다.
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author HJKIM
	 */
	public String nextNumberKey();
	
	
	/**
	 * <pre>
	 * 20자리 자리의 순차적인 Number RandomKey를 생성한다.
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author HJKIM
	 */
	public String nextSeqNumberKey();
		
	/**
	 * <pre>
	 * 14자리 자리의 순차적인 Number RandomKey를 생성한다.
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author HJKIM
	 */
	public String nextBackupKey();
		
	
}
