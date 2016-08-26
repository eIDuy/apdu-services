/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//7000, 7001, 7002, 7004
//son los primeros requests al MW id-data
/**
 *
 * @author gdotta
 */
public class FCITemplate {
    //TODO solve the EF DF distinction with inheritance
    private boolean isDF;
    private boolean isEF;
    private String fileName; //only for DF
    private int fileSize; //only for EF
    private byte fdb; //only for EF
    private int fileId; 
    private byte lifeCycleStatusByte; //only for EF

    public boolean isIsDF() {
        return isDF;
    }

    public void setIsDF(boolean isDF) {
        this.isDF = isDF;
    }

    public boolean isIsEF() {
        return isEF;
    }

    public void setIsEF(boolean isEF) {
        this.isEF = isEF;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public byte getFdb() {
        return fdb;
    }

    public void setFdb(byte fdb) {
        this.fdb = fdb;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public byte getLifeCycleStatusByte() {
        return lifeCycleStatusByte;
    }

    public void setLifeCycleStatusByte(byte lifeCycleStatusByte) {
        this.lifeCycleStatusByte = lifeCycleStatusByte;
    }
  

    public FCITemplate() {
    }
   
    public FCITemplate(boolean isDF, boolean isEF, String fileName, short fileSize, byte fdb, short fileId, byte lifeCycleStatusByte) {
        this.isDF = isDF;
        this.isEF = isEF;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.fdb = fdb;
        this.fileId = fileId;
        this.lifeCycleStatusByte = lifeCycleStatusByte;
    }
    
    public void buildFromBuffer(byte[] buffer, int offset, int length) {
        //FABRIZIO: Esta Firma es la semantica que generalmente usan las tarjetas
        //para estas operaciones, aunque no tiene por que ser la que usemos nosotros...


        //TODO build a neat TLV parser
        if (buffer[offset] != 0x6F) {
            //error
            return;
        }
        //TODO support DFs
        //TODO implement a function to convert
        if (buffer[offset+2] == 0x83) {
            //DF
            
        } else {
            //EF
            //& 0xFF para pasar de byte a int.
            this.fileSize =  (0xff & buffer[offset+4])*256 + (0xff & buffer[offset+5]);
            this.fdb = buffer[offset+8];
            this.fileId =  (0xff & buffer[offset+11])*256 +  (0xff & buffer[offset+12]);
            this.lifeCycleStatusByte = buffer[15];
        }
        
    }
    
}
