package de.persosim.simulator.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.persosim.simulator.tlv.TlvDataObject;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * 
 * 
 * @author slutters
 *
 */
public abstract class Utils {
		
	public static final byte[] BITMASK            = new byte[]{(byte) 0x01, (byte) 0x02, (byte) 0x04, (byte) 0x08, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x80};
	public static final byte[] BITMASK_COMPLEMENT = new byte[]{(byte) 0xFE, (byte) 0xFD, (byte) 0xFB, (byte) 0xF7, (byte) 0xEF, (byte) 0xDF, (byte) 0xBF, (byte) 0x7F};

	public static final short MASK_BYTE_TO_SHORT = (short) 0x00FF;
	public static final short MASK_FIRST_BYTE_OF_SHORT = (short) 0xFF00;
	public static final int MASK_BYTE_TO_INT = (short) 0x000000FF;
	public static final int MASK_SHORT_TO_INT = 0x0000FFFF;
	
	public static final byte DATE_SET_MIN_VALUE = (byte) -1;
	public static final byte DATE_NO_CHECKS = (byte) 0;
	public static final byte DATE_SET_MAX_VALUE = (byte) 1;
	
	/*--------------------------------------------------------------------------------*/
	
	// This is a static utils class and should not be inherited or instantiated
	private Utils() {}
	
	public static byte getFirstByteOfShort(short shortValue) {
		return (byte)(MASK_FIRST_BYTE_OF_SHORT >>> 8);
	}
	
	/**
	 * Returns an unsigned byte masked to short
	 * @param byteValue the byte to be masked to short
	 * @return the unsigned byte masked to short
	 */
	public static short maskUnsignedByteToShort(byte byteValue) {
		return (short) (byteValue & MASK_BYTE_TO_SHORT);
	}
	
	/**
	 * Returns an unsigned byte masked to int
	 * @param byteValue the byte to be masked to int
	 * @return the unsigned byte masked to int
	 */
	public static int maskUnsignedByteToInt(byte byteValue) {
		return (int) (byteValue & MASK_BYTE_TO_INT);
	}
	
	/**
	 * Returns an unsigned short masked to int
	 * @param shortValue the short to be masked to int
	 * @return the unsigned short masked to int
	 */
	public static int maskUnsignedShortToInt(short shortValue) {
		return (int) (shortValue & MASK_SHORT_TO_INT);
	}
	
	/*--------------------------------------------------------------------------------*/
	
	/**
	 * Returns a short resulting from concatenating two bytes
	 * @param byte1 the leading byte
	 * @param byte2 the trailing byte
	 * @return a short resulting from concatenating two bytes
	 */
	public static short concatenate(byte byte1, byte byte2) {
		short concatenation;
		
		concatenation = maskUnsignedByteToShort(byte1);
		concatenation <<= 8;
		concatenation |= maskUnsignedByteToShort(byte2);
		
		return concatenation;
	}
	
	/**
	 * Returns a concatenation of one or more byte arrays
	 * @param byteArrays one or more byte arrays
	 * @return a concatenation of one or more byte arrays
	 */
	public static byte[] concatByteArrays(byte[]... byteArrays) {
		
		ByteArrayOutputStream outputStream;
		outputStream = new ByteArrayOutputStream();
		for (byte[] currentByteArray : byteArrays) {
			if (currentByteArray == null) continue;
			
			try {

				outputStream.write(currentByteArray);
			} catch (IOException e) {
				ApiLoggerResolver.logError(Utils.class.getSimpleName(), e.getMessage());
			}
		}

		return outputStream.toByteArray();
	}

