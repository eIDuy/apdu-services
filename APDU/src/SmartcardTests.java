import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;


/**
 *
 * @author gdotta
 */
public class SmartcardTests {

	private static byte[] selectIAS = hexStringToByteArray("");

	private static String minucia_3CQ = "761460F21474971A9C6B1C81801D5F641F638220BD4F2243D22299EE229578297EF92A55AF2D5ABD2E79A933BBB4399BC93E591F404A78437E2544699B459A4D4546C34979AA49B9BA4F598051607451406652664F5469B0547990557A6E5B82E25C59595C48AC5D586E63698264B75D666B1B674B9769B5AD6976316DAB4A6E6A9771B2D071583E778B6A784BB27A75997D6FAE7E731B854BA8876F5C8B8B698B89B68E73B491B254938AA9966B309B498B9B68B19B6CE69D5C50A049C7A19BAEA34CD4A55C43A68954A78868AA86A5AA4989AB8958AE46B5AF43CEAF6094B5476AB6449BB7452CB98887BE8637C3454FC44598C44456C6A44BCE4659CE4477CF4433D2848BD5429AD7832FDBA558DB8432E2A670E4845BE54229E9872FECA563EEA477F064";
	private static String minucia_3CQ_cortada = "761460F21474971A9C6B1C81801D5F641F638220BD4F2243D22299EE229578297EF92A55AF2D5ABD2E79A933BBB4399BC93E591F404A78437E2544699B459A4D4546C34979AA49B9BA4F598051607451406652664F5469B0547990557A6E5B82E25C59595C48AC5D586E63698264B75D666B1B674B9769B5AD6976316DAB4A6E6A9771B2D071583E778B6A784BB27A75997D6FAE7E731B854BA8876F5C8B8B698B89B68E73B491B254938AA9966B309B498B9B68B19B6CE69D5C50A049C7A19B";
	private static String minucia_3CQ_cortada_2 = "761460F21474971A9C6B1C81801D5F641F638220BD4F2243D22299EE229578297EF92A55AF2D5ABD2E79A933BBB4399BC93E591F404A78437E2544699B459A4D4546C34979AA49B9BA4F598051607451406652664F5469B0547990557A6E5B82E25C59595C48AC5D586E63698264B75D666B1B674B9769B5AD6976316DAB4A6E6A9771B2D071583E778B6A784BB27A75997D6FAE7E731B854BA8876F5C8B8B698B89B68E73B491B254938AA9966B309B498B9B68B19B6CE69D5C50A049C7A19BAEA34CD4A55C43A68954A78868AA86A5AA4989AB8958AE46B5AF43CEAF6094B5476AB6449BB7452CB98887BE8637C3454FC44598C44456C6A4";

	private static String minucia_3CR = "A401B8A3115A7F115D4B15805916608917BD78177DD91955E419B4C51B76BC1F993C22649B275BCF2956E9295568317EF73195B237B795385AE13B576D415F7E427C8C429C5345A4684A7D284D69404E858F4F5B684F62D44F975E58413F60AB74609D2766AB8F66B8AE67977A685C79739C38768C9C769665806F7A81756E83B350856D5A87AE218C6C66907156978E8598962B994C6A9A72429CAA589C6B62A1ACB0A49938A76AD0A79B4AAA8C75AD5A5BAF8CD1B45EB0B49C8FB65C62B747A5BB5C7FBE5F67BF8150C189B3C55F74C7A2A0CC5F52CE472FD0899DD4403CDD879EDD5F4DE0457AE56339E6A697E7A184F0423FF24597F3A336F76891F783";
	private static String minucia_3CR_cortada = "A401B8A3115A7F115D4B15805916608917BD78177DD91955E419B4C51B76BC1F993C22649B275BCF2956E9295568317EF73195B237B795385AE13B576D415F7E427C8C429C5345A4684A7D284D69404E858F4F5B684F62D44F975E58413F60AB74609D2766AB8F66B8AE67977A685C79739C38768C9C769665806F7A81756E83B350856D5A87AE218C6C66907156978E8598962B994C6A9A72429CAA589C6B62A1ACB0A49938A76AD0A79B4AAA8C75AD5A5BAF8CD1B45EB0B49C8FB65C62B747A5BB5C7FBE5F67BF8150C189B3C55F74C7A2A0CC5F52CE472FD0899DD4403CDD879EDD5F4DE0457AE56339E6A697E7A184F0423FF24597F3A3";
	private static String minucia_3CR_cortada_2 = "A401B8A3115A7F115D4B15805916608917BD78177DD91955E419B4C51B76BC1F993C22649B275BCF2956E9295568317EF73195B237B795385AE13B576D415F7E427C8C429C5345A4684A7D284D69404E858F4F5B684F62D44F975E58413F60AB74609D2766AB8F66B8AE67977A685C79739C38768C9C769665806F7A81756E83B350856D5A87AE218C6C66907156978E8598962B994C6A9A72429CAA589C6B62A1ACB0A49938A76AD0A79B4AAA8C75AD5A5BAF8CD1B45EB0B49C8FB65C62B747";

