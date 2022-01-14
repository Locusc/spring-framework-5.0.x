package cn.locusc.chapter5.code.fb;

import cn.locusc.chapter5.code.pojo.Car;
import org.springframework.beans.factory.FactoryBean;

public class CarFactoryBean implements FactoryBean<Car> {

	private String carInfo;

	@Override
	public Car getObject() {
		Car car = new Car();
		String[] infos = carInfo.split(",");
		car.setBrand(infos[0]);
		car.setMaxSpeed(Integer.parseInt(infos[1]));
		car.setPrice(Double.parseDouble(infos[2]));
		return car;
	}

	@Override
	public Class<?> getObjectType() {
		return Car.class;
	}

	public boolean isSingleton() {
		return false;
	}

	public String getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
}
