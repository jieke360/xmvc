package info.txtfile.app.test;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import com.alanx.xmvc.core.ActionHelper;
/**
 * example, 如需要获取request,response对象，可extends Processor 
 * @author xiaoqulai
 *
 */
public class TestProcessor{
	Logger log = LoggerFactory.getLogger(TestProcessor.class);
	private byte testbyte; //t=1
	private Byte testByte; //t=1
	private short testshort; //t=1
	private Short testShort; //t=1
	private int testInt; //t=1
	private Integer testInteger; //t=1
	private long testlong; //t=1
	private Long testLong; //t=1
	private float testfloat; //t=1
	private Float testFloat; //t=1
	private char testchar = '0'; //t=1
	private Character testCharacter; //t=1
	private double testdouble; //t=1
	private Double testDouble; //t=1
	private boolean testboolean; //t=1
	private Boolean testBoolean; //t=1
	private String testString; //t=1
	private Date testDate; //t=1
	private BigDecimal testBigDecimal; //t=1
	
	private byte[] testbyteArr; //t=1&t=2
	private Byte[] testByteArr; //t=1&t=2
	private short[] testshortArr; //t=1&t=2
	private Short[] testShortArr; //t=1&t=2
	private int[] testIntArr; //t=1&t=2
	private Integer[] testIntegerArr; //t=1&t=2
	private long[] testlongArr; //t=1&t=2
	private Long[] testLongArr; //t=1&t=2
	private float[] testfloatArr; //t=1&t=2
	private Float[] testFloatArr; //t=1&t=2
	private char[] testcharArr; //t=1&t=2
	private Character[] testCharacterArr; //t=1&t=2
	private double[] testdoubleArr; //t=1&t=2
	private Double[] testDoubleArr; //t=1&t=2
	private boolean[] testbooleanArr; //t=1&t=2
	private Boolean[] testBooleanArr; //t=1&t=2
	private String[] testStringArr; //t=1&t=2
	private Date[] testDateArr; //t=1&t=2
	private BigDecimal[] testBigDecimalArr; //t=1&t=2
	
	private List<Byte> testbyteList;	//t=1&t=2, t[0]=1&t[1]=2
	private List<Short> testShortList; //t=1&t=2,t[0]=1&t[1]=2
	private List<Integer> testIntegerList; //t=1&t=2, t[0]=1&t[1]=2
	private List<Long> testLongList; //t=1&t=2, t[0]=1&t[1]=2
	private List<Float> testFloatList; //t=1&t=2, t[0]=1&t[1]=2
	private List<Character> testCharacterList; //t=1&t=2, t[0]=1&t[1]=2
	private List<Double> testDoubleList; ///t=1&t=2, t[0]=1&t[1]=2
	private List<Boolean> testBooleanList; //t=1&t=2, t[0]=1&t[1]=2
	private List<Date> testDateList; //t=2011-01-01&t=2011-01-02 00:00:00
	private List<BigDecimal> testBigDecimalList;//t=1&t=2, t[0]=1&t[1]=2
	
	private LinkedList<String> testByteLinkedList; //t=1&t=2, t[0]=1&t[1]=2
	
	private Set<Byte> testbyteSet; //t=1&t=2
	private Set<Short> testShortSet; //t=1&t=2
	private Set<Integer> testIntegerSet; //t=1&t=2
	private Set<Long> testLongSet; //t=1&t=2
	private Set<Float> Set; //t=1&t=2
	private Set<Character> testCharacterSet; //t=1&t=2
	private Set<Double> testDoubleSet; //t=1&t=2
	private Set<Boolean> testBooleanSet; //t=1&t=2
	private Set<Date> testDateSet; //t=1&t=2
	private Set<BigDecimal> testBigDecimalSet; //t=1&t=2
	
