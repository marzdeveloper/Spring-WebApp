package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Sender;

public interface DeviceService {
	List<Device> findAll();
	
	Device findById(int id);
	
	Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn);
	
	Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn, Date checkOut, Sender sender);
	
	Device update(Device device);
	
	void delete(int id);

	void delete(Device device);
	
	List<Device> findAllBySenderId(int sender);
}