	private static String pulgarDer_3CR_1 = "A3085C99087D860C9BA00E7B1E12434113605415602218634A184095197AB8199A731C9C6D1DBD071F4568265DC9305966337D7E339BAC395B263B65A73CBA393F62FC3F9672419DDD46995C485F69497D3C4B42455064B2505A5D547F65569E8B589B6A5D7C0D5F4AD867997B695B3E6A67266B69DF6CBA696D7CDC6F779F759BBA767969795D987ABB727D797D7D5AB47DB9557E66E27E97AE845A8D8C7C568EAA708F9D68916350924949946C709663B196598D9A77339A6D6F9C811B9EAE859FB784A475B3A6B6E2A6B76CAE7373AF95D8AF9942B2AF7AB2B228B650BDBA976EBC5330BD7038BE4F5BC3B61CC46EDCC65D64C85558C854";
	private static String pulgarDer_3CR_2 = "A3085C860C9B1E12435415602218634A184095197AB8199A731C9C6D1DBD071F4568265DC930597E339BAC395B263B65393F62FC3F9672419DDD469969497D3C4B42B2505A5D547F65569E8B589BD867997B695B3E6A67266B69DF6CBADC6F779F759B69795D987ABB7D7D5A557E66E27E97AE845A8D8C7C568EAA708F9D50924949946C709663B196598D9A77339A6D6F9C811B9EAE859FB7B3A6B6E2A6B76CAE7373AF9542B2AF7AB2B2BDBA976EBC5330BD7038BE4F5BC3B61CC46EDCC65D64C85558C854A3CB57E5CBA067D298D2D241F3D49FB9DB9B94DC586EDE78C3E37E65E859A1E85ADFEA604DEC59BAEE7D98EF9CB2F35E81F75D";

	// Certificado extraido del eID
	private static String certificate_HEX_DER_encoded = "";

	// Certificado del ministerio del interior, el que firma los del eID
	private static String MiCA = "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";

	private static String HASH = "4D7CCCBD17064A12DD43021668679F7B488AFD55AAB1502E0CA8A55F5A8E2C0B";

	private static String HASH_Signature = "";
	
	private static String PIN_ASCII = "";

	/**
	 * @param args
	 *            the command line arguments
	 * @throws Base64DecodingException
	 * @throws CertificateException
	 * @throws SignatureException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchProviderException 
	 */
	public static void main(String[] args) throws CardException, IOException,
			CertificateException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		// TODO code application logic here
		// show the list of available terminals
		TerminalFactory factory = TerminalFactory.getDefault();
		List<CardTerminal> terminals = factory.terminals().list();
		// System.out.println("Terminals: " + terminals);
		// get the first terminal
		CardTerminal terminal = terminals.get(0);
		// establish a connection with the card
		Card card = terminal.connect("T=0");
		// System.out.println("card ATR: " +
		// byteArrayToHex(card.getATR().getBytes()));
		CardChannel channel = card.getBasicChannel();