	private Map<String,String> testMapStringString; //t[0]=1&t[1]=2
	private Map<String,Byte> testMapStringByte;//t[0]=1&t[1]=2
	private Map<String,Short> testMapStringShort;//t[0]=1&t[1]=2
	private Map<String,Integer> testMapStringInteger;//t[0]=1&t[1]=2
	private Map<String,Long> testMapStringLong;//t[0]=1&t[1]=2
	private Map<String,Float> testMapStringFloat;//t[0]=1&t[1]=2
	private Map<String,Character> testMapStringCharacter;//t[0]=1&t[1]=2
	private Map<String,Double> testMapStringDouble;//t[0]=1&t[1]=2
	private Map<String,Boolean> testMapStringBoolean;//t[0]=1&t[1]=2
	private Map<String,Date> testMapStringDate;//t[0]=1&t[1]=2
	private Map<String,BigDecimal> testMapStringBigDecimal;//t[0]=1&t[1]=2
	
	private Map<Integer,String> testMapIntegerString;//t[0]=1&t[1]=2
	private Map<Integer,Byte> testMapIntegerByte;//t[0]=1&t[1]=2
	private Map<Integer,Short> testMapIntegerShort;//t[0]=1&t[1]=2
	private Map<Integer,Integer> testMapIntegerInteger;//t[0]=1&t[1]=2
	private Map<Integer,Long> testMapIntegerLong;//t[0]=1&t[1]=2
	private Map<Integer,Float> testMapIntegerFloat;//t[0]=1&t[1]=2
	private Map<Integer,Character> testMapIntegerCharacter;//t[0]=1&t[1]=2
	private Map<Integer,Double> testMapIntegerDouble;//t[0]=1&t[1]=2
	private Map<Integer,Boolean> testMapIntegerBoolean;//t[0]=1&t[1]=2
	private Map<Integer,Date> testMapIntegerDate;//t[0]=1&t[1]=2
	private Map<Integer,BigDecimal> testMapIntegerBigDecimal;//t[0]=1&t[1]=2
	
	private Map<Character,String> testMapCharacterString;//t[0]=1&t[1]=2
	private Map<Character,Byte> testMapCharacterByte;//t[0]=1&t[1]=2
	private Map<Character,Short> testMapCharacterShort;//t[0]=1&t[1]=2
	private Map<Character,Integer> testMapCharacterInteger;//t[0]=1&t[1]=2
	private Map<Character,Long> testMapCharacterLong;//t[0]=1&t[1]=2
	private Map<Character,Float> testMapCharacterFloat;//t[0]=1&t[1]=2
	private Map<Character,Character> testMapCharacterCharacter;//t[0]=1&t[1]=2
	private Map<Character,Double> testMapCharacterDouble;//t[0]=1&t[1]=2
	private Map<Character,Boolean> testMapCharacterBoolean;//t[0]=1&t[1]=2
	private Map<Character,Date> testMapCharacterDate;//t[0]=1&t[1]=2
	private Map<Character,BigDecimal> testMapCharacterBigDecimal;//t[0]=1&t[1]=2
	
	private Map<Date,Boolean> testMapDateBoolean;//t[0]=1&t[1]=2
	
	
	//对象
	private TestModel testModel; //testModel.models[1].model.models[1].testInteger=0 & testModel.models[1].model.testInteger=1
	//对象数组
	private TestModel[] testModelArr; //testModelArr[0].model.testInteger=0&testModelArr[1].model.model.models[1].testInteger=1
	//对象List
	private List<TestModel> testModelList;
	//Set
	private Set<TestModel> testModelSet;

	//对象Map
	private Map<Byte,TestModel> testMapByteTestModel;
	private Map<Short,TestModel> testMapShortTestModel;
	private Map<Integer,TestModel> testMapIntegerTestModel;
	private Map<Long,TestModel> testMapLongTestModel;
	private Map<Float,TestModel> testMapFloatTestModel;
	private Map<Double,TestModel> testMapDoubleTestModel;
	private Map<Character,TestModel> testMapCharacterTestModel;
	private Map<Boolean,TestModel> testMapBooleanTestModel;
	private Map<Date,TestModel> testMapDateTestModel;
	private Map<BigDecimal,TestModel> testMapBigDecimalTestModel;

	//自定义转换器测试
	private Point testPoint;
	private Point[] testArryPoint;
	private List<Point> testListPoint;
	private Set<Point> testSetPoint;
	private Map<Integer,Point> testMapPoint;
	

