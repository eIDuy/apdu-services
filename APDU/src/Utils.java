
import java.io.File;
import java.io.FileInputStream;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ffaggiani
 */
public class Utils {

    public static void printBytes(String response, String tag, int beginIndex, int endIndex) {
        System.out.println(tag + subBytes(response, beginIndex, endIndex));
    }

    public static String subBytes(String a, int beginIndex, int endIndex) {
        return a.substring(beginIndex * 2, endIndex * 2 + 2);
    }

    public static byte[] intToByteArray(int a) {
        byte[] b;
        if (a >= 256) {
            b = new byte[2];
            b[0] = (byte) (a / 256);
            b[1] = (byte) (a % 256);
        } else {
            b = new byte[1];
            b[0] = (byte) a;
        }

        return b;
    }

    public static byte[] convertlength(int a) {
        if (a >= 256) {
            byte[] b = new byte[3];
            byte[] b2 = intToByteArray(a);
            b[0] = (byte) 0x00;
            b[1] = b2[0];
            b[2] = b2[1];
            return b;
        } else {
            return intToByteArray(a);
        }
    }

    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static ResponseAPDU sendCommand(CardChannel chan, byte CLASS, byte INS,
            byte P1, byte P2, byte[] data, int le) throws CardException {
        int length = data.length; // largo de la data a mandar
        int i = 0;
        int iteraciones = 0;
        int SW1 = 0, SW2 = 0;
        byte[] command;
        ResponseAPDU r = null;

        //si datain vacio
        // mando el comando con LE solo
        if (length == 0) {
            //Si le distinto de 0 lo agrego al final de command           
            command = new byte[5];
            command[0] = CLASS;
            command[1] = INS;
            command[2] = P1;
            command[3] = P2;
            command[4] = intToByteArray(le)[0];
            r = chan.transmit(new CommandAPDU(command));
            SW1 = r.getSW1();
            SW2 = r.getSW2();
            System.out.println(byteArrayToHex(command));
            System.out.println(byteArrayToHex(r.getBytes()));
        }
        while (length - i > 0) {
            iteraciones++;
            if (length - i > 0xFF) {
            	command = new byte[255 + 6]; //le al final
                command[261] = intToByteArray(le)[0];
                command[0] = (byte) (CLASS | 0x10);
                command[4] = (byte) 0xFF; // mando el maximo de datos que puedo
                System.arraycopy(data, i, command, 5, 0xFF);
            } else {
                command = new byte[length - i + 6];
                command[length - i + 6 - 1] = intToByteArray(le)[0];//le al final
                command[0] = CLASS;
                command[4] = (byte) (length - i); // mando el maximo de datos
                // que puedo
                System.arraycopy(data, i, command, 5, length - i);
            }
            command[1] = INS;
            command[2] = P1;
            command[3] = P2;

            r = chan.transmit(new CommandAPDU(command));
            SW1 = r.getSW1();
            SW2 = r.getSW2();
            System.out.println(byteArrayToHex(command));
            System.out.println(byteArrayToHex(r.getBytes()));

            i += 0xFF;

        }
        return r;
    }

    public static void printDataIN(String datain) {

    }

    public static void printCommand(String command) {

    }

    public static String PinToAsciiHex(String pin) {

        //Return an Hex representation of the input Pin.
        //Each byte in hex represent an ascii digit.
        String pinAscii = "";

        for (int i = 0; i < pin.length(); i++) {
            char c = pin.charAt(i);
            String hex = Integer.toHexString((int) c);
            pinAscii = pinAscii.concat(hex);
        }

        //Padding with 00 to complete 12 bytes
        int padding = (24 - pinAscii.length()) / 2;

        for (int j = 0; j < padding; j++) {
            pinAscii += "00";
        }

        return pinAscii;
    }

    public static String bytesToHexFromFile(String filePath) {

        //bytesToHex function from file.
        //Situable for minutiate in binary file.
        //Return a single line with hex representation
        File file = new File(filePath);

        FileInputStream fileInputStream = null;

        byte[] bFile = new byte[(int) file.length()];

        String hexfromBinary = "";

        try {

            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();

            for (int i = 0; i < bFile.length; i++) {
                hexfromBinary += ((char) bFile[i]);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return hexfromBinary;
    }

}
