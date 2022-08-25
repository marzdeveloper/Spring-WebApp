package advPro.GestioneDispositivi.Services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import advPro.GestioneDispositivi.Model.Dao.DeviceDao;
import advPro.GestioneDispositivi.Model.Entities.Device;
import advPro.GestioneDispositivi.Model.Entities.Sender;

@Transactional
@Service("deviceService")
public class DeviceServiceDefault implements DeviceService {

	private DeviceDao deviceRepository;
	
	@Override
	public List<Device> findAll() {
		return this.deviceRepository.findAll();
	}

	@Transactional(readOnly=true)
	@Override
	public Device findById(int id) {
		return this.deviceRepository.findById(id);
	}
	
	@Override
	public Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn) {
		return this.deviceRepository.create(serialNumber, brand, model, reason, document, checkIn, null, null);
	}
	
	@Override
	public Device create(String serialNumber, String brand, String model, String reason, String document, Date checkIn, Date checkOut, Sender sender) {
		return this.deviceRepository.create(serialNumber, brand, model, reason, document, checkIn, checkOut, sender);
	}

	@Override
	public Device update(Device divice) {
		return this.deviceRepository.update(divice);
	}

	@Override
	public void delete(int id) {
		Device dev = findById(id);
		this.deviceRepository.delete(dev);
	}
	
	@Autowired
	public void setDeviceRepository(DeviceDao deviceRepository) {
		this.deviceRepository = deviceRepository;
	}

	@Override
	public void delete(Device divice) {
		this.deviceRepository.delete(divice);
	}
	
	@Override
	public List<Device> findAllBySenderId(int sender) {
		return this.deviceRepository.findAllBySenderId(sender);
	}
}
