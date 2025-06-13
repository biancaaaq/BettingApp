package proiect.bet.sportbet.models;

public class MesajDto {
    private String continut;
    private Long grupId;
    private Long utilizatorId;

    // Getteri și setteri
    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public Long getGrupId() {
        return grupId;
    }

    public void setGrupId(Long grupId) {
        this.grupId = grupId;
    }

    public Long getUtilizatorId() {
        return utilizatorId;
    }

    public void setUtilizatorId(Long utilizatorId) {
        this.utilizatorId = utilizatorId;
    }
}