	public Point getTestPoint() {
		return testPoint;
	}


	public void setTestPoint(Point testPoint) {
		this.testPoint = testPoint;
	}


	public Point[] getTestArryPoint() {
		return testArryPoint;
	}


	public void setTestArryPoint(Point[] testArryPoint) {
		this.testArryPoint = testArryPoint;
	}


	public List<Point> getTestListPoint() {
		return testListPoint;
	}


	public void setTestListPoint(List<Point> testListPoint) {
		this.testListPoint = testListPoint;
	}


	public Set<Point> getTestSetPoint() {
		return testSetPoint;
	}


	public void setTestSetPoint(Set<Point> testSetPoint) {
		this.testSetPoint = testSetPoint;
	}


	public Map<Integer, Point> getTestMapPoint() {
		return testMapPoint;
	}


	public void setTestMapPoint(Map<Integer, Point> testMapPoint) {
		this.testMapPoint = testMapPoint;
	}


	public LinkedList<String> getTestByteLinkedList() {
		return testByteLinkedList;
	}


	public void setTestByteLinkedList(LinkedList<String> testByteLinkedList) {
		this.testByteLinkedList = testByteLinkedList;
	}


	public Set<Byte> getTestbyteSet() {
		return testbyteSet;
	}


	public void setTestbyteSet(Set<Byte> testbyteSet) {
		this.testbyteSet = testbyteSet;
	}


	public Set<Short> getTestShortSet() {
		return testShortSet;
	}


	public void setTestShortSet(Set<Short> testShortSet) {
		this.testShortSet = testShortSet;
	}


	public Set<Integer> getTestIntegerSet() {
		return testIntegerSet;
	}


	public void setTestIntegerSet(Set<Integer> testIntegerSet) {
		this.testIntegerSet = testIntegerSet;
	}


	public Set<Long> getTestLongSet() {
		return testLongSet;
	}


	public void setTestLongSet(Set<Long> testLongSet) {
		this.testLongSet = testLongSet;
	}


	public Set<Float> getSet() {
		return Set;
	}


	public void setSet(Set<Float> set) {
		Set = set;
	}


	public Set<Character> getTestCharacterSet() {
		return testCharacterSet;
	}


	public void setTestCharacterSet(Set<Character> testCharacterSet) {
		this.testCharacterSet = testCharacterSet;
	}


	public Set<Double> getTestDoubleSet() {
		return testDoubleSet;
	}


	public void setTestDoubleSet(Set<Double> testDoubleSet) {
		this.testDoubleSet = testDoubleSet;
	}


	public Set<Boolean> getTestBooleanSet() {
		return testBooleanSet;
	}


	public void setTestBooleanSet(Set<Boolean> testBooleanSet) {
		this.testBooleanSet = testBooleanSet;
	}


	public Set<Date> getTestDateSet() {
		return testDateSet;
	}


	public void setTestDateSet(Set<Date> testDateSet) {
		this.testDateSet = testDateSet;
	}


	public Set<BigDecimal> getTestBigDecimalSet() {
		return testBigDecimalSet;
	}


	public void setTestBigDecimalSet(Set<BigDecimal> testBigDecimalSet) {
		this.testBigDecimalSet = testBigDecimalSet;
	}


	public Set<TestModel> getTestModelSet() {
		return testModelSet;
	}


	public void setTestModelSet(Set<TestModel> testModelSet) {
		this.testModelSet = testModelSet;
	}


	public byte getTestbyte() {
		return testbyte;
	}


	public void setTestbyte(byte testbyte) {
		this.testbyte = testbyte;
	}


	public Byte getTestByte() {
		return testByte;
	}


	public void setTestByte(Byte testByte) {
		this.testByte = testByte;
	}


	public short getTestshort() {
		return testshort;
	}


	public void setTestshort(short testshort) {
		this.testshort = testshort;
	}


	public Short getTestShort() {
		return testShort;
	}


	public void setTestShort(Short testShort) {
		this.testShort = testShort;
	}


	public int getTestInt() {
		return testInt;
	}


	public void setTestInt(int testInt) {
		this.testInt = testInt;
	}


