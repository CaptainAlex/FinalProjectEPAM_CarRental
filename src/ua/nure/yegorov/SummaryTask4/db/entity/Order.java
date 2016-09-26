package ua.nure.yegorov.SummaryTask4.db.entity;

import java.sql.Date;

/**
 * Order entity.
 * 
 * @author A.Yegorov
 *
 */

public class Order extends Entity {

	private static final long serialVersionUID = -8603843450683498202L;

	private int userId;

	private int carId;

	private boolean driverStatus;

	private Date orderData;

	private Date returnData;

	private int orderPrice;

	private int statusId;

	private String rejectionReason;

	private boolean damage;

	private int priceForRepairs;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarId(int carId) {
		this.carId = carId;
	}

	public boolean isDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(boolean driverStatus) {
		this.driverStatus = driverStatus;
	}

	public Date getOrderData() {
		return orderData;
	}

	public void setOrderData(Date orderData) {
		this.orderData = orderData;
	}

	public Date getReturnData() {
		return returnData;
	}

	public void setReturnData(Date returnData) {
		this.returnData = returnData;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public boolean isDamage() {
		return damage;
	}

	public void setDamage(boolean damage) {
		this.damage = damage;
	}

	public int getPriceForRepairs() {
		return priceForRepairs;
	}

	public void setPriceForRepairs(int priceForRepairs) {
		this.priceForRepairs = priceForRepairs;
	}

	@Override
	public String toString() {
		return "Order [userId=" + userId + ", carId=" + carId
				+ ", driverStatus=" + driverStatus + ", orderData=" + orderData
				+ ", returnData=" + returnData + ", orderPrice=" + orderPrice
				+ ", statusId=" + statusId + ", rejectionReason="
				+ rejectionReason + ", damage=" + damage + ", priceForRepairs="
				+ priceForRepairs + "]";
	}

}