	/**
	 * Returns a concatenation of an array and multiple additional object of fitting
	 * type
	 * 
	 * @param clazz
	 *            the element type of the array to create
	 * @param array
	 *            the starting array
	 * @param toConcat
	 *            single elements to append to the starting array, can be multiple
	 *            single elements or one array of fitting type
	 * @return a new array containing all given elements
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] append(Class<T> clazz, T[] array, T... toConcat) {

		List<T> content = new ArrayList<>();
		if (array != null) {
			content.addAll(Arrays.asList(array));
		}

		for (T current : toConcat) {
			content.add(current);
		}

		return content.toArray((T[]) Array.newInstance(clazz, content.size()));
	}
	
	
	/**
	 * Returns a byte array that has been appended by the provided bytes
	 * @param leadingByteArray leading byte array
	 * @param trailingBytes one or more trailing bytes
	 * @return a byte array that has been appended by the provided bytes
	 */
	public static byte[] appendBytes(byte[] leadingByteArray, byte... trailingBytes) {
		
		 return concatByteArrays(leadingByteArray,trailingBytes);
		 
	}
	
	/**
	 * Returns the logarithm of the given value to the given base
	 * @param value the value
	 * @param base the base
	 * @return the logarithm of the given value to the given base
	 */
	public static double logarithm(double value, int base) {
		return Math.log10(value)/Math.log10(base);
	}
	
	/**
	 * Returns the unsigned absolute value of minimum 1 byte length.
	 * 
	 * In general the same as BigInteger's toByteArary():byte[]
	 * with the following differences:
	 * - result is unsigned
	 * - BigInteger.ZERO --> new byte[]{(byte) 0x00}
	 * 
	 * @param bigInt signed value
	 * @return unsigned absolute value of minimum 1 byte length
	 */
	public static byte[] toUnsignedByteArray(BigInteger bigInt) {
		if(bigInt == null) {throw new NullPointerException();}
		
		byte[] resultTMP, resultFINAL;
		BigInteger bigIntTmp;
		
		/* reset leading sign bit (if present), i.e. compute absolute value */
		bigIntTmp = bigInt.abs();
		
		if(bigIntTmp.compareTo(BigInteger.ZERO) == 0) {
			resultTMP = new byte[]{(byte) 0x00};
		} else{
			resultTMP = bigIntTmp.toByteArray();
		}
		
		/*
		 * Crop leading empty byte previously used for sign (if present).
		 * Leading sign byte is 0x00 due to taking absolute value previously.
		 */
		if((resultTMP[0] == (byte) 0x00) && (resultTMP.length > 1)) {
			resultFINAL = new byte[resultTMP.length - 1];
			System.arraycopy(resultTMP, 1, resultFINAL, 0, resultFINAL.length);
		} else{
			resultFINAL = resultTMP;
		}
		
		return resultFINAL;
	}
	
	/**
	 * This method converts an integer to an unsigned byte array without leading zeros
	 * @param input the integer to convert
	 * @return unsigned byte array
	 */
	public static byte[] toShortestUnsignedByteArray(int input) {
		byte [] array = toUnsignedByteArray(input);
		return removeLeadingZeroBytes(array);
	}
	
	/**
	 * Returns an unsigned byte array representation of an unsigned int.
	 * Returned Array has length 4; unused bytes are padded to 0x00.
	 * @param input the int
	 * @return the unsigned byte array representation of the unsigned int
	 */
	public static byte[] toUnsignedByteArray(int input) {
		return new byte[]{(byte) ((input & 0xFF000000) >> 24), (byte) ((input & 0x00FF0000) >> 16), (byte) ((input & 0x0000FF00) >> 8), (byte) (input & 0x000000FF)};
	}
	
	/**
	 * Returns an unsigned byte array representation of an unsigned short
	 * Returned Array has length 2; unused bytes are padded to 0x00.
	 * @param input the short
	 * @return the unsigned byte array representation of the unsigned short
	 */
	public static byte[] toUnsignedByteArray(short input) {
		return new byte[]{(byte) ((input & (short) 0xFF00) >> 8), (byte) (input & (short) 0x00FF)};
	}
	
	/**
	 * Returns an unsigned byte array representation of an unsigned byte
	 * Returned Array has length 1; unused bytes are padded to 0x00.
	 * @param number the byte
	 * @return the unsigned byte array representation of the unsigned byte
	 */
	public static byte[] toUnsignedByteArray(byte number) {
		return new byte[]{number};
	}
	