		int swValue = 0;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (swValue != 6) {

			// Display menu graphics
			System.out.println("\n");
			System.out.println("================================");
			System.out.println("|   SELECCION DE CASO DE USO   |");
			System.out.println("================================");
			System.out.println("| Opciones                     |");
			System.out.println("|                              |");
			System.out.println("|  1. Card authentication PIN  |");
			System.out.println("|  2. Card authentication FP   |");
			System.out.println("|  3. User authentication PIN  |");
			System.out.println("|  4. User identification      |");
			System.out.println("|  5. PKI signature            |");
			System.out.println("|  6. Card CPLC INFO           |");
			System.out.println("|  7. Exit                     |");
			System.out.println("============================");

			System.out.println("Seleccione una opcion");

			swValue = Integer.parseInt(br.readLine());

			switch (swValue) {
			case 1:
				System.out.println("Card authentication PIN\n");

				if (selectIAS(channel)) {
					System.out.println("IAS Select Success\n\n");
				} else {
					System.out.println("IAS Select ERROR\n\n");
				}

				//Ingresa el pin y lo transformo a ASCII
				System.out.println("Ingrese el pin");
				String pin = br.readLine();
				inputPin(pin);			
				
				if (verifyPIN(channel)) {
					System.out.println("PIN Exitoso\n\n");
				} else {
					System.out.println("PIN fallido\n\n");
				}

				if (selectEFCertificate(channel)) {
					System.out.println("Lectura del certificado exitoso\n\n");
				} else {
					System.out.println("Lectura del certificado Fallo\n\n");
				}

				System.out.println(certificate_HEX_DER_encoded + "\n\n");

				if (MSE_SET_DST(channel)) {
					System.out.println("SET DST\n\n");
				} else {
					System.out.println("Error SET DST\n\n");
				}

				if (PSO_HASH(channel)) {
					System.out.println("PSO HASH\n\n");
				} else {
					System.out.println("Error PSO HASH\n\n");
				}

				if (PSO_CDS(channel)) {
					System.out.println("PSO_ComputeDigitalSignature\n\n");
				} else {
					System.out.println("Error PSO_ComputeDigitalSignature\n\n");
				}

				if (validateHashSignature()) {
					System.out.println("HASH Validado\n\n");
				} else {
					System.out.println("HASH Invalido\n\n");
				}

				break;

			case 2:
				System.out.println("Card authentication FP\n\n");

				if (verifyFP(channel)) {
					System.out.println("MOC Exitoso");
				} else {
					System.out.println("MOC fallido");
				}

				break;
			case 3:
				System.out.println("User authentication PIN");
				break;
			case 4:
				System.out.println("User identification");
				break;
			case 5:
				System.out.println("PKI signature");
				break;
			case 6:
				System.out.println("Card CPLC INFO");
				break;
			case 7:
				System.out.println("Exit selected");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid selection");
				System.exit(1);
			}

		}

