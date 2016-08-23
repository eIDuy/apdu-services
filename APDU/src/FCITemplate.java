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
class FCITemplate {
    //TODO solve the EF DF distinction with inheritance
    private boolean isDF;
    private boolean isEF;
    private String fileName; //only for DF
    private int fileSize; //only for EF
    private byte fdb; //only for EF
    private int fileId; 
    private byte lifeCycleStatusByte; //only for EF

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
            this.fileSize = buffer[offset+4]*256 + buffer[offset+5];
            this.fdb = buffer[offset+8];
            this.fileId = buffer[offset+11]*256 + buffer[offset+12];
            this.lifeCycleStatusByte = buffer[15];
        }
        
    }
    
}
