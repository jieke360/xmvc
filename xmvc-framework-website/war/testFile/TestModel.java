package info.txtfile.app.test;

import java.util.List;

import com.alanx.xmvc.core.Processor;


public class TestModel extends Processor {

	private static final long serialVersionUID = -6050465713367923176L;

	private int testInt;
	private Integer testInteger;
	private String testString;
	
	TestModel model;
	List<TestModel> models;
	TestModel[] testModelArr;
	
	
	public List<TestModel> getModels() {
		return models;
	}

	public void setModels(List<TestModel> models) {
		this.models = models;
	}

	public TestModel getModel() {
		return model;
	}

	public void setModel(TestModel model) {
		this.model = model;
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

	public String getTestString() {
		return testString;
	}

	public void setTestString(String testString) {
		this.testString = testString;
	}

	public TestModel[] getTestModelArr() {
		return testModelArr;
	}

	public void setTestModelArr(TestModel[] testModelArr) {
		this.testModelArr = testModelArr;
	}
	
	
}
