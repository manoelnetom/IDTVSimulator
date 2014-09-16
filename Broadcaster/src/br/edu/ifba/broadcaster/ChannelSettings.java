package br.edu.ifba.broadcaster;

public class ChannelSettings {
    
    private String name;
    private String ip;
    private int mainContentPort; 
    private int extraContentPort;
    private int carouselTime;

    public ChannelSettings(String name, String ip, int mainContentPort, int extraContentPort, int carouselTime) {
        
        this.name= name;
        this.ip = ip;
        this.mainContentPort = mainContentPort;
        this.extraContentPort = extraContentPort;
        this.carouselTime = carouselTime;
    }
    
    /**
     * @return the Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mainContentPort
     */
    public int getMainContentPort() {
        return mainContentPort;
    }

    /**
     * @param mainContentPort the mainContentPort to set
     */
    public void setMainContentPort(int mainContentPort) {
        this.mainContentPort = mainContentPort;
    }

    /**
     * @return the extraContentPort
     */
    public int getExtraContentPort() {
        return extraContentPort;
    }

    /**
     * @param extraContentPort the extraContentPort to set
     */
    public void setExtraContentPort(int extraContentPort) {
        this.extraContentPort = extraContentPort;
    }

    /**
     * @return the carouselTime
     */
    public int getCarouselTime() {
        return carouselTime;
    }

    /**
     * @param carouselTime the carouselTime to set
     */
    public void setCarouselTime(int carouselTime) {
        this.carouselTime = carouselTime;
    }
    
    
}