	public Integer getTestInteger() {
		return testInteger;
	}


	public void setTestInteger(Integer testInteger) {
		this.testInteger = testInteger;
	}


	public long getTestlong() {
		return testlong;
	}


	public void setTestlong(long testlong) {
		this.testlong = testlong;
	}


	public Long getTestLong() {
		return testLong;
	}


	public void setTestLong(Long testLong) {
		this.testLong = testLong;
	}


	public float getTestfloat() {
		return testfloat;
	}


	public void setTestfloat(float testfloat) {
		this.testfloat = testfloat;
	}


	public Float getTestFloat() {
		return testFloat;
	}


	public void setTestFloat(Float testFloat) {
		this.testFloat = testFloat;
	}


	public char getTestchar() {
		return testchar;
	}


	public void setTestchar(char testchar) {
		this.testchar = testchar;
	}


	public Character getTestCharacter() {
		return testCharacter;
	}


	public void setTestCharacter(Character testCharacter) {
		this.testCharacter = testCharacter;
	}


	public double getTestdouble() {
		return testdouble;
	}


	public void setTestdouble(double testdouble) {
		this.testdouble = testdouble;
	}


	public Double getTestDouble() {
		return testDouble;
	}


	public void setTestDouble(Double testDouble) {
		this.testDouble = testDouble;
	}


	public boolean isTestboolean() {
		return testboolean;
	}


	public void setTestboolean(boolean testboolean) {
		this.testboolean = testboolean;
	}


	public Boolean getTestBoolean() {
		return testBoolean;
	}


	public void setTestBoolean(Boolean testBoolean) {
		this.testBoolean = testBoolean;
	}


	public String getTestString() {
		return testString;
	}


	public void setTestString(String testString) {
		this.testString = testString;
	}


