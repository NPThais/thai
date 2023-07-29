
package com.tlk.entity;


public class Toping {
    private Integer matoping;
    private String tentoping;
    private Double giatoping;

    public Toping() {
    }

    public Toping(Integer matoping, String tentoping, Double giatoping) {
        this.matoping = matoping;
        this.tentoping = tentoping;
        this.giatoping = giatoping;
    }

    public Integer getMatoping() {
        return matoping;
    }

    public void setMatoping(Integer matoping) {
        this.matoping = matoping;
    }

    public String getTentoping() {
        return tentoping;
    }

    public void setTentoping(String tentoping) {
        this.tentoping = tentoping;
    }

    public Double getGiatoping() {
        return giatoping;
    }

    public void setGiatoping(Double giatoping) {
        this.giatoping = giatoping;
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Toping toping = (Toping) o;

        if (matoping != null ? !matoping.equals(toping.matoping) : toping.matoping != null) return false;
        if (tentoping != null ? !tentoping.equals(toping.tentoping) : toping.tentoping != null) return false;
        return giatoping != null ? giatoping.equals(toping.giatoping) : toping.giatoping == null;
    }

    @Override
    public int hashCode() {
        int result = matoping != null ? matoping.hashCode() : 0;
        result = 31 * result + (tentoping != null ? tentoping.hashCode() : 0);
        result = 31 * result + (giatoping != null ? giatoping.hashCode() : 0);
        return result;
    }
    
    
}
