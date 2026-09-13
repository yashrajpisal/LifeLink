package com.kurukshetra.model.hospitalModel;

public class HospitalResourceModel {

    private int totalICUBeds;
    private int availableICUBeds;

    private int totalEmergencyBeds;
    private int availableEmergencyBeds;

    private int totalGeneralBeds;
    private int availableGeneralBeds;

    private int totalVentilators;
    private int availableVentilators;

    private int oxygenReserve;

    public HospitalResourceModel() {
    }

    public HospitalResourceModel(
            int totalICUBeds,
            int availableICUBeds,
            int totalEmergencyBeds,
            int availableEmergencyBeds,
            int totalGeneralBeds,
            int availableGeneralBeds,
            int totalVentilators,
            int availableVentilators,
            int oxygenReserve
    ) {
        this.totalICUBeds = totalICUBeds;
        this.availableICUBeds = availableICUBeds;
        this.totalEmergencyBeds = totalEmergencyBeds;
        this.availableEmergencyBeds = availableEmergencyBeds;
        this.totalGeneralBeds = totalGeneralBeds;
        this.availableGeneralBeds = availableGeneralBeds;
        this.totalVentilators = totalVentilators;
        this.availableVentilators = availableVentilators;
        this.oxygenReserve = oxygenReserve;
    }

    public int getTotalICUBeds() {
        return totalICUBeds;
    }

    public void setTotalICUBeds(int totalICUBeds) {
        this.totalICUBeds = totalICUBeds;
    }

    public int getAvailableICUBeds() {
        return availableICUBeds;
    }

    public void setAvailableICUBeds(int availableICUBeds) {
        this.availableICUBeds = availableICUBeds;
    }

    public int getTotalEmergencyBeds() {
        return totalEmergencyBeds;
    }

    public void setTotalEmergencyBeds(int totalEmergencyBeds) {
        this.totalEmergencyBeds = totalEmergencyBeds;
    }

    public int getAvailableEmergencyBeds() {
        return availableEmergencyBeds;
    }

    public void setAvailableEmergencyBeds(int availableEmergencyBeds) {
        this.availableEmergencyBeds = availableEmergencyBeds;
    }

    public int getTotalGeneralBeds() {
        return totalGeneralBeds;
    }

    public void setTotalGeneralBeds(int totalGeneralBeds) {
        this.totalGeneralBeds = totalGeneralBeds;
    }

    public int getAvailableGeneralBeds() {
        return availableGeneralBeds;
    }

    public void setAvailableGeneralBeds(int availableGeneralBeds) {
        this.availableGeneralBeds = availableGeneralBeds;
    }

    public int getTotalVentilators() {
        return totalVentilators;
    }

    public void setTotalVentilators(int totalVentilators) {
        this.totalVentilators = totalVentilators;
    }

    public int getAvailableVentilators() {
        return availableVentilators;
    }

    public void setAvailableVentilators(int availableVentilators) {
        this.availableVentilators = availableVentilators;
    }

    public int getOxygenReserve() {
        return oxygenReserve;
    }

    public void setOxygenReserve(int oxygenReserve) {
        this.oxygenReserve = oxygenReserve;
    }
}