	public Date getTestDate() {
		return testDate;
	}


	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}


	public BigDecimal getTestBigDecimal() {
		return testBigDecimal;
	}


	public void setTestBigDecimal(BigDecimal testBigDecimal) {
		this.testBigDecimal = testBigDecimal;
	}


	public byte[] getTestbyteArr() {
		return testbyteArr;
	}


	public void setTestbyteArr(byte[] testbyteArr) {
		this.testbyteArr = testbyteArr;
	}


	public Byte[] getTestByteArr() {
		return testByteArr;
	}


	public void setTestByteArr(Byte[] testByteArr) {
		this.testByteArr = testByteArr;
	}


	public short[] getTestshortArr() {
		return testshortArr;
	}


	public void setTestshortArr(short[] testshortArr) {
		this.testshortArr = testshortArr;
	}


	public Short[] getTestShortArr() {
		return testShortArr;
	}


	public void setTestShortArr(Short[] testShortArr) {
		this.testShortArr = testShortArr;
	}


	public int[] getTestIntArr() {
		return testIntArr;
	}


	public void setTestIntArr(int[] testIntArr) {
		this.testIntArr = testIntArr;
	}


	public Integer[] getTestIntegerArr() {
		return testIntegerArr;
	}


	public void setTestIntegerArr(Integer[] testIntegerArr) {
		this.testIntegerArr = testIntegerArr;
	}


	public long[] getTestlongArr() {
		return testlongArr;
	}


	public void setTestlongArr(long[] testlongArr) {
		this.testlongArr = testlongArr;
	}


	public Long[] getTestLongArr() {
		return testLongArr;
	}


	public void setTestLongArr(Long[] testLongArr) {
		this.testLongArr = testLongArr;
	}


	public float[] getTestfloatArr() {
		return testfloatArr;
	}


	public void setTestfloatArr(float[] testfloatArr) {
		this.testfloatArr = testfloatArr;
	}


	public Float[] getTestFloatArr() {
		return testFloatArr;
	}


	public void setTestFloatArr(Float[] testFloatArr) {
		this.testFloatArr = testFloatArr;
	}


	public char[] getTestcharArr() {
		return testcharArr;
	}


	public void setTestcharArr(char[] testcharArr) {
		this.testcharArr = testcharArr;
	}


	public Character[] getTestCharacterArr() {
		return testCharacterArr;
	}


	public void setTestCharacterArr(Character[] testCharacterArr) {
		this.testCharacterArr = testCharacterArr;
	}


	public double[] getTestdoubleArr() {
		return testdoubleArr;
	}


	public void setTestdoubleArr(double[] testdoubleArr) {
		this.testdoubleArr = testdoubleArr;
	}


	public Double[] getTestDoubleArr() {
		return testDoubleArr;
	}


	public void setTestDoubleArr(Double[] testDoubleArr) {
		this.testDoubleArr = testDoubleArr;
	}


	public boolean[] getTestbooleanArr() {
		return testbooleanArr;
	}


	public void setTestbooleanArr(boolean[] testbooleanArr) {
		this.testbooleanArr = testbooleanArr;
	}


	public Boolean[] getTestBooleanArr() {
		return testBooleanArr;
	}


	public void setTestBooleanArr(Boolean[] testBooleanArr) {
		this.testBooleanArr = testBooleanArr;
	}


	public String[] getTestStringArr() {
		return testStringArr;
	}


	public void setTestStringArr(String[] testStringArr) {
		this.testStringArr = testStringArr;
	}


	public Date[] getTestDateArr() {
		return testDateArr;
	}


	public void setTestDateArr(Date[] testDateArr) {
		this.testDateArr = testDateArr;
	}


	public BigDecimal[] getTestBigDecimalArr() {
		return testBigDecimalArr;
	}


	public void setTestBigDecimalArr(BigDecimal[] testBigDecimalArr) {
		this.testBigDecimalArr = testBigDecimalArr;
	}


	public List<Byte> getTestbyteList() {
		return testbyteList;
	}


	public void setTestbyteList(List<Byte> testbyteList) {
		this.testbyteList = testbyteList;
	}


	public List<Short> getTestShortList() {
		return testShortList;
	}


	public void setTestShortList(List<Short> testShortList) {
		this.testShortList = testShortList;
	}


	public List<Integer> getTestIntegerList() {
		return testIntegerList;
	}


	public void setTestIntegerList(List<Integer> testIntegerList) {
		this.testIntegerList = testIntegerList;
	}


	public List<Long> getTestLongList() {
		return testLongList;
	}


	public void setTestLongList(List<Long> testLongList) {
		this.testLongList = testLongList;
	}


	public List<Float> getTestFloatList() {
		return testFloatList;
	}


	public void setTestFloatList(List<Float> testFloatList) {
		this.testFloatList = testFloatList;
	}


	public List<Character> getTestCharacterList() {
		return testCharacterList;
	}


	public void setTestCharacterList(List<Character> testCharacterList) {
		this.testCharacterList = testCharacterList;
	}


	public List<Double> getTestDoubleList() {
		return testDoubleList;
	}


	public void setTestDoubleList(List<Double> testDoubleList) {
		this.testDoubleList = testDoubleList;
	}


	public List<Boolean> getTestBooleanList() {
		return testBooleanList;
	}


	public void setTestBooleanList(List<Boolean> testBooleanList) {
		this.testBooleanList = testBooleanList;
	}


	public List<Date> getTestDateList() {
		return testDateList;
	}


	public void setTestDateList(List<Date> testDateList) {
		this.testDateList = testDateList;
	}


	public List<BigDecimal> getTestBigDecimalList() {
		return testBigDecimalList;
	}


	public void setTestBigDecimalList(List<BigDecimal> testBigDecimalList) {
		this.testBigDecimalList = testBigDecimalList;
	}


	public Map<String, String> getTestMapStringString() {
		return testMapStringString;
	}


	public void setTestMapStringString(Map<String, String> testMapStringString) {
		this.testMapStringString = testMapStringString;
	}


	public Map<String, Byte> getTestMapStringByte() {
		return testMapStringByte;
	}


	public void setTestMapStringByte(Map<String, Byte> testMapStringByte) {
		this.testMapStringByte = testMapStringByte;
	}


	public Map<String, Short> getTestMapStringShort() {
		return testMapStringShort;
	}


	public void setTestMapStringShort(Map<String, Short> testMapStringShort) {
		this.testMapStringShort = testMapStringShort;
	}


	public Map<String, Integer> getTestMapStringInteger() {
		return testMapStringInteger;
	}


	public void setTestMapStringInteger(Map<String, Integer> testMapStringInteger) {
		this.testMapStringInteger = testMapStringInteger;
	}


	public Map<String, Long> getTestMapStringLong() {
		return testMapStringLong;
	}


	public void setTestMapStringLong(Map<String, Long> testMapStringLong) {
		this.testMapStringLong = testMapStringLong;
	}


	public Map<String, Float> getTestMapStringFloat() {
		return testMapStringFloat;
	}


	public void setTestMapStringFloat(Map<String, Float> testMapStringFloat) {
		this.testMapStringFloat = testMapStringFloat;
	}


	public Map<String, Character> getTestMapStringCharacter() {
		return testMapStringCharacter;
	}


	public void setTestMapStringCharacter(Map<String, Character> testMapStringCharacter) {
		this.testMapStringCharacter = testMapStringCharacter;
	}


	public Map<String, Double> getTestMapStringDouble() {
		return testMapStringDouble;
	}


	public void setTestMapStringDouble(Map<String, Double> testMapStringDouble) {
		this.testMapStringDouble = testMapStringDouble;
	}


	public Map<String, Boolean> getTestMapStringBoolean() {
		return testMapStringBoolean;
	}


	public void setTestMapStringBoolean(Map<String, Boolean> testMapStringBoolean) {
		this.testMapStringBoolean = testMapStringBoolean;
	}


	public Map<String, Date> getTestMapStringDate() {
		return testMapStringDate;
	}


	public void setTestMapStringDate(Map<String, Date> testMapStringDate) {
		this.testMapStringDate = testMapStringDate;
	}


	public Map<String, BigDecimal> getTestMapStringBigDecimal() {
		return testMapStringBigDecimal;
	}


	public void setTestMapStringBigDecimal(Map<String, BigDecimal> testMapStringBigDecimal) {
		this.testMapStringBigDecimal = testMapStringBigDecimal;
	}


	public Map<Integer, String> getTestMapIntegerString() {
		return testMapIntegerString;
	}


	public void setTestMapIntegerString(Map<Integer, String> testMapIntegerString) {
		this.testMapIntegerString = testMapIntegerString;
	}


	public Map<Integer, Byte> getTestMapIntegerByte() {
		return testMapIntegerByte;
	}


	public void setTestMapIntegerByte(Map<Integer, Byte> testMapIntegerByte) {
		this.testMapIntegerByte = testMapIntegerByte;
	}


	public Map<Integer, Short> getTestMapIntegerShort() {
		return testMapIntegerShort;
	}


	public void setTestMapIntegerShort(Map<Integer, Short> testMapIntegerShort) {
		this.testMapIntegerShort = testMapIntegerShort;
	}


	public Map<Integer, Integer> getTestMapIntegerInteger() {
		return testMapIntegerInteger;
	}


	public void setTestMapIntegerInteger(Map<Integer, Integer> testMapIntegerInteger) {
		this.testMapIntegerInteger = testMapIntegerInteger;
	}


	public Map<Integer, Long> getTestMapIntegerLong() {
		return testMapIntegerLong;
	}


	public void setTestMapIntegerLong(Map<Integer, Long> testMapIntegerLong) {
		this.testMapIntegerLong = testMapIntegerLong;
	}


	public Map<Integer, Float> getTestMapIntegerFloat() {
		return testMapIntegerFloat;
	}


	public void setTestMapIntegerFloat(Map<Integer, Float> testMapIntegerFloat) {
		this.testMapIntegerFloat = testMapIntegerFloat;
	}


	public Map<Integer, Character> getTestMapIntegerCharacter() {
		return testMapIntegerCharacter;
	}


	public void setTestMapIntegerCharacter(Map<Integer, Character> testMapIntegerCharacter) {
		this.testMapIntegerCharacter = testMapIntegerCharacter;
	}


	public Map<Integer, Double> getTestMapIntegerDouble() {
		return testMapIntegerDouble;
	}


	public void setTestMapIntegerDouble(Map<Integer, Double> testMapIntegerDouble) {
		this.testMapIntegerDouble = testMapIntegerDouble;
	}


	public Map<Integer, Boolean> getTestMapIntegerBoolean() {
		return testMapIntegerBoolean;
	}


	public void setTestMapIntegerBoolean(Map<Integer, Boolean> testMapIntegerBoolean) {
		this.testMapIntegerBoolean = testMapIntegerBoolean;
	}


	public Map<Integer, Date> getTestMapIntegerDate() {
		return testMapIntegerDate;
	}


	public void setTestMapIntegerDate(Map<Integer, Date> testMapIntegerDate) {
		this.testMapIntegerDate = testMapIntegerDate;
	}


	public Map<Integer, BigDecimal> getTestMapIntegerBigDecimal() {
		return testMapIntegerBigDecimal;
	}


	public void setTestMapIntegerBigDecimal(Map<Integer, BigDecimal> testMapIntegerBigDecimal) {
		this.testMapIntegerBigDecimal = testMapIntegerBigDecimal;
	}


	public Map<Character, String> getTestMapCharacterString() {
		return testMapCharacterString;
	}


	public void setTestMapCharacterString(Map<Character, String> testMapCharacterString) {
		this.testMapCharacterString = testMapCharacterString;
	}


	public Map<Character, Byte> getTestMapCharacterByte() {
		return testMapCharacterByte;
	}


	public void setTestMapCharacterByte(Map<Character, Byte> testMapCharacterByte) {
		this.testMapCharacterByte = testMapCharacterByte;
	}


	public Map<Character, Short> getTestMapCharacterShort() {
		return testMapCharacterShort;
	}


	public void setTestMapCharacterShort(Map<Character, Short> testMapCharacterShort) {
		this.testMapCharacterShort = testMapCharacterShort;
	}


	public Map<Character, Integer> getTestMapCharacterInteger() {
		return testMapCharacterInteger;
	}


	public void setTestMapCharacterInteger(Map<Character, Integer> testMapCharacterInteger) {
		this.testMapCharacterInteger = testMapCharacterInteger;
	}


	public Map<Character, Long> getTestMapCharacterLong() {
		return testMapCharacterLong;
	}


	public void setTestMapCharacterLong(Map<Character, Long> testMapCharacterLong) {
		this.testMapCharacterLong = testMapCharacterLong;
	}


	public Map<Character, Float> getTestMapCharacterFloat() {
		return testMapCharacterFloat;
	}


	public void setTestMapCharacterFloat(Map<Character, Float> testMapCharacterFloat) {
		this.testMapCharacterFloat = testMapCharacterFloat;
	}


	public Map<Character, Character> getTestMapCharacterCharacter() {
		return testMapCharacterCharacter;
	}


	public void setTestMapCharacterCharacter(Map<Character, Character> testMapCharacterCharacter) {
		this.testMapCharacterCharacter = testMapCharacterCharacter;
	}


	public Map<Character, Double> getTestMapCharacterDouble() {
		return testMapCharacterDouble;
	}


	public void setTestMapCharacterDouble(Map<Character, Double> testMapCharacterDouble) {
		this.testMapCharacterDouble = testMapCharacterDouble;
	}


	public Map<Character, Boolean> getTestMapCharacterBoolean() {
		return testMapCharacterBoolean;
	}


	public void setTestMapCharacterBoolean(Map<Character, Boolean> testMapCharacterBoolean) {
		this.testMapCharacterBoolean = testMapCharacterBoolean;
	}


	public Map<Character, Date> getTestMapCharacterDate() {
		return testMapCharacterDate;
	}


	public void setTestMapCharacterDate(Map<Character, Date> testMapCharacterDate) {
		this.testMapCharacterDate = testMapCharacterDate;
	}


	public Map<Character, BigDecimal> getTestMapCharacterBigDecimal() {
		return testMapCharacterBigDecimal;
	}


	public void setTestMapCharacterBigDecimal(Map<Character, BigDecimal> testMapCharacterBigDecimal) {
		this.testMapCharacterBigDecimal = testMapCharacterBigDecimal;
	}


	public TestModel getTestModel() {
		return testModel;
	}


	public void setTestModel(TestModel testModel) {
		this.testModel = testModel;
	}


	public TestModel[] getTestModelArr() {
		return testModelArr;
	}


	public void setTestModelArr(TestModel[] testModelArr) {
		this.testModelArr = testModelArr;
	}


	public List<TestModel> getTestModelList() {
		return testModelList;
	}


	public void setTestModelList(List<TestModel> testModelList) {
		this.testModelList = testModelList;
	}


	public Map<Byte, TestModel> getTestMapByteTestModel() {
		return testMapByteTestModel;
	}


	public void setTestMapByteTestModel(Map<Byte, TestModel> testMapByteTestModel) {
		this.testMapByteTestModel = testMapByteTestModel;
	}


	public Map<Short, TestModel> getTestMapShortTestModel() {
		return testMapShortTestModel;
	}


	public void setTestMapShortTestModel(Map<Short, TestModel> testMapShortTestModel) {
		this.testMapShortTestModel = testMapShortTestModel;
	}


	public Map<Integer, TestModel> getTestMapIntegerTestModel() {
		return testMapIntegerTestModel;
	}


	public void setTestMapIntegerTestModel(Map<Integer, TestModel> testMapIntegerTestModel) {
		this.testMapIntegerTestModel = testMapIntegerTestModel;
	}


	public Map<Long, TestModel> getTestMapLongTestModel() {
		return testMapLongTestModel;
	}


	public void setTestMapLongTestModel(Map<Long, TestModel> testMapLongTestModel) {
		this.testMapLongTestModel = testMapLongTestModel;
	}


	public Map<Float, TestModel> getTestMapFloatTestModel() {
		return testMapFloatTestModel;
	}


	public void setTestMapFloatTestModel(Map<Float, TestModel> testMapFloatTestModel) {
		this.testMapFloatTestModel = testMapFloatTestModel;
	}


	public Map<Double, TestModel> getTestMapDoubleTestModel() {
		return testMapDoubleTestModel;
	}


	public void setTestMapDoubleTestModel(Map<Double, TestModel> testMapDoubleTestModel) {
		this.testMapDoubleTestModel = testMapDoubleTestModel;
	}


	public Map<Character, TestModel> getTestMapCharacterTestModel() {
		return testMapCharacterTestModel;
	}


	public void setTestMapCharacterTestModel(Map<Character, TestModel> testMapCharacterTestModel) {
		this.testMapCharacterTestModel = testMapCharacterTestModel;
	}


	public Map<Boolean, TestModel> getTestMapBooleanTestModel() {
		return testMapBooleanTestModel;
	}


	public void setTestMapBooleanTestModel(Map<Boolean, TestModel> testMapBooleanTestModel) {
		this.testMapBooleanTestModel = testMapBooleanTestModel;
	}


	public Map<Date, TestModel> getTestMapDateTestModel() {
		return testMapDateTestModel;
	}


	public void setTestMapDateTestModel(Map<Date, TestModel> testMapDateTestModel) {
		this.testMapDateTestModel = testMapDateTestModel;
	}


	public Map<BigDecimal, TestModel> getTestMapBigDecimalTestModel() {
		return testMapBigDecimalTestModel;
	}


	public void setTestMapBigDecimalTestModel(Map<BigDecimal, TestModel> testMapBigDecimalTestModel) {
		this.testMapBigDecimalTestModel = testMapBigDecimalTestModel;
	}


	public Map<Date, Boolean> getTestMapDateBoolean() {
		return testMapDateBoolean;
	}


	public void setTestMapDateBoolean(Map<Date, Boolean> testMapDateBoolean) {
		this.testMapDateBoolean = testMapDateBoolean;
	}


	public void test() {
		String jsonString = JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd",SerializerFeature.PrettyFormat);
		try {
			ServletContext.getResponse().getWriter().print(jsonString);
			ServletContext.getResponse().getWriter().close();
			log.info("json:\n{}",jsonString);
		} catch (IOException e) {
			throw new RuntimeException("响应异常", e);
		}
	}
}
