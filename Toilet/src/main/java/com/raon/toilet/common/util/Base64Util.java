package com.raon.toilet.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Base64 인코더 디코더 Utility
 */
public class Base64Util {
	
	private static final String BASE64_STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	
	private static final char BASE64_CHARS[] = BASE64_STR.toCharArray();
	
	public static String base64encode(byte[] byteArray) {
		StringBuilder stringbuilder = new StringBuilder(((byteArray.length + 2) / 3) * 4);
		for (int i = 0; i < byteArray.length; i += 3) {
			byte byte0 = byteArray[i];
			byte byte1 = i >= byteArray.length - 1 ? 0 : byteArray[i + 1];
			byte byte2 = i >= byteArray.length - 2 ? 0 : byteArray[i + 2];
			stringbuilder.append(BASE64_CHARS[(byte0 & 255) >> 2]);
			stringbuilder.append(BASE64_CHARS[(byte0 & 3) << 4 | (byte1 & 240) >> 4]);
			stringbuilder.append(i >= byteArray.length - 1 ? "=" : ((Object) (Character
					.valueOf(BASE64_CHARS[(byte1 & 15) << 2 | (byte2 & 192) >> 6]))));
			stringbuilder.append(i >= byteArray.length - 2 ? "=" : ((Object) (Character
					.valueOf(BASE64_CHARS[byte2 & 63]))));
		}
		return stringbuilder.toString();
	}
	
	public static byte[] base64decode(String str) {
		str = str.trim();
		byte byte0 = str.endsWith("==") ? 2 : ((byte) (((byte) (str.endsWith("=") ? 1 : 0))));
		int i = (str.length() / 4) * 3 - byte0;
		str = str.replace('=', 'A');
		byte[] byteArray = new byte[i];
		int j = str.length();
		int k = 0;
		for (int l = 0; l < j; l += 4) {
			int i1 = BASE64_STR.indexOf(str.charAt(l));
			int j1 = BASE64_STR.indexOf(str.charAt(l + 1));
			int k1 = BASE64_STR.indexOf(str.charAt(l + 2));
			int l1 = BASE64_STR.indexOf(str.charAt(l + 3));
			byte byte1 = (byte) (i1 << 2 | j1 >> 4);
			byte byte2 = (byte) (j1 << 4 | k1 >> 2);
			byte byte3 = (byte) (k1 << 6 | l1);
			byteArray[k++] = byte1;
			if (k >= i) {
				continue;
			}
			byteArray[k++] = byte2;
			if (k < i) {
				byteArray[k++] = byte3;
			}
		}
		return byteArray;
	}
	//
	
	private static int lineLength;
	private static boolean lineCut;

	/** RFC maximum length */
	public static final int RFC_MAX_LINE_LENGTH = 76;
	/** Usual length (esp for openssl) */
	public static final int USUAL_LINE_LENGTH = 64;

	static	{
		reset();
	}

	/** Resets the state of Base64. */
	public static void reset()	{
		lineLength = USUAL_LINE_LENGTH;
		lineCut = true;
	}

	/** The length of a line before '\n'.
	 * This is automatically rounded to multiple of 4. */
	public static void setLineLength(int length)	{
		lineLength = length;
	}

	/** Sets whether to cut line at linelengths or not.
	 * Default vaule is true. */
	public static void setLineCut(boolean _lineCut)	{
		lineCut = _lineCut;
	}

	/** Encodes with base 64 */
    public static byte[] encode(byte[] in) throws IOException {
		return encode(in,lineCut,lineLength);
    }

	/** Encodes with base 64 */
    public static byte[] encode(byte[] in, byte[] seperator) throws IOException {
		return encode(in,lineCut,lineLength, seperator);
    }    
    
	/** Encodes with base 64 */
    public static byte[] encode(byte[] in,boolean wrap) throws IOException {
		return encode(in,wrap,lineLength);
    }

	/** Encodes with base 64 */
    public static byte[] encode(byte[] in,boolean wrap, byte[] seperator) throws IOException {
		return encode(in,wrap,lineLength, seperator);
    }
    
    /** Encodes with base 64 */
	public static byte[] encode(byte[] in, boolean wrap, int lineLength) throws IOException {
	    return encode(in, wrap, lineLength, "\n".getBytes());
	}
	
	public static byte[] encode(byte[] in, boolean wrap, int lineLength, byte[] seperator) throws IOException {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		encode(new ByteArrayInputStream(in), baos, wrap, lineLength, seperator);
		baos.close();
		return baos.toByteArray();
	}

	/** Encodes with base 64 */
    public static void encode(InputStream is, OutputStream os, boolean wrap, int ll) throws IOException {
        encode(is, os, wrap, ll, "\n".getBytes());
    }

    public static void encode(InputStream is, OutputStream os, boolean wrap, int ll, byte[] seperator) throws IOException {
		byte[] inBuffer = new byte[3];
		byte[] outBuffer = new byte[4];
		int lineCount = 0;
		while(is.available() != 0)	{
			int read = is.read(inBuffer);
			encodeBlock(inBuffer, read, outBuffer);
			os.write(outBuffer);
			lineCount += 4;
			if(wrap && ll <= lineCount)	{
				os.write(seperator);
				lineCount = 0;
			}
		}
		if(wrap && lineCount != 0)	{
			os.write(seperator);
		}
    }


