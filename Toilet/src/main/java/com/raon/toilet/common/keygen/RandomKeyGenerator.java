package com.raon.toilet.common.keygen;

import java.security.SecureRandom;

public class RandomKeyGenerator implements KeyGenerator{
	
	/* (non-Javadoc)
	 * @see com.raon.onepass.common.keygen.KeyGenerator#nextKey()
	 */
	public String nextKey() {
		long time = System.currentTimeMillis();
		long s1 = new SecureRandom().nextLong();
		long s2 = new SecureRandom().nextLong();
		long s3 = new SecureRandom().nextLong();
	
		String temp = Long.toHexString(((time & s1) | s2) ^ s3).toUpperCase();
		
		for ( int i=temp.length(); i< KeyGenerator.KEY_SIZE; i++ )
			temp += new SecureRandom().nextInt(10);	
		
		return temp; 
	}
	
	/* (non-Javadoc)
	 * @see com.raon.onepass.common.keygen.KeyGenerator#nextNumberKey()
	 */
	public String nextNumberKey() {
		
		long time = System.currentTimeMillis();
		long s1 = new SecureRandom().nextLong();
		long s2 = new SecureRandom().nextLong();
		long s3 = new SecureRandom().nextLong();
	
		String temp = Long.toString(Math.abs(((time & s1) | s2) ^ s3));
		
		for ( int i=temp.length(); i< KeyGenerator.KEY_SIZE; i++ )
			temp += new SecureRandom().nextInt(10);	
		
		return temp; 
	}

	/* (non-Javadoc)
	 * @see com.raon.onepass.common.keygen.KeyGenerator#nextSeqNumberKey()
	 */
	public synchronized String nextSeqNumberKey() {
		
		long time = 0;
//		try {
//			Thread.sleep(1);
			time = System.currentTimeMillis();
//		} catch (InterruptedException e) {
//			
//		}
		String temp = Long.toString(time);
		for ( int i=temp.length(); i< KeyGenerator.KEY_SIZE; i++ )
			temp += new SecureRandom().nextInt(10);	
		
		return temp; 
	}	
  
	public synchronized String nextBackupKey() {
		
		long time = 0;
//		try {
//			Thread.sleep(1);
			time = System.currentTimeMillis();
//		} catch (InterruptedException e) {
//			
//		}
		String temp = Long.toString(time % (60*60*1000));	// per hour
		for ( int i=temp.length(); i< KeyGenerator.KEY_SIZE; i++ )
			temp += new SecureRandom().nextInt(10);	
		
		return temp; 
	}	
  
}
