package com.kurukshetra.controller.hospitalController;

import com.kurukshetra.dao.hospitalDao.HospitalResourceDao;
import com.kurukshetra.model.hospitalModel.HospitalResourceModel;

public class HospitalResourceController {

    private HospitalResourceDao hospitalResourceDao;

    public HospitalResourceController() {
        hospitalResourceDao = new HospitalResourceDao();
    }

    public void saveHospitalResource(
            String hospitalId,
            int totalICUBeds,
            int availableICUBeds,
            int totalEmergencyBeds,
            int availableEmergencyBeds,
            int totalGeneralBeds,
            int availableGeneralBeds,
            int totalVentilators,
            int availableVentilators,
            int oxygenReserve) {

        HospitalResourceModel resource = new HospitalResourceModel(
                totalICUBeds,
                availableICUBeds,
                totalEmergencyBeds,
                availableEmergencyBeds,
                totalGeneralBeds,
                availableGeneralBeds,
                totalVentilators,
                availableVentilators,
                oxygenReserve);

        hospitalResourceDao.saveHospitalResource(
                hospitalId,
                resource);
    }

    public HospitalResourceModel getHospitalResource(
            String hospitalId) {

        return hospitalResourceDao.getHospitalResource(
                hospitalId);
    }

    public void listenToHospitalResource(
            String hospitalId,
            java.util.function.Consumer<HospitalResourceModel> listener) {

        hospitalResourceDao.listenToHospitalResource(
                hospitalId,
                listener);
    }
}