	/** Encodes with base 64 */
    public static void encode(InputStream is, OutputStream os)
    throws IOException {
		encode(is,os,lineCut,lineLength);
    }

	/** Encodes with base 64 */
    public static void encode(InputStream is, OutputStream os,boolean wrap)
    throws IOException {
		encode(is,os,wrap,lineLength);
    }
 

	/** Decodes with base 64 */
    public static byte[] decode(byte[] in)
    throws IOException
    {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
        decode(new ByteArrayInputStream(in), baos);
		baos.close();
        return baos.toByteArray();
    }

	/** Decodes with base 64 */
    public static void decode(InputStream is, OutputStream os)
    throws IOException
    {
		try {
			byte[] inBuffer = new byte[4];
			byte[] outBuffer = new byte[3];
			while(is.available() != 0)	{
				int read = 0;
				while(is.available() != 0)	{
					byte current = (byte)is.read();
					if(!isDecodeDomain(current))	{
						continue;
					}
					inBuffer[read] = current;
					read++;
					if(read == 4) break;
				}
				if(read != 4 && read != 0)
					throw new IOException("Base64 decode fail, last: "+read);
				if(read != 0) {
					int wrote = decodeBlock(inBuffer, outBuffer);
					os.write(outBuffer,0,wrote);
				}
			}
		} catch ( ArrayIndexOutOfBoundsException ex ) {
			throw new IOException("Base64 decode fail : illegal characters in data");
		}
    }

	private static boolean isDecodeDomain(byte current)	{
//		if ( current < 0 ) return false;
		return (B2I[(int)current] >= 0);
	}

	private static byte B2I[] = {
		/* 00  01  02  03  04  05  06  07  08  09  0A  0B  0C  0D  0E  0F */
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		/* 10  11  12  13  14  15  16  17  18  19  1A  1B  1C  1D  1E  1F */
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		/*      !   "   #   $   %   &   '   (   )   *   +   ,   -   .   / */
		   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,
		/*  0   1   2   3   4   5   6   7   8   9   :   ;   <   =   >   ? */
		   52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,  0, -1, -1,
		/*  @   A   B   C   D   E   F   G   H   I   J   K   L   M   N   O */
		   -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,
		/*  P   Q   R   S   T   U   V   W   X   Y   Z   [   \   ]   ^ _ */
		   15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,
		/*  `   a   b   c   d   e   f   g   h   i   j   k   l   m   n   o */
		   -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
		/*  p   q   r   s   t   u   v   w   x   y   z   {   |   }   ~  DL */
		   41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1
	};
	
	private static byte I2B[] = {
		(byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F',
		(byte)'G', (byte)'H', (byte)'I', (byte)'J', (byte)'K', (byte)'L',
		(byte)'M', (byte)'N', (byte)'O', (byte)'P', (byte)'Q', (byte)'R',
		(byte)'S', (byte)'T', (byte)'U', (byte)'V', (byte)'W', (byte)'X',
		(byte)'Y', (byte)'Z', (byte)'a', (byte)'b', (byte)'c', (byte)'d',
		(byte)'e', (byte)'f', (byte)'g', (byte)'h', (byte)'i', (byte)'j',
		(byte)'k', (byte)'l', (byte)'m', (byte)'n', (byte)'o', (byte)'p',
		(byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u', (byte)'v',
		(byte)'w', (byte)'x', (byte)'y', (byte)'z', (byte)'0', (byte)'1',
		(byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6', (byte)'7',
		(byte)'8', (byte)'9', (byte)'+', (byte)'/'
	};


	/** 4bytes to 3bytes only
	  * @param in 4 bytes of input bytes
	  * @param out 3 bytes of output bytes
	 */
	public static final int decodeBlock(byte[] in, byte[] out)
	{
		int ret = 3;
		int[] idx = new int[4];
		//boolean end = false;

		for ( int i = 0; i < 4; i++ ) {
			byte current = in[i];

			if ( current == 0x3D ) {
				// FIX by godslord 
				// 마지막 contents가 0x00일 경우 길이 계산이 제대로 안 됨
				ret = ( i == 2 )?1:2;
				break;
			}

			idx[i] = B2I[(int)current];
		}

		out[0] = (byte)((idx[0] & 0xff) << 2 | (idx[1] & 0xff) >> 4);
		out[1] = (byte)((idx[1] & 0xff) << 4 | (idx[2] & 0xff) >> 2);
		out[2] = (byte)((idx[2] & 0xff) << 6 | (idx[3] & 0xff));

		return ret;
	}

	/** 3bytes to 4bytes only
	  * @param in input bytes(3)
	  * @param len input bytes to decode(upto 3)
	  * @param out out bytes(4)
	 */
	public static final void encodeBlock(byte[] in, int len, byte[] out) {
		if(out == null) out = new byte[4];
		int i=3;

		while(len < i){
			in[--i]=0;
		}

		out[0] = (byte)((in[0] & 0xfc) >>> 2);
		out[1] = (byte)(((in[0] & 0x03) << 4)| ((in[1] & 0xf0) >>> 4));
		out[2] = (byte)(((in[1] & 0x0f) << 2) | ((in[2] & 0xc0) >>> 6));
		out[3] = (byte)(in[2] & 0x3f);

		for ( i = 0; i < 4; i++ ) out[i] = I2B[out[i]];

		if(len<3){
			out[3]=(byte)'=';
		}
		if(len<2){
			out[2]=(byte)'=';
		}
	}
}
