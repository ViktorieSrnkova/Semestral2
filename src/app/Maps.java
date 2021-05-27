/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author VikyVixxxen
 */
public class Maps {
    private String maps;

    public Maps(String maps) {
        this.maps = maps;
    }    
    
     public String getMaps() {
        return maps;
    }

    public void setMaps(String maps) {
        this.maps = maps;
    }
    @Override
   public String toString() {
        return String.format("%16s",maps);
    }
   
    public static void main(String[] args) {
        Maps newMap = new Maps("Za pokladem");
        System.out.println(newMap.toString());
    }
}
