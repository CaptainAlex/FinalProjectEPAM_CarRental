package ua.nure.yegorov.SummaryTask4.db.entity;

/**
 * Car entity.
 * 
 * @author A.Yegorov
 *
 */

public class Car extends Entity {

	private static final long serialVersionUID = -5178381616966650418L;

	private String name;

	private String mark;

	private String carClass;

	private int carPrice;

	private int carDriverPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCarClass() {
		return carClass;
	}

	public void setCarClass(String carClass) {
		this.carClass = carClass;
	}

	public int getCarPrice() {
		return carPrice;
	}

	public void setCarPrice(int carPrice) {
		this.carPrice = carPrice;
	} 
	public int getCarDriverPrice() {
		return carDriverPrice;
	}

	public void setCarDriverPrice(int carDriverPrice) {
		this.carDriverPrice = carDriverPrice;
	}

	@Override
	public String toString() {
		return "Car [name=" + name + ", mark=" + mark + ", carClass="
				+ carClass + ", carPrice=" + carPrice + ", carDriverPrice="
				+ carDriverPrice + "]";
	}

}