	/**
	 * Returns a byte array with leading 0x00 bytes removed.
	 * If the input byte array is all 0x00, all will be removed except for one.
	 * @param input the byte array to be cropped
	 * @return the byte array with leading 0x00 bytes removed.
	 */
	public static byte[] removeLeadingZeroBytes(byte[] input) {
		int index;
		
		for(index = 0; index < input.length; index++) {
			if(input[index] != (byte) 0x00) {
				break;
			}
		}
		
		if(index == input.length) {
			/* if input is all 0x00 */
			index--;
		}
		
		return Arrays.copyOfRange(input, index, input.length);
	}
	
	/**
	 * Returns the given input padded with either leading or trailing zeroes.
	 * Padding is only performed if necessary, i.e. input length < wanted
	 * length. In this case a new object of wanted length containing input data
	 * padded with additional leading or trailing zeroes is returned. If input
	 * length equals wanted length a clone of the input object is returned.
	 * 
	 * @param input
	 *            the byte array to be padded
	 * @param wantedLength
	 *            the result array length
	 * @return zero padded byte array
	 * @throws IllegalArgumentException
	 *             when input length > wanted length
	 */
	public static byte [] padWithZeroes(byte [] input, int wantedLength, boolean padWithLeadingZeros){
		if(input.length == wantedLength) {
			return input.clone();
		}
		
		if (wantedLength < input.length){
			throw new IllegalArgumentException("Wanted length is smaller than the input length");
		}
		
		byte[] zeroes = new byte [wantedLength - input.length];
		
		if(padWithLeadingZeros) {
			return Utils.concatByteArrays(zeroes, input);
		} else{
			return Utils.concatByteArrays(input, zeroes);
		}
		
	}
	
	/**
	 * Returns the given input padded with leading zeroes. Padding is only
	 * performed if necessary, i.e. input length < wanted length. In this case a
	 * new object of wanted length containing input data padded with additional
	 * leading zeroes is returned. If input length equals wanted length a clone
	 * of the input object is returned.
	 * 
	 * @param input
	 *            the byte array to be padded
	 * @param wantedLength
	 *            the result array length
	 * @return zero padded byte array
	 * @throws IllegalArgumentException
	 *             when input length > wanted length
	 */
	public static byte [] padWithLeadingZeroes(byte [] input, int wantedLength){
		return padWithZeroes(input, wantedLength, true);
	}
	
	/**
	 * Returns the given input padded with trailing zeroes. Padding is only
	 * performed if necessary, i.e. input length < wanted length. In this case a
	 * new object of wanted length containing input data padded with additional
	 * trailing zeroes is returned. If input length equals wanted length a clone
	 * of the input object is returned.
	 * 
	 * @param input
	 *            the byte array to be padded
	 * @param wantedLength
	 *            the result array length
	 * @return zero padded byte array
	 * @throws IllegalArgumentException
	 *             when input length > wanted length
	 */
	public static byte [] padWithTrailingZeroes(byte [] input, int wantedLength){
		return padWithZeroes(input, wantedLength, false);
	}
	
	/**
	 * Returns an unsigned int representation of the provided byte array. The
	 * byte value is interpreted as being unsigned. This method works for
	 * integers up to 0xFFFFFFFF.
	 * 
	 * @param value
	 * @param maxValue
	 * @return
	 */
	private static int getDataTypeFromUnsignedByteArray(byte[] value, long maxValue) {
		if(value == null) {throw new IllegalArgumentException("value must not be null");}
		if(value.length < 1) {throw new IllegalArgumentException("value must have byte length > 0");}
		
		BigInteger bigInt;
		
		bigInt = new BigInteger(1, value);
		
		if (bigInt.compareTo(new BigInteger(Long.toString(maxValue))) > 0){
			throw new IllegalArgumentException("value too big for signed data type");
		}
		
		return bigInt.intValue();
	}
	
