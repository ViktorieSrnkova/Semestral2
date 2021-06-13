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

    /**constructor
     *
     * @param maps
     */
    public Maps(String maps) {
        this.maps = maps;
    }    
    
    /**returns map
     *
     * @return String - map
     */
    public String getMaps() {
        return maps;
    }

    /**
     *
     * @return
     */
    @Override
   public String toString() {
        return String.format("%16s",maps);
    }
   
    /**testing main
     *
     * @param args
     */
    public static void main(String[] args) {
        Maps newMap = new Maps("Za pokladem");
        System.out.println(newMap.toString());
    }
}
