package spring;

/**
 *
 */
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import data.Auth.Rol;


public final class ChipherTool
{

	private static String key="pk35JqE58HeukB95";
	private ChipherTool()
	{

	}

	public static Rol getRol(String token) {
		return Rol.valueOf(ChipherTool.decrypt(token).split("_")[0]);
	}
	
	public static String getUser(String token) {
		String token2 = ChipherTool.decrypt(token).split("_")[1];
		return ChipherTool.decrypt(token2).split("_")[0];
	}
	
	public static String encrypt(final String property)
	{
		try
		{
			final Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			final Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			final byte[] encrypted = cipher.doFinal(property.getBytes());
			final Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(encrypted);
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e)
		{
			System.out.println(e.getStackTrace().toString());
			return "";
			
		}

	}

	public static String decrypt(final String string)
	{
		try
		{
			final Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			final Cipher cipher = Cipher.getInstance("AES");
			final Base64.Decoder decoder = Base64.getDecoder();
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			return new String(cipher.doFinal(decoder.decode(string)));
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e)
		{
			System.out.println(e.getStackTrace().toString());
			return "";

		}
	}

}
