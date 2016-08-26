
import java.nio.ByteBuffer;
import java.util.Arrays;


//7000, 7001, 7002, 7004

/**
 *
 * @author gdotta
 */
public class FCITemplate {
    //Current FCITemplate data does not support security attributes or access
    //mode bytes
    
    //TODO solve the EF DF distinction with inheritance
    private boolean isDF;
    private boolean isEF;
    private String fileName; //only for DF
    private int fileSize; //only for EF
    private byte fdb; //only for EF
    private int fileId; 
    private byte lifeCycleStatusByte; //only for EF
    //TODO 

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
    
    public void buildFromBuffer(byte[] buffer, int offset, int length) throws Exception {
        //FABRIZIO: Esta Firma es la semantica que generalmente usan las tarjetas
        //para estas operaciones, aunque no tiene por que ser la que usemos nosotros...


        //TODO build a neat TLV parser
        if (buffer[offset] != 0x6F) {
            throw new Exception("Bad/Unknown FCI Template");
        }
        //TODO support DFs
        if (buffer[offset+2] == 0x83) {
            //DF
            this.isEF = false;
            this.isDF = true;
            //TODO implement a function to convert
            this.fileId = (0xff & buffer[offset+4])*256 + (0xff & buffer[offset+5]);
            
            //DF name TLV comes after FCI TLV
            //DF name TLV offset is offset + 2 byte for FCI tag and length + FCI data length
            //DF name Length position is offset + DF name TLV offset + 1
            //DF name starts at name TLV offset + 2
            int fciDataLength = buffer[offset+1];
            int nameTlvOffset = 2 + fciDataLength;
            int nameLength = buffer[nameTlvOffset+1];
            ByteBuffer name = ByteBuffer.wrap(Arrays.copyOfRange(buffer, nameTlvOffset+2, nameLength+1));
            this.fileName = Utils.asciiDecoder.decode(name).toString();
        } else if (buffer[offset+2] == 0x81) {
            //EF
            this.isEF = true;
            this.isDF = false;
            this.fileSize =  (0xff & buffer[offset+4])*256 + (0xff & buffer[offset+5]);
            this.fdb = buffer[offset+8];
            this.fileId =  (0xff & buffer[offset+11])*256 +  (0xff & buffer[offset+12]);
            this.lifeCycleStatusByte = buffer[15];
        } else {
            //Unknown FCI
            throw new Exception("Bad/Unknown FCI Template");
        }
    }
    
}