	/**
	 * Constructs a {@link BigInteger} from an unsigned byte array.
	 * @param unsigned
	 *            the byte array to be treated as unsigned
	 * @return the {@link BigInteger} created from a concatenation of a 0x00
	 *         byte and the input
	 */
	public static BigInteger getBigIntegerFromUnsignedByteArray(byte[] unsigned) {
		return new BigInteger(concatByteArrays(new byte[] { 0 }, unsigned));
	}
	
	public static int getIntFromUnsignedByteArray(byte[] value) {
		return (int) getDataTypeFromUnsignedByteArray(value, 0x0FFFFFFFFl);
	}
	
	public static short getShortFromUnsignedByteArray(byte[] value) {
		return (short) getDataTypeFromUnsignedByteArray(value, 0x0FFFFl);
	}

	/**
	 * Check if any of the given objects is null.
	 * 
	 * @param toTest
	 * @return true, iff one of the given objects is null
	 */
	public static boolean isAnyNull(Object ...toTest) {
		for(Object o : toTest){
			if (o == null){
				return true;
			}
		}
		return false;
	}
	
	public static boolean arrayHasPrefix(byte [] data, byte [] prefix){
		if (data.length < prefix.length){
			return false;
		}
		for (int i = 0; i < prefix.length; i++){
			if (data [i] != prefix[i]){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	 * If string does not encode a data as expected (YYYYMMDD) a {@link NumerFormatException} is thrown.
	 * 
	 * @see {@link #getDate(String, byte)}
	 * @param dateString the date encoded as follows: YYYYMMDD
	 * @return a {@link Date} object
	 */
	public static Date getDate(String dateString) {
		return getDate(dateString, (byte) 0);
	}
	
	/**
	 * This method creates a {@link Date} object from a {@link String} representation with respect to year, month and day.
	 * The provided String is expected to be exactly 8 characters long and encoded as follows: YYYYMMDD.
	 * If the String parts for month or day contain non-numeric characters, they will be handled according to the second provided parameter:
	 * <ul>
	 * <li>-1: the minimum possible value will be chosen</li>
	 * <li> 0: a NumberFormatException will be thrown</li>
	 * <li> 1: the maximum possible value will be chosen</li>
	 * </ul>
	 * <br/>
	 * Well formatted date strings will not be checked for validity, e.g. 20140199 would not be discarded.
	 * <br/>
	 * All parts not specified within the input string (hour, minutes, seconds and milliseconds) will be set to zero in order to retrieve comparable return values.
	 * When calling this method twice with the same input the return values will be distinct but .equals() evaluates to true;
	 * 
	 * @param dateString the date encoded as follows: YYYYMMDD
	 * @param handleNonNumericCharacters determine how non-numeric characters will be handled
	 * @return a {@link Date} object
	 */
	public static Date getDate(String dateString, byte handleNonNumericCharacters) {
		if (dateString == null) {throw new NullPointerException("date must not be null");}
		if (dateString.length() != 8) {throw new IllegalArgumentException("date must be exactly 8 characters long");}

		Calendar calendar = Calendar.getInstance();

		int year = Integer.parseInt(dateString.substring(0, 4));

		calendar.set(Calendar.YEAR, year);

		int month;
		int day;

		try {
			month = Integer.parseInt(dateString.substring(4, 6));
			month--; // set month is zero based
		} catch (NumberFormatException e) {
			switch (handleNonNumericCharacters) {
			case -1:
				month = Calendar.JANUARY;
				break;
			case 0:
				throw e;
			case 1:
				month = Calendar.DECEMBER;
				break;
			default:
				throw new IllegalArgumentException("invalid value for handling illegal month");
			}
		}

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);

		try {
			day = Integer.parseInt(dateString.substring(6, 8));
		} catch (NumberFormatException e) {
			switch (handleNonNumericCharacters) {
			case -1:
				day = 1;
				break;
			case 0:
				throw e;
			case 1:
				day = calendar.getActualMaximum(Calendar.DATE);
				break;
			default:
				throw new IllegalArgumentException("invalid value for handling illegal day");
			}
		}

		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return calendar.getTime();
	}
	
	/**
	 * This method encodes a {@link Date} object to a byte[] representation.
	 * The resulting byte[] encodes a single digit out of YYMMDD with every byte.
	 * @param date the {@link Date} object to encode
	 * @return the {@link Date} object encoded as byte[]
	 */
	public static byte[] encodeDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		year %= 100;
		month++;
		
		byte y2 = (byte) (year%10);
		byte y1 = (byte) ((year-y2)/10);
		
		byte m2 = (byte) (month%10);
		byte m1 = (byte) ((month-m2)/10);
		
		byte d2 = (byte) (day%10);
		byte d1 = (byte) ((day-d2)/10);
		
		return new byte[]{y1, y2, m1, m2, d1, d2};
	}
	
	/**
	 * Creates a new byte array containing the bytes of the given input in
	 * inverted order.
	 * 
	 * @param input
	 * @return an inverted order byte array
	 */
	public static byte[] invertByteOrder(byte[] input) {
		byte[] result = new byte[input.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = input[input.length - i - 1];
		}
		return result;
	}
	
	/**
	 * Extracts the value field from an arbitrary structure containing a length
	 * field
	 * 
	 * @param data
	 * @param offsetLengthField
	 *            the offset to the length field
	 * @param lengthFieldLength
	 *            the length in bytes of the length field
	 * @return the value part as byte array
	 */
	public static byte[] getValue(byte[] data, int offsetLengthField,
			int lengthFieldLength) {
		int length = Utils.getIntFromUnsignedByteArray(Arrays.copyOfRange(data,
				offsetLengthField, offsetLengthField + lengthFieldLength));
		return Arrays.copyOfRange(data, offsetLengthField + lengthFieldLength,
				offsetLengthField + lengthFieldLength + length);
	}
	
	/**
	 * Extracts the value field from an arbitrary structure containing a flipped byte order length
	 * field
	 * 
	 * @param data
	 * @param offsetLengthField
	 *            the offset to the length field
	 * @param lengthFieldLength
	 *            the length in bytes of the length field
	 * @return the value part as byte array
	 */
	public static byte[] getValueFlippedByteOrder(byte[] data, int offsetLengthField,
			int lengthFieldLength) {
		int length = Utils.getIntFromUnsignedByteArray(Utils.invertByteOrder(Arrays.copyOfRange(data,
				offsetLengthField, offsetLengthField + lengthFieldLength)));
		return Arrays.copyOfRange(data, offsetLengthField + lengthFieldLength,
				offsetLengthField + lengthFieldLength + length);
	}
	
	/**
	 * Create a simple structure with length field from an arbitrary structure containing a length
	 * field
	 * 
	 * @see #getValue(byte[], int, int)
	 * 
	 * @param data
	 * @param offsetLengthField
	 *            the offset to the length field
	 * @param lengthFieldLength
	 *            the length in bytes of the length field
	 * @return the value part as byte array
	 */
	public static byte[] createLengthValue(byte[] data, int lengthFieldLength) {
		return Utils.concatByteArrays(padWithLeadingZeroes(Utils.toShortestUnsignedByteArray(data.length), lengthFieldLength), data);
	}
	
	/**
	 * Create a simple structure with length field from an arbitrary structure containing a length
	 * field with flipped byte order
	 * 
	 * @see #getValue(byte[], int, int)
	 * 
	 * @param data
	 * @param offsetLengthField
	 *            the offset to the length field
	 * @param lengthFieldLength
	 *            the length in bytes of the length field
	 * @return the value part as byte array
	 */
	public static byte[] createLengthValueFlippedByteOrder(byte[] data, int lengthFieldLength) {
		return Utils.concatByteArrays(invertByteOrder(padWithLeadingZeroes(Utils.toShortestUnsignedByteArray(data.length), lengthFieldLength)), data);
	}
}