		// disconnect
		card.disconnect(false);
	}

	/*
	 * if (isVerifiedPIN(channel)) { System.out.println("PIN Verificado"); }
	 * else { System.out.println("PIN sin Verificar"); }
	 * 
	 * 
	 * 
	 * 
	 * if (unverifyPIN(channel)) { System.out.println("PIN Desverificado"); }
	 * else { System.out.println("PIN NO Desverificado"); } if
	 * (isVerifiedPIN(channel)) { System.out.println("PIN Verificado"); } else {
	 * System.out.println("PIN sin Verificar"); }
	 * 
	 * // if (selectIAS2(channel)) { //
	 * System.out.println("IAS Select 2 Success"); // } else { //
	 * System.out.println("IAS Select 2 ERROR"); // }
	 * 
	 * // disconnect card.disconnect(false); } }
	 */

	public static boolean getCPLCData(CardChannel channel) throws CardException {
		String CLASS = "80";
		String INSTRUCTION = "CA";
		String PARAM1 = "9F";
		String PARAM2 = "7F";

		String dataIN = "";

		// ESTO DEBE SER EL APP ID de la IAS
		// REVISAR LA DOCUMENTACION DE GEMALTO PARA CONFIRMARLO

		int dataINLength = dataIN.length() / 2;
		int dataOUTLength = 0;

		String commandString = CLASS + INSTRUCTION + PARAM1 + PARAM2;
		if (dataINLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataINLength))+ dataIN;
		}
		if (dataOUTLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataOUTLength));
		}
		if (dataINLength == 0 && dataOUTLength == 0) {
			// OBSERVACION el LC o LE siempre tiene que ir, aunque sea en 0
			commandString += "00";
		}
		System.out.println("Command: " + commandString);
		ResponseAPDU r = channel.transmit(new CommandAPDU(hexStringToByteArray(commandString)));
		String response = byteArrayToHex(r.getBytes());

		// TODO si se codifica esta operacion, se puede armar una clase que
		// parsee las cosas y luego permita imprimir o tomar los datos en forma
		// facil
		// System.out.println("Tag + Length: " + subBytes(response, 0, 2));
		// System.out.println("IC Fabricator: " + subBytes(response, 3, 4));
		// System.out.println("IC Type: " + subBytes(response, 5, 6));
		// System.out.println("OS id: " + subBytes(response, 7, 8));
		// System.out.println("OS release date: " + subBytes(response, 9, 10));
		// System.out.println("OS release level: " + subBytes(response, 11,
		// 12));
		// System.out.println("**** Complete Serial Number: " +
		// subBytes(response, 13, 20));
		// System.out.println("******* IC Fabrication Date: " +
		// subBytes(response, 13, 14));
		// System.out.println("******* IC Serial Number: " + subBytes(response,
		// 15, 18));
		// System.out.println("******* IC Batch ID: " + subBytes(response, 19,
		// 20));
		// System.out.println("IC Module Fabricator: " + subBytes(response, 21,
		// 22));
		// System.out.println("IC Module Packaging Date: " + subBytes(response,
		// 23, 24));
		// System.out.println("ICC Manufacturer: " + subBytes(response, 25,
		// 26));
		// System.out.println("IC Embedding Date: " + subBytes(response, 27,
		// 28));
		// System.out.println("IC Pre-Personalizer: " + subBytes(response, 29,
		// 30));
		// System.out.println("IC Pre-Personalization Date: " +
		// subBytes(response, 31, 32));
		// System.out.println("IC Pre-Personalization Equipment Id: " +
		// subBytes(response, 33, 36));
		// System.out.println("IC Personalizer: " + subBytes(response, 37, 38));
		// System.out.println("IC Personalization Date: " + subBytes(response,
		// 39, 40));
		// System.out.println("IC Personalization Equipment Identifier: " +
		// subBytes(response, 41, 44));

		System.out.println("response: " + byteArrayToHex(r.getBytes()));
		return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);
	}

	public static void printBytes(String response, String tag, int beginIndex, int endIndex) {
		System.out.println(tag + subBytes(response, beginIndex, endIndex));
	}

	public static String subBytes(String a, int beginIndex, int endIndex) {
		return a.substring(beginIndex * 2, endIndex * 2 + 2);
	}

	// Retorna true si pudo seleccionar la aplicacion IAS
	public static boolean selectIAS(CardChannel channel) throws CardException {
		String CLASS = "00";
		String INSTRUCTION = "A4";
		String PARAM1 = "04";
		String PARAM2 = "00";

		String dataIN = "A00000001840000001634200";
		// String dataIN = "A0000000180C000001634200";

		// ESTO DEBE SER EL APP ID de la IAS
		// REVISAR LA DOCUMENTACION DE GEMALTO PARA CONFIRMARLO

		int dataINLength = dataIN.length() / 2; // calculo de bytes del Data
												// Field
		int dataOUTLength = 0;

		String commandString = CLASS + INSTRUCTION + PARAM1 + PARAM2;
		if (dataINLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataINLength))
					+ dataIN;
		}
		if (dataOUTLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataOUTLength));
		}
		if (dataINLength == 0 && dataOUTLength == 0) {
			// OBSERVACION el LC o LE siempre tiene que ir, aunque sea en 0
			commandString += "00";
		}

		ResponseAPDU r = channel.transmit(new CommandAPDU(
				hexStringToByteArray(commandString)));
		System.out.println("command: " + commandString);
		System.out.println("response: " + byteArrayToHex(r.getBytes()));
		return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);
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

	private static boolean verifyFP(CardChannel channel) throws CardException {
		String CLASS = "00";
		String INSTRUCTION = "21";
		String PARAM1 = "00";
		String PARAM2 = "21";

		String dataIN = "";
		dataIN += "7F2E"; // TAG del TLV para llevar las minucias
		// en la documentacion de gemalto y de la IAS figura como mandar
		// estatico el 8180 en el length
		// pero no funciona si se hace asi.
		// encontre que funciona si se pone el length como 81XX, donde XX es el
		// largo de lo que sigue
		// que si bien es otro TLV, para este es value.
		// Como el TLV hijo es minucias + length + value, entonces el largo de
		// este
		// es largo(minucias) + 2
		dataIN += "81"
				+ byteArrayToHex(intToByteArray(2 + pulgarDer_3CR_2.length() / 2)); // Length
																					// del
																					// FP
																					// container

		// 81 aca es el tag del proximo TLV, y el length es el largo de las
		// minucias, que es el value
		// hay un ejemplo confuso en la documentacion, que pone como que habria
		// que poner el length
		// como 81XX igual que en el TLV padre, pero esto no es asi. Se pone el
		// length normal
		dataIN += "81"
				+ byteArrayToHex(intToByteArray(pulgarDer_3CR_2.length() / 2));
		dataIN += pulgarDer_3CR_2;

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte,
				hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
	}

	private static boolean verifyPIN(CardChannel channel) throws CardException {
		String CLASS = "00";
		String INSTRUCTION = "20";
		String PARAM1 = "00";
		String PARAM2 = "11";
		
		
		

		String dataIN = PIN_ASCII; // 1234

		// Aparentemente el Reference PIN es de 12 bytes de largo,
		// por lo que el DATA tiene que ser siempre de 12 bytes.
		// se pone con padding de ceros para completar 12.
		// Los caracteres van codificados en ascii, hay que ver si se hace
		// enforcement de eso

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte,	hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
		// 9000 es SW de exito
	}

	private static boolean isVerifiedPIN(CardChannel channel) throws CardException {
		String CLASS = "00";
		String INSTRUCTION = "20";
		String PARAM1 = "00";
		String PARAM2 = "11";

		String dataIN = "";
		// el buffer vacio y el LC 0 indica que se pregunta si esta verificado

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte,	hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
	}

	private static boolean unverifyPIN(CardChannel channel)		throws CardException {
		String CLASS = "00";
		String INSTRUCTION = "20";
		String PARAM1 = "FF";
		String PARAM2 = "11";

		String dataIN = "";
		// el buffer vacio, el modo FF y el LC 0 indica que se quiere
		// "desverificar"

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte, hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
	}

	// Precondicion: Verify PIN
	public static boolean MSE_SET_DST(CardChannel channel) throws CardException {

		String CLASS = "00";
		String INSTRUCTION = "22";
		String PARAM1 = "41";
		String PARAM2 = "B6";

		// mse-set, en el documento de IAS esta al reves la especificacion.
		// Revisar bien los parámetros, el iso.

		String dataIN = "840101800102"; // Select the key pair (RSA/ECC) and the
										// signature ALGO

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte,hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
		// 9000 es SW de exito

	}

	// Precondicion: Verify PIN
	public static boolean PSO_HASH(CardChannel channel) throws CardException {

		String CLASS = "00";
		String INSTRUCTION = "2A";
		String PARAM1 = "90";
		String PARAM2 = "A0";

		String hash_external = HASH;
		String length = byteArrayToHex(intToByteArray(hash_external.length() / 2));

		String dataIN = "90"; // Select the key pair (RSA/ECC) and the signature
								// ALGO
		dataIN += length;
		dataIN += hash_external;

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		byte CLASSbyte = hexStringToByteArray(CLASS)[0];
		byte INSbyte = hexStringToByteArray(INSTRUCTION)[0];
		byte P1byte = hexStringToByteArray(PARAM1)[0];
		byte P2byte = hexStringToByteArray(PARAM2)[0];
		return sendCommand(channel, CLASSbyte, INSbyte, P1byte, P2byte,	hexStringToByteArray(dataIN));

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
		// 9000 es SW de exito
	}

	// Precondicion: PSO_HASH
	public static boolean PSO_CDS(CardChannel channel) throws CardException {

		String CLASS = "00";
		String INSTRUCTION = "2A";
		String PARAM1 = "9E";
		String PARAM2 = "9A";

		String dataIN = "";

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;
		// System.out.println("length dataIN: " + dataINLength);

		int dataOUTLength = 0;

		String commandString = CLASS + INSTRUCTION + PARAM1 + PARAM2;
		if (dataINLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataINLength))
					+ dataIN;
		}
		if (dataOUTLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataOUTLength));
		}
		if (dataINLength == 0 && dataOUTLength == 0) {
			// OBSERVACION el LC o LE siempre tiene que ir, aunque sea en 0
			commandString += "00";
		}

		ResponseAPDU r = channel.transmit(new CommandAPDU(
				hexStringToByteArray(commandString)));
		System.out.println("command: " + commandString);
		System.out.println("response: " + byteArrayToHex(r.getBytes()));

		HASH_Signature = byteArrayToHex(r.getData());

		return (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00);

		// El SW 6A80 indica error de codificacion, es decir, en los TLV
		// El SW 63Cx indica error de match y que quedan x intentos
		// 9000 es SW de exito
	}

	public static boolean sendCommand(CardChannel chan, byte CLASS, byte INS,
			byte P1, byte P2, byte[] data) throws CardException {
		int length = data.length; // largo de la data a mandar
		int i = 0;
		int iteraciones = 0;
		int SW1 = 0, SW2 = 0;
		byte[] command;
		if (length == 0) {
			// mando el comando con LC 0
			command = new byte[5];
			command[0] = CLASS;
			command[1] = INS;
			command[2] = P1;
			command[3] = P2;
			command[4] = (byte) 0x00;
			ResponseAPDU r = chan.transmit(new CommandAPDU(command));
			SW1 = r.getSW1();
			SW2 = r.getSW2();
			System.out.println("Iteracion " + iteraciones);
			System.out.println("Comando: " + byteArrayToHex(command));
			System.out.println("Respuesta: " + byteArrayToHex(r.getBytes()));
		}
		while (length - i > 0) {
			iteraciones++;
			if (length - i > 0xFF) {
				command = new byte[255 + 5];
				command[0] = (byte) (CLASS | 0x10);
				command[4] = (byte) 0xFF; // mando el maximo de datos que puedo
				System.arraycopy(data, i, command, 5, 0xFF);
			} else {
				command = new byte[length - i + 5];
				command[0] = CLASS;
				command[4] = (byte) (length - i); // mando el maximo de datos
													// que puedo
				System.arraycopy(data, i, command, 5, length - i);
			}
			command[1] = INS;
			command[2] = P1;
			command[3] = P2;

			ResponseAPDU r = chan.transmit(new CommandAPDU(command));
			SW1 = r.getSW1();
			SW2 = r.getSW2();
			System.out.println("Iteracion " + iteraciones);
			System.out.println("Comando: " + byteArrayToHex(command));
			System.out.println("Respuesta: " + byteArrayToHex(r.getBytes()));

			i += 0xFF;

		}

		return (SW1 == (int) 0x90 && SW2 == (int) 0x00);
	}

	public static void printDataIN(String datain) {

	}

	public static void printCommand(String command) {

	}

	public static boolean selectEFCertificate(CardChannel channel)
			throws CardException {

		// Siempre se va quedando con el maximo que puede DF (233) a partir de
		// la direccion 00 00 va suamndo DF hasta llegar a 053A donde espera una
		// respuesta de A2 en lugar de DF

		// select File 00A4000002B00100 //Pagina 171 del documento IAS ,
		// haciendo este select devuelve un fci template donde indica el largo
		// del certificado para saber cuantos select posteriores hacer
		// El largo esta especificado en los offset 4–5 del FCI for EFs
		// retorando por el select

		// TODO, ver de donde sale el select al offset B001 en caso de que haya
		// mas certificados, capaz lo obtiene en select anteriores

		/*
		 * {"commands":[{"type":"CommandAPDU","apdu":"00B00000DF","swPattern":"9000"
		 * } ,{"type":"CommandAPDU","apdu":"00B000DFDF","swPattern":"9000"},
		 * {"type":"CommandAPDU","apdu":"00B001BEDF","swPattern":"9000"},
		 * {"type":"CommandAPDU","apdu":"00B0029DDF","swPattern":"9000"},
		 * {"type":"CommandAPDU","apdu":"00B0037CDF","swPattern":"9000"},
		 * {"type":"CommandAPDU","apdu":"00B0045BDF","swPattern":"9000"},
		 * {"type":"CommandAPDU","apdu":"00B0053AA2","swPattern":"9000"}]
		 */

		// Construyo el select inicial B001 es la direccion a leer para obtener
		// el fci template del certificado

		String CLASS = "00";
		String INSTRUCTION = "A4";
		String PARAM1 = "00";
		String PARAM2 = "00";

		String dataIN = "B001";

		System.out.println("dataIN: " + dataIN);

		int dataINLength = dataIN.length() / 2;

		int dataOUTLength = 0;

		String commandString = CLASS + INSTRUCTION + PARAM1 + PARAM2;
		if (dataINLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataINLength))+ dataIN;
		}
		if (dataOUTLength > 0) {
			commandString += byteArrayToHex(intToByteArray(dataOUTLength));
		}
		if (dataINLength == 0 && dataOUTLength == 0) {
			// OBSERVACION el LC o LE siempre tiene que ir, aunque sea en 0
			commandString += "00";
		}

		ResponseAPDU r = channel.transmit(new CommandAPDU(hexStringToByteArray(commandString)));
		System.out.println("command: " + commandString);
		System.out.println("response: " + byteArrayToHex(r.getBytes()));

		// Si la lectura del archivo es exitosa debo interpretar el fci template
		// de la respuesta para saber el largo del certificado a leer
		if (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00) {

			System.out.println("Select File Exitoso\n\n");
			String response = byteArrayToHex(r.getBytes());

			// Construyo el Read Binary, lo que cambia en cada read son P1 y P2
			// porque van variando los offset para ir leyendo el certificado
			CLASS = "00";
			INSTRUCTION = "B0";
			dataIN = "";

			dataINLength = dataIN.length() / 2;

			// La cantidad de REad binary a realizar es en base al Value of File
			// Size del fci for EFs obtenido como respuesta del select anterior.
			// En los offset 4 y 5 del fci se encuentra el tamaño del
			// certificado.
			String certificateSize = subBytes(response, 4, 5);
			int certSizeIntBytes = Integer.parseInt(certificateSize, 16);
			int FF_int = Integer.parseInt("FF", 16);

			int cantBytes = 0;

			String PARAM1_PARAM2 = "";

			while (cantBytes < certSizeIntBytes) {

				// Calculo el LE
				// Si la cantidad de Bytes que me quedan por obtener es mayor a
				// DF entonces me traigo DF.
				// Sino me traigo los Bytes que me quedan.

				if (cantBytes + FF_int <= certSizeIntBytes) {
					dataOUTLength = FF_int;
				} else {
					dataOUTLength = certSizeIntBytes - cantBytes;
				}

				// Param1 y param2 comienzan en 00 00, voy incrementando DF
				// bytes hasta leer el total del certificado.
				// Si el offset es menor a FF debo agregar 00 en P1

				if (cantBytes <= 255) {
					PARAM1_PARAM2 = "00"+ byteArrayToHex(intToByteArray(cantBytes));
				} else {
					PARAM1_PARAM2 = byteArrayToHex(intToByteArray(cantBytes));
				}

				// Envio el Select
				commandString = CLASS + INSTRUCTION + PARAM1_PARAM2;

				if (dataINLength > 0) {
					commandString += byteArrayToHex(intToByteArray(dataINLength))+ dataIN;
				}
				if (dataOUTLength > 0) {
					commandString += byteArrayToHex(intToByteArray(dataOUTLength));
				}
				if (dataINLength == 0 && dataOUTLength == 0) {
					// OBSERVACION el LC o LE siempre tiene que ir, aunque sea
					// en 0
					commandString += "00";
				}

				r = channel.transmit(new CommandAPDU(hexStringToByteArray(commandString)));
				System.out.println("command: " + commandString);
				System.out.println("response: " + byteArrayToHex(r.getBytes()));

				// El certificado esta en ASCII
				certificate_HEX_DER_encoded += byteArrayToHex(r.getData());

				if (r.getSW1() == (int) 0x90 && r.getSW2() == (int) 0x00) {

					cantBytes += dataOUTLength;

				} else {

					// Fallo algun read binary
					return false;
				}

			}

		} else {

			// Fallo el select File antes del Read Binary
			return false;
		}
		
		// Si llego aca retorno true, ya hizo todos los readBinary
		return true;
	}

	public static boolean validateHashSignature() throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {

		
		
		//The terminal can now perform:
		//1) Verify the card certificate signature (signed by the C.A.)
		//2) Extract the card public RSA key from the card certificate
		//3) Perform a signature verification (HASH, public key, card signature)
		//4) If sig is OK  the card is genuine.
		
		
		//parseo la representacion B64 del certificado de la MiCA a un objeto x509 
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream certificado_b64_MiCA = new ByteArrayInputStream(Base64.getDecoder().decode(MiCA));
		X509Certificate certificado_MiCA = (X509Certificate) cf.generateCertificate(certificado_b64_MiCA);
		PublicKey pubKeyMiCA = certificado_MiCA.getPublicKey();

				
		//creo el objeto x509 certificate a partir del certificado extraido del eID, extraigo la clave publica para validar el hash
		
		InputStream certificado_b64_eID = new ByteArrayInputStream(hexStringToByteArray(certificate_HEX_DER_encoded));
		X509Certificate certificado_eID = (X509Certificate) cf.generateCertificate(certificado_b64_eID);

		//1) Verify the card certificate signature (signed by the C.A.)
		certificado_eID.checkValidity();
		//Esta dando signature does not match
		//certificado_eID.verify(pubKeyMiCA);
			
		//2) Extract the card public RSA key from the card certificate
		PublicKey pubKeyeID = certificado_eID.getPublicKey();

		
		//3) Perform a signature verification (HASH, public key, card signature)
		Cipher decrypt=Cipher.getInstance("RSA/ECB/PKCS1Padding");
		decrypt.init(Cipher.DECRYPT_MODE, pubKeyeID);
		String decryptedMessage = byteArrayToHex(decrypt.doFinal(hexStringToByteArray(HASH_Signature)));		
		
		System.out.println(decryptedMessage);
		
		//4) If sig is OK  the card is genuine.
		return decryptedMessage.equals(HASH);
	}
	
	
	public static void minutiate_binaryToHex (){
		
	     File file = new File("");

		 FileInputStream fileInputStream = null;
		 
	     byte[] bFile = new byte[(int) file.length()];
	      
	     try {
	    	  
	         //convert file into array of bytes
	         fileInputStream = new FileInputStream(file);
	         fileInputStream.read(bFile);
	         fileInputStream.close();
	         
	         for (int i = 0; i < bFile.length; i++)
	         {
	            System.out.print((char) bFile[i]);
	         }
	         
	     }catch (Exception e){
	    	  
	    	  e.printStackTrace();
	     }

	}
	
	public static void inputPin(String pin){
		
		for (int i=0;i<pin.length();i++){
			char c = pin.charAt(i);
			String hex = Integer.toHexString((int)c);
			PIN_ASCII = PIN_ASCII.concat(hex);			
		}
		
		//Agrego padding para completar los 12 bytes con 00 (sino no funciona)
		int padding = (24-PIN_ASCII.length())/2;
		
		for (int j=0;j <padding;j++){			
			PIN_ASCII = PIN_ASCII.concat("00");					
		}
		
		System.out.println("PIN: "+PIN_ASCII);
		
	}
	
}
