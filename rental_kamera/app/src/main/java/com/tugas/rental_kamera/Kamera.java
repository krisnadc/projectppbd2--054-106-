package com.tugas.rental_kamera;

public class Kamera {
    private String KodeKamera,NamaKamera,Status;

    public Kamera(String a, String b, String c) {
        KodeKamera = a;
        NamaKamera = b;
        Status = c;
    }

    public String getKodeKamera() {
        return KodeKamera;
    }
    public void setKodeKamera(String masuk) {
        this.KodeKamera = masuk;
    }

    public String getNamaKamera() {
        return NamaKamera;
    }
    public void setNamaKamera(String masuk) {
        this.NamaKamera = masuk;
    }

    public String getStatus() {
        return Status;
    }
    public void setStatus(String masuk) {
        this.Status = masuk;
    }